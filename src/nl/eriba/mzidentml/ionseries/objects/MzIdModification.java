/*
 * @author Vikthor Nijenhuis
 * @project Peptide mzIdentML Identfication Module *
 */
package nl.eriba.mzidentml.ionseries.objects;

import java.util.ArrayList;

/**
 * Defines the MzIdModification of a Peptide object.
 *
 * @author vnijenhuis
 */
public class MzIdModification {

    /**
     * Name of the object in the MzIdentML file as String.
     */
    private final String objectName;

    /**
     * Mono-isotopic mass delta of the peptide sequence.
     */
    private final Double monoIsotopicMassDelta;

    /**
     * Location of the amino acid modification.
     */
    private final Integer modificationLocation;

    /**
     * List of amino acid residues.
     */
    private final ArrayList<String> residueList;

    /**
     * List of modification names.
     */
    private final ArrayList<String> modificationNameList;

    /**
     * Modification object from the mzIdentML file.
     *
     * @param monoIsotopicMassDelta mono-isotopic mass delta of the peptide sequence.
     * @param modificationLocation location of the amino acid modification.
     * @param residueList list of amino acid residues.
     * @param modificationNameList List of modification names.
     */
    public MzIdModification(final Double monoIsotopicMassDelta, final Integer modificationLocation, final ArrayList<String> residueList, final ArrayList<String> modificationNameList) {
        this.objectName = "Modification";
        this.monoIsotopicMassDelta = monoIsotopicMassDelta;
        this.modificationLocation = modificationLocation;
        this.residueList = residueList;
        this.modificationNameList = modificationNameList;
    }

    /**
     * Returns the mono-isotopic mass delta of the ion.
     *
     * @return the monoIsotopicMassDelta as Integer.
     */
    public Double getMonoIsotopicMassDelta() {
        return monoIsotopicMassDelta;
    }

    /**
     * Returns the location of the amino acid modification.
     *
     * @return the location as Integer
     */
    public Integer getLocation() {
        return modificationLocation;
    }

    /**
     * Returns a list of amino acid residues.
     *
     * @return list of residues.
     */
    public ArrayList<String> getResidues() {
        return residueList;
    }

    /**
     * Returns a list of names of modifications.
     *
     * @return ArrayList with modification names as String.
     */
    public ArrayList<String> getNames() {
        return modificationNameList;
    }

    /**
     * toString function of the MzIdModification object.
     *
     * @return object attributes as String.
     */
    @Override
    public String toString() {
        return this.objectName + "{monoisotopicMassDelta: " + this.getMonoIsotopicMassDelta() + ", location: "
                + this.getLocation() + ", residues: " + this.getResidues() + "}";
    }
}
