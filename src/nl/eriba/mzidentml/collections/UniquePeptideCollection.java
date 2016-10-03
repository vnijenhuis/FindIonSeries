/*
 * @author Vikthor Nijenhuis
 * @project Peptide mzIdentML Identfication Module * 
 */
package nl.eriba.mzidentml.collections;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import nl.eriba.mzidentml.ionseries.objects.UniquePeptideEntry;

/**
 *
 * @author f103013
 */
public class UniquePeptideCollection {
    
    /**
     * Creates an ArrayList for UniquePeptideEntry objects.
     */
    private final ArrayList<UniquePeptideEntry> uniquePeptideEntries;

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
    public final void addUniquePeptideEntry(final UniquePeptideEntry uniquePeptideEntry) {
        uniquePeptideEntries.add(uniquePeptideEntry);
    }

    /**
     * Removes a UniquePeptideEntry object from the ArrayList.
     *
     * @param uniquePeptideEntry UniquePeptideEntry object.
     */
    public final void removeUniquePeptideEntry(final UniquePeptideEntry uniquePeptideEntry) {
        uniquePeptideEntries.remove(uniquePeptideEntry);
    }

    /**
     * Returns an ArrayList of UniquePeptideEntry objects.
     *
     * @return ArrayList of UniquePeptideEntry objects.
     */
    public final ArrayList<UniquePeptideEntry> getUniquePeptideList() {
        return uniquePeptideEntries;
    }
    
    /**
     * 
     * @return 
     */
    static Comparator<UniquePeptideEntry> getSingleProteinAccessionComparator() {
        return new Comparator<UniquePeptideEntry>() {
            @Override
            public int compare(UniquePeptideEntry o1, UniquePeptideEntry o2) {
                return o1.getAccessionList().get(0).compareTo(o1.getAccessionList().get(0));
            }
        };
    }

    /**
     * 
     */
    public final void sortOnSingleProteinAccession() {
        Collections.sort(this.uniquePeptideEntries, getSingleProteinAccessionComparator());
    }
    
    /**
     * 
     * @return 
     */
    static Comparator<UniquePeptideEntry> sortOnPeptideSequenceComparator() {
        return new Comparator<UniquePeptideEntry>() {
            @Override
            public int compare(UniquePeptideEntry o1, UniquePeptideEntry o2) {
                return o1.getSequence().compareTo(o2.getSequence());
            }
        };
    }

    /**
     * 
     */
    public final void sortOnPeptideSequence() {
        Collections.sort(this.uniquePeptideEntries, sortOnPeptideSequenceComparator());
    }
}
