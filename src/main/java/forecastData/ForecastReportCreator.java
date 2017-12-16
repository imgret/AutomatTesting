package forecastData;

import com.google.gson.Gson;
import http.HttpUtility;
import forecastDataParser.ForecastDataParser;
import org.json.JSONException;
import repositoryOperator.forecastFileWriter.ForecastFileWriter;

import java.io.IOException;
import java.util.List;

public class ForecastReportCreator {

    private HttpUtility httpUtility;
    private ForecastFileWriter fileWriter;

    public ForecastReportCreator() {
        this.httpUtility = new HttpUtility();
        this.fileWriter = new ForecastFileWriter();
    }

    public ForecastReportCreator(HttpUtility httpUtility, ForecastFileWriter fileWriter) {
        this.httpUtility = httpUtility;
        this.fileWriter = fileWriter;
    }

    public ForecastCurrentReport createForecastCurrentReport(ForecastDataParser dataParser) throws JSONException{
        ForecastCurrentReport currentReport = new ForecastCurrentReport(
            dataParser.getCurrentForecastAverageTemperature());
        return currentReport;
    }

    public ForecastThreeDaysReport createForecastThreeDaysReport(ForecastDataParser dataParser) throws JSONException {
        ForecastOneDayReport[] oneDayReports = new ForecastOneDayReport[3];

        List<String> dates = dataParser.getThreeDaysForecastDates();
        List<String> minTemperatures = dataParser.getThreeDaysMinimumTemperatures();
        List<String> maxTemperatures = dataParser.getThreeDaysMaximumTemperatures();

        for (int day = 0; day < 3; day++) {
            List<String> datesOfDay = dates.subList(day * 8, (day + 1) * 8);
            List<String> maxTemperaturesOfDay = maxTemperatures.subList(day * 8, (day + 1) * 8);
            List<String> minTemperaturesOfDay = minTemperatures.subList(day * 8, (day + 1) * 8);

            oneDayReports[day] = new ForecastOneDayReport(datesOfDay,
                    maxTemperaturesOfDay, minTemperaturesOfDay);
        }
        return new ForecastThreeDaysReport(oneDayReports);
    }

    public ForecastFullReport createForecastFullReport(String town) throws IOException, JSONException {
        ForecastCurrentReport currentReport;
        ForecastThreeDaysReport threeDaysReport;

        String currentForecastText = downloadCurrentForecastTextFromTown(town);
        String fiveDaysForecastText = downloadFiveDaysForecastTextFromTown(town);

        currentReport = createForecastCurrentReport(
                new ForecastDataParser(ForecastType.CURRENT_WEATHER, currentForecastText));
        threeDaysReport = createForecastThreeDaysReport(
                new ForecastDataParser(ForecastType.FIVE_DAY_FORECAST, fiveDaysForecastText));

        ForecastFullReport fullReport = new ForecastFullReport(
                town,
                new ForecastDataParser(ForecastType.CURRENT_WEATHER, currentForecastText)
                        .getWeatherForecastGEOTypeCoordinates(),
                currentReport, threeDaysReport);

        writeForecastFullReportToFile(town, fullReport);

        return fullReport;
    }

    private String downloadCurrentForecastTextFromTown(String town) throws IOException {
        String currentForecastUrl = httpUtility.createDownloadUrlUsingForecastTypeAndTown(
                ForecastType.CURRENT_WEATHER, town);

        httpUtility.createHttpUrlConnection(currentForecastUrl);
        String currentForecastText = httpUtility.downloadWeatherForecastText();
        httpUtility.closeHttpUrlConnection();

        return currentForecastText;
    }

    private String downloadFiveDaysForecastTextFromTown(String town) throws IOException {
        String fiveDaysForecastUrl = httpUtility.createDownloadUrlUsingForecastTypeAndTown(
                ForecastType.FIVE_DAY_FORECAST, town);

        httpUtility.createHttpUrlConnection(fiveDaysForecastUrl);
        String fiveDaysForecastText = httpUtility.downloadWeatherForecastText();
        httpUtility.closeHttpUrlConnection();

        return fiveDaysForecastText;
    }

    private void writeForecastFullReportToFile(String town, ForecastFullReport fullReport) throws IOException {
        fileWriter.openFile(town + ".txt");
        fileWriter.writeToFile(new Gson().toJson(fullReport));
        fileWriter.closeFile();
    }

//    public ForecastReport getForecastReport();

//    public CurrentWeatherReport getCurrent...

    // переделать довноал контроллев в довнлоад то файл контроллер и добавить ему зависимость от Файл врайтера
    // В свою очередь файл врайтер может быть в классе Repository у которого есть методы сейв и рид (допустим)
    // форекаст дата переделать в форекаст фактори и он будет выдавать неизменяемые объекты с данными по прогнозу
    // В зависимости от типа прогноза погоды
    // ForecastReport хранит список из объектов хранящих прогноз на день.
}
