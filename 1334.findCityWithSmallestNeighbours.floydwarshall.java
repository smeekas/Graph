class Solution {
    public int findTheCity(int n, int[][] edges, int distanceThreshold) {

        // just do floyd warshall
        // then count destination city from every city that meets threshold.
        int mat[][]=new int[n][n];
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                if(i==j)mat[i][j]=0;
                else mat[i][j]=(int)1e9;
            }
        }
        for(int[] edge:edges){
            int src=edge[0];
            int des=edge[1];
            int wt=edge[2];
            mat[src][des]=wt;
            mat[des][src]=wt;
        }
        for(int k=0;k<n;k++){
            for(int i=0;i<n;i++){
                for(int j=0;j<n;j++){
                   if(mat[i][k]+mat[k][j]< mat[i][j]){
                       mat[i][j]=mat[i][k]+mat[k][j];
                   }
                }
            }
        }

        int city=0,min=(int)1e9;
        for(int i=0;i<n;i++){
            int count=0;
            for(int j=0;j<n;j++){
                    if(mat[i][j]<=distanceThreshold)count++;
            }
            // count represent max cities we can reach in threshold from city-i.
            if(count<=min){
                // if it is less then current value then update the answer
                min=count;
                city=i;
            }
        }
        return  city;
    }
}
