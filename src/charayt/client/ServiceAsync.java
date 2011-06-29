package charayt.client;

import com.google.gwt.user.client.rpc.AsyncCallback;


public interface ServiceAsync {

  public void checkLogin(String username, String passwort, AsyncCallback<Person> callback);
  
  public void regist(String username, String passwort,String email, AsyncCallback<Boolean> callback);
}
