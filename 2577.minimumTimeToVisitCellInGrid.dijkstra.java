import java.util.Arrays;
import java.util.PriorityQueue;

class Solution {
    int xd[]=new int[]{0,0,1,-1};
    int yd[]=new int[]{1,-1,0,0};
    public int minimumTime(int[][] grid) {
        // if neighbour cell have higher, then we have to wait until that much time passes.
        // but we cannot stop. we have to move.
        // so we can move back and forth to previous node and do time pass until neighbour node become lower than current time.
        PriorityQueue<Pair> pq=new PriorityQueue<>((a,b)->a.wt-b.wt);;
        int n=grid.length,m=grid[0].length;
        int minDis[][]=new int[n][m];
        int LARGE=(int)1e8;
        for(int[] row:minDis){
            Arrays.fill(row,LARGE);
        }
        minDis[0][0]=0;

        if(grid[0][1] >grid[0][0]+1  && grid[1][0] > grid[0][0]+1){
            // if we cannot move from starting node then we cannot get answer.
            // if we stand at starting node then there is no any node where we can go and do timepass.
            // and stopping at one node is not allowed. we have to keep moving.
            // so this is case we will never get an answer.
            return -1;
        }
        // if above condition is false then we will always have answer.
        // because there is atleast one node where we can do oscillation and do time pass until neighbour node have less value than current time.

        pq.add(new Pair(0,0,0));
        while (!pq.isEmpty()){
            Pair node=pq.poll();
            for(int i=0;i<xd.length;i++){
                int nx=node.x+xd[i];
                int ny=node.y+yd[i];
                if(isValid(nx,ny,n,m)){
                    // if node is valid
                    if(node.wt+1 >=grid[nx][ny]){
                        // if we can move to neighbour cell...
                        // it takes 1 sec to travel to neighbour.
                        // when we reach grid[nx][ny], time will be node.wt+1
                        // if node.wt+1 is higher (current time is higher than neighbour) then we can move to neighbour.
                        if(node.wt+1 < minDis[nx][ny]){
                            // if newTime is lesser than explore it.
                            pq.add(new Pair(nx,ny,node.wt+1));
                            minDis[nx][ny]=node.wt+1;
                        }
                    }else {
                        // we cannot move to neighbour cell.
                        // we have to do timepass until neighbour become lesser than current time
                        // but we will not do oscillation and do time waste
                        // we will find out how much oscillation we have to do.
                        int diff=grid[nx][ny]-node.wt; // difference between current time and neighbour.
                        // we need to waste diff amount of time.
                        // so node.wt + diff.
                        // if diff is even then after coming from previous node we will where we started
                        // total time passed = grid[nx][ny].
                        // 1 more second to travel to neighbour.
                        // total = node.wt + diff + 1  (for even difference)

                        //if difference is odd then if oscillations, we will be at where we started but
                        // total time passed = grid[nx][ny]-1. (ex.1 2 7) => we are at 2. diff=5. two oscillation. 2->1->2->1->2. total time now=6. one
                        // 1 more second to travel to neighbour
                        // total = node.wt + diff -1 + 1  = node.wt + diff (for odd difference)


                        int newWt=node.wt+diff+(diff%2==0?1:0);
                        if(newWt< minDis[nx][ny]){
                            pq.add(new Pair(nx,ny,newWt));
                            minDis[nx][ny]=newWt;
                        }

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
class  Pair{
    int x,y,wt;
    Pair(int x,int y,int wt){
        this.y=y;
        this.x=x;
        this.wt=wt;

    }
}
