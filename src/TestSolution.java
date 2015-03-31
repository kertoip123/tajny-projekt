import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

public class TestSolution {
    private String inputFileName;
    private String outputFileName;
    private ProblemInstance problemInstance;
    private int goalValue=0;

    private int numberOfCars;
    ArrayList<LinkedList<Integer>> points;

    public TestSolution(String inputFileName, String outputFileName){
        this.inputFileName = inputFileName;
        this.outputFileName = outputFileName;

        this.problemInstance = Main.readInput(this.inputFileName);

        numberOfCars = problemInstance.getNumberOfCars();

        readOutput(outputFileName);
    }

    private void readOutput(String outputFileName){
        try {
            Scanner scanner = new Scanner(new File(outputFileName));
            int begin =0 , end = 0;
            for(int i=0; i<numberOfCars; i++){

            }


        }
        catch (FileNotFoundException e) { e.printStackTrace(); }
    }


}
