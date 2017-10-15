package http;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class TestHttpUtility {

    ArrayList<String> parameters;

    @Before
    public void setup() {
        parameters = new ArrayList<>();
        parameters.add("appid=515cde85cd4f2998a633a1c50afe3dfa");
        parameters.add("units=metric");
        parameters.add("q=Tallinn,ee");
    }

    @Test
    public void testCreateApiUrlAddress() {
        String urlAddress = HttpUtility.createApiUrlAddress(ForecastType.CURRENT_WEATHER, parameters);
        System.out.println(urlAddress);

        assertEquals("http://api.openweathermap.org/data/2.5/weather?appid=515cde85cd4f2998a633a1c50afe3dfa&units=metric&q=Tallinn,ee", urlAddress);
    }

    @Test
    public void testHttpConnectionToApi() throws IOException {
        String urlAddress = HttpUtility.createApiUrlAddress(ForecastType.CURRENT_WEATHER, parameters);
        HttpUtility.createHttpUrlConnection(urlAddress);
        int responseCode = HttpUtility.getHttpConnectionResponseCode();
        HttpUtility.closeHttpUrlConnection();

        assertEquals(200, responseCode);
    }

    @Test
    public void testWeatherForecastFileDownload() throws IOException {
        String urlAddress = HttpUtility.createApiUrlAddress(ForecastType.CURRENT_WEATHER, parameters);
        HttpUtility.createHttpUrlConnection(urlAddress);
        boolean fileIsDownloaded = HttpUtility.downloadWeatherForecastFile("forecastData/test/downloadTest.json");
        HttpUtility.closeHttpUrlConnection();

        assertEquals(true, fileIsDownloaded);
    }
}
