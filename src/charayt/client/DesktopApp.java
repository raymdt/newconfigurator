
package charayt.client;

import com.extjs.gxt.ui.client.data.BaseModelData;
import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.event.ComponentEvent;
import com.extjs.gxt.ui.client.event.MenuEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.util.IconHelper;
import com.extjs.gxt.ui.client.widget.TabItem;
import com.extjs.gxt.ui.client.widget.TabPanel;
import com.extjs.gxt.ui.client.widget.Window;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.menu.Menu;
import com.extjs.gxt.ui.client.widget.menu.MenuItem;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.Frame;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RootPanel;


public class DesktopApp implements EntryPoint {

  private Person sessionUser = null;
  private PopupPanel vPanel ;
  
  private static DesktopApp singleton;
  
  private Desktop desktop ;
  
  
  
  
  public static DesktopApp get() {
    return singleton;
  }
  
  

  private void itemSelected(ComponentEvent ce) {
    Window w;
    if (ce instanceof MenuEvent) {
      MenuEvent me = (MenuEvent) ce;
      w = me.getItem().getData("window");
    } else {
      w = ce.getComponent().getData("window");
    }
    if (!desktop.getWindows().contains(w)) {
      desktop.addWindow(w);
    }
    if (w != null && !w.isVisible()) {
      w.show();
    } else {
      w.toFront();
    }
  }
  
  
  

  public void onModuleLoad() {
    
    
    singleton = this;
    
  setLoginPage();
  
  }
  
  public void setDesktop(Person user) {
    
    this.sessionUser = user;
   RootPanel.get("login").clear();
   
   desktop = new Desktop();
    SelectionListener<MenuEvent> menuListener = new SelectionListener<MenuEvent>() {
      @Override
      public void componentSelected(MenuEvent me) {
        itemSelected(me);
      }
    };

    SelectionListener<ComponentEvent> shortcutListener = new SelectionListener<ComponentEvent>() {
      @Override
      public void componentSelected(ComponentEvent ce) {
        itemSelected(ce);
      }
    };

    Window chesseWindows = createCheeseWindows();
    Window configuratorWindows = createConfiguratorWindows();
//    Window configuratorWindows2 = createConfiguratorWindows2();
   

    Shortcut s1 = new Shortcut();
    s1.setText("Cheese App");
    s1.setId("grid-win-shortcut");
    s1.setData("window", chesseWindows);
    s1.addSelectionListener(shortcutListener);
    desktop.addShortcut(s1);

    Shortcut s2 = new Shortcut();
    s2.setText("Car Configurator");
    s2.setId("acc-win-shortcut");
    s2.setData("window", configuratorWindows);
    s2.addSelectionListener(shortcutListener);
    desktop.addShortcut(s2);
    
//    Shortcut s3 = new Shortcut();
//    s3.setText("Car Configurator build2");
//    s3.setId("acc-win-shortcut");
//    s3.setData("window", configuratorWindows);
//    s3.addSelectionListener(shortcutListener);
//    desktop.addShortcut(s3);

    TaskBar taskBar = desktop.getTaskBar();

    StartMenu menu = taskBar.getStartMenu();
    menu.setHeading(sessionUser.getUserName());
    menu.setIconStyle("user");

    MenuItem menuItem = new MenuItem("Cheese App");
    menuItem.setData("window", chesseWindows);
    menuItem.setIcon(IconHelper.createStyle("icon-grid"));
    menuItem.addSelectionListener(menuListener);
    menu.add(menuItem);

    menuItem = new MenuItem("Tab Window");
    menuItem.setIcon(IconHelper.createStyle("tabs"));
    menuItem.addSelectionListener(menuListener);
    menuItem.setData("window", createTabWindow());
    menu.add(menuItem);

    menuItem = new MenuItem("Car Configurator");
    menuItem.setIcon(IconHelper.createStyle("accordion"));
    menuItem.addSelectionListener(menuListener);
    menuItem.setData("window", configuratorWindows);
    menu.add(menuItem);
    
//    menuItem = new MenuItem("Car Configurator build2");
//    menuItem.setIcon(IconHelper.createStyle("accordion"));
//    menuItem.addSelectionListener(menuListener);
//    menuItem.setData("window", configuratorWindows2);
//    menu.add(menuItem);

    menuItem = new MenuItem("Bogus Submenu");
    menuItem.setIcon(IconHelper.createStyle("bogus"));

    Menu sub = new Menu();

    for (int i = 0; i < 5; i++) {
      MenuItem item = new MenuItem("Test Windows " + (i + 1));
      item.setData("window", createBogusWindow(i));
      item.addSelectionListener(menuListener);
      sub.add(item);
    }

    menuItem.setSubMenu(sub);
    menu.add(menuItem);

    // tools
    MenuItem tool = new MenuItem("Settings");
    tool.setIcon(IconHelper.createStyle("settings"));
    tool.addSelectionListener(new SelectionListener<MenuEvent>() {
      @Override
      public void componentSelected(MenuEvent ce) {
      //  Info.display("Event", "The 'Settings' tool was clicked");
      }
    });
    menu.addTool(tool);

    menu.addToolSeperator();

    tool = new MenuItem("Logout");
    tool.setIcon(IconHelper.createStyle("logout"));
    tool.addSelectionListener(new SelectionListener<MenuEvent>() {
      @Override
      public void componentSelected(MenuEvent ce) {
        System.out.println("logout -clicked");
        setLoginPage();
      }
    });
    menu.addTool(tool);
  }
  
  

  private Window createConfiguratorWindows() {
    Window w = new Window();
    w.setIcon(IconHelper.createStyle("icon-grid"));
    w.setMinimizable(true);
    w.setMaximizable(true);
    w.setHeading("Zweite Test3DApp");
    w.setSize(600, 450);
    w.setLayout(new FitLayout());
    
    Frame app3d = new Frame("scenes/car3dx/AC_1038.xhtml");
    System.out.println(app3d.getUrl());



    w.add(app3d);
    return w;
  }

  
  
//  
//  private Window createConfiguratorWindows2() {
//    Window w = new Window();
//    w.setIcon(IconHelper.createStyle("icon-grid"));
//    w.setMinimizable(true);
//    w.setMaximizable(true);
//    w.setHeading("Zweite Test3DApp build2");
//    w.setSize(400, 300);
//    w.setLayout(new FitLayout());
//    
//    Frame app3d2 = new Frame("scenes/car3d/AC_1038.xhtml");
//    System.out.println(app3d2.getUrl());
//
//
//
//    w.add(app3d2);
//    return w;
//  }
  
  
  
  
  
  private Window createCheeseWindows() {
    Window w = new Window();
    w.setIcon(IconHelper.createStyle("icon-grid"));
    w.setMinimizable(true);
    w.setMaximizable(true);
    w.setHeading("Chesse");
    w.setSize(800, 600);
    w.setLayout(new FitLayout()); 
    Frame app3d = new Frame("scenes/cheese.xhtml");
    System.out.println(app3d.getUrl());



    w.add(app3d);
    return w;
  }

  private Window createTabWindow() {
    Window w = new Window();
    w.setMinimizable(true);
    w.setMaximizable(true);
    w.setSize(740, 480);
    w.setIcon(IconHelper.createStyle("tabs"));
    w.setHeading("Configurator Testapp");

    w.setLayout(new FitLayout());

    TabPanel panel = new TabPanel();

    for (int i = 0; i < 4; i++) {
      TabItem item = new TabItem("Configurator Testapp " + (i + 1));
      item.addText("Something useful would be here");
      panel.add(item);
    }

    w.add(panel);
    return w;
  }

  private Window createBogusWindow(int index) {
    Window w = new Window();
    w.setIcon(IconHelper.createStyle("bogus"));
    w.setMinimizable(true);
    w.setMaximizable(true);
    w.setHeading("Configurator Testapp " + ++index);
    w.setSize(400, 300);
    return w;
  }

  private ModelData newItem(String text, String iconStyle) {
    ModelData m = new BaseModelData();
    m.set("name", text);
    m.set("icon", iconStyle);
    return m;
  }
  
  private Window createAppStoreWindows() {
    Window w = new Window();
    w.setIcon(IconHelper.createStyle("AppStore"));
    w.setMinimizable(true);
    w.setMaximizable(true);
    w.setHeading("App-Store");
    w.setSize(800, 600);
    w.setLayout(new FitLayout()); 
    Frame hm = new Frame("http://www.hm.edu");
    System.out.println(hm.getUrl());



    w.add(hm);
    return w;
  }
  
  
  
  public void setRegister() {  
   RootPanel.get("login").clear();
   RootPanel.get("login").setStyleName("reg-panel-align");
   
   vPanel = new PopupPanel();
   vPanel.center();

   Reg register = new Reg(); 
   vPanel.add(register);

   
    RootPanel.get("login").add(vPanel);
    
  }
  
  
  public void setLoginPage() {
    
//  RootPanel.get("login").setWidth("405px");
//  RootPanel.get("login").setHeight("280px");
    RootPanel.get().clear();
    sessionUser = null;
    RootPanel.get("login").clear();
    RootPanel.get("login").setStyleName("log-panel-align");

    vPanel = new PopupPanel();
    vPanel.center();

    Log login = new Log();
    vPanel.add(login);
    

 
    RootPanel.get("login").add(vPanel);
    
  }


 public PopupPanel getlogin() {
   
   PopupPanel vPanel = new PopupPanel();
   vPanel.center();

   Log login = new Log();
   vPanel.add(login);
   return vPanel;

 }
  public void setHomePage(Person user) {

    
    
  }
  
}
