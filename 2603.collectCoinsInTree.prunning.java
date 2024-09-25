import java.util.*;

class Solution {
    public int collectTheCoins(int[] coins, int[][] edges) {
        // idea is first of all we prune all leaves without coins because they will not help us.
        // from remaining tree, from maximum path between two leaves, we need to go through (max-path)-2-2.
        // but but but
        // from all leaves with coin we will remove edge {leaf to parent} and another edge {parent to it's parent}.
        // because we can collect coins from two steps away. so we don't need this edges.
        // final answer will be remaining edges
        int n=edges.length;
        HashMap<Integer, HashSet<Integer>> adj=new HashMap<>();
        for(int[] edge:edges){
            int src=edge[0];
            int des=edge[1];
            adj.putIfAbsent(src,new HashSet<>());
            adj.putIfAbsent(des,new HashSet<>());
            adj.get(src).add(des);
            adj.get(des).add(src);
        }
        Queue<Integer> q=new ArrayDeque<>();
        for(int key: adj.keySet()){
            if(adj.get(key).size()==1 && coins[key]==0){
                // leaf
                q.add(key);
            }
            // add node if it is leaf and it don't contain coin. we will prune them
        }
        int deleted=0;
        while (!q.isEmpty()){
            int node=q.poll();
            if(!adj.get(node).iterator().hasNext())continue;  // if node do not have adj (safety condition)
            int adjNode=adj.get(node).iterator().next(); // adj node of leaf
            adj.get(node).clear(); // remove leaf to parent
            adj.get(adjNode).remove(node); // remove parent to leaf
            deleted+=2; // since it is undirected graph. each edge is counted twice.
            if(adj.get(adjNode).size()==1 && coins[adjNode]==0){
                // if parent node also become leaf
                // and if it don't CONTAIN COIN then add it in queue for pruning.
                q.add(adjNode);
            }
        }
        //  now graph do not contain useless nodes.
        // so from all coin nodes we will remove one edge.
        //from coin's parent we will remove one edge.
        // that is because we can reach coin from 2 step away. so we don't need them
        for(int key: adj.keySet()){
            if(adj.get(key).size()==1){
                q.add(key);
            }
        }

        // now remove coin leaves
        int steps=2;
        while (steps-->0){
            List<Integer> sub=new ArrayList<>();
            while (!q.isEmpty()){
                int node=q.poll();
                if(!adj.get(node).iterator().hasNext())continue;
                int adjNode=adj.get(node).iterator().next();
                adj.get(adjNode).remove(node);
                adj.get(node).clear();
                deleted+=2;
                if(adj.get(adjNode).size()==1)sub.add(adjNode); // add all coin's parent to separate list
            }
            // now add all parent to queue for removal.
            q.addAll(sub);
        }
        return n*2 - deleted;
    }
}

