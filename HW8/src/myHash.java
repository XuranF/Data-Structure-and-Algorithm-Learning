import java.io.*;
import java.util.*;

public class myHash{
    //internal node class of each entry
    private class Pair{
        private String key;
        private Integer value;
        public Pair(String key, Integer value){
            this.key=key;
            this.value=value;
        }
        public String getKey(){return key;}
        public Integer getValue(){return value;}
        public void setValue(Integer value){this.value=value;}
    }

    //internal entry class of hash
    private class Entry{
        private Integer entrySize;
        List<Pair> chain;
        
        public Entry(){
            entrySize=0;
            chain=new LinkedList<>();
        }

        public Integer getEntrySize(){return entrySize;}

        public Integer entryFind(String key){
            for(Pair p: chain){
                if (p.getKey().equals(key)) return p.getValue();
            }
            return -1;
        }

        public void entryPut(String key, Integer value){
            if(entryFind(key)==-1){
                chain.add(new Pair(key,value));
                entrySize++;
            }
            else {
                for(Pair p: chain){
                    if (p.getKey().equals(key)) {
                        p.setValue(value);
                        break;
                    }
                }
            }
        }

        public void entryDelete(String key){
            ListIterator<Pair> iter=chain.listIterator();
            while(iter.hasNext()){
                Pair p=iter.next();
                if(p.getKey().equals(key)){
                    entrySize--;
                    iter.remove();
                    break;
                }
            }
        }

        public void entryIncrease(String key){
            if(entryFind(key)!=-1){
                for(Pair p: chain){
                    if (p.getKey().equals(key)) {
                        p.setValue(p.getValue()+1);
                        break;
                    }
                }
            }
        }

        public void entryOutputKey(){
            for(Pair p:chain) System.out.println("'"+p.getKey()+"' appears "+p.getValue());
        }
    }

    private final int MAXHASH=1000;
    private final Entry[] keySet;

    public myHash(){
        keySet=new Entry[MAXHASH];
        for(int i=0;i<MAXHASH;i++) keySet[i]=new Entry();
    }
    //another constructor to directly interact with a file
    public myHash(String path) throws FileNotFoundException {

        keySet=new Entry[MAXHASH];
        for(int i=0;i<MAXHASH;i++) keySet[i]=new Entry();
        Scanner in =new Scanner(new File(path));
        while(in.hasNext()){
            String key=in.next();
            if(find(key)==-1) insert(key,1);
            else increase(key);
        }
    }

    private int hashCode(String key){
        int hash=0;
        for(int i=0;i<key.length();i++) {
            hash+=key.charAt(i)*Math.pow(31,i);
        }
        return hash % MAXHASH;
    }
    /*
    HashMap.put()
     */
    public void insert(String key, Integer value) {
        keySet[hashCode(key)].entryPut(key,value);
    }
    public void delete(String key) {
        keySet[hashCode(key)].entryDelete(key);
    }
    public int find(String key) {
        return keySet[hashCode(key)].entryFind(key);
    }
    /*
    Increase the value of key by 1
     */
    public void increase(String key) {
        keySet[hashCode(key)].entryIncrease(key);
    }
    public void listAllKeys(){
        for(int i=0;i<MAXHASH;i++)
            keySet[i].entryOutputKey();
    }

    /*
    to test whether hash function is a good one based on the list length of each entry
    and output the statistics to an .txt file
     */
    public void generateHistogram() throws FileNotFoundException {

        int sum=0;
        PrintWriter out = new PrintWriter("src/output.txt");
        for(int i=0;i<MAXHASH;i++) {
            int tmp=keySet[i].getEntrySize();
            out.println(tmp);
            sum+=tmp;
        }
        out.println("α is "+sum/MAXHASH);
        out.close();
    }

    public static void main(String[] args) throws FileNotFoundException {
        myHash hash2=new myHash("src/alice_in_wonderland.txt");
        hash2.generateHistogram();
    }
}