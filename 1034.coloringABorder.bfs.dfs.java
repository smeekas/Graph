class Solution {
    int xd[]={1,-1,0,0};
    int yd[]={0,0,1,-1};
    public int[][] colorBorder(int[][] grid, int row, int col, int color) {
        if(true){
            return  apporach2(grid,row,col,color);
        }
        //approach
        // we do dfs on component and mark it as -ve value
        int n=grid.length;
        int m=grid[0].length;
        int originalColor=grid[row][col];
        grid[row][col]= -grid[row][col];
        dfs(row,col,grid,n,m,originalColor);

        // if node is not boundary node or border node that means it is inside node.
        // if node is part of component and  surrounded by originalColor or -originalColor means it is inside node.
        // if it inside node then revert it back to original node.
        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                int count=0;
                for(int k=0;k<4;k++){
                    int nx=i+xd[k];
                    int ny=j+yd[k];
                    if(grid[i][j]<0 && isValid(nx,ny,n,m) && Math.abs(grid[nx][ny])==originalColor)count++;
                }
                if(count==4){
                    grid[i][j]=originalColor;
                }
            }
        }
        // now grid contain -ve value for border nodes.
        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                if(grid[i][j]<0)grid[i][j]=color;
            }
        }
        return  grid;
    }
    void dfs(int x,int y,int [][]grid,int n,int m,int og){


        for(int i=0;i<4;i++){
            int nx=x+xd[i];
            int ny=y+yd[i];
            if(isValid(nx,ny,n,m) && grid[nx][ny]==og){
                grid[nx][ny]= -grid[nx][ny];
                dfs(nx,ny,grid,n,m,og);
            }
        }
    }
    boolean isValid(int r,int c,int n,int m){
            return r>=0 && c>=0 && r<n && c<m;
    }
    int[][] apporach2(int[][]grid,int row,int col,int color){
        int n=grid.length;
        int m=grid[0].length;
        int originalColor=grid[row][col];
        // second approach is we do dfs/bfs but if child node is not valid or child is of different color then we consider parent as border.

        boolean vis[][]=new boolean[n][m];
        ArrayList<Pair> border=new ArrayList<>(); // list that contain border cells.
        Queue<Pair> q=new ArrayDeque<>();
        vis[row][col]=true;
        q.add(new Pair(row,col));

        while (!q.isEmpty()){
            Pair node=q.poll();
            for(int k=0;k<4;k++){
                int nx=node.x+xd[k];
                int ny=node.y+yd[k];
                if(!isValid(nx,ny,n,m) || grid[nx][ny]!=originalColor){ //if node is not valid or node is of different color then parent is border cell.
                    border.add(node);
                    // we might add same node multiple times in border array.
                }else if(isValid(nx,ny,n,m) && vis[nx][ny]==false){ // else if node is not visited then continue bfs
                    vis[nx][ny]=true;
                    q.add(new Pair(nx,ny));
                }
            }

        }
        for(Pair pair:border){
            int x= pair.x;
            int y= pair.y;
            if(grid[x][y]!=color)grid[x][y]=color; // if border color is not changed then change it.
        }
        return  grid;
    }


}
class Pair{
    int x,y;
    Pair(int x,int y){
        this.x=x;
        this.y=y;
    }
}
