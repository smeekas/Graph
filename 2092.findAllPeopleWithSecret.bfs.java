import java.util.*;

class Solution {
    public List<Integer> findAllPeople(int n, int[][] meetings, int firstPerson) {
    if(true)return  optimal(n,meetings,firstPerson);
        // below is naive approach.
        // perfectly valid but time-consuming (TLE)
        // intuition
        //we could have sorted the meetings by time and first iterate meetings then if src or des knows secret then mark other true. but problem is
        // multiple meetings can be happening at the same time, in that case they will know secret.
        //ex.
        // [5,2,4],[2,3,4]
        // let say 3 knows secret.
        // in first meeting we will mark 5 & 2 as false (do not know secret)
        // in second meeting 2 will be true.
        // our answer is 2 & 3, but actually it is 5,2,3.
        // since meeting is taking place at the same time 5 also should know the answer.
        // our solution for the above problem is that we track map of at what time which meeting is happening.
        //for time 4 -> {5,2}, {2,3}
        //if at time 4 any person knows secret then all connected people should know secret.


        Arrays.sort(meetings,(a,b)->a[2]-b[2]); // sort by time.
        List<Integer> ans=new ArrayList<>();
        TreeMap<Integer,ArrayList<Node>> tm=new TreeMap<>(); //treemap because we want times to be ordered.

        for(int[]meeting:meetings){
            int src=meeting[0];
            int des=meeting[1];
            int wt=meeting[2];
            // add meetings to particular time.
            if(tm.containsKey(wt)){
                tm.get(wt).add(new Node(src,des));
            }else{
                tm.put(wt,new ArrayList<>());
                tm.get(wt).add(new Node(src,des));
            }
        }
        boolean knows[]=new boolean[n];
        knows[0]=true;
        knows[firstPerson]=true;
        //initially 0 and firstPerson knows secret.

        for(Integer wt:tm.keySet()){

            Queue<Integer> q=new ArrayDeque<>();
            HashMap<Integer,ArrayList<Integer>> adj=new HashMap();
            // from every meeting at time wt, we construct adj matrix.
            for(Node nodes:tm.get(wt)){

                // meeting is bidirectional, so we add edge both the way.

                if(adj.containsKey(nodes.src)){
                    adj.get(nodes.src).add(nodes.des);
                }else{
                    adj.put(nodes.src,new ArrayList<>());
                    adj.get(nodes.src).add(nodes.des);
                }

                if(adj.containsKey(nodes.des)){
                    adj.get(nodes.des).add(nodes.src);
                }else{
                    adj.put(nodes.des,new ArrayList<>());
                    adj.get(nodes.des).add(nodes.src);
                }

                // if any node from meeting knows secret then that node will be starting node for us.
                if(knows[nodes.src])q.add(nodes.src);
                if(knows[nodes.des])q.add(nodes.des);
            }
            // simple bfs
            while (!q.isEmpty()){
                int currNode=q.poll();
                for(Integer adjNodes:adj.get(currNode)){
                    if(knows[adjNodes]==false){
                        knows[adjNodes]=true;
                        q.add(adjNodes);
                    }
                }
            }



        }
        for(int whoKnows=0;whoKnows<n;whoKnows++){
            // whoever is marked as visited will be in our answer!
            if(knows[whoKnows])ans.add(whoKnows);
        }
        return ans;
    }
    List<Integer> optimal(int n, int[][]meetings,int firstPerson){

        // IN optimal way we do only one bfs/dfs.
        // from source node we go to adjacent node only if adjNode's meeting time is >= then source node's meeting time.
        // this way we ensure that adjacent node knows secret only after source node knows secret.


        ArrayList<ArrayList<Pair>> adj=new ArrayList<>();
        for(int i=0;i<n;i++){
            adj.add(new ArrayList<>());
        }
        for(int[]meeting:meetings){
            int src=meeting[0];
            int des=meeting[1];
            int wt=meeting[2];
           adj.get(src).add(new Pair(des,wt));
            adj.get(des).add(new Pair(src,wt));
        }

        int visited[]=new int[n];

        Arrays.fill(visited,Integer.MAX_VALUE);
        // we want minimum time, so we fill array with max values.
        visited[0]=0;
        visited[firstPerson]=0;
        Queue<Pair> q=new ArrayDeque<>();

        q.add(new Pair(0,0));
        q.add(new Pair(firstPerson,0));

        while (!q.isEmpty()){
            Pair currNode=q.poll();
            for(Pair adjNodes:adj.get(currNode.des)){
                // adjacent node's meeting time >=parent's meeting time
                // we also have another condition.
                // if we can know secret earlier than stored time, then we can also add new minimum time and explore it's adjacent with lesser time.
                if(adjNodes.wt>=currNode.wt && adjNodes.wt< visited[adjNodes.des]){
                    visited[adjNodes.des]=adjNodes.wt;
                    q.add(new Pair(adjNodes.des,adjNodes.wt));
                }

            }
        }

        List<Integer> ans=new ArrayList<>();
        for(int whoKnows=0;whoKnows<n;whoKnows++){
            // whoever is marked as visited will be in our answer!
            if(visited[whoKnows]!=Integer.MAX_VALUE)ans.add(whoKnows);
        }
        return ans;
    }
}

class Node{
    int src, des;
    Node(int src,int des){
        this.src=src;
        this.des=des;
    }
}
class  Pair{
    int  des,wt;
    Pair(int des,int wt){
        this.des=des;
        this.wt=wt;
    }
}
