import java.util.ArrayDeque;
import java.util.PriorityQueue;
import java.util.Queue;

class Solution {
    int xd[]={0,0,1,-1};
    int yd[]={1,-1,0,0};
    int LARGE=(int)1e8;
    public int shortestPathAllKeys(String[] grid) {

        Queue<Pair> q=new ArrayDeque<>();
        int n=grid.length,m=grid[0].length();
        int keycount=0;
        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                char c=grid[i].charAt(j);
                if(c=='@')q.add(new Pair(i,j,0,0));
                else if(c>='a' && c<='f')keycount++;
            }
        }
        int total=(int)Math.pow(2,keycount)-1;
        // with n keys, maximum 2^n states are possible
        // 0 to (2^n)-1  total 2^n states.
        //maximum value can be (2^n)-1.
        // every cell will have 2^n states.
        // n=3 keys. 000   0(3rd key)0(2nd key0(1st key)
        // with all keys state will be 111.
        // with only 3rd key state will be 100.
        // if any cell have all keys then we will return.

        boolean visited[][][]=new boolean[n][m][total+1];
        while (!q.isEmpty()){
            Pair node=q.poll();
            int x= node.x,y= node.y,steps= node.steps,keys= node.keys;
            if(keys==total)return steps; // if cell has all keys then return number of steps.
            for(int i=0;i<xd.length;i++){
                int nx=x+xd[i];
                int ny=y+yd[i];
                if(isValid(nx,ny,n,m)) {
                    char nc=grid[nx].charAt(ny);
                    if(nc=='#')continue; // if wall
                    if(nc>='a' && nc<='f'){
                    // if key
                        int newKey=keys | (1<<nc-'a');
                        // (1<<nc-'a') gives key state. ex. for key c  it will be 000100 => 0001(c)0(b)0(a) (n=6)
                        // we will or it with current state. that wil give use merged and new key state
                        if(visited[nx][ny][newKey]==false){
                            // if  adjacent cell is not visited with this new key
                            // mark as visited
                            visited[nx][ny][newKey]=true;
                            // add it into queue
                            q.add(new Pair(nx,ny,steps+1,newKey));
                        }
                    }else if(nc>='A' && nc<='F'){
                        // if lock
                        // for lock-X, key-X must exist
                        if(((keys>>(nc-'A'))&1)==1){
                        // (nc-'A') will give key index
                        // (keys>>(nc-'A'))  moves key index to right most side
                        // ((keys>>(nc-'A'))&1) since key is now at right most index, we do AND operation with 1. if result is 1 that means keyIndex is 1 else 0

                            // if keyIndex is 1 means for given lock, key exists.


                            if(visited[nx][ny][keys]==false){
                                // if cell is not visited by current set of keys.
                                //mark as visited.
                                visited[nx][ny][keys]=true;
                                // add it into queue
                                q.add(new Pair(nx,ny,steps+1,keys));
                            }
                        }
                    }else if(nc=='.' || nc=='@'){
                        // if cell is empty or start point (we can come again too)
                        if(visited[nx][ny][keys]==false){
                            // if cell is not visited by current set of keys.
                            //mark as visited.
                            visited[nx][ny][keys]=true;
                            // add it into queue
                            q.add(new Pair(nx,ny,steps+1,keys));
                        }
                    }
                }
            }
        }
        // if we are not able to collect all keys and iterations are over.
        // no answer is possible
        return -1;
        
        //SPACE -> O(m*n*2^k)  (visited matrix)
        //TIME -> O(m*n*2^k) (we may visit all cells in visited matrix)
    }
    boolean isValid(int x,int y,int n,int m){
        return  x>=0 && y>=0 && x<n && y<m;
    }
}
class Pair{
    int x,y,keys,steps;

    public Pair(int x, int y, int steps, int keys) {
        this.x = x;
        this.y = y;
        this.keys = keys;
        this.steps = steps;
    }
}
