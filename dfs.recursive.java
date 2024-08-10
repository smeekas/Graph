class Solution {
    public ArrayList<Integer> dfsOfGraph(int V, ArrayList<ArrayList<Integer>> adj) {
         boolean visited[]=new boolean[adj.size()]; //we have to keep track of which vertex is visited.
            visited[0]=true; //we start with vertex-0
            ArrayList<Integer> ans=new ArrayList<>(); // store ans (vertexes)
            dfs(0,adj,visited,ans);   //start from 0th vertex
            return  ans;
        
    }
    
        public void dfs(int currNode,ArrayList<ArrayList<Integer>> adj,boolean[] visited, ArrayList<Integer> ans){
                ans.add(currNode); // add currNode in ans.
                for (Integer node:adj.get(currNode)){  // visit all adjacent vertex of currNode
                    if(visited[node]==false){  // if they are not visited then we will visit it!
                        visited[node]=true; // mark as visited
                        dfs(node,adj,visited,ans);  //start exploring new node
                    }
                }
    }
}
