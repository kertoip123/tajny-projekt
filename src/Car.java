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
        this.parentMagazine = parent;
        position = parent.getNumber();
        roadMap = new LinkedList<Integer>();
        roadMap.add(position);
    }

    public void setCapacity(int c){
        capacity = c;
    }
}
