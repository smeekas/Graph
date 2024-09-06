class Solution {
    int[][][] dir={{{0,-1},{0,1}},
            {{-1,0},{1,0}}
            ,{{0,-1},{1,0}}
            ,{{0,1},{1,0}}
            ,{{-1,0},{0,-1}}
            ,{{-1,0},{0,1}}
    };
    public boolean hasValidPath(int[][] grid) {
        if(true)return upscaling(grid);
        int n=grid.length,m=grid[0].length;
        boolean visited[][]=new boolean[n][m];
        return  dfs(0,0,-1,-1,grid,n,m,visited);
    }
    // approach-1
    boolean dfs(int x,int y,int px,int py,int [][]grid,int n,int m,boolean[][]vis){
        //for every type of tile there are to ways to leave tile. which we have described in dirs array. ({row,col} wise)
        //if we exit tile that does not mean we can successfully exit it.
        // for ex. street 1, street 2  are adjacent tile
        // street 1 says that we can go to  street 2.
        // but we know that this is invalid.
        // so we try to come back from street 2. if we can then that means our move from street 1 to street 2 was valid and we can continue.
            int [][] directions=dir[grid[x][y]-1]; // directions of particular street.
            if(x==n-1 && y==m-1)return  true; //if we reached destination cell then valid path exists.
            for(int []dirs:directions){

                    int nx=dirs[0]+x;
                    int ny=dirs[1]+y;
                    // {nx,ny} new cell as per the exit specified by {x,y}
                    if(isValid(nx,ny,n,m) && (nx!=px || ny!=py) && vis[nx][ny]==false){
                        // if cell is valid and it is not parent(so we don't go into infinite loop) and it is not visited...
                        for(int[] backDir:dir[grid[nx][ny]-1]){ // // directions of new street.
                            //if we can reach {x,y} from {nx,ny}
                            if(backDir[0]+nx==x && backDir[1]+ny==y){
                                // that means our move from {x,y} to {nx,ny} was valid.
                                vis[nx][ny]=true;
                                if( dfs(nx,ny,x,y,grid, n,m,vis)==true){
                                    return  true;
                                }
                            }
                        }

                    }
        }
            return  false;
    }
    boolean isValid(int x,int y,int n,int m){
        return  x>=0 && y>=0 && x<n && y<m;
    }
    int xd[]={0,0,1,-1};
    int yd[]={1,-1,0,0};
    // approach-2
    boolean upscaling(int[][]grid){
        // what if we upscale grid by 3x
        // so every street becomes {x1,y1},{x2,y2},{x3,y3};
        int n=grid.length,m=grid[0].length;
        int g[][]=new int[n*3][m*3];
        for(int []row:g){
            Arrays.fill(row,0);
        }
        boolean visited[][]=new  boolean[n*3][m*3];
        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                int number=grid[i][j];
                int up_i=i*3;
                int up_j=j*3;
                // for every cell in original grid, now we have 3x3 grid.
                // so as per the street style we fill this new super grid.
                // for cell {i,j} in original grid, {i*3,j*3} is {0,0}, {i*3+1,j*3+1} is {1,1}, {i*3+2,j*3+2} is {2,2}
                switch (number){
                    case  1:{
                        g[up_i+1][up_j]=1;
                        g[up_i+1][up_j+1]=1;
                        g[up_i+1][up_j+2]=1;
                        break;
                    }
                    case  2:{
                        g[up_i][up_j+1]=1;
                        g[up_i+1][up_j+1]=1;
                        g[up_i+2][up_j+1]=1;
                        break;
                    }
                    case  3:{
                        g[up_i+1][up_j]=1;
                        g[up_i+1][up_j+1]=1;
                        g[up_i+2][up_j+1]=1;
                        break;
                    }
                    case  4:{
                        g[up_i+1][up_j+2]=1;
                        g[up_i+1][up_j+1]=1;
                        g[up_i+2][up_j+1]=1;
                        break;
                    }
                    case  5:{
                        g[up_i][up_j+1]=1;
                        g[up_i+1][up_j+1]=1;
                        g[up_i+1][up_j]=1;
                        break;
                    }
                    case  6:{
                        g[up_i][up_j+1]=1;
                        g[up_i+1][up_j+1]=1;
                        g[up_i+1][up_j+2]=1;
                        break;
                    }
                }
            }
        }
        // now we just need to do normal dfs.
        // in original grid we start with {0,0} cell. for that we have 3x3 cell in new grid.
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                if(g[i][j]==1){
                    // if cell is street and not visited then start visiting it.
                    if(visited[i][j]==false){
                        visited[i][j]=true;
                        if(upscaledDfs(i,j,g,visited,n*3,m*3)){
                            return  true;
                        }
                    }
                }
            }
        }
        return  false;
    }
    boolean upscaledDfs(int i, int j,int [][]grid,boolean visited[][],int n,int m){
        if(i>=n-3 && j>=m-3)return  true; // if cell reaches last cell according to original grid that means we succeed.
        //for 2x3 original grid we have 6x9 super grid.
        //so if cell reaches {3,4,5}(i),{6,7,8}(j) we can say that our cell is in last cell according to original grid.
        for(int k=0;k<4;k++){
            int nx=i+xd[k];
            int ny=j+yd[k];
            if(isValid(nx,ny,n,m) && visited[nx][ny]==false && grid[nx][ny]==1){
                // if new cell is valid and not visited and street then we visit it.
                visited[nx][ny]=true;
                if(upscaledDfs(nx,ny,grid,visited,n,m))return  true;
            }
        }
        return  false;
    }

}
