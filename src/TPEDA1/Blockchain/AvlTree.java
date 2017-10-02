package TPEDA1.Blockchain;

import org.omg.CORBA.PUBLIC_MEMBER;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by navi on 23/09/17.
 */
public class AvlTree<T> {
    private AvlNode root;
    private Comparator <T> cmp;
    private String hash;

    public AvlTree(Comparator<T> cmp){
        this.cmp=cmp;
    }

    public int height(AvlNode N) {
        if (N == null)
            return 0;

        return N.height;
    }

    // A utility function to get maximum of two integers
    private int max(int a, int b) {
        return (a > b) ? a : b;
    }

    // A utility function to right rotate subtree rooted with y
    // See the diagram given above.
    private AvlNode rightRotate(AvlNode y) {
        AvlNode x = y.left;
        AvlNode T2 = x.right;

        // Perform rotation
        x.right = y;
        y.left = T2;

        // Update heights
        y.height = max(height(y.left), height(y.right)) + 1;
        x.height = max(height(x.left), height(x.right)) + 1;

        // Return new root
        return x;
    }

    // A utility function to left rotate subtree rooted with x
    // See the diagram given above.
    private AvlNode leftRotate(AvlNode x) {
        AvlNode y = x.right;
        AvlNode T2 = y.left;

        // Perform rotation
        y.left = x;
        x.right = T2;

        //  Update heights
        x.height = max(height(x.left), height(x.right)) + 1;
        y.height = max(height(y.left), height(y.right)) + 1;

        // Return new root
        return y;
    }

    public boolean contains(T elem) {
    	return contains(root, elem);

    }
    
    private boolean contains(AvlNode current, T elem) {
    	
    	if (current == null) {
    		return false;
    	}
    	
    	int value = cmp.compare(current.elem, elem);
    	if (value < 0) {
    		return contains(current.right, elem);
    	} else if (value > 0) {
    		return contains(current.left, elem);
    	} else {
    		return true;
    	}
    }
    // Get Balance factor of node N
    public int getBalance(AvlNode N) {
        if (N == null)
            return 0;

        return height(N.left) - height(N.right);
    }
    public void insert(T elem){
        root=insertR(root,elem);
        this.hash="";
        preOrderHash(root);
        System.out.println("HASH: "+this.hash);
        System.out.println("INTEGER: "+Long.parseLong(this.hash, 16));
    }
    private AvlNode insertR(AvlNode node, T key) {

        /* 1.  Perform the normal BST insertion */
        if (node == null)
            return (new AvlNode(key));

        if (cmp.compare(key,node.elem)>0)  //key is bigger than node, add it to the right subtree
            node.right = insertR(node.right, key);
        else if (cmp.compare(key,node.elem)<0) //key is smaller than node, add it to the left subtree
            node.left = insertR(node.left, key);
        else // Duplicate keys not allowed
            return node;

        /* 2. Update height of this ancestor node */
        node.height = 1 + max(height(node.left),
                height(node.right));

        /* 3. Get the balance factor of this ancestor
              node to check whether this node became
              unbalanced */
        int balance = getBalance(node);

        // If this node becomes unbalanced, then there
        // are 4 cases:
        // Left Left Case
        if (balance > 1 && cmp.compare(key,node.left.elem)<0) //left subtree bigger than right subtree && new elem smaller than left child
            return rightRotate(node);

        // Right Right Case
        if (balance < -1 && cmp.compare(key,node.right.elem)>0) //right subtree bigger than left subtree && new elem bigger than right child
            return leftRotate(node);

        // Left Right Case
        if (balance > 1 && cmp.compare(key,node.left.elem)>0) {//left subtree bigger than right subtree && new elem bigger than left child
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        // Right Left Case
        if (balance < -1 && cmp.compare(key,node.right.elem)<0) {//right subtree bigger than left subtree && new elem smaller than right child
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        /* return the (unchanged) node pointer */
        return node;
    }

    public void remove(T elem){
        root=removeR(root,elem);
        this.hash="";
        preOrderHash(root);
        System.out.println(this.hash);
    }


    private AvlNode removeR(AvlNode node, T key) {

        /* 1.  Perform the normal BST delete */
        if (node == null)
            return node;

        if (cmp.compare(key,node.elem)>0) {
            node.right = removeR(node.right, key);
        }
        else if (cmp.compare(key,node.elem)<0) {
            node.left = removeR(node.left, key);
        }
        else {
            if (node.left == null)
                return node.right;
            else if (node.right == null)
                return node.left;
            else {
                node.elem = minValue(node.right);
                node.right = removeR(node.right, node.elem);
            }
        }

        /* 2. Update height of this ancestor node */
        node.height = 1 + max(height(node.left),
                height(node.right));

        /* 3. Get the balance factor of this ancestor
              node to check whether this node became
              unbalanced */
        int balance = getBalance(node);

        // If this node becomes unbalanced, then there
        // are 4 cases Left Left Case
        if (balance > 1 && cmp.compare(key,node.elem)>0)
            return rightRotate(node);

        // Right Right Case
        if (balance < -1 && cmp.compare(key,node.elem)<0)
            return leftRotate(node);

        // Left Right Case
        if (balance > 1 && cmp.compare(key,node.elem)>0) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        // Right Left Case
        if (balance < -1 && cmp.compare(key,node.elem)<0) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }


        return node;

    }
    private T minValue(AvlNode node)
    {
        T minv = node.elem;
        while (node.left != null)
        {
            minv = node.left.elem;
            node = node.left;
        }
        return minv;
    }
    // A utility function to print preorder traversal
    // of the tree.
    // The function also prints height of every node
    public void preOrder(AvlNode node) {
        if (node != null) {
            System.out.print(node.elem + " ");
            preOrder(node.left);
            preOrder(node.right);
        }
    }
    public void preOrderHash(AvlNode node) {
        if (node != null) {
            Integer aux=node.elem.hashCode();
            this.hash=hash+Integer.toHexString(aux);
            preOrderHash(node.left);
            preOrderHash(node.right);
        }
    }

    public void print() {
        printRec(this.root);
        System.out.println();
    }

    private void printRec(AvlNode tree) {
        if(tree == null)
            return;
        System.out.print(tree.elem);
        if(tree.left != null || tree.right != null)
            System.out.print("(");
        printRec(tree.left);
        if(tree.left != null && tree.right != null)
            System.out.print(",");
        printRec(tree.right);
        if(tree.left != null || tree.right != null)
            System.out.print(")");
    }

    public int hashCode(){
        return Integer.parseInt(this.hash, 16);
    }

    private class AvlNode {

        AvlNode left, right;

        T elem;

        int height;

        public AvlNode(T n) {

            left = null;

            right = null;

            elem = n;

            height = 1;

        }
        public String toString(){
            return elem.toString();
        }
    }
}
