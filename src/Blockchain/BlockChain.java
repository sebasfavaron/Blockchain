package src.Blockchain;

import com.sun.xml.internal.messaging.saaj.util.Base64;

import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
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
    			cadena.set(index, new Block(aux.prevHexa, aux.data, aux.elem));
    		}
    		
    	}
    	
    	return true;
    }

    public ArrayList<Integer> getBlockIndexes(int elem) {
        ArrayList<Integer> ret = new ArrayList<>();
        for (Block each: cadena) {
            if (each.elem.equals(elem)) {
                ret.add(each.indice);
            }
        }
        return ret;
    }
    
    public void setAmountZeroes(Integer amountZeroes){
        this.amountZeroes = amountZeroes;
    }

    public void add(T elem, String data){
        if(cadena.size() == 0){
            cadena.add(new Block("0", data, elem));
        } else {
            cadena.add(new Block(cadena.get(cadena.size() - 1).hash.getHexaNumber(), data, elem));
        }
    }
    
    public boolean isValid() {
    	String ref = "0";
    	String zeroes = "";
    	
    	for (int i = 0; i < amountZeroes; i ++) {
    		zeroes += "0";
    	}
    	
    	for (Block block: cadena) {
    		if (!block.hash.getHexaNumber().startsWith(zeroes)) {
    			return false;
    		}
    		if (!block.prevHexa.equals(ref)) {
    			return false;
    		}
    		ref = block.hash.getHexaNumber();
    		
    	}
    	
    	return true;
    }
    
    public void print(){
        for (Block block:cadena) {
            System.out.println(block);
            System.out.println("-------------------------");
        }
        System.out.println("Tree: "+tree.print());
    }

    public AvlTree<T> getTree() {
        return tree;
    }

    private class Block {
        private Integer indice;
        private Integer nonce;
        private String data;
        private String prevHexa;
        private Hexa hash;
        private T elem;

        public Block(String prevHexa, String data, T elem) {
            //En elem se guarda el dato concreto
            this.elem = elem;
            this.indice = ++index;
            //En data se guarda la info de la operacion
            this.data = data;
            this.prevHexa = prevHexa;
            this.nonce = 0;
            String concatData = indice.toString() + data + prevHexa + "." + nonce.toString(); //le pongo un '.' para reemplazar el nonce mas facil
            System.out.println("ConcatData = "+concatData);
            this.hash = new Hexa(concatData);
            mine();
            System.out.println("ConcatData = "+hash.getConcatData());
        }

        public void mine(){
            //checks if hash starts with the required amount of zeroes, updates it if it doesn't
            while (!hash.check(amountZeroes)){
                hash.inc();
            }
            nonce = hash.getNonce();
            System.out.println("Found! "+hash.getHexaNumber());
        }

        public String toString(){
            return  "Index = "+ indice.toString() + '\n' + 
                    "Nonce = "+ nonce.toString() + '\n' +
                    "Data = "+ data+ '\n' +
                    "Previous = "+ prevHexa+ '\n' +
                    "HashCode = "+ hash.getHexaNumber()+ '\n';
        }
    }
}
