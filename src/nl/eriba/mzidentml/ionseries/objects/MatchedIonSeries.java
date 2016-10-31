/*
 * @author Vikthor Nijenhuis
 * @project FindIonSeries toolkit.
 */
package nl.eriba.mzidentml.ionseries.objects;

import java.util.ArrayList;

/**
 * Defines the data of a given ion series.
 *
 * @author vnijenhuis
 */
public class MatchedIonSeries {

    /**
     * List of ion indices of an ion series.
     */
    private final ArrayList<Integer> indexList;

    /**
     * Flag for the ion series.
     */
    private final Integer ionSeriesFlag;

    /**
     * Peptide amino acid sequence.
     */
    private final String peptideSequence;

    /**
     * Peptide score.
     */
    private final Double peptideScore;

    /**
     * Protein accesions of this peptide sequence.
     */
    private final String accessions;

    /**
     * List of indices covered by B-ions.
     */
    private final ArrayList<Integer> bIonIndexList;

    /**
     * List of indices covered by Y-ions.
     */
    private final ArrayList<Integer> yIonIndexList;

    /**
     * List of indices covered by immonium-ions and non y and b ions.
     */
    private final ArrayList<Integer> combinedIonIndices;

    /**
     * List of indices covered by immonium-ions and non y and b ions.
     */
    private final ArrayList<Integer> combinedAllIonIndices;

    /**
     * Defines the MatchedIonSeries object.
     *
     * @param peptideSequence peptide amino acid sequence.
     * @param peptideScore peptide score.
     * @param accessions protein accessions.
     * @param combinedIonIndices list of combined non y and b ions.
     * @param bIonIndexList list of B-ion indices.
     * @param yIonIndexList list of Y-ion indices.
     * @param combinedAllIonIndices list of all combined ion indices.
     * @param indexList list of ion indices.
     * @param ionSeriesFlag contains the ion series flag.
     */
    public MatchedIonSeries(final String peptideSequence, final Double peptideScore, final String accessions, final ArrayList<Integer> combinedIonIndices, final ArrayList<Integer> bIonIndexList, final ArrayList<Integer> yIonIndexList,
            final ArrayList<Integer> combinedAllIonIndices, final ArrayList<Integer> indexList, final Integer ionSeriesFlag) {
        this.peptideSequence = peptideSequence;
        this.peptideScore = peptideScore;
        this.accessions = accessions;
        this.combinedIonIndices = combinedIonIndices;
        this.bIonIndexList = bIonIndexList;
        this.yIonIndexList = yIonIndexList;
        this.combinedAllIonIndices = combinedAllIonIndices;
        this.indexList = indexList;
        this.ionSeriesFlag = ionSeriesFlag;
    }

    /**
     * Returns a list of ion series indices.
     *
     * @return ArrayList of the best completeion series indices as Integers.
     */
    public ArrayList<Integer> getFinalIonSeries() {
        return this.indexList;
    }

    /**
     * Returns a list of non y and b ion series indices.
     *
     * @return ArrayList of non y and b ion series indices as Integers.
     */
    public ArrayList<Integer> getCombinedIonSeries() {
        return this.combinedIonIndices;
    }
 
    /**
     * Returns a list of all combined ion series indices.
     *
     * @return ArrayList of all ion series indices as Integers.
     */
    public ArrayList<Integer> getAllCombinedIonSeries() {
        return this.combinedAllIonIndices;
    }

    /**
     * Returns a list of b ion series indices.
     *
     * @return ArrayList of b ion series indices as Integers.
     */
    public ArrayList<Integer> getIonSeriesBIon() {
        return this.bIonIndexList;
    }

    /*
     * Returns a list of y ion series indices.
     *
     * @return ArrayList of y ion series indices as Integers.
     */
    public ArrayList<Integer> getIonSeriesYIon() {
        return this.yIonIndexList;
    }

    /**
     * Returns the peptide score.
     *
     * @return peptide score as Double.
     */
    public Double getPeptideScore() {
        return this.peptideScore;
    }

    /**
     * Returns the peptide amino acid sequence.
     *
     * @return peptide amino acid sequence as String.
     */
    public String getPeptideSequence() {
        return this.peptideSequence;
    }

    /**
     * Returns the ion series flag.
     *
     * @return ion series flag as Integer.
     */
    public Integer getIonSeriesFlag() {
        return this.ionSeriesFlag;
    }

    /**
     * Returns the ion series flag.
     *
     * @return ion series flag as Integer.
     */
    public String getProteinAccessions() {
        return this.accessions;
    }

    /**
     * ToString function that displays the data present in this object.
     *
     * @return object data as String.
     */
    @Override
    public String toString() {
        return "MatchedIonSeries{Peptide Sequence: " + this.getPeptideSequence() + "Peptide Score: " + this.getPeptideScore() + "Protein Accessions: " + this.getProteinAccessions() + ",Combined non y and b ion indices: " + this.getCombinedIonSeries()
                + ", B-ion series indices: " + this.getIonSeriesBIon() + ", y-ion series indices: " + this.getIonSeriesYIon() + ", Ion series indices: " + this.getFinalIonSeries() + ", Ion series flag: " + this.getIonSeriesFlag() + "}";
    }
}
