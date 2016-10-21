/*
 * @author Vikthor Nijenhuis
 * @project FindIonSeries toolkit.
 */
package nl.eriba.mzidentml.ionseries.objects;

import java.util.ArrayList;

/**
 * Defines a combination of peptide entries.
 *
 * @author vnijenhuis
 */
public class CombinedPeptideEntry {

    private final String peptideSequence;
    private final ArrayList<String> accessionList;

    /**
     * Defines a combined peptide entry which contains a list of accessions for
     * a given peptide sequence.
     *
     * @param sequence peptide amino acid sequence.
     * @param accessionList ArrayList of accession ids.
     */
    public CombinedPeptideEntry(String sequence, ArrayList<String> accessionList) {
        this.peptideSequence = sequence;
        this.accessionList = accessionList;
    }

    /**
     * Returns a list of accessions ids.
     *
     * @return ArrayList with accession ids as String.
     */
    public final ArrayList<String> getAccessionList() {
        return this.accessionList;
    }

    /**
     * Returns the amount of accessions in the accession list.
     *
     * @return ArrayList size as Integer.
     */
    public final Integer getAccessionCount() {
        return this.accessionList.size();
    }

    /**
     * Returns the peptide amino acid sequence.
     *
     * @return peptide amino acid sequence as String.
     */
    public final String getSequence() {
        return this.peptideSequence;
    }

    /**
     * ToString function that displays the data present in this object.
     *
     * @return object data as String.
     */
    @Override
    public String toString() {
        return "UniquePeptide{Sequence: " + this.getSequence() + ", Accessions: " + this.getAccessionList() + "}";
    }
}
