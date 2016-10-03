/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.eriba.mzidentml.ionseries.main;

import nl.eriba.mzidentml.ionseries.tools.GeneralTools;
import nl.eriba.mzidentml.ionseries.tools.InputTools;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import javax.xml.parsers.ParserConfigurationException;
import nl.eriba.mzidentml.collections.MatchedIonSeriesCollection;
import nl.eriba.mzidentml.ionseries.identifiication.IonSeriesGenerator;
import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.xml.sax.SAXException;

/**
 *
 * @author f103013
 */
public class FindIonSeries {
/**
     * Main class of the project.
     *
     * @param args commandline arguments.
     * @throws IOException could not access the given file.
     * @throws InterruptedException process was interrupted.
     * @throws ExecutionException execution failed.
     * @throws org.apache.commons.cli.ParseException
     * @throws org.xml.sax.SAXException
     * @throws javax.xml.parsers.ParserConfigurationException
     */
    public static void main(String[] args) throws IOException, InterruptedException, ExecutionException, ParseException, SAXException, ParserConfigurationException, org.apache.commons.cli.ParseException {
        FindIonSeries peptideIdentification = new FindIonSeries();
        peptideIdentification.startIonSeriesIdentification(args);
    }
    private Options commandlineOptions;
    private String separator;
    private InputTools inputTools;
    private GeneralTools generalTools;

    /**
     * Starts the identification process of mzid data.
     *
     * @param args commandline arguments.
     * @throws IOException could not find or open the file specified.
     * @throws java.text.ParseException
     * @throws InterruptedException process was interrupted by another process.
     * @throws ExecutionException could not execute the process.
     * @throws org.apache.commons.cli.ParseException exception encountered in the commandline parser.
     * @throws org.xml.sax.SAXException
     * @throws javax.xml.parsers.ParserConfigurationException
     */
    public final void startIonSeriesIdentification(final String[] args) throws IOException, ParseException, InterruptedException, ExecutionException, SAXException, ParserConfigurationException, org.apache.commons.cli.ParseException {
        //Initiates the commandline parser.
        CommandLineParser parser = new BasicParser();
        //Gather arguments from the given options of the commandline.
        CommandLine cmd = parser.parse(commandlineOptions, args);
        System.out.println("Starting mzid identification!");
        //Help function.
        if (Arrays.toString(args).toLowerCase().contains("help")) {
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("Peptide scan collector", commandlineOptions);
            System.exit(0);
        } else {
            //Allocate command line input to variables.
            String inputFile = cmd.getOptionValue("mzid");
            EntryFileReader reader = new EntryFileReader();
            String outputDirectory = cmd.getOptionValue("output");
            //Set the amount of threads to be used.
            Integer threads = getThreads(cmd);
            //Set the standard threshold value to 5% (0.05).
            Double intensityThreshold = generalTools.getIntensityThreshold(cmd);
            //Determine path separator.
            inputTools.isDirectory(outputDirectory);
            separator = getSeparator();
            //Read input file
            if (inputTools.isTxtFile(inputFile)) {
                String[] folders = inputFile.split(separator);
                String method = folders[folders.length - 2];
                ArrayList<String> entryFileList = reader.readMainTextFile(inputFile);
                LinkedHashMap<String, ArrayList<String>> mzidEntryMap = reader.createMzIdHashMap(entryFileList, separator);
                processIonSeries(outputDirectory, method, mzidEntryMap, intensityThreshold, threads);
            } else {
                System.out.println("WARNING: given file is not a .txt file: " + inputFile);
            }
        }
    }
    
    /**
     * Returns the amount of threads used for multithreading.
     *
     * @param cmd commandline arguments.
     * @return amount of threads as Integer.
     */
    private Integer getThreads(CommandLine cmd) {
        Integer threads = 1;
        if (cmd.hasOption("threads")) {
            try {
                threads = Integer.parseInt(cmd.getOptionValue("threads"));
            } catch (Exception e) {
                System.out.println("Please enter a number as input instead of " + cmd.getOptionValue("threads")
                        + ".\nCurrent input results in error: " + e.getMessage());
            }
        }
        return threads;
    }

    /**
     *
     * @return
     */
    private String getSeparator() {
        String os = System.getProperties().getProperty("os.name").toLowerCase();
        if (os.contains("windows")) {
            separator = "\\\\"; //windows
        } else if (os.contains("linux") || os.contains("unix")) {
            separator = "/"; //linux
        }
        return separator;
    }

    private String generateOutputDirectory(final String file, final String outputDirectory) {
        String[] split = file.split(separator);
        String fileName = split[split.length - 1];
        fileName = fileName.substring(0, fileName.indexOf(".mzid"));
        String folder = split[split.length - 2];
        //Generate directory if it does not exist.
        String directory = outputDirectory + folder + separator + fileName + separator;
        Path path = Paths.get(directory);
        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                System.out.println("WARNING: could not create a directory for the given mzid file: " + file + "\nError: " + e);
                System.out.println("Creating path: " + path + " failed. Process will terminate.");
                System.exit(0);
            }
        }
        return directory;
    }

    private void processIonSeries(String outputDirectory, String method, LinkedHashMap<String, ArrayList<String>> mzidEntryMap, Double itensityThreshold, Integer threads) throws InterruptedException, ExecutionException, IOException {
        System.out.println("Starting identification of PeptideShaker mzid data...");
        Integer sampleSize = 0;
        for (Map.Entry<String, ArrayList<String>> x : mzidEntryMap.entrySet()) {
            if (sampleSize <= x.getValue().size()) {
                sampleSize = x.getValue().size();
            }
        }
        for (Map.Entry<String, ArrayList<String>> mzidList: mzidEntryMap.entrySet()) {
            for (String mzidFile: mzidList.getValue()) {
                String directory = generateOutputDirectory(mzidFile, outputDirectory);
                IonSeriesGenerator generator = new IonSeriesGenerator(null, null, null, itensityThreshold);
                MatchedIonSeriesCollection generateIonSeries = generator.generateIonSeries(mzidFile, itensityThreshold, threads);
                IonSeriesCsvWriter writer = new IonSeriesCsvWriter();
                writer.writeCsv(directory, generateIonSeries);
            }
        }
    }
}
