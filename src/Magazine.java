
public class Magazine {
    static int vehicles;

    int x, y;       // coordinates
    int number;
    Car [] cars;

    //TODO list of neighbours

    public Magazine(int x, int y, int number){
        this.x = x;
        this.y = y;
        this.number = number;

        cars = new Car[vehicles];
        for(Car c: cars)
            c = new Car(this);
    }

    public int getNumber(){ return number; }

    public static void setVehicles(int v){
        vehicles = v;
    }
}
