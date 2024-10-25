import java.util.*;

class Solution {
    public int[] countVisitedNodes(List<Integer> edges) {
        // every node has one outgoing edge.
        // nodes in cycle can visit {cycle-length} nodes
        //other nodes will be coming towards cycle.
        // so those nodes can visit {node-to-cycle} nodes + {cycle-length} nodes

        //graph can be disconnected too
        // what we can do is
        // we first answer nodes which are in cycle -> answer will be {cycle-length}
        int n=edges.size();
        int ind[]=new int[n];
        for(int i:edges){
            ind[i]++;
        }
        Queue<Integer> q=new ArrayDeque<>();
        ArrayList<Integer> zeroNodes=new ArrayList<>(); // needed for non-cycle nodes
        for(int i=0;i<n;i++){
            if(ind[i]==0) {
                q.add(i);
                zeroNodes.add(i);
            }
        }
        while (!q.isEmpty()){
            int node=q.poll();
            int adjNode=edges.get(node);
            ind[adjNode]--;
            if(ind[adjNode]==0)q.add(adjNode);
        }
        // graph can be disconncted and we may have >1 cycles
        boolean visited[]=new boolean[n];
        int ans[]=new int[n];
        for(int i=0;i<n;i++){
            if(visited[i]==false && ind[i]>0){
                // if node is not visited, and it is part of cycle (ind[i]>0)
                // from this node we travel the cycle and get length of cycle
                visited[i]=true;
                int newNode=edges.get(i); //adjNode
                int count=1; // cycle length
                ArrayList<Integer> nodes=new ArrayList<>();
                nodes.add(i); // nodes which are part of cycle
                while (visited[newNode]==false){
                    // loop until we reach visited node(node from where we started)
                    visited[newNode]=true;
                    count++;
                    nodes.add(newNode); 
                    newNode=edges.get(newNode);
                }
                // now nodes contain all nodes which are part of cycle
                for(int node:nodes){
                    ans[node]=count;
                }

            }
        }

        // from all nodes which have 0 in-degree, we apply DFS
        for(int node:zeroNodes){
            if(visited[node]==false){
                visited[node]=true;
                // 1 for current node, dfs for child nodes.
                ans[node]=1+dfs(edges,node,visited,ans);
            }
        }
        return  ans;

    }
    int dfs(List<Integer> edges,int currNode,boolean visited[],int ans[]){
        int adjNode=edges.get(currNode);
        if(visited[adjNode]==false){
            visited[adjNode]=true;
            // if this child node is not visited then we apply dfs
            // 1 for current node, dfs for child nodes
            ans[adjNode]=1+dfs(edges,adjNode,visited,ans);
            return  ans[adjNode];
        }else{
            // if this child is already visited then we just return the answer.
            return  ans[adjNode];
        }
    }

}
