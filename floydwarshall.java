class Solution
{
    public void shortest_distance(int[][] matrix)
    {
    // this is multi source shortest path algorithm.
    // shortest path from every node to other node
       int n=matrix.length;
       int m=matrix[0].length;
       for(int i=0;i<n;i++){
           for(int j=0;j<m;j++){
               if(matrix[i][j]==-1)matrix[i][j]=(int)1e9;
           }
       }
       // idea is simple.
      // we try to go via node
      //ex.
      // we first try to go via node-0.
      // i-->node-0 + node-0-->j.
      // do above for all nodes(i,j).
      // then we try to go via node-1
      // i--> node-1 + node-1-->j
      // and so on.
       for(int k=0;k<n;k++){
           for(int i=0;i<n;i++){
               for(int j=0;j<m;j++){
                        //i-k + k-j == i-j
                   if(matrix[i][k]+matrix[k][j]<matrix[i][j]){
                       matrix[i][j]=matrix[i][k]+matrix[k][j];
                   }

               }
           }
       }

        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                if(matrix[i][j]==(int)1e9)matrix[i][j]=-1;
            }
        }
       
    }
}
