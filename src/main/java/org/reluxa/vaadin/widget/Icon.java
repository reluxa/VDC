package org.reluxa.vaadin.widget;

public class Icon {
	
	String iconClass;
	
	public Icon(String iconClass) {
		this.iconClass = iconClass;
	};
	
	@Override
	public String toString() {
		return "<span class=\"icon-"+iconClass+"\">&nbsp;</span>";
	}
	
	public static Icon get(String ic) {
		return new Icon(ic);
	}
	
	public static Icon HOME = new Icon("home");
	public static Icon USERS = new Icon("users");


}
