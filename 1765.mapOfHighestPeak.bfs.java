import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;

class Solution {
    int xd[]={0,0,1,-1};
    int yd[]={1,-1,0,0};
    public int[][] highestPeak(int[][] isWater) {
        // if you look carefully.....
        // this is multi-source bfs!
        // start with all water cell
        // since bfs go level by level. in every level distance will be increase by 1.
        // since we will start with multiple sources, if node is visited by one source then other node will not visit it.
        // this way every source will maximize height level by level..
        Queue<Pair> q=new ArrayDeque<>();
        int n=isWater.length;
        int m=isWater[0].length;
        int ans[][]=new int[n][m];
        for(int r[]:ans){
            Arrays.fill(r,-1);
        }
        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                if(isWater[i][j]==1){
                    ans[i][j]=0;
                    // collecting all sources
                    q.add(new Pair(i,j,0));
                }
            }
        }
        while (!q.isEmpty()){
            Pair node=q.poll();
            for(int k=0;k<4;k++){
                int nx=node.x+xd[k];
                int ny=node.y+yd[k];
                if(isValid(nx,ny,n,m) && isWater[nx][ny]==0 && ans[nx][ny]<0){
                    //if node is valid, and it is land(o) and not yet visited(<0)
                    ans[nx][ny]=node.dis+1; // mark as visited
                    q.add(new Pair(nx,ny,ans[nx][ny])); // add in queue for bfs
                }
            }
        }
        return ans;
    }
    boolean isValid(int x,int y,int n,int m){
        return  x>=0 && y>=0 && x<n && y<m;
    }
}
class Pair{
    int x, y, dis;
    Pair(int x,int y,int dis){
        this.dis=dis;
        this.y=y;
        this.x=x;
    }
}
