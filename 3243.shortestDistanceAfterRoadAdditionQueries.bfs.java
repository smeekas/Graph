import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

class Solution {
    public int[] shortestDistanceAfterQueries(int n, int[][] queries) {

        ArrayList<ArrayList<Integer>> adj=new ArrayList<>();
        for(int i=0;i<n;i++)adj.add(new ArrayList<>());

        for(int i=1;i<n;i++){
            adj.get(i-1).add(i);
            // initial graph
        }
        int visited[]=new int[n];
        for(int i=0;i<n;i++){
            visited[i]=i;
            // shortest path to node-i is i.
        }
        
        List<Integer> ans=new ArrayList<>();
        // for every query we will do dijkstra from src of newly added node.
        // bfs also works fine since graph is made of unit weighted edge.
        
        for(int [] query:queries){
            int src=query[0],des=query[1];
            adj.get(src).add(des);
            Queue<Integer> q=new ArrayDeque<>();
            q.add(src);
            while (!q.isEmpty()){
                int currNode=q.poll();
                for(int adjNodes:adj.get(currNode)){
                    if(visited[currNode]+1<visited[adjNodes]){
                        visited[adjNodes]=visited[currNode]+1;
                        q.add(adjNodes);
                    }
                }
            }
            ans.add(visited[n-1]);
        }
        int ansArr[]=new int[ans.size()];
        for(int i=0;i<ansArr.length;i++){
            ansArr[i]=ans.get(i);
        }
        return ansArr;
    }
}
