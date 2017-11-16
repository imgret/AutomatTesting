package downloadController;

import forecastData.ForecastType;
import forecastFileWriter.ForecastFileWriter;
import http.HttpUtility;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class ForecastDownloadController {

    private final String inputFileName = "input.txt";
    private ArrayList<String> parameters =
            new ArrayList<>(Arrays.asList("appid=515cde85cd4f2998a633a1c50afe3dfa", "units=metric"));
    private HttpUtility httpUtility;

    public ForecastDownloadController() {
        this.httpUtility = new HttpUtility();
    }

    public ForecastDownloadController(HttpUtility httpUtility) {
        this.httpUtility = httpUtility;
    }

    public void downloadToFileCurrentForecastForGivenTown(String town) throws IOException {
        parameters.add("q=" + town);
        String url = httpUtility.createApiUrlAddress(ForecastType.CURRENT_WEATHER, parameters);
        parameters.remove(parameters.size() - 1);

        httpUtility.createHttpUrlConnection(url);
        String forecastText = httpUtility.downloadWeatherForecastText();
        httpUtility.closeHttpUrlConnection();

        String outputFileName = town + ".txt";
        ForecastFileWriter fileWriter = new ForecastFileWriter();
        fileWriter.openFile(outputFileName);
        fileWriter.writeToFile(forecastText);
        fileWriter.closeFile();
    }

    public void downloadToFileFiveDayForecastForGivenTown(String town) throws IOException {
        parameters.add("q=" + town);
        String url = httpUtility.createApiUrlAddress(ForecastType.FIVE_DAY_FORECAST, parameters);
        parameters.remove(parameters.size() - 1);

        httpUtility.createHttpUrlConnection(url);
        String forecastText = httpUtility.downloadWeatherForecastText();
        httpUtility.closeHttpUrlConnection();

        String outputFileName = town + ".txt";
        ForecastFileWriter fileWriter = new ForecastFileWriter();
        fileWriter.openFile(outputFileName);
        fileWriter.writeToFile(forecastText);
        fileWriter.closeFile();
    }

    public void downloadToFileCurrentForecastForTownsFromInputFile() throws IOException {
        Set<String> towns = Files.lines(Paths.get(inputFileName)).collect(Collectors.toSet());
        for (String town : towns) {
            downloadToFileCurrentForecastForGivenTown(town);
        }
    }

    public void downloadToFileFiveDayForecastForTownsFromInputFile() throws IOException {
        Set<String> towns = Files.lines(Paths.get(inputFileName)).collect(Collectors.toSet());
        for (String town : towns) {
            downloadToFileFiveDayForecastForGivenTown(town);
        }
    }
}
