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
 * This class is an implementation of a Block Chain which is a is a continuously growing list of records, called blocks,
 * which are linked and secured using cryptography.
 * Each block typically contains a hash pointer as a link to a previous block where each block saves a data of type T
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

    /**
     * This method is used to modify an specific Block by his index with the data in the file
     * @param index Index of the block that you want to modify
     * @param file  File where to take the data
     * @return Returns true if the block data was successfully changed else false
     * @throws FileNotFoundException
     */
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

    /**
     *  (gabriel completar)
     * @param elem
     * @return
     */
    public ArrayList<Integer> getBlockIndexes(int elem) {
        ArrayList<Integer> ret = new ArrayList<>();
        for (Block each: cadena) {
            if (each.elem.equals(elem)) {
                ret.add(each.indice);
            }
        }
        return ret;
    }

    /**
     * This method is used to set the amount of zeros that his hexa has to have at the beginning
     * @param amountZeroes Number of zeros to set
     */
    public void setAmountZeroes(Integer amountZeroes){
        this.amountZeroes = amountZeroes;
    }

    public void add(T elem, String data, AvlTree<T> tree){
        if(cadena.size() == 0){
            cadena.add(new Block("0", data, elem, tree));
        } else {
            cadena.add(new Block(cadena.get(cadena.size() - 1).hash.getHexaNumber(), data, elem, tree));
        }
    }

    /**
     *  This method is used to check if all block's hashes have the amount of zeros requested
     * @return True if has the amount of zeros requested else false
     */
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

    /**
     *
     */
    public void print(){
        for (Block block:cadena) {
            System.out.println("-------------------------");
            System.out.println(block);
        }
        System.out.println("Tree: "+tree.print());
    }

    /**
     *  Return the AvlTree created
     * @return AvlTree
     */
    public AvlTree<T> getTree() {
        return tree;
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
            //En elem se guarda el dato concreto
            this.elem = elem;
            this.indice = ++index;
            //En data se guarda la info de la operacion
            this.data = data;
            this.prevHexa = prevHexa;
            this.nonce = 0;
            this.tree = tree;
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

        public AvlTree<T> getTree() {
            return tree;
        }

        public String toString(){
            return  "Index = "+ indice.toString() + '\n' + 
                    "Nonce = "+ nonce.toString() + '\n' +
                    "Data = "+ data+ '\n' +
                    "Previous = "+ prevHexa+ '\n' +
                    "HashCode = "+ hash.getHexaNumber()+ '\n' +
                    "Tree = "+ tree.print();
        }
    }
    private class Hexa {
        private String concatData;
        private String hexaNumber;
        private int nonce;

        public Hexa(String concatData) {
            nonce = 1;
            this.concatData = concatData;
            hexaNumber = sha256(concatData);
        }

        public void inc() {
            nonce++;
            // Como el nonce esta al final de concatData, justo despues de un '.',
            // lo siguiente corta el nonce anterior y concatena ahi el nuevo nonce
            concatData = concatData.substring(0, concatData.lastIndexOf(".") + 1) + nonce;
            hexaNumber = sha256(concatData);
        }

        public boolean check(int zeros) {
            char[] number = hexaNumber.toCharArray();
            for (int i=0; i<zeros; i++){
                if (number[i] != '0'){
                    return false;
                }
            }
            return true;
        }

        public String sha256(String base) {
            try{
                java.security.MessageDigest digest = java.security.MessageDigest.getInstance("SHA-256");
                byte[] hash = digest.digest(base.getBytes("UTF-8"));
                StringBuilder hexString = new StringBuilder();

                for (byte b : hash) {
                    String hex = Integer.toHexString(0xff & b);
                    if(hex.length() == 1)
                        hexString.append('0');
                    hexString.append(hex);
                }

                return hexString.toString();
            } catch(Exception ex){
                throw new RuntimeException(ex);
            }
        }

        public int getNonce() {
            return nonce;
        }

        public String toString(){
            return "HEXA = " + hexaNumber;
        }

        public String getHexaNumber(){
            return this.hexaNumber;
        }

        public String getConcatData() {
            return this.concatData;
        }

    }

}
