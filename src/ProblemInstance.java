
public class ProblemInstance {

    Magazine []  magazines;
    DeliveryPoint [] deliveryPoints;
    int [][] distance, costs;

    public ProblemInstance (int r, int d, int v, int c) {
        deliveryPoints = new DeliveryPoint[r];
        magazines = new Magazine[d];
        Car.setCapacity(c);
        Magazine.setVehicles(v);
    }

    public ProblemInstance setMagazines(Magazine [] m){
        magazines = m;
        return this;
    }

    public ProblemInstance setDeliveryPoints(DeliveryPoint[] dp){
        deliveryPoints = dp;
        return this;
    }

    public void createCostsMatrix() {
        // TODO create distance/costs matrix
    }


}
