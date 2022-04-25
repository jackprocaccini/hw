import java.io.File;
import java.io.FileWriter;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Scanner;

public class Pop {

    public static void main(String[] args) {
        String inputCsvPath;

        if(args.length != 1) {
            System.out.print("Specify a csv file: ");
            Scanner sc = new Scanner(System.in);
            inputCsvPath = sc.nextLine();
            sc.close();
        } else {
            inputCsvPath = args[0];
        }

        File inputCsv = new File(inputCsvPath);
        String outputCsvPath = "summary.csv";

        try {
            Scanner input = new Scanner(inputCsv);
            String currentLine = input.nextLine(); // First line is column titles
            int[] yearlyTotals = new int[currentLine.split(",").length - 1]; // Array to store yearly totals

            StringBuilder output = new StringBuilder();
            FileWriter fw = new FileWriter(outputCsvPath, true);
            fw.write(currentLine + "\n");

            while(input.hasNextLine()) {
                currentLine = input.nextLine();
                fw.write(currentLine + "\n");

                // Make the current line we're reading an array of strings, just to make it easier to process
                String[] currentLineAsArrayOfValues = currentLine.split(",");

                for(int i = 1; i < currentLineAsArrayOfValues.length; i++) { // want to start at pos 1 since we don't care about the state, just population for that year
                    int popAsInt = Integer.parseInt(currentLineAsArrayOfValues[i]); // convert string to int so we can add easy
                    yearlyTotals[i - 1] += popAsInt;
                }
                
            }

            input.close();

            output.append("Total,");

            for(int yearlyTotal : yearlyTotals) {
                NumberFormat scientificNotation = new DecimalFormat("#.######E0");
                System.out.println(scientificNotation.format(yearlyTotal));
                output.append(scientificNotation.format(yearlyTotal) + ","); // format the string in scientific notiation and add it to our output string
            }

            fw.write(output.toString()); // write the line with the population totals to our output file
            fw.close();

        } catch (Exception e) {
            System.out.println("Unable to calculate total population: " + e.getMessage());
        }
    }
    
}
