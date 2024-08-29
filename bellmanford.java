class Solution {
    static int[] bellman_ford(int V, ArrayList<ArrayList<Integer>> edges, int S) {
	
        int distance[]=new int[V];
        Arrays.fill(distance,(int)1e8);
        int n=V-1;
        distance[S]=0;
        
        // shortest path can have n-1 node max.
        // idea is we do relaxation n-1 times.
        // after that we must have shortest path.
        // if even after that if we try to do relaxation and it does that then it means we have negative cycle.
        
        
        
        while (n-->0){
            for(int i=0;i<edges.size();i++){
                int src=edges.get(i).get(0);
                int des=edges.get(i).get(1);
                int wt=edges.get(i).get(2);
                // we don't do relaxation if src is infinity.
                // if src is infinity then infinity + anything ==infinity.
                if(distance[src]!=1e8 &&    distance[src]+wt<distance[des]){
                    distance[des]=distance[src]+wt;
                }
            }
        }
        for(int i=0;i<edges.size();i++){
            int src=edges.get(i).get(0);
            int des=edges.get(i).get(1);
            int wt=edges.get(i).get(2);
             if(distance[src]!=1e8 && distance[src]+wt<distance[des]){
                return  new int[]{-1};
            }
        }
        return  distance;
    }
}

