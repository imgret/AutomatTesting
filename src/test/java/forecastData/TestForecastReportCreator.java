package forecastData;

import forecastDataParser.ForecastDataParser;
import http.HttpUtility;
import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import repositoryOperator.forecastFileWriter.ForecastFileWriter;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class TestForecastReportCreator {

    private ForecastDataParser dataParserMock;
    private ForecastReportCreator reportCreator;
    private HttpUtility httpUtilityMock;
    private ForecastFileWriter fileWriterMock;

    @Before
    public void starter() {
        dataParserMock = mock(ForecastDataParser.class);
        httpUtilityMock = mock(HttpUtility.class);
        fileWriterMock = mock(ForecastFileWriter.class);
        reportCreator = new ForecastReportCreator(httpUtilityMock, fileWriterMock);
    }

    @Test
    public void testGettingCurrentForecastReportWithGettingAvgTemperatureFromParser() throws JSONException {
        reportCreator.createForecastCurrentReport(dataParserMock);
        verify(dataParserMock).getCurrentForecastAverageTemperature();
    }

    @Test
    public void testGettingThreeDaysForecastReportWithGettingDatesFromParser() throws JSONException {
        reportCreator.createForecastThreeDaysReport(dataParserMock);
        verify(dataParserMock).getThreeDaysForecastDates();
    }

    @Test
    public void testGettingThreeDaysForecastReportWithGettingMaxTemperaturesFromParser() throws JSONException {
        reportCreator.createForecastThreeDaysReport(dataParserMock);
        verify(dataParserMock).getThreeDaysMaximumTemperatures();
    }

    @Test
    public void testGettingThreeDaysForecastReportWithGettingMinTemperaturesFromParser() throws JSONException {
        reportCreator.createForecastThreeDaysReport(dataParserMock);
        verify(dataParserMock).getThreeDaysMinimumTemperatures();
    }

    @Test
    public void testGettingForecastFullReportUsingHttpUtilityDownloadTextTwoTimes() throws IOException, JSONException {
        reportCreator.createForecastFullReport("Town");
        verify(httpUtilityMock, times(2)).downloadWeatherForecastText();
    }

    @Test
    public void testGettingForecastFullReportUsingFileWriter() throws IOException, JSONException {
        reportCreator.createForecastFullReport("Town");
        verify(fileWriterMock).writeToFile(anyString());
    }
}
