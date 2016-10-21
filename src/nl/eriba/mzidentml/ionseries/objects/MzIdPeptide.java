/*
 * @author Vikthor Nijenhuis
 * @project FindIonSeries toolkit.
 */
package nl.eriba.mzidentml.ionseries.objects;

import java.util.ArrayList;

/**
 * Defines a Peptide Object.
 *
 * @author vnijenhuis
 */
public class MzIdPeptide {

    /**
     * Name of the object in the MzIdentML file as String.
     */
    private final String objectName;

    /**
     * Id of the Peptide object.
     */
    private final String peptideId;

    /**
     * Amino acid sequence of the Peptide object.
     */
    private final String peptideSequence;

    /**
     * List of amino acid modifications.
     */
    private final ArrayList<MzIdModification> modificationList;

    /**
     * List of amino acid sub modifications.
     */
    private final ArrayList<MzIdSubstituteModification> subModificationList;

    /**
     * Peptide object from the mzIdentML file.
     *
     * @param peptideId Peptide object id.
     * @param peptideSequence peptide sequence.
     * @param modificationList list of amino acid residue modifications.
     * @param subModificationList list of amino acid residue substitute modifications.
     */
    public MzIdPeptide(String peptideId, String peptideSequence, ArrayList<MzIdModification> modificationList, ArrayList<MzIdSubstituteModification> subModificationList) {
        this.objectName = "Peptide";
        this.peptideId = peptideId;
        this.peptideSequence = peptideSequence;
        this.modificationList = modificationList;
        this.subModificationList = subModificationList;
    }

    /**
     * Returns the peptide ID.
     *
     * @return peptide id as String.
     */
    public final String getPeptideId() {
        return this.peptideId;
    }

    /**
     * Returns the peptide sequence.
     *
     * @return peptide sequence as String.
     */
    public final String getPeptideSequence() {
        return this.peptideSequence;
    }

    /**
     * Returns the peptide amino acid modifications.
     *
     * @return list of peptide modifications
     */
    public final ArrayList<MzIdModification> getModifications() {
        return this.modificationList;
    }

    /**
     * Returns the peptide amino acid substitute modifications.
     *
     * @return list of peptide substitute modifications.
     */
    public final ArrayList<MzIdSubstituteModification> getSubstituteModifications() {
        return this.subModificationList;
    }

    /**
     * toString function of the MzIdPeptide object.
     *
     * @return object attributes as String.
     */
    @Override
    public String toString() {
        return this.objectName + "{Peptide id: " + this.getPeptideId() + ", Peptide sequence: " + this.getPeptideSequence() + ", Modification: "
                + this.getModifications() + ", Substitute modification: " + this.getSubstituteModifications() + "}";
    }
}
