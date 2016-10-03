/*
 * @author Vikthor Nijenhuis
 * @project Peptide mzIdentML Identfication Module * 
 */
package nl.eriba.mzidentml.collections;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import nl.eriba.mzidentml.ionseries.objects.MatchedIonSeries;

/**
 *
 * @author f103013
 */
public class MatchedIonSeriesCollection {
    
    /**
     * Creates an ArrayList for MatchedIonSeries objects.
     */
    private final ArrayList<MatchedIonSeries> matchedIonSeriesList;

    /**
     * ArrayList of MatchedIonSeries objects.
     */
    public MatchedIonSeriesCollection() {
        matchedIonSeriesList = new ArrayList<>();
    }

    /**
     * Adds a MatchedIonSeries object to the ArrayList.
     *
     * @param matchedIonSeries MatchedIonSeries object.
     */
    public final void addMatchedIonSeries(final MatchedIonSeries matchedIonSeries) {
        matchedIonSeriesList.add(matchedIonSeries);
    }

    /**
     * Removes a MatchedIonSeries object from the ArrayList.
     *
     * @param matchedIonSeries MatchedIonSeries object.
     */
    public final void removeMatchedIonSeries(final MatchedIonSeries matchedIonSeries) {
        matchedIonSeriesList.remove(matchedIonSeries);
    }

    /**
     * Returns an ArrayList of MatchedIonSeries objects.
     *
     * @return ArrayList of MatchedIonSeries objects.
     */
    public final ArrayList<MatchedIonSeries> getMatchedIonSeriesList() {
        return matchedIonSeriesList;
    }
    
    /**
     * 
     * @return 
     */
    static Comparator<MatchedIonSeries> sortOnPeptideSequenceComparator() {
        return new Comparator<MatchedIonSeries>() {
            @Override
            public int compare(MatchedIonSeries o1, MatchedIonSeries o2) {
                return o1.getPeptideSequence().compareTo(o2.getPeptideSequence());
            }
        };
    }

    /**
     * 
     */
    public final void sortOnPeptideSequence() {
        Collections.sort(this.matchedIonSeriesList, sortOnPeptideSequenceComparator());
    }
}
