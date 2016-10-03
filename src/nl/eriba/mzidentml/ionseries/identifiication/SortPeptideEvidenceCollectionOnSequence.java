/*
 * @author Vikthor Nijenhuis
 * @project Peptide mzIdentML Identfication Module * 
 */
package nl.eriba.mzidentml.ionseries.identifiication;

import java.util.Comparator;
import uk.ac.ebi.jmzidml.model.mzidml.PeptideEvidence;

/**
 *
 * @author f103013
 */
public class SortPeptideEvidenceCollectionOnSequence implements Comparator<PeptideEvidence>{
    @Override
    public int compare(PeptideEvidence o1, PeptideEvidence o2) {
        return o1.getPeptideRef().compareTo(o2.getPeptideRef());
    }
}

