/*
 * @author Vikthor Nijenhuis
 * @project FindIonSeries toolkit.
 */
package nl.eriba.mzidentml.ionseries.objects;

/**
 * Defines the Substitute Modification of a peptide object.
 *
 * @author vnijenhuis
 */
public class MzIdSubstituteModification {

    /**
     * Name of the object in the MzIdentML file as String.
     */
    private final String objectName;

    /**
     * Mono-isotopic mass delta.
     */
    private final Double monoIsotopicMassDelta;

    /**
     * Location of the modification.
     */
    private final Integer location;

    /**
     * Original amino acid residue.
     */
    private final String originalResidue;

    /**
     * Amino acid replacement for the original residue.
     */
    private final String newResidue;

    /**
     * Substitute Modification object from the mzIdentML file.
     *
     * @param monoMassDelta mono-isotopic mass delta of the peptide sequence.
     * @param location sequence location of the modification.
     * @param originalResidue original amino acid residue.
     * @param newResidue replacement of the original amino acid residue.
     */
    public MzIdSubstituteModification(final Double monoMassDelta, final Integer location, final String originalResidue, final String newResidue) {
        this.objectName = "SubstituteModification";
        this.location = location;
        this.monoIsotopicMassDelta = monoMassDelta;
        this.originalResidue = originalResidue;
        this.newResidue = newResidue;
    }

    /**
     * Returns the location of the amino acid substitute modification.
     *
     * @return substitute modification location as Integer.
     */
    public final Integer getLocation() {
        return this.location;
    }

    /**
     * Returns the mono-isotopic mass delta of the peptide sequence.
     *
     * @return mono-isotopic mass delta as Double.
     */
    public final Double getMonoIsotopicMassDelta() {
        return this.monoIsotopicMassDelta;
    }

    /**
     * Returns the original amino acid residue.
     *
     * @return amino acid residue as String.
     */
    public final String getOriginalResidue() {
        return this.originalResidue;
    }

    /**
     * Returns the new amino acid residue.
     *
     * @return amino acid residue as String.
     */
    public final String getNewResidue() {
        return this.newResidue;
    }

    /**
     * toString function of the MzIdSubstituteModification object.
     *
     * @return object attributes as String.
     */
    @Override
    public String toString() {
        return this.objectName + "{monoisotopicMassDelta: " + this.getMonoIsotopicMassDelta() + ", location: " + this.getLocation() + ", residues: "
                + this.getOriginalResidue() + ", replaced_residues: " + this.getNewResidue() + "}";
    }
}
