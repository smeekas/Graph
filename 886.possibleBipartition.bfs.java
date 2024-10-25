import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;

class Solution {
    public boolean possibleBipartition(int n, int[][] dislikes) {
        // here we have to check whether graph is bipartite or not.
        ArrayList<ArrayList<Integer>> adj=new ArrayList<>();
        for(int i=0;i<=n;i++)adj.add(new ArrayList<>());
        for(int [] edge:dislikes){
            int src=edge[0],des=edge[1];
            adj.get(src).add(des);
            adj.get(des).add(src);

        }
        int visited[] =new int[n+1];

        for(int i=1;i<=n;i++){
            if(visited[i]>0)continue; //already visited.
            Queue<Pair> q=new ArrayDeque<>();
            q.add(new Pair(i,1));
            visited[i]=1;
            while (!q.isEmpty()){
                Pair node=q.poll();
                for(int adjNode:adj.get(node.node)){
                    if(visited[adjNode]==0){
                        // not visited
                        visited[adjNode]=node.state==1?2:1;
                        q.add(new Pair(adjNode,visited[adjNode]));
                    }else{
                        // parent and child have same color.
                        //graph cannot be bi-partite
                        if(visited[adjNode]==node.state)return false;
                    }
                }
            }
        }

        return  true;
    }
}
class Pair{
    int node;
    int state;
    Pair(int node,int state){
        this.node=node;
        this.state=state;
    }
}
