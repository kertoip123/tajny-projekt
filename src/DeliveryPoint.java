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

    void setNeighbours(Integer [] neighbourArray, int start, int finish){
        neighbourhood = new LinkedList<Integer>();
        for(Integer neighbour: neighbourArray){
            neighbourhood.add(neighbour);
        }
        neighbourhood.remove(0);
        for(int j=start; j<finish;j++)
            neighbourhood.remove(new Integer(j));

    }

    Integer getClosestNeighbour(){
        return neighbourhood.get(0);
    }

    int getOrder(){
        return order;
    }

    int getNumber(){
        return number;
    }
}
