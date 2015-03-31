import java.util.LinkedList;

public class DeliveryPoint {
    int x, y;
    int order = 0;
    int number;
    boolean served = false;
    //TODO list of neighbours

    public DeliveryPoint (int x, int y, int order , int number){
        this.x = x;
        this.y = y;
        this.order = order;
        this.number = number;
    }
}
