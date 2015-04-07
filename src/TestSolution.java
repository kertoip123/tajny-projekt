import javafx.util.Pair;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class TestSolution {

    private String inputFileName;
    private String outputFileName;
    private ProblemInstanceTwo problemInstance;

    private int goalValue=0;

    private int numberOfCars;

    public TestSolution(String inputFileName, String outputFileName){

        this.inputFileName = inputFileName;
        this.outputFileName = outputFileName;

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
                   // System.out.print(problemInstance.getDistance(previous, next)+ " ");
                    previous = next;
                }while(begin != previous);
              //  System.out.print("\n");
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

    static boolean verification(ProblemInstanceTwo problemInstance){
        int testGoalValue = 0;
        int totalDistance=0;
        problemInstance.createSortedCostList();
        for(Magazine m: problemInstance.magazines)
            for(Car c : m.cars) {
                totalDistance += c.distance;
                int previousPoint = -1;
                int capacity = problemInstance.initialCapacity;
                for (Integer point : c.roadMap) {
                    if(previousPoint == -1){
                        previousPoint = point;
                        continue;
                    }
                    testGoalValue += problemInstance.getDistance(previousPoint,point);
                    previousPoint = point;
                    if(point == c.parentMagazine.getNumber())  continue;
                    int cost = problemInstance.deliveryPoints[point].getOrder();
                    capacity -= cost;
                    problemInstance.costs.remove(new Pair<Integer, Integer>(point, cost));

                }
                if (capacity < 0 ) return false;
            }
        if(problemInstance.costs.size()>0 || testGoalValue != problemInstance.ourGoalValue || totalDistance != problemInstance.ourGoalValue )
            return false;
        return true;
    }
}
