
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
        distance = new int[deliveryPoints.length+magazines.length][deliveryPoints.length+magazines.length];

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


}
