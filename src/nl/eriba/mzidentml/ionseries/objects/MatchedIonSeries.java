/*
 * @author Vikthor Nijenhuis
 * @project Peptide mzIdentML Identfication Module * 
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
    private final String peptideSequence;
    private final String peptideScore;
    private final String accessions;
    private final ArrayList<Integer> bIonIndexList;
    private final ArrayList<Integer> yIonIndexList;

    /**
     * Defines the MatchedIonSeries object.
     *
     * @param peptideSequence
     * @param peptideScore
     * @param accessions
     * @param indexList contains a list of index numbers.
     * @param ionSeriesFlag contains the ion series flag.
     */
    public MatchedIonSeries(final String peptideSequence, final String peptideScore, final String accessions, final ArrayList<Integer> bIonIndexList, final ArrayList<Integer> yIonIndexList,
            final ArrayList<Integer> indexList, final Integer ionSeriesFlag) {
        this.peptideSequence = peptideSequence;
        this.peptideScore = peptideScore;
        this.accessions = accessions;
        this.bIonIndexList = bIonIndexList;
        this.yIonIndexList = yIonIndexList;
        this.indexList = indexList;
        this.ionSeriesFlag = ionSeriesFlag;
    }

    /**
     * Returns a list of ion series indices.
     *
     * @return ArrayList of ion series indices as Integers.
     */
    public ArrayList<Integer> getIonSeriesIndexList() {
        return  this.indexList;
    }

    /**
     * Returns a list of b ion series indices.
     *
     * @return ArrayList of b ion series indices as Integers.
     */
    public ArrayList<Integer> getIonSeriesBion() {
        return  this.bIonIndexList;
    }
    /*
     * Returns a list of y ion series indices.
     *
     * @return ArrayList of y ion series indices as Integers.
     */
    public ArrayList<Integer> getIonSeriesYion() {
        return  this.yIonIndexList;
    }

    /**
     * Returns the peptide score.
     *
     * @return peptide score as String.
     */
    public String getPeptideScore() {
        return  this.peptideScore;
    }

    /**
     * Returns the peptide amino acid sequence.
     *
     * @return peptide amino acid sequence as String.
     */
    public String getPeptideSequence() {
        return  this.peptideSequence;
    }

    /**
     * Returns the ion series flag.
     *
     * @return ion series flag as Integer.
     */
    public Integer getIonSeriesFlag() {
        return  this.ionSeriesFlag;
    }
    
    /**
     * Returns the ion series flag.
     *
     * @return ion series flag as Integer.
     */
    public String getProteinAccessions() {
        return this.accessions;
    }  

    @Override
    public String toString() {
        return "MatchedIonSeries{Peptide Sequence: " + this.getPeptideSequence() + "Peptide Score: " + this.getPeptideScore()+ "Protein Accessions: " + this.getProteinAccessions()
                + ", B-ion series indices: " + this.getIonSeriesBion()+ ", y-ion series indices: " + this.getIonSeriesYion()+ ", Ion series indices: " + this.getIonSeriesIndexList() + ", Ion series flag: " + this.getIonSeriesFlag() + "}";
    }
}
