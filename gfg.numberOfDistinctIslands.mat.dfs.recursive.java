class Solution {
    int[] xd=new int[]{0,0,-1,1};
    int[] yd=new int[]{-1,1,0,0};
    int countDistinctIslands(int[][] grid) {
       
        int N=grid.length,M=grid[0].length;
        HashSet<String> hs=new HashSet<>();

        for(int i=0;i<N;i++){
           for(int j=0;j<M;j++){
               if(grid[i][j]==1){
               StringBuffer sb=dfs(new Pair(i,j),grid,new Pair(i,j));
               //each dfs will return shape of the island.
               hs.add(sb.toString());
               }
           }
        }
        return  hs.size();
    }
    StringBuffer dfs(Pair pair, int [][]grid,Pair init){
        //since shape will be same we consider first node as base. so we subtract base node from all other node,
        // which will give us shape.
        StringBuffer sb=new StringBuffer();
            for(int i=0;i<4;i++){
                int nx=pair.x+xd[i];
                int ny=pair.y+yd[i];
                if(isValid(nx,ny,grid.length,grid[0].length) && grid[nx][ny]==1){
                    grid[nx][ny]=0;
                   sb.append(dfs(new Pair(nx,ny),grid,init));
                   //append whatever we got from dfs.
                }
            }
            return  sb.append(pair.x-init.x).append(pair.y-init.y);
            // we append x & y coordinates.
    }

    boolean isValid(int x,int y,int N,int M){
        return  x>=0 && y>=0 && x<N && y<M;
    }
}

class  Pair{
    int x, y;
    Pair(int x,int y){
            this.x=x;
            this.y=y;
    }
}


