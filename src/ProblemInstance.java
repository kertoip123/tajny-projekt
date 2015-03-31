import java.util.Arrays;
import java.util.Comparator;

public class ProblemInstance {

    Magazine []  magazines;
    DeliveryPoint [] deliveryPoints;
    int [][] distance;
    int []costs;

    public ProblemInstance (int r, int d, int v, int c, DeliveryPoint [] dp, Magazine [] m) {
        deliveryPoints = new DeliveryPoint[r];
       // magazines = new Magazine[d];

        Magazine.setVehicles(v);
        magazines = m;
        deliveryPoints = dp;
        for(Magazine x: magazines)
            x.createCars().setCapacities(c);
    }


    public void createCostsMatrix() {
        // TODO create distance/costs matrix
    }

    public void createDistanceMatrix(){
        int total = deliveryPoints.length+magazines.length;
        distance = new int[total][total];
        int i;
        for(i=0;i<deliveryPoints.length;i++){
            int j;
            for(j=0;j<deliveryPoints.length;j++){
                distance[i][j]=pitagoras(deliveryPoints[i].x,deliveryPoints[i].y,deliveryPoints[j].x,deliveryPoints[j].y);
            }
            for(;j<total;j++){
                distance[i][j]=pitagoras(deliveryPoints[i].x,deliveryPoints[i].y,magazines[j-deliveryPoints.length].x,magazines[j-deliveryPoints.length].y);
            }
            // TODO Limit list of neighbourhood to DP
            Integer [] tempArray =  intArrayToIntegerArray(distance[i]);
            Integer [] indexes = sortNeighbourhood(tempArray);
            deliveryPoints[i].setNeighbours(indexes);

        }
        for(;i<total;i++){
            for(int j=0;j<deliveryPoints.length;j++) {
                distance[i][j] = pitagoras(magazines[i-deliveryPoints.length].x, magazines[i-deliveryPoints.length].y, deliveryPoints[j].x, deliveryPoints[j].y);
            }
            Integer [] tempArray =  intArrayToIntegerArray(distance[i]);
            Integer [] indexes = sortNeighbourhood(tempArray);
            magazines[(i-deliveryPoints.length)].setNeighbours(indexes, deliveryPoints.length, total);
        }
    }

    int getDistance(int i, int j){
        if(distance[i][j]==0)
            System.out.println("Operation not permitted !!");
        return distance[i][j];
    }

    int pitagoras(int x1, int y1, int x2, int y2){
        int temp = (x1-x2)*(x1-x2)+(y1-y2)*(y1-y2);
        double result = (double) temp;
        double root = Math.sqrt(result);
        int finalResult = (int) Math.round(root);
        return finalResult;
    }

    public void printResultsToConsole(){
        for(int i=0; i<magazines.length; i++){
            for(int j=0; j<Magazine.vehicles;j++){
                System.out.println(magazines[i].cars[j].index + " " +magazines[i].cars[j].roadMap.toString());
            }
        }
    }

    Integer[] sortNeighbourhood(final Integer permutation[]){
        int size = permutation.length;
        final Integer[] indexes = new Integer[size];
        final Integer[] finalIndexes = new Integer[size];
        for(int i=0; i < size ; i++)
            indexes[i] = i;
        Arrays.sort(indexes, new Comparator<Integer>() {
            @Override
            public int compare(final Integer o1, final Integer o2) {
                return Integer.compare(permutation[o1], permutation[o2]);
            }
        });
        return indexes;
    }

    Integer [] intArrayToIntegerArray(int []array){
        Integer [] tempArray =  new Integer[array.length];
        int k=0;
        for (int value : array) {
            tempArray[k++] = Integer.valueOf(value);
        }
        return tempArray;
    }

    int getTotalCarNumber(){
        return magazines.length*Magazine.vehicles;
    }

}
