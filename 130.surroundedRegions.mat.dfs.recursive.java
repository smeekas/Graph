class Solution {
    int xd[]=new int[]{0,0,-1,1};
    int yd[]=new int[]{1,-1,0,0};
    public void solve(char[][] board) {
        // intuition
        // we do traversal of all cell from edge. mark them as -
        // later we convert all 0s as X.
        // then we convert - to 0.

        ArrayList<Pair> edgeZero=new ArrayList<>(); // we store all edge zeros
        int N=board.length,M=board[0].length;
        for(int i=0;i<N;i++){
            for(int j=0;j<M;j++){
                if(i==0||j==0||i==N-1||j==M-1){
                    if(board[i][j]=='O'){
                        edgeZero.add(new Pair(i,j));
                    }
                }
            }
        }
        for(Pair zeroNode:edgeZero){
            board[zeroNode.x][zeroNode.y]='-';
            dfs(new Pair(zeroNode.x,zeroNode.y),board,N,M);
        }


        for(int i=0;i<N;i++){
            for(int j=0;j<M;j++){
                if(board[i][j]=='O')board[i][j]='X';
                if(board[i][j]=='-')board[i][j]='O';
            }
        }

    }
    void dfs(Pair curr, char[][]board,int N,int M){

        for(int i=0;i<4;i++){
            int nx=curr.x+xd[i];
            int ny=curr.y+yd[i];
            if(isValid(nx,ny,N,M) && board[nx][ny]=='O'){
                //if cell is O then we  traverse it.
                board[nx][ny]='-';
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
