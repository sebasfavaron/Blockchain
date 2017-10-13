package src.Blockchain;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * This class is an implementation of a Block Chain which is a is a continuously growing list of records, called blocks,
 * which are linked and secured using cryptography.
 * Each block typically contains a hash pointer as a link to a previous block where each block saves a data of type T
 */
public class BlockChain<T> {

    private List<Block> chain;
    private Integer index;
    private Integer amountZeroes;
    private AvlTree<T> tree;
    private Comparator<T> cmp;

    public BlockChain(Comparator<T> cmp){
        index = 0;
        this.tree = new AvlTree<>(cmp);
        this.cmp = cmp;
        this.chain = new ArrayList<>();
    }

    /**
     * This method is used to modify an specific Block by his index with the data in the file
     * @param index Index of the block that you want to modify
     * @param file  File where to take the data
     * @return Returns true if the block data was successfully changed else false
     * @throws FileNotFoundException
     */
    public boolean modifyByIndex(int index, File file) throws FileNotFoundException {
    	if (index > chain.size()) {
    		return false;
    	}
    	try {
            chain.get(index - 1).data = new String(Files.readAllBytes(Paths.get(file.getAbsolutePath())));
        } catch (IOException e) {
            System.out.println("File not found.");
            return false;
        }
        chain.get(index-1).rehash();
        return true;
    }

    /**
     * This method returns a list with all the blocks from the blockchain
     * that are related with the elem
     * @param elem
     * @return ret which contains all the blocks that satisfies the condition
     */
    public ArrayList<Integer> getBlockIndexes(int elem) {
        ArrayList<Integer> ret = new ArrayList<>();
        for (Block each: chain) {
            if (each.elem.contains(elem)) {
                ret.add(each.indice);
            }
        }
        return ret;
    }

    /**
     * This method is used to set the amount of zeros that his hexa has to have at the beginning
     * @param amountZeroes Number of zeros to set
     */
    public void setAmountZeros(Integer amountZeroes){
        this.amountZeroes = amountZeroes;
    }

    public void add(HashSet<T> elem, String data, AvlTree<T> tree){
        if(chain.size() == 0){
            chain.add(new Block(elem,"0", data, tree));
        } else {
            chain.add(new Block(elem,chain.get(chain.size() - 1).hash.getHexaNumber(), data, tree));
        }
    }
    
    public void add(HashSet<T> elem, String data, AvlTree<T> tree, Integer nonce, String hexa, String prevHexa){
        if(chain.size() == 0){
            chain.add(new Block("0", data, elem, tree, nonce, hexa));
        } else {
            chain.add(new Block(prevHexa, data, elem, tree, nonce, hexa));
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

    /**
     *
     */
    public String toString() {
        StringBuilder ret = new StringBuilder();
        for(Block block : chain) {
            ret.append(block.toString()).append("-------------------------\n");
        }
        ret.append("Index: "+index+"\n").append("AmountZeroes: "+amountZeroes+"\n").append("Tree: " + tree.print());
        return ret.toString();
    }

    /**
     *  Return the AvlTree created
     * @return AvlTree
     */
    public AvlTree<T> getTree() {
        return tree;
    }
    
    // Overwrites all previous data
    public void setProperties(Integer index, Integer amountZeroes, AvlTree<T> tree) {
        this.index = index;
        this.amountZeroes = amountZeroes;
        this.tree = tree;
    }
    public String getBlockData(int i){
        return chain.get(i-1).getData();
    }
    public String getBlockHash(int i){
        return chain.get(i-1).hash.hexaNumber;
    }
    public String getBlockPrevious(int i){
        return chain.get(i-1).prevHexa;
    }
    public Integer getIndex(){
        return index;
    }
    public Integer getAmountZeroes(){
        return amountZeroes;
    }
    public Comparator<T> getCmp(){
        return cmp;
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
        private HashSet<T> elem;

        public Block(HashSet elems,String prevHexa, String data, AvlTree<T> tree) {
            this.elem = elems;
            this.indice = ++index;
            ////Data stores the type of operation performed
            this.data = data;
            this.prevHexa = prevHexa;
            this.nonce = 0;
            this.tree = tree;
            String concatData = indice.toString() + data + prevHexa + "." + nonce.toString(); //le pongo un '.' para reemplazar el nonce mas facil
            this.hash = new Hexa(concatData);
            mine();
        }
        
        public Block(String prevHexa, String data, HashSet elem, AvlTree<T> tree, Integer nonce, String hexa) {
            this.prevHexa = prevHexa;
            //Data stores the type of operation performed
            this.data = data;
            this.elem = elem;
            this.tree = tree;
            this.nonce = nonce;
            this.hash = new Hexa(hexa, nonce);
            this.indice = ++index;
        }

        /**
         * This method is used to retrive a data from a block
         * @return data of a blockchain
         */
        public String getData(){
            return data;
        }

        private void mine(){
            //checks if hash starts with the required amount of zeroes, updates it if it doesn't
            while (!hash.check(amountZeroes)){
                hash.inc();
            }
            nonce = hash.getNonce();
        }

        private void rehash(){
            String concatData = indice.toString() + data + prevHexa + nonce.toString() + nonce.toString(); //le pongo un '.' para reemplazar el nonce mas facil
            this.hash = new Hexa(concatData);
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
    private class Hexa {
        private String concatData;
        private String hexaNumber;
        private int nonce;

        public Hexa(String concatData) {
            nonce = 1;
            this.concatData = concatData;
            hexaNumber = sha256(concatData);
        }
        
        //For the load operation
        public Hexa(String hexaNumber, Integer nonce) {
            this.hexaNumber = hexaNumber;
            this.nonce = nonce;
        }

        private void inc() {
            nonce++;
            // To replace nonce just replace what's after the '.' with the new nonce
            concatData = concatData.substring(0, concatData.lastIndexOf(".") + 1) + nonce;
            hexaNumber = sha256(concatData);
        }

        private boolean check(int zeros) {
            char[] number = hexaNumber.toCharArray();
            for (int i=0; i<zeros; i++){
                if (number[i] != '0'){
                    return false;
                }
            }
            return true;
        }

        private String sha256(String base) {
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

        private int getNonce() {
            return nonce;
        }

        public String toString(){
            return "HEXA = " + hexaNumber;
        }

        private String getHexaNumber(){
            return this.hexaNumber;
        }

    }

}
