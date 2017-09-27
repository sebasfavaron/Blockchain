import java.util.ArrayList;
import java.util.Comparator;

/**
 * Created by navi on 23/09/17.
 */
public class BlockChain<T> {

    private MyLinkedList<Block> cadena;
    private int index;
    private Comparator<T> cmp;
    private Integer amountZeroes;
    public BlockChain(Comparator<T> cmp){
        index=0;
        this.cmp=cmp;
    }
    public void setAmountZeroes(Integer amountZeroes){
        this.amountZeroes = amountZeroes;
    }

    public void add(T elem){
        if(cadena.length() == 0){
            cadena.add( new Block(elem,cmp));
            cadena.get(0);
        }
    }

    private class Block {
        private int indice;
        private int nonce;
        private String datos;
        private T data;
        private String prev;
        private String hash;
        private AvlTree<T> tree;

        public Block(T elem,Comparator<T> cmp) {
            this.indice = ++index;
            this.data = elem; //queremos poner al elem directo o un mensaje onda "insert 'elem'"?
            this.tree = new AvlTree<>(cmp);
            this.tree.insert(elem);
            this.prev = "0";
        }
        public void mine(){
            String aux = "";
            for(int i=0; i<amountZeroes; i++){
                aux = aux.concat("0");
            }

            //checks if hash starts with the required amount of zeroes, updates it if it doesn't
            while(hash.substring(0,amountZeroes).equals(aux)){
                hash = ((Integer) hashCode()).toString(); //lo hice re villero, fijense como hacerlo bien. no se porque hash es String y no int
                nonce++;
                System.out.println(hash);
            }
        }
        //Calculates the hash
        public int hashCode(){
            int result = 1;
            result = 31 * result + indice;
            result = 31 * result + nonce;
            result = 31 * result + data.hashCode();
            result = 31 * result + prev.hashCode();
            return result;
        }
    }
}
