package src.Blockchain.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import src.Blockchain.AvlTree;
import src.Blockchain.BlockChain;

import java.util.Comparator;

public class MyTest {
    private BlockChain<Integer> blockChain;
    private AvlTree<Integer> avlTree;
    private Comparator<Integer> comparator;
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
        avlTree.remove(3);
        assertEquals("2(1,5(4,6))",avlTree.print());
        avlTree.remove(6);
        assertEquals("2(1,5(4l))",avlTree.print());
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

    }

    @Test
    public void modifyByIndexTest(){

    }
    @Test
    public void getBlockIndexesTest(){

    }

    @Test
    public void isValidTest(){

    }
    @Test
    public void toStringTest(){

    }
    @Test
    public void getTreeTest(){

    }
    @Test
    public void setPropertiesTest(){

    }

}
