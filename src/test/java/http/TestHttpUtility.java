package http;

import forecastData.ForecastType;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestHttpUtility {

    private HttpUtility httpUtility;
    private ArrayList<String> parameters;
    private String apiUrlAddress;

    @Before
    public void starter() {
        httpUtility = new HttpUtility();

        parameters = new ArrayList<>();
        parameters.add("appid=515cde85cd4f2998a633a1c50afe3dfa");
        parameters.add("units=metric");
        parameters.add("q=Tallinn,ee");

        apiUrlAddress = "http://api.openweathermap.org/data/2.5/weather?appid=515cde85cd4f2998a633a1c50afe3dfa&units=metric&q=Tallinn,ee";
    }

    @Test
    public void testCreateApiUrlAddress() {
        String urlAddress = httpUtility.createApiUrlAddress(ForecastType.CURRENT_WEATHER, parameters);

        assertEquals(apiUrlAddress, urlAddress);
    }

    @Test
    public void testSuccessfulHttpConnectionToApi() throws IOException {
        httpUtility.createHttpUrlConnection(apiUrlAddress);
        int responseCode = httpUtility.getHttpConnectionResponseCode();
        httpUtility.closeHttpUrlConnection();

        assertEquals(200, responseCode);
    }

    @Test
    public void testWeatherForecastTextDownloadUsingMock() throws IOException {
        String expectedText = "Forecast text download test\nand mock\n";
        InputStream inputStream = new ByteArrayInputStream((expectedText).getBytes("UTF-8"));
        HttpURLConnection httpURLConnectionMock = mock(HttpURLConnection.class);
        when(httpURLConnectionMock.getInputStream()).thenReturn(inputStream);

        httpUtility.setHttpURLConnection(httpURLConnectionMock);
        String actualText = httpUtility.downloadWeatherForecastText();

        assertEquals(expectedText, actualText);
    }

    @Test
    public void testWeatherForecastTextDownloadUsingInternetIsNotEmptyString() throws IOException {
        httpUtility.createHttpUrlConnection(apiUrlAddress);
        String forecastText = httpUtility.downloadWeatherForecastText();
        httpUtility.closeHttpUrlConnection();

        assertNotEquals("", forecastText);
    }
}
