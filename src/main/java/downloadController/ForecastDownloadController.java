package downloadController;

import forecastData.ForecastType;
import http.HttpUtility;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

public class ForecastDownloadController {

    private final String inputFileName = "input.txt";
    private final String outputFileName = "output.txt";
    private ArrayList<String> parameters =
            new ArrayList<>(Arrays.asList("appid=515cde85cd4f2998a633a1c50afe3dfa", "units=metric"));

    public void downloadCurrentForecastForGivenTown(String town) throws IOException {
        parameters.add("q=" + town);
        String url = HttpUtility.createApiUrlAddress(ForecastType.CURRENT_WEATHER, parameters);
        HttpUtility.createHttpUrlConnection(url);
        HttpUtility.downloadWeatherForecastFile(outputFileName);
        HttpUtility.closeHttpUrlConnection();
        parameters.remove(parameters.size() - 1);
    }

    public void downloadFiveDayForecastForGivenTown(String town) throws IOException {
        parameters.add("q=" + town);
        String url = HttpUtility.createApiUrlAddress(ForecastType.FIVE_DAY_FORECAST, parameters);
        HttpUtility.createHttpUrlConnection(url);
        HttpUtility.downloadWeatherForecastFile(outputFileName);
        HttpUtility.closeHttpUrlConnection();
        parameters.remove(parameters.size() - 1);
    }

    public String addTownToInputFileUsingConsole() throws FileNotFoundException {
        System.out.print("Add town: ");

        Scanner in = new Scanner(System.in);
        String town = in.next();
        in.close();

        FileOutputStream outputStream = new FileOutputStream(inputFileName);
        System.setOut(new PrintStream(outputStream));
        System.out.println(town);

        return town;
    }

    public void downloadCurrentForecastForTownFromInputFile() throws IOException {
        Set<String> towns = Files.lines(Paths.get(inputFileName)).collect(Collectors.toSet());
        for (String town : towns) {
            downloadCurrentForecastForGivenTown(town);
        }
    }

    public void downloadFiveDayForecastForTownFromInputFile() throws IOException {
        Set<String> towns = Files.lines(Paths.get(inputFileName)).collect(Collectors.toSet());
        for (String town : towns) {
            downloadFiveDayForecastForGivenTown(town);
        }
    }
}
