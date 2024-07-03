package com;

class Message implements java.io.Serializable {
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	String subject, text;
	
	Message(String s, String t) {
		this.subject = s;
		this.text = t;
	}
	String getSubject() {
		return subject;
	}
	
	String getText() {
		return text;
	}
}
