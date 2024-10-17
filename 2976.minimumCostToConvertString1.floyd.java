import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

class Solution {
    public long minimumCost(String source, String target, char[] original, char[] changed, int[] cost) {
        // here from original and change we can construct graph and find multi source shortest path

        int INF=(int)1e7;
        long [][]multi=new long[26][26]; // for alphabets
        for(int i=0;i<26;i++){
            for(int j=0;j<26;j++){
                if(i==j)multi[i][j]=0;
                else{
                    multi[i][j]=INF;
                }
            }
        }
        int n=original.length;
        for(int i=0;i<n;i++){
            int src=original[i]-'a';
            int dest=changed[i]-'a';
            int c=cost[i];
            if(src==dest)continue;
            // Math.min because multiple edges may exist from src to des with different weight.
            multi[src][dest]=Math.min(multi[src][dest],c);
        }
        // floyd warshall
        for(int k=0;k<26;k++){
            for(int i=0;i<26;i++){
                for(int j=0;j<26;j++){
                    if(i==j)continue;
                    long ik= multi[i][k];
                    long kj= multi[k][j];
                    if(ik!=INF && kj!=INF){
                        multi[i][j]=Math.min(multi[i][j],ik+kj);
                    }
                }
            }
        }

        long ans=0;
        for(int i=0;i<source.length();i++){
            char sc=source.charAt(i);
            char st=target.charAt(i);
            int s=sc-'a';
            int d=st-'a';
            // if we cannot change character means answer do not exist.
            if(multi[s][d]==INF)return -1;
            ans+=multi[s][d];
        }
        return ans;

    }
}
class Pair{
    int node,cost;
    Pair(int node,int cost){
        this.node=node;
        this.cost=cost;
    }
}
