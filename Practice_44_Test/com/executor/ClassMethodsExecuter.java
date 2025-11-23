package com.executor;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import com.executor.java.JavaComandTVBekapcs;


public class ClassMethodsExecuter {
	
	
	public static void main(String[] args) {
		
		checkExecuteClassForMethod("tvBekapcs");
	}
	
	private final static ExecutorFunctionClass[] executorClasses = new ExecutorFunctionClass[] {
			
			new JavaComandTVBekapcs()
			
			};
	
	
	public static void checkExecuteClassForMethod(String searchedMethodName) {
		
		for(int i = 0; i < executorClasses.length ; i++) {
			
			if(executeMethodByClass(executorClasses[i] ,searchedMethodName))
				break;
		}
	}

	public static boolean executeMethodByClass(ExecutorFunctionClass class1, String methodName) {
			
		boolean result = false;
		
		Class inputClass = null;
	
		try {
			inputClass = class1.getClass();
		} catch (IllegalArgumentException | SecurityException e) {

			e.printStackTrace();
		}

		for (Method method : inputClass.getDeclaredMethods())	{
			System.out.println("ClassName: " +class1.getClass().getName()+ ", method.getName() "+method.getName());
			if(method.getName().equals(methodName)) {
//				try {
//					method.invoke(inputClass);
//					result = true;
//					return result;
//				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
				break;
			}
		}
			return result;
	}
	
//	private static <T> List<T> pushBack(List<T> list, Class<T> typeKey) throws Exception {
//    list.add(typeKey.getConstructor().newInstance());
//    return list;
	
//    public static void processClass(Class<?> clazz, String methodName) throws Exception {
//        Method method = clazz.getMethod(methodName);
//        Object instance = clazz.getDeclaredConstructor().newInstance();
//        method.invoke(instance);
//    }
//
//    public static void main(String[] args) throws Exception {
//        processClass(ReflectionTarget.class, "sayHello");
//    }
//}
//
//class ReflectionTarget {
//    public void sayHello() {
//        System.out.println("Hello, Reflection!");
//    }
	
	
}
