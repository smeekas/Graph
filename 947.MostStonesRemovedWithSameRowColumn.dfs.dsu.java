import java.util.ArrayList;

class Solution {
    public int removeStones(int[][] stones) {
        // logic
        // we can remove stone from coordinate if another stone is present in same row or column.
        // if we create group of stone wth above fact then we will get a bunch of groups.
        // from every group we can remove all stones except last one. because for last one no other stone is left
        // so we left with 1 stone from every group.
        //so we can remove (number of stones - total groups(1 stone left from every group)) stones!
        if(true){
            return  dsu(stones);
        }
        boolean visited[]=new boolean[stones.length];
        int components=0;
        for(int i=0;i<stones.length;i++){
            if(visited[i]==false){
                visited[i]=true;
                dfs(stones,visited,i);
                components++;
            }
        }
        return  stones.length-components;
    }
    int dsu(int[][] stones){
        int n=stones.length;
        Disjoint dj=new Disjoint(n);
        // every stone is node. so we track their indexes in dsu.
        
        for(int i=0;i<n;i++){
            for(int j=i+1;j<n;j++){
                if(dj.findUParent(i)==dj.findUParent(j)){
                    // already in same component
                }else{
                    // for stone i we check other stones.
                    // if they are in same row / column then we will union them. we can say that they are in same component.
                    if(stones[j][0]==stones[i][0]|| stones[j][1]==stones[i][1]){
                        dj.unionByRank(i,j);
                    }
                }
            }
        }
        int component=0;
        for(int i=0;i<n;i++){
            if(dj.parent.get(i)==i)component++;
        }
        return  n-component;
    }
    void dfs(int[][]stones,boolean []visited,int stoneI){
        int r=stones[stoneI][0];
        int c=stones[stoneI][1];

        for(int i=0;i<stones.length;i++){
            if((stones[i][0]==r || stones[i][1]==c) && visited[i]==false){
                visited[stoneI]=true;
                dfs(stones,visited,i);
            }
        }
    }
}

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
