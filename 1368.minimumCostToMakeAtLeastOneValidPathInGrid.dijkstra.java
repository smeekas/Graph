import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Queue;

class Solution {
    int LARGE=(int)1e8;

    int xd[]={0,0,1,-1};
    int yd[]={1,-1,0,0};
    // RIGHT, LEFT, BOTTOM, UP
    public int minCost(int[][] grid) {
        // 1 RIGHT
        // 2 LEFT
        // 3 BOTTOM
        // 4 UP
        PriorityQueue<Pair> pq=new PriorityQueue<>((a,b)->a.wt-b.wt);

        // here we apply dijkstra. if we have to change sign then that will be counted in cost.
        pq.add(new Pair(0,0,0));
        int n=grid.length,m=grid[0].length;
        int best[][]=new int[n][m];
        for(int[]row:best) Arrays.fill(row,LARGE);
        best[0][0]=0;
        while (!pq.isEmpty()){
            Pair node=pq.poll();
            if(node.x==n-1 && node.y==m-1)return node.wt;

            for(int i=0;i<xd.length;i++){
                int nx= node.x+xd[i];
                int ny= node.y+yd[i];
                if(isValid(nx,ny,n,m)){
                    if(matched(i,grid[node.x][node.y])){
                        // if parent's sign is pointing toward this child...
                        // cost will not be increased
                        if(node.wt<best[nx][ny]){
                            best[nx][ny]=node.wt;
                            pq.add(new Pair(nx,ny,node.wt));
                        }
                    }else{
                        // parent's sign is not pointing toward this child so we need to include in our cost
                        if(node.wt+1< best[nx][ny]){
                            best[nx][ny]=node.wt+1;
                            pq.add(new Pair(nx,ny,node.wt+1));
                        }
                    }
                }
            }
        }
        return -1;

    }
    boolean matched(int index,int gridValue){
        // index=> our xd, yd array
        // gridValue is number which is present in grid.
        return  index==gridValue-1;
    }
    boolean isValid(int x,int y,int n,int m){
        return  x>=0 && y>=0 && x<n && y<m;
    }
}
class Pair{
    int x,y,wt;
    Pair(int x,int y,int wt){
        this.y=y;
        this.x=x;
        this.wt=wt;
    }
}
