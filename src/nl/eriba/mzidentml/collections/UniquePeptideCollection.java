/*
 * @author Vikthor Nijenhuis
 * @project FindIonSeries toolkit
 */
package nl.eriba.mzidentml.collections;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import nl.eriba.mzidentml.ionseries.objects.CombinedPeptideEntry;

/**
 * Defines a collection of UniquePeptideEntry objects.
 *
 * @author vnijenhuis
 */
public class UniquePeptideCollection {

    /**
     * Creates an ArrayList for UniquePeptideEntry objects.
     */
    private final ArrayList<CombinedPeptideEntry> uniquePeptideEntries;

    /**
     * ArrayList of UniquePeptideEntry objects.
     */
    public UniquePeptideCollection() {
        uniquePeptideEntries = new ArrayList<>();
    }

    /**
     * Adds a UniquePeptideEntry object to the ArrayList.
     *
     * @param uniquePeptideEntry UniquePeptideEntry object.
     */
    public final void addUniquePeptideEntry(final CombinedPeptideEntry uniquePeptideEntry) {
        uniquePeptideEntries.add(uniquePeptideEntry);
    }

    /**
     * Removes a UniquePeptideEntry object from the ArrayList.
     *
     * @param uniquePeptideEntry UniquePeptideEntry object.
     */
    public final void removeUniquePeptideEntry(final CombinedPeptideEntry uniquePeptideEntry) {
        uniquePeptideEntries.remove(uniquePeptideEntry);
    }

    /**
     * Returns an ArrayList of UniquePeptideEntry objects.
     *
     * @return ArrayList of UniquePeptideEntry objects.
     */
    public final ArrayList<CombinedPeptideEntry> getUniquePeptideList() {
        return uniquePeptideEntries;
    }

    /**
     * Compares peptide sequences with eachother.
     *
     * @return Integer value based on compared sequences.
     */
    static Comparator<CombinedPeptideEntry> sortOnPeptideSequenceComparator() {
        return new Comparator<CombinedPeptideEntry>() {
            @Override
            public int compare(CombinedPeptideEntry o1, CombinedPeptideEntry o2) {
                return o1.getSequence().compareTo(o2.getSequence());
            }
        };
    }

    /**
     * Compares the peptide sequences of all entries in the given collection and
     * sorts the collection based on the results.
     */
    public final void sortOnPeptideSequence() {
        Collections.sort(this.uniquePeptideEntries, sortOnPeptideSequenceComparator());
    }
}
