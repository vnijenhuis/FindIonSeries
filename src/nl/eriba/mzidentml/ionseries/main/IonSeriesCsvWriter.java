/*
 * @author Vikthor Nijenhuis
 * @project FindIonSeries toolkit.
 */
package nl.eriba.mzidentml.ionseries.main;

import java.io.FileWriter;
import java.io.IOException;
import nl.eriba.mzidentml.collections.MatchedIonSeriesCollection;
import nl.eriba.mzidentml.ionseries.objects.MatchedIonSeries;
import nl.eriba.mzidentml.ionseries.tools.InputTools;

/**
 * Generates a csv file with data from the MzIdentML files.
 *
 * @author vnijenhuis
 */
public class IonSeriesCsvWriter {

    /**
     * Writes the output csv file.
     *
     * @param outputDirectory directory to write the file to
     * @param matchedIonSeries collection of MatchedIonSeries objects.
     * @throws java.io.IOException could not find or access the given file.
     */
    public final void writeCsv(String outputDirectory, final MatchedIonSeriesCollection matchedIonSeries) throws IOException {
        String outputFile = outputDirectory + "ion-series.csv";
        InputTools test = new InputTools();
        FileWriter writer;
        if (test.isFile(outputFile)) {
            writer = new FileWriter(outputFile, true);
            System.out.println("Writing data to existing file: " + outputFile);
        } else {
            writer = new FileWriter(outputFile);
            System.out.println("Writing output to " + outputFile);
        }
        String delimiter = ",";
        String lineEnding = "\n";
        String header = generateCsvHeader(lineEnding, delimiter);
        writer.append(header);
        for (MatchedIonSeries ionSeries : matchedIonSeries.getMatchedIonSeriesList()) {
            String row = generateCsvRow(ionSeries, lineEnding, delimiter);
            writer.append(row);
        }
        writer.flush();
        writer.close();
        System.out.println("Finished writing to " + outputFile);
    }

    /**
     * Generates a header row for the csv file.
     *
     * @param lineEnding line ending for each csv row.
     * @param delimiter delimiter for each csv column.
     * @return header of the csv file as String.
     */
    private String generateCsvHeader(final String lineEnding, final String delimiter) {
        String header = "";
        header += "Peptide Sequence" + delimiter;
        header += "Peptide Score" + delimiter;
        header += "Complete Ion Series" + delimiter;
        header += "Ion Series Flag" + delimiter;
        header += "Protein Acessions" + lineEnding;
        return header;
    }

    /**
     * Generates a data row for the csv file.
     *
     * @param matchedIonSeries MatchedIonSeries object with mzid ion series data.
     * @param lineEnding line ending for each csv row.
     * @param delimiter delimiter for each csv column.
     * @return ScanID data row as String.
     */
    private String generateCsvRow(final MatchedIonSeries matchedIonSeries, final String lineEnding, final String delimiter) {
        String row = "";
        row += matchedIonSeries.getPeptideSequence() + delimiter;
        row += matchedIonSeries.getPeptideScore() + delimiter;
        //Each index of the ion series is added and separated by a :
        for (int i = 0; i < matchedIonSeries.getFinalIonSeries().size(); i++) {
            if (i == 0) {
                row += matchedIonSeries.getFinalIonSeries().get(i);
            } else {
                row += ":" + matchedIonSeries.getFinalIonSeries().get(i);
            }
        }
        row += delimiter;
        row += matchedIonSeries.getIonSeriesFlag() + delimiter;
        row += matchedIonSeries.getProteinAccessions() + lineEnding;
        return row;
    }
}
