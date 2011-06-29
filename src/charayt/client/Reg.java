package charayt.client;

import org.apache.bcel.generic.NEW;

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.widget.Label;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.VerticalPanel;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.DateField;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.FormPanel.LabelAlign;
import com.extjs.gxt.ui.client.widget.form.HtmlEditor;
import com.extjs.gxt.ui.client.widget.form.Radio;
import com.extjs.gxt.ui.client.widget.form.RadioGroup;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.layout.ColumnData;
import com.extjs.gxt.ui.client.widget.layout.ColumnLayout;
import com.extjs.gxt.ui.client.widget.layout.FormData;
import com.extjs.gxt.ui.client.widget.layout.FormLayout;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

public class Reg extends LayoutContainer {
  
  TextField<String> passwort = new TextField<String>();
  TextField<String> username = new TextField<String>();
  
  TextField<String> Firma = new TextField<String>();
  DateField birthday = new DateField();
  TextField<String> stadt = new TextField<String>();
  TextField<String> email = new TextField<String>();
  TextField<String> land = new TextField<String>();
  FormPanel panel;
  Radio mann = new Radio();
  HtmlEditor a = new HtmlEditor();
  Label error = new Label();
  Radio frau = new Radio();
  
  private VerticalPanel vp;

  public Reg() {
    vp = new VerticalPanel();
    vp.setSpacing(10);
    vp.setStyleAttribute("background", "transparent");
    createColumnForm();
    add(vp);
   // vp.setSize("800", "560");
  }

  private void createColumnForm() {

    panel = new FormPanel();
    panel.setFrame(true);
    panel.setHeading("Registrierung");
    panel.setSize(600, -1);
    panel.setLabelAlign(LabelAlign.TOP);
    panel.setButtonAlign(HorizontalAlignment.CENTER);

    LayoutContainer main = new LayoutContainer();
    main.setStyleAttribute("margin", "5px");
    main.setLayout(new ColumnLayout());

    LayoutContainer left = new LayoutContainer();
    left.setBorders(true);
    left.setStyleAttribute("paddingRight", "5px");
    FormLayout layout = new FormLayout();
    layout.setLabelAlign(LabelAlign.TOP);
    left.setLayout(layout);

  
    username.setAllowBlank(false);
    username.setFieldLabel("Username");
    
    left.add(username, new FormData("-90"));

   
    Firma.setFieldLabel("Firma");
    left.add(Firma, new FormData("-90"));


    birthday.setFieldLabel("Birthday");
    left.add(birthday, new FormData("-90"));

 
    stadt.setFieldLabel("Stadt");
    left.add(stadt, new FormData("-90"));
    stadt.setSize("200", "22");

    LayoutContainer right = new LayoutContainer();
    right.setStyleAttribute("paddingLeft", "5px");
    layout = new FormLayout();
    right.setLayout(layout);
    FormLayout fl_right = new FormLayout();
    fl_right.setLabelAlign(LabelAlign.TOP);
    right.setLayout(fl_right);

    
    passwort.setFieldLabel("Passwort");
    passwort.setPassword(true);
    right.add(passwort, new FormData("-90"));

   
    email.setFieldLabel("Email");
    right.add(email, new FormData("-90"));

   
    land.setFieldLabel("Land");
    right.add(land, new FormData("-90"));
    land.setWidth("100");

   
    mann.setBoxLabel("Mann");

    
    frau.setBoxLabel("Frau");

    RadioGroup group = new RadioGroup();
    group.setFieldLabel("Geschlecht");
    group.add(mann);
    mann.setWidth("93px");
    group.add(frau);
    frau.setWidth("93px");
    right.add(group);

    main.add(left, new ColumnData(.5));
    main.add(right, new ColumnData(.5));

    panel.add(main, new FormData("100%"));

   
    a.setFieldLabel("Wie sind Sie auf uns gekommen?");
    a.setHeight(200);
    a.setStyleAttribute("margin", "20px");
    panel.add(a, new FormData("100%"));

    Button senden = new Button("Senden");
    senden.setSize(100, 35);
    
    senden.addListener(Events.OnClick, new Listener<ButtonEvent>() {

      @Override
      public void handleEvent(ButtonEvent be) {
        if(username.getValue()!=null && passwort.getValue()!=null && email.getValue()!=null)
            regist(username.getValue(), passwort.getValue(), email.getValue());
        
      }
    }) ;
    
    panel.addButton(senden);
    
    Button abbrechen = new Button("Loeschen");
    abbrechen.setSize(100, 35);
    
    abbrechen.addListener(Events.OnClick, new Listener<ButtonEvent>() {

      @Override
      public void handleEvent(ButtonEvent be) {
      panel.clear();
        
      }
    }) ;
    
    
    panel.addButton(abbrechen);
    
    Button zurueck = new Button("Zurueck zur Anmeldung");
    zurueck.setSize(140, 35);
    zurueck.addListener(Events.OnClick, new Listener<ButtonEvent>() {

      @Override
      public void handleEvent(ButtonEvent be) {
       goToLogin();
        
      }
    }) ;
    
    panel.addButton(zurueck);

    
    panel.add(error);
    vp.add(panel);
    panel.setSize("713px", "545px");
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
  
  
  
  private void regist(String username, String password,String email) {
    setError("tEST Register fuer " + username);

    AsyncCallback callback = new AsyncCallback() {

      @Override
      public void onFailure(Throwable caught) {
        setError("Registrierung.....fehler  "+ caught.getMessage()+"   ");
       
      }

      @Override
      public void onSuccess(Object result) {
        setError("callback  succes");
        DesktopApp.get().setLoginPage();
      }
    };

   getService().regist(username, password,email, callback);
     
  }
  
  
  
  public void goToLogin() {
    DesktopApp.get().setLoginPage();
  }
}
