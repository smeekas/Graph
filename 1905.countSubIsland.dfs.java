class Solution {
    int[] xd ={0,0,1,-1};
    int[] yd ={1,-1,0,0};
    public int countSubIslands(int[][] grid1, int[][] grid2) {
        // naive approach is do dfs in grid2.
        //build  list of list which contain all island coordinates. {island1, island2,...};
        // in every island, for every cell we check if g1 has land or not.if g1's land count do not match g2's land count in island then island is invalid.

        int n=grid2.length;
        int m=grid2[0].length;
        int ans=0;
        // idea is we do dfs/bfs, and if cell is land in grid 2 then we check in grid 1.
        // if both are land then we continue search and mark cell as 0.
        // even if cell is not valid we finish searching that component.
        // logic behind this is that if we return false for invalid island as soon as we found them,
        // we might face invalid results in the future iteration.
        // because some cells of invalid island is still 1.
        // so we iterate invalid island and mark 0. and return false for invalid island.
        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                if(grid2[i][j]==1){
                    if(grid1[i][j]==1){
                        //for cell(i,j) in g2 which is land , if cell in g1 is land.

                    grid2[i][j]=0;
                    // mark cell(i,j).
                    if(dfs(grid1,grid2,i,j,n,m)==true){
                      // if island turns out to be valid.
                        // increase count.
                    ans+=1;
                    }
                    }
                }
            }
        }
        return  ans;
    }
    boolean dfs(int[][]g1,int [][]g2,int x,int y,int n,int m){
        boolean result=true;
        // variable to keep track of result.
        // by default, we assume island is valid.
        // if island will be marked as invalid if it is invalid.
        for(int k=0;k<4;k++){
            int nx=x+xd[k];
            int ny=y+yd[k];
            if(isValid(nx,ny,n,m)){
                if(g2[nx][ny]==1){
                    // if cell in g2 is land...
                    if(g1[nx][ny]==1){
                        // if cell in g1 is also land...
                        g2[nx][ny]=0;
                        // mark cell in g2.
                        // do iteration in child nodes
                        if(dfs(g1,g2,nx,ny,n,m)==false){
                            // if child says that island which we are exploring is invalid then change the result instead of returning.
                            result=false;
                        }

                    }else {
                        // for cell in g2 which is land, cell in g1 is water. again island is invalid.
                        // so mark island as invalid.
                        result= false;
                    }
                }
            }
        }
        return  result;
    }
    boolean isValid(int x,int y,int n,int m){
        return  x>=0 && y>=0 && x<n && y<m;
    }
}
