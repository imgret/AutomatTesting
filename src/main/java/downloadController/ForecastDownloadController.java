package downloadController;

import forecastData.ForecastType;
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
    // private final String outputFileName = "output.txt";
    private ArrayList<String> parameters =
            new ArrayList<>(Arrays.asList("appid=515cde85cd4f2998a633a1c50afe3dfa", "units=metric"));

    public boolean downloadCurrentForecastForGivenTown(String town) throws IOException {
        String outputFileName = town + ".txt";
        parameters.add("q=" + town);
        String url = HttpUtility.createApiUrlAddress(ForecastType.CURRENT_WEATHER, parameters);
        HttpUtility.createHttpUrlConnection(url);
        boolean isDownloaded = HttpUtility.downloadWeatherForecastFile(outputFileName);
        HttpUtility.closeHttpUrlConnection();
        parameters.remove(parameters.size() - 1);
        return isDownloaded;
    }

    public boolean downloadFiveDayForecastForGivenTown(String town) throws IOException {
        String outputFileName = town + ".txt";
        parameters.add("q=" + town);
        String url = HttpUtility.createApiUrlAddress(ForecastType.FIVE_DAY_FORECAST, parameters);
        HttpUtility.createHttpUrlConnection(url);
        boolean isDownloaded = HttpUtility.downloadWeatherForecastFile(outputFileName);
        HttpUtility.closeHttpUrlConnection();
        parameters.remove(parameters.size() - 1);
        return isDownloaded;
    }

    public boolean downloadCurrentForecastForTownFromInputFile() throws IOException {
        boolean isDownloaded = true;
        Set<String> towns = Files.lines(Paths.get(inputFileName)).collect(Collectors.toSet());
        for (String town : towns) {
            isDownloaded = downloadCurrentForecastForGivenTown(town);
            if (!isDownloaded) {
                return isDownloaded;
            }
        }
        return isDownloaded;
    }

    public boolean downloadFiveDayForecastForTownFromInputFile() throws IOException {
        boolean isDownloaded = true;
        Set<String> towns = Files.lines(Paths.get(inputFileName)).collect(Collectors.toSet());
        for (String town : towns) {
            isDownloaded = downloadFiveDayForecastForGivenTown(town);
            if (!isDownloaded) {
                return isDownloaded;
            }
        }
        return isDownloaded;
    }
}
