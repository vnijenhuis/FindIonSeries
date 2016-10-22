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
    private final String peptideScore;

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
     * List of indices covered by immonium-ions.
     */
    private final ArrayList<Integer> immoniumIndexList;

    /**
     * Defines the MatchedIonSeries object.
     *
     * @param peptideSequence peptide amino acid sequence.
     * @param peptideScore peptide score.
     * @param accessions protein accessions.
     * @param immoniumIndexList list of immonium-ion indices.
     * @param bIonIndexList list of B-ion indices.
     * @param yIonIndexList list of Y-ion indices.
     * @param indexList list of ion indices.
     * @param ionSeriesFlag contains the ion series flag.
     */
    public MatchedIonSeries(final String peptideSequence, final String peptideScore, final String accessions, final ArrayList<Integer> immoniumIndexList, final ArrayList<Integer> bIonIndexList, final ArrayList<Integer> yIonIndexList,
            final ArrayList<Integer> indexList, final Integer ionSeriesFlag) {
        this.peptideSequence = peptideSequence;
        this.peptideScore = peptideScore;
        this.accessions = accessions;
        this.immoniumIndexList = immoniumIndexList;
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
        return this.indexList;
    }

    /**
     * Returns a list of immonium ion series indices.
     *
     * @return ArrayList of b ion series indices as Integers.
     */
    public ArrayList<Integer> getIonSeriesImmoniumIon() {
        return this.immoniumIndexList;
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
     * @return peptide score as String.
     */
    public String getPeptideScore() {
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
        return "MatchedIonSeries{Peptide Sequence: " + this.getPeptideSequence() + "Peptide Score: " + this.getPeptideScore() + "Protein Accessions: " + this.getProteinAccessions()
                + ",Immonium ion series indices: " + this.getIonSeriesImmoniumIon() + ", B-ion series indices: " + this.getIonSeriesBIon() + ", y-ion series indices: " + this.getIonSeriesYIon() + ", Ion series indices: " + this.getIonSeriesIndexList() + ", Ion series flag: " + this.getIonSeriesFlag() + "}";
    }
}
