import java.util.*;
import java.util.stream.Collectors;

class Solution {
    int LARGE=(int)1e8;
    public int numberOfSets(int n, int maxDistance, int[][] roads) {
        // first we can generate all subset of nodes.
        // for every subset we create adj matrix and get multi source-shortest path.
        // in multi source the shortest path, no distance should be greater than maxDistance.
        
        List<HashSet<Integer>> subsets=getSubSet(n-1);
        int ans=0;
        for(HashSet<Integer> sub:subsets){
            if(isValid(n,sub,roads,maxDistance))ans++;
        }
        return ans;
    }
    List<HashSet<Integer>> getSubSet(int n){
        List<Integer> currSubset=new ArrayList<>();
        List<HashSet<Integer>> ans=new ArrayList<>();
        subs(n,currSubset,ans);
        return ans;
    }
    void subs(int n,List<Integer> curr,List<HashSet<Integer>> ans){
        if(n==-1){
            HashSet<Integer> cloned=new HashSet<>();
            for(Integer ii:curr)cloned.add(ii);
            ans.add(cloned);
            return;
        }
        subs(n-1,curr,ans); // don't take
        curr.add(n);
        subs(n-1,curr,ans); //  take
        curr.remove(curr.size()-1);
    }
    boolean isValid(int n,HashSet<Integer> allowedNodes,int[][]roads,int max){
        int min[][]=new int[n][n];
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                if(i==j)min[i][j]=0;
                else min[i][j]=LARGE;
            }
        }
        for(int i=0;i<roads.length;i++){
            int src=roads[i][0];
            int des=roads[i][1];
            int wt=roads[i][2];
            if(allowedNodes.contains(src) && allowedNodes.contains(des)){
                // both node should be part of current subset.
                // there can be multiple edge between two nodes. so we use Math.min
                // undirected 
                min[src][des]=Math.min(min[src][des],wt);
                min[des][src]=Math.min(min[des][src],wt);
            }
        }
        for(int k=0;k<n;k++){
            for(int i=0;i<n;i++){
                for(int j=0;j<n;j++){
                    if(min[i][k]!=LARGE || min[k][j]!=LARGE && allowedNodes.contains(i) && allowedNodes.contains(j)){
                        // both node must be part of current subset.
                        min[i][j]=Math.min(min[i][j],min[i][k]+min[k][j]);
                    }
                }
            }
        }
        
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                if(allowedNodes.contains(i) && allowedNodes.contains(j)){
                    if(min[i][j]>max)return  false;
                }
            }
        }
        return  true;
    }
}
class Pair{
    int des,wt;
    Pair(int des,int wt){
        this.des=des;
        this.wt=wt;
    }
}
