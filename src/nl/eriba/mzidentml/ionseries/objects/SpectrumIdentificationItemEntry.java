/*
 * @author Vikthor Nijenhuis
 * @project Peptide mzIdentML Identfication Module * 
 */
package nl.eriba.mzidentml.ionseries.objects;

import java.util.ArrayList;

/**
 * Defines the output of MzIdSpectrumIdentificationItem objects.
 *
 * @author vnijenhuis
 */
public class SpectrumIdentificationItemEntry {

    /**
     * Peptide amino acid sequence.
     */
    private final String peptideSequence;

    /**
     * Reversed peptide amino acid sequence.
     */
    private final String reversedPeptideSequence;

    /**
     * Peptide amino acid -10LogP score.
     */
    private final String psmScore;

    /**
     * Calculates mass-to-charge ratio.
     */
    private final Double calculatedMassToCharge;

    /**
     * Experimental mass-to-charge ratio.
     */
    private final Double experimentalMassToCharge;

    /**
     * Theoretical mass-to-charge ratio.
     */
    private final Double theoreticalMassToCharge;

    /**
     * Peptide amino acid sequence length.
     */
    private final Integer sequenceLength;

    /**
     * Ion fragment list.
     */
    private final ArrayList<MzIdIonFragment> fragmentList;

    /**
     * Defines a SpectrumIdentificationItemOutput object.
     *
     * @param peptideSequence Peptide amino acid sequence.
     * @param reversedPeptideSequence Reversed peptide amino acid sequence.
     * @param psmScore Peptide amino acid -10LogP score.
     * @param calculatedMassToCharge Calculates mass-to-charge ratio.
     * @param experimentalMassToCharge Experimental mass-to-charge ratio.
     * @param theoreticalMassToCharge Theoretical mass-to-charge ratio.
     * @param sequenceLength Peptide amino acid sequence length.
     * @param ionFragmentList Ion fragment list.
     */
    public SpectrumIdentificationItemEntry(final String peptideSequence, final String reversedPeptideSequence, final String psmScore, final Double calculatedMassToCharge,
            final Double experimentalMassToCharge, final Double theoreticalMassToCharge, final Integer sequenceLength, final ArrayList<MzIdIonFragment> ionFragmentList) {
        this.peptideSequence = peptideSequence;
        this.reversedPeptideSequence = reversedPeptideSequence;
        this.psmScore = psmScore;
        this.calculatedMassToCharge = calculatedMassToCharge;
        this.theoreticalMassToCharge = theoreticalMassToCharge;
        this.experimentalMassToCharge = experimentalMassToCharge;
        this.sequenceLength = sequenceLength;
        this.fragmentList = ionFragmentList;
    }

    /**
     * @return the peptide amino acid sequence as String.
     */
    public String getPeptideSequence() {
        return peptideSequence;
    }

    /**
     * @return the reversed peptide amino acid sequence as String.
     */
    public String getReversedPeptideSequence() {
        return reversedPeptideSequence;
    }

    /**
     * @return the psmScore as String.
     */
    public String getPsmScore() {
        return psmScore;
    }

    /**
     * @return the calculatedMassToCharge as Double.
     */
    public Double getCalculatedMassToCharge() {
        return calculatedMassToCharge;
    }

    /**
     * @return the theoreticalMassToCharge as Double.
     */
    public Double getTheoreticalMassToCharge() {
        return theoreticalMassToCharge;
    }

    /**
     * @return the experimentalMassToCharge as Double.
     */
    public Double getExperimentalMassToCharge() {
        return experimentalMassToCharge;
    }

    /**
     * @return the peptide amino acid sequence length as Integer.
     */
    public Integer getSequenceLength() {
        return sequenceLength;
    }

    /**
     * @return an ArrayList of MzIdIonFragment objects.
     */
    public ArrayList<MzIdIonFragment> getIonFragmentList() {
        return fragmentList;
    }

    /**
     * toString() function of the SpectrumIdentificationItemEntry object.
     *
     * @return SpectrumIdentificationItemEntry values as String.
     */
    @Override
    public String toString() {
        return "SpectrumIdentificationItemOutput{Sequence: " + this.getPeptideSequence() + " Reverse Sequence: " + this.getReversedPeptideSequence() + ", Score: " + this.getPsmScore() + ", Calculated m/z: " + this.getCalculatedMassToCharge()
                + ", Experimental m/z: " + this.getExperimentalMassToCharge() + ", Theoretical m/z: " + this.getTheoreticalMassToCharge() + ", length: " + this.getSequenceLength() + ", Ion Fragments: "
                + this.getIonFragmentList() + "}";
    }
}
