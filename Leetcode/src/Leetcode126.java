import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class Leetcode126 {
    public List<List<String>> findLadders(String beginWord, String endWord, List<String> wordList) {
       
        //Using a set instead of list because search time of set is omega(1)
        Set<String> wordSet = new HashSet<>();
        Set<String> done = new HashSet<>();
        for (int i = 0 ;i < wordList.size(); i++) {
            wordSet.add(wordList.get(i));
        }
        
        //if we cannot find endWord in list, then a map is not possible
        if (!wordSet.contains(endWord)) {
            return new ArrayList<>();
        }
        wordList.add(beginWord);
        
        //Preprocess all input string to find all words at a distance of one.
        //An easy way to do this replace character by * and substitute letter in this place to find words at a distance of 1
        Map<String, HashSet<String>> map = new HashMap<>();
        for (int i = 0; i < wordList.size(); i++) {
            String inp = wordList.get(i);
            for (int j = 0 ;  j < inp.length(); j++) {
                String modified = inp.substring(0,j) + "*" + inp.substring(j+1, inp.length());
                for (int k = 0 ; k < wordList.size(); k++) {
                    String adjWord = wordList.get(k);
                    if ( modified.equals(adjWord.substring(0,j) + "*" + adjWord.substring(j+1, adjWord.length()))) {
                        HashSet<String> s = map.getOrDefault(modified, new HashSet<>());
                        s.add(adjWord);
                        map.put(modified, s);
                    } 
                }

            }
        }
   
    //Implement bfs to find all at distance of one, add all to queue and repeat same for next level
    Map<String, Set<String>> adjMap = new HashMap<>();
         Map<String, Set<String>> rMap = new HashMap<>();
    Queue<String> queue = new ArrayDeque<>();
    int count = 1;
    int k = 1;
    queue.add(beginWord);
    boolean found = false;
    int level = 0;
    while (!queue.isEmpty() && !found) {
        level++;
        count = k;
        k = 0;
        Set<String> toRemove = new HashSet<>();
        for (int i = 0; i < count; i++) {
            String inp = queue.poll();
            wordSet.remove(inp);
            if (!done.contains(inp)) {
                 done.add(inp);
            for (int j = 0 ; j < inp.length(); j++) {
                 String modified = inp.substring(0,j) + "*" + inp.substring(j+1, inp.length());
                if (map.containsKey(modified)) {
                    for (String s : map.get(modified)) {
                        if (!s.equals(inp)) {
                            if (wordSet.contains(s) || s.equals(endWord)) {
                               // wordSet.remove(s);
                                if (s.equals(endWord)) {
                                    found = true;
                                }
                                queue.add(s);
                                toRemove.add(s);
                                k++;
                                Set<String> mSet =  adjMap.getOrDefault(inp, new HashSet<>());
                                mSet.add(s);
                                adjMap.put(inp, mSet);
                                
                                 Set<String> mSet1 =  rMap.getOrDefault(s, new HashSet<>());
                                mSet1.add(inp);
                                rMap.put(s, mSet1);
                            }
                        }
                    }
                }
            }
        }
        }
         for (String s : toRemove) {
               wordSet.remove(s);
        }
    }
        
        
      //recursively search for all possible paths
        List<List<String>> ret = rec(beginWord, level, 0, endWord, rMap);  
        for (int i = 0 ; i < ret.size(); i++) {
             Collections.reverse(ret.get(i));
        }
        return ret;
    }

    public List<List<String>> rec(String endWord, int currlevel, int maxLevel,
                                 String currWord,  Map<String, Set<String>> adjMap) {
        if (currlevel == maxLevel) {
            if(endWord.equals(currWord)) {
                List<List<String>> ret = new ArrayList<>();
                List<String> l = new ArrayList<>();
                l.add(endWord);
                ret.add(l);
                return ret;
            } else {
                return null;
            }
        } else {
            List<List<String>> ret = new ArrayList<>();
            currlevel = currlevel - 1;
            if (adjMap.containsKey(currWord)) {
                Map<String, List<List<String>>> d = new HashMap<>();
                Set<String> wordSet = adjMap.get(currWord);
                for (String s : wordSet) {
                    List<List<String>> temp = new ArrayList<>();
                    if (d.containsKey(s)) {
                        temp = d.get(s);
                    } else {
                     temp = rec(endWord, currlevel, maxLevel,
                            s, adjMap);
                  
                        d.put(s, temp);
                    }
                      if (null != temp) {
                        for (List<String> t : temp) {
                            t.add(0, currWord);
                        }
                        ret.addAll(temp);
                    }
                }
            }
            return ret;
        }
        
    }
}










































