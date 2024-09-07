import java.util.ArrayDeque;
import java.util.Queue;

class Solution {
    int xd[]={0,0,1,-1};
    int yd[]={1,-1,0,0};
    public int maxDistance(int[][] grid) {
        //again multi source bfs.
        // same as 1765. highest peak
        int n=grid.length;
        int m=grid[0].length;
        Queue<Pair> q=new ArrayDeque<>();
        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                if(grid[i][j]==1)q.add(new Pair(i,j,0)); // collect all sources
            }
        }
        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                if(grid[i][j]==1)grid[i][j]=0;
                else if(grid[i][j]==0)grid[i][j]=-1;
                // if it is land then mark as 0.
                // if it is water then mark as -1 (not visited)
            }
        }

        int maxAns=-1;
        while (!q.isEmpty()){
            Pair node=q.poll();
            for(int i=0;i<4;i++){
                int nx=node.x+xd[i];
                int ny=node.y+yd[i];
                if(isValid(nx,ny,n,m)==true){
                    if(grid[nx][ny]==-1){
                        // not visited
                        grid[nx][ny]=node.dis+1;
                        maxAns=Math.max(maxAns,grid[nx][ny]);
                        q.add(new Pair(nx,ny,grid[nx][ny]));
                    }
                }
            }
        }

        return maxAns;
    }
    boolean isValid(int x,int y,int n,int m){
        return  x>=0 && y>=0 && x<n && y<m;
    }
}
class Pair{
    int x,y,dis;
    Pair(int x,int y,int dis){
        this.dis=dis;
        this.x=x;
        this.y=y;
    }
}
