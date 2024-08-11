class Solution {
    public int findCircleNum(int[][] isConnected) {
        // just like number of island but here matrix is adj matrix.(like adj list)
        int ans=0;
        boolean[]visited=new boolean[isConnected.length];
        // we will do dft (traversal)
        for(int i=0;i<isConnected.length;i++){
            if(!visited[i]){
                // if some vertex is visited then we will skip them.
                // if some vertex is not visited rven after previous dfs, that mean that vertex is not connected
                ans++;
                visited[i]=true;
                // we will do dfs of graph with vertex-i.
                dfs(i,isConnected,visited);
            }
        }
        return  ans;
    }

void dfs(int vertex,int[][] matrix,boolean[] visited){


    for (int newVertex=0;newVertex<matrix.length;newVertex++){
        // do dfs on adj vertexes of current vertex.
        if(!visited[newVertex] && newVertex!=vertex && matrix[vertex][newVertex]!=0){
            // it should not have been visited, cannot be same as current vertex, it must be 1 (edge exists)
            visited[newVertex]=true;
            dfs(newVertex,matrix,visited);
        }
    }
}

}
