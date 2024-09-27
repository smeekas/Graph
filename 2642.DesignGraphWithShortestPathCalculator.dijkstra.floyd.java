import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.PriorityQueue;

class Graph {
    // two ways
    // 1. in shortestPath method do dijkstra when requested
    // 2. floyd warshall

    ArrayList<ArrayList<Pair>> adj=new ArrayList<>();
    int multi[][];
    int len;
    public Graph(int n, int[][] edges) {
        if(true){
            //floyd warshall
            // initially we will run floyd warshall with given edges.
            this.len=n;
            this.multi=new int[n][n];
            for(int[]row:multi){
                Arrays.fill(row,(int)1e9);
            }
            for(int i=0;i<n;i++)multi[i][i]=0;
            for(int[] edge:edges){
                int src=edge[0];
                int des=edge[1];
                int wt=edge[2];
                multi[src][des]=wt; // directed graph
            }
            for(int k=0;k<n;k++){
                for(int i=0;i<n;i++){
                    for(int j=0;j<n;j++){
                        if(multi[i][k]+multi[k][j]<multi[i][j]){
                            multi[i][j]=multi[i][k]+multi[k][j];
                        }
                    }
                }
            }
        }else{
            // dijkstra
            // initially we will create adjacency list
            for(int i=0;i<n;i++){
                this.adj.add(new ArrayList<>());
            }
            for(int []edge:edges){
                int src=edge[0];
                int des=edge[1];
                int wt=edge[2];
                this.adj.get(src).add(new Pair(des,wt));
            }
        }
    }

    public void addEdge(int[] edge) {
        if(true){
            //floyd warshall
            int src=edge[0];
            int des=edge[1];
            int wt=edge[2];
            // for every node to src we have distance
            // for des to every node we have distance
            // from every node to every node we have distance, but now we want to consider this new edge too.
            // what is minimum i-j(current value) or i-src + src-des + des-j ?
            
            for(int i=0;i<len;i++){
                for(int j=0;j<len;j++){
                    int newCost=multi[i][src]+multi[des][j]+wt;
                    if(newCost<multi[i][j]){
                        multi[i][j]=newCost;
                    }
                }
            }

        }else{
            // dijkstra
            // we just update adjacency list
            int src=edge[0];
            int des=edge[1];
            int wt=edge[2];
            this.adj.get(src).add(new Pair(des,wt));
        }
    }

    public int shortestPath(int node1, int node2) {
        if(true){
            // floyd warshall
            // just return answer
            return multi[node1][node2]==1e9?-1:multi[node1][node2];
        }
        // dijkstra
        // we apply dijkstra from node1.
        HashMap<Integer,Integer> visited=new HashMap<>();
        visited.put(node1,0);
        PriorityQueue<Pair> pq=new PriorityQueue<>((a, b)->a.wt-b.wt);
        pq.add(new Pair(node1,0));
        while(!pq.isEmpty()){
            Pair node=pq.poll();
            int nodewt=node.wt;
            for(Pair adjNode:adj.get(node.node)){
                if(visited.containsKey(adjNode.node)){
                    // visited
                    if(nodewt+adjNode.wt<visited.get(adjNode.node)){
                        // visited but we found even shorter path
                        visited.put(adjNode.node,nodewt+adjNode.wt);
                        pq.add(new Pair(adjNode.node,nodewt+adjNode.wt));
                    }
                }else{
                    // not at all visited.
                    visited.put(adjNode.node,nodewt+adjNode.wt);
                    pq.add(new Pair(adjNode.node,nodewt+adjNode.wt));
                }
            }
        }

        return visited.containsKey(node2)?visited.get(node2):-1;
    }
}
class Pair{
    int node,wt;
    Pair(int node,int wt){
        this.node=node;
        this.wt=wt;
    }
}

