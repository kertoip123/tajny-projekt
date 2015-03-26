public class Car {
    static int capacity;

    int position;   //index from 1 to r+d
    int distance = 0;
    Magazine parentMagazine;
    boolean jobDone = false;
    // TODO list of visited places

    public Car (Magazine parent){
        this.parentMagazine = parent;
        position = parent.getNumber();
    }

    public static void setCapacity(int c){
        capacity = c;
    }
}
