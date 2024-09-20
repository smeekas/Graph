import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Queue;

class Solution {
    public int findShortestCycle(int n, int[][] edges) {
        // here graph is undirected.
        //here approach which we used in find the longest cycle problem will not work.
        // so we do bfs from every node. as soon as we find cycle, that will be the shortest(because we have done bfs)

        ArrayList<ArrayList<Integer>> adj=new ArrayList<>();
        for(int i=0;i<n;i++)adj.add(new ArrayList<>());
        for(int edge[]:edges){
                int src=edge[0];
                int des=edge[1];
                adj.get(src).add(des);
                adj.get(des).add(src);
        }
        int minCycle=Integer.MAX_VALUE;
        for(int i=0;i<n;i++){
            int visited[]=new int[n];
            Arrays.fill(visited,-1);;
            Queue<Pair> q=new ArrayDeque<>();
            // queue of Pair because we also need to check while visiting adjacent, we are not visiting parent node.
            q.add(new Pair(i,-1));
            visited[i]=1;
            while (!q.isEmpty()){
                Pair node=q.poll();
                for(Integer adjNode:adj.get(node.node)){
                    if(adjNode!=node.parent){
                        if(visited[adjNode]==-1){
                                visited[adjNode]=visited[node.node]+1;
                                q.add(new Pair(adjNode,node.node));
                        }else{
                            // node and adjNode have same distance.
                            // in that case (node-1) + (adjNode-1) + 1(node to adjNode) (-1 because visited gives nodes. we want to calculate edge)  = node+adjNode-1
                            //in short (len from i to node)+(len from i to adjNode) + (adjNode to node)
                            
                            int cycleLen=visited[adjNode]+visited[node.node]-1;
                            minCycle=Math.min(cycleLen,minCycle);
                            // once we found cycle, we do not want to continue wth dfs anymore. so break it.
                            break;
                        }
                    }
                }

            }

        }
        // if cycle not found then return -1 else min length of cycle
        return  minCycle==Integer.MAX_VALUE?-1:minCycle;
    }
}
class Pair{
    int node,parent;
    Pair(int node,int parent){
        this.node=node;
        this.parent=parent;
    }
}
