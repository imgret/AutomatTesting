package repositoryOperator.forcastFileReader;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class TestForecastFileReader {

    private ForecastFileReader fileReader;

    @Before
    public void starter() {
        fileReader = new ForecastFileReader();
    }

    @Test
    public void testReadingTextDataFromFile() throws IOException {
        fileReader.openFile("currentForecastDataTest.json");
        String actualTextData = fileReader.readFromFile();
        fileReader.closeFile();
        String expectedTextData = "{\"main\":{\"pressure\":987,\"humidity\":93,\"temp\":5,\"temp_min\":5,\"temp_max\":5}}";
        assertEquals(expectedTextData, actualTextData);
    }

    @Test
    public void testCallingReadFunction() throws IOException {
        Scanner mockedScanner = mock(Scanner.class);
        fileReader.setScanner(mockedScanner);
        fileReader.readFromFile();

        verify(mockedScanner).hasNextLine();
    }

}
