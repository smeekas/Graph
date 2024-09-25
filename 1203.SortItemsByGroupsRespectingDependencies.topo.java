import java.util.*;

class Solution {
    public int[] sortItems(int n, int m, int[] group, List<List<Integer>> beforeItems) {

        // criteria are
        // element should follow topo sort order (with beforeItems)
        // elements with same groups should be together

        // so ideally if element-A from group-X is before element-B from group-Y then  A->B for nodes and X->Y for groups.
        // so we can construct two graphs. one for nodes and one for groups.
        // what about nodes without group(-1)?  we will also consider them as separate group.

        HashMap<Integer,ArrayList<Integer>> nadj=new HashMap<>();  //for nodes
        HashMap<Integer,ArrayList<Integer>> gadj=new HashMap<>();  //for graphs

        for(int i=0;i<n;i++)nadj.putIfAbsent(i,new ArrayList<>());
        for(int i=0;i<m;i++)gadj.put(i,new ArrayList<>());

        int gCount=m;
        //total group count is m then whoever is -1 we will assign group m,m+1, m+2,....
        for(int i=0;i<group.length;i++){
            if(group[i]==-1){
                group[i]=gCount++;
                gadj.put(group[i],new ArrayList<>()); //create entry for that groups too
            }
        }


        for(int i=0;i<n;i++){

            for(int j=0;j<beforeItems.get(i).size();j++){
                int beforeNode=beforeItems.get(i).get(j);
                nadj.get(beforeNode).add(i);
                // beforeNode -> i

                if(group[beforeNode]!=group[i] ){
                    // if both nodes group are not same then we will also create new edge in group's graph

                        gadj.get(group[beforeNode]).add(group[i]);

                }
            }
        }
        List<Integer> ntopo=getTopo(nadj,n); //topo sort of nodes
        List<Integer> gtopo=getTopo(gadj,gCount); //topo sort of groups
        if(ntopo.size()!=n|| gtopo.size()!=gCount){
            // if topo sort do not contain all nodes means there is cycle either in nodes or graph.

            return  new int[]{};
        }

        // we need mapping of group to elements of group.
        // what we will do is we will traverse topo sort of nodes and for every node we will add node into node's groups mapping.
        // by this way for every group we will have all element of that group in topo sorted order. that is the reason behind using LinkedHashset.
        // LinkedHashSet maintain insertion order.
        // now we will traverse topo order of group and for every group we will add all nodes into answer.
        LinkedHashMap<Integer, LinkedHashSet<Integer>> groupMapping=new LinkedHashMap<>();
        List<Integer> ans=new ArrayList<>();

        for(int i=0;i<gCount;i++)groupMapping.put(i,new LinkedHashSet<>()); //for every group we create 
        for(Integer nnode:ntopo){
            groupMapping.get(group[nnode]).add(nnode);
        }
        for(Integer key:gtopo){

                ans.addAll(groupMapping.get(key));

        }
        return  ans.stream().mapToInt(i->i).toArray();
    }

    List<Integer> getTopo(HashMap<Integer,ArrayList<Integer>> adj,int n){
            int indegree[]=new int[n];
            for(Integer key:adj.keySet()){
                for(Integer adjNodes:adj.get(key)){
                    indegree[adjNodes]++;
                }
            }
            List<Integer> ans=new ArrayList<>();
        Queue<Integer> q=new ArrayDeque<>();
            for(int i=0;i<n;i++){
                if(indegree[i]==0) {
                    q.add(i);
                    ans.add(i);
                }
            }
            while (!q.isEmpty()){
                int node=q.poll();
                for(Integer adjNodes:adj.get(node)){
                    indegree[adjNodes]--;
                    if(indegree[adjNodes]==0) {
                        q.add(adjNodes);
                        ans.add(adjNodes);

                    }
                }
            }
            return  ans;
    }
}
