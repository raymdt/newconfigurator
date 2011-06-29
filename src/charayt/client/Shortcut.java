
package charayt.client;

import com.extjs.gxt.ui.client.GXT;
import com.extjs.gxt.ui.client.core.El;
import com.extjs.gxt.ui.client.event.ComponentEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.util.IconHelper;
import com.extjs.gxt.ui.client.util.Util;
import com.extjs.gxt.ui.client.widget.Component;
import com.extjs.gxt.ui.client.widget.IconSupport;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.AbstractImagePrototype;


public class Shortcut extends Component implements IconSupport {

  private AbstractImagePrototype icon;
  private El iconEl;
  private String text;
  private El textEl;

  public Shortcut() {

  }

  
  public Shortcut(String id, String text) {
    setId(id);
    setText(text);
  }


  public void addSelectionListener(SelectionListener<? extends ComponentEvent> listener) {
    addListener(Events.Select, listener);
  }

  public AbstractImagePrototype getIcon() {
    return icon;
  }


  public String getText() {
    return text;
  }

  @Override
  public void onComponentEvent(ComponentEvent ce) {
    super.onComponentEvent(ce);
    if (ce.getEventTypeInt() == Event.ONCLICK) {
      onClick(ce);
    }
  }


  public void removeSelectionListener(SelectionListener<? extends ComponentEvent> listener) {
    removeListener(Events.Select, listener);
  }

  public void setIcon(AbstractImagePrototype icon) {
    if (rendered) {
      iconEl.setInnerHtml("");
      iconEl.appendChild((Element) icon.createElement().cast());
    }
    this.icon = icon;

  }

  public void setIconStyle(String icon) {
    setIcon(IconHelper.create(icon, 48, 48));

  }


  public void setText(String text) {
    this.text = text;
    if(textEl != null){
      textEl.update(Util.isEmptyString(text) ? "&#160;" : text);
    }
  }

  protected void onClick(ComponentEvent ce) {
    ce.stopEvent();
    fireEvent(Events.Select, ce);
  }

  @Override
  protected void onRender(Element target, int index) {
    super.onRender(target, index);
    setElement(DOM.createElement("dt"), target, index);
    El a = el().createChild("<a href='#'></a>");
    iconEl = a.createChild("<div><img src='" + GXT.BLANK_IMAGE_URL + "' /></div>");
    textEl = a.createChild("<div></div>");

    
    el().updateZIndex(0);
    sinkEvents(Event.ONCLICK);
    if (icon != null) {
      setIcon(icon);
    }
    setText(text);
  }

}
