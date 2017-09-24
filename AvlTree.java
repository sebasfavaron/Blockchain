import org.omg.CORBA.PUBLIC_MEMBER;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by navi on 23/09/17.
 */
public class AvlTree<T> {
    AvlNode root;
    Comparator <T> cmp;
    String hash;

    public AvlTree(Comparator<T> cmp){
        this.cmp=cmp;
    }

    int height(AvlNode<T> N) {
        if (N == null)
            return 0;

        return N.height;
    }

    // A utility function to get maximum of two integers
    int max(int a, int b) {
        return (a > b) ? a : b;
    }

    // A utility function to right rotate subtree rooted with y
    // See the diagram given above.
    AvlNode<T> rightRotate(AvlNode<T> y) {
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
    AvlNode<T> leftRotate(AvlNode x) {
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

    // Get Balance factor of node N
    int getBalance(AvlNode<T> N) {
        if (N == null)
            return 0;

        return height(N.left) - height(N.right);
    }
    public void insert(T elem){
        root=insertR(root,elem);
        this.hash="";
        preOrderHash(root);
        System.out.println(this.hash);
    }
    public void remove(T elem){
        //root=removeR(root,elem); falta implementacion
        this.hash="";
        preOrderHash(root);
        System.out.println(this.hash);
    }

    private AvlNode<T> insertR(AvlNode<T> node, T key) {

        /* 1.  Perform the normal BST insertion */
        if (node == null)
            return (new AvlNode(key));

        if (cmp.compare(key,node.elem)>0)
            node.left = insertR(node.left, key);
        else if (cmp.compare(key,node.elem)<0)
            node.right = insertR(node.right, key);
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

        /* return the (unchanged) node pointer */
        return node;
    }

    // A utility function to print preorder traversal
    // of the tree.
    // The function also prints height of every node
    void preOrder(AvlNode node) {
        if (node != null) {
            System.out.print(node.elem + " ");
            preOrder(node.left);
            preOrder(node.right);
        }
    }
    void preOrderHash(AvlNode<T> node) {
        if (node != null) {
            Integer aux=node.elem.hashCode();
            this.hash=hash+Integer.toHexString(aux);
            preOrderHash(node.left);
            preOrderHash(node.right);
        }
    }

    private class AvlNode <T>{

        AvlNode left, right;

        T elem;

        int height;

        public AvlNode(T n) {

            left = null;

            right = null;

            elem = n;

            height = 0;

        }
    }
}
