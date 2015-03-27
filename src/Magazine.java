
public class Magazine {
    static int vehicles;

    int x, y;       // coordinates
    int number;
    Car [] cars = null;

    //TODO list of neighbours (only Delivery Points)

    public Magazine(int x, int y, int number){
        this.x = x;
        this.y = y;
        this.number = number;
    }

    public int getNumber(){ return number; }

    public static void setVehicles(int v){
        vehicles = v;
    }

    public Magazine setCapacities(int c){
        for(Car x: cars){
            x.setCapacity(c);
        }
        return this;
    }

    public Magazine createCars(){
        cars = new Car[vehicles];
        for(int i=0;i<vehicles;i++){
            cars[i] = new Car(this,i+1);
        }
        return this;
    }
}
