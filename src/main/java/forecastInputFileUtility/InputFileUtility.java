package forecastInputFileUtility;

import java.io.*;
import java.util.Scanner;

public class InputFileUtility {

    private static String inputFileName = "input.txt";

    public static void addOneTownToInputFileUsingConsole() throws IOException {
        System.out.print("Add town to input file: ");

        Scanner in = new Scanner(System.in);
        String town = in.next();


        FileOutputStream outputStream = new FileOutputStream(inputFileName, true);
        System.setOut(new PrintStream(outputStream));
        System.out.println(town);
        outputStream.close();

        System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
    }

    public static void clearInputFile() throws FileNotFoundException {
        PrintWriter inputFileWriter = new PrintWriter(inputFileName);
        inputFileWriter.print("");
        inputFileWriter.close();
    }

    public static void addTownsToInputFileUsingConsole(int numberOfTowns) throws IOException {
        for (int i = 0; i < numberOfTowns; i++) {
            addOneTownToInputFileUsingConsole();
        }
    }

    public static void addTownToInputFile(String town) throws IOException {
        FileWriter fileWriter = new FileWriter(inputFileName, true);
        PrintWriter inputFileWriter = new PrintWriter(fileWriter);
        inputFileWriter.println(town);
        inputFileWriter.close();
    }
}
