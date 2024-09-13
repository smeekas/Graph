import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;

class Solution {
    public int minScore(int n, int[][] roads) {
        // dumb idea is we can just take minimum edge weight from roads.
        // but this is wrong approach.
        // minimum edge from roads may not exist between 1 and n.

        // so we do traversal from 1. from traversal, we will keep track of edge with minimum edge weight.
        ArrayList<ArrayList<Pair>> graph=new ArrayList<>();
        for(int i=0;i <=n;i++){
            graph.add( new ArrayList<>());
        }
        for(int[]edge:roads){
            int src=edge[0],des=edge[1],wt=edge[2];
            graph.get(src).add(new Pair(des,wt));
            graph.get(des).add(new Pair(src,wt));
        }
        boolean visited[]=new boolean[n+1];
        visited[1]=true;
        Queue<Pair> q=new ArrayDeque<>();
        int min=Integer.MAX_VALUE;
        q.add(new Pair(1,0));
        while (!q.isEmpty()){
            Pair node= q.poll();
            for(Pair adjNodes:graph.get(node.node)){
                if(visited[adjNodes.node]==false){
                    visited[adjNodes.node]=true;
                    q.add(new Pair(adjNodes.node,adjNodes.wt));
                }
                // if node is not visited then we definitely consider edge.
                // but if node is already visited, then also since edge exists we count edge
                // that is the reason of taking answer outside if.
                // NOTE that we just want minimum edge from graph (1 to n).
                // here outside if we will check for answer for every edge.
                min=Math.min(min,adjNodes.wt);
            }
        }
        return min;
    }
}
class Pair{
    int node,wt;
    Pair(int node,int wt){
        this.node=node;
        this.wt=wt;
    }
}
