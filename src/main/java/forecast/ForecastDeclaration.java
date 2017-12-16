package forecast;

import forecastController.ForecastController;
import org.json.JSONException;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class ForecastDeclaration {

    public static void main(String... args) throws IOException, JSONException {
        ForecastController forecastController = new ForecastController();
        List<String> forecasts = forecastController.getForecastsForTowns(Arrays.asList("Moscow", "London"));
        forecasts.forEach(System.out::println);

        // System.out.println(new HttpUtility().createDownloadUrlUsingForecastTypeAndTown(ForecastType.FIVE_DAY_FORECAST, "Tallinn"));
    }
}
