//reference: CLRS 3rd version
import java.util.NoSuchElementException;

public class RBTree {
    //private static final int test[] = {1,15,4,6,2,7,9,0,3,8,10,89,66,52,34,79};
    private static final int test[] = {8,7,6,5,4,3,2,1};
    public static void main(String[] args){
        int len = test.length;
        RBTree tree1 = new RBTree();
        System.out.printf("original data: ");
        for(int i=0; i<len; i++)
            System.out.printf("%d ", test[i]);
        System.out.printf("\n");
        for(int i=0; i<len; i++) {
            tree1.insert(test[i]);
            System.out.printf("node inserted: %d\n", test[i]);
            System.out.printf("tree height: %d\n",tree1.height());

            System.out.printf("tree details: \n");
            tree1.print();
            System.out.printf("\n");
        }

        System.out.printf("\nin order walk-through: ");
        tree1.sort();
        System.out.printf("\n");

        System.out.printf("\nsearch 8: ");
        System.out.println(tree1.search(8).key);
        System.out.printf("\n");

        System.out.printf("minimum value: %s\n", tree1.minimum());
        System.out.printf("maximum value: %s\n", tree1.maximum());
        System.out.printf("tree details: \n");
        tree1.print();
        System.out.printf("\n");
    }

    private enum Color{RED, BLACK};

    private class Node{
        Node parent,left,right;
        Color color;
        int key;

        public Node(int key,Color color){
            parent=left=right=null;
            this.color=color;
            this.key=key;
        }
        public void setParent(Node parent) {this.parent=parent;}
        public void setColor(Color color) {this.color = color;}
        public void setLeft(Node left) {this.left = left;}
        public void setRight(Node right) {this.right = right;}
    }

    private Node root;
    public RBTree(){
        root=null;
    }
    public RBTree(int key){
        root=new Node(key,Color.BLACK);
    }
    public void insert(int key){
        Node y=null, x=root;
        Node z=new Node(key,Color.RED);
        while(x!=null){
            y=x;
            if(z.key<x.key) x=x.left;
            else x=x.right;
        }
        z.setParent(y);
        if(y==null) this.root=z;
        else if(z.key<y.key) y.left=z;
        else y.right=z;
        insertFixUp(z);
    }
    public void insertFixUp(Node z){
        Node y=null;//uncle
        while(z.parent!=null&&z.parent.color==Color.RED){
            if(z.parent.parent!=null&&z.parent==z.parent.parent.left){
                y=z.parent.parent.right;
                if(y!=null&&y.color==Color.RED){
                    z.parent.setColor(Color.BLACK);
                    y.setColor(Color.BLACK);
                    z.parent.parent.setColor(Color.RED);
                    z=z.parent.parent;
                    continue;
                }
                if(z==z.parent.right){
                    z=z.parent;
                    leftRotate(z);
                }
                z.parent.setColor(Color.BLACK);
                z.parent.parent.setColor(Color.RED);
                rightRotate(z.parent.parent);
            }
            else if(z.parent.parent!=null&&z.parent==z.parent.parent.right){
                y=z.parent.parent.left;
                if(y!=null&&y.color==Color.RED){
                    z.parent.setColor(Color.BLACK);
                    y.setColor(Color.BLACK);
                    z.parent.parent.setColor(Color.RED);
                    z=z.parent.parent;
                    continue;
                }
                if(z==z.parent.left){
                    z=z.parent;
                    rightRotate(z);
                }
                z.parent.setColor(Color.BLACK);
                z.parent.parent.setColor(Color.RED);
                leftRotate(z.parent.parent);
            }
        }
        root.setColor(Color.BLACK);
    }
    public void leftRotate(Node x){
        Node y=x.right;
        x.right=y.left;
        if(y.left!=null) y.left.parent=x;
        y.parent=x.parent;
        if(x.parent==null) this.root=y;
        else if(x==x.parent.left) x.parent.left=y;
        else x.parent.right=y;
        y.left=x;
        x.parent=y;
    }
    public void rightRotate(Node y){
        Node x=y.left;
        y.left=x.right;
        if(x.right!=null) x.right.parent=y;
        x.parent=y.parent;
        if(y.parent==null) this.root=x;
        else if(y==y.parent.left) y.parent.left=x;
        else y.parent.right=x;
        x.right=y;
        y.parent=x;
    }
    /*
    in order traversal
     */
    public void sort(){
        inOrder(this.root);
    }
    private void inOrder(Node r){
        if(r!=null){
            inOrder(r.left);
            System.out.print(r.key+" ");
            inOrder(r.right);
        }
    }

    public Node search(int key){
        return searchHelper(this.root,key);
    }
    private Node searchHelper(Node r,int key){
        if(r==null) return null;
        if(key<r.key) return searchHelper(r.left,key);
        else if(key>r.key) return searchHelper(r.right,key);
        else return r;
    }

    public int maximum(){
        Node tmp=maximum(this.root);
        if(tmp!=null) return tmp.key;
        throw new NoSuchElementException("Tree is null");
    }
    private Node maximum(Node n){
        while(n.right!=null) n=n.right;
        return n;
    }

    public int minimum(){
        Node tmp=minimum(this.root);
        if(tmp!=null) return tmp.key;
        throw new NoSuchElementException("Tree is null");
    }
    public Node minimum(Node n){
        while(n.left!=null) n=n.left;
        return n;
    }

    public Node successor(Node x){
        if(x.right!=null) return minimum(x.right);
        Node y=x.parent;
        while (y!=null&&x==y.right){
            x=y;
            y=y.parent;
        }
        return y;
    }

    public Node predecessor(Node x){
        if(x.left!=null) return maximum(x.left);
        Node y=x.parent;
        while (y!=null&&x==y.left){
            x=y;
            y=y.parent;
        }
        return y;
    }
    /*
    calculate tree height to test RBTree O(logn) height promise
     */
    public int height(){
        return heightHelper(this.root);
    }
    private int heightHelper(Node n){
        if(n==null) return 0;
        return Math.max(heightHelper(n.left),heightHelper(n.right))+1;
    }

    //reference: https://www.cnblogs.com/skywang12345/p/3624343.html
    public void print(){
        if(root!=null) {
            printHelper(this.root,0);
        }
    }
    private void printHelper(Node n,int d){
        if(n!=null){
            if(d==0) System.out.printf("%2d is root\n", n.key);
            else System.out.printf("%2d(%s) is %2d's %6s child\n",n.key,n.color==Color.RED? "RED":"BLACK",n.parent.key,
                    d>0?"left":"right");
            printHelper(n.left,1);
            printHelper(n.right,-1);
        }
    }
}
