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
        this.cadena=new MyLinkedList<>();
    }
    public void setAmountZeroes(Integer amountZeroes){
        this.amountZeroes = amountZeroes;
    }

    public void add(T elem){
        if(cadena.length() == 0){
            cadena.add( new Block(elem,"0",new AvlTree(cmp)));
            cadena.get(0).mine();
        }
        cadena.add(new Block(elem,Integer.toHexString(cadena.get(cadena.length()-1).hashCode()),cadena.get(cadena.length()-1).tree));
    }
    public void print(){
        for (Block a:cadena) {
            a.print();
            System.out.println("-------------------------");
        }
    }

    private class Block {
        private Integer indice;
        private Integer nonce;
        private String datos;
        private T data;
        private Hexa prev;
        private Hexa hash;
        private AvlTree<T> tree;

        public Block(T elem,String prev,AvlTree tree) {
            this.indice = ++index;
            this.data = elem; //queremos poner al elem directo o un mensaje onda "insert 'elem'"?
            this.tree = tree;
            this.tree.insert(elem);
            this.prev =new Hexa(prev);
            this.hash=new Hexa((int) (Math.pow(2,(double)indice.hashCode())*Math.pow(3,nonce.hashCode())*Math.pow(5,tree.hashCode())*Math.pow(7,data.hashCode())*Math.pow(11,prev.hashCode())));
            mine();
        }
        public void mine(){
            //checks if hash starts with the required amount of zeroes, updates it if it doesn't
            while (hash.check(amountZeroes)){
                nonce++;
                hash.add(nonce);
            }
        }
        //Calculates the hash
        public int hashCode(){
            return hash.intNumber;
        }
        public void print(){
            System.out.println("Index = "+ indice);
            System.out.println("Nonce = "+ nonce);
            System.out.println("Data = "+ datos);
            System.out.println("Previous = "+ prev);
            System.out.println("HashCode = "+ hash);
            tree.print();
        }
    }
}
