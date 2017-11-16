package forecastFileWriter;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class TestForecastFileWriter {

    private ForecastFileWriter fileWriter;

    @Before
    public void starter() throws IOException {
        fileWriter = new ForecastFileWriter();
    }

    @Test
    public void testForecastFileIsSuccessfullyCreated() throws IOException {
        String fileName = "testFileCreation.txt";

        fileWriter.openFile(fileName);
        fileWriter.closeFile();

        File file = new File(fileName);
        assertEquals(true, file.exists());
        Files.deleteIfExists(Paths.get(fileName));
    }

    @Test
    public void testForecastFileAndDirectoriesIsSuccessfullyCreated() throws IOException {
        String fileName = "writer_test/testFileCreation.txt";

        fileWriter.openFile(fileName);
        fileWriter.closeFile();

        File file = new File(fileName);
        assertEquals(true, file.exists());
        Files.deleteIfExists(Paths.get(fileName));
        Files.deleteIfExists(Paths.get("writer_test"));
    }

    @Test
    public void testForecastFileWriterWrittenText() throws IOException {
        String fileName = "testWrittenText.txt";

        fileWriter.openFile(fileName);
        fileWriter.writeToFile("It is some test!");
        fileWriter.closeFile();

        String fileContent = Files.lines(Paths.get(fileName)).collect(Collectors.joining());
        assertEquals("It is some test!", fileContent);
        Files.deleteIfExists(Paths.get("testWrittenText.txt"));
    }

    @Test
    public void testForecastFileWriterWriteFunctionCall() throws IOException {
        FileWriter mockedWriter = mock(FileWriter.class);
        fileWriter.setFileWriter(mockedWriter);
        fileWriter.writeToFile("Testing writing with mock");

        verify(mockedWriter).write("Testing writing with mock");
    }
}
