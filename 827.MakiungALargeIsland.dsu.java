import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

class Solution {
    int[] xd ={0,0,1,-1};
    int[] yd ={1,-1,0,0};
    public int largestIsland(int[][] grid) {
        // we will use DSU.
        // below logic will clear doubts.
        // we first go through grid, and connect ones.
        // we use union by Size so that we can track size of an island.
        int n=grid.length;
        int m=grid[0].length;
        Disjoint dj=new Disjoint(n*m);
        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                if(grid[i][j]==1){
                    // if cell is island
                    for(int k=0;k<4;k++){
                        int ni=i+xd[k];
                        int nj=j+yd[k];
                        if(isValid(ni,nj,n,n) && grid[ni][nj]==1){
                            // if neighbour cell is island

                            int index=getIndex(i,j,n),newIndex=getIndex(ni,nj,n);
                            if(dj.findUParent(index)!=dj.findUParent(newIndex)){
                                // if current node and neighbour node are not connected then connect them
                                // if they have same ultimate parent that means they are already connected.
                                // so no point in connecting  them again
                                    dj.unionBySize(index,newIndex);
                            }
                        }
                    }
                }
            }
        }
        int maxAns= Collections.max(dj.size); // maximum island that exists.
        //this is answer if we are not able to make larger island.

        //now we go through node-0,
        // check neighbour's of cell,
        // count area of them
        // and see if it is maximum then we count it as our new answer.
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                    if(grid[i][j]==0){
                        // reason for using hashmap.
                        // 2 neighbour can be part of same group too.
                        // so we calculate unique neighbour's area
                        HashMap<Integer,Integer> hm=new HashMap<>();
                        int localAns=1; // current node's area 1.
                        for(int k=0;k<4;k++){
                            int ni=i+yd[k];
                            int nj=j+xd[k];
                            if(isValid(ni,nj,n,n) && grid[ni][nj]==1){
                                // if neighbour is valid island
                                int newIndex=getIndex(ni,nj,n);
                                int u_parent=dj.findUParent(newIndex);
                                if(!hm.containsKey(u_parent)){
                                    // if neighbour do not exist in map then add new entry with its size. 
                                    hm.put(u_parent,dj.size.get(u_parent));
                                }
                            }
                        }
                        for(int key:hm.keySet()){
                            //sum all neighbour's area. 
                                localAns+=hm.get(key);
                        }
                        // add it if it is max.
                        maxAns=Math.max(maxAns,localAns);
                    }
            }
        }
        return  maxAns;

    }
    boolean isValid(int r,int c,int rows,int columns){
        return  r>=0 && c>=0 && r<rows && c<columns;
    }
    int getIndex(int r,int c,int columns){
        return  r*columns+c;
    }
}

class Disjoint{
    ArrayList<Integer> rank;
    ArrayList<Integer> parent;
    ArrayList<Integer> size;
    Disjoint(int n){
        rank=new ArrayList<>();
        parent=new ArrayList<>();
        size=new ArrayList<>();

        for(int i=0;i<=n;i++){
            rank.add(0);
            parent.add(i);
            size.add(1);
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
    void unionBySize(int u,int v){
        int ul_u=findUParent(u);
        int ul_v=findUParent(v);
        if(ul_u==ul_v)return;

        int ul_u_size=size.get(ul_u);
        int ul_v_size=size.get(ul_v);
        if(ul_v_size<ul_u_size){
            parent.set(ul_v,ul_u);
            int newSizeOfU=size.get(ul_v)+size.get(ul_u);
            size.set(ul_u,newSizeOfU);
        }else if(ul_v_size>ul_u_size){
            parent.set(ul_u,ul_v);
            int newSizeOfV=size.get(ul_v)+size.get(ul_u);
            size.set(ul_v,newSizeOfV);
        }else{
            parent.set(ul_u,ul_v);
            int newSizeOfV=size.get(ul_v)+size.get(ul_u);
            size.set(ul_v,newSizeOfV);
        }
    }

}
