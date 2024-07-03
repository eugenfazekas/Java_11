package com.mi;

public class CreateFunctionObject {

	static String createFunctionObject (FunctionModel input) {
		
		String func = "";
		
		func = "     "+input.getReturnValueType() +" " +input.getName()+ " "+ "(" +input.getArgument()+")"+  " { \n\n        " + input.getSyntax() + "\n    }"; 
		return func;
	}
}
