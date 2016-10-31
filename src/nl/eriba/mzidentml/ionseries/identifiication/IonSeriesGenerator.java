/*
 * @author Vikthor Nijenhuis
 * @project FindIonSeries toolkit.
 */
package nl.eriba.mzidentml.ionseries.identifiication;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import nl.eriba.mzidentml.collections.MatchedIonSeriesCollection;
import nl.eriba.mzidentml.collections.MzIdPeptideCollection;
import nl.eriba.mzidentml.collections.SingleDatabaseReferenceCollection;
import nl.eriba.mzidentml.collections.UniquePeptideCollection;
import nl.eriba.mzidentml.ionseries.objects.MatchedIonSeries;
import nl.eriba.mzidentml.ionseries.objects.MzIdIonFragment;
import nl.eriba.mzidentml.ionseries.objects.MzIdModification;
import nl.eriba.mzidentml.ionseries.objects.MzIdPeptide;
import nl.eriba.mzidentml.ionseries.objects.MzIdSubstituteModification;
import nl.eriba.mzidentml.ionseries.objects.SingleDatabaseReference;
import nl.eriba.mzidentml.ionseries.objects.CombinedPeptideEntry;
import uk.ac.ebi.jmzidml.MzIdentMLElement;
import uk.ac.ebi.jmzidml.model.mzidml.CvParam;
import uk.ac.ebi.jmzidml.model.mzidml.FragmentArray;
import uk.ac.ebi.jmzidml.model.mzidml.IonType;
import uk.ac.ebi.jmzidml.model.mzidml.Modification;
import uk.ac.ebi.jmzidml.model.mzidml.Peptide;
import uk.ac.ebi.jmzidml.model.mzidml.PeptideEvidence;
import uk.ac.ebi.jmzidml.model.mzidml.SequenceCollection;
import uk.ac.ebi.jmzidml.model.mzidml.SpectrumIdentificationItem;
import uk.ac.ebi.jmzidml.model.mzidml.SpectrumIdentificationList;
import uk.ac.ebi.jmzidml.model.mzidml.SpectrumIdentificationResult;
import uk.ac.ebi.jmzidml.model.mzidml.SubstitutionModification;
import uk.ac.ebi.jmzidml.xml.io.MzIdentMLUnmarshaller;

/**
 * Generates the ion series of each SpectrumIdentificationItem.
 *
 * @author vnijenhuis
 */
public class IonSeriesGenerator implements Callable {

    /**
     * Specified signal intensity threshold.
     */
    private final Double userIntensityThreshold;

    /**
     * SpectrumIdentificationItem object.
     */
    private final SpectrumIdentificationItem spectrumItem;

    /**
     * List of unique peptides and the data corresponding to these peptides.
     */
    private final UniquePeptideCollection uniquePeptideCountList;

    /**
     * mzid format file reader.
     *
     * @param spectrumItem the given SpectrumIdentificationItem object.
     * @param uniquePeptideCountList list of UniquePeptideEntry objects.
     * @param intensityThreshold user specified signal intensity threshold.
     */
    public IonSeriesGenerator(final SpectrumIdentificationItem spectrumItem, final UniquePeptideCollection uniquePeptideCountList, final Double intensityThreshold) {
        this.userIntensityThreshold = intensityThreshold;
        this.spectrumItem = spectrumItem;
        this.uniquePeptideCountList = uniquePeptideCountList;
    }

    /**
     * Collects mzid data by storing the data into a collection of ScanID
     * objects.
     *
     * @param mzIdFile file with the .mzid extension and MzIdentMl format.
     * @param threads amount of threads used for the program.
     * @param intensityThreshold user specified signal intensity threshold.
     * @return returns a collection of ScanID objects.
     * @throws java.lang.InterruptedException
     * @throws java.util.concurrent.ExecutionException
     */
    public MatchedIonSeriesCollection generateIonSeries(final String mzIdFile, final Double intensityThreshold, final Integer threads) throws InterruptedException, ExecutionException {
        File mzIdentMLFile = new File(mzIdFile);
        System.out.println("Reading given file: " + mzIdFile);
        //Unmarshaller that transforms storage data format to a memory format
        MzIdentMLUnmarshaller unmarshaller = new MzIdentMLUnmarshaller(mzIdentMLFile);
        System.out.println("Retrieving <SpectrumIdentificationList> element...");
        SpectrumIdentificationList spectrumIdList = unmarshaller.unmarshal(MzIdentMLElement.SpectrumIdentificationList);
        SequenceCollection sequenceCollection = unmarshaller.unmarshal(SequenceCollection.class);
        Collections.sort(spectrumIdList.getSpectrumIdentificationResult(), new SortSpectrumResultBySequence());
        System.out.println("Retrieving <PeptideEvidence> elements...");
        List<PeptideEvidence> peptideEvidenceList = sequenceCollection.getPeptideEvidence();
        System.out.println("Creating unique peptide collection...");
        MzIdPeptideCollection peptideCollection = createPeptideCollection(sequenceCollection.getPeptide());
        SingleDatabaseReferenceCollection singleDatabaseReferenceCollection = createSequenceDatabaseReferenceCollection(peptideEvidenceList, peptideCollection);
        UniquePeptideCollection createUniquePeptideCountList = createUniquePeptideCountList(singleDatabaseReferenceCollection);
        MatchedIonSeriesCollection matchedIonSeriesCollection = new MatchedIonSeriesCollection();
        //Process data of each SpectrumIdentificationItem.
        Integer count = 0;
        System.out.println("Starting identification of ion series...");
        ExecutorService executor = Executors.newFixedThreadPool(threads);
        for (SpectrumIdentificationResult spectrumIdResult : spectrumIdList.getSpectrumIdentificationResult()) {
            count++;
            for (SpectrumIdentificationItem spectrumIdItem : spectrumIdResult.getSpectrumIdentificationItem()) {
                if (spectrumIdItem.isPassThreshold()) {
                    Callable<MatchedIonSeries> callable = new IonSeriesGenerator(spectrumIdItem, createUniquePeptideCountList, intensityThreshold);
                    //Collects the output from the call function
                    Future<MatchedIonSeries> future = executor.submit(callable);
                    MatchedIonSeries matchedIonSeries = future.get();
                    matchedIonSeriesCollection.addMatchedIonSeries(matchedIonSeries);
                }
            }
            if (count % 2000 == 0) {
                System.out.println("Matched data for " + count + " <SpectrumIdentificationResult> elements.");
            }
        }
        executor.shutdown();
        System.out.println("Matched data for " + count + " <SpectrumIdentificationResult> elements.");
        return matchedIonSeriesCollection;
    }

    /**
     * Call function that can be used by a thread to execute the given task.
     *
     * @return MatchedIonSeries object.
     */
    @Override
    public Object call() {
        String score = "";
        String peptideSequence = spectrumItem.getPeptideRef();
        List<IonType> fragmentList = spectrumItem.getFragmentation().getIonType();
        String accessions = getAccessions(peptideSequence);
        //Process all ion fragments of the given peptide amino acid sequence.
        ArrayList<MzIdIonFragment> ionFragmentList = createMzIdIonFragmentList(fragmentList);
        //Determines the length of the actual peptide sequence.
        //Gather psmScore from the cvParam list.
        List<CvParam> parameterList = spectrumItem.getCvParam();
        for (CvParam parameter : parameterList) {
            if (parameter.getName().contains("PSM score")) {
                score = parameter.getValue();
                break;
            }
        }
        Double peptideScore = Double.parseDouble(score);
        //Determine amino acid sequence length.
        Integer sequenceLength;
        if (peptideSequence.contains("_")) {
            sequenceLength = peptideSequence.split("_")[0].length();
        } else {
            sequenceLength = peptideSequence.length();
        }
        ArrayList<Integer> bIonIndices = new ArrayList<>(sequenceLength);
        ArrayList<Integer> yIonIndices = new ArrayList<>(sequenceLength);
        ArrayList<Integer> combinedIonIndices = new ArrayList<>(sequenceLength);
        ArrayList<Integer> combinedAllIonIndices = new ArrayList<>();
        ArrayList<Integer> finalIndexList = new ArrayList<>();
        for (MzIdIonFragment ionFragment : ionFragmentList) {
            String name = ionFragment.getName();
            List<Integer> indexList = ionFragment.getIndexList();
            ArrayList<Boolean> intensityValues = ionFragment.getItensityValues();
            //Removes hits with different index and intensity counts. (size should be the same to process data.
           if (intensityValues.size() == indexList.size()) {
                for (int i = 0; i < indexList.size(); i++) {
                    Integer listIndex = indexList.get(i);
                    Integer sequenceIndex = listIndex;
                    if (intensityValues.get(i)) {
                        if (name.matches("frag: y ion")) {
                            if (!yIonIndices.contains(sequenceIndex)) {
                                yIonIndices.add(sequenceIndex);
                            }
                            if (!combinedAllIonIndices.contains(sequenceIndex)) {
                                combinedAllIonIndices.add(sequenceIndex);
                            }
                        } else if (name.matches("(frag: y ion -).*")) {
                            if (!combinedIonIndices.contains(sequenceIndex)) {
                                combinedIonIndices.add(sequenceIndex);
                            }
                            if (!combinedAllIonIndices.contains(sequenceIndex)) {
                                combinedAllIonIndices.add(sequenceIndex);
                            }
                        } else if (name.matches("frag: b ion")) {
                            sequenceIndex = sequenceLength - listIndex;
                            if (!bIonIndices.contains(sequenceIndex)) {
                                bIonIndices.add(sequenceIndex);
                            }
                            if (!combinedAllIonIndices.contains(sequenceIndex)) {
                                combinedAllIonIndices.add(sequenceIndex);
                            }
                        } else if (name.matches("(frag: b ion -).*")) {
                            if (!combinedIonIndices.contains(sequenceIndex)) {
                                combinedIonIndices.add(sequenceIndex);
                            }
                            if (!combinedAllIonIndices.contains(sequenceIndex)) {
                                combinedAllIonIndices.add(sequenceIndex);
                            }
                        } else if (name.contains("immonium")) {
                            if (!combinedIonIndices.contains(sequenceIndex)) {
                                combinedIonIndices.add(sequenceIndex);
                            }
                            if (!combinedAllIonIndices.contains(sequenceIndex)) {
                                combinedAllIonIndices.add(sequenceIndex);
                            }
                        }
                    }
                }
            }
        }
        //Flag 0 for incomplete ion serie, flag 1 for complete b ion serie, flag 2 for complete y ion serie, flag 3 for combined ion series (includes immonium)
        Integer ionSerieFlag = 0;
        sequenceLength--;
        if (sequenceLength == bIonIndices.size()) {
            ionSerieFlag = 1;
            finalIndexList = bIonIndices;
        } else if (sequenceLength == yIonIndices.size()) {
            ionSerieFlag = 2;
            finalIndexList = yIonIndices;
        } else if (sequenceLength == combinedIonIndices.size()) {
            ionSerieFlag = 3;
            finalIndexList = combinedIonIndices;
        } else if (sequenceLength == combinedAllIonIndices.size()) {
            ionSerieFlag = 4;
            finalIndexList = combinedAllIonIndices;
        }
        Collections.sort(finalIndexList);
        MatchedIonSeries ionSeries = new MatchedIonSeries(peptideSequence, peptideScore, accessions, combinedIonIndices, bIonIndices, yIonIndices, combinedAllIonIndices, finalIndexList, ionSerieFlag);
        return ionSeries;
    }

    /**
     * Creates a collection of SingleDatabaseReference objects.
     *
     * @param peptideEvidenceList list of PeptideEvidence objects from the jmzidentml package.
     * @param peptideCollection list of MzIdPeptide objects.
     * @return collection of SingleDatabaseReference objects.
     */
    private SingleDatabaseReferenceCollection createSequenceDatabaseReferenceCollection(final List<PeptideEvidence> peptideEvidenceList, final MzIdPeptideCollection peptideCollection) {
        System.out.println("Creating SequenceDatabaseReference object collection...");
        SingleDatabaseReferenceCollection sequenceDatabaseReferenceCollection = new SingleDatabaseReferenceCollection();
        Collections.sort(peptideEvidenceList, new SortPeptideEvidenceCollectionOnSequence());
        peptideCollection.sortOnPeptideSequence();
        for (PeptideEvidence peptideEvidence : peptideEvidenceList) {
            if (!peptideEvidence.isIsDecoy()) {
                ArrayList<MzIdPeptide> removeablePeptideEntries = new ArrayList<>();
                String proteinAccession = peptideEvidence.getDBSequenceRef();
                Integer start = peptideEvidence.getStart();
                Integer end = peptideEvidence.getEnd();
                String peptideSequence = peptideEvidence.getPeptideRef();
                String pre = peptideEvidence.getPre();
                String post = peptideEvidence.getPost();
                String id = peptideEvidence.getId().split("_")[1];
                Integer evidenceId = Integer.parseInt(id);
                MzIdPeptide newEntry = null;
                ArrayList<String> modifications = new ArrayList<>();
                for (MzIdPeptide entry : peptideCollection.getPeptides()) {
                    if (entry.getModifications().isEmpty() && entry.getSubstituteModifications().isEmpty()) {
                        removeablePeptideEntries.add(entry);
                    } else if (entry.getPeptideSequence().equals(peptideSequence)) {
                        newEntry = entry;
                        for (MzIdModification modification : entry.getModifications()) {
                            for (String name : modification.getNames()) {
                                if (!modifications.contains(name)) {
                                    modifications.add(name);
                                }
                            }
                        }
                    }
                }
                removeablePeptideEntries.add(newEntry);
                for (MzIdPeptide x : removeablePeptideEntries) {
                    peptideCollection.getPeptides().remove(x);
                }
                SingleDatabaseReference sequenceDatabaseReference = new SingleDatabaseReference(proteinAccession, evidenceId, peptideSequence, start, end, pre, post, modifications);
                sequenceDatabaseReferenceCollection.addDatabaseReference(sequenceDatabaseReference);
            }
        }
        return sequenceDatabaseReferenceCollection;
    }

    /**
     * Creates a list of UniquePeptideEntry objects.
     *
     * @param sequenceDatabaseReferenceCollection list of
     * SingleDatabaseReference objects.
     * @return collection of UniquePeptideEntry objects.
     */
    private UniquePeptideCollection createUniquePeptideCountList(final SingleDatabaseReferenceCollection singleDatabaseReferenceCollection) {
        System.out.println("Creating list for peptide data objects.");
        //Determine if a sequence is unique to one accession.
        UniquePeptideCollection uniquePeptides = new UniquePeptideCollection();
        ArrayList<String> accessionList = new ArrayList<>();
        //Sort collections on sequence. This causes UniquePeptideEntry list to be sorted on sequence as well.
        singleDatabaseReferenceCollection.sortOnPeptideSequence();
        //Get first entry sequence.
        String targetSequence = singleDatabaseReferenceCollection.getDatabaseSequenceReferenceList().get(0).getPeptideSequence();
        for (SingleDatabaseReference databaseReference : singleDatabaseReferenceCollection.getDatabaseSequenceReferenceList()) {
            //Check if current sequence matches the targetSequence and add accession to given sequence.
            if (databaseReference.getPeptideSequence().equals(targetSequence)) {
                //if accession is not present add it to the list. Duplicate accessions are not necessary.
                if (!accessionList.contains(databaseReference.getProteinAccession())) {
                    accessionList.add(databaseReference.getProteinAccession());
                }
                if (!accessionList.contains(databaseReference.getProteinAccession())) {
                    accessionList.add(databaseReference.getProteinAccession());
                }
            } else {
                //UniquePeptideEntry object is created that contains the target sequence and the list of accession ids.
                Collections.sort(accessionList);
                CombinedPeptideEntry uniquePeptide = new CombinedPeptideEntry(targetSequence, accessionList);
                uniquePeptides.addUniquePeptideEntry(uniquePeptide);
                accessionList = new ArrayList<>();
                accessionList.add(databaseReference.getProteinAccession());
                targetSequence = databaseReference.getPeptideSequence();
            }
        }
        return uniquePeptides;
    }

    /**
     * Creates a collection of MzIdPeptide objects.
     *
     * @param peptides list of PeptideItem objects.
     * @return collection of MzIdPeptide objects.
     */
    private MzIdPeptideCollection createPeptideCollection(final List<Peptide> peptides) {
        System.out.println("Creating MzIdPeptide object collection...");
        MzIdPeptideCollection newPeptideCollection = new MzIdPeptideCollection();
        //Loops through list of all PeptideItem objects.
        for (Peptide peptide : peptides) {
            String id = peptide.getId();
            String sequence = peptide.getPeptideSequence();
            List<Modification> mods = peptide.getModification();
            List<SubstitutionModification> subMods = peptide.getSubstitutionModification();
            ArrayList<MzIdSubstituteModification> subModificationList = new ArrayList<>();
            ArrayList<MzIdModification> modificationList = new ArrayList<>();
            //Create list of Modification objects.
            for (Modification modification : mods) {
                Integer location = modification.getLocation();
                ArrayList<String> nameList = new ArrayList<>();
                for (CvParam param : modification.getCvParam()) {
                    String name = param.getName();
                    nameList.add(name);
                }
                Double monoMassDelta = modification.getMonoisotopicMassDelta();
                List<String> modResidues = modification.getResidues();
                ArrayList<String> residueList = new ArrayList<>();
                for (String residue : modResidues) {
                    residueList.add(residue);
                }
                //Stores data in new Modification object.
                MzIdModification modificationObject = new MzIdModification(monoMassDelta, location, residueList, nameList);
                //Adds objects to the list.
                modificationList.add(modificationObject);
            }
            //Create list of SubstituteModification objects.
            for (SubstitutionModification subModification : subMods) {
                Double monoMassDelta = subModification.getMonoisotopicMassDelta();
                Integer location = subModification.getLocation();
                String originalResidue = subModification.getOriginalResidue();
                String replacedResidue = subModification.getReplacementResidue();
                //Stores data in new SubstituteModification object.
                MzIdSubstituteModification subModificationObject = new MzIdSubstituteModification(monoMassDelta, location, originalResidue, replacedResidue);
                //Adds objects to the list.
                subModificationList.add(subModificationObject);
            }
            //Stores data in new PeptideItem object.
            MzIdPeptide peptideObject = new MzIdPeptide(id, sequence, modificationList, subModificationList);
            //Adds objects to the MzIdPeptideCollection.
            newPeptideCollection.addPeptide(peptideObject);
        }
        return newPeptideCollection;
    }

    /**
     * Creates a list of MzIdIonFragments that pass the user specified
     * threshold.
     *
     * @param fragmentList list of IonType objects.
     * @return filtered list of MzIdIonFragment objects.
     */
    final ArrayList<MzIdIonFragment> createMzIdIonFragmentList(List<IonType> fragmentList) {
        ArrayList<MzIdIonFragment> ionFragmentList = new ArrayList<>();
        for (IonType ionType : fragmentList) {
            List<Integer> indexList = ionType.getIndex();
            ArrayList<Double> measuredMassToChargeValues = new ArrayList<>();
            ArrayList<Boolean> passedIntensityThreshold = new ArrayList<>();
            FragmentArray fragmentArray = ionType.getFragmentArray().get(0);
            //Gathers measured mass to charge values of each fragment array.
            for (float fragmentValue : fragmentArray.getValues()) {
                double measuredMassToCharge = fragmentValue;
                measuredMassToChargeValues.add(measuredMassToCharge);
            }
            //Set standard threshold to 5%.
            Double intensityThreshold = 0.05;
            // Calculate the user defined intensity threshold value per peptide sequence.
            FragmentArray measureIntensity = ionType.getFragmentArray().get(1);
            if (userIntensityThreshold >= intensityThreshold) {
                intensityThreshold = userIntensityThreshold;
            }
            Double highestPeakIntensity = 0.0;
            //Test peakIntensity versus the specified threshold.
            for (Float intensityValue : measureIntensity.getValues()) {
                double intensity = intensityValue;
                Double peakIntensity = intensity * intensityThreshold;
                if (peakIntensity > highestPeakIntensity) {
                    highestPeakIntensity = peakIntensity;
                }
            }
            //Test if ion intensity passes the user specified threshold.
            for (Float intensityValue : measureIntensity.getValues()) {
                double intensity = intensityValue;
                if (intensity >= highestPeakIntensity) {
                    passedIntensityThreshold.add(true);
                } else {
                    passedIntensityThreshold.add(false);
                }
            }
            //Create MzIdIonFragment object containing the name, indices, m/z values and a true/false list for passing the intensity threshold.
            String name = ionType.getCvParam().getName();
            MzIdIonFragment fragment = new MzIdIonFragment(name, indexList, measuredMassToChargeValues, passedIntensityThreshold);
            ionFragmentList.add(fragment);
        }
        return ionFragmentList;
    }

    /**
     * Gathers all accessions per peptide sequence and determines if the peptide
     * is unique to a protein sequence.
     *
     * @param sequence peptide sequence.
     * @return EvidenceData with a list of accessions and uniqueness flag.
     */
    public final String getAccessions(final String sequence) {
        String accessions = "";
        for (CombinedPeptideEntry peptide : uniquePeptideCountList.getUniquePeptideList()) {
            if (peptide.getSequence().equals(sequence)) {
                for (String accession : peptide.getAccessionList()) {
                    if (accessions.isEmpty()) {
                        accessions += accession;
                    } else {
                        accessions += ":" + accession;
                    }
                }
                break;
            }
        }
        return accessions;
    }

}
