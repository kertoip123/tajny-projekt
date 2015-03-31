import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        ProblemInstance problemInstance = readInput("test.txt");
        problemInstance.createDistanceMatrix();
        problemInstance.printResultsToConsole();

        TestSolution testSolution = new TestSolution("problem_10.txt", "problem_10_solution.txt");
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
                    dp[index] = new DeliveryPoint(x, y, o, index+1);
                }
                else
                    m[index-r] = new Magazine(x, y, index+1);
            }

            problem = new ProblemInstance(r, d, v, c, dp, m);
        }
        catch (FileNotFoundException e) { e.printStackTrace(); }

        return problem;
    }
}
