package http;

import forecastData.ForecastType;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class HttpUtility {

    public final static String API_URL_WITHOUT_PARAMETERS = "http://api.openweathermap.org/data/2.5/";
    // private final static String API_KEY_PARAMETER = "appid=515cde85cd4f2998a633a1c50afe3dfa";

    private static HttpURLConnection httpURLConnection;

    public static String createApiUrlAddress(ForecastType forecastType, ArrayList<String> parameters) {
        StringBuilder urlAddress = new StringBuilder(API_URL_WITHOUT_PARAMETERS);
        urlAddress.append(forecastType.getForecastType());
        urlAddress.append("?");
        for (String parameter: parameters) {
            urlAddress.append(parameter);
            urlAddress.append("&");
        }
        urlAddress.deleteCharAt(urlAddress.length() - 1);
        return urlAddress.toString();
    }

    public static void createHttpUrlConnection(String urlAddress) throws IOException {
        URL url = new URL(urlAddress);
        httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.connect();
    }

    public static void closeHttpUrlConnection() {
        httpURLConnection.disconnect();
    }

    public static int getHttpConnectionResponseCode() throws IOException {
        return httpURLConnection.getResponseCode();
    }

    public static boolean downloadWeatherForecastFile(String fileName) throws IOException {
        boolean isDownloaded = false;
        String fileContent;
        StringBuilder fileContentStringBuilder = new StringBuilder("");
        InputStream connectionInputStream = httpURLConnection.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connectionInputStream));
        String nextLine;
        while ((nextLine = bufferedReader.readLine()) != null) {
            fileContentStringBuilder.append(nextLine);
            fileContentStringBuilder.append("\n");
        }
        bufferedReader.close();
        connectionInputStream.close();
        fileContent = fileContentStringBuilder.toString();
        File contentFile = new File(fileName);
//        contentFile.getParentFile().mkdirs();
        FileWriter fileWriter = new FileWriter(contentFile);
        fileWriter.append(fileContent);
        fileWriter.close();
        isDownloaded = true;
        return isDownloaded;
    }
}
