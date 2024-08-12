class Solution {
    int xd[]=new int[]{0,0,-1,1};
    int yd[]=new int[]{-1,1,0,0};
    public int[][] updateMatrix(int[][] mat) {
        int N=mat.length,M=mat[0].length;
        Queue<Pair> q=new ArrayDeque<>();
        int distance[][]=new int[N][M];  //our result
        boolean visited[][]=new boolean[N][M];  //track of visited nodes.

        for (int i=0;i<N;i++){
            for (int j=0;j<M;j++){
                if(mat[i][j]==0) {
                    q.add(new Pair(i, j, 0));
                    // we have to parallel processing. so we will add all zeroes to queue.
                    distance[i][j]=0;  // cell with zero have distance zero
                };
            }
        }
        while (!q.isEmpty()){
            Queue<Pair> adj=new ArrayDeque<>();
            while (!q.isEmpty()){
                        Pair node=q.poll();
                        distance[node.x][node.y]=node.distance;
                        for(int i=0;i<4;i++){
                            int newX=node.x+xd[i];
                            int newY=node.y+yd[i];
                            if(isValid(newX,newY,N,M) && mat[newX][newY]!=0 && !visited[newX][newY] ){
                                // node must be valid, it must not be visited & it cannot be zero.(means node which are not processed yet.)
                                visited[newX][newY]=true;
                                adj.add(new Pair(newX,newY,node.distance+1)); //from currNode to newNode distance will increase by 1.
                            }
                        }
            }
            q.addAll(adj);
        }
        return  distance;
    }
    boolean isValid(int x,int y,int N,int M){
        return  x<N && y<M && x>=0 && y>=0;
    }
}
class Pair{
    int x,y,distance;
    Pair(int x,int y,int distance){
        this.x=x;
        this.y=y;
        this.distance=distance;
    }
}
