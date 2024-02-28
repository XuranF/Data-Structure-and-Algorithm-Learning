
public class BinomialHeap {
    public static void main(String[] args) throws Exception {
        BinomialHeap myHeap=new BinomialHeap();
        BinomialHeap myHeap2=new BinomialHeap();
        int a[] = {12, 7, 25, 15, 28, 33, 41};
        int b[] = {27,11,8,17,14,38,6,29,12,18,1,25,10};
        for(Integer i:a) myHeap.insert(i);
        for(Integer i:b) myHeap2.insert(i);
        myHeap.print();
        myHeap2.print();
        myHeap2.decreaseKey(38,5);
        myHeap2.print();
        System.out.println(myHeap2.getMinimum().key);
        System.out.println(myHeap2.extractMin().key);
        myHeap2.print();
        System.out.println(myHeap2.extractMin().key);
        System.out.println(myHeap2.extractMin().key);
        myHeap2.print();
        myHeap2.delete(18);
        myHeap2.print();
        myHeap.union(myHeap2).print();
    }

    private class heapNode{
        int key;
        int degree;
        heapNode parent,child,sibling;

        public heapNode(int key){
            this.key=key;
            this.degree=0;
            parent=child=sibling=null;
        }
    }

    private heapNode head;
    public BinomialHeap(){
        this.head=null;
    }

    public BinomialHeap(heapNode node){
        this.head=node;
    }

    public BinomialHeap makeHeap(heapNode node){
        return new BinomialHeap(node);
    }

    public heapNode getMinimum() throws Exception {
        int ans=Integer.MAX_VALUE;
        heapNode minimum=null;
        heapNode iter=head;
        while(iter!=null){
            if(ans>iter.key){
                ans=iter.key;
                minimum=iter;
            }
            iter=iter.sibling;
        }
        if(minimum==null) throw new Exception("the binomial heap is null");
        return minimum;
    }

    public BinomialHeap union(BinomialHeap heap){
        head=union(head,heap.head);
        return makeHeap(head);
    }

    private heapNode union(heapNode node1, heapNode node2) {
        heapNode root= mergeTwoLists(node1,node2),iter=root;
        heapNode next=iter.sibling,nnext,dummy=new heapNode(-1),prev;
        dummy.sibling=root;
        prev=dummy;
        while(next!=null){
            nnext=next.sibling;
            if(next.degree<iter.degree){
                prev.sibling=next;
                next.sibling=iter;
                iter.sibling=nnext;
                prev=next;
                next=nnext;
                continue;
            }
            if(next.degree!=iter.degree){
                prev=iter;
                iter=next;
            }
            else{
                iter.sibling=null;
                next.sibling=null;
                if(iter.key>=next.key) iter=incorporate(iter,next);
                else iter=incorporate(next,iter);
                prev.sibling=iter;
                iter.sibling=nnext;
            }
            next=nnext;
        }
        return dummy.sibling;
    }
    //merge two heads in ascending degree
    private heapNode mergeTwoLists(heapNode node1, heapNode node2){
        if(node1==null) return node2;
        if(node2==null) return node1;
        if(node1.degree<=node2.degree){
            node1.sibling=mergeTwoLists(node1.sibling,node2);
            return node1;
        }
        node2.sibling=mergeTwoLists(node1,node2.sibling);
        return node2;
    }
    //connect two heads with same degree
    private heapNode incorporate(heapNode node1, heapNode node2){
        node1.sibling=node2.child;
        node1.parent=node2;
        node2.child=node1;
        node2.degree++;
        return node2;
    }

    public void insert(int key) throws Exception {
        heapNode newNode=new heapNode(key);
        if(find(key)) throw new Exception("Cannot add same key into the binomial heap!");
        head=union(newNode,head);
    }

    public heapNode extractMin() throws Exception{
        heapNode minimum=getMinimum();
        heapNode iter,dummy=new heapNode(-1);
        dummy.sibling=head;
        iter=dummy;
        while(iter.sibling!=minimum){
            iter=iter.sibling;
        }
        iter.sibling=minimum.sibling;
        minimum.sibling=null;
        head=union(dummy.sibling,disassemble(minimum.child));
        return minimum;
    }
    //break down the child of minimum child
    private heapNode disassemble(heapNode minimum){
        if(minimum==null) return null;
        heapNode curr=minimum,next=curr.sibling,nnext;
        curr.sibling=null;
        while(next!=null){
            nnext=next.sibling;
            curr.parent=null;
            next.sibling=curr;
            curr=next;
            next=nnext;
        }
        curr.parent=null;
        return curr;
    }

    public void delete(int key) throws Exception {
        decreaseKey(key,Integer.MIN_VALUE);
        extractMin();
    }

    public void decreaseKey(int key, int value) throws Exception {
        if(!find(key)) throw new Exception("no key found!");
        heapNode iter=head;
        while(iter!=null){
            if(iter.key==key){iter.key=value;return;}
            if(iter.key>key){
                iter=iter.sibling;
                continue;
            }
            //remember to float up if the min-heap property is broken
            heapNode node=dfs(iter.child,key),parent;
            if(node!=null) {
                node.key=value;
                parent=node.parent;
                while(parent!=null){
                    if(node.key>parent.key) break;
                    int tmp=node.key;
                    node.key=parent.key;
                    parent.key=tmp;
                    node=parent;
                    parent=parent.parent;
                }
                break;
            }
            iter=iter.sibling;
        }
    }

    private boolean find(int key){
        heapNode iter=head;
        while(iter!=null){
            if(iter.key==key) return true;
            if(iter.key>key){
                iter=iter.sibling;
                continue;
            }
            if(dfs(iter.child,key)!=null) return true;
            iter=iter.sibling;
        }
        return false;
    }
    private heapNode dfs(heapNode node,int key){
        if(node==null) return null;
        if(node.key==key) return node;
        heapNode node1=dfs(node.child,key);
        if(node1==null) node1=dfs(node.sibling,key);
        return node1;
    }

    //'print' references: https://github.com/wangkuiwu/datastructs_and_algorithm/blob/master/source/heap/binomial/java/BinomialHeap.java
    public void print() {
        if (head == null) return;
        heapNode p = head;
        System.out.printf("== Binomial Heap( ");
        while (p != null) {
            System.out.printf("B%d ", p.degree);
            p = p.sibling;
        }
        System.out.printf(")details：\n");

        int i=0;
        p = head;
        while (p != null) {
            i++;
            System.out.printf("%d. BinaryHeapB%d: \n", i, p.degree);
            System.out.printf("\t%2d(%d) is root\n", p.key, p.degree);
            print(p.child, p, 1);
            p = p.sibling;
        }
        System.out.printf("\n");
    }
    private void print(heapNode node, heapNode prev, int direction) {
        while(node != null) {
            if(direction==1) System.out.printf("\t%2d(%d) is %2d's child\n", node.key, node.degree, prev.key);
            else System.out.printf("\t%2d(%d) is %2d's next\n", node.key, node.degree, prev.key);
            if (node.child != null) print(node.child, node, 1);

            prev = node;
            node = node.sibling;
            direction = 2;
        }
    }
}
