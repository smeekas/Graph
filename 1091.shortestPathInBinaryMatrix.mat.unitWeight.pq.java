import java.util.Arrays;
import java.util.PriorityQueue;

class Pair{
    int distance,x,y;
    Pair(int x,int y,int distance){
        this.x=x;
        this.y=y;
        this.distance=distance;
    }
}
class Solution {
    int xd[]=new int[]{0,0,1,-1,-1,-1,1,1};
    int yd[]=new int[]{1,-1,0,0,-1,1,-1,1};
    public int shortestPathBinaryMatrix(int[][] grid) {
        // from 0,0 to n-1,n-1.
        // path with 0 only. 
        
        
        int n=grid.length;
        if(grid[0][0]==1)return  -1; // if source have block then return -1. 
        int result[][]=new int[n][n];
        for(int i[]:result){
            Arrays.fill(i,(int)(1e9));
        }
        PriorityQueue<Pair> pq=new PriorityQueue<>((a, b)->a.distance-b.distance);
        pq.add(new Pair(0,0,1));
        result[0][0]=1;
        // src is taken up so add it into result.
        while (!pq.isEmpty()){
            
            Pair node=pq.poll();
            
            for(int i=0;i<xd.length;i++){
                // we can travel 8-directionally.
                int nx=node.x+xd[i];
                int ny=node.y+yd[i];
                if(isValid(nx,ny,n) && grid[nx][ny]==0){
                    // if node is valid & it is valid node (0 not 1)
                    int distance=node.distance+1; // our new distance to new node.
                    if(distance<result[nx][ny]){
                        result[nx][ny]=distance;
                        pq.add(new Pair(nx,ny,distance));
                    }
                }
            }
        }
        // if destination is 1e9 means it is not reachable.
        return  result[n-1][n-1]==(int)(1e9)?-1:result[n-1][n-1];
    }
    boolean isValid(int x,int y,int n){
        return  x>=0 && y>=0 && x<n && y<n;
    }
}
