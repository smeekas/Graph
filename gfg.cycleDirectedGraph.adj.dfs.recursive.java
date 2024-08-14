class Solution {

    public boolean isCyclic(int V, ArrayList<ArrayList<Integer>> adj) {
        // 1->2 & 1->3 & 2->4 & 3->4
        // in above case we might do dfs like 1 -> 2 -> 4 then traverse back 4 -> 2 -> 1 then explore other node 1 -> 3 -> 4.
        // in second case we might encounter 4 again which is alredy visited but i this is not cycle.
        // so do detect cycle in directed graph using dfs we keep track of path.
        // if we react node & node is already in the path then we can say that cycle exists.
        boolean visited[]=new boolean[V];
        boolean samePath[]=new boolean[V]; // array which says which node is in path 
        
        for(int i=0;i<V;i++){
            if(visited[i]==false){
                visited[i]=true;
                samePath[i]=true; 
                if(dfs(i,adj,visited,samePath)){
                    return  true;
                }
                samePath[i]=false;  //since we explored i, so we remove it from path.
            }
        }
        return  false;
    }

    boolean dfs(int currNode,ArrayList<ArrayList<Integer>> adj,boolean visited[], boolean samePath[]){

            for(Integer node:adj.get(currNode)){
                    if(visited[node]==false){
                        visited[node]=true;
                        samePath[node]=true;
                        // add node is path & mark as visited.
                        if(dfs(node,adj,visited,samePath)){
                            // if cycle exists then return true.
                            return  true;
                        }
                        // we finished exploring node so remove it from path
                        samePath[node]=false;
                    }else{
                        // if node is visited & node is already in the path then we definitely have a cycle.
                        if(samePath[node])return  true;
                    }
            }
            // dfs completed without returning true. so cycle do not exists.
        return  false;
    }
}
