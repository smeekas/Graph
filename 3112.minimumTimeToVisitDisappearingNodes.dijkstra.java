import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;

class Solution {
    public int[] minimumTime(int n, int[][] edges, int[] disappear) {


        // we do dijkstra only. but we go to adjNode if we can reach there before it disappears.

        ArrayList<ArrayList<Pair>> adj=new ArrayList<>();
        for(int i=0;i<n;i++)adj.add(new ArrayList<>());
        for(int[] edge:edges){
            int src=edge[0],des=edge[1],dis=edge[2];
            // undirected
            adj.get(src).add(new Pair(des,dis));
            adj.get(des).add(new Pair(src,dis));
        }

        PriorityQueue<Pair> pq=new PriorityQueue<>((a,b)->a.dis-b.dis);
        int[] distance=new int[n];
        Arrays.fill(distance,(int)1e9);
        pq.add(new Pair(0,0));
        distance[0]=0;
        while (!pq.isEmpty()){
            Pair currNode=pq.poll();
            int currDis=currNode.dis;

            //--------------------
             if(currDis>distance[currNode.node])continue;
            //--------------------
            // without above line this problem will give TLE.
            // reason
            // from pq we always visit the shortest distance node first.
            // we may have same node twice in pq, with different distances.
            // we will process node with the shortest distance first, but we will process again with more distance
            // ( with more distance, we will go into loop, but we won't be adding any adjNode in queue because we already looped through adjNode with shorter distance)
            // so if current distance is higher than what we currently have then we will ignore it.

            for(Pair adjNodes:adj.get(currNode.node)){
                int newDis=currDis+adjNodes.dis;
                if(newDis<distance[adjNodes.node] && newDis<disappear[adjNodes.node] ){
                    // if new distance is shortest, and we can reach there before it disappears.
                    distance[adjNodes.node]=currDis+adjNodes.dis;
                    pq.add(new Pair(adjNodes.node,distance[adjNodes.node]));
                }
            }
        }
        for(int i=0;i<distance.length;i++){
            if(distance[i]>disappear[i]){
                distance[i]=-1;
            }
        }
        return distance;

    }
}
class Pair{
    int node, dis;
    Pair(int node,int dis){
        this.node=node;
        this.dis=dis;
    }
}
