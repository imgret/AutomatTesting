package forecastInputFileUtility;

import org.junit.Before;
import org.junit.Test;

import javax.print.DocFlavor;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

public class TestInputFileUtility {

    private String inputFileName = "input.txt";

    @Before
    public void starter() {

    }

    /*
    Can't test user input and is it added to input.txt file. Scanner is not correctly working in JUnit test. !
    */

    @Test
    public void testAddingOneTownToInputFileUsingConsole() throws IOException {
        String inputTown = "Helsinki";
        InputStream inputStream = new ByteArrayInputStream(inputTown.getBytes("UTF-8"));
        System.setIn(inputStream);

        InputFileUtility.addOneTownToInputFileUsingConsole();

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

        InputFileUtility.clearInputFile();

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

        InputFileUtility.addTownsToInputFileUsingConsole(3);

        System.setIn(new FileInputStream(FileDescriptor.in));

        List<String> inputFileContent = Files.lines(Paths.get(inputFileName)).collect(Collectors.toList());
        assertEquals(inputTowns, inputFileContent.subList(inputFileContent.size() - 3, inputFileContent.size()));
    }

    @Test
    public void testAddingTownToInputFile() throws IOException {
        String town = "Tallinn";
        InputFileUtility.addTownToInputFile(town);

        List<String> inputFileContent = Files.lines(Paths.get(inputFileName)).collect(Collectors.toList());
        assertEquals(town, inputFileContent.get(inputFileContent.size() - 1));
    }
}
