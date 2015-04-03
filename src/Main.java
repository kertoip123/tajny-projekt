import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    private static final String DIRECTORY = "10_przykladowych_problemow_z_rozwiazaniami/";
    private static final String OUR_DIRECTORY = "ourSolutions/";
    private static final String problemChosen = "problem_8.txt";
    private static final String solutionChosen = "problem_8solution.txt";

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();

        ProblemInstance problemInstance = readInput(DIRECTORY+problemChosen);
        problemInstance.createDistanceMatrix();
        problemInstance.createSortedCostList();

        while (problemInstance.dpServed < problemInstance.deliveryPoints.length)
            problemInstance.solveThisProblem();//One DP is satisfied by the best car available
        problemInstance.moveRemainingCarsToParentMagazines();
        problemInstance.printResultsToConsole();

        long stopTime = System.currentTimeMillis();
        Long diff = stopTime - startTime;
        System.out.println(diff.doubleValue() / 1000 + "s");

        System.out.println(problemInstance.ourGoalValue);

        wirteOutput(OUR_DIRECTORY + solutionChosen, problemInstance);

        TestSolution testSolution = new TestSolution(DIRECTORY+problemChosen, DIRECTORY+solutionChosen);
        System.out.println("Provided test goal value: " + testSolution);
        TestSolution ourTestSolution = new TestSolution(DIRECTORY+problemChosen, OUR_DIRECTORY+solutionChosen);
        System.out.println("Our test goal value: " + ourTestSolution);

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

    static void wirteOutput (String fileName, ProblemInstance problem){

        try {
            FileWriter writer = new FileWriter(new File(fileName));
            for(Magazine m: problem.magazines)
                for(Car c: m.cars)
                    if(c.isInTravel()){
                        String newLine = c.parseSolution();
                        writer.write(newLine);
                    }
            writer.close();

        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }
}
