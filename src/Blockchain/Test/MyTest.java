package src.Blockchain.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.omg.PortableInterceptor.INACTIVE;
import src.Blockchain.AvlTree;
import src.Blockchain.BlockChain;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;

public class MyTest {
    private BlockChain<Integer> blockChain;
    private AvlTree<Integer> avlTree;
    private Comparator<Integer> comparator;
    private HashSet<Integer> datos;
    private AvlTree<Integer> tree;
    @Before
    public void testInitializer() {
        comparator=new Comparator<Integer>() {
            @Override
            public int compare(Integer i1, Integer i2) {
                return i1-i2;
            }
        };
        avlTree=new AvlTree<>(comparator);
        blockChain=new BlockChain<>(comparator);
        blockChain.setAmountZeros(4);
        tree=new AvlTree<>(comparator);
        datos=new HashSet<>();
    }

    @Test
    public void insertTest(){
        avlTree.insert(1);
        assertEquals("1",avlTree.print());
        avlTree.insert(2);
        avlTree.insert(3);
        assertEquals("2(1,3)",avlTree.print());
        avlTree=new AvlTree<>(comparator);
        avlTree.insert(3);
        avlTree.insert(2);
        avlTree.insert(1);
        assertEquals("2(1,3)",avlTree.print());
        avlTree.insert(4);
        avlTree.insert(5);
        avlTree.insert(6);
        assertEquals("4(2(1,3),5(6r))",avlTree.print());
        avlTree=new AvlTree<>(comparator);
        avlTree.insert(6);
        avlTree.insert(5);
        avlTree.insert(4);
        avlTree.insert(3);
        avlTree.insert(2);
        avlTree.insert(1);
        assertEquals("3(2(1l),5(4,6))",avlTree.print());
    }
    @Test
    public void removeTest(){
        avlTree.insert(6);
        avlTree.insert(5);
        avlTree.insert(4);
        avlTree.insert(3);
        avlTree.insert(2);
        avlTree.insert(1);
        avlTree.remove(3);
        assertEquals("4(2(1l),5(6r))",avlTree.print());
        avlTree.remove(6);
        assertEquals("4(2(1l),5)",avlTree.print());
    }
    @Test
    public void containsTest(){
        avlTree.insert(3);
        assertEquals(true, avlTree.contains(3));
        avlTree.remove(3);
        assertEquals(false, avlTree.contains(3));
    }

    //BLOCKCHAIN TESTS
    @Test
    public void setAmountOfZerosTest(){
        Integer four = new Integer(4);
        blockChain.setAmountZeros(4);
        assertEquals(blockChain.getAmountZeroes(), four);
    }

    @Test
    public void addTest(){
        blockChain.setAmountZeros(4);
        avlTree.insert(1);
        datos=tree.insert(1);
        blockChain.add(datos,"Insert 1",avlTree);
        assertEquals("Insert 1",blockChain.getBlockData(1));
        datos=tree.insert(2);
        blockChain.add(datos,"Insert 2",avlTree);
        assertEquals("Insert 2",blockChain.getBlockData(2));
        assertEquals(blockChain.getBlockHash(1),blockChain.getBlockPrevious(2));
    }
    @Test
    public void getBlockIndexesTest(){
        datos=avlTree.insert(3);
        blockChain.add(datos,"Insert 3",avlTree);
        ArrayList<Integer> lista=new ArrayList<>();
        lista.add(1);
        assertEquals(lista,blockChain.getBlockIndexes(3)) ;
    }
    @Test
    public void toStringTest(){

    }
    @Test
    public void modifyByIndexTest() throws FileNotFoundException {
        datos=avlTree.insert(3);
        blockChain.add(datos,"Insert 3",avlTree);
        blockChain.modifyByIndex(1,new File("test"));
        assertEquals("tratando de romper el tp",blockChain.getBlockData(1));

    }
    @Test
    public void isValidTest(){
        datos=avlTree.insert(3);
        blockChain.add(datos,"Insert 3",avlTree);
        assertEquals(true,blockChain.isValid());
    }

    @Test
    public void setPropertiesTest(){

    }

}
