import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.PriorityQueue;

class Solution {
    public int reachableNodes(int[][] edges, int maxMoves, int n) {
        // sub nodes as edge weight
        ArrayList<ArrayList<Pair>> adj= new ArrayList<>();
        for(int i=0;i<n;i++)adj.add(new ArrayList<>());
        for(int i=0;i<edges.length;i++){
            int src=edges[i][0],des=edges[i][1],wt=edges[i][2];
            // undirected graph
            adj.get(src).add(new Pair(des,wt));
            adj.get(des).add(new Pair(src,wt));
        }

        PriorityQueue<Pair> pq=new PriorityQueue<>((a,b)->a.wt-b.wt);
        int INF=(int)1e8;
        int dis[]=new int[n];
        Arrays.fill(dis,INF);
        dis[0]=0;
        int ans=0;
        pq.add(new Pair(0,0));

        while (!pq.isEmpty()){
            Pair node=pq.poll();
            int nodeIndex=node.node;
            int nodeWt=node.wt;
            for(Pair adjNode:adj.get(node.node)){
                int adjNodeIndex=adjNode.node;
                int adjWt=adjNode.wt;
                int nodesToReachAdj=nodeWt+adjWt+1; // for 2 subNodes, it takes 3 steps to reach next node via subNodes.
                if(nodesToReachAdj<=maxMoves && nodesToReachAdj<dis[adjNodeIndex]){
                    // it must be less than max Moves and  lesser than current distance
                    dis[adjNodeIndex]=nodesToReachAdj;
                    pq.add(new Pair(adjNodeIndex,nodesToReachAdj));
                }
            }
        }


        for(int di:dis)if(di!=INF)ans++;  // main nodes

        // iterate over edges
        for(int edge[]:edges){
            int src=edge[0],des=edge[1],wt=edge[2];
            int fromSrc=dis[src]; //number of moves taken to reach src
            int fromDes=dis[des]; //number of moves taken to reach des
            if(fromSrc==INF && fromDes==INF)continue;  //for any edge if their node are not reachable from either end, then ignore edge

            int remainingS=maxMoves-fromSrc;  // number of moves remaining from src
            int remainingD=maxMoves-fromDes;  // number of moves remaining from des
            if(fromDes==INF || fromSrc==INF){
                // if node is not reachable from either end then ans is

                if(fromDes==INF)ans+=remainingS; // node des is not reachable means node src s reachable. take ans from src
                else if(fromSrc==INF)ans+=remainingD; // node src is not reachable means node des s reachable. take ans from des

            }else{
                    // both src and des have some number of moves.

                if(remainingS+remainingD<wt){
                    // if moves from src and des together are lesser than subNodes.
                    // then answer is sum of reachable nodes from both nodes.
                    // (node are not overlapping)
                    ans+=remainingS+remainingD;
                }else{
                    // sum is = or higher means, there must be overlapping.
                     // but if sum is higher, then wt we can only take wt amount of subNodes
                    ans+=wt;
                }
            }
        }
        return  ans;
    }
}
class Pair{
    int node,wt;
    Pair(int node,int wt){
        this.node=node;
        this.wt=wt;
    }
}
