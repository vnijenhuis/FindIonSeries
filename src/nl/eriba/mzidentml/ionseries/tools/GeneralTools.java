/*
 * @author Vikthor Nijenhuis
 * @project Peptide mzIdentML Identfication Module * 
 */
package nl.eriba.mzidentml.ionseries.tools;

import org.apache.commons.cli.CommandLine;

/**
 *
 * @author f103013
 */
public class GeneralTools {
    
    /**
     * Returns the amount of threads used for multithreading.
     *
     * @param cmd commandline arguments.
     * @return amount of threads as Integer.
     */
    public final Double getIntensityThreshold(CommandLine cmd) {
        double threshold = 0.05;
        double divide = 100.0;
        if (cmd.hasOption("intensity")) {
            String thresholdValue = cmd.getOptionValue("intensity").replace("\\W", "");
            if (thresholdValue.isEmpty()) {
                throw new IllegalArgumentException("Please enter a number as input instead of providing no input.\nExamples: 95.0, 95%, 0.95");
            } else if (thresholdValue.matches(".*([a-zA-Z]).*")) {
                throw new IllegalArgumentException("Please enter a number as input instead of " + thresholdValue + "\nExamples: 95.0, 95%, 0.95");
            } else if (thresholdValue.matches("0\\.?(\\d?)+")) {
                threshold = Double.parseDouble(thresholdValue);
            } else if (thresholdValue.matches("([1-9]+)\\.?(\\d?)+")) {
                threshold = (Double.parseDouble(thresholdValue) / divide);
            }
        } else {
            System.out.println("Threshold for intensity set to 0.05 (5%)");
            return threshold;
        }
        System.out.println("Threshold for intensity set to " + threshold + "!");
        return threshold;
    }
}
