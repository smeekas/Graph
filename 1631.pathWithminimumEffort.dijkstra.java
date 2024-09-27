import java.util.Arrays;
import java.util.PriorityQueue;

class Solution {
    int xd[]={1,-1,0,0};
    int yd[]={0,0,1,-1};
    public int minimumEffortPath(int[][] heights) {


        // we may have multiple ways to reach end from start
        // in each path we need to consider maximum difference
        // from all this differences we need to return minimum difference
        
        int n=heights.length,m=heights[0].length;
        PriorityQueue<Pair> pq=new PriorityQueue<>((a,b)->a.diff-b.diff);

        int minDis[][]=new int[n][m];

        for(int row[]:minDis) Arrays.fill(row,(int)(1e9));
        
        pq.add(new Pair(0,0,(int)0)); //starting node 0,0
            minDis[0][0]=(int)0;

        while (!pq.isEmpty()){
            Pair node=pq.poll();
            for(int i=0;i<xd.length;i++){
                int nx=node.x+xd[i];
                int ny=node.y+yd[i];
                if(isValid(nx,ny,n,m)){
                    int newDiff=Math.abs(heights[nx][ny]-heights[node.x][node.y]);  // this node's difference with parent node
                    int maxDiffSoFar=Math.max(newDiff,node.diff); //this path's maximum difference so far.
                    
                        if(maxDiffSoFar<minDis[nx][ny]){ 
                            // if this maximum difference is lower than current node's stored difference. 
                            pq.add(new Pair(nx,ny,maxDiffSoFar));
                            minDis[nx][ny]=maxDiffSoFar;
                        }

                }
            }
        }

        return  minDis[n-1][m-1];
    }
    boolean isValid(int x,int y,int n,int m){
        return  x>=0 && y>=0 && x<n && y<m;
    }
}
class Pair{
    int x,y,diff;
    Pair(int x,int y,int diff){
        this.x=x;
        this.y=y;
        this.diff=diff;
    }
}
