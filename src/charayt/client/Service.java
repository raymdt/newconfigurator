package charayt.client;

import com.google.gwt.user.client.rpc.RemoteService;


public interface Service extends RemoteService {

  public Person checkLogin(String username, String passwort);
  
  public boolean regist(String username, String passwort,String email);
}
