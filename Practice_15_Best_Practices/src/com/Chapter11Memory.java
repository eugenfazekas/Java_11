package com;

import com.walk.Clazz01;

import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class Chapter11Memory {
    public static void main(String... args) {
        demo1_FullMemory();
        demo2_jcmd_diagnostics();
        demo4_StackWalk();
    }

    private static void demo1_FullMemory() {
        int max = 99_888_999;
        System.out.println("Chapter11Memory.main() is running for " + max + " objects...");
        List<AnObject> list = new ArrayList<>();
        IntStream.range(0, max).forEach(i -> list.add(new AnObject(i)));
    }
    private static class AnObject {
        private int prop;
        AnObject(int i){
            this.prop = i;
        }
    }

    private static void demo2_jcmd_diagnostics() {
        int max = 60;
        System.out.println("Chapter11Memory.main() is running for " + max + " minutes...");
        try{
            Thread.sleep(max*60*1000);
        } catch (InterruptedException ex) {}
    }

   
    private static void execute3(Connection conn, String sql){
        try (Statement st = createStatement(conn)) {
            st.execute(sql);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void execute4(Statement st, String sql){
        try {
            st.execute(sql);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void execute5(Statement st, String sql){
        try {
            st.execute(sql);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if(st != null) {
                try{
                    st.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    private static void execute6(Statement st, String sql){
        try (st) {
            st.execute(sql);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void execute7(Connection conn, Statement st, String sql){
        try (conn; st) {
            st.execute(sql);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static Statement createStatement(Connection conn){
        try {
            return conn.createStatement();
        }
        catch(Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }



    private static class MyResource1 implements AutoCloseable {
        public MyResource1(){
            System.out.println("MyResource1 is acquired");
        }

        public void close() throws Exception {
            //Do what has to be done to release this resource
            System.out.println("MyResource1 is closed");
        }
    }

    private static class MyResource2 implements AutoCloseable {
        public MyResource2(){
            System.out.println("MyResource2 is acquired");
        }

        public void close() throws Exception {
            //Do what has to be done to release this resource
            System.out.println("MyResource2 is closed");
        }
    }

    private static void demo4_StackWalk(){
        System.out.println("\ndemo4_StackWalk() called by "+StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE).getCallerClass().getSimpleName());
        new Clazz01().method();
    }
}