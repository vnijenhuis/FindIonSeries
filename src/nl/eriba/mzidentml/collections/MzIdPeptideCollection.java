/*
 * @author Vikthor Nijenhuis
 * @project FindIonSeries toolkit.
 */
package nl.eriba.mzidentml.collections;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import nl.eriba.mzidentml.ionseries.objects.MzIdPeptide;

/**
 * Defines a collection of MzIdPeptide objects.
 *
 * @author vnijenhuis
 */
public class MzIdPeptideCollection {

    /**
     * Creates an ArrayList for MzIdPeptide objects.
     */
    private final ArrayList<MzIdPeptide> peptides;

    /**
     * ArrayList of MzIdPeptide objects.
     */
    public MzIdPeptideCollection() {
        peptides = new ArrayList<>();
    }

    /**
     * Adds a MzIdPeptide object to the ArrayList.
     *
     * @param peptide MzIdPeptide object.
     */
    public final void addPeptide(final MzIdPeptide peptide) {
        peptides.add(peptide);
    }

    /**
     * Removes a MzIdPeptide object from the ArrayList.
     *
     * @param peptide MzIdPeptide object.
     */
    public final void removePeptide(final MzIdPeptide peptide) {
        peptides.remove(peptide);
    }

    /**
     * Returns an ArrayList of MzIdPeptide objects.
     *
     * @return ArrayList of MzIdPeptide objects.
     */
    public final ArrayList<MzIdPeptide> getPeptides() {
        return peptides;
    }

    /**
     * Compare peptide sequences with eachother.
     * 
     * @return Integer value based on matching of the two Strings.
     */
    static Comparator<MzIdPeptide> getPeptideSequenceComparator() {
        return new Comparator<MzIdPeptide>() {
            @Override
            public int compare(MzIdPeptide o1, MzIdPeptide o2) {
                return o1.getPeptideSequence().compareTo(o2.getPeptideSequence());
            }
        };
    }

    /**
     * Sorts the collection based on the peptide sequence String.
     */
    public final void sortOnPeptideSequence() {
        Collections.sort(this.peptides, getPeptideSequenceComparator());
    }
}
