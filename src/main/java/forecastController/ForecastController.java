package forecastController;

import forecastData.ForecastFullReport;
import forecastData.ForecastOneDayReport;
import forecastData.ForecastReportCreator;
import repositoryOperator.forecastInputFileUtility.InputFileUtility;
import org.json.JSONException;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class ForecastController {

    private ForecastReportCreator reportCreator;

    public ForecastController() {
        reportCreator = new ForecastReportCreator();
    }

    public ForecastController(ForecastReportCreator reportCreator) {
        this.reportCreator = reportCreator;
    }

    public String getForecastForTown(String town) throws IOException, JSONException {

        ForecastFullReport fullReport = reportCreator.createForecastFullReport(town);

        StringBuilder forecastStringBuilder = new StringBuilder();

        forecastStringBuilder.append("\n");
        forecastStringBuilder.append(String.format("Town: %s\n", town));
        forecastStringBuilder.append(String.format("Current average temperature: %s\n\n",
                fullReport.currentReport.averageTemperature));
        for (ForecastOneDayReport report : fullReport.threeDaysReport.oneDayReports) {
            String temperatures = getMinAndMaxTemperaturesForOneDay(report).get(0);
            forecastStringBuilder.append(temperatures);
        }
        forecastStringBuilder.append(String.format("Location GEO coordinates: %s\n", fullReport.coordinates));
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

    public List<String> getMinAndMaxTemperaturesForOneDay(ForecastOneDayReport oneDayReport) throws JSONException {
        List<String> minAndMaxTemperaturesForDays = new LinkedList<>();
        List<String> dates = oneDayReport.dates;
        List<String> minTemperatures = oneDayReport.minTemperatures;
        List<String> maxTemperatures = oneDayReport.maxTemperatures;

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
}
