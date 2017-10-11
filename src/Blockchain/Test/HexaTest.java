package src.Blockchain.Test;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class HexaTest {
    @Test
    public void inc() throws Exception {
    }

    @Test
    public void check() throws Exception {
    }

    @Test
    public void sha256() throws Exception {
    }

    @Test
    public void getNonce() throws Exception {
    }

    @Test
    public String toString() {
        return null;
    }

    @Test
    public void getHexaNumber() throws Exception {
    }

    @Test
    public void getConcatData() throws Exception {
    }

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addClass(Hexa.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

}
