import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Stack;

class Solution {
    int xd[]={0,1};
    int yd[]={1,0};

    public boolean isPossibleToCutPath(int[][] grid) {
        int n=grid.length,m=grid[0].length;
        //DIAGONAL WAY HOW?
        // first we try to find the shortest path
        // after that we remove this shortest path
        // even after that if we can reach destination, that means answer is not possible
        // else answer is possible

        // since we want nodes which are on path, we use parent array
        Pair [][]parents=new Pair[n][m];

        if(bfs(grid,n,m,parents)==false){
            // if path is not found that means graph is disconnected
            // so return true
            return true;
        }
        Pair currNode=new Pair(n-1,m-1);
        // start from destination, traverse their parents
        while (currNode!=null){
            grid[currNode.x][currNode.y]=0;
            currNode=parents[currNode.x][currNode.y];


        }
        grid[0][0]=1; // start and end node should not be 0
        grid[n-1][m-1]=1;
        return bfs(grid,n,m,parents)==false;
        // is path is not found, then return true.

    }
    // true means path found
    boolean bfs(int [][]grid,int n,int m,Pair parents[][]){
        boolean visited[][]=new boolean[n][m];

        Queue<Pair> q=new ArrayDeque<>();
        q.add(new Pair(0,0));
        visited[0][0]=true;

        parents[0][0]=null;
        while (!q.isEmpty()){
            Pair node=q.poll();
            if(node.x==n-1 && node.y==m-1)return true;
            for(int i=0;i<xd.length;i++){
                int nx=node.x+xd[i];
                int ny=node.y+yd[i];
                if(isValid(nx,ny,n,m) && grid[nx][ny]==1 && visited[nx][ny]==false){

                    visited[nx][ny]=true;
                    parents[nx][ny]=node;
                    q.add(new Pair(nx,ny));
                }
            }
        }

        return  false;
    }
    boolean isValid(int x,int y,int n,int m){
        return  x>=0 && y>=0 && x<n && y<m;
    }
}
class Pair{
    int x,y;
    Pair(int x,int y){
        this.x=x;
        this.y=y;
    }
}
