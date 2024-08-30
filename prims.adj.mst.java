class  Pair{
    int des,wt;
    Pair(int des,int wt){
        this.des=des;
        this.wt=wt;
    }
}

class Solution {
    static int spanningTree(int V, int E, List<List<int[]>> adj) {

        // logic behind this algorithm
        // from the node which we have visited we want to explore adjacent node which have the shortest distance.
        // we keep doing that until we cannot do. and we only explore adjacent node if it doesn't introduce cycle.
        

        ArrayList<ArrayList<Pair>> g=new ArrayList<>();
        for(int i=0;i<V;i++){
            g.add(new ArrayList<>());
        }
        for(int i=0;i<V;i++){
            int src=i;
            for(int[] adjVertexes:adj.get(src)){
                int des=adjVertexes[0];
                int wt=adjVertexes[1];
                g.get(src).add(new Pair(des,wt));
                g.get(des).add(new Pair(src,wt));
            }
        }
        
        boolean visited[]=new boolean[V];
        PriorityQueue<Pair> pq=new PriorityQueue<>((a, b)->a.wt-b.wt);
        pq.add(new Pair(0,0));
        // we have added 0. so we will mark this as visited and explore it's adjacent and add them in queue.
        int ans=0;
        while (!pq.isEmpty()){
            Pair node=pq.poll();
            if(visited[node.des])continue; // if visited then skip exploring node.
            // if not visited them mark as visited because we will be exploring this node's adjacent node and adding them in queue.
            visited[node.des]=true;
            
            ans+=node.wt;  //because we have taken this node from queue, and it didn't introduce cycle we will add weight to the ans.
            for(Pair adjNodes:g.get(node.des)){
                // explore adjacent node and add them in queue.
                // we will take node with minimum weight next.
                if(!visited[adjNodes.des])
                    pq.add(new Pair(adjNodes.des, adjNodes.wt));

            }
        }
        return  ans;
    }
}
