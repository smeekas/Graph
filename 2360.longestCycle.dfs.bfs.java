class Solution {
    public int longestCycle(int[] edges) {
//        if(true){
//            return  bfs(edges);
//        }
        int n=edges.length;
        boolean visited[]=new boolean[n];
        boolean samePath[]=new boolean[n]; //to check whether node which we are exploring is part of the samepath or not.
        int cycle[]=new int[n]; // to track length of cycle.

        Arrays.fill(cycle,1); // every node is cycle of length 1
        int ans[]=new int[1];
        ans[0]=-1;  // to get answer

        for(int i=0;i<n;i++){
            if(visited[i]==false){
                visited[i]=true;
                samePath[i]=true;
                dfs(i,edges,visited,samePath,cycle,ans);
                samePath[i]=false;
                // we are done exploring node i so we have to remove if from samepath.
            }
        }
        return ans[0];

    }
    void dfs(int src, int[] edges,boolean visited[],boolean samePath[],int cycle[],int ans[]){

        int adj=edges[src];
        if(adj==-1)return; //src do not have outgoing cycle.
        if(visited[adj]==false){
            // if not visited then mark as visited and add it to samePath.
                visited[adj]=true;
                samePath[adj]=true;
                cycle[adj]=cycle[src]+1; //cycle length increases
                dfs(adj,edges,visited,samePath,cycle,ans);
            // we are done exploring node adj, so we have to remove if from samepath.
                samePath[adj]=false;

        }else{
            if(samePath[adj]==true){
                // if node is visited and adj is part of samePath means we have cycle.
                // from adj to src we have to calculate cycle length.
                //src will have higher value. src-adj+1 gives length of cycle.
                ans[0]=Math.max(cycle[src]-cycle[adj]+1,ans[0]);
                samePath[adj]=false;
                // we are done exploring node adj, so we have to remove if from samepath.
            }
        }
    }
    int bfs(int edges[]){
        //since this problem includes cycle detection we will use topo sort for bfs algorithm.
        // logic is we first run normal topo sort.
        // after topo sort finishes we will still have some node with indegrees (nodes which are part of cycle)
        // now we can find every cycle, traverse them and collect their lengths.

        // here every node can have only one outgoing edge.
            int n=edges.length;
            int indegree[]=new int[n];

            Arrays.fill(indegree,0);
            for(int i=0;i<n;i++){
                if(edges[i]==-1)continue;
                indegree[edges[i]]++;
            }
            Queue<Integer> q=new ArrayDeque<>();
            for(int i=0;i<n;i++){
             if(indegree[i]==0)q.add(i);
            }
            while (!q.isEmpty()){
                int node=q.poll();
                int adj=edges[node];
                if(adj==-1)continue;
                indegree[adj]--;
                if(indegree[adj]==0)q.add(adj);
            }

            boolean visited[]= new boolean[n];
            int max=-1;
            for(int i=0;i<n;i++){
                if(indegree[i]>0){
                    // if indegree is > 0 means node i is part of cycle
                    int length=0;

                    int currNode=i;
                    while (visited[currNode]==false){
                        // traverse entire cycle
                            visited[currNode]=true;
                            length++;
                            currNode=edges[currNode];
                    }
                    max=Math.max(max,length);
                }
            }
            // what if some node are part of two different cycles?
            // if visited marked by one cycle, we cannot explore it during second cycle traversal

            // here outdegree is only one. so node cannot be part of two cycles !

            if(max!=-1)return  max;
            return -1;
    }

}
