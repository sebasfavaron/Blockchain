import java.util.ArrayList;

/**
 * Created by navi on 23/09/17.
 */
public class BlockChain<T> {

    ArrayList<Block<T>> cadena;
    int index;

    public BlockChain(){
        cadena=new ArrayList<>();
        index=0;
    }

    public void add(T elem){
        if(cadena.isEmpty()){
            cadena.add(new Block<T>(++index,))
        }
    }

    private class Block<T> {
        int indice;
        int nonce;
        T data;
        Hexa prev;
        Hexa hash;

        public Block(int index,){

        }
    }
}
