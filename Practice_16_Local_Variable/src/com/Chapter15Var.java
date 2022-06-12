package com;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
public class Chapter15Var {
    public static void main(String... args) {
        var list1 = new ArrayList();
        var list2 = new ArrayList<String>();

        Map<Integer, List<String>> map1 = new HashMap<>();
        //...
        for(Map.Entry<Integer, List<String>> e: map1.entrySet()){
            List<String> names = e.getValue();
            //...
        }

        var map2 = new HashMap<Integer, List<String>>();
        //...
        for(var e: map1.entrySet()){
            var names = e.getValue();
            //...
        }

        A a1 = new AImpl();
        a1.m();
        //a1.f();  //does nto compile

        var a = new AImpl();
        a.m();
        a.f();

        //var arr = {1,2,3};
        int[] arr = {1,2,3};
        var ar = new int[2];
        System.out.println(ar[0]);

        for(var i = 0; i < 10; i++){
            //...
        }

        var aImpl = new A(){
            @Override
            public void m(){
                //...
            }

        };

        var var = 1;
        
        Function<String, Integer> noneByDefault = notUsed -> 0; // currently
        // Function<String, Integer> noneByDefault1 = _ -> 0; // proposed


         Map<String, Integer> map = new HashMap<>();
                 String key = "theInitialKey";

 /*
         map.computeIfAbsent(key, _ -> {
             String key = "theShadowKey"; // shadow variable
             return key.length();
         });
 */
                 

                 Map<String, Integer> salaries = new HashMap<>();
                 salaries.put("John", 40000);
                 salaries.put("Freddy", 30000);
                 salaries.put("Samuel", 50000);

                 salaries.replaceAll((name, oldValue) ->
                         name.equals("Freddy") ? oldValue : oldValue + 10000);

                 System.out.println(salaries);

                 BiFunction<Double, Integer, Double> f1 = (Double x, Integer y) -> x/y;
                 BiFunction<Double, Integer, Double> f2 = (x, y) -> x/y;
                 System.out.println(f2.apply(3., 2));

                 BiFunction<Double, Integer, Double> f3 = (var x, var y) -> x/y;
                 BiFunction<Double, Integer, Double> f4 = (var x, var y) -> x / y;

                 Double j = 3.;
                 Integer i = 2;
                 System.out.println(f4.apply(j, i));
    }

    public void var(int i){
        ///
    }

    interface A {
        void m();
    }

    static class AImpl implements A {
        public void m(){}
        public void f(){}
    }

}