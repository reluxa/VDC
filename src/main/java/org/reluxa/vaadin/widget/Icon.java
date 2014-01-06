package org.reluxa.vaadin.widget;

public class Icon {
	
	String iconClass;
	
	String color;
	
	public Icon(String iconClass) {
		this.iconClass = iconClass;
	};
	
	public Icon(String iconClass, String color) {
		this.iconClass = iconClass;
		this.color = color;
	};

	
	@Override
	public String toString() {
		StringBuffer buf = new StringBuffer();
		
		if (color != null) {
			buf.append("<span style=\"color:green\">");	
		}
		
		buf.append("<span class=\"icon-");
		buf.append(iconClass);
		buf.append("\">&nbsp;</span>");
		
		if (color != null) {
			buf.append("</span>");	
		}

		return buf.toString();
	}
	
	
	public static Icon get(String ic) {
		return new Icon(ic);
	}
	
	public static Icon get(String ic, String color) {
		return new Icon(ic, color);
	}
	
	
	public static Icon HOME = new Icon("home");
	public static Icon USERS = new Icon("users");


}
