import java.util.*;

class Solution {
    public List<List<String>> accountsMerge(List<List<String>> accounts) {

        Disjoint dj=new Disjoint(accounts.size());
        HashMap<String,Integer> hm=new HashMap<>();
        // we want to merge accounts not email.
        // so I think we have to union accounts.
        // we want to union account by looking at emails.

        for(int accIndex=0;accIndex<accounts.size();accIndex++){
            int accountSize=accounts.get(accIndex).size();
            for(int i=1;i<accountSize;i++){
                // we will iterate over emails.
                // we put email and accountIndex into map
                String  email=accounts.get(accIndex).get(i);
                if(hm.containsKey(email)){
                    // if email already exists in map then it means then this email is part of current account (accIndex) and also account from map.
                    // so we should union them
                    dj.unionByRank(hm.get(email),accIndex);
                }else{
                    hm.put(email,accIndex);
                }
            }
        }
        // now due to some of the email that are part of different account we have unioned them.
        // so dsu contain information about merged accounts.
        HashMap<Integer, TreeSet<String>> accToEmail=new HashMap<>();
        // we map accIndex with emails. (merged accounts)
        for(int i=0;i<accounts.size();i++){
            int parentAccIndex=dj.findUParent(i);
            // for every ith account we get its ultimate parent
            // if ultimate parent do not exist in map,
            // we add all email of ith account into ultimate parent's key.
            //else we add all email of ith account into ultimate parent's key.
            //reason of using treeset is because we do not want duplication, and we want it in ordered manner.
            int size=accounts.get(i).size();

            if(accToEmail.containsKey(parentAccIndex)){
                accToEmail.get(parentAccIndex).addAll(accounts.get(i).subList(1,size));
            }else{
                TreeSet<String> allEmails=new TreeSet<>();
                allEmails.addAll(accounts.get(i).subList(1,size));
                accToEmail.put(parentAccIndex,allEmails);
            }
        }
        List<List<String>> ans=new ArrayList<>();
        for (Integer entry : accToEmail.keySet()) {
            
            List<String> subList=new ArrayList<>();
            // for every unique user account index to email...
            //we get it's name
            subList.add(accounts.get(entry).get(0));
            
            //we add all collected emails.
            subList.addAll((accToEmail.get(entry)));
            // add it to ans.
            ans.add(subList);
        }
        return ans;
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

