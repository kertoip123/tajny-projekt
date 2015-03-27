
public class ProblemInstance {

    Magazine []  magazines;
    DeliveryPoint [] deliveryPoints;
    int [][] distance;
    int []costs;

    public ProblemInstance (int r, int d, int v, int c, DeliveryPoint [] dp, Magazine [] m) {
        deliveryPoints = new DeliveryPoint[r];
        magazines = new Magazine[d];
        Magazine.setVehicles(v);
        magazines = m;
        deliveryPoints = dp;
        for(Magazine x: magazines)
            x.setCapacities(c);
    }



    public void createCostsMatrix() {
        // TODO create distance/costs matrix
    }

    public void printResultsToConsole(){
        for(int i=0; i<magazines.length; i++){
            for(int j=0; j<Magazine.vehicles;j++){
                System.out.println(magazines[i].cars[j].index + " " +magazines[i].cars[j].roadMap.toString());
            }
        }
    }


}
