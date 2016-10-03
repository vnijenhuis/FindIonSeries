/*
 * @author Vikthor Nijenhuis
 * @project Peptide mzIdentML Identfication Module * 
 */
package nl.eriba.mzidentml.ionseries.objects;

import java.util.ArrayList;

/**
 *
 * @author f103013
 */
public class UniquePeptideEntry {

    private final String peptideSequence;
    private final ArrayList<String> accession;
    private final Integer endIndex;
    private final Integer startIndex;
    private final String preAminoAcid;
     private final String postAminoAcid;

    /**
     * 
     * @param sequence
     * @param accessionList 
     */
    public UniquePeptideEntry(String sequence, ArrayList<String> accessionList, String preAminoAcid, String postAminoAcid, Integer startIndex, Integer endIndex) {
        this.peptideSequence = sequence;
        this.accession = accessionList;
        this.endIndex = endIndex;
        this.startIndex = startIndex;
        this.preAminoAcid = preAminoAcid;
        this.postAminoAcid = postAminoAcid;
    }
    
    public final ArrayList<String> getAccessionList() {
        return this.accession;
    }
    
    public final Integer getAccessionCount() {
        return this.accession.size();
    }
    
    public final String getSequence() {
        return this.peptideSequence;
    }

    public Integer getStartIndex() {
        return this.startIndex;
    }

    public Integer getEndIndex() {
        return this.endIndex;
    }

    public String getPreAminoAcid() {
        return this.preAminoAcid;
    }

    public String getPostAminoAcid() {
        return this.postAminoAcid;
    }

    @Override
    public String toString() {
        return "UniquePeptide{Sequence: " + this.getSequence() + ", Pre amino acid: " + this.getPreAminoAcid() + ", Post amino acid: " + this.getPostAminoAcid() + ", Start: " + this.getStartIndex() + ", End: " + this.getEndIndex() + ", Accessions: " + this.getAccessionList() +"}";
    }
}
