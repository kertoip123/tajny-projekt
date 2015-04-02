import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

    private static final String DIRECTORY = "10_przykladowych_problemow_z_rozwiazaniami/";

    public static void main(String[] args) {
        ProblemInstance problemInstance = readInput(DIRECTORY+"problem_3.txt");
        problemInstance.createDistanceMatrix();
        problemInstance.createSortedCostList();

        while (problemInstance.dpServed < problemInstance.deliveryPoints.length)
            problemInstance.solveThisProblem();//One DP is satisfied by the best car available
        problemInstance.moveRemainingCarsToParentMagazines();
        problemInstance.printResultsToConsole();
        System.out.println(problemInstance.ourGoalValue);


       TestSolution testSolution = new TestSolution("problem_3.txt", "problem_3_solution.txt");
        System.out.println("test goal value: " + testSolution);
    }

    static ProblemInstance readInput (String fileName){

        ProblemInstance problem = null;

        try {
            Scanner scanner = new Scanner(new File(fileName));

            int r = scanner.nextInt();
            int d = scanner.nextInt();
            int v = scanner.nextInt();
            int c = scanner.nextInt();

            Magazine.setVehicles(v);
            DeliveryPoint[] dp = new DeliveryPoint[r];
            Magazine [] m = new Magazine[d];

            int index, x, y, o;
            while(scanner.hasNextInt()){
                index = scanner.nextInt()-1;
                x = scanner.nextInt();
                y = scanner.nextInt();
                if(index<r) {
                    o = scanner.nextInt();
                    dp[index] = new DeliveryPoint(x, y, o, index);
                }
                else
                    m[index-r] = new Magazine(x, y, index);
            }

            problem = new ProblemInstance(r, d, v, c, dp, m);
        }
        catch (FileNotFoundException e) { e.printStackTrace(); }

        return problem;
    }
}
