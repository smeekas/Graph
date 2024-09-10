class Solution {
    public List<List<Integer>> allPathsSourceTarget(int[][] graph) {
        // graph is DAG.
        // so from every node we do dfs and add current node  in current path.
        // when we reach destination we add entire current path into answer.
        // after finishing dfs from current node, we remove current node.
        
        List<List<Integer>> ans=new ArrayList<>();
        List<Integer> currAns=new ArrayList<>();
        currAns.add(0);
        dfs(0,graph.length-1,graph,ans,currAns);
        return  ans;
    }
    void dfs(int curr,int des,int [][]g,List<List<Integer>> ans,List<Integer> currentPath){
        if(curr==des){
            List<Integer> newAns=new ArrayList<>();
            newAns.addAll(currentPath);
            ans.add(newAns);
            return;
        }
        for(int i:g[curr]){
            currentPath.add(i);
            dfs(i,des,g,ans,currentPath);
            currentPath.remove(currentPath.size()-1);
        }
    }
}
