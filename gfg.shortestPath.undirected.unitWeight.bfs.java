class Solution {

    public int[] shortestPath(int[][] edges,int n,int m ,int src) {

        ArrayList<ArrayList<Integer>> adj=new ArrayList<>();
        for(int i=0;i<n;i++){
            adj.add(new ArrayList<>());
        }
        for(int i=0;i<edges.length;i++){
            // since this is undirected graph we have edge both the way.
                adj.get(edges[i][0]).add(edges[i][1]);
                adj.get(edges[i][1]).add(edges[i][0]);
        }
        // we can not use topo sort because graph is undirected.
        // since we have unit weight we can apply bfs from source node.
        // at every level we have distance will increase by 1.
        Queue<Integer> q=new ArrayDeque<>();
        int dis[]=new int[n];
        boolean visited[]=new boolean[n];

        Arrays.fill(dis,(int)1e9); // max value cannot be used. max_value + 1 becomes -ve value.
        dis[src]=0; //distance from source to source is 0.
        q.add(src);
        visited[src]=true;
        while (!q.isEmpty()){
            int node=q.poll();
            for(Integer adjNode:adj.get(node)){
                    if(!visited[adjNode]){
                        visited[adjNode]=true;
                        dis[adjNode]=dis[node]+1;
                        q.add(adjNode);
                    }
            }
        }
        for(int i=0;i<n;i++){
            if(dis[i]==(int)(1e9))dis[i]=-1; //unreachable node will have -1.
        }
        return  dis;
    }
}
