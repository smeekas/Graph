class Solution {
    int xd[]={0,0,1,-1};
    int yd[]={1,-1,0,0};
    public boolean containsCycle(char[][] grid) {
        // just like undirected graph approach.
    
        int n=grid.length;
        int m=grid[0].length;
        boolean visited[][]=new boolean[n][m];
        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                if(visited[i][j]==false){
                    visited[i][j]=true;
                    // we need to use parent coordinates.
                    // so when children is exploring it do not explore parent.
                    if(dfs(grid,i,j,-1,-1,n,m,visited,grid[i][j])){
                        return  true;
                    }
                }
            }
        }
        return false;
    }
    boolean dfs(char[][]grid,int x,int y,int px,int py,int n,int m,boolean[][]vis,char color){

        for(int i=0;i<4;i++){
                int nx=x+xd[i];
                int ny=y+yd[i];
                if(isValid(nx,ny,n,m) && grid[nx][ny]==color){
                    // if child is valid and of same color....
                    if( (nx!=px|| ny!=py)  &&  vis[nx][ny]==true){
                        // if this child node is not parent and it is visited means we have a cycle !
                        // if either x coordinate or y coordinate is not same that menas it is not parent.
                        return  true;
                    }else if(vis[nx][ny]==false){
                        // if it is not visited then visit it and continue search.
                            vis[nx][ny]=true;
                            if(dfs(grid,nx,ny,x,y,n,m,vis,color)){
                                return  true;
                            }

                    }
                }
        }
        return  false;
    }
    boolean isValid(int x,int y,int n,int m){
            return  x>=0 && y>=0 && x<n && y<m;
    }
}
