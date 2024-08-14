class Solution {
    public boolean isBipartite(int[][] graph) {
        // we will try to make sure that no two adjacent node have same color.
        // that way we can make two set where 1 edge connect two vertices from both set (bipartite)
        int  colors[]=new int[graph.length];  //colors array (will also act as visited array)
        Arrays.fill(colors,-1);
        // we will do traversal instead of search. (graph can be disconnected)
        for(int i=0;i<graph.length;i++){
            if(colors[i]==-1){

//                boolean res=  bfs(i,graph,colors);
//                if(!res)return  res;
                boolean res=  dfs(-1,i,graph,colors);  //-1 parent so that node will have color-0 when we add +1
                // if graph is not bipartite return false.
                if(!res)return  res;
            }
        }



        return true;

    }
    boolean bfs(int curr,int[][]graph,int colors[]){
        Queue<Integer> q=new ArrayDeque<>();
        q.add(curr);
        colors[curr]=1; //assume current node have color-1
        while (!q.isEmpty()){
            int currNode=q.poll();
            for(Integer adjNodes:graph[currNode]){
                if(colors[adjNodes]==-1){
                    // if adjacent node is not visited then assign color-0 & add to queue
                    colors[adjNodes]=(colors[currNode]+1)%2;
                    //(color+1)%2
                    // 0 => (0+1)%2 => 1 & 1 => (1+1)%2 =>0
                    q.add(adjNodes);
                }else{
                    // if adjacent node is visited & it's color is same as current node's color means graph cannot be bipartite.
                    // so return false
                    if(colors[adjNodes]==colors[currNode])return  false;

                }
            }
        }

        return  true;
    }

    boolean dfs(int parentColor,int curr,int[][]graph,int colors[]){

        colors[curr]=(parentColor+1) %2;
        for(Integer adjNodes:graph[curr]){
            if(colors[adjNodes]==-1){
                boolean result=dfs(colors[curr],adjNodes,graph,colors);
                if(!result)return  result;
            }else{
                if(colors[adjNodes]==colors[curr]){
                    return  false;
                }
            }
        }
        return  true;
    }
}
