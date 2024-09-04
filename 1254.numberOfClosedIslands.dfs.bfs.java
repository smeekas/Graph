import java.util.ArrayDeque;
import java.util.Queue;

class Solution {
    int xd[]={0,0,1,-1};
    int yd[]={1,-1,0,0};
    public int closedIsland(int[][] grid) {
        int n=grid.length;
        int m=grid[0].length;
        // first we do traversal from all edge zeros and mark them as -1.
        // so now we have islands (0s) which is surrounded by 1s.
        // so we do traversal on 0s and count the number of island!
    for(int i=0;i<n;i++){
        //2 columns left, right
        if(grid[i][0]==0) {
            grid[i][0]=-1;
            dfs(i, 0, grid, n, m);
        }
        if(grid[i][m-1]==0) {
            grid[i][m-1]=-1;
            dfs(i, m - 1, grid, n, m);
        }
    }

        for(int i=0;i<m;i++){
            //2 rows top, bottom
            if(grid[0][i]==0) {
                grid[0][i]=-1;
                dfs(0, i, grid, n, m);
            }
            if(grid[n-1][i]==0) {
                grid[n-1][i]=-1;
                dfs(n - 1, i, grid, n, m);
            }
        }
        int ans=0;
        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                if(grid[i][j]==0){


                Queue<Pair> q=new ArrayDeque<>();
                    ans++;
                    q.add(new Pair(i,j));
                    while (!q.isEmpty()){
                        Pair node=q.poll();
                        for(int k=0;k<4;k++){
                            int nx=node.x+xd[k];
                            int ny=node.y+yd[k];
                            if(isValid(nx,ny,n,m) && grid[nx][ny]==0){
                                grid[nx][ny]=-1;
                                q.add(new Pair(nx,ny));
                            }
                        }
                    }
                }
            }
        }
        return  ans;



    }
    void dfs(int x,int y,int[][]grid,int n,int m){
            for(int i=0;i<4;i++){
                int nx=x+xd[i];
                int ny=y+yd[i];
                if(isValid(nx,ny,n,m) && grid[nx][ny]==0){
                    grid[nx][ny]=-1;
                        dfs(nx,ny,grid,n,m);
                }
            }
    }
    boolean isValid(int x,int y,int n,int m){
        return  x>=0 && y>=0 && x<n && y<m;
    }
}
class  Pair{
    int x, y;
    Pair(int x,int y){
        this.x=x;
        this.y=y;
    }
}
