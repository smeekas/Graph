import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

class Solution {
    int LARGE=(int)1e9;
// we will apply dijkstra here.
    // first we try to move forward.
    // if we are not able to move forward, then we check for other cell in same column.
    // if there is no obstacle, then we will try to go there
    public int minSideJumps(int[] obstacles) {
        int grid[][]=new int[3][obstacles.length];
        int best[][]=new int[3][obstacles.length];
        for(int row[]:best) Arrays.fill(row,LARGE);

        best[1][0]=0;
        for(int i=0;i<obstacles.length;i++){
            if(obstacles[i]!=0){
                grid[obstacles[i]-1][i]=1; // fill the obstacles.
            }
        }

        PriorityQueue<Pair> pq=new PriorityQueue<>((a,b)->a.wt-b.wt);
        pq.add(new Pair(1,0,0));

        while (!pq.isEmpty()){
            Pair node=pq.poll();
            if(node.y+1 <obstacles.length && grid[node.x][node.y+1]!=1 ){
                    // if we have not exceeded the column and forward cell is not obstacle then we will move
                // this will not incur any cost
                if(node.wt<best[node.x][node.y+1]){
                    best[node.x][node.y+1]=node.wt;
                    pq.add(new Pair(node.x, node.y+1,node.wt));
                }

            }else{
                // forward cell is obstacle,
                // we will search in same column for sideway jump
                //this sideway jump will incur cost
                int newWt=node.wt+1;
                for(int i=0;i<3;i++){
                    if(i== node.x)continue;
                    if(newWt<best[i][node.y] && grid[i][node.y]!=1) {
                        best[i][node.y] = newWt;
                        pq.add(new Pair(i, node.y, newWt));
                    }
                }

            }

        }
        int min=LARGE;
        for(int i=0;i<3;i++){
            // we search for answer in last column.
            min=Math.min(best[i][obstacles.length-1],min);
        }
        return min;
    }
}
class Pair{
    int x,y,wt;
    public Pair(int x, int y, int wt) {
        this.x = x;
        this.y = y;
        this.wt = wt;
    }
}
