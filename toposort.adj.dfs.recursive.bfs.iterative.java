class Solution
{
   
    static int[] topoSort(int V, ArrayList<ArrayList<Integer>> adj)
    {
        // topo sort is that you should visit element that doesn't have incoming edge first.aka not dependent on anynode.
        // in topo sort all parent(adjacent) node of the current node must be on the left of the current node.
        // multiple topo sort is possible.

        boolean visited[]=new boolean[V];
        ArrayList<Integer> ans=new ArrayList<>();
            for(int i=0;i<V;i++){
                if(visited[i]==false){
                    visited[i]=true;
                dfs(i,adj,ans,visited);
                }
            }

            return bfs(V,adj).stream().mapToInt(i->i).toArray();
    }

    static  void dfs(int currNode,ArrayList<ArrayList<Integer>> adj,ArrayList<Integer> ans,boolean[]visited){
        for(Integer adjNode:adj.get(currNode)){
            if(visited[adjNode]==false){
                    visited[adjNode]=true;
                    dfs(adjNode,adj,ans,visited);
            }
        }
        // we visit all child and then add curr node to front of the toposort.
        // means all child nodes are on right. and parent node is on left.
        ans.add(0,currNode);
    }
    static  List<Integer> bfs(int V,ArrayList<ArrayList<Integer>> adj){
    // this looks like bfs but it is not.
        //KAHN's algo
        int indegree[]=new int[V]; //array which store's in-degree
        List<Integer> ans=new ArrayList<>(); // store ans
        for(ArrayList<Integer> adjNodes:adj){
            for(Integer adjNode:adjNodes){
                indegree[adjNode]++;  // store in-degrees
            }
        }
        Queue<Integer> q=new ArrayDeque<>(); // queue for bfs

        for(int i=0;i<V;i++){
            if(indegree[i]==0){
                q.add(i); // initially we start with all nodes with 0 in-degree.
            }
        }
        // there will be atleast one node with 0 in-degree (because of DAG)
        //we add node in ans, we delete this node means we decrease in-degree of adj nodes.
        // ex. 0->{1,4,5}
        // we add 0 to ans. decrease 1 from node-1,4,5.
        // if adj node's in-degree become zero then it means now that node is not dependent on any one. so we add that node in queue.
        while (!q.isEmpty()){
            int node=q.poll();
            ans.add(node);
            for(Integer adjNodes:adj.get(node)){
                indegree[adjNodes]--;
                if(indegree[adjNodes]==0){
                    q.add(adjNodes);
                }
            }
        }
        return  ans;
    }
}
