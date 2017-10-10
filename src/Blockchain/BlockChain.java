package src.Blockchain;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

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
    
    public boolean modifyByIndex(int index, File file) throws FileNotFoundException {
    	if (index > cadena.size()) {
    		return false;
    	}
    	try {
            cadena.get(index - 1).data = new String(Files.readAllBytes(Paths.get(file.getAbsolutePath())));
        } catch (IOException e) {
            System.out.println(e);
        }
        cadena.get(index-1).rehash();
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
            System.out.println("-------------------------");
            System.out.println(block);
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
            this.hash = new Hexa(concatData);
            mine();
        }

        public void mine(){
            //checks if hash starts with the required amount of zeroes, updates it if it doesn't
            while (!hash.check(amountZeroes)){
                hash.inc();
            }
            nonce = hash.getNonce();
        }

        public void rehash(){
            String concatData = indice.toString() + data + prevHexa + "." + nonce.toString(); //le pongo un '.' para reemplazar el nonce mas facil
            this.hash = new Hexa(concatData);
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
