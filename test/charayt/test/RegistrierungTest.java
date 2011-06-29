package charayt.test;

import static org.junit.Assert.assertEquals;

import java.sql.Connection;
import java.sql.DriverManager;

import org.junit.Test;

import junit.framework.TestCase;
import charayt.client.DesktopApp;
import charayt.client.Person;
import charayt.client.Service;
import charayt.client.ServiceAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

public class RegistrierungTest extends TestCase{
  String url = "jdbc:mysql://localhost/carconfigurator";
  String user = "root";
  String passwd = "root";
  Person testperson;
  @Override
  protected void setUp() throws Exception {
    
    super.setUp();
    System.err.println("setup");
   
    testperson = new Person("charly","charlypass","charlyemail");
  }

  @Override
  protected void tearDown() throws Exception {

  
  }
  
  @Test
  public void testInsert1() {
    
assertTrue(registrieren(testperson.getUserName(), testperson.getPassword(), testperson.getEmail()));
  }
  
  
  @Test
  public void testInsert2() {
    
    assertFalse(registrieren(testperson.getUserName(), testperson.getPassword(), testperson.getEmail()));
  }
  
  @Test
  public void testInsert4() {
    
    assertTrue(registrieren(testperson.getUserName()+"h", testperson.getPassword(), testperson.getEmail()));
  }
  
  
  @Test
  public void testInsert5() {
    
    assertFalse(registrieren(testperson.getUserName()+"h", testperson.getPassword(), testperson.getEmail()));
  }
  
  @Test
  public void testDatabaseurl() {
    url="fake url";
    assertFalse(registrieren(testperson.getUserName()+"y", testperson.getPassword(), testperson.getEmail()));
  }
  
  @Test
  public void testDatabaseusername() {
 user="fakeuser";
    assertFalse(registrieren(testperson.getUserName()+"y", testperson.getPassword(), testperson.getEmail()));
 
  }
  
  
  @Test
  public void testDatabasePass() {
    passwd="fakepasswd";
    assertFalse(registrieren(testperson.getUserName()+"y", testperson.getPassword(), testperson.getEmail()));
    cleare();
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


  private ServiceAsync getService() {
    System.out.println("Service asynnys");
    ServiceAsync svc = (ServiceAsync) GWT.create(Service.class);
    ServiceDefTarget endpoint = (ServiceDefTarget) svc;
    if(GWT.isScript()==false) {
    endpoint.setServiceEntryPoint(GWT.getModuleBaseURL() + "Service");
    }
    else {
      endpoint.setServiceEntryPoint("Service");
    }
    return svc;
  }

  
  
  
  private void regist(String username, String password,String email) {
   

    AsyncCallback callback = new AsyncCallback() {

      @Override
      public void onFailure(Throwable caught) {
      
       
      }

      @Override
      public void onSuccess(Object result) {
    
        DesktopApp.get().setLoginPage();
      }
    };

   getService().regist(username, password,email, callback);
     
  }
  
  
  
  public void goToLogin() {
    DesktopApp.get().setLoginPage();
  }
}


