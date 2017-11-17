package forecastData;

import forcastFileReader.ForecastFileReader;
import forecastDataProcessing.ForecastDataProcessor;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

public class ForecastData {

    private String textData;
    private JSONObject jsonData;
    private ForecastDataProcessor dataProcessor;
    private ForecastType forecastType;

    public ForecastData(String fileName, ForecastType forecastType) throws JSONException, IOException {
        this.forecastType = forecastType;

        ForecastFileReader reader = new ForecastFileReader();
        reader.openFile(fileName);
        this.textData = reader.readFromFile();
        reader.closeFile();

        this.dataProcessor = new ForecastDataProcessor(this);
        this.jsonData = dataProcessor.getJsonData();
    }

    public void setForecastType(ForecastType forecastType) {
        this.forecastType = forecastType;
    }

    public void setTextData(String textData) throws JSONException {
        this.textData = textData;
        this.jsonData = dataProcessor.getJsonData();
    }

    public void setJsonData(JSONObject jsonData) {
        this.textData = jsonData.toString();
        this.jsonData = jsonData;
    }

    public ForecastType getForecastType() {
        return forecastType;
    }

    public String getTextData() {
        return textData;
    }

    public JSONObject getJsonData() {
        return jsonData;
    }

    public String getLocationLatitude() throws JSONException {
        return dataProcessor.getWeatherForecastLocationLatitude();
    }

    public String getLocationLongitude() throws JSONException {
        return dataProcessor.getWeatherForecastLocationLongitude();
    }

    public String getLocationCountry() throws JSONException {
        return dataProcessor.getWeatherForecastLocationCountry();
    }

    public String getLocationTown() throws JSONException {
        return dataProcessor.getWeatherForecastLocationTown();
    }

    public String getForecastDate() throws JSONException {
        return dataProcessor.getCurrentForecastDate();
    }

    public List<String> getFiveDaysForecastDates() throws JSONException {
        return dataProcessor.getFiveDaysForecastDates();
    }

    public String getCurrentMaximumTemperature() throws JSONException {
        return dataProcessor.getCurrentForecastMaximumTemperature();
    }

    public List<String> getFiveDaysMaximumTemperatures() throws JSONException {
        return dataProcessor.getFiveDaysMaximumTemperatures();
    }

    public String getCurrentMinimumTemperature() throws JSONException {
        return dataProcessor.getCurrentForecastMinimumTemperature();
    }

    public List<String> getFiveDaysMinimumTemperatures() throws JSONException {
        return dataProcessor.getFiveDaysMinimumTemperatures();
    }

    public String getCurrentAverageTemperature() throws JSONException {
        return dataProcessor.getCurrentForecastAverageTemperature();
    }

    public List<String> getFiveDaysAverageTemperatures() throws JSONException {
        return dataProcessor.getFiveDaysAverageTemperatures();
    }

    public String getCurrentCloudiness() throws JSONException {
        return dataProcessor.getCurrentForecastCloudiness();
    }

    public List<String> getFiveDaysCloudiness() throws JSONException {
        return dataProcessor.getFiveDaysCloudiness();
    }

    public String getCurrentWindDirection() throws JSONException {
        return dataProcessor.getCurrentForecastWindDirection();
    }

    public List<String> getFiveDaysWindDirections() throws JSONException {
        return dataProcessor.getFiveDaysWindDirections();
    }

    public String getCurrentHumidity() throws JSONException {
        return dataProcessor.getCurrentForecastHumidity();
    }

    public List<String> getFiveDaysHumidity() throws JSONException {
        return dataProcessor.getFiveDaysHumidity();
    }

}
