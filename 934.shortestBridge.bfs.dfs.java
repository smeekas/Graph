import java.util.ArrayDeque;
import java.util.Queue;

class Solution {
    int xd[]={0,0,1,-1};
    int yd[]={1,-1,0,0};
    public int shortestBridge(int[][] grid) {
        // idea is very simple
        //  first we mark water cells as -1.
        // since there are only 2 islands. we explore only one island and mark it as 2.
        // then we do bfs from all cell marked as 2.
        // logic is from the border of the already visited island we try to reach unvisited island.
        // this will give us shortest bridge between two island.
        int n=grid.length;
        int m=grid[0].length;
        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                if(grid[i][j]==0)grid[i][j]=-1;
            }
        }

        boolean stop=false;
        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                if(grid[i][j]==1){
                    grid[i][j]=2;
                    dfs(i,j,grid,n,m);
                    //once we find first island we want to stop finding second island.
                    stop=true;
                }
                if(stop)break;
            }
            if(stop)break;
        }

        Queue<Pair> q=new ArrayDeque<>();
        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                // reason of adding initial distance as 2 is....
                // our second island is marked as 1.
                // if we have initial distance as 0 then we will have distance 1.
                // this is conflict.
                // so island is marked as 1 and we have initial distance as 2. so no conflict (we will subtract 2 from final answer.)
                if(grid[i][j]==2)q.add(new Pair(i,j,2));
                // add all visited island cells in queue.
            }
        }
        // although we are adding all cells (marked as 2) in queue, we will be traversing on water cells only.
        while (!q.isEmpty()){
            Pair node=q.poll();
            for(int i=0;i<4;i++){
                int nx= node.x+xd[i];
                int ny= node.y+yd[i];
                if(isValid(nx,ny,n,m)){
                    // if cell is valid
                    if(grid[nx][ny]==-1){
                        // if water cell
                    grid[nx][ny]=node.distance+1;
                    q.add(new Pair(nx,ny,grid[nx][ny]));
                    }else if (grid[nx][ny]==1){
                        // if we reached second island
                        return  node.distance-2;
                    }
                }
            }
        }

        return  0;
    }
    // border if cell is invalid or cell is not adjacent to all land
    void dfs(int x, int y, int [][]grid,int n,int m){
        for(int k=0;k<4;k++){
            int nx=x+xd[k];
            int ny=y+yd[k];
            if(isValid(nx,ny,n,m) && grid[nx][ny]==1){
                    grid[nx][ny]=2;
                    dfs(nx,ny,grid,n,m);
            }
        }
    }
    boolean isValid(int x,int y,int n,int m){
        return  x>=0 && y>=0 && x<n && y<m;
    }
}
class Pair{
    int x,y,distance;
    Pair(int x,int y,int distance){
        this.x=x;
        this.y=y;
        this.distance=distance;
    }
}
