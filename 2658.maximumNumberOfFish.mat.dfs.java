class Solution {
    int xd[]= {0,0,-1,1};
    int yd[]= {1,-1,0,0};
    public int findMaxFish(int[][] grid) {
        //same as count area of island.
        // instead of 1 for every cell we have cell's value.
        int n=grid.length;
        int m=grid[0].length;
            boolean[][]visited=new boolean[n][m];
            int max=0;
            for(int i=0;i<n;i++){
                for(int j=0;j<m;j++){
                    if(grid[i][j]>0 && visited[i][j]==false){
                        visited[i][j]=true;
                    max=Math.max(dfs(i,j,grid,visited,n,m),max);
                    }
                }
            }
            return  max;
    }
    int dfs(int i,int j,int [][]grid,boolean[][]visited,int n,int m){
        int ans=0;
            for(int k=0;k<4;k++){
                int nx=i+xd[k];
                int ny=j+yd[k];
                if(isValid(nx,ny,n,m) && visited[nx][ny]==false && grid[nx][ny]>0){
                    visited[nx][ny]=true;
                    ans+=dfs(nx,ny,grid,visited,n,m);
                }
            }
            return  ans+=grid[i][j];
    }
    boolean isValid(int i,int j,int n,int m){
        return  i>=0 && j>=0 && i<n && j<m;
    }
}
