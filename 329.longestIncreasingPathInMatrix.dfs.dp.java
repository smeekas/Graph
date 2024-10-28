import java.util.Arrays;

class Solution {
    int xd[]={0,0,1,-1};
    int yd[]={1,-1,0,0};
    int LARGE=(int)1e8;
    public int longestIncreasingPath(int[][] matrix) {
        // we will try dfs to find the longest path from every cell.
        //for every cell we also store the longest path which it found.
        int n=matrix.length,m=matrix[0].length;
        int best[][]=new int[n][m];
        for(int[]row:best) Arrays.fill(row,-1);

        int bestAns=1;
        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                if(best[i][j]==-1){

                    bestAns=Math.max(bestAns,dfs(matrix,best,i,j,n,m));
                }
            }
        }
        return bestAns;
    }
    int dfs(int [][]grid,int [][]best,int x,int y,int n,int m){
        int max=1;  //current node.
        if(best[x][y]!=-1)return  best[x][y]; // if matrix has stored the longest path from current node, just use it.
        
        for(int i=0;i<xd.length;i++){
            int nx=x+xd[i];
            int ny=y+yd[i];
            if(isValid(nx,ny,n,m) && grid[nx][ny] >grid[x][y] ){
                // if cell is valid and from curr node to adjacent node, if path is increasing...
                // then continue dfs
                max=Math.max(max,dfs(grid,best,nx,ny,n,m)+1);
                // dfs returns longest path from adjacent node. 1 is for current node.
            }
        }
        // store longest path from current node into matrix and return it.
        best[x][y]=max;
        return  max;
    }
    boolean isValid(int x,int y,int n,int m){
        return  x>=0 && y>=0 && x<n && y<m;
    }
}
