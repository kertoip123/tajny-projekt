/**
 * Created by KAMIL on 2015-04-07.
 */
import javafx.util.Pair;
import java.util.*;

public class ProblemInstanceTwo {

    Magazine []  magazines;
    DeliveryPoint [] deliveryPoints;
    int [][] distance;
    List<Pair<Integer, Integer>> costs;
    final int NO_COMPARISON_AVAILABLE = -1;
    int ourGoalValue=0;
    //int hamiltonianGoalValue = 0;
    int dpServed = 0;

    //Temp
    int theBestCarIndex = NO_COMPARISON_AVAILABLE;
    int theBestPlaceToGo = NO_COMPARISON_AVAILABLE;
    int theBestParentMagazine = NO_COMPARISON_AVAILABLE;
    int initialCapacity;
    //
    List<Integer> elementsToRemove = new LinkedList<Integer>();
    final int WAGA = 2;
    final int WAGADWA = 3;

    //
    long startTime;

    public ProblemInstanceTwo (int r, int d, int v, int c, DeliveryPoint [] dp, Magazine [] m) {
        deliveryPoints = new DeliveryPoint[r];
        // magazines = new Magazine[d];
        Magazine.setVehicles(v);
        magazines = m;
        deliveryPoints = dp;
        initialCapacity = c;
        for(Magazine x: magazines)
            x.createCars().setCapacities(initialCapacity);
    }

    public void setStartTime(long time){
        startTime=time;
    }

    public void createSortedCostList(){
        costs = new LinkedList<Pair<Integer, Integer>>();
        for(DeliveryPoint dp: deliveryPoints){
            costs.add(new Pair(Integer.valueOf(dp.number),Integer.valueOf(dp.order)));
        }
        Collections.sort(costs, new Comparator<Pair<Integer, Integer>>() {
            @Override
            public int compare(Pair<Integer, Integer> o1, Pair<Integer, Integer> o2) {
                return Integer.compare(o1.getValue(), o2.getValue());
            }

        });
    }


    public void createDistanceMatrix(){
        int total = deliveryPoints.length+magazines.length;
        distance = new int[total][total];
        int i;
        for(i=0;i<deliveryPoints.length;i++){
            int j;
            for(j=0;j<deliveryPoints.length;j++){
                distance[i][j]=pitagoras(deliveryPoints[i].x,deliveryPoints[i].y,deliveryPoints[j].x,deliveryPoints[j].y);
            }
            for(;j<total;j++){
                distance[i][j]=pitagoras(deliveryPoints[i].x,deliveryPoints[i].y,magazines[j-deliveryPoints.length].x,magazines[j-deliveryPoints.length].y);
            }
            // TODO Limit list of neighbourhood to DP
            //Dp Lists Created Dynamically after the Car has visited it
            //Integer [] tempArray =  intArrayToIntegerArray(distance[i]);
            //Integer [] indexes = sortNeighbourhood(tempArray);
            //deliveryPoints[i].setNeighbours(indexes, deliveryPoints.length, total);

        }
        for(;i<total;i++) {
            int j;
            for (j = 0; j < deliveryPoints.length; j++) {
                distance[i][j] = pitagoras(magazines[i - deliveryPoints.length].x, magazines[i - deliveryPoints.length].y, deliveryPoints[j].x, deliveryPoints[j].y);
            }
            for (; j < total; j++) {
                distance[i][j] = -1;

            }
            Integer[] tempArray = intArrayToIntegerArray(distance[i]);
            Integer[] indexes = sortNeighbourhood(tempArray);
            magazines[(i - deliveryPoints.length)].setNeighbours(indexes, deliveryPoints.length, total);
        }
    }

    int getDistance(int i, int j) {
        if (distance[i][j] == -1)
            System.out.println("Operation not permitted !!");
        return distance[i][j];
    }

    int pitagoras(int x1, int y1, int x2, int y2){
        int temp = (x1-x2)*(x1-x2)+(y1-y2)*(y1-y2);
        double result = (double) temp;
        double root = Math.sqrt(result);
        int finalResult = (int) Math.round(root);
        return finalResult;
    }

    public void printResultsToConsole(){
        for(int i=0; i<magazines.length; i++){
            for(int j=0; j<Magazine.vehicles;j++){
                System.out.println(magazines[i].cars[j].index + " " +magazines[i].cars[j].roadMap.toString() + " " +magazines[i].cars[j].distance);
                ListIterator<Integer> it = magazines[i].cars[j].roadMap.listIterator();
                Integer prev = it.next();
                while(it.hasNext()) {
                    Integer next = it.next();
                    System.out.print(distance[prev][next] + " ");
                    prev =next;

                }
                System.out.print("\n");
            }
        }
    }

    Integer[] sortNeighbourhood(final Integer permutation[]){
        int size = permutation.length;
        final Integer[] indexes = new Integer[size];
        final Integer[] finalIndexes = new Integer[size];
        for(int i=0; i < size ; i++)
            indexes[i] = i;
        Arrays.sort(indexes, new Comparator<Integer>() {
            @Override
            public int compare(final Integer o1, final Integer o2) {
                return Integer.compare(permutation[o1], permutation[o2]);
            }
        });
        return indexes;
    }

    Integer [] intArrayToIntegerArray(int []array){
        Integer [] tempArray =  new Integer[array.length];
        int k=0;
        for (int value : array) {
            tempArray[k++] = Integer.valueOf(value);
        }
        return tempArray;
    }

    Integer [] intArrayToIntegerArrayTwo(int []array, int MagazineIndex){
        Integer [] tempArray =  new Integer[array.length];
        int k=0;
        for (int value : array) {
            tempArray[k] = Integer.valueOf(WAGA*value+WAGADWA*distance[k][MagazineIndex]);
            k++;
        }
        return tempArray;
    }





    boolean solveThisProblem(){
        //if(dpServed == 45)  dpServed = 50;
        //System.out.println(dpServed);
        Long diff = System.currentTimeMillis() - startTime;
        if(diff.doubleValue()/1000 > 1.00){
            return false;
        }
        theBestCarIndex = NO_COMPARISON_AVAILABLE;
        theBestPlaceToGo = NO_COMPARISON_AVAILABLE;
        theBestParentMagazine = NO_COMPARISON_AVAILABLE;
        for(Magazine m: magazines)
            for(Car c: m.cars)
                if(!c.isJobDone())
                    compareWithTheLeader(c);
        if(theBestPlaceToGo == NO_COMPARISON_AVAILABLE) return true;
        Car leader = magazines[theBestParentMagazine-deliveryPoints.length].getCar(theBestCarIndex);
        DeliveryPoint bestDestination = deliveryPoints[theBestPlaceToGo];
        makeMove(leader, bestDestination);
        updateLists(theBestPlaceToGo);
        dpServed++;
        return true;
    }



    void compareWithTheLeader(Car c){
        if(c.isInTravel()){//Determining whether Magazine or DP should be considered
            int closestNeigh = deliveryPoints[c.position].getClosestNeighbour().intValue();
            if(closestNeigh == -1) return;
            if(validateSolution(deliveryPoints[closestNeigh].order, c.getCapacity())){
                if(theBestCarIndex==NO_COMPARISON_AVAILABLE){
                    theBestCarIndex = c.index;
                    theBestParentMagazine = c.parentMagazine.getNumber();
                    theBestPlaceToGo = closestNeigh;
                }else if(distance[magazines[theBestParentMagazine-deliveryPoints.length].getCar(theBestCarIndex).getPosition()][theBestPlaceToGo]
                        > distance[c.getPosition()][closestNeigh]){
                    theBestCarIndex = c.index;
                    theBestParentMagazine = c.parentMagazine.getNumber();
                    theBestPlaceToGo = closestNeigh;
                }
            }

            else deliveryPoints[c.position].neighbourhood.remove(0);

        }
        else {
            int closestNeigh = magazines[c.position-deliveryPoints.length].getClosestNeighbour().intValue();
            if (deliveryPoints[closestNeigh].order<=c.getCapacity()) {
                if (theBestCarIndex == NO_COMPARISON_AVAILABLE) {
                    theBestCarIndex = c.index;
                    theBestParentMagazine = c.parentMagazine.getNumber();
                    theBestPlaceToGo = closestNeigh;
                } else if (distance[magazines[theBestParentMagazine - deliveryPoints.length].getCar(theBestCarIndex).getPosition()][theBestPlaceToGo] >
                        distance[c.getPosition()][closestNeigh]) {
                    theBestCarIndex = c.index;
                    theBestParentMagazine = c.parentMagazine.getNumber();
                    theBestPlaceToGo = closestNeigh;
                }
            }
        }
    }

    boolean validateSolution(int order, int carCapacity){
        if(order > carCapacity)     return false;

        List<Integer> tempCosts = new LinkedList<Integer> ();
        for(Pair<Integer, Integer> pair : costs){
            tempCosts.add(pair.getValue());
        }
        tempCosts.remove(new Integer(order));

        LinkedList<Integer> carCapList = new LinkedList<Integer> ();
        for(Magazine m: magazines)
            for(Car car : m.cars)
                carCapList.add(car.capacity);
        carCapList.remove(new Integer(carCapacity));
        carCapList.add(carCapacity-order);

        Collections.sort(carCapList, new Comparator<Integer>() {    //descending order
            @Override
            public int compare(Integer o1, Integer o2) {
                return o2.compareTo(o1);
            }
        });

        for(Integer capacity : carCapList){
            ListIterator itr = tempCosts.listIterator(tempCosts.size());
            while(itr.hasPrevious()) {
                int cost = (Integer) itr.previous();
                if(capacity < tempCosts.get(0)) break;
                if (cost <= capacity) {
                    capacity -= cost;
                    itr.remove();
                }
            }
        }

        if(tempCosts.size()==0) return true;
        return false;
    }


    int getTotalCarNumber(){
        return magazines.length*Magazine.vehicles;
    }

    public void makeMove(Car c, DeliveryPoint where){
        if(!c.isInTravel())
            c.setInTravel();
        c.capacity -= where.getOrder();
        ourGoalValue += distance[where.getNumber()][c.getPosition()];
        c.distance += distance[where.getNumber()][c.getPosition()];
        c.setPosition(where.getNumber());
        costs.remove(new Pair(where.getNumber(), where.getOrder()));
        c.roadMap.add(where.getNumber());
        //Created After Making a Move
        int total = deliveryPoints.length+magazines.length;
        Integer [] tempArray =  intArrayToIntegerArrayTwo(distance[where.getNumber()], c.parentMagazine.getNumber());
        Integer [] indexes = sortNeighbourhood(tempArray);
        where.setNeighbours(indexes, deliveryPoints.length, total);
        if(!costs.isEmpty()){
            if(c.capacity<costs.get(0).getValue()){
                c.roadMap.add(c.parentMagazine.getNumber());
                ourGoalValue += distance[c.parentMagazine.getNumber()][c.getPosition()];
                c.distance += distance[c.parentMagazine.getNumber()][c.getPosition()];
                c.setJobDone();
                c.setPosition(c.parentMagazine.getNumber());
            }
        }
    }

    private void updateLists(int elementToRemove){
        elementsToRemove.add(elementToRemove);
        for(DeliveryPoint dp: deliveryPoints){
            if(dp.neighbourhood!=null){//!dp.neighbourhood.isEmpty()){
                Iterator<Integer> it = elementsToRemove.iterator();
                while(it.hasNext())
                    dp.neighbourhood.remove(new Integer(it.next()));
            }
        }
        for(Magazine m: magazines){
            m.dpNeighbourhood.remove(new Integer(elementToRemove));
        }
    }

    void moveRemainingCarsToParentMagazines(){
        for(Magazine m: magazines)
            for(Car c: m.cars)
                if(c.isInTravel() && !c.isJobDone()) {
                    c.roadMap.add(c.parentMagazine.getNumber());
                    ourGoalValue += distance[c.parentMagazine.getNumber()][c.getPosition()];
                    c.distance += distance[c.parentMagazine.getNumber()][c.getPosition()];
                    c.setJobDone();
                    c.setPosition(c.parentMagazine.getNumber());
                }
    }

    public void solveUsingShortestCycle(){
        ourGoalValue = 0;
        for(Magazine m: magazines)
            for(Car c : m.cars)
                ourGoalValue += c.getShortestHamiltonianCycle(distance);
    }








}
