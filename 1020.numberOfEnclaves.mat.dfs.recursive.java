class Solution {
    int xd[]=new int[]{0,0,-1,1};
    int yd[]=new int[]{1,-1,0,0};
    public int numEnclaves(int[][] board) {
    // prerequisite:- 130 surrouded Regions.

	// intuition
	// capture all edge 1s. mark them as 0/-1.
	// count all remaining 1.
        ArrayList<Pair> edgeZero=new ArrayList<>();
        int N=board.length,M=board[0].length;
        for(int i=0;i<N;i++){
            for(int j=0;j<M;j++){
                if(i==0||j==0||i==N-1||j==M-1){
                     if(board[i][j]==1){
                        edgeZero.add(new Pair(i,j));
                    }
                }
            }
        }
        for(Pair zeroNode:edgeZero){
            board[zeroNode.x][zeroNode.y]=-1;
            dfs(new Pair(zeroNode.x,zeroNode.y),board,N,M);
        }
        int result=0;
        for(int i=0;i<N;i++){
            for(int j=0;j<M;j++){
                if(board[i][j]==1)result++;
            }
        }

         return result;

    }
    void dfs(Pair curr, int[][]board,int N,int M){

        for(int i=0;i<4;i++){
            int nx=curr.x+xd[i];
            int ny=curr.y+yd[i];
             if(isValid(nx,ny,N,M) && board[nx][ny]==1){
                board[nx][ny]=-1;
                dfs(new Pair(nx,ny),board,N,M);
            }
        }
    }

    boolean isValid(int x,int y,int N,int M){
        return  x>=0 && y>=0 && x<N && y<M;
    }
}
class Pair{
    int x,y;
    Pair(int x,int y){
        this.x=x;
        this.y=y;
    }
}
