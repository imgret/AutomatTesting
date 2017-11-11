package dataLoader;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class TestDataLoader {

    private ForecastDataLoader dataLoader;

    @Before
    public void starter() {
        dataLoader = new ForecastDataLoader();
    }

    @Test
    public void testGettingTextDataFromFile() throws IOException {
        String actualTextData = dataLoader.getTextDataFromFile("currentForecastDataTest.json");
        String expectedTextData = "{\"main\":{\"pressure\":987,\"humidity\":93,\"temp\":5,\"temp_min\":5,\"temp_max\":5}}";
        assertEquals(expectedTextData, actualTextData);
    }

}
