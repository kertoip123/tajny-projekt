import java.util.LinkedList;
import java.util.List;

public class Car {
    int capacity; //nie moze byc static !
    int index;
    int position;   //index from 1 to r+d
    int distance = 0;
    Magazine parentMagazine;
    boolean jobDone = false;
    List<Integer> roadMap;

    public Car (Magazine parent, int i){
        this.index = i;
        this.parentMagazine = parent;
        this.position = parent.getNumber();
        this.roadMap = new LinkedList<Integer>();
        this.roadMap.add(position);
    }

    public void setCapacity(int c){
        capacity = c;
    }
}
