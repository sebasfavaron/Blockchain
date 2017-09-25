import java.util.ArrayList;
import java.util.Comparator;

/**
 * Created by navi on 23/09/17.
 */
public class BlockChain<T> {

    private Block[] cadena;
    private int index;
    private Comparator<T> cmp;
    private Integer zero;
    public BlockChain(Comparator<T> cmp){
        index=0;
        this.cmp=cmp;
    }
    public void zeros(Integer zero){
        this.zero=zero;
    }

    public void add(T elem){
        if(cadena.length==0){
            cadena[cadena.length]= new Block(elem,cmp);
            cadena[cadena.length-1].mine();
        }
    }

    private class Block {
        private int indice;
        private int nOnce;
        private String datos;
        private T data;
        private String prev;
        private String hash;
        private AvlTree<T> tree;

        public Block(T elem,Comparator<T> cmp) {
            this.indice=++index;
            this.data=elem;
            this.tree=new AvlTree<>(cmp);
            this.tree.insert(elem);
            this.prev="0";
        }
        public void mine(){

        }
    }
}
