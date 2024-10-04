import java.util.*;

class Solution {
    public int[][] buildMatrix(int k, int[][] rowConditions, int[][] colConditions) {
        // first of all we need topo sort of rows and columns
        // we also need toposort and their index.
        int ans[][]=new int[k][k];
        HashMap<Integer,Integer> hmr=getTopo(k,rowConditions);
        HashMap<Integer,Integer> hmc=getTopo(k,colConditions);
        if(hmr.size()!=k || hmc.size()!=k)return new int[0][0]; // if one of them forms cycle then we cannot build matrix.
        
        for(int i=1;i<=k;i++){
            // for particular item get their row and column index.
            int ri=hmr.get(i);
            int ci=hmc.get(i);
            ans[ri][ci]=i; // place item in their spot.
        }
        return ans;
    }
    HashMap<Integer,Integer> getTopo(int k,int[][]conditions){
        int indegree[]=new int[k+1];
        ArrayList<ArrayList<Integer>> adj=new ArrayList<>();
        for(int i=0;i<=k;i++)adj.add(new ArrayList<>());

        for(int[] row:conditions){
            int src=row[0],des=row[1];
            adj.get(src).add(des);
            indegree[des]++;
        }
        Queue<Integer> q=new ArrayDeque<>();
        HashMap<Integer,Integer> hmr=new HashMap<>();
        ArrayList<Integer> ans=new ArrayList<>();
        for(int i=1;i<=k;i++) {
            if (indegree[i] == 0) {
                q.add(i);
                ans.add(i);
            }
        }
        while (!q.isEmpty()){
            int node=q.poll();
            for(int adjNodes:adj.get(node)){
                indegree[adjNodes]--;
                if(indegree[adjNodes]==0){
                    q.add(adjNodes);
                    ans.add(adjNodes);
                }
            }
        }
        // topo sort item and corresponding index
        for(int i=0;i<ans.size();i++){
            hmr.put(ans.get(i),i);
        }
        return hmr;
    }
}
