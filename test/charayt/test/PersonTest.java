package charayt.test;



import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import charayt.client.Person;

public class PersonTest {
  Person person;
  @Before
  public void setUp() throws Exception {
    System.out.println("test Beginn");
    person = new Person("testusername", "testpasswort", "testemail");
  }
  
  @After
  public void tearDown() {
    person =null;
    System.out.println("test Vorbei");
  }

  @Test
  public void testUserErzeugtNotNull() {
      assertNotNull(person);
  }

  @Test
  public void testUserNameGetterTrue() {
assertEquals("testusername", person.getUserName());
  }
  
  @Test
  public void testUserNameGetterFalse() {
    assertFalse(person.getUserName().equals("bingo"));
  }
  
  
  
    @Test
  public void testUserNameSetter() {
      person.setUserName("tchinda");

      assertEquals("tchinda",person.getUserName());
  }

    
    @Test
    public void testPasswortGetterTrue() {
  assertEquals("testpasswort", person.getPassword());
    }
    
    @Test
    public void testpasswortGetterFalse() {
      assertFalse(person.getPassword().equals("bingo"));
    }
    
    
    
      @Test
    public void testPasswortSetter() {
        person.setPassword("passw");

        assertEquals("passw",person.getPassword());
    }
    
  @Test
  public void testCreateUser() throws Exception {
      assertNotNull("user Homer should have been created");
  }
  
  @Test
  public void testDelete() {
 person = null;
 assertNull(person);
  }
  
}
