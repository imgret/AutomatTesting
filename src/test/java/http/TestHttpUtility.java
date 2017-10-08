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
    }

    @Test
    public void testCreateApiUrlAddress() {
        String urlAddress = HttpUtility.createApiUrlAddress(ForecastType.CURRENT_WEATHER, parameters);
        assertEquals("http://api.openweathermap.org/data/2.5/weather?appid=515cde85cd4f2998a633a1c50afe3dfa", urlAddress);
    }

    @Test
    public void testHttpConnectionToApi() throws IOException {
        String urlAddress = HttpUtility.createApiUrlAddress(ForecastType.CURRENT_WEATHER, parameters);
        HttpUtility.createHttpUrlConnection(urlAddress);
        HttpUtility.closeHttpUrlConnection();
    }

    @Test
    public void testWeatherForecastFileDownload(){
        String location = "Tallinn";
        boolean weatherForecastFileIsDownloaded = HttpUtility.downloadWeatherForecastFile(location);

        assertEquals(true, false);
    }
}
