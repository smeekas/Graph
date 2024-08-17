class Solution {
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        ArrayList<ArrayList<Integer>> adj=new ArrayList<>(numCourses);
        // construct adjacent matrix
        for(int i=0;i<numCourses;i++){
            adj.add(new ArrayList<>());
        }
        for(int i=0;i<prerequisites.length;i++){
            adj.get(prerequisites[i][1]).add(prerequisites[i][0]);
            
        }
        
        // below logic is same as finding wether cycle exists or not.
        // if cycle exists then we won't be able to take all course else we can.
        boolean[]visited=new boolean[numCourses];
        boolean[]samePath=new boolean[numCourses];
        for(int i=0;i<numCourses;i++){
                if(visited[i]==false){
                    visited[i]=true;
                    samePath[i]=true;
                    if(dfs(i,adj,visited,samePath)){
                        return  false;
                    }
                    samePath[i]=false;
                }
        }
        return  true;
    }
    boolean dfs(int currNode,ArrayList<ArrayList<Integer>> adj,boolean[]visited,boolean[]samePath){

        for (Integer adjNode:adj.get(currNode)){
                if(visited[adjNode]==false){
                    visited[adjNode]=true;
                    samePath[adjNode]=true;
                    if(dfs(adjNode,adj,visited,samePath)){
                        return  true;
                    }
                    samePath[adjNode]=false;
                }else{
                    if(samePath[adjNode])return  true;
                }
        }
        return  false;
    }
}
