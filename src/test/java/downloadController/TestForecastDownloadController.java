package downloadController;

import dataLoader.ForecastDataLoader;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

public class TestForecastDownloadController {

    private ForecastDownloadController downloadController;

    @Before
    public void starter() {
        downloadController = new ForecastDownloadController();
    }

    /*
    Can't find method to test data download to file. !!!

    Can't test user input and is it added to input.txt file. Scanner is not correctly working in JUnit test. !

    @Test
    public void testAddingTownToInputFileUsingConsole() throws IOException {
        String inputContent = downloadController.addTownToInputFileUsingConsole();
        String inputFileContent = Files.lines(Paths.get("input.txt")).collect(Collectors.joining());
        assertEquals(inputContent, inputFileContent);
    }
    */
}
