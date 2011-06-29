package charayt.client;

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.util.Margins;
import com.extjs.gxt.ui.client.widget.Composite;
import com.extjs.gxt.ui.client.widget.Label;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.CheckBox;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.FormPanel.Encoding;
import com.extjs.gxt.ui.client.widget.form.FormPanel.Method;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.layout.FormData;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
  
public class Log extends Composite {
  
  
 private TextField<String> benutzernametb;
 private TextField<String> passwordtb;
 private Label error = new Label();
 private Button btnEinloggen;
  
  public Log() { 
    setStyleAttribute("margin", "10px");  
  
    final FormPanel panel = new FormPanel();  
    panel.setPadding(22);
    panel.setLabelWidth(100);
    panel.setHeading("Logen Sie sich ein");  
    panel.setFrame(true);  
    panel.setAction("myurl");  
    panel.setEncoding(Encoding.MULTIPART);  
    panel.setMethod(Method.POST);  
    panel.setButtonAlign(HorizontalAlignment.CENTER);  
    panel.setWidth(350);  
    
  
    initComponent(panel);
    panel.setSize("390px", "251px");
  
    final TextField<String> benutzernametb = new TextField<String>();  
    benutzernametb.setFieldLabel("Name");  
    panel.add(benutzernametb);
    benutzernametb.setSize("140", "30");
    
    final TextField<String> passwordtb = new TextField<String>();  
    passwordtb.setSelectOnFocus(true);
    passwordtb.setPassword(true);
    passwordtb.setFieldLabel("passwort");  
    panel.add(passwordtb);
    passwordtb.setSize("140", "30");
    btnEinloggen = new Button("Einlogen");  
    
    panel.addButton(btnEinloggen);  
    btnEinloggen.setSize("100", "35");
    
    btnEinloggen.addListener(Events.OnClick , new Listener<ButtonEvent>() {
      
      @Override
      public void handleEvent(ButtonEvent be) {
        System.out.println("ooonclick");
        checkLogin(benutzernametb.getValue(), passwordtb.getValue());
        
      }
    });
  
    Button passwortvergessen = new Button("Registrieren");  
    passwortvergessen.addListener(Events.OnClick, new Listener<ButtonEvent>() {

      @Override
      public void handleEvent(ButtonEvent be) {
       goToRegist();
        
      }
    }) ;
      
      CheckBox chckbxAngemeldetBleiben = new CheckBox();
      chckbxAngemeldetBleiben.setHeight(35);
      
      FormData fd_chckbxAngemeldetBleiben = new FormData("100% -185");
      fd_chckbxAngemeldetBleiben.setMargins(new Margins(16, 0, 2, 110));
      panel.add(chckbxAngemeldetBleiben, fd_chckbxAngemeldetBleiben);
      chckbxAngemeldetBleiben.setWidth("150");
      chckbxAngemeldetBleiben.setBoxLabel("Angemeldet bleiben");
      chckbxAngemeldetBleiben.setHideLabel(true);
    
       
    panel.addButton(passwortvergessen);  
    passwortvergessen.setSize("150", "35");
  
 Label lblAllRightReserved = new Label("All Right reserved  Charly && Eric");
 lblAllRightReserved.setHeight(20);
//    panel.add(lblAllRightReserved, new FormData("100%"));
 FormData fd_error = new FormData("");
 fd_error.setMargins(new Margins(0, 0, 9, 0));
 fd_error.setWidth(170);
 fd_error.setHeight(45);
 error.setWidth(160);
 error.setText("error hier");
 panel.add(error, fd_error);
 error.setSize("160", "40");
  }  
  
  
  
  private void goToRegist() {

    System.out.println("OOOOOOOOOOOOOOOOOOOOOOUUUUUUUUUUUUUUUUUUUUUUUUPPPPPPPPPPPPA");
      DesktopApp.get().setRegister();
   
  }
 
  
  private void checkLogin(String username, String password) {
    setError("Login Test fuer " + username);

    AsyncCallback callback = new AsyncCallback() {

      @Override
      public void onFailure(Throwable caught) {
       // setError("Scheisse.....fehler  "+ caught.getMessage()+"   ");
        DesktopApp.get().setDesktop(new Person("ray", "raym", "ray@ray.de"));

       
      }

      @Override
      public void onSuccess(Object result) {
        setError("du succes");
        Person user = (Person) result;
       if (user != null) {
        
          
       
          DesktopApp.get().setDesktop(user);
        } else {
          setError("Falscher Benutzername oder Passwort");
        }
      }
    };

    getService().checkLogin(username, password, callback);
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

  public void setError(String err) {
    error.setText(err);
  }
}  