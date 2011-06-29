package charayt.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import javax.servlet.http.HttpSession;


import charayt.client.Person;
import charayt.client.Service;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;


public class ServiceImpl  extends RemoteServiceServlet implements Service{

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  private static final String USER_SESSION ="AppUser";
  
  
  private void setUserInSession(Person user) {
    HttpSession session = getThreadLocalRequest().getSession();
    session.setAttribute(USER_SESSION, user);
  }
  @Override
  public Person checkLogin(String username, String passwort) {
    boolean ok = false;
    System.out.println("HHHHHHHHHHHHHHHHHHIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIH");
    
    
    try {
      Class.forName("com.mysql.jdbc.Driver");
      System.out.println("DRIVER OK !");
      String url = "jdbc:mysql://localhost/carconfigurator";
      String user = "root";
      String passwd = "root";
      Connection conn = DriverManager.getConnection(url,user,passwd);
      System.out.println("Connection sucessfull");
      
      java.sql.Statement state = conn.createStatement();
      ResultSet result = state.executeQuery("SELECT*FROM person where username='"+username+"' and passwort='"+passwort+"'");
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
    
    System.out.println("DER OK IST "+ok);
    if(ok) {
      Person user = new Person();
      user.setUserName(username);
      setUserInSession(user);
      return user;
      
    }
    else
      return null;
  
  }
  @Override
  public boolean regist(String username, String passwort, String email) {
    
    try {
      System.out.println("Entree mysqlregistration");
      Class.forName("com.mysql.jdbc.Driver");
      System.out.println("DRIVER OK !");
      String url = "jdbc:mysql://localhost/carconfigurator";
      String user = "root";
      String passwd = "root";
      Connection conn = DriverManager.getConnection(url,user,passwd);
      System.out.println("Connection sucessfull");
      
      java.sql.Statement state = conn.createStatement();
      System.out.println("regist apres le state");
      state.executeUpdate("INSERT INTO person value('"+username+"','"+passwort+"','"+email+"')");
      System.out.println("Yuhu insert success");
    return true;
    
  }
    catch(Exception e) {
      e.getMessage();
      System.err.println("Insertionsfehler");
      return false;
    }
  

  }
}
