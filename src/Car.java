import javafx.util.Pair;

import java.lang.reflect.Array;
import java.util.*;

public class Car {
    int capacity; //nie moze byc static !
    int index;
    int position;   //index from 1 to r+d
    int distance = 0;
    Magazine parentMagazine;
    boolean jobDone = false;
    boolean inTravel = false;
    List<Integer> roadMap;

    final String WHITE_SPACE = " ";
    final String END_OF_LINE = "\n";

    public Car(Magazine parent, int i) {
        this.index = i;
        this.parentMagazine = parent;
        this.position = parent.getNumber();
        this.roadMap = new LinkedList<Integer>();
        this.roadMap.add(position);
    }

    public void setCapacity(int c) {
        capacity = c;
    }

    public int getCapacity() {
        return capacity;
    }

    public boolean isJobDone() {
        return jobDone;
    }

    public void setJobDone() {
        jobDone = true;
    }

    public boolean isInTravel() {
        return inTravel;
    }

    public void setInTravel() {
        inTravel = true;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int pos) {
        position = pos;
    }

    String parseSolution() {
        StringBuilder buildSolution = new StringBuilder();
        buildSolution.append(index + 1);
        buildSolution.append(WHITE_SPACE);
        for (Integer i : roadMap) {
            buildSolution.append(i + 1);
            buildSolution.append(WHITE_SPACE);
        }
        buildSolution.append(END_OF_LINE);
        return buildSolution.toString();
    }

    public int getShortestHamiltonianCycle(int[][] dist) {
        int n = roadMap.size()-1;
        if(n==0) return 0;
        int[][] dp = new int[1 << n][n];
        for (int[] d : dp)
            Arrays.fill(d, Integer.MAX_VALUE / 2);
        dp[1][0] = 0;
        for (int mask = 1; mask < 1 << n; mask += 2) {
            for (int i = 1; i < n; i++) {
                if ((mask & 1 << i) != 0) {
                    for (int j = 0; j < n; j++) {
                        if ((mask & 1 << j) != 0) {
                            dp[mask][i] = Math.min(dp[mask][i], dp[mask ^ (1 << i)][j] + dist[roadMap.get(j)][roadMap.get(i)]);
                        }
                    }
                }
            }
        }
       /* int res = Integer.MAX_VALUE;
        for (int i = 1; i < n; i++) {
            res = Math.min(res, dp[(1 << n) - 1][i] + dist[roadMap.get(0)][roadMap.get(i)]);
        }*/

        // reconstruct path
        int cur = (1 << n) - 1;
        Integer [] order = new Integer [n+1];
        int last = 0;
        for (int i = n - 1; i >= 1; i--) {
            int bj = -1;
            for (int j = 1; j < n; j++) {
                if ((cur & 1 << j) != 0 && (bj == -1 || dp[cur][bj] + dist[roadMap.get(bj)][roadMap.get(last)] > dp[cur][j] + dist[roadMap.get(j)][roadMap.get(last)])) {
                    bj = j;
                }
            }
            order[i] = bj;
            cur ^= 1 << bj;
            last = bj;
        }
        order[0] = 0;
        order[n] = 0;
       // System.out.println(Arrays.toString(order));
        int res = 0;
        for(int i=0; i<order.length; i++) {
            order[i] = roadMap.get(order[i]);
            if(i>0)
                res += dist[order[i-1]][order[i]];
        }

       // if(!roadMap.containsAll(Arrays.asList(order)))
          //  System.out.println(Arrays.toString(order)+res);
        roadMap = new LinkedList<Integer>(Arrays.asList(order));
        distance = res;
        return res;
    }

    Integer getTheFarthestPoint(final int [][] dist){
       return Collections.max(roadMap, new Comparator<Integer> (){
            @Override
            public int compare(Integer o1, Integer o2){
                return Integer.compare(dist[parentMagazine.getNumber()][o1], dist[parentMagazine.getNumber()][o2]);
            }
        });
    }

    Integer findTheMostExpensivePoint(int [][] dist){
        ArrayList<Pair<Integer, Integer>> singlePointCost = new ArrayList(roadMap.size()-2);

        ListIterator<Integer> it = roadMap.listIterator();
        Integer previous = it.next();
        Integer current = it.next();
        Integer next;
        while(it.hasNext()){
            next = it.next();
            singlePointCost.add(new Pair<Integer, Integer>(current, dist[previous][current]+dist[current][next] - dist[previous][next]));
            previous = current;
            current = next;
        }

       // System.out.println(singlePointCost);

        Integer value = Collections.max(singlePointCost, new Comparator<Pair<Integer, Integer>>(){
            @Override
            public int compare(Pair<Integer, Integer> o1, Pair<Integer, Integer> o2){
                return Integer.compare(o1.getValue(), o2.getValue());
            }
        }).getKey();

        //System.out.println(value);
        return value;
    }
}