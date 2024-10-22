import java.util.*;

class Solution {

    public List<List<Integer>> findCriticalAndPseudoCriticalEdges(int n, int[][] edges) {
        // first we find MST
        // after that we loop on all edges and ignore one edge at a time
        // if after ignoring edge, if new mst increases that means that edge is critical edge
        List<Pair> pairs= new ArrayList<>();
        for(int i=0;i<edges.length;i++){
            int src=edges[i][0];
            int des=edges[i][1];
            int wt=edges[i][2];
            pairs.add(new Pair(src,des,wt,i));
        }
        Collections.sort(pairs,(a,b)->a.wt-b.wt);;
        int total=mst(n,pairs,-1,true);

        List<List<Integer>> ans=new ArrayList<>();

        ans.add(new ArrayList<>());
        ans.add(new ArrayList<>());
        HashSet<Integer> pseudo=new HashSet<>();
        HashSet<Integer> critical=new HashSet<>();

        // if mst total increases by deleting edge => critical edge
        // if edge appear in one mst then it is pseudo critical
        for(int i=0;i<pairs.size();i++){
            int subtotal= mst(n,pairs,i,true);
            if(subtotal>total){
                // if after ignoring current edge, mst wt increases that means edge-i is critical
                critical.add(pairs.get(i).index);
                // i is critical
            }
        }
        // pseudo edge finding
        // pseudo edge is that appear in some MST, not all
        // so if edge is critical then it cannot be pseudo and if edge is pseudo then it cannot be critical.
        // because critical edge appear in all MST and pseudo edge do not.
        // 
        for(int i=0;i<pairs.size();i++){
            if(critical.contains(pairs.get(i).index))continue; // if edge is critical then ignore that edge for pseudo edge finding
            int subtotal= mst(n,pairs,i,false); // find mst but we must take edge-i.
            if(subtotal==total){
                // if after including current edge, mst wt remains same that means edge-i is pseudo critical.
                // from multiple msts, edge-i will find its palce in some of the msts.
                pseudo.add(pairs.get(i).index);
               
            }
        }
        ans.get(0).addAll(critical);
        ans.get(1).addAll(pseudo);
        return ans;
    }

    int mst(int n,List<Pair> edges,int edgeIndex,boolean ignore){
        DSU dj=new DSU(n);
        int ans=0;
        HashSet<Integer> takenEdges=new HashSet<>();
        if(ignore==false){
            // must take
            int src=edges.get(edgeIndex).src;
            int des=edges.get(edgeIndex).des;
            int wt=edges.get(edgeIndex).wt;
            dj.union(src,des);
            ans+=wt;
        }
        for(int i=0;i<edges.size();i++){
            if(i==edgeIndex )continue;
            int src=edges.get(i).src;
            int des=edges.get(i).des;
            int wt=edges.get(i).wt;

            if(dj.find(src)==dj.find(des)){

            }else{
                dj.union(src,des);
                ans+=wt;
                takenEdges.add(i);

            }
        }

        int components=0;
        for(int i=0;i<n;i++){
            if(dj.parent[i]==i){
                components++;
            }

        }
        if(components>1){
            // after ignoring edge, graph split into 2 part.
            // MST cannot be formed.
            return Integer.MAX_VALUE;
        }
        return  ans;

    }
}
class DSU{
    int parent[];
    int sizes[];
    DSU(int n){
        parent=new int[n];
        sizes=new int[n];
        for(int i=0;i<n;i++){
            sizes[i]=1;
            parent[i]=i;
        }
    }
    void union(int i,int j){
        int ui=find(i);
        int uj=find(j);
        int uiSize=sizes[ui];
        int ujSize=sizes[uj];
        if(ui==uj)return;
        int combined=uiSize+ujSize;
        if(uiSize>ujSize){
            parent[uj]=ui;
            sizes[ui]=combined;
        }else if(uiSize<ujSize){
            parent[ui]=uj;
            sizes[uj]=combined;
        }else{
            parent[ui]=uj;
            sizes[uj]=combined;
        }
    }
    int find(int i){
        if(parent[i]==i)return  i;
        return  parent[i]=find(parent[i]);
    }
}
class Pair{
    int src,des,wt,index;

    public Pair(int src, int des, int wt, int index) {
        this.src = src; //edge src
        this.des = des; //edge des
        this.wt = wt; //edge wt
        this.index = index; //edge index in given array
    }
}
