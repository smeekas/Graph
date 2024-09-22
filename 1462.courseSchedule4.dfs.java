import java.util.*;

class Solution {
    public List<Boolean> checkIfPrerequisite(int numCourses, int[][] prerequisites, int[][] queries) {
        // brute-force is for every query we do bfs/dfs


        // prerequisites arrays says for [u,v]  to take course-v we must take course-u
        // course-u is prerequisite of course-v.
        // graph u->v, will not help us. because we want to find prerequisites only.
        // if graph is v->u then we can find prerequisite of course-v easily.

        //for every course running dfs/bfs is expensive. becase number of queries are higher.
        // so we can run dfs/bfs for every node and for every node we collect number of nodes we can reach. (number of nodes are comparatively less.)
        // number of nodes we can reach is basically prerequisites.
        // for while answering queries for ex. [u,v] (u is prerequisite of v?) for node-v whether we can reach node-u or not.
        ArrayList<ArrayList<Integer>> adj=new ArrayList<>();
        for(int i=0;i<numCourses;i++)adj.add(new ArrayList<>());
        for(int [] preq:prerequisites){
            int src=preq[0];
            int des=preq[1];
            adj.get(des).add(src);
        }
        HashMap<Integer, HashSet<Integer>> hm=new HashMap<>();  // for every node, how many nodes we can reach
        for(int i=0;i<numCourses;i++){
            HashSet<Integer> hs=new HashSet<>();
            boolean visited[]=new boolean[numCourses];
            visited[i]=true;
            dfs(i,adj,hs,visited);
            hm.put(i,hs);
        }
        List<Boolean> ans=new ArrayList<>();

        for(int q[]:queries){
            int src=q[0];
            int des=q[1];
            ans.add(hm.get(des).contains(src));
        }
        return ans;
    }
    void dfs(int src,ArrayList<ArrayList<Integer>> adj, HashSet<Integer> hs,boolean visited[]){
        for(Integer adjNode:adj.get(src)){
            if(visited[adjNode]==false){
                visited[adjNode]=true;
                hs.add(adjNode);
                dfs(adjNode,adj,hs,visited);
            }
        }
    }

}
