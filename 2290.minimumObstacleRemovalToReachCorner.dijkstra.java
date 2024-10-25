import java.util.Arrays;
import java.util.PriorityQueue;

class Solution {
    int xd[]={0,0,1,-1};
    int yd[]={1,-1,0,0};
    int LARGE=(int)1e8;

    public int minimumObstacles(int[][] grid) {
        
        // we apply normal dijkstra only
        // if we find obstacle in the path then that will incur cost
        int n=grid.length,m=grid[0].length;
        PriorityQueue<Pair> pq=new PriorityQueue<>((a,b)->a.wt-b.wt);
        pq.add(new Pair(0,0,0));
        int best[][]=new int[n][m];
        for(int[]row:best) Arrays.fill(row,LARGE);
        best[0][0]=grid[0][0];
        
        while (!pq.isEmpty()){
            Pair node=pq.poll();
            if(node.x==n-1 && node.y==m-1)return node.wt;
            for(int i=0;i<xd.length;i++){
                int nx=node.x+xd[i];
                int ny=node.y+yd[i];
                if(isValid(nx,ny,n,m)){
                    if(grid[nx][ny]==1){
                        // 1 means obstacle, this will incur cost
                        if(node.wt+1< best[nx][ny]){
                            best[nx][ny]= node.wt+1;
                            pq.add(new Pair(nx,ny,node.wt+1));
                        }
                    }else{
                        // 0 means no obstacle, we can explore with current weight only
                        if(node.wt< best[nx][ny]){
                            best[nx][ny]= node.wt;
                            pq.add(new Pair(nx,ny,node.wt));
                        }
                    }
                }
            }
        }
        return 0;
    }
    boolean isValid(int x,int y,int n,int m){
        return  x>=0 && y>=0 && x<n && y<m;
    }
}
class  Pair{
    int x,y,wt;
    Pair(int x,int y,int wt){
        this.x=x;
        this.y=y;
        this.wt=wt;
    }
}
