import java.util.Arrays;
import java.util.PriorityQueue;

class Solution {
    int xd[]={0,0,1,-1};
    int yd[]={1,-1,0,0};
    int LARGE=(int)1e8;

    public int swimInWater(int[][] grid) {

        // simple dijkstra
        // if adjacent node's height is higher than current time. then we have to wait for that difference.
        // here waiting will incur cost. if height is lower or same then we can move freely.
        PriorityQueue<Pair> pq=new PriorityQueue<>((a,b)->a.wt-b.wt);
        int n=grid.length,m=grid[0].length;
        int best[][]=new int[n][m];
        for(int[]row:best) Arrays.fill(row,LARGE);
        pq.add(new Pair(0,0,grid[0][0]));
        best[0][0]=grid[0][0];

        while (!pq.isEmpty()){
            Pair node=pq.poll();
            for(int i=0;i<xd.length;i++){
                int nx=node.x+xd[i];
                int ny=node.y+yd[i];
                if(isValid(nx,ny,n,m)){
                    if(node.wt>=grid[nx][ny]){
                        // if adj node's height is lower than current time
                        // we can move freely. so no cost will be incurred.
                        if(node.wt <best[nx][ny]){
                            best[nx][ny]= node.wt;
                            pq.add(new Pair(nx,ny, node.wt));
                        }
                    }else{
                        // adj node's height is higher than current time
                        // we have to wait for time to be >=adj node's height/
                        int newWt=node.wt+ grid[nx][ny]-node.wt;
                        if(newWt<best[nx][ny]){
                            best[nx][ny]=newWt;
                            pq.add(new Pair(nx,ny, newWt));
                        }
                    }
                }
            }
        }
        return  best[n-1][m-1];
    }
    boolean isValid(int x,int y,int n,int m){
        return  x>=0 && y>=0 && x<n && y<m;
    }
}
class Pair{
    int x,y,wt;

    public Pair(int x, int y, int wt) {
        this.x = x;
        this.y = y;
        this.wt = wt;
    }
}
