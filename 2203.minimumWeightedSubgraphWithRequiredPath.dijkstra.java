import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;

class Solution {
    long INF=Long.MAX_VALUE;
    public long minimumWeight(int n, int[][] edges, int src1, int src2, int dest) {
        // simple Idea
        // worst case is that we go from src1 and src2 individually.
        // answer is possible only if we can reach destination from src1 and src2 anyhow.
        // optimal answer from src1 to des and src2  to des, might include overlapping path.
        // assume overlapping starts from node-x.
        // now we can find the shortest path from src1 to node-x and from src-2 to node-x
        // and shortest path from node-x to destination.
        // we can try all possible nodes for node-x and take optimal answer.
        // for every node which will be taken for node-x, how to find the shortest path from node-x to des?
        // we apply dijkstra from destination on reverse graph!
        // that way we will have the shortest path to all nodes from destination.
        ArrayList<ArrayList<Pair>> adj=new ArrayList<>();
        ArrayList<ArrayList<Pair>> adjR=new ArrayList<>();
        for(int i=0;i<n;i++) {
            adj.add(new ArrayList<>());
            adjR.add(new ArrayList<>());
        }
        for(int edge[]:edges){
            int src=edge[0],des=edge[1],wt=edge[2];
            adj.get(src).add(new Pair(des,wt));
            adjR.get(des).add(new Pair(src,wt));
        }

        long fromSrc1[]=shortestPath(adj,src1,n);
        long fromSrc2[]=shortestPath(adj,src2,n);

        // if destination node is unreachable via either src1 or src2 then return -1. 
        if(fromSrc1[dest]==INF || fromSrc2[dest]==INF)return -1;

        long fromDes[]=shortestPath(adjR,dest,n);

        // let us assume, src1 to des and src2 to des do not overlap, and we can reach des from src1 and src separately.
        
        long best=fromSrc1[dest]+fromSrc2[dest];
        for(int i=0;i<n;i++){
            // if node i from src-1 or from src-2 or des is unreachable that means from node-i we don't have answer.
            if(fromSrc1[i]==INF || fromSrc2[i]==INF || fromDes[i]==INF)continue;
            best=Math.min(best,fromSrc1[i]+fromSrc2[i]+fromDes[i]);
        }


        return best;
    }
    long[] shortestPath(ArrayList<ArrayList<Pair>> adj, int src,int n){
        long distance[]=new long[n];
        Arrays.fill(distance,INF);
        distance[src]=0;
        PriorityQueue<Pair> pq=new PriorityQueue<>((a,b)->Long.compare(a.wt,b.wt));
        pq.add(new Pair(src,0));
        while (!pq.isEmpty()){
            Pair node=pq.poll();
            if(node.wt>distance[node.node])continue;
            for(Pair adjNode:adj.get(node.node)){
                if(node.wt+adjNode.wt<distance[adjNode.node]){
                    distance[adjNode.node]=node.wt+adjNode.wt;
                    pq.add(new Pair(adjNode.node,distance[adjNode.node]));
                }
            }
        }
        return  distance;
    }
}
class Pair{
    int node;long wt;
    Pair(int node,long wt){
        this.node=node;
        this.wt=wt;
    }
}
