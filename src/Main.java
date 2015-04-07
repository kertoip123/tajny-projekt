import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    private static final String DIRECTORY = "10_przykladowych_problemow_z_rozwiazaniami/";
    private static final String OUR_DIRECTORY = "ourSolutions/";
    private static final String problemChosen = "problem_3.txt";
    private static final String solutionChosen = "problem_3_solution.txt";

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();

        ProblemInstanceTwo problemInstance = readInput(DIRECTORY+problemChosen);
        problemInstance.createDistanceMatrix();
        problemInstance.createSortedCostList();

        while (problemInstance.dpServed < problemInstance.deliveryPoints.length)
            problemInstance.solveThisProblem();//One DP is satisfied by the best car available
        problemInstance.moveRemainingCarsToParentMagazines();
        problemInstance.solveUsingShortestCycle();
      //  problemInstance.printResultsToConsole();
       // System.out.println(problemInstance.ourGoalValue);

      //  problemInstance.findBetterSolution();
      //  problemInstance.swapPoints();
        problemInstance.printResultsToConsole();
        System.out.println(problemInstance.ourGoalValue);

        long stopTime = System.currentTimeMillis();
        Long diff = stopTime - startTime;
        System.out.println(diff.doubleValue() / 1000 + "s");

        System.out.println(TestSolution.verification(problemInstance));

        //problemInstance.solveUsingShortestCycle();
        //System.out.println(problemInstance.ourGoalValue);
        //System.out.println(TestSolution.verification(problemInstance));


        writeOutput(OUR_DIRECTORY + solutionChosen, problemInstance);

        TestSolution testSolution = new TestSolution(DIRECTORY+problemChosen, DIRECTORY+solutionChosen);
        System.out.println("Provided test goal value: " + testSolution);
        TestSolution ourTestSolution = new TestSolution(DIRECTORY+problemChosen, OUR_DIRECTORY+solutionChosen);
        System.out.println("Our test goal value: " + ourTestSolution);


/*
        Car c = new Car(new Magazine(1,1,54), 1);
        int cost = 0;
       // c.roadMap.add(new Integer(54));
        c.roadMap.add(new Integer (19));
        cost += problemInstance.distance[54][19];
        c.roadMap.add(new Integer (15));
        cost += problemInstance.distance[19][15];
        c.roadMap.add(new Integer (10));
        cost += problemInstance.distance[15][10];
        c.roadMap.add(new Integer (13));
        cost += problemInstance.distance[10][13];
        c.roadMap.add(new Integer(54));
        cost += problemInstance.distance[13][54];
        System.out.println(c.roadMap.toString()+" "+cost);
        c.getShortestHamiltonianCycle(problemInstance.distance);
*/
    }

    static ProblemInstanceTwo readInput (String fileName){

        ProblemInstanceTwo problem = null;

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

            problem = new ProblemInstanceTwo(r, d, v, c, dp, m);
        }
        catch (FileNotFoundException e) { e.printStackTrace(); }

        return problem;
    }

    static void writeOutput (String fileName, ProblemInstanceTwo problem){

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
