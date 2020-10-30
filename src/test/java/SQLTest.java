import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import fr.lunki.sqlapi.SQLAPI;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SQLTest{

    private ServerMock server;
    private SQLAPI sqlapi;

    @Before
    public void setUp(){
        server = MockBukkit.mock();
        sqlapi = MockBukkit.load(SQLAPI.class);
    }

    @After
    public void tearDown(){
        MockBukkit.unmock();
    }

    @Test
    public void testInsert(){
        System.out.println("Adding one player to the server");
        for(int i=0;i<50;i++){
            PlayerMock player = server.addPlayer();
            System.out.println(player.getUniqueId());
        }
    }
}
