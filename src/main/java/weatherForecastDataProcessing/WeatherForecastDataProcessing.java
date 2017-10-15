package weatherForecastDataProcessing;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

public class WeatherForecastDataProcessing {

    private String fileName;
    private JSONObject jsonFileContent;

    public WeatherForecastDataProcessing(String fileName) throws IOException, JSONException {
        this.fileName = fileName;
        this.jsonFileContent = new JSONObject(getWeatherForecastFileContent());
    }

    public void setFileName(String fileName) throws IOException, JSONException {
        this.fileName = fileName;
        this.jsonFileContent = new JSONObject(getWeatherForecastFileContent());
    }

    public String getFileName() {
        return fileName;
    }

    public void setJsonFileContent(JSONObject fileContent) {
        this.jsonFileContent = fileContent;
    }

    public JSONObject getJsonFileContent() {
        return jsonFileContent;
    }

    public String getWeatherForecastFileExtension() {
        String fileExtension = "null";
        int i = fileName.lastIndexOf('.');
        if (i > 0) {
            fileExtension = fileName.substring(i+1);
        }
        return fileExtension;
    }

    public String getWeatherForecastFileContent() throws IOException {
        String fileContent;
        StringBuilder fileContentStringBuilder = new StringBuilder("");
        File forecastFile = new File(fileName);
        FileReader fileReader = new FileReader(forecastFile);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String nextLine;
        while ((nextLine = bufferedReader.readLine()) != null) {
            fileContentStringBuilder.append(nextLine);
            fileContentStringBuilder.append("\n");
        }
        fileContentStringBuilder.deleteCharAt(fileContentStringBuilder.length() - 1);
        bufferedReader.close();
        fileReader.close();
        fileContent = fileContentStringBuilder.toString();
        return fileContent;
    }

    public String getWeatherForecastLocationLatitude() {
        return "";
    }

    public String getWeatherForecastLocationLongitude() {
        return "";
    }

    public String getWeatherForecastLocationCountry() {
        return "";
    }

    public String getWeatherForecastLocationRegion() {
        return "";
    }

    public String getWeatherForecastLocationTown() {
        return "";
    }

    public String getWeatherForecastDate() {
        return "";
    }

    public String getWeatherForecastTimeStep() {
        return "";
    }

    public String getWeatherForecastTemperatureUnits() {
        return "";
    }

    public String getWeatherForecastMaximumTemperature() throws JSONException {
        String maxTemperature = "null";
        JSONObject mainFromJson;
         mainFromJson = (JSONObject) jsonFileContent.get("main");
         maxTemperature = mainFromJson.getString("temp_max");
        return maxTemperature;
    }

    public String getWeatherForecastMinimumTemperature() throws JSONException {
        String minTemperature = "null";
        JSONObject mainFromJson;
        mainFromJson = (JSONObject) jsonFileContent.get("main");
        minTemperature = mainFromJson.getString("temp_min");
        return minTemperature;
    }

    public LinkedList<String> getFiveDaysTemperature() throws JSONException {
        String temperature = "null";
        JSONArray listOfForecastsForFiveDays;
        LinkedList<String> temperaturesForFiveDays = new LinkedList<>();
        listOfForecastsForFiveDays = (JSONArray) jsonFileContent.get("list");
        for (int i = 0; listOfForecastsForFiveDays.length() > i; i++) {
            temperaturesForFiveDays.add(listOfForecastsForFiveDays.getJSONObject(i).getJSONObject("main").getString("temp"));
        }
        return temperaturesForFiveDays;
    }

    public String getWeatherForecastNebulosity() {
        return "";
    }

    public String getWeatherForecastWindDirection() {
        return "";
    }

    public String getWeatherForecastHumidity() {
        return "";
    }
}
