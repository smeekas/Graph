class Solution {

    public boolean isCyclic(int V, ArrayList<ArrayList<Integer>> adj) {
	// 99.99% same as topo sort
	// topo sort should have all vertices in some order.
	// if topo sort do not contain all vertices then graph will have cycle.
	// if cycle exists then at some point no vertice will have 0 in-degree, we will not add vertex to queue. so loop will break.
	
        int indegree[]=new int[V];
        Queue<Integer> q=new ArrayDeque<>();
        List<Integer> ans=new ArrayList<>();
        for(ArrayList<Integer> adjNodes:adj){
            for(Integer adjNode:adjNodes){
                indegree[adjNode]++;
            }
        }
        for(int i=0;i<V;i++){
            if(indegree[i]==0){
                q.add(i);
            }
        }
        while (!q.isEmpty()){
            int node=q.poll();
            ans.add(node);
            for(Integer adjNode:adj.get(node)){
                indegree[adjNode]--;
                if(indegree[adjNode]==0)q.add(adjNode);
            }
        }
        return  ans.size()!=V;
    }
}
