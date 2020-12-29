import org.junit.*;
import org.junit.validator.PublicClassValidator;

import java.util.*;
import play.test.*;
import models.*;

public class BasicTest extends UnitTest {

    @Before
    public void setup(){
        Fixtures.deleteDatabase();
    }

    @Test
    public void CreateandRetrieveUser(){
        //Creamos un usuario nuevo 
        new User ("Axel","pito").save(); 
        User axel = User.find("byName", "Axel").first(); 



        //miramos si funciona 
        assertNotNull(axel);

        assertEquals("Axel", axel.name);
        assertEquals("pito", axel.password);
        assertNotNull(User.exists("Axel", "pito"));
        assertNull(User.exists("Axel", "pingo"));
        assertEquals(0, User.deleteUser(axel));
    }
   

}
