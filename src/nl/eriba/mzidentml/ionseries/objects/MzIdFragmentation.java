/*
 * @author Vikthor Nijenhuis
 * @project Peptide mzIdentML Identfication Module * 
 */
package nl.eriba.mzidentml.ionseries.objects;

import java.util.ArrayList;

/**
 * Defines the Fragmentation object from the MzIdentML file.
 *
 * @author vnijenhuis
 */
public class MzIdFragmentation {

    /**
     * Name of the object in the MzIdentML file.
     */
    private final String objectName;

    /**
     * List of IonType objects.
     */
    private final ArrayList<MzIdIonFragment> ionTypeList;

    /**
     * List of cvParam objects.
     */
    private final ArrayList<MzIdCvParam> cvParamList;

    /**
     * Fragmentation of the SpectrumIdentificationItem object.
     *
     * @param ionTypeList list of IonType objects.
     * @param cvParamList list of cvParam objects.
     */
    public MzIdFragmentation(ArrayList<MzIdIonFragment> ionTypeList, ArrayList<MzIdCvParam> cvParamList) {
        this.objectName = "Fragmentation";
        this.ionTypeList = ionTypeList;
        this.cvParamList = cvParamList;
    }

    /**
     * Returns a list of ion types.
     *
     * @return list of MzIdIonFragment objects.
     */
    public final ArrayList<MzIdIonFragment> getIonList() {
        return ionTypeList;
    }

    /**
     * Returns a list of cv parameters.
     *
     * @return list of MzIdCvParam objects.
     */
    public final ArrayList<MzIdCvParam> getCvParameterList() {
        return this.cvParamList;
    }

    /**
     * toString function of the MzIdCvParam object.
     *
     * @return object attributes as String.
     */
    @Override
    public String toString() {
        return this.objectName + "{IonTypeList: " + this.getIonList() + ", cvParamList: " + this.getCvParameterList() + "}";
    }
}
