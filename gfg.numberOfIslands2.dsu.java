class Solution {
    int xd[]={0,0,1,-1};
    int yd[]={1,-1,0,0};
    public List<Integer> numOfIslands(int rows, int cols, int[][] operators) {
        // since graph is changing and we want answer at change we will use DSU.
        // bruteforce like solution will be after every node, do dfs/bfs to calculate number of islands.
        List<Integer> ans=new ArrayList<>();
        Disjoint dj=new Disjoint(rows*cols);
        // we treat every cell in matrix as node.

        int[][] matrix=new int[rows][cols];

        for(int row[]:matrix){
            Arrays.fill(row,-1);
            // -1 means no node. 1 means node.
        }
        int islands=0;
        // after adding new node, number of nodes can increase and can decrease too.
        // we can have 3 islands and adding one connecting node can make number of island 1.
        // logic
        // by adding new island we always increase count.
        // but....
        // if neighbour node exists then we connect our node with neighbour.
        // now we already have count of neighbour node, and current node is part of neighbour so we decrease count by 1.
        // now assume there is another neighbour.
        // that neighbour can be entirely different neighbour or it is also part of previous neighbour.
        // if it is different neighbour then that means now previous neighbour and this new neighbour will be connected by current node.
        // so we union current node and new neighbour. and decrease count by 1.

        // if another neighbour is part of previous neighbour then current node's ultimate parent and this neighbour's ultimate parent will be same. we will not make connection, and we will not decrease count.
        for(int i=0;i<operators.length;i++){
            int r=operators[i][0];
            int c=operators[i][1];

            if(matrix[r][c]==1){
                // if node is already visited then collect answer and go to next iteration.
                ans.add(islands);
                continue;
            }
            
            islands++; // increase count
            matrix[r][c]=1;  //mark node as visited.
            for(int j=0;j< xd.length;j++){
                int nr=r+xd[j];
                int nc=c+yd[j];
                if(isValid(nr,nc,rows,cols) && matrix[nr][nc]==1){
                    // if at nr,nc node exists...

                    int newIndex=getIndex(nr,nc,cols);
                    int index=getIndex(r,c,cols);
                if(dj.findUParent(index)!=dj.findUParent(newIndex)){
                    // if ultimate parent are not same means both node are part of different groups.
                    // so we have to merge them.
                    islands--;
                    dj.unionByRank(index,newIndex);
                    }
                }

            }

            ans.add(islands);
        }
        return  ans;
    }
    boolean isValid(int r,int c,int rows,int cols){
        return  r>=0 && c>=0 && r<rows && c<cols;
    }
    int getIndex(int r,int c,int cols){
        return r*cols+c;
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

