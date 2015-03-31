import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class TestSolution {

    private static final String DIRECTORY = "10_przykladowych_problemow_z_rozwiazaniami/";

    private String inputFileName;
    private String outputFileName;
    private ProblemInstance problemInstance;

    private int goalValue=0;

    private int numberOfCars;

    public TestSolution(String inputFileName, String outputFileName){

        this.inputFileName = DIRECTORY+inputFileName;
        this.outputFileName = DIRECTORY+outputFileName;

        this.problemInstance = Main.readInput(this.inputFileName);
        problemInstance.createDistanceMatrix();

        this.numberOfCars = problemInstance.getTotalCarNumber();

        readOutput();
    }

    private void readOutput(){
        try {
            Scanner scanner = new Scanner(new File(outputFileName));

            int begin =-1 , previous = -1, next=-1;

            for(int i=0; i<numberOfCars; i++){
                if(!scanner.hasNextInt())   break;
                scanner.nextInt();
                begin = scanner.nextInt()-1;
                previous = begin;

                do{
                    next = scanner.nextInt()-1;

                    goalValue += problemInstance.getDistance(previous, next);
                    previous = next;
                }while(begin != previous);
            }


        }
        catch (FileNotFoundException e) { e.printStackTrace(); }
    }

    public int getGoalValue(){
        return goalValue;
    }

    @Override
    public String toString(){
        return ((Integer)goalValue).toString();
    }

}
