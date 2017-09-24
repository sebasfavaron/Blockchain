/**
 * Created by navi on 23/09/17.
 */
public class BlockChain<T> {

    Block[] blocks;

    public void insert(){}

    private class Block<T> {
        int indice;
        int nonce;
        T data;
        Hexa prev;
        Hexa hash;

        public Block(){

        }
    }
}
