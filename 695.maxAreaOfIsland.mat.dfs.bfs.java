class Pair{
    int x, y;
    Pair(int x,int y){
        this.x=x;
        this.y=y;
    }
}
class Solution {
    int xd[]={0,0,1,-1};
    int yd[]={1,-1,0,0};
    public int maxAreaOfIsland(int[][] grid) {
        //we count number of nodes in every component.
//        return  dfs(grid);
        return  bfs(grid);
    }
    int bfs(int[][] grid){
        int n=grid.length,m=grid[0].length;
        boolean[][] visited=new boolean[n][m];
        Queue<Pair> q=new ArrayDeque<>();
        int maxAns=0;
        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                if(visited[i][j]==true||grid[i][j]==0)continue;
                // if node is already visited or it is water cell then go to next iteration.
                int localAns=1; 
                // localAns represents area of current island. 1 for initial cell.
                q.add(new Pair(i,j));
                visited[i][j]=true;
                while (!q.isEmpty()){
                    Pair node=q.poll();
                    for(int k=0;k<4;k++){
                            int nx=node.x+xd[k];
                            int ny=node.y+yd[k];
                            if(isValid(nx,ny,n,m) && grid[nx][ny]==1 && visited[nx][ny]==false){
                                visited[nx][ny]=true;
                                localAns++;
                                q.add(new Pair(nx,ny));
                            }
                    }
                }
                maxAns=Math.max(maxAns,localAns);
            }
        }
        return  maxAns;

    }
    int dfs(int [][]grid){
        int n=grid.length,m=grid[0].length;
        boolean[][] visited=new boolean[n][m];
        int maxArea=0;
        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                if(visited[i][j]==false && grid[i][j]==1){
                    // if node is not visited & it is land
                    visited[i][j]=true;
                    maxArea=Math.max(maxArea,dfsHelper(i,j,grid,visited,n,m));
                }
            }
        }
        return  maxArea;
    }
    int dfsHelper(int x,int y,int [][]grid,boolean[][]visited,int n,int m){
        int area=0;
        for(int i=0;i<4;i++){
            int nx=x+xd[i];
            int ny=y+yd[i];
            if(isValid(nx,ny,n,m) && visited[nx][ny]==false && grid[nx][ny]==1){
                    visited[nx][ny]=true;
                   area+= dfsHelper(nx,ny,grid,visited,n,m);
            }
        }
        // area represent all child node's area. +1 for current node's area.
        return  area+1;
    }
    boolean isValid(int r,int c,int n,int m){
        return  r>=0 && c>=0 && r<n && c<m;
    }
}
