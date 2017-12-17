package forecastData;

import com.google.gson.Gson;
import forecastDataParser.ForecastDataParser;
import http.HttpUtility;
import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import repositoryOperator.forecastFileWriter.ForecastFileWriter;

import java.io.IOException;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class TestForecastReportCreator {

    private ForecastDataParser dataParserMock;
    private ForecastReportCreator reportCreator;
    private ForecastDataParser currentForecastParserMock;
    private ForecastDataParser fiveDaysForecastParserMock;

    @Before
    public void starter() {
        dataParserMock = mock(ForecastDataParser.class);
        reportCreator = new ForecastReportCreator();
        currentForecastParserMock = mock(ForecastDataParser.class);
        fiveDaysForecastParserMock = mock(ForecastDataParser.class);
    }

    @Test
    public void testGettingCurrentForecastReportWithGettingAvgTemperatureFromParser() throws JSONException {
        reportCreator.createForecastCurrentReport(dataParserMock);
        verify(dataParserMock).getCurrentForecastAverageTemperature();
    }

    @Test
    public void testGettingThreeDaysForecastReportWithGettingDatesFromParser() throws JSONException {
        when(dataParserMock.getThreeDaysForecastDates()).thenReturn(Arrays.asList("", "", "", "", "", "", "", "",
                "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""));
        when(dataParserMock.getThreeDaysMinimumTemperatures()).thenReturn(Arrays.asList("", "", "", "", "", "", "", "",
                "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""));
        when(dataParserMock.getThreeDaysMaximumTemperatures()).thenReturn(Arrays.asList("", "", "", "", "", "", "", "",
                "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""));
        reportCreator.createForecastThreeDaysReport(dataParserMock);
        verify(dataParserMock).getThreeDaysForecastDates();
    }

    @Test
    public void testGettingThreeDaysForecastReportWithGettingMaxTemperaturesFromParser() throws JSONException {
        when(dataParserMock.getThreeDaysForecastDates()).thenReturn(Arrays.asList("", "", "", "", "", "", "", "",
                "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""));
        when(dataParserMock.getThreeDaysMinimumTemperatures()).thenReturn(Arrays.asList("", "", "", "", "", "", "", "",
                "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""));
        when(dataParserMock.getThreeDaysMaximumTemperatures()).thenReturn(Arrays.asList("", "", "", "", "", "", "", "",
                "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""));
        reportCreator.createForecastThreeDaysReport(dataParserMock);
        verify(dataParserMock).getThreeDaysMaximumTemperatures();
    }

    @Test
    public void testGettingThreeDaysForecastReportWithGettingMinTemperaturesFromParser() throws JSONException {
        when(dataParserMock.getThreeDaysForecastDates()).thenReturn(Arrays.asList("", "", "", "", "", "", "", "",
                "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""));
        when(dataParserMock.getThreeDaysMinimumTemperatures()).thenReturn(Arrays.asList("", "", "", "", "", "", "", "",
                "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""));
        when(dataParserMock.getThreeDaysMaximumTemperatures()).thenReturn(Arrays.asList("", "", "", "", "", "", "", "",
                "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""));
        reportCreator.createForecastThreeDaysReport(dataParserMock);
        verify(dataParserMock).getThreeDaysMinimumTemperatures();
    }

    @Test
    public void testCreatingForecastFullReportCorrectly() throws IOException, JSONException {
        when(currentForecastParserMock.getCurrentForecastAverageTemperature()).thenReturn("0");
        when(fiveDaysForecastParserMock.getThreeDaysForecastDates()).thenReturn(Arrays.asList("", "", "", "", "", "", "", "",
                "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""));
        when(fiveDaysForecastParserMock.getThreeDaysMinimumTemperatures()).thenReturn(Arrays.asList("", "", "", "", "", "", "", "",
                "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""));
        when(fiveDaysForecastParserMock.getThreeDaysMaximumTemperatures()).thenReturn(Arrays.asList("", "", "", "", "", "", "", "",
                "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""));
        when(currentForecastParserMock.getWeatherForecastGEOTypeCoordinates()).thenReturn("coordinates");
        ForecastFullReport actualFullReport = reportCreator.createForecastFullReport(
                "Town", currentForecastParserMock, fiveDaysForecastParserMock);

        ForecastFullReport expectedFullReport = new ForecastFullReport("Town", "coordinates",
                new ForecastCurrentReport("0"),
                new ForecastThreeDaysReport(new ForecastOneDayReport[]{
                        new ForecastOneDayReport(
                                Arrays.asList("", "", "", "", "", "", "", ""),
                                Arrays.asList("", "", "", "", "", "", "", ""),
                                Arrays.asList("", "", "", "", "", "", "", "")
                        ),
                        new ForecastOneDayReport(
                                Arrays.asList("", "", "", "", "", "", "", ""),
                                Arrays.asList("", "", "", "", "", "", "", ""),
                                Arrays.asList("", "", "", "", "", "", "", "")
                        ),
                        new ForecastOneDayReport(
                                Arrays.asList("", "", "", "", "", "", "", ""),
                                Arrays.asList("", "", "", "", "", "", "", ""),
                                Arrays.asList("", "", "", "", "", "", "", "")
                        )
                })
        );
        assertEquals(new Gson().toJson(expectedFullReport), new Gson().toJson(actualFullReport));
    }
}
