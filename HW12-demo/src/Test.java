
// minimum depth of binary tree
//       1
//   1       1
//1    1

public class Test {
    public static void main(String[] args) {
        treeNode root = new treeNode(1);
        treeNode node1 = new treeNode(1);
        treeNode node2 = new treeNode(1);
        treeNode node3 = new treeNode(1);
        treeNode node4 = new treeNode(1);
        root.setLeftChild(node1);
        root.setRightChild(node2);
        node1.setLeftChild(node3);
        node1.setRightChild(node4);
        System.out.println("the depth of the tree is "+ findMinimumDepth(root));
    }
    public class treeNode{
        int nodeValue;
        treeNode leftChild, rightChild;
        public treeNode(int nodeValue){
            this.nodeValue = nodeValue;
        }
        public void setLeftChild(treeNode leftChild){
            this.leftChild = leftChild;
        }
        public void setRightChild(treeNode rightChild){
            this.rightChild = rightChild;
        }
    }

    public int findMinimumDepth(treeNode node){
        if(node==null) return 0;
        int leftDepth = findMinimumDepth(node.leftChild);
        int rightDepth = findMinimumDepth(node.rightChild);
        return 1+Math.min(leftDepth,rightDepth);
    }


//    public static void main(){
//        //build an example tree
//        treeNode root = new treeNode(1);
//        treeNode node1 = new treeNode(1);
//        treeNode node2 = new treeNode(1);
//        treeNode node3 = new treeNode(1);
//        treeNode node4 = new treeNode(1);
//        root.setLeftChild(node1);
//        root.setRightChild(node2);
//        node1.setLeftChild(node3);
//        node1.setRightChild(node4);
//        System.out.println("the depth of the tree is "+ findMinimumDepth(root));
//    }

}




