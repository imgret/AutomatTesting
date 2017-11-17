package forecastInputFileUtility;

import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class TestInputFileUtility {

    private String inputFileName = "input.txt";
    private InputFileUtility inputFileUtility;

    @Before
    public void starter() throws IOException {
        inputFileUtility = new InputFileUtility();
    }

    @Test
    public void testAddingOneTownToInputFileUsingConsole() throws IOException {
        String inputTown = "Helsinki";
        InputStream inputStream = new ByteArrayInputStream(inputTown.getBytes("UTF-8"));
        System.setIn(inputStream);

        inputFileUtility.openInputFile();
        inputFileUtility.addOneTownToInputFileUsingConsole();
        inputFileUtility.closeInputFile();

        System.setIn(new FileInputStream(FileDescriptor.in));

        List<String> inputFileContent = Files.lines(Paths.get(inputFileName)).collect(Collectors.toList());
        assertEquals(inputTown, inputFileContent.get(inputFileContent.size() - 1));
    }

    @Test
    public void testClearingInputFile() throws IOException {
        FileOutputStream outputStream = new FileOutputStream(inputFileName, true);
        System.setOut(new PrintStream(outputStream));
        System.out.println("Here is new line.");
        outputStream.close();
        System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));

        inputFileUtility.openInputFile();
        inputFileUtility.clearInputFile();
        inputFileUtility.closeInputFile();

        String inputFileContent = Files.lines(Paths.get(inputFileName)).collect(Collectors.joining());
        assertEquals("", inputFileContent);
    }

    @Test
    public void testAddingThreeTownsToInputFileUsingConsole() throws IOException {
        List<String> inputTowns = new LinkedList<>(Arrays.asList("Helsinki", "Tallinn", "Moscow"));
        InputStream inputStream =
                new SequenceInputStream(
                        new SequenceInputStream(
                                new ByteArrayInputStream((inputTowns.get(0) + "\n").getBytes("UTF-8")),
                                new ByteArrayInputStream((inputTowns.get(1) + "\n").getBytes("UTF-8"))),
                        new ByteArrayInputStream((inputTowns.get(2) + "\n").getBytes("UTF-8"))
                );
        System.setIn(inputStream);

        inputFileUtility.openInputFile();
        inputFileUtility.addTownsToInputFileUsingConsole(3);
        inputFileUtility.closeInputFile();

        System.setIn(new FileInputStream(FileDescriptor.in));

        List<String> inputFileContent = Files.lines(Paths.get(inputFileName)).collect(Collectors.toList());
        assertEquals(inputTowns, inputFileContent.subList(inputFileContent.size() - 3, inputFileContent.size()));
    }

    @Test
    public void testAddingTownToInputFile() throws IOException {
        String town = "Tallinn";

        inputFileUtility.openInputFile();
        inputFileUtility.addTownToInputFile(town);
        inputFileUtility.closeInputFile();

        List<String> inputFileContent = Files.lines(Paths.get(inputFileName)).collect(Collectors.toList());
        assertEquals(town, inputFileContent.get(inputFileContent.size() - 1));
    }

    @Test
    public void testAddingTwoTownsToInputFile() throws IOException {
        inputFileUtility.openInputFile();
        inputFileUtility.addTownToInputFile("Tallinn");
        inputFileUtility.addTownToInputFile("Riga");
        inputFileUtility.closeInputFile();

        List<String> inputFileContent = Files.lines(Paths.get(inputFileName)).collect(Collectors.toList());
        assertEquals("Tallinn", inputFileContent.get(inputFileContent.size() - 2));
        assertEquals("Riga", inputFileContent.get(inputFileContent.size() - 1));
    }

    @Test
    public void testCallingPrintlnFunctionInPrintWriterInAddTownFunction() throws IOException {
        String town = "Testing mocked PrintWriter";
        PrintWriter mockedWriter = mock(PrintWriter.class);
        inputFileUtility.setAppendPrintWriter(mockedWriter);
        inputFileUtility.addTownToInputFile(town);
        verify(mockedWriter).println(town);
    }

    @Test
    public void testClearingInputFileUsingPrintInPrintWriter() throws FileNotFoundException {
        PrintWriter mockedWriter = mock(PrintWriter.class);
        inputFileUtility.setRewritePrintWriter(mockedWriter);
        inputFileUtility.clearInputFile();
        verify(mockedWriter).print("");
    }
}
