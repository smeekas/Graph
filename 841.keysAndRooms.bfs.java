import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;

class Solution {
    public boolean canVisitAllRooms(List<List<Integer>> rooms) {
        // in this problem we have to see if all nodes are reachable from 0 or not.
        Queue<Integer> q=new ArrayDeque<>();
        boolean visited[]=new boolean[rooms.size()];
        q.add(0);
        visited[0]=true;
        // bfs from node 0
        while (!q.isEmpty()){
            int node=q.poll();
            for(Integer adjNodes:rooms.get(node)){
                if(visited[adjNodes]==false){
                    visited[adjNodes]=true;
                    q.add(adjNodes);
                }
            }
        }
        
        for(boolean isVisited:visited){
            // if any node is still not visited that means we cannot reach all node from node-0.
            if(isVisited==false)return  false;
        }
        return  true;
    }

}
