package forecastController;

import downloadController.ForecastDownloadController;
import forecastData.ForecastData;
import forecastData.ForecastType;
import forecastInputFileUtility.InputFileUtility;
import org.json.JSONException;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class ForecastController {

    private InputFileUtility inputFileUtility;
    private ForecastDownloadController downloadController;

    public ForecastController() {
        inputFileUtility = new InputFileUtility();
        downloadController = new ForecastDownloadController();
    }

    public String getForecastForTown(String town) throws IOException, JSONException {
        ForecastData forecastData;
        String currentAverageTemperature;
        List<String> daysMinAndMaxTemperatures;
        String geoCoordinates;
        StringBuilder forecastStringBuilder = new StringBuilder();

        inputFileUtility.openInputFile();
        inputFileUtility.clearInputFile();
        inputFileUtility.addTownToInputFile(town);
        inputFileUtility.closeInputFile();

        downloadController.downloadToFileCurrentForecastForGivenTown(town);
        forecastData = new ForecastData(town + ".txt", ForecastType.CURRENT_WEATHER);
        currentAverageTemperature = forecastData.getCurrentAverageTemperature();

        downloadController.downloadToFileFiveDayForecastForGivenTown(town);
        forecastData = new ForecastData(town + ".txt", ForecastType.FIVE_DAY_FORECAST);
        daysMinAndMaxTemperatures = getMinAndMaxTemperaturesForEachDayOfForecast(forecastData);

        geoCoordinates = getGEOTypeCoordinates(forecastData);

        forecastStringBuilder.append("\n");
        forecastStringBuilder.append(String.format("Town: %s\n", town));
        forecastStringBuilder.append(String.format("Current average temperature: %s\n\n", currentAverageTemperature));
        for (String temperatures : daysMinAndMaxTemperatures) {
            forecastStringBuilder.append(temperatures);
        }
        forecastStringBuilder.append(String.format("Location GEO coordinates: %s\n", geoCoordinates));
        forecastStringBuilder.append("--------------------------------");

        return forecastStringBuilder.toString();
    }

    public List<String> getForecastsForTowns(List<String> towns) throws IOException, JSONException {
        List<String> townsForecasts = new LinkedList<>();

        for (String town : towns) {
            townsForecasts.add(getForecastForTown(town));
        }

        return townsForecasts;
    }

    public List<String> getMinAndMaxTemperaturesForEachDayOfForecast(ForecastData forecastData) throws JSONException {
        List<String> minAndMaxTemperaturesForDays = new LinkedList<>();
        List<String> dates = forecastData.getFiveDaysForecastDates();
        List<String> minTemperatures = forecastData.getFiveDaysMinimumTemperatures();
        List<String> maxTemperatures = forecastData.getFiveDaysMaximumTemperatures();

        String dateWithoutTime = "";
        Double minTemperature = Double.MAX_VALUE;;
        Double maxTemperature = Double.MIN_VALUE;
        for (int i = 0; i < minTemperatures.size(); i++) {
            if (Double.valueOf(minTemperatures.get(i)) < minTemperature) {
                minTemperature = Double.valueOf(minTemperatures.get(i));
            }
            if (Double.valueOf(maxTemperatures.get(i)) > maxTemperature) {
                maxTemperature = Double.valueOf(maxTemperatures.get(i));
            }
            if (i % 8 ==  7) {
                dateWithoutTime = dates.get(i).split(" ")[1];
                minAndMaxTemperaturesForDays.add(
                        String.format("Minimum %s temperature: %.2f\nMaximum %s temperature: %.2f\n\n",
                        dateWithoutTime, minTemperature, dateWithoutTime, maxTemperature));
                minTemperature = Double.MAX_VALUE;
                maxTemperature = Double.MIN_VALUE;
            }
        }
        return minAndMaxTemperaturesForDays;
    }

    public String getGEOTypeCoordinates(ForecastData forecastData) throws JSONException {
        String latitude = forecastData.getLocationLatitude();
        String longitude = forecastData.getLocationLongitude();

        return String.format("%s:%s", latitude, longitude);
    }
}
