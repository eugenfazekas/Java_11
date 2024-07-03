package com.mi;

public class FunctionModel {
   
   private String id;
   private String name;
   private String argument;
   private String returnValueType;
   private String syntax;
    
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getArgument() {
		return argument;
	}
	public void setArgument(String argument) {
		this.argument = argument;
	}
	public String getReturnValueType() {
		return returnValueType;
	}
	public void setReturnValueType(String returnValueType) {
		this.returnValueType = returnValueType;
	}
	public String getSyntax() {
		return syntax;
	}
	public void setSyntax(String syntax) {
		this.syntax = syntax;
	}
	
	@Override
	public String toString() {
		return "FunctionModel [id=" + id + ", name=" + name + ", argument=" + argument + ", returnValueType="
				+ returnValueType + ", syntax=" + syntax + "]";
	}
}
