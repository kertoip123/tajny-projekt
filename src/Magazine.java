
public class Magazine {
    static int vehicles;

    int x, y;       // coordinates
    int number;
    Car [] cars;

    //TODO list of neighbours (only Delivery Points)

    public Magazine(int x, int y, int number){
        this.x = x;
        this.y = y;
        this.number = number;

        cars = new Car[vehicles];
        for(int i=0;i<vehicles;i++){
            cars[i] = new Car(this,i+1);
        }

    }

    public int getNumber(){ return number; }

    public static void setVehicles(int v){
        vehicles = v;
    }

    public void setCapacities(int c){
        for(Car x: cars){
            x.setCapacity(c);
        }
    }
}
