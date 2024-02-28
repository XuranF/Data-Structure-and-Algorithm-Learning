import java.util.Random;
import java.util.Stack;

public class skipList {
    public static void main(String[] args) throws Exception {
        skipList list1=new skipList();
        for(int i=5;i<=20;i++) list1.insert(i);
        list1.print();
        //list1.delete(4);//to test nonexistent key exception
        list1.delete(8);
        System.out.println("\nAfter deletion of 8:");
        list1.print();

        list1.insert(8);
        System.out.println("\nAfter reinsertion of 8:");
        list1.print();

        System.out.println("\nAfter insertion of 21:");
        list1.insert(21);
        //list1.insert(20);//to test duplicate key exception
        list1.print();

    }

    private class skipNode{
        int key;
        skipNode down,next;

        public skipNode(int key){
            this.key=key;
            this.down=null;
            this.next=null;
        }
        public void setDown(skipNode down){ this.down=down;}
        public void setNext(skipNode next) {this.next=next;}
    }

    private int totalLevel;
    private Random rseed;
    private skipNode head,tail;

    public skipList(){
        //the skip list is designed to have two sentinel nodes
        head=new skipNode(Integer.MIN_VALUE);
        tail=new skipNode(Integer.MAX_VALUE);
        head.setNext(tail);
        totalLevel=1;
        rseed=new Random();
    }

    /*
    * to insert a key into the skip list, building up levels if needed
    */
    public void insert(int key) throws Exception {
        if(lookUp(key)) throw new Exception("key already exists");
        //stack is used to track all parent nodes from different level
        Stack<skipNode> stack=new Stack<>();
        skipNode tmp=head;

        while(tmp!=null){
            if(tmp.next.key>key) {
                stack.push(tmp);
                if(tmp.down==null) break;
                tmp=tmp.down;
            }
            else tmp=tmp.next;
        }

        int newLevel=0;
        double rand=rseed.nextDouble();
        skipNode prevNode=null;
        while(rand>0.5||newLevel==0) {
            newLevel++;
            skipNode newNode=generateNode(key);

            //adjust horizontal pointer relation
            if(newLevel<=totalLevel){
                skipNode node=stack.pop();
                newNode.setNext(node.next);
                node.setNext(newNode);
            }
            else{
                skipNode newHead=generateNode(Integer.MIN_VALUE);
                skipNode newTail=generateNode(Integer.MAX_VALUE);
                newHead.setDown(head);head=newHead;
                newTail.setDown(tail);tail=newTail;
                newHead.setNext(newNode);
                newNode.setNext(newTail);
            }

            //adjust vertical pointer relation
            newNode.setDown(prevNode);
            prevNode=newNode;
            rand=rseed.nextDouble();
        }
        //update the height of whole skip list
        totalLevel=Math.max(totalLevel,newLevel);
    }
    /*
    helper function for insertion
     */
    private skipNode generateNode(int key){
        return new skipNode(key);
    }

    /*
    delete a key from the skip list
     */
    public void delete(int key) throws Exception {
        if(!lookUp(key)) throw new Exception("key doesn't exist");
        skipNode tmp=head;
        while(tmp!=null){
            if(tmp.next.key==key) {
                tmp.setNext(tmp.next.next);
                tmp=tmp.down;
            }
            else if(tmp.next.key>key) tmp=tmp.down;
            else tmp=tmp.next;
        }
    }

    /*
    return true if the key exists
     */
    public boolean lookUp(int key){
        skipNode tmp=head;
        while(tmp!=null){
            if(tmp.key==key) return true;
            if(tmp.next.key>key) tmp=tmp.down;
            else tmp=tmp.next;
        }
        return false;
    }

    /*
    print the skip list
     */
    public void print(){
        //use two nodes to align
        skipNode iter=head,last=iter;
        while(last.down!=null) last=last.down;
        while(iter!=null){
            skipNode rowIter1=iter,rowIter2=last;
            while(rowIter1!=null&&rowIter2!=null){
                if(rowIter1.key==rowIter2.key){
                    if(rowIter1.key==Integer.MIN_VALUE) System.out.printf("%-8s","-INF->");
                    else if(rowIter1.key==Integer.MAX_VALUE) System.out.printf("%-8s","+INF");
                    else System.out.printf("%-5s",rowIter1.key+"->");
                    rowIter1=rowIter1.next;
                }
                else System.out.printf("%-5s","");
                rowIter2=rowIter2.next;
            }
            iter=iter.down;
            System.out.print("\n");
        }
    }
}
