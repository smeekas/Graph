class Solution {
    public ArrayList<Integer> dfsOfGraph(int V, ArrayList<ArrayList<Integer>> adj) {
        boolean visited[]=new boolean[adj.size()]; //we have to keep track of which vertex is visited.

        ArrayList<Integer> ans=new ArrayList<>(); // store ans (vertexes)
        Stack<Integer> st=new Stack<>();  //assume this is our call stack
            st.add(0); //we start with vertex-0
            visited[0]=true;
            while (!st.isEmpty()){
                    int currVertex=st.pop(); // current vertex
                        ans.add(currVertex);
                        for(Integer newVertex:adj.get(currVertex)){
                            // traverse all adjacent vertexes & add them in stack
                            if(!visited[newVertex]){  //only if they are not visited before.
                                st.add(newVertex);
                                visited[newVertex]=true;
                            }
                        }
            }
        return  ans;

    }


}
