import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;

class Solution {
    public int minimumCost(int[] start, int[] target, int[][] specialRoads) {
        //from one special road we can go to another special road.
        // from src, we try to go to all special roads by manhattan distance.
        // once we reach starting of special road, we can go to end of special road by given weight.
        
        PriorityQueue<Pair> pq=new PriorityQueue<>((a,b)->a.wt-b.wt);
        HashMap<Integer,Integer> visited=new HashMap<>();
        pq.add(new Pair(start[0],start[1],0));

        int best=Math.abs(start[0]-target[0])+Math.abs(start[1]-target[1]); // best case if we go cell by cell without special road.

        
        // here we also treat special road as node !

        while (!pq.isEmpty()){
            Pair node=pq.poll();
            int cx=node.x;
            int cy= node.y;
            int cwt=node.wt;

            for(int i=0;i<specialRoads.length;i++){
                int x1=specialRoads[i][0];
                int y1=specialRoads[i][1];
                int x2=specialRoads[i][2];
                int y2=specialRoads[i][3];

                int nodeToSpRoad=Math.abs(cx-x1)+Math.abs(cy-y1); // distance till src of special road .
                int total=cwt+nodeToSpRoad+specialRoads[i][4]; // to reach end of sp road
                

                if(visited.containsKey(i)){
                    // already visited.
                    if(total<visited.get(i)){
                        // if newDistance is lesser, then explore it
                        visited.put(i,total);
                        pq.add(new Pair(x2,y2,total));
                    }
                }else{
                    // not at all visited.
                    visited.put(i,total);
                    pq.add(new Pair(x2,y2,total));
                }


            }
        }
        // some special road's end can be the target or cannot be the target.
        // from end of the special road we try to go to our target.
        for(int i=0;i<specialRoads.length;i++){
            int x=specialRoads[i][2];
            int y=specialRoads[i][3];
            int costTOEndOfSpRoad=visited.get(i);
            int costOfEndToTarget=Math.abs(x-target[0])+ Math.abs(y-target[1]);
            best=Math.min(best,costTOEndOfSpRoad+costOfEndToTarget);
        }

        return best;
    }

}
class Pair{
    int x,y,wt;

    public Pair(int x, int y, int wt) {
        this.x = x;
        this.y = y;
        this.wt = wt;
    }
}
