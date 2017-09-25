import java.util.ArrayList;

/**
 * Created by navi on 23/09/17.
 */
public class BlockChain<T> {

    Block<T>[] cadena;
    int index;

    public BlockChain(){
        cadena=new Block[0];
        index=0;
    }

    public void add(T elem){
        if(cadena.length==0){
            cadena[cadena.length]= new Block<>();
        }
    }

    private class Block<T> {
        int indice;
        int nonce;
        T data;
        Hexa prev;
        Hexa hash;

        public Block() {

        }
    }
}
