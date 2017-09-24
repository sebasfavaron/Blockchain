

import java.util.Comparator;

/**
 * Created by navi on 23/09/17.
 */
public class Main {
    public static void main(String[]args){
        AvlTree tree = new AvlTree(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1-o2;
            }
        });

        /* Constructing tree given in the above figure */
        tree.insert(9);
        tree.insert(2);
        tree.insert(3);
        tree.insert(4);
        tree.insert(5);
        tree.insert(6);


        /* The constructed AVL Tree would be
             30
            /  \
          20   40
         /  \     \
        10  25    50
        */
        System.out.println("Preorder traversal" +
                " of constructed tree is : ");
        tree.preOrder(tree.root);
    }
}
