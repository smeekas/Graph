class Solution {
    public int orangesRotting(int[][] grid) {

        // WE BFS ON ROTTON ORANGES NOT ON FRESH ORANGES
         int n=grid.length;
        int m=grid[0].length;
        int count=-1; //ans
        int i=0,j=0;    
        int fresh[]=new int[1];
        fresh[0]=0;   //count of fresh oranges.
         Queue<Pair> q=new ArrayDeque();
          for( i=0;i<n;i++){
                for( j=0;j<m;j++){
                    if(grid[i][j]==1){
                        fresh[0]++;
                    }
                    if(grid[i][j]==2){
                        q.add(new Pair(i,j));
                    }
                }
                }

//queue contain all the rotton images.
// if we do traversal then ans might be wrong because 2 start point on rotton image might rotton other oranges parallaly.
//and traversal might rotton based on one rotton orange and it will give big ans but in reality it rotten parallaly hence ans should be low.

           count= bfs(grid,i,j,n,m,fresh,q);
    if(fresh[0]>0){
        //after bfs if still fresh oranges left then orange are disconnected and all orange can't rott.
        return -1;
    }
    if(count==-1){
        // no fresh orange but ans is -1 means 0 orange rott. so it could be done in 0 minute right.
        return 0;
    }
    //return ans;
        return count; 
    }
      int bfs(int[][]grid,int row,int col,int n,int m,int []fresh, Queue<Pair> q){
                int total_minutes=0;
       int xc[]=new int[]{-1,1,0,0};
       int yc[]=new int[]{0,0,-1,1};
        //during 
        while(!q.isEmpty() && fresh[0]>0){  //we can remove this fresh count from condition but then we have return total_minutes-1. because without mentioned condition we again enter loop and increase minute again and queue will become empty and we break the loop. soooo if we add mentioned condtion then we will not enter the queue when fresh oranges are finished so we have to just return total_minutes.
            int qlen=q.size();
                total_minutes++;  //total_minutes increased when one level change(level contain rotten oranges from multiple entry point. parallely rotting oranges)
            while(qlen>0){
                // capture current len until iterate that. so that we can process all rotten oranges of the one puerticular level.
                int x=q.peek().x;
                int y=q.peek().y;
               
                q.remove();   
                for(int co=0;co<4;co++){
                    int nx=x+xc[co];
                    int ny=y+yc[co];
                    if(isValid(nx,ny,n,m) && grid[nx][ny]==1){
                        //if orange is fresh then we rott it grid[][]=2; and reduce fresh count fresh[0]--. and add it to queue.
                        //all oranges added to queue (rotted oranges) will we processed in next level.(might contain oranges from muliplte entry point bfs) hence processing oranges in one level makes it parallel rotting.
                         q.add(new Pair(nx,ny));
                                         fresh[0]--;
                        // we could have also done grid[nx][ny]=2 when we pop it. in this step we only do adding to queue. but remember queue contain only rotted oranges! 
                         grid[nx][ny]=2;
                    }
                }
                qlen--;  //decreasing len. 
            }
        }
        return total_minutes;
    }
    boolean isValid(int x,int y,int n,int m){
        if(x<0||y<0||x>=n||y>=m ){
                return false;
            }
            return true;
    }
}

// I thought we cound so something like storing level in node so that we know when we are do bfs. and count it. but that won;t do parallel processing.
// above method is HOW WE DO PARALLEL bfs and how we modify bfs algorithm according to question.
class Pair{
    int x,y;
    Pair(int x,int y){
        this.x=x;
        this.y=y;
       
    }
}
