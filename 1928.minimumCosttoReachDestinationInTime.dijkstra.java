import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;

class Solution {
    public int minCost(int maxTime, int[][] edges, int[] passingFees) {
        int n=passingFees.length;
        ArrayList<ArrayList<Pair>> adj=new ArrayList<>(n);
        for(int i=0;i<n;i++)adj.add(new ArrayList<>());
        for(int []edge:edges){
            int src=edge[0],des=edge[1],wt=edge[2];
            adj.get(src).add(new Pair(des,wt,-1));
            adj.get(des).add(new Pair(src,wt,-1));

        }
        // we cannot apply dijkstra on minimum time.
        // we may have two same nodes. node-x =>{min time, more cost}  node-y =>{more time, less cost}
        // dijkstra will pull node-x first. and in later iteration it will ignore node-y due to more time
        // but in reality, in future iterations, either node-x or node-y could have given us answer.

        // since we want to return minimum cost in answer & restriction is on time, let us minimize cost
        // we will do dijkstra and in the graph we try to minimize cost.
        // in the graph we track time too. if in some iteration, time overflows then we will not do further iterations.
        PriorityQueue<Pair> pq=new PriorityQueue<>((a,b)->a.fee-b.fee);
        pq.add(new Pair(0,0,passingFees[0]));
        int minTime[]=new int[n];
        int minPrice[]=new int[n];
        Arrays.fill(minPrice,(int)1e8);
        Arrays.fill(minTime,(int)1e8);
        minTime[0]=0;
        minPrice[0]=passingFees[0];
        while (!pq.isEmpty()){
            Pair node=pq.poll();
            int nodeT=node.time,nodeF=node.fee,nodeI=node.node;
            if(nodeT>maxTime)continue; // to reduce few iteration and save some time
            for(Pair adjNode:adj.get(node.node)){
                int adjI=adjNode.node;
                int adjT=adjNode.time;
                int adjF=passingFees[adjI];

                if(nodeT+adjT<=maxTime){  // first of all time should not be more than threshold.

                    if(nodeF+adjF<minPrice[adjI]){
                        // if cost is lower than current cost then update cost
                        // since we are moving to new node, calculate time to go to adjNode too.
                        minPrice[adjI]=nodeF+adjF;
                        minTime[adjI]=nodeT+adjT;
                        pq.add(new Pair(adjI,minTime[adjI],minPrice[adjI]));
                    }else{
                        // price is higher here.
                        // but this can also give use answer. so let's explore this else part
                        // here price is higher than our best price stored.
                        // here time is less than threshold.
                        if(nodeT+adjT<minTime[adjI]){
                            // if time is less than threshold ( which it is) and time is lesser than what currently is stored
                            // update time
                            minTime[adjI]=nodeT+adjT;
                            // we will not update cost here, as it is higher than what is stored. (that's why we are in else part)

                            // exlore adjNode with new time and more cost than what currently is stored
                            pq.add(new Pair(adjI,minTime[adjI],nodeF+adjF));
                        }
                    }
                }
            }
        }
        return minPrice[n-1]==1e8?-1:minPrice[n-1];
    }
}
class Pair{
    int node,time,fee;

    public Pair(int node, int time, int fee) {
        this.node = node;
        this.time = time;
        this.fee = fee;
    }
}
