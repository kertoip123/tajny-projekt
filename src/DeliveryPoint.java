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

        for(int i=0; i<neighbourhood.size(); i++){
            if(neighbourhood.get(i) == this.number) {
                neighbourhood.remove(i);
                break;
            }
        }

        for(int j=start; j<finish;j++)
            neighbourhood.remove(new Integer(j));

    }

    Integer getClosestNeighbour(){
        if(neighbourhood.size()>0) return neighbourhood.get(0);
        return -1;
    }

    int getOrder(){
        return order;
    }

    int getNumber(){
        return number;
    }
}
