class Solution {
    public int[] findOrder(int numCourses, int[][] prerequisites) {
        
        // basically we have to find topo sort if we are able to.
        // graph can and cannot contain cycle.
        // if cycle then empty array else toposort
        ArrayList<ArrayList<Integer>> adj =new ArrayList<>();
        for(int i=0;i<numCourses;i++){
            adj.add(new ArrayList<>());
        }
        for (int i=0;i<prerequisites.length;i++){
            adj.get(prerequisites[i][1]).add(prerequisites[i][0]);
        }
        boolean [] visited=new boolean[numCourses];
        boolean [] samePath=new boolean[numCourses];
        ArrayList<Integer> ans=new ArrayList<>();
        for(int i=0;i<numCourses;i++){
            if(visited[i]==false){
                visited[i]=true;
                samePath[i]=true;
                if(!dfs(i,adj,visited,samePath,ans)){
                    samePath[i]=false;
                }else{
                    // if cycle exists then directly return empty array.
                    return  new int[]{};
                }
            }
        }
        return ans.stream().mapToInt(i->i).toArray();
    }
    // return true if cycle exists.
    boolean dfs(int currNode,ArrayList<ArrayList<Integer>> adj, boolean[]visited, boolean[]samePath,ArrayList<Integer> ans){
        for(Integer adjNodes:adj.get(currNode)){
            if(visited[adjNodes]==false){
                visited[adjNodes]=true;
                samePath[adjNodes]=true;
                if(dfs(adjNodes,adj,visited,samePath,ans)){
                    return  true;
                }
                samePath[adjNodes]=false;
            }else{
                if(samePath[adjNodes]==true){
                    return  true;
                }
            }
        }
        ans.add(0,currNode);
        return  false;
    }
}
