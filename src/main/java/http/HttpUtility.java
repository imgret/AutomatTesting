package http;

import forecastData.ForecastType;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class HttpUtility {

    public final String API_URL_WITHOUT_PARAMETERS = "http://api.openweathermap.org/data/2.5/";
    // private final static String API_KEY_PARAMETER = "appid=515cde85cd4f2998a633a1c50afe3dfa";

    private HttpURLConnection httpURLConnection;

    public void setHttpURLConnection(HttpURLConnection httpURLConnection) {
        this.httpURLConnection = httpURLConnection;
    }

    public String createApiUrlAddress(ForecastType forecastType, ArrayList<String> parameters) {
        StringBuilder urlAddressBuilder = new StringBuilder(API_URL_WITHOUT_PARAMETERS);
        urlAddressBuilder.append(forecastType.getForecastType());
        urlAddressBuilder.append("?");
        for (String parameter : parameters) {
            urlAddressBuilder.append(parameter);
            urlAddressBuilder.append("&");
        }
        urlAddressBuilder.deleteCharAt(urlAddressBuilder.length() - 1);
        return urlAddressBuilder.toString();
    }

    public void createHttpUrlConnection(String urlAddress) throws IOException {
        URL url = new URL(urlAddress);
        httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.connect();
    }

    public void closeHttpUrlConnection() {
        httpURLConnection.disconnect();
    }

    public int getHttpConnectionResponseCode() throws IOException {
        return httpURLConnection.getResponseCode();
    }

    public String downloadWeatherForecastText() throws IOException {

        InputStream connectionInputStream = httpURLConnection.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connectionInputStream));

        String nextLine;
        StringBuilder forecastTextBuilder = new StringBuilder();

        while ((nextLine = bufferedReader.readLine()) != null) {
            forecastTextBuilder.append(nextLine);
            forecastTextBuilder.append("\n");
        }

        bufferedReader.close();
        connectionInputStream.close();

        return forecastTextBuilder.toString();
    }
}
