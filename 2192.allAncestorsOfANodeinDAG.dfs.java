import java.util.ArrayList;
import java.util.List;

class Solution {
    public List<List<Integer>> getAncestors(int n, int[][] edges) {
        //  we do dfs from every single node. graph is DAG.
        // in dfs iteration, for every node, source of dfs is ancestor.
        // this way we will collect ancestors.
        List<List<Integer>> ans=new ArrayList<>();
        ArrayList<ArrayList<Integer>> graph=new ArrayList<>();
        for(int i=0;i<n;i++){
            graph.add(new ArrayList<>());
            ans.add(new ArrayList<>());
        }
        for(int []edge:edges){
            graph.get(edge[0]).add(edge[1]);
        }
        for(int i=0;i<n;i++){
            boolean []vis=new boolean[n];
            // dfs from every node.
            dfs(graph,i,ans,i,vis);

        }
        return  ans;

    }
    void dfs(ArrayList<ArrayList<Integer>> graph,int curr,List<List<Integer>> ans,int src,boolean[]vis){

        for(int adjNode:graph.get(curr)){
            if(vis[adjNode]==false){
                vis[adjNode]=true;
                ans.get(adjNode).add(src);
                // for current node node source is ancestor.
                dfs(graph,adjNode,ans,src,vis);
            }

        }

    }
}
