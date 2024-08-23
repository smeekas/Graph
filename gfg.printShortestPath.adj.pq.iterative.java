class Pair{
    int wt;
    int des;
    Pair(int des,int wt){
        this.des=des;
        this.wt=wt;
    }
}
class Solution {

    // instead of printing minimum distance from src to node we want path.
    public List<Integer> shortestPath(int n, int m, int edges[][]) {

        // if path exists then return list with {distance-to-n, ...path from 1 to n}
        //else {-1}
        // if we path actual shorted path then we have to just have to modify distance array
        // In distance array we can store distance as well as parent (from where we got shorted path.)
        ArrayList<ArrayList<Pair>> adj=new ArrayList<>();
        for(int i=0;i<=n;i++){
            adj.add(new ArrayList<>());
        }
        for(int i=0;i<m;i++){
            int src=edges[i][0];
            int des=edges[i][1];
            int wt=edges[i][2];
            adj.get(src).add(new Pair(des,wt));
            adj.get(des).add(new Pair(src,wt));
        }
        PriorityQueue<Pair> pq=new PriorityQueue<>((a,b)->a.wt-b.wt);

        int distance[][]=new int[n+1][2];  //{0->distance, 1->parent}
        for(int i=0;i<=n;i++){
            distance[i]=new int[]{(int)1e9,-1};  //initially distance is infinity and parent is -1.
        }

        distance[1]=new int[]{0,-1}; // our source node is 1. so mark distance as 0.

        pq.add(new Pair(1,0));  //src & distance

        while (!pq.isEmpty()){
            Pair node=pq.poll();
            for (Pair adjNode:adj.get(node.des)){

                int distanceToNode=distance[node.des][0];
                int wt=adjNode.wt;
                int adjDistance=distance[adjNode.des][0];

                if(distanceToNode+wt<adjDistance){
                    distance[adjNode.des][0]=distanceToNode+wt; //distance
                    distance[adjNode.des][1]=node.des; // parent
                    pq.add(new Pair(adjNode.des,distanceToNode+wt));
                }
            }
        }

        ArrayList<Integer> ans=new ArrayList<>();
        if(distance[n][0]==(int)1e9){
            // if final node's distance is 1e9 means it is unreachable
            ans.add(-1);
            return  ans;
        }

        // we start with n.
        // from n we go to its parent then parent's parent etc...
        int element=n;
        int i=0;
        // i is used because path cannot contain more node than total nodes.
        while (i<n && element!=-1){ // go till element become -1.

            ans.add(0,element); // add node
            element=distance[element][1]; // go to node's parent
            i++;
        }

        ans.add(0,distance[n][0]); //add total distance in the beginning .
        return  ans;
    }
}
