package repositoryOperator.forecastInputFileUtility;

import java.io.*;
import java.util.Scanner;

public class InputFileUtility {

    private String inputFileName = "input.txt";
    private PrintWriter appendPrintWriter;
    private PrintWriter rewritePrintWriter;

    public InputFileUtility() {
    }

    public void setAppendPrintWriter(PrintWriter appendPrintWriter) {
        this.appendPrintWriter = appendPrintWriter;
    }

    public void setRewritePrintWriter(PrintWriter rewritePrintWriter) {
        this.rewritePrintWriter = rewritePrintWriter;
    }

    public void openInputFile() throws IOException {
        appendPrintWriter = new PrintWriter(new FileWriter(inputFileName, true));
        rewritePrintWriter = new PrintWriter(new FileWriter(inputFileName, false));
    }

    public void closeInputFile() {
        appendPrintWriter.close();
        rewritePrintWriter.close();
    }

    public void addOneTownToInputFileUsingConsole() throws IOException {
        System.out.print("Add town to input file: ");

        Scanner in = new Scanner(System.in);
        String town = in.nextLine();

        addTownToInputFile(town);
    }

    public void clearInputFile() throws FileNotFoundException {
        rewritePrintWriter.print("");
    }

    public void addTownsToInputFileUsingConsole(int numberOfTowns) throws IOException {
        for (int i = 0; i < numberOfTowns; i++) {
            addOneTownToInputFileUsingConsole();
        }
    }

    public void addTownToInputFile(String town) throws IOException {
        appendPrintWriter.println(town);
    }
}
