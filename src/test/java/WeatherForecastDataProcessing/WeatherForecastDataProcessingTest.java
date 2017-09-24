package WeatherForecastDataProcessing;

import org.junit.Test;
import weatherForecastDataProcessing.WeatherForecastDataProcessing;

import static org.junit.Assert.assertEquals;

public class WeatherForecastDataProcessingTest {

    @Test
    public void testWeatherForecastFileExtension(){
        String fileExtension = WeatherForecastDataProcessing.getWeatherForecastFileExtension();
        assertEquals("json", "cvs");
    }

    @Test
    public void testOpenWeatherForecastFile(){
        String filename = "Name of weather forecast file";
        boolean weatherForecastFileIsOpened = WeatherForecastDataProcessing.openWeatherForecastFile();
        assertEquals(true, false);
    }

    @Test
    public void testWeatherForecastLocationLatitude(){
        String latitude = WeatherForecastDataProcessing.getWeatherForecastLocationLatitude();
        assertEquals("10.009996", "4.00008");
    }

    @Test
    public void testWeatherForecastLocationLongitude(){
        String latitude = WeatherForecastDataProcessing.getWeatherForecastLocationLongitude();
        assertEquals("78.078496", "54.000761");
    }

    @Test
    public void testWeatherForecastLocationCountry(){
        String locationCountry = WeatherForecastDataProcessing.getWeatherForecastLocationCountry();
        assertEquals("Estonia", "Germany");
    }

    @Test
    public void testWeatherForecastLocationRegion(){
        String locationRegion = WeatherForecastDataProcessing.getWeatherForecastLocationRegion();
        assertEquals("Harjumaa", "Saaremaa");
    }

    @Test
    public void testWeatherForecastLocationTown(){
        String locationTown = WeatherForecastDataProcessing.getWeatherForecastLocationTown();
        assertEquals("Tallinn", "Madrid");
    }

    @Test
    public void testWeatherForecastDate(){
        String date = WeatherForecastDataProcessing.getWeatherForecastDate();
        assertEquals("03.10.2017", "00.00.0000");
    }

    @Test
    public void testWeatherForecastTimeStep(){
        String timeStep = WeatherForecastDataProcessing.getWeatherForecastTimeStep();
        assertEquals("3 hours", "1 hour");
    }

    @Test
    public void testWeatherForecastTemperatureUnits(){
        String temperatureUnits = WeatherForecastDataProcessing.getWeatherForecastTemperatureUnits();
        assertEquals("Celsius", "Fahrenheit");
    }

    @Test
    public void testWeatherForecastTemperatureUnitsConversionToCelsius(){
        WeatherForecastDataProcessing.covertToCelsius();
        String temperatureUnits = WeatherForecastDataProcessing.getWeatherForecastTemperatureUnits();
        assertEquals("Celsius", "Fahrenheit");
    }

    @Test
    public void testWeatherForecastTemperatureUnitsConversionToFahrenheit(){
        WeatherForecastDataProcessing.covertToFahrenheit();
        String temperatureUnits = WeatherForecastDataProcessing.getWeatherForecastTemperatureUnits();
        assertEquals("Fahrenheit", "Celsius");
    }

    @Test
    public void testWeatherForecastMaximumTemperature(){
        String maximumTemperature = WeatherForecastDataProcessing.getWeatherForecastMaximumTemperature();
        assertEquals("50", "-40");
    }

    @Test
    public void testWeatherForecastMinimumTemperature(){
        String minimumTemperature = WeatherForecastDataProcessing.getWeatherForecastMaximumTemperature();
        assertEquals("25", "100");
    }

    @Test
    public void testWeatherForecastNebulosity(){
        String nebulosity = WeatherForecastDataProcessing.getWeatherForecastNebulosity();
        assertEquals("Cloudy", "Empty");
    }

    @Test
    public void testWeatherForecastWindDirection(){
        String windDirection = WeatherForecastDataProcessing.getWeatherForecastWindDirection();
        assertEquals("NE", "Up");
    }

    @Test
    public void testWeatherForecastHumidity(){
        String humidity = WeatherForecastDataProcessing.getWeatherForecastHumidity();
        assertEquals("20%", "0.78987654");
    }
}
