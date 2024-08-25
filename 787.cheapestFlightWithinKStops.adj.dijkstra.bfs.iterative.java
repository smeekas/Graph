import java.util.*;

class  Pair{
    int des,wt;
    Pair(int  des, int wt){
        this.des=des;
        this.wt=wt;
    }
}
class Node{
    int des,wt,stops;
    Node(int  des, int wt,int stops){
        this.des=des;
        this.wt=wt;
        this.stops=stops;
    }
}

class Solution {
    public int findCheapestPrice(int n, int[][] flights, int src, int dst, int k) {
        ArrayList<ArrayList<Pair>> adj=new ArrayList<>();
        for(int i=0;i<n;i++){
            adj.add(new ArrayList<>());
        }
        for(int i=0;i<flights.length;i++){
            int srcNode=flights[i][0];
            int desNode=flights[i][1];
            int wt=flights[i][2];
            adj.get(srcNode).add(new Pair(desNode,wt));
        }
        int distance[]=new int[n];
        Arrays.fill(distance,(int)1e9);

        // edge cases for this problem.
        // we may find the shortest distance with more number of stops.
        // now if we have distance with less number of stops then dijkstra will ignore this.
        // dijkstra specialize in minimizing distance. it does not care about how many stops it is taking.
        // but we have to care about that too.

        Queue<Node> pq=new ArrayDeque<Node>();
        // reason behind using queue instead of priority queue.
        // we will start with source node.
        // we will traverse source's adj. then adj's adj.
        // we are going level by level. each time stops increasing.
        // so by using priority queue we will have overhead of logN when inserting new node. which will increase the TC.
        // that why we are using queue.

        // also we have take node with less stop first.
        // why not by price?? (in pq)
        // explanation:
        // assume we go with sorting of price
        // assume there are two same nodes in the queue, one with less distance but more stops and second with more distance but with less stops.
        //  pq will pick first node.
        // we might not able to reach destination with first node due to more stops.
        // later when we take second node from queue, we will reject because it has more distance.

        // so we take node with less stops first. so we sort by stops. so we use queue (we will add node in increasing stops manner(level by level)  )

        // then what about the case when in above case  first node lead to result rather then second node?
        // we will fail if we keep taking distance from distance array.
        // assume 0--(5)-->1, 0--(1)-->3,  3--(2)-->1, ....
        // we assume that 0->3->1 will lead to ans.

        // in above graph we will 0 then 1
        // now in distance array we have 0(0),1(5)
        // explore 3
        // 0(0),1(5),3(1)
        //explore 1
        // 0(0),1(5),3(1) , we explored graph connected to 1. got some ans.
        // explore 3
        // now here if we take distance from distance array then we will fail because distance array says 1 is reachable in 5 price.
        // but we have better path where we can reach in 3 price but with the more stops.

        // but if we take distance from the path which we are exploring then we will update the node 1 with price 3
        //  0(0),1(3),3(1)
        // now we push node-1 in queue and explore graph connected to it.
        // we already have condition which stops further exploration if stops exceed k.

        pq.add(new Node(src,0,-1));
        distance[src]=0;
        while (!pq.isEmpty()){
                Node currNode=pq.poll();
                int node=currNode.des;
                int wt=currNode.wt;
                int stops=currNode.stops;
                if(stops>=k)continue;  //if current node is at k stops then we are at limit now (that why =)
                for(Pair adjNode:adj.get(node)){
                    int adjWt=adjNode.wt;
                    if(stops <= k &&  adjWt + wt < distance[adjNode.des]){
                        distance[adjNode.des]=adjWt+wt;
                        pq.add(new Node(adjNode.des,distance[adjNode.des],stops+1));
                    }
                }
        }
        return  distance[dst]==(int)1e9?-1:distance[dst];

    }
}
