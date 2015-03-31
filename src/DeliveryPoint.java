import java.util.LinkedList;
import java.util.List;

public class DeliveryPoint {
    int x, y;
    int order = 0;
    int number;
    boolean served = false;
    List<Integer> neighbourhood;
    //TODO list of neighbours

    public DeliveryPoint (int x, int y, int order , int number){
        this.x = x;
        this.y = y;
        this.order = order;
        this.number = number;
    }

    void setNeighbours(Integer [] neighbourArray){
        neighbourhood = new LinkedList<Integer>();
        for(Integer neighbour: neighbourArray){
            neighbourhood.add(++neighbour);
        }
        neighbourhood.remove(0);

    }

    int getOrder(){
        return order;
    }
}
