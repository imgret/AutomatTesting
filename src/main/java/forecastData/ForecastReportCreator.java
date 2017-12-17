package forecastData;

import com.google.gson.Gson;
import http.HttpUtility;
import forecastDataParser.ForecastDataParser;
import org.json.JSONException;
import repositoryOperator.forcastFileReader.ForecastFileReader;
import repositoryOperator.forecastFileWriter.ForecastFileWriter;

import java.io.IOException;
import java.util.List;

public class ForecastReportCreator {

    public ForecastReportCreator() {
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

    public ForecastFullReport createForecastFullReport(String town, ForecastDataParser currentForecastParser,
                                                       ForecastDataParser fiveDaysForecastParser) throws IOException, JSONException {
        ForecastCurrentReport currentReport;
        ForecastThreeDaysReport threeDaysReport;

        currentReport = createForecastCurrentReport(currentForecastParser);
        threeDaysReport = createForecastThreeDaysReport(fiveDaysForecastParser);

        ForecastFullReport fullReport = new ForecastFullReport(
                town,
                currentForecastParser.getWeatherForecastGEOTypeCoordinates(),
                currentReport, threeDaysReport);

        return fullReport;
    }

//    public ForecastReport getForecastReport();

//    public CurrentWeatherReport getCurrent...

    // переделать довноал контроллев в довнлоад то файл контроллер и добавить ему зависимость от Файл врайтера
    // В свою очередь файл врайтер может быть в классе Repository у которого есть методы сейв и рид (допустим)
    // форекаст дата переделать в форекаст фактори и он будет выдавать неизменяемые объекты с данными по прогнозу
    // В зависимости от типа прогноза погоды
    // ForecastReport хранит список из объектов хранящих прогноз на день.
}
