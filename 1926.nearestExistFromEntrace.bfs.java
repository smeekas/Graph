class Solution {
    int xd[]={0,0,1,-1};
    int yd[]={1,-1,0,0};
    public int nearestExit(char[][] maze, int[] entrance) {

        // if we go level by level from entrance.
        // whoever reaches the boundary first we can say that many steps we require.
        return  bfs(maze,entrance);
    }
    int bfs(char[][] maze,int[]entrance){
        int n=maze.length;
        int m=maze[0].length;
        int srcX=entrance[0];
        int srcY=entrance[1];
        boolean [][]visited=new boolean[n][m];
        Queue<Pair> q=new ArrayDeque<>();
        visited[srcX][srcY]=true;
        q.add(new Pair(srcX,srcY,0));

        while (!q.isEmpty()){
            Pair node=q.poll();
            for(int i=0;i<4;i++){
                int nx=node.x+xd[i];
                int ny=node.y+yd[i];
                if(!isValid(nx,ny,n,m) && (node.x!=srcX || node.y!=srcY)){
                    // if node is not valid (out of maze) and parent node is not entrance (either row is not same or column is not same)
                    // that means we reached exit.
                    // so return number of steps parent took.
                    return node.steps;
                }else{
                    if( isValid(nx,ny,n,m) && maze[nx][ny]=='.' && visited[nx][ny]==false){
                        // if node is valid and it is empty cell and not visited then we can continue search.
                        visited[nx][ny]=true;
                        q.add(new Pair(nx,ny,node.steps+1));
                    }
                }
            }
        }
        return -1;
    }
    boolean isValid(int x,int y,int n,int m){
        return  x>=0 && y>=0 && x<n && y<m;
    }
}
class Pair{
    int x,y,steps;
    Pair(int x,int y,int steps){
        this.x=x;
        this.y=y;
        this.steps=steps;
    }
}
