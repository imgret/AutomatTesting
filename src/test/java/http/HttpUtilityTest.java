package http;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class HttpUtilityTest {

    @Test
    public void testHttpConnectionToApi(){
        String requestUrl = "<API REQUEST URL>";
        String response = HttpUtility.makeHttpGetRequest(requestUrl);

        assertEquals("correctAnswer", "wrongAnswer");
    }

    @Test
    public void testWeatherForecastFileDownload(){
        String location = "Tallinn";
        boolean weatherForecastFileIsDownloaded = HttpUtility.downloadWeatherForecastFile(location);

        assertEquals(true, false);
    }
}
