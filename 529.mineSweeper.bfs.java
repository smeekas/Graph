import java.util.ArrayDeque;
import java.util.Queue;

class Solution {
    int xd[]={-1,0,1,-1,1,-1,0,1};
    int yd[]={-1,-1,-1,0,0,1,1,1};
    public char[][] updateBoard(char[][] board, int[] click) {
        int x=click[0],y=click[1];
        int n=board.length,m=board[0].length;
        boolean visited[][]=new boolean[n][m];
        if(board[x][y]=='M') {
            // if clicked cell is mine
            // reveal it return it.
            board[x][y] = 'X';
            return board;
        }
        int mineCount=countBombs(x,y,board);
        if(mineCount>0){
            // if clicked cell is neighbour of atleast one mine then reveal number and return it.
            board[x][y]=Character.forDigit(mineCount,10);
            return  board;
        }
        // now clicked cell is not mine, nor number.
        Queue<Pair> q=new ArrayDeque<>();
        q.add(new Pair(x,y));
        board[x][y]='B';
        visited[x][y]=true;
        // now if we clicked blank cell then we want to explore all neighbours but limit is number cell.
        // we do not explore  number cell
        
        while (!q.isEmpty()){
            Pair node=q.poll();
            int neighbourMines=countBombs(node.x,node.y,board);
            //neighbourMines gives count of neighbour mine count
            if(neighbourMines==0){
                // if zero means blank cell
                for(int k=0;k<xd.length;k++){
                    int nx=node.x+xd[k];
                    int ny=node.y+yd[k];
                    if(isValid(nx,ny,n,m) && visited[nx][ny]==false){
                        // if cell is valid and not visited.
                        if(board[nx][ny]=='E'){
                            // if this neighbour is not revealed then mark it as blank.
                            board[nx][ny]='B';
                            visited[nx][ny]=true;
                             //mark as visited and add it into queue.
                            // now if this neighbour's neighbour contain mine then when we start exploring node, neighbour's mine count will take care of marking digit there.
                            q.add(new Pair(nx,ny));
                        }else{
                            // B or digit.
                            // no need to do anything...
                        }
                    }
                }
            }else{
                // if not zero that means this cell contain digit.
                // so we put digit and we do not add this node to queue.
                board[node.x][node.y]=Character.forDigit(neighbourMines,10);
            }

        }
        return  board;
    }
    boolean isValid(int x,int y,int n,int m){
        return x>=0 && y>=0 && x<n && y<m;
    }
    int countBombs(int x,int y,char[][]board){
        int mineCount=0;
        int n=board.length,m=board[0].length;

        for(int k=0;k<xd.length;k++){
            int nx=x+xd[k];
            int ny=y+yd[k];
            if(isValid(nx,ny,n,m)){
                if(board[nx][ny]=='M')mineCount++;
            }
        }
        return mineCount;
    }
}
class Pair{
    int x,y;
    Pair(int x,int y){
        this.x=x;
        this.y=y;
    }
}
