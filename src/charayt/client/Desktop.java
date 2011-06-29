
package charayt.client;

import java.util.ArrayList;
import java.util.List;

import com.extjs.gxt.ui.client.core.DomQuery;
import com.extjs.gxt.ui.client.core.El;
import com.extjs.gxt.ui.client.core.XDOM;
import com.extjs.gxt.ui.client.event.WindowEvent;
import com.extjs.gxt.ui.client.event.WindowListener;
import com.extjs.gxt.ui.client.widget.ComponentHelper;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.Viewport;
import com.extjs.gxt.ui.client.widget.Window;
import com.extjs.gxt.ui.client.widget.layout.RowData;
import com.extjs.gxt.ui.client.widget.layout.RowLayout;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.RootPanel;


public class Desktop {

  protected TaskBar taskBar = new TaskBar();
  protected WindowListener listener;
  protected Viewport viewport;
  protected LayoutContainer desktop;
  protected Window activeWindow;
  protected List<Shortcut> shortcuts;
  protected List<Window> windows;
  protected El shortcutEl;

  public Desktop() {
    shortcuts = new ArrayList<Shortcut>();
    windows = new ArrayList<Window>();

    initListeners();

    viewport = new Viewport();
    viewport.setLayout(new RowLayout());

    desktop = new LayoutContainer() {
      @Override
      protected void onRender(Element parent, int index) {
        super.onRender(parent, index);
        Element el = DomQuery.selectNode("#x-desktop");
        if (el == null) {
          el = DOM.createDiv();
          el.setClassName("x-desktop");
        }
        getElement().appendChild(el);
      }
    };

    viewport.add(desktop, new RowData(1, 1));
    viewport.add(taskBar, new RowData(1, 30));

    Element el = DomQuery.selectNode("#x-shortcuts");
    if (el == null) {
      el = DOM.createDiv();
      el.setClassName("x-shortcuts");
      XDOM.getBody().appendChild(el);
    }
    shortcutEl = new El(el);
    RootPanel.get().add(viewport);
  }

 
  public void addShortcut(Shortcut shortcut) {
    if (shortcutEl != null) {
      shortcuts.add(shortcut);
      shortcut.render(shortcutEl.dom);
      ComponentHelper.doAttach(shortcut);
    }
  }


  public void addWindow(Window window) {
    if (windows.add(window)) {
      window.setContainer(desktop.getElement());
      window.addWindowListener(listener);
    }
  }

  
  public LayoutContainer getDesktop() {
    return desktop;
  }


  public StartMenu getStartMenu() {
    return taskBar.getStartMenu();
  }


  public List<Shortcut> getShortcuts() {
    return shortcuts;
  }


  public TaskBar getTaskBar() {
    return taskBar;
  }

 
  public List<Window> getWindows() {
    return windows;
  }


  public void minimizeWindow(Window window) {
    window.setData("minimize", true);
    window.hide();
  }

  
  public void removeShortcut(Shortcut shortcut) {
    if (shortcutEl != null) {
      shortcuts.remove(shortcut);
      shortcutEl.dom.removeChild(shortcut.getElement());
      ComponentHelper.doDetach(shortcut);
    }
  }


  public void removeWindow(Window window) {
    if (windows.remove(window)) {
      window.removeWindowListener(listener);
      if (activeWindow == window) {
        activeWindow = null;
      }
      taskBar.removeTaskButton((TaskButton) window.getData("taskButton"));
    }
  }

  protected void initListeners() {
    listener = new WindowListener() {
      @Override
      public void windowActivate(WindowEvent we) {
        markActive(we.getWindow());
      }

      @Override
      public void windowDeactivate(WindowEvent we) {
        markInactive(we.getWindow());
      }

      @Override
      public void windowHide(WindowEvent we) {
        onHide(we.getWindow());
      }

      @Override
      public void windowMinimize(WindowEvent we) {
        minimizeWindow(we.getWindow());
      }

      @Override
      public void windowShow(WindowEvent we) {
        onShow(we.getWindow());
      }

    };

  }

  protected void onHide(Window window) {
    if (window.getData("minimize") != null) {
      markInactive(window);
      return;
    }
    if (activeWindow == window) {
      activeWindow = null;
    }
    taskBar.removeTaskButton((TaskButton) window.getData("taskButton"));
  }

  private void markActive(Window window) {
    if (activeWindow != null && activeWindow != window) {
      markInactive(activeWindow);
    }
    taskBar.setActiveButton((TaskButton) window.getData("taskButton"));
    activeWindow = window;
    TaskButton btn = window.getData("taskButton");
    btn.addStyleName("active-win");
    window.setData("minimize", null);
  }

  private void markInactive(Window window) {
    if (window == activeWindow) {
      activeWindow = null;
      TaskButton btn = window.getData("taskButton");
      btn.removeStyleName("active-win");
    }
  }

  private void onShow(Window window) {
    TaskButton btn = window.getData("taskButton");
    window.setData("minimize", null);
    if (btn != null && taskBar.getButtons().contains(btn)) {
      return;
    }
    taskBar.addTaskButton(window);
  }

}
