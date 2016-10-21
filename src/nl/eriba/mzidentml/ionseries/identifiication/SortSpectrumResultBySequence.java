/*
 * @author Vikthor Nijenhuis
 * @project FindIonSeries toolkit.
 */
package nl.eriba.mzidentml.ionseries.identifiication;

import java.util.Comparator;
import uk.ac.ebi.jmzidml.model.mzidml.SpectrumIdentificationResult;

/**
 * Sorter for the SpectrumIdentificationResult object from the jmzidentml package.
 *
 * @author vnijenhuis
 */
public class SortSpectrumResultBySequence implements Comparator<SpectrumIdentificationResult> {

    /**
     * Compares peptide sequences from DatabaseSearchPsmEntry objects to each
     * other and sorts these objects based on the result.
     *
     * @param o1 DatabaseSearchPsmEntry object.
     * @param o2 DatabaseSearchPsmEntry object.
     * @return comparison value between both sequences.
     */
    @Override
    public int compare(SpectrumIdentificationResult o1, SpectrumIdentificationResult o2) {
        return o1.getSpectrumIdentificationItem().get(0).getPeptideRef().compareTo(o2.getSpectrumIdentificationItem().get(0).getPeptideRef());
    }
}
