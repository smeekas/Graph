import java.util.*;

class Solution {
    public int[] loudAndRich(int[][] richer, int[] quiet) {


        // in richer [x,y] => x is richer than y.
        //what we want is for person-x, lowest quiet value among all richer persons than x.
        // now for richer array [x,y], if we construct graph x -> y way that means x is richer than y.
        // we cannot get who is richer than y. we can get who is poorer than x.
        // so we will construct graph in reverse order. for richer [x,y] y -> x  (x is richer than y)

        // now for every person we traverse graph, check who are richer and then take minimum value of all richer person then we will get answer.
        // since constraint are smaller (at max 500 nodes) we can apply above method.
        int n=quiet.length;
        HashMap<Integer, HashSet<Integer>> hm=new HashMap<>();
        ArrayList<ArrayList<Integer>> adj=new ArrayList<>();
        for(int i=0;i<n;i++)adj.add(new ArrayList<>());
        for(int[] rich:richer){
            int src=rich[0],des=rich[1];
            adj.get(des).add(src);
            // graph will be directed.
        }
        int ans[]=new int[n];
        for(int i=0;i<n;i++){
            // for every node, we traverse graph starting from node-i.
            // we keep track of lowest quiet value.
            boolean visited[]=new boolean[n];
            visited[i]=true;
            Queue<Integer> q=new ArrayDeque<>();
            int quietest=quiet[i];
            // if no richer person exists then we have to return quietness value of current node.
            int quietestNode=i;
            q.add(i);
            while (!q.isEmpty()){
                int node=q.poll();
                for(Integer adjNodes:adj.get(node)){
                    if(visited[adjNodes]==false){
                        visited[adjNodes]=true;
                        if(quiet[adjNodes]<quietest) {
                            quietest = quiet[adjNodes];
                            quietestNode=adjNodes;
                        }
                            q.add(adjNodes);

                    }
                }
            }
            ans[i]=quietestNode;
        }
        return ans;
    }
}
