class Solution {
    public int[] findRedundantConnection(int[][] edges) {


        // graph contain mst+ 1 edge
        // do kruskal and track which edge is taken
        // return edge which is not taken
        // if all edges are taken, return empty array

        int max=0;
        for(int[] ed:edges)max=Math.max(ed[1],Math.max(max,ed[0]));  //find maximum node-index
        DSU dj=new DSU(max); //create disjoint set

        boolean taken[]=new  boolean[max+1];
        for(int i=0;i<edges.length;i++){
            int []edge=edges[i];
            int src=edge[0],des=edge[1];
            if(dj.find(src)!=dj.find(des)){
                dj.union(src,des);
                taken[i]=true;
            }

        }
        for(int i=0;i<=max;i++){
            if(taken[i]==false){
                return  edges[i];
            }
        }
        return new int[]{};
    }
}
class DSU{
    int parent[];
    int size[];
    DSU(int n){
        parent=new int[n+1];
        size=new int[n+1];
        for(int i=0;i<=n;i++){
            parent[i]=i;
            size[i]=1;
        }
    }
    void union(int i,int j){

        int ui=find(i);
        int uj=find(j);
        int ui_size=size[ui];
        int uj_size=size[uj];
        if(ui==uj)return;
        if(ui_size>uj_size){
            parent[uj]=ui;
            size[ui]+=uj_size;
        }else{
            parent[ui]=uj;
            size[uj]+=ui_size;
        }
    }
    int find(int i){
        if(parent[i]==i)return i;
        parent[i]=find(parent[i]);
        return  parent[i];
    }

}
