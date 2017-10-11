package src.Blockchain.Test;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;
import src.Blockchain.BlockChain;

import static org.junit.Assert.*;

@RunWith(Arquillian.class)
public class BlockChainTest {
    @Test
    public void modifyByIndex() throws Exception {
    }

    @Test
    public void getBlockIndexes() throws Exception {
    }

    @Test
    public void setAmountZeroes() throws Exception {
    }

    @Test
    public void add() throws Exception {
    }

    @Test
    public void isValid() throws Exception {
    }

    @Test
    public void print() throws Exception {
    }

    @Test
    public void getTree() throws Exception {
    }

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addClass(BlockChain.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

}
