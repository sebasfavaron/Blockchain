package src.Blockchain;

import org.omg.CORBA.PUBLIC_MEMBER;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

/**
 * This class is a binary tree-type data structure which has the function of auto balancing in insertions and removals
 * ensuring a temporal complexity of at least log(N) for searches additions and removals
 */
public class AvlTree<T> {
    private AvlNode root;
    private Comparator <T> cmp;
    private String hash;

    public AvlTree(Comparator<T> cmp){
        this.cmp=cmp;
    }

    /**
     * This method is used to determine the height of each node. Height is measured in a bottom-top method, leaf nodes
     * have a height of 0 all other nodes are 1 unit higher than their highest child node.
     * @param N node who's height is being determined.
     * @return 0 if the node is a leaf (null node), else returns the height.
     * @author
     */
    public int height(AvlNode N) {
        if (N == null)
            return 0;

        return N.height;
    }

    /**
     * This method is used to get maximum of two integers.
     * @param a Integer being compared.
     * @param b Integer being compared.
     * @return a if a is bigger than b, else returns b.
     * @author
     */
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

    /**
     *
     * @param x
     * @return
     */
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

    /**
     * This method is used to determine if a given element is contained in the data-structure.
     * @param elem generic element that is being consulted.
     * @return true if it is contained, else false.
     * @author
     */
    public boolean contains(T elem) {
    	return contains(root, elem);

    }

    /**
     *
     * @param current
     * @param elem
     * @return
     */
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

    public Comparator<T> getCmp() {
        return cmp;
    }

    /**
     * This method is used to assign a given node its balance value. The balance value is used to ensure that every node
     * in the data-structure mantains the balancing criteria.
     * @param N node being consulted.
     * @return an int that represents the balance value.
     * @author
     */
    // Get Balance factor of node N
    public int getBalance(AvlNode N) {
        if (N == null)
            return 0;

        return height(N.left) - height(N.right);
    }

    /**
     * This method is used to insert a generic element into the data-structure.
     * @param elem generic element being inserted.
     * @author
     */
    public void insert(T elem){
        root=insertR(root,elem);
        this.hash="";
        preOrderHash(root);
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

    /**
     * This method is used to remove a generic element into the data-structure.
     * @param elem generic element being removed.
     * @author
     */
    public void remove(T elem) {
        root = removeR(root,elem);
        this.hash = "";
        preOrderHash(root);
    }


    private AvlNode removeR(AvlNode node, T key) {

        /* 1.  Perform the normal BST delete */
        if (node == null)
            return null;

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

    /**
     * This method is used to assign the data-structure its hashcode.
     * @param node root of tree.
     * @author
     */
    public void preOrderHash(AvlNode node) {
        if (node != null) {
            Integer aux=node.elem.hashCode();
            this.hash=hash+Integer.toHexString(aux);
            preOrderHash(node.left);
            preOrderHash(node.right);
        }
    }

    /**
     * This method is used to print the current state of the data-structure
     * @return "empty" if no nodes have been instantiated, else returns the data-structure's state
     * @author
     */
    public String print() {
        if(this.root == null)
            return "empty";
        return printRec(this.root);
    }

    private String printRec(AvlNode tree) {
        String ret = "";
        if(tree == null)
            return ret;
        ret += tree.elem;
        String left = printRec(tree.left);
        String right = printRec(tree.right);
        if(tree.left != null || tree.right != null)
            ret += "(";
        ret += left;
        if(!left.equals("") && right.equals(""))
            ret += "l";
        else if(!left.equals("") && !right.equals(""))
            ret += ",";
        ret += right;
        if(!right.equals("") && left.equals(""))
            ret += "r";
        if(tree.left != null || tree.right != null)
            ret += ")";
        return ret;
    }

    public AvlTree<T> clone() {
        AvlTree<T> tree = new AvlTree<>(cmp);
        tree.root = cloneR(root);
        return tree;
    }

    /**
     * TODO TUYO SEBAS
     * @param tree
     * @return
     * @author
     */
    private AvlNode cloneR(AvlNode tree) {
        if(tree==null){
            return tree;
        }
        AvlNode left = null;
        AvlNode right = null;
        if(tree.left != null)
            left = cloneR(tree.left);
        if(tree.right != null)
            right = cloneR(tree.right);
        return new AvlNode(tree.elem, left, right);
    }

    /**
     * This method is used to consult the hashcode of the data-structure.
     * @return
     * @author
     */
    public int hashCode(){
        return Integer.parseInt(this.hash, 16);
    }
    
    // Generates a tree of integers from a string
    public AvlTree<Integer> load(String s, Comparator<Integer> cmp) {
        AvlTree<Integer> tree = new AvlTree<>(cmp);
        tree.root = loadR(s);
        return tree;
    }

    private AvlTree<Integer>.AvlNode loadR(String s) {
        if(s.length() <= 0)
            return null;
        AvlTree<Integer>.AvlNode left = null, right = null;
        if(s.contains("(")) {
            int commaIndex = commaSearch(s);
            if (commaIndex == -1) {
                String child = s.substring(s.indexOf('(')+1, s.indexOf(')'));
                if (child.charAt(child.length() - 1) == 'l')
                    left = loadR(child.substring(0,child.length()-1));
                else
                    right = loadR(child.substring(0,child.length()-1));
            }
            else {
                String lChild = s.substring(s.indexOf('(')+1,commaIndex);
                String rChild = s.substring(commaIndex+1, s.lastIndexOf(')'));
                left = loadR(lChild);
                right = loadR(rChild);
            }
        }
        int parenIndex = s.indexOf('(');
        int elem = Integer.parseInt(s.substring(0,parenIndex == -1? s.length() : parenIndex));
        AvlTree<Integer>.AvlNode node = new AvlTree<Integer>.AvlNode(elem, left, right);
        return node;
    }

    //Searches for the comma dividing the greater sons in the tree represented in s
    private int commaSearch(String s) {
        int parenthesisWeight = 0;
        char[] c = s.toCharArray();
        for(int i=0; i<s.length(); i++) {
            // weight of 1 means the index is looking at the first children
            // (ex: 4(2(1),5) weight is 1 when looking at the 2, the comma and 5)
            if(parenthesisWeight == 1 && c[i]==',')
                return i;
            if(c[i] == '(')
                parenthesisWeight++;
            if(c[i] == ')')
                parenthesisWeight--;
        }
        return -1;
    }

    private class AvlNode {

        AvlNode left, right;
        T elem;
        int height;

        public AvlNode(T elem) {
            left = null;
            right = null;
            this.elem = elem;
            height = 1;
        }

        public AvlNode(T elem, AvlNode left, AvlNode right) {
            this.left = left;
            this.right = right;
            this.elem = elem;
            height = 1;
        }
        public String toString(){
            return elem.toString();
        }
    }
}
