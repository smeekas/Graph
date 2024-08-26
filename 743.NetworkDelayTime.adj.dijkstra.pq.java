import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;

class Pair{
    int des,wt;
    Pair(int des,int wt){
        this.des=des;
        this.wt=wt;
    }
}
class Solution {
    public int networkDelayTime(int[][] times, int n, int k) {
        // just run dijkstra then on distance find max value.

        ArrayList<ArrayList<Pair>> adj=new ArrayList<>();
        for(int i=0;i<=n;i++){
            adj.add(new ArrayList<>());
        }
        for(int i=0;i<times.length;i++){
            int src=times[i][0];
            int des=times[i][1];
            int wt=times[i][2];
            adj.get(src).add(new Pair(des,wt));
        }
        int visited[]=new int[n+1];
        Arrays.fill(visited,(int)1e9);
        dijkstra(adj,visited,k);
        int max=0;
        for(int i=1;i<=n;i++){
            max=Math.max(max,visited[i]);
        }
        return  max==(int)1e9?-1:max;
    }

    void dijkstra(ArrayList<ArrayList<Pair>> adj,int visited[],int src){
        PriorityQueue<Pair> q=new PriorityQueue<>((a, b)->a.wt-b.wt);
        visited[src]=0;
        q.add(new Pair(src,0));
        while (!q.isEmpty()){
            Pair node=q.poll();
            for(Pair adjNodes:adj.get(node.des)){
                int adjwt=adjNodes.wt;
                int adjNode=adjNodes.des;
                int parwt=node.wt;
                if(adjwt+parwt < visited[adjNode]){
                    visited[adjNode]=adjwt+parwt;
                    q.add(new Pair(adjNode,visited[adjNode]));
                }
            }
        }
    }
}
