/*
 * @author Vikthor Nijenhuis
 * @project FindIonSeries toolkit.
 */
package nl.eriba.mzidentml.ionseries.objects;

import java.util.ArrayList;
import java.util.List;

/**
 * Defines the MzIdIonFragment object.
 *
 * @author vnijenhuis
 */
public class MzIdIonFragment {

    /**
     * Name of the object in the MzIdentML file as String.
     */
    private final String objectName;

    /**
     * Charge state of the ion fragment.
     */
    private final String name;

    /**
     * List of indexes.
     */
    private final List<Integer> indices;

    /**
     * List of fragments.
     */
    private final ArrayList<Double> measureValues;

    /**
     * List of fragments.
     */
    private final ArrayList<Boolean> passIntensityThreshold;

    /**
     * Creates an MzIdIonType object with corresponding data.
     *
     * @param name
     * @param indexList list of indices.
     * @param measureValues
     * @param passedIntensityThreshold
     */
    public MzIdIonFragment(final String name, final List<Integer> indexList,
            final ArrayList<Double> measureValues, final ArrayList<Boolean> passedIntensityThreshold) {
        this.objectName = "MzIdIonFragment";
        this.name = name;
        this.indices = indexList;
        this.measureValues = measureValues;
        this.passIntensityThreshold = passedIntensityThreshold;
    }

    /**
     * Returns the charge state of this ion.
     *
     * @return charge state of the ion as Integer.
     */
    public final String getName() {
        return this.name;
    }

    /**
     * Returns the indices of each fragment of this ion.
     *
     * @return a List of Integers.
     */
    public final List<Integer> getIndexList() {
        return this.indices;
    }

    /**
     * Returns a list of ion series intensity values.
     *
     * @return an ArrayList with Double values.
     */
    public final ArrayList<Double> getMeasureValues() {
        return this.measureValues;
    }

    /**
     * Returns a list of booleans to determine if a ion intensity passed the threshold.
     *
     * @return an ArrayList with Boolean values.
     */
    public final ArrayList<Boolean> getItensityValues() {
        return this.passIntensityThreshold;
    }

    /**
     * toString function of the MzIdIonFragment object.
     *
     * @return object attributes as String.
     */
    @Override
    public String toString() {
        return this.objectName + "{Name: " + this.getName() + ", index: " + this.getIndexList() + ", Measured m/z: "
                + this.getMeasureValues() + ", Intensities: " + this.getItensityValues() + "}";
    }
}
