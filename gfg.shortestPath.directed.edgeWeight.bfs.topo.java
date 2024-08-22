class Pair{
    int des,wt;
    Pair(int des,int wt){
        this.des=des;
        this.wt=wt;
    }
}
class Solution {

    public int[] shortestPath(int N,int M, int[][] edges) {
        ArrayList<ArrayList<Pair>> adj=new ArrayList<>();
        for(int i=0;i<N;i++){
            adj.add(new ArrayList<>());
        }
        for(int i=0;i<M;i++){
            // constructing graph with edge weights.
            adj.get(edges[i][0]).add(new Pair(edges[i][1],edges[i][2]));
        }

        // we can utilize topo sort here.
        // source node is 0.
        // in topo sort left node have 0 dependency.
        // adjacent node will appear on right side.
        
        ArrayList<Integer> topo=getTopo(adj,N);

        int distance[]=new int[N];
        Arrays.fill(distance,(int)1e9); // max_value + 1 become -ve. so we use 1e9 (any large integer)
        distance[0]=0;

        while (!topo.isEmpty()){
            int node=topo.remove(0);
            //we take left most node first.
            for(Pair adjNodes:adj.get(node)){
                // we update adjacent node of current node.
                if(distance[node]+adjNodes.wt<distance[adjNodes.des]){
                    // if distance from src to current node + distance to adjacent node is lesser than distance from src to adjacent node directly then 
                    // we have new lesser distance for adjacent node.
                    distance[adjNodes.des]=distance[node]+adjNodes.wt;
                }
                // because of topo sort, we update adjacent node which are not been considered yet.
                // after current node is finished, we will take adjacent node in future iteration.
            }

        }
        for(int i=0;i<N;i++){
            if(distance[i]==(int)(1e9))distance[i]=-1; // if node is not reachable then -1.
        }
        return  distance;
    }
    ArrayList<Integer> getTopo(ArrayList<ArrayList<Pair>> adj,int V){
        ArrayList<Integer> ans=new ArrayList<>();
        Queue<Integer> q=new ArrayDeque<>();
        int[] indeg=new int[V];
        Arrays.fill(indeg,0);
        for(ArrayList<Pair> vertexes:adj){
            for (Pair node:vertexes){
                indeg[node.des]++;
            }
        }
        for(int i=0;i<indeg.length;i++){
            if(indeg[i]==0){
                q.add(i);
            }
        }
        while (!q.isEmpty()){
            Integer node=q.poll();
            ans.add(node);
            for(Pair adjNode:adj.get(node)){
                indeg[adjNode.des]--;
                if(indeg[adjNode.des]==0)q.add(adjNode.des);
            }
        }
        return  ans;
    }
}
