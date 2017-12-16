package forecastController;

import forecastData.*;
import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

public class TestForecastController {

    private ForecastController forecastController;

    @Before
    public void starter() {
        forecastController = new ForecastController();
    }

    @Test
    public void testGettingCorrectMinimalAndMaximalTemperaturesForEachDayOfGivenForecast() throws JSONException, IOException {
        ForecastFullReport fullReportMock = new ForecastFullReport(
                "Tallinn", "10:-10",
                new ForecastCurrentReport("-9"),
                new ForecastThreeDaysReport(new ForecastOneDayReport[]{
                        new ForecastOneDayReport(
                                Arrays.asList(
                                "00:00:00 31.12.2017", "03:00:00 31.12.2017", "06:00:00 31.12.2017", "09:00:00 31.12.2017",
                                "12:00:00 31.12.2017", "15:00:00 31.12.2017", "18:00:00 31.12.2017", "21:00:00 31.12.2017"),
                                Arrays.asList("3", "2", "1", "0", "0.1567", "-0.01", "-3.8999999", "-2"),
                                Arrays.asList("3", "2", "1", "0", "0.1567", "-0.01", "-3.8999999", "-2")),
                        new ForecastOneDayReport(
                                Arrays.asList(
                                "00:00:00 32.12.2017", "03:00:00 32.12.2017", "06:00:00 32.12.2017", "09:00:00 32.12.2017",
                                "12:00:00 32.12.2017", "15:00:00 32.12.2017", "18:00:00 32.12.2017", "21:00:00 32.12.2017"),
                                Arrays.asList("-0.0", "-0.1", "2.6", "5.89", "6", "4.5", "2.2222", "-1.09"),
                                Arrays.asList("-0.0", "-0.1", "2.6", "5.89", "5", "4.5", "2.2222", "-1.09"))
                })
        );
        List<String> actualMinAndMaxTemperatures = new LinkedList<>();
        actualMinAndMaxTemperatures.add(forecastController.getMinAndMaxTemperaturesForOneDay(
                fullReportMock.threeDaysReport.oneDayReports[0]).get(0));
        actualMinAndMaxTemperatures.add(forecastController.getMinAndMaxTemperaturesForOneDay(
                fullReportMock.threeDaysReport.oneDayReports[1]).get(0));
        List<String> expectedMinAndMaxTemperatures = Arrays.asList(
                "Minimum 31.12.2017 temperature: -3,90\nMaximum 31.12.2017 temperature: 3,00\n\n",
                "Minimum 32.12.2017 temperature: -1,09\nMaximum 32.12.2017 temperature: 6,00\n\n");
        assertEquals(expectedMinAndMaxTemperatures, actualMinAndMaxTemperatures);
    }

    @Test
    public void testGettingMaxAndMinTemperaturesAndDateForOneDayReport() throws JSONException {
        ForecastOneDayReport oneDayReport = new ForecastOneDayReport(
                Arrays.asList(
                        "00:00:00 31.12.2017", "03:00:00 31.12.2017", "06:00:00 31.12.2017", "09:00:00 31.12.2017",
                        "12:00:00 31.12.2017", "15:00:00 31.12.2017", "18:00:00 31.12.2017", "21:00:00 31.12.2017"),
                Arrays.asList("3", "2", "1", "0", "0.1567", "-0.01", "-3.8999999", "-2"),
                Arrays.asList("3", "2", "1", "0", "0.1567", "-0.01", "-3.8999999", "-2")
        );
        String actualMinAndMaxTemperaturesAndDate = forecastController.getMinAndMaxTemperaturesForOneDay(oneDayReport).get(0);
        String expectedMinAndMaxTemperaturesAndDate = "Minimum 31.12.2017 temperature: -3,90\n" +
                "Maximum 31.12.2017 temperature: 3,00\n\n";
        assertEquals(expectedMinAndMaxTemperaturesAndDate, actualMinAndMaxTemperaturesAndDate);
    }
}
