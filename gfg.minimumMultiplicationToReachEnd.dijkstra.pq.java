class Pair{
    int des, wt;
    Pair(int des,int wt){
        this.des=des;
        this.wt=wt;
    }
}
class Solution {
    int minimumMultiplications(int[] arr, int start, int end) {
        
        // from start we  create node by multiplying with every number in array.
        // put them in queue.
        // this is like bfs (level by level distance is increased by 1) 
        
        // we can have maximum 100000 nodes (mod 100000)
        int distance[]=new int[100000];
        PriorityQueue<Pair> pq=new PriorityQueue<>((a, b)->a.wt-b.wt);
        Arrays.fill(distance,(int)1e9);
        distance[start]=0;
        pq.add(new Pair(start,0));
        while (!pq.isEmpty()){
            Pair nodePair=pq.poll();
            int node=nodePair.des;
            int wt=nodePair.wt;
            for(int i=0;i<arr.length;i++){
                int newNode=node*arr[i] % 100000;
                // create new node.
                // if new distance is lesser than current distance then update it & put new node in queue.
                if(wt+1 < distance[newNode]){
                    distance[newNode]=wt+1;
                    pq.add(new Pair(newNode,wt+1));
                }
            }
        }

        return distance[end]==(int)1e9?-1:distance[end];
    }
}

