/*
 * @author Vikthor Nijenhuis
 * @project FindIonSeries toolkit.
 */
package nl.eriba.mzidentml.ionseries.objects;

/**
 * Defines the cvParam object.
 *
 * @author vnijenhuis
 */
public class MzIdCvParam {

    /**
     * Name of the object in the MzIdentML file.
     */
    private final String objectName;

    /**
     * Name of the cvParam.
     */
    private final String name;

    /**
     * Accession id of the cvParam.
     */
    private final String accession;

    /**
     * Value of the cvParam.
     */
    private final String value;

    /**
     * cvParam for the SpectrumIdentificationResult object.
     *
     * @param name name of the cvParam.
     * @param accession accession id of the cvParam. s * @param value value of the cvParam.
     * @param value value of the cvParam object.
     */
    public MzIdCvParam(final String name, final String accession, final String value) {
        this.objectName = "SpectrumIdentificationResult cvParam";
        this.name = name;
        this.accession = accession;
        this.value = value;
    }

    /**
     *
     * Returns the name of the cvParameter.
     *
     * @return the name as String.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the accession ID of the parameter.
     *
     * @return the accession as String.
     */
    public String getAccession() {
        return accession;
    }

    /**
     * Returns the value of the cvParameter.
     *
     * @return the value as String.
     */
    public String getValue() {
        return value;
    }

    /**
     * toString function of the MzIdCvParam object.
     *
     * @return object attributes as String.
     */
    @Override
    public String toString() {
        return this.objectName + "{name: " + this.getName() + ", accession: " + this.getAccession() + ", Values: "
                + this.getValue() + "}";
    }
}
