import java.util.*;

class Solution {
    public boolean isPrintable(int[][] targetGrid) {
        // from target grid first we can find rectangle of particular number (number-x).
        // if any other number(number-y) exists inside that rectangle then we have a dependency. number-X --> number-y

        // to find rectangle of particular node, we find maxColIndex, minColIndex, maxRowIndex, minRowIndex.

        HashMap<Integer,Desc> hs=new HashMap<>();
        HashSet<Integer> unique=new HashSet<>();  // collection of all unique nodes.
        int n=targetGrid.length,m=targetGrid[0].length;
        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                int node=targetGrid[i][j];
                unique.add(node);
                if(hs.containsKey(node)){
                    Desc desc=hs.get(node);
                    hs.get(node).maxCol=Math.max(desc.maxCol,j);
                    hs.get(node).minCol=Math.min(desc.minCol,j);
                    hs.get(node).minRow=Math.min(desc.minRow,i);
                    hs.get(node).maxRow=Math.max(desc.maxRow,i);
                }else{
                    // since this is new node, max and min row are same  & max and min column are same
                    hs.put(node,new Desc(j,j,i,i));
                }
            }
        }
        HashMap<Integer,ArrayList<Integer>> adj=new HashMap();
        int indegree[]=new int[61];  // at max there can we 1-60 numbers in grid

        for(int node:hs.keySet()){
            Desc desc=hs.get(node);
            for(int i=desc.minRow;i<= desc.maxRow;i++){
                for(int j=desc.minCol;j<= desc.maxCol;j++){

                    int currNode=targetGrid[i][j];

                    // if in node's rectangle we find any other node then we will add edge.
                    if(node!=currNode){
                        if(adj.containsKey(node)){
                            adj.get(node).add(currNode);
                            // node->currNode.
                            // we need to print node first and then currNode.
                            indegree[currNode]++;
                        }else{
                            adj.put(node,new ArrayList<>());
                            adj.get(node).add(currNode);
                            indegree[currNode]++;
                        }
                    }
                }
            }
        }
        // after creating graph we now just need to do topo sort.
        Queue<Integer> q=new ArrayDeque<>();
        int ansNodes=0;
        for(int i=0;i<indegree.length;i++) {
            if (indegree[i] == 0 && unique.contains(i)) {
                // if indegree is zero and actual number exists then
                q.add(i);
                ansNodes++;
            }
        };
        
        while (!q.isEmpty()){
            int node=q.poll();
            if(!adj.containsKey(node))continue; //if node do not exists in adj then move on.
            for(Integer adjNodes:adj.get(node)){
                indegree[adjNodes]--;
                if(indegree[adjNodes]==0){
                    q.add(adjNodes);
                    ansNodes++;
                }
            }
        }
         // if topo sort contain all nodes then we can print it. else there must exists cycle so we won't be able to print it.
        return  ansNodes==unique.size();
    }
}
class Desc{
    int minRow,maxRow,minCol,maxCol;

    public Desc(int maxCol, int minCol, int maxRow, int minRow) {
        this.maxCol = maxCol;
        this.minCol = minCol;
        this.maxRow = maxRow;
        this.minRow = minRow;
    }

    @Override
    public String toString() {
        return "Desc{" +
                "minRow=" + minRow +
                ", maxRow=" + maxRow +
                ", minCol=" + minCol +
                ", maxCol=" + maxCol +
                '}';
    }
}
