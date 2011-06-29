package charayt.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import charayt.client.Person;


public class DataAccessFacadeTest  {
    private Person person;

    
    public DataAccessFacadeTest() throws IOException {
    }

    @Before
    public void setUp() throws Exception {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testUserExists() {
        assertNotNull("User Ralphi should exist");
    }

    @Test
    public void testUserDoesNotExist() {
        assertNull(person);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testcreatePerson() {
       person = new Person();
    }

    @Test
    public void testCreateUser() throws Exception {
        assertNull(person);
    }
    
    @Test
    public void testDelete() {
        assertEquals(null, person);
    }
    
}
