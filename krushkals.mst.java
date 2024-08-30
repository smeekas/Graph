import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class Disjoint{
    ArrayList<Integer> rank;
    ArrayList<Integer> parent;
    Disjoint(int n){
        rank=new ArrayList<>();
        parent=new ArrayList<>();
        for(int i=0;i<=n;i++){
            rank.add(0);
            parent.add(i);
        }
    }
    int findUParent(int n){
        if(parent.get(n)==n)return  n;
        int ul_p=findUParent(parent.get(n));
        parent.set(n,ul_p);
        return  ul_p;
    }
    void unionByRank(int u,int v){
        int ul_u=findUParent(u);
        int ul_v=findUParent(v);
        if(ul_u==ul_v)return;
        int ul_u_rank=rank.get(ul_u);
        int ul_v_rank=rank.get(ul_v);
        if(ul_u_rank<ul_v_rank){
            parent.set(ul_u,ul_v);
        }else if(ul_u_rank>ul_v_rank){

            parent.set(ul_v,ul_u);
        }else{
            // if both rank are same then we can do anything
            parent.set(ul_v,ul_u);
            int rankU=rank.get(ul_u_rank);
            rank.set(ul_u,rankU+1);
        }
    }

}

class  Pair{
    int src,des,wt;
    Pair(int src,int des,int wt){
        this.des=des;
        this.src=src;
        this.wt=wt;
    }
}

class Solution {
    static int spanningTree(int V, int E, List<List<int[]>> adj) {
        // logic behind krushkal's algorithm
        // we sort all edges by weight
        // we take edge with minimum weight first.
        // if edge do not introduce cycle we take it.
        // we explore all edge by same way.

        ArrayList<ArrayList<Pair>> g=new ArrayList<>();
        for(int i=0;i<V;i++){
            g.add(new ArrayList<>());
        }
        ArrayList<Pair> edges=new ArrayList<>();
        for(int i=0;i<V;i++){
            int src=i;
            for(int[] adjVertexes:adj.get(src)){
                int des=adjVertexes[0];
                int wt=adjVertexes[1];
                edges.add(new Pair(src,des,wt));

            }
        }
        Collections.sort(edges,(a, b)->a.wt- b.wt);

        int ans=0;
        int i=0;
        Disjoint dj=new Disjoint(V);
        while (i<edges.size()){
            // iterate over all edges
            Pair edge=edges.get(i);
            int src=edge.src;
            int des=edge.des;
            int wt=edge.wt;
            // findUParent from dsu will give us ultimate parent
            // all nodes from same component will have same ultimate parent.
            if(dj.findUParent(src)!=dj.findUParent(des)){
                // not same parent means adding this node will not introduce cycle.

                // if both ultimate parents are same then adding that edge will introduce cycle.
                
                dj.unionByRank(src,des);
                //unionByRank will connect src and des. so now src and des will have same ultimate parent.
                ans+=wt;
            }
            i++;
        }

        return  ans;
    }

}
