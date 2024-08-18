class Solution {
    public List<Integer> eventualSafeNodes(int[][] graph) {
        // we will use bfs (topo sort)
        // node with out-degree 0 are terminal node.
        // we want safe node.
        // safe node will not have node with cycle.

        // topo sort start with node with in-degree 0
        // we add all nodes in topo sort who do not fall into cycle.

        // so for this problem we will reverse edges of the graph and apply toposort.

        ArrayList<ArrayList<Integer>> g=new ArrayList<>(graph.length);
        for(int[] i:graph)g.add(new ArrayList<>());
        List<Integer> ans=new ArrayList<>();
        int []in=new int[graph.length];

        for(int i=0;i<graph.length;i++){
            for(Integer node:graph[i]){
                g.get(node).add(i);
                in[i]++;
            }
        }
        Queue<Integer> q=new ArrayDeque<>();
        for(int i=0;i<in.length;i++){
            if(in[i]==0)q.add(i);
        }
        while (!q.isEmpty()){
            int node=q.poll();
            ans.add(node);
            for(Integer adjNode:g.get(node)){
                in[adjNode]--;
                if(in[adjNode]==0)q.add(adjNode);
            }
        }
        Collections.sort(ans);
        return  ans;

    }
}
