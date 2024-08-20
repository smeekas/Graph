class Solution
{
    public String findOrder(String [] dict, int N, int K)
    {
        // main task in this problem is to construct graph from given string.
        // rest is to find toposort.
        // we will compare two strings character by character.
        // in comparing when character mismatch we can say that edge exists from string(i).char to string(i+1).char

        ArrayList<ArrayList<Integer>> graph=new ArrayList<>();
        for(int i=0;i<K;i++){
            graph.add(new ArrayList<>());
        }
        for(int i=0;i<dict.length-1;i++){
            // i, i+1
            // we will compare string i & string i+1.
            int j=0;
            while(j<dict[i].length() && j<dict[i+1].length() && dict[i].charAt(j)==dict[i+1].charAt(j))j++;
            // we loop though string until character matches.

            if(j<dict[i].length() && j<dict[i+1].length()){
                // for comparing j must be less than both string's length.
                // since we have sorted order of alien dictionary we can say that edge exists from string(i).char to string(i+1).char
                graph.get(dict[i].charAt(j)-'a').add(dict[i+1].charAt(j)-'a');
            }
            // edge will not exist in below case
            // ex. "abcd","abc"
            
        }
        return  getTopo(graph,K);


    }
    String getTopo(ArrayList<ArrayList<Integer>> adj,int V){
        int []indegree=new int[V];
        for(ArrayList<Integer> nodes:adj){
            for(Integer node:nodes){
                indegree[node]++;
            }
        }
        Queue<Integer> q=new ArrayDeque<>();
        for (int i=0;i<V;i++){
            if(indegree[i]==0)q.add(i);
        }
        StringBuffer sb=new StringBuffer();
        while (!q.isEmpty()){
            int node=q.poll();
            sb.append((char)(node+'a'));
            for(Integer adjNodes:adj.get(node)){
                indegree[adjNodes]--;
                if(indegree[adjNodes]==0)q.add(adjNodes);
            }
        }
        if(sb.length()!=V)return  "";
        return  sb.toString();
    }
}
