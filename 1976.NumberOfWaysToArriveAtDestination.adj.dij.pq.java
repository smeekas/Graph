import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;

class Pair{
    int des;
    long wt;
    Pair(int des,long wt){
        this.des=des;
        this.wt=wt;
    }
}
class Solution {
    public int countPaths(int n, int[][] roads) {
        ArrayList<ArrayList<Pair>> adj=new ArrayList<>();
        for(int i=0;i<n;i++){
            adj.add(new ArrayList<>());
        }
        for(int  i=0;i<roads.length;i++){
            int src=roads[i][0];
            int des=roads[i][1];
            int wt=roads[i][2];
            adj.get(src).add(new Pair(des,wt));
            adj.get(des).add(new Pair(src,wt));
        }
        PriorityQueue<Pair> pq=new PriorityQueue<>((a,b)->Long.compare(a.wt,b.wt));

        long distance[]=new long[n];
        int ways[]=new int[n];

        Arrays.fill(distance,Long.MAX_VALUE);
        Arrays.fill(ways,0);

        distance[0]=0;
        ways[0]=1;

        pq.add(new Pair(0,0));

        while (!pq.isEmpty()){
            Pair node=pq.poll();
            for (Pair adjNode:adj.get(node.des)){
                long nodeWt=node.wt;
                long adjWt=adjNode.wt;
                if(nodeWt+adjWt<distance[adjNode.des]){
                        // if we found new lesser distance then update it & push it to the queue.
                    distance[adjNode.des]=nodeWt+adjWt;
                    //if we can reach parent in x ways then we can reach this adjacent node in also x ways.
                    ways[adjNode.des]=ways[node.des];
                    pq.add(new Pair(adjNode.des,distance[adjNode.des]));

                }else if(nodeWt+adjWt==distance[adjNode.des]){
                    // if distance is same then we found more ways to reach this adjacent node.
                    //assume we can reach parent in x ways & this adjacent node is y ways.
                    // now from parent to adjacent node we have same minimum distance.
                    // so along with y ways we can also use this x ways to reach adjacent node.
                    // so totals ways are x+y
                    ways[adjNode.des]=(ways[adjNode.des]+ways[node.des]) % ((int)1e9+7);
                }
                
                // what if after exploring node-x we get to node-x with even less distance?
                // since we found less distance we will add node into queue again. and now consider number of ways we can have that new lesser distance.
            }
        }

        return  ways[n-1];
    }
}
