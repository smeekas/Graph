import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;

class Solution {

    public ArrayList<Integer> bfsOfGraph(int V, ArrayList<ArrayList<Integer>> adj) {
        boolean visited[]=new boolean[adj.size()]; // array to store which vertex is visited or not.
        ArrayList<Integer> ans=new ArrayList<>();  // store answer
        Queue<Integer> q=new ArrayDeque<>();  // queue for bfs

        q.add(0); //we start with 0th vertex.
        while (!q.isEmpty()){
                //we take one vertex from queue, mark it as visited, add it to answer & add all adjacent vertexes to queue.
            int currNode=q.remove();
            if(visited[currNode]){
                // if current vertex is already visited (it is possible that is current vertex is adjacent to vertex-x. and we have already processed adjacent of vertex-x) then we will skip it.
                continue;
            }
            visited[currNode]=true; // marking vertex as visited.
            ans.add(currNode);
            for(Integer nodes:adj.get(currNode)){
                // adding all adjacent vertexes to queue.
                q.add((nodes));
            }
        }
        return  ans;
    }
}
