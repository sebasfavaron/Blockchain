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
import java.util.List;
import java.util.Scanner;

/**
 * Created by navi on 23/09/17.
 */
public class BlockChain<T> {

    private Integer index;
    private Integer amountZeroes;
    private AvlTree<T> tree;
    private List<Block> chain;

    public BlockChain(Comparator<T> cmp){
        index = 0;
        this.tree = new AvlTree<>(cmp);
        this.chain = new ArrayList<>();
    }
    
    public boolean modifyByIndex(int index, File file) throws FileNotFoundException {
    	if (index > chain.size()) {
    		return false;
    	}
    	try {
            chain.get(index - 1).data = new String(Files.readAllBytes(Paths.get(file.getAbsolutePath())));
        } catch (IOException e) {
            System.out.println(e);
        }
        chain.get(index-1).rehash();
        return true;
    }

    public ArrayList<Integer> getBlockIndexes(int elem) {
        ArrayList<Integer> ret = new ArrayList<>();
        for (Block each: chain) {
            if (each.elem.equals(elem)) {
                ret.add(each.indice);
            }
        }
        return ret;
    }
    
    public void setAmountZeroes(Integer amountZeroes){
        this.amountZeroes = amountZeroes;
    }

    public void add(T elem, String data, AvlTree<T> tree){
        if(chain.size() == 0){
            chain.add(new Block("0", data, elem, tree));
        } else {
            chain.add(new Block(chain.get(chain.size() - 1).hash.getHexaNumber(), data, elem, tree));
        }
    }

    public void add(T elem, String data, AvlTree<T> tree, Integer nonce, String hexa, String prevHexa){
        if(chain.size() == 0){
            chain.add(new Block("0", data, elem, tree, nonce, hexa));
        } else {
            chain.add(new Block(prevHexa, data, elem, tree, nonce, hexa));
        }
    }

    public boolean isValid() {
    	String ref = "0";
    	String zeroes = "";
    	
    	for (int i = 0; i < amountZeroes; i ++) {
    		zeroes += "0";
    	}
    	
    	for (Block block: chain) {
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

    public String toString() {
        StringBuilder ret = new StringBuilder();
        for(Block block : chain) {
            ret.append(block.toString()).append("-------------------------\n");
        }
        ret.append("Index: "+index+"\n").append("AmountZeroes: "+amountZeroes+"\n").append("Tree: " + tree.print());
        return ret.toString();
    }

    public AvlTree<T> getTree() {
        return tree;
    }

    // Overwrites all previous data
    public void setProperties(Integer index, Integer amountZeroes, AvlTree<T> tree) {
        this.index = index;
        this.amountZeroes = amountZeroes;
        this.tree = tree;
    }

    public void resetChain() {
        chain = new ArrayList<>();
    }
    
    private class Block {
        private Integer indice;
        private Integer nonce;
        private String data;
        private String prevHexa;
        private Hexa hash;
        private AvlTree<T> tree;
        private T elem;

        public Block(String prevHexa, String data, T elem, AvlTree<T> tree) {
            this.elem = elem;
            this.indice = ++index;
            //Data stores the type of operation performed
            this.data = data;
            this.prevHexa = prevHexa;
            this.tree = tree;
            this.nonce = 0;
            String concatData = indice.toString() + data + prevHexa + "." + nonce.toString(); //le pongo un '.' para reemplazar el nonce mas facil
            this.hash = new Hexa(concatData);
            mine();
        }

        public Block(String prevHexa, String data, T elem, AvlTree<T> tree, Integer nonce, String hexa) {
            this.prevHexa = prevHexa;
            //Data stores the type of operation performed
            this.data = data;
            this.elem = elem;
            this.tree = tree;
            this.nonce = nonce;
            this.hash = new Hexa(hexa, nonce);
            this.indice = ++index;
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

        public AvlTree<T> getTree() {
            return tree;
        }

        public String toString(){
            return "Index: "+ indice.toString() + '\n' +
                   "Nonce: "+ nonce.toString() + '\n' +
                   "Tree: "+ tree.print()+ '\n' +
                   "Previous: "+ prevHexa+ '\n' +
                   "HashCode: "+ hash.getHexaNumber()+ '\n' +
                   "Elem: "+ elem.toString() + '\n' +
                   "Data: "+ data + '\n';
        }
    }
}
