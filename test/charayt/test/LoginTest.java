package charayt.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import org.junit.Test;

import charayt.client.Person;

import junit.framework.TestCase;

public class LoginTest extends TestCase{

  
  String url = "jdbc:mysql://localhost/carconfigurator";
  String user = "root";
  String passwd = "root";
  Person p1 = new Person("person1","p1","p1");
  Person p2 = new Person("person2","p1","p1");
  Person p3 = new Person("person3","p1","p1");
  Person p4 = new Person("person1","p4","p1");
  Person p5 = new Person("person1","p5","p1");
  Person p6 = new Person("person1","p6","p1");
  Person p7 = new Person("person1","p1","p1");
  
  {
    registrieren(p1.getUserName(), p1.getPassword(), p1.getEmail());
    registrieren(p2.getUserName(), p1.getPassword(), p1.getEmail());
    registrieren(p3.getUserName(), p1.getPassword(), p1.getEmail());
    registrieren(p4.getUserName(), p1.getPassword(), p1.getEmail());
    registrieren(p5.getUserName(), p1.getPassword(), p1.getEmail());
    registrieren(p6.getUserName(), p1.getPassword(), p1.getEmail());
    registrieren(p7.getUserName(), p1.getPassword(), p1.getEmail());
  }
  
  
  @Override
  protected void setUp() throws Exception {
    
    super.setUp();
    
  }

  @Override
  protected void tearDown() throws Exception {
    super.tearDown();
  }
  
  
  
  @Test
  public void testLogin1() {
    
    assertNotNull(checkLogin(p1.getUserName(), p1.getPassword()));
  }
  @Test
  public void testLogin2() {
    
    assertNotNull(checkLogin(p2.getUserName(), p1.getPassword()));
  }
  @Test
  public void testLogin3() {
    
    assertNotNull(checkLogin(p3.getUserName(), p1.getPassword()));
  }
  @Test
  public void testLogin4() {
    
    assertNotNull(checkLogin(p4.getUserName(), p1.getPassword()));
  }
  @Test
  public void testLogin5() {
    
    assertNotNull(checkLogin(p5.getUserName(), p1.getPassword()));
  }
  @Test
  public void testLogin6() {
    
    assertNotNull(checkLogin(p6.getUserName(), p1.getPassword()));
  }
  @Test
  public void testLogin7() {
    
    assertNotNull(checkLogin(p7.getUserName(), p1.getPassword()));
  }
  @Test
  public void testLogin1username() {
    
Person p = checkLogin(p1.getUserName(), p1.getPassword());
assertTrue(p.userName.equals(p1.getUserName()));

  }
  
  @Test
  public void testLogin1Passwort() {
    
    Person p = checkLogin(p1.getUserName(), p1.getPassword());
    assertTrue(p.getPassword().equals(p1.getPassword()));
    
  }
  
  @Test
  public void testLogin1Email() {
    
    Person p = checkLogin(p1.getUserName(), p1.getPassword());
    assertTrue(p.getEmail()==null);
    
  }
  @Test
  public void testFalscherUsername() {
    
    Person p = checkLogin(p1.getUserName()+"p", p1.getPassword());
    assertNull(p);
    
  }
  
  @Test
  public void testFalschesPasswort() {
    
    Person p = checkLogin(p1.getUserName(), p1.getPassword()+"s");
    assertNull(p);
    
  }
  
  
  @Test
  public void testLoginP1withP2Pass() {
    
    Person p = checkLogin(p1.getUserName(), p1.getPassword());
    assertTrue(p.getPassword().equals(p2.getPassword()));
    
  }
  @Test
  public void testLoginFalscheUsernameandPassword() {
    
    Person p = checkLogin(p1.getUserName()+"x", p1.getPassword()+"r");

    assertNull(p);
  }
  
  @Test
  public void testclear() {
    
cleare();
}
  
  public Person checkLogin(String username, String passwort) {
    boolean ok = false;    
    
    try {
      Class.forName("com.mysql.jdbc.Driver");
      System.out.println("DRIVER OK !");

      Connection conn = DriverManager.getConnection(url,user,passwd);
      System.out.println("Connection sucessfull");
      
      java.sql.Statement state = conn.createStatement();
      ResultSet result = state.executeQuery("SELECT*FROM personTest where username='"+username+"' and passwort='"+passwort+"'");
      ResultSetMetaData resultMeta = result.getMetaData();
      
      for(int i=1; i<=resultMeta.getColumnCount();i++) 
        System.out.println("\n"+resultMeta.getColumnName(i).toUpperCase());
        
        while(result.next()) {
          for(int j=1;j<=resultMeta.getColumnCount();j++) {
            System.out.println("\n"+result.getObject(j).toString());
           
            
        }
          
          if(result.getObject(1).toString().equals(username) && result.getObject(2).equals(passwort))
            ok=true;
      }
    
        result.close();
        state.close();
    }
    catch(Exception e) {
      System.out.println("ERROR bei der Verbindung");
      e.printStackTrace();
    }
    
   
    if(ok) {
      Person user = new Person();
      user.setUserName(username);
      user.setPassword(passwort);
      return user;
      
    }
    else
      return null;
  
  }
  
  
public boolean registrieren(String username, String passwort, String email) {
    
    try {
      System.out.println("Entree mysqlregistration");
      Class.forName("com.mysql.jdbc.Driver");
      System.out.println("DRIVER OK !");

      Connection conn = DriverManager.getConnection(url,user,passwd);
      System.out.println("Connection sucessfull");
      
      java.sql.Statement state = conn.createStatement();
      System.out.println("regist apres le state");
      state.executeUpdate("INSERT INTO personTest value('"+username+"','"+passwort+"','"+email+"')");
      System.out.println("Yuhu insert success");
    return true;
    
  }
    catch(Exception e) {
      e.getMessage();
      System.err.println("Insertionsfehler");
      return false;
    }
  

  }
  

public void cleare() {
  
  try {
    System.out.println("Entree mysqlregistration");
    Class.forName("com.mysql.jdbc.Driver");
    System.out.println("DRIVER OK !");

    Connection conn = DriverManager.getConnection(url,user,passwd);
    System.out.println("Connection sucessfull");
    
    java.sql.Statement state = conn.createStatement();
    System.out.println("regist apres le state");
    state.executeUpdate("DELETE FROM personTest");
    System.out.println("Yuhu insert success");

  
}
  catch(Exception e) {
    e.getMessage();
    System.err.println("delete-Fehler");
  
  }


}

}
