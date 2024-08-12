class Solution {

    public boolean isCycle(int V, ArrayList<ArrayList<Integer>> adj) {
        boolean visited[]=new boolean[V];
        // we will do BFT (because graph might be disconnected)
           for(int i=0;i<V;i++){
               if(!visited[i]){
                   visited[i]=true;
                   boolean res= dfs(new Pair(i,-1),V,adj,visited);
                   // if cycle found then return true else continue the search
                   if(res)return  res;
               }
           }
           return  false;
    }

boolean dfs(Pair node,int V,ArrayList<ArrayList<Integer>> adj,boolean []visited){
        //since dfs will go in depth first if we encounter node which is visited & which is not parent then we can say that we found a cycle
        for (Integer newNode:adj.get(node.node)){
            if(!visited[newNode]){
                visited[newNode]=true;
               boolean res= dfs(new Pair(newNode,node.node),V,adj,visited);
               //if cycle is detected then return true else continue dfs.
                if(res)return  res;
            }else{
                if(newNode!=node.parent){
                    return  true;
                }
            }
        }
        return  false;
}
}
class Pair{
    int node,parent;
    Pair(int node,int parent){
        this.node=node;
        this.parent=parent;
    }
}
