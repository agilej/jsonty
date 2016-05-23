package org.agilej.jsonty.test;

import static org.junit.Assert.assertEquals;

import java.io.*;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

import org.agilej.jsonty.JSONBuilder;
import org.agilej.jsonty.JSONModel;
import org.agilej.jsonty.support.AbstractJSONModel;
import org.junit.Test;


public class BuilderTest {

    @Test
    public void test_primary_types(){
        final int age = 12;
        final short s = 1;
        final long l = 1l;
        final String name = "srape";
        final float f = 1.23f;
        final double d = 1.25d;
        
        JSONModel module = new AbstractJSONModel() {
            public void config() {
                expose(age).withName("int");        //int
                expose('a').withName("char");       //char
                expose((byte)1).withName("byte");   //byte
                expose(s).withName("short");        //short
                expose(l).withName("long");         //long
                expose(f).withName("float");        //float
                expose(d).withName("double");       //double
                expose(name).withName("login");     //string
            }
        };
        
        //should be {"int":12,"char":"a","byte":1,"short":1,"long":1,"float":1.23,"double":1.25,"login":"srape"}
        String expected = "{\"int\":12,\"char\":\"a\",\"byte\":1,\"short\":1,\"long\":1,\"float\":1.23,\"double\":1.25,\"login\":\"srape\"}";
        assertEquals(expected, build(module));

        Writer fw = new StringWriter();
        new JSONBuilder(module).build(fw);
//        try {
//            fw = new FileWriter(new File("jsonty_resutl.json"));
//            new JSONBuilder(module).build(fw);
//            fw.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }



    @Test
    public void test_string_escape(){
        final String name = "\"foo\" is not \"bar\". specials: \b\r\n\f\t\\/";

        JSONModel module = new AbstractJSONModel() {
          public void config() {
              expose(name).withName("login");     //string
          }
        };

        //should be {"int":12,"char":"a","byte":1,"short":1,"long":1,"float":1.23,"double":1.25,"login":"srape"}
        String expected = "{\"login\":\"\\\"foo\\\" is not \\\"bar\\\". specials: \\b\\r\\n\\f\\t\\\\\\/\"}";
        System.out.println(build(module));
        assertEquals(expected, build(module));
    }
    
    
    @Test
//    @Ignore
    public void test_array_with_primary_types(){
        
        JSONModel module = new AbstractJSONModel() {
            public void config() {
                expose(new int[]{1,2,3}).withName("ints");        //ints
                expose(new String[]{"one","two","three2"}).withName("strings");        //strings
                
            }
        };
        
        String expected = "{\"ints\":[1,2,3],\"strings\":[\"one\",\"two\",\"three2\"]}";
        assertEquals(expected, build(module));
        System.out.println(build(module));
    }
    
    @Test
//    @Ignore
    public void test_ollection_with_primary_types(){
        
        JSONModel module = new AbstractJSONModel() {
            public void config() {
                expose(Arrays.asList(1, 2, 3)).withName("ints");        //ints
                expose(Arrays.asList("one","two","three")).withName("strings");        //strings
                
            }
        };
        
        String expected = "{\"ints\":[1,2,3],\"strings\":[\"one\",\"two\",\"three\"]}";
        assertEquals(expected, build(module));
        System.out.println(build(module));
    }

    @Test
    public void test_map_with_primary_types(){
        
        final Map<String, Object> map = new LinkedHashMap<String, Object>();
        map.put("name", "jam\"es");
        map.put("age", 18);
        
        JSONModel module = new AbstractJSONModel() {
            public void config() {
                expose(map).withName("map");        //map
            }
        };
        
        String expected = "{\"map\":{\"name\":\"jam\\\"es\",\"age\":18}}";
        assertEquals(expected, build(module));
        System.out.println(build(module));
    }
    
    @Test
//  @Ignore
  public void test_only_one_collection_data_with_name(){
      JSONModel module = new AbstractJSONModel() {
          public void config() {
              expose(Arrays.asList(1, 2, 3)).withName(null);        //ints
          }
      };
      
      String exptected = "{\"\":[1,2,3]}";
      assertEquals(exptected, build(module));
      System.out.println(build(module));
  }
    
    @Test
//  @Ignore
  public void test_only_one_collection_data_without_name(){
      JSONModel module = new AbstractJSONModel() {
          public void config() {
              expose(Arrays.asList(1, -2, 3));
          }
      };
      
      String exptected = "[1,-2,3]";
      assertEquals(exptected, build(module));
      System.out.println(build(module));
  }
    
    @Test
//  @Ignore
  public void test_null_value(){
      JSONModel module = new AbstractJSONModel() {
          public void config() {
              expose(null).withName("null");
          }
      };
      
      String exptected = "{\"null\":null}";
      assertEquals(exptected, build(module));
      System.out.println(build(module));
  }
    
    @Test
//  @Ignore
  public void test_bool_value(){
      JSONModel module = new AbstractJSONModel() {
          public void config() {
              expose(true).withName("bool");
          }
      };
      
      String expected = "{\"bool\":true}";
      assertEquals(expected, build(module));
      System.out.println(build(module));
  }

    private String build(JSONModel module){
        return new JSONBuilder(module).build();
    }
    
}