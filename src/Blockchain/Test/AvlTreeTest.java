package src.Blockchain.Test;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.runner.RunWith;
import src.Blockchain.AvlTree;

@RunWith(Arquillian.class)
public class AvlTreeTest {
    @org.junit.Test
    public void height() throws Exception {
    }

    @org.junit.Test
    public void contains() throws Exception {
    }

    @org.junit.Test
    public void getBalance() throws Exception {
    }

    @org.junit.Test
    public void insert() throws Exception {
    }

    @org.junit.Test
    public void remove() throws Exception {
    }

    @org.junit.Test
    public void preOrder() throws Exception {
    }

    @org.junit.Test
    public void preOrderHash() throws Exception {
    }

    @org.junit.Test
    public void print() throws Exception {
    }

    @org.junit.Test
    public Object clone() {
        return null;
    }

    @org.junit.Test
    public void cloneR() throws Exception {
    }

    @org.junit.Test
    public int hashCode() {
        return 0;
    }

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addClass(AvlTree.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

}
