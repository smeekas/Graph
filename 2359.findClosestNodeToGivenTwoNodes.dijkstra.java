import java.util.*;

class Solution {

    public int closestMeetingNode(int[] edges, int node1, int node2) {
        // here we need to find maximum distance from node-A to node-X and node-B to node-X to be minimized.
        // we can apply dijkstra from both sources and then do processing on the found result.
        int n1[]=dijkstra(edges,node1);
        int n2[]=dijkstra(edges,node2);
        int node=-1;
        int min=(int)1e8;

        for(int i=0;i<edges.length;i++){
            if(n1[i]==-1|| n2[i]==-1)continue; //either node1 or node2 cannot reach this node-i.
            if(Math.max(n1[i],n2[i])<min){
                min=Math.max(n1[i],n2[i]);
                node=i;
            }
        }
        return  node;
    }
    int[] dijkstra(int []edge,int node){
        int dis[]=new int[edge.length];
        Arrays.fill(dis,(int)(1e8));
        dis[node]=0;
        Queue<Pair> q=new ArrayDeque();
        q.add(new Pair(node,0));
        while (!q.isEmpty()){
            Pair currNode=q.poll();
            int adjNode=edge[currNode.node];
            if(adjNode==-1)continue;
            if(currNode.wt+1< dis[adjNode]){
                dis[adjNode]=currNode.wt+1;
                q.add(new Pair(adjNode,dis[adjNode]));
            }
        }
        return  dis;
    }
}
class Pair{
    int node,wt;
    Pair(int node,int wt){
        this.wt=wt;
        this.node=node;
    }
}
