package forecastData;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class TestForecastData {

    private ForecastData currentForecastData;
    private ForecastData fiveDaysForecastData;

    @Before
    public void starter() throws IOException, JSONException {
        currentForecastData = new ForecastData("currentForecastDataTest.json", ForecastType.CURRENT_WEATHER);
        fiveDaysForecastData = new ForecastData("fiveDaysForecastDataTest.json", ForecastType.FIVE_DAY_FORECAST);
    }

    @Test
    public void testTextDataLoadingFromCurrentForecastFile() {
        String actualText = currentForecastData.getTextData();
        String expectedText = "{\"main\":{\"pressure\":987,\"humidity\":93,\"temp\":5,\"temp_min\":5,\"temp_max\":5}}";
        assertEquals(expectedText, actualText);
    }

    @Test
    public void testTextDataLoadingFromFiveDaysForecastFile() {
        String actualText = fiveDaysForecastData.getTextData();
        String expectedText = "{\"list\":[{\"main\":{\"temp\":7.44,\"temp_min\":7.44,\"temp_max\":7.69,\"humidity\":96}}," +
                "{\"main\":{\"temp\":6.78,\"temp_min\":6.78,\"temp_max\":6.97,\"humidity\":96}}]}";
        assertEquals(expectedText, actualText);
    }

    @Test
    public void testTextDataSetterConvertedDataToJson() throws JSONException {
        currentForecastData.setTextData("{\"main\":{\"temp\":6.78,\"temp_min\":6.78,\"temp_max\":6.97,\"humidity\":96}}");
        JSONObject actualJson = currentForecastData.getJsonData();
        JSONObject expectedJson = new JSONObject("{\"main\":{\"temp\":6.78,\"temp_min\":6.78,\"temp_max\":6.97,\"humidity\":96}}");
        assertEquals(expectedJson.toString(), actualJson.toString());
    }

    /*

    Can't test, because JSONObject.toString() gives elements from json string in different order. !

    @Test
    public void testJsonDataSetterConvertedDataToText() throws JSONException {
        JSONObject json = new JSONObject("{\"main\":{\"pressure\":987,\"humidity\":93,\"temp\":5,\"temp_min\":5,\"temp_max\":5}}");
        fiveDaysForecastData.setJsonData(json);
        String actualText = json.toString();
        String expectedText = "{\"main\":{\"pressure\":987,\"humidity\":93,\"temp\":5,\"temp_min\":5,\"temp_max\":5}}";
        assertEquals(expectedText, actualText);
    }
    */

}
