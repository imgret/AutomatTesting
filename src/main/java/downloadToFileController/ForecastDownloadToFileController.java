package downloadToFileController;

import com.google.gson.Gson;
import forecastData.ForecastFullReport;
import forecastData.ForecastReportCreator;
import forecastData.ForecastType;
import forecastDataParser.ForecastDataParser;
import org.json.JSONException;
import repositoryOperator.forcastFileReader.ForecastFileReader;
import repositoryOperator.forecastFileWriter.ForecastFileWriter;
import http.HttpUtility;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class ForecastDownloadToFileController {

    private final String inputFileName = "input.txt";
    private HttpUtility httpUtility;
    private ForecastFileWriter fileWriter;
    private ForecastFileReader fileReader;
    private ForecastReportCreator reportCreator;

    public ForecastDownloadToFileController() {
        this.httpUtility = new HttpUtility();
        this.fileWriter = new ForecastFileWriter();
        this.fileReader = new ForecastFileReader();
        this.reportCreator = new ForecastReportCreator();
    }

    public ForecastDownloadToFileController(HttpUtility httpUtility, ForecastFileWriter fileWriter,
                                            ForecastFileReader fileReader, ForecastReportCreator reportCreator) {
        this.httpUtility = httpUtility;
        this.fileWriter = fileWriter;
        this.fileReader = fileReader;
        this.reportCreator = reportCreator;
    }

    public void setHttpUtility(HttpUtility httpUtility) {
        this.httpUtility = httpUtility;
    }

    public void setFileWriter(ForecastFileWriter fileWriter) {
        this.fileWriter = fileWriter;
    }

    public void setFileReader(ForecastFileReader fileReader) {
        this.fileReader = fileReader;
    }

    public String downloadForecastFromUrl(String url) throws IOException {
        httpUtility.createHttpUrlConnection(url);
        String forecastText = httpUtility.downloadWeatherForecastText();
        httpUtility.closeHttpUrlConnection();
        return forecastText;
    }

    public String getFullReportJsonFromForecastFullText(String town, String downloadedCurrentForecastText,
                                                        String downloadedFiveDaysForecastText) throws IOException, JSONException {
        Gson gson = new Gson();
        ForecastFullReport fullReport = reportCreator.createForecastFullReport(
                town,
                new ForecastDataParser(ForecastType.CURRENT_WEATHER, downloadedCurrentForecastText),
                new ForecastDataParser(ForecastType.FIVE_DAY_FORECAST, downloadedFiveDaysForecastText));
        return gson.toJson(fullReport);
    }

    public void writeForecastToFile(String outputFileName, String forecastText) throws IOException {
        fileWriter.openFile(outputFileName);
        fileWriter.writeToFile(forecastText);
        fileWriter.closeFile();
    }

    public Set<String> readTownsForForecastFromInputFile() throws IOException {
        fileReader.openFile(inputFileName);
        String inputFileContent = fileReader.readFromFile();
        fileReader.closeFile();
        Set<String> towns = new HashSet<>(Arrays.asList(inputFileContent.split("\n")));
        return towns;
    }

    public void downloadToFileCurrentForecastForGivenTown(String town) throws IOException {
        String url = httpUtility.createDownloadUrlUsingForecastTypeAndTown(ForecastType.CURRENT_WEATHER, town);
        String forecastText = downloadForecastFromUrl(url);
        String outputFileName = town + ".txt";
        writeForecastToFile(outputFileName, forecastText);
    }

    public void downloadToFileFiveDayForecastForGivenTown(String town) throws IOException {
        String url = httpUtility.createDownloadUrlUsingForecastTypeAndTown(ForecastType.FIVE_DAY_FORECAST, town);
        String forecastText = downloadForecastFromUrl(url);
        String outputFileName = town + ".txt";
        writeForecastToFile(outputFileName, forecastText);
    }

    public void downloadToFileFullForecastReportForGivenTown(String town) throws IOException, JSONException {
        String currentForecastUrl = httpUtility.createDownloadUrlUsingForecastTypeAndTown(ForecastType.CURRENT_WEATHER, town);
        String fiveDaysForecastUrl = httpUtility.createDownloadUrlUsingForecastTypeAndTown(ForecastType.FIVE_DAY_FORECAST, town);
        String currentForecastText = downloadForecastFromUrl(currentForecastUrl);
        String fiveDaysForecastText = downloadForecastFromUrl(fiveDaysForecastUrl);
        String fullForecastText = getFullReportJsonFromForecastFullText(town, currentForecastText, fiveDaysForecastText);
        String outputFileName = town + ".txt";
        writeForecastToFile(outputFileName, fullForecastText);
    }

    public void downloadToFileCurrentForecastForTownsFromInputFile() throws IOException {
        Set<String> towns = readTownsForForecastFromInputFile();
        for (String town : towns) {
            downloadToFileCurrentForecastForGivenTown(town);
        }
    }

    public void downloadToFileFiveDayForecastForTownsFromInputFile() throws IOException {
        Set<String> towns = readTownsForForecastFromInputFile();
        for (String town : towns) {
            downloadToFileFiveDayForecastForGivenTown(town);
        }
    }

    public void downloadToFileFullForecastForTownsFromInputFile() throws IOException, JSONException {
        Set<String> towns = readTownsForForecastFromInputFile();
        for (String town : towns) {
            downloadToFileFullForecastReportForGivenTown(town);
        }
    }
}
