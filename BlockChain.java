import java.util.ArrayList;
import java.util.Comparator;

/**
 * Created by navi on 23/09/17.
 */
public class BlockChain<T> {

    Block[] cadena;
    int index;
    Comparator<T> cmp;
    public BlockChain(Comparator<T> cmp){
        cadena=new Block[0];
        index=0;
        this.cmp=cmp;
    }

    public void add(T elem){
        if(cadena.length==0){
            cadena[cadena.length]= new Block(elem,cmp);
        }
    }

    private class Block {
        int indice;
        int nonce;
        String datos;
        T data;
        String prev;
        String hash;
        AvlTree<T> tree;

        public Block(T elem,Comparator<T> cmp) {
            this.indice=++index;
            this.data=elem;
            this.tree=new AvlTree<T>(cmp);
            this.tree.insert(elem);
            this.prev="0";
        }
    }
}
