class Solution {
   
    public boolean isCycle(int V, ArrayList<ArrayList<Integer>> adj) {
        boolean visited[]=new boolean[V];
        // we will do BFT (because graph might be disconnected)
           for(int i=0;i<V;i++){ 
               if(!visited[i]){
                   boolean res= bfs(i,V,adj,visited);
                   // if cycle found then return true else continue the search
                   if(res)return  res;
               }
           }
           return  false;
    }

    boolean bfs(int node,int V,ArrayList<ArrayList<Integer>> adj,boolean[] visited ){
        Queue<Pair> q=new ArrayDeque<>();
        q.add(new Pair(node,-1)); // since we are starting we put parent as -1
        visited[node]=true;
            while (!q.isEmpty()){
                    Pair currNodePair=q.poll();
                    int parent=currNodePair.parent;
                    int currNode=currNodePair.node;
                    // we are tracking parent because graph is undirected means we have 1-2 edge & 2-1 edge. 
                    for(Integer newNode:adj.get(currNode)){
                            if(visited[newNode]){
                                // if node is visited & node is not parent that mean there is cycle.
                                    if(newNode!=parent)return  true;
                                    //while doing bfs we came across node which is visited. so difinitely we can go to that node to form a cycle.
                            }else{
                                //if node is not visited then mark as visited & add node to queue.
                                visited[newNode]=true;
                                q.add(new Pair(newNode,currNode));
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
