class Solution {

    int[] xd=new int[]{-1,1,0,0};
    int[] yd=new int[]{0,0,1,-1};
    public int[][] floodFill(int[][] image, int sr, int sc, int color) {
        dfs(image,sr,sc,image[sr][sc],color);
        return  image;
    }
    void dfs(int[][] image,int x,int y,int sourceColor,int color){
         // we want to change sourceColor to color.
            image[x][y]=color;
            for(int i=0;i<4;i++){
                int newX=x+xd[i];
                int newY=y+yd[i];

                if(isValid(newX,newY,image.length,image[0].length) && image[newX][newY]==sourceColor && image[newX][newY]!=color){
                    // it must be valid indices, cell must have sourceColor & it cannot be changed color.
                    //  if sourceColor & color are same we will go in infinite loop. to break it we have last condition.
                    dfs(image,newX,newY,sourceColor,color);
                }
            }

    }

    boolean isValid(int x,int y,int N,int M){
        return  x<N && y<M && x>=0 && y>=0;
    }

}
