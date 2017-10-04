package Blockchain;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Created by navi on 23/09/17.
 */
public class BlockChain<T> {

    private ArrayList<Block> cadena;
    private int index;
    private Comparator<T> cmp;
    private Integer amountZeroes;
    private AvlTree<T> tree;
    
    public BlockChain(Comparator<T> cmp){
        index = 0;
        this.tree = new AvlTree<>(cmp);
        this.cmp = cmp;
        this.cadena = new ArrayList<>();
    }
    
    public boolean modifyByIndex(int index, T file) {
    	if (index + 1 > cadena.size()) {
    		return false;
    	}
    	
    	for (int i = 0; i < cadena.size(); i++) {
    		if (i == index) {
    			Block aux = cadena.get(index);
    			cadena.set(index, new Block(file, aux.prev.getHexaNumber(), aux.tree, aux.data));
    		}
    		
    	}
    	
    	return true;
    }
    
    public void setAmountZeroes(Integer amountZeroes){
        this.amountZeroes = amountZeroes;
    }

    public void add(T elem, String data){
        if(cadena.size() == 0){
            cadena.add(new Block(elem,"0", tree, data));
        } else {
            cadena.add(new Block(elem, Integer.toHexString(cadena.get(cadena.size() - 1).hashCode()), tree, data));
        }
    }
    
    public boolean isValid() {
    	String ref = "0";
    	String zeroes = "";
    	
    	for (int i = 0; i < amountZeroes; i ++) {
    		zeroes += "0";
    	}
    	
    	for (Block each: cadena) {
    		if (!each.hash.getHexaNumber().startsWith(zeroes)) {
    			return false;
    		}
    		if (!each.prev.getHexaNumber().equals(ref)) {
    			return false;
    		}
    		ref = each.hash.getHexaNumber();
    		
    	}
    	
    	return true;
    }
    
    public void print(){
        for (Block a:cadena) {
            a.print();
            System.out.println("-------------------------");
        }
    }

    public AvlTree<T> getTree() {
        return tree;
    }

    private class Block {
        private Integer indice;
        private Integer nonce;
        private String datos;
        private String data;
        private Hexa prev;
        private Hexa hash;
        private AvlTree<T> tree;

        //todo: el arbol esta hard-codeado para insertar solamente. No podemos buscar ni remover. Podriamos hacer una u otra
        // en base a la data que nos entra (si dice Removed algo hacemos remove)
        public Block(T elem, String prev, AvlTree<T> tree, String data) {
            this.indice = ++index;
            this.data = data; //queremos poner al elem directo o un mensaje onda "insert 'elem'"?
            this.tree = tree;
            this.tree.insert(elem);
            this.prev = new Hexa(prev);
            this.nonce = 0;
            this.hash = new Hexa((int) (Math.pow(2,(double)indice.hashCode())*Math.pow(5,tree.hashCode())*Math.pow(7,data.hashCode())*Math.pow(11,prev.hashCode())));
            mine();
        }
        public void mine(){
            //checks if hash starts with the required amount of zeroes, updates it if it doesn't
            while (hash.check(amountZeroes)){
                hash.inc();
            }
            nonce = hash.getNonce();
            System.out.println("Found!");
        }
        //Calculates the hash
        public int hashCode(){
            return hash.getIntNumber();
        }
        public void print(){
            System.out.println("Index = "+ indice);
            System.out.println("Nonce = "+ nonce);
            System.out.println("Data = "+ data);
            System.out.println("Previous = "+ prev);
            System.out.println("HashCode = "+ hash);
            tree.print();
        }
    }
}
