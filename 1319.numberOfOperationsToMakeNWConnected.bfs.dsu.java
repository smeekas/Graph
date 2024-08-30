import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;

class Solution {
    public int makeConnected(int n, int[][] connections) {

        // our logic for dfs.
        // assume graph are n single nodes.
        // so to connect all of them we need minimum n-1 edges.
        // so if graph have less then n-1 means we will never be able to make entire graph connected.
        if(connections.length<n-1)return  -1;

        // now if we have x components then we need x-1 edges to connect them.
        // this is same as counting number of islands.
        // we can do it via dfs or bfs

        // we follow same logic in dsu
//        return  bfs(n,connections);
        return  dsu(n,connections);
    }
    int bfs(int n,int[][]conn){
            ArrayList<ArrayList<Integer>> adj=new ArrayList<>();
            for(int i=0;i<n;i++){
                adj.add(new ArrayList<>());
            }
            for(int i=0;i<conn.length;i++){
                adj.get(conn[i][0]).add(conn[i][1]);
                adj.get(conn[i][1]).add(conn[i][0]);
            }
        boolean visited[]=new boolean[n];
        int components=0;
            for(int i=0;i<n;i++){
                if(visited[i]==false){
                Queue<Integer> q=new ArrayDeque<>();
                q.add(i);
                while (!q.isEmpty()){
                    int node=q.poll();
                    for(Integer adjNode:adj.get(node)){
                        if(visited[adjNode]==false){
                            visited[adjNode]=true;
                            q.add(adjNode);
                        }
                    }
                }
                components++;
                }
            }
   return components-1;
    }
    int dsu(int n,int [][]conn){
            Disjoint dj=new Disjoint(n);
            for (int i=0;i<conn.length;i++){
                if(dj.findUParent(conn[i][0])==dj.findUParent(conn[i][1])){
                        // will introduce cycle
                }else{
                    dj.unionByRank(conn[i][0],conn[i][1]);
                }
            }
            // how to count number of components??
        // well all nodes from same component will have same ultimate parent.
        // so we can use set and add ultimate parent of all nodes.
        // or
        // we just have to count node that is ultimate parent of itself.
        // because for all nodes in component, they will have same ultimate parent (which is not itself).
        // so we are counting total number of bosses (ultimate parents) which is number of components.
        int components=0;
        for(int i=0;i<n;i++){
                if(dj.findUParent(i)==i)components++;
        }
        return  components-1;
    }
}


class Disjoint{
    ArrayList<Integer> rank;
    ArrayList<Integer> parent;
    Disjoint(int n){
        rank=new ArrayList<>();
        parent=new ArrayList<>();
        for(int i=0;i<=n;i++){
            rank.add(0);
            parent.add(i);
        }
    }
    int findUParent(int n){
        if(parent.get(n)==n)return  n;
        int ul_p=findUParent(parent.get(n));
        parent.set(n,ul_p);
        return  ul_p;
    }
    void unionByRank(int u,int v){
        int ul_u=findUParent(u);
        int ul_v=findUParent(v);
        if(ul_u==ul_v)return;
        int ul_u_rank=rank.get(ul_u);
        int ul_v_rank=rank.get(ul_v);
        if(ul_u_rank<ul_v_rank){
            parent.set(ul_u,ul_v);
        }else if(ul_u_rank>ul_v_rank){

            parent.set(ul_v,ul_u);
        }else{
            // if both rank are same then we can do anything
            parent.set(ul_v,ul_u);
            int rankU=rank.get(ul_u_rank);
            rank.set(ul_u,rankU+1);
        }
    }

}
