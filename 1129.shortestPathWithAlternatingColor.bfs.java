import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Queue;

class Solution {
    public int[] shortestAlternatingPaths(int n, int[][] redEdges, int[][] blueEdges) {
        ArrayList<ArrayList<Pair>> adj=new ArrayList<>();
        // we assume 0 is for red color and 1 is for blue color.

        for(int i=0;i<n;i++){
            adj.add(new ArrayList<>());
        }
        for(int i=0;i<redEdges.length;i++){
            int src=redEdges[i][0];
            int des=redEdges[i][1];
            adj.get(src).add(new Pair(des,0,0)); // we won't use this dis
        }

        for(int i=0;i<blueEdges.length;i++){
            int src=blueEdges[i][0];
            int des=blueEdges[i][1];
            adj.get(src).add(new Pair(des,1,0)); // we won't use this dis
        }
        Queue<Pair> q=new ArrayDeque<>();
        int visited[][]=new int[n][2]; // for every node we can visit either via red or blue edge.
        // 0 for red, 1 for blue
        int distance[]=new int[n];
        Arrays.fill(distance,-1);
        for(int i=0;i<n;i++){
            visited[i][0]=-1;
            visited[i][1]=-1;
            //initially nothing is visited.
        }

        distance[0]=0; // 0 to 0 distance is 0.
        q.add(new Pair(0,-1,0));
        while (!q.isEmpty()){
            Pair node=q.poll();
            int nodeIndex=node.node;
            int nodeDis=node.dis;
            int nodeColor=node.color;

            for(Pair adjPair:adj.get(nodeIndex)){
                int adjColor=adjPair.color;
                int adjNode=adjPair.node;
                if(visited[adjNode][adjColor]==-1 && adjColor!=nodeColor){
                    // if adjNode from nodeIndex is not visited via adjColor and nodeIndex's visiting color (nodeColor) and adjColor are not same (means alternate)

                    visited[adjNode][adjColor]=1; // mark as visited
                    if(distance[adjNode]==-1){
                        // if adjNode is not at all visited then collect distance.
                        distance[adjNode]=nodeDis+1;
                    }
                    // add adjNode with visiting color and also distance 
                    q.add(new Pair(adjNode,adjColor,nodeDis+1));
                }else{

                }
            }
        }
        // in this algorithm we will be visiting some node again but via different path (and via different colored edge)
        // and this is bfs like call so once we assign distance it will be minimum only. so no need to check Math.max and stuff
        return  distance;
    }
}
class  Pair{
    int node,color,dis;
    Pair(int node,int color,int dis){
        this.node=node;
        this.color=color;
        this.dis=dis;
    }
}
