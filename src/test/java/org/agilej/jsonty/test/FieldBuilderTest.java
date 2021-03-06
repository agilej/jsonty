package org.agilej.jsonty.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.agilej.jsonty.EntityMapper;
import org.agilej.jsonty.Environment;
import org.agilej.jsonty.FieldBuilder;
import org.agilej.jsonty.FieldExposer;
import org.agilej.jsonty.mapping.DetailedUserEntity;
import org.junit.Test;

import static org.junit.Assert.*;


public class FieldBuilderTest {

    @Test
    public void test_constructor(){
        FieldBuilder builder = new FieldBuilder("a");
        assertEquals("a", builder.getValue());
        
        builder = new FieldBuilder(2);
        assertEquals(2, builder.getValue());

        List<String> object = new ArrayList<String>();
        builder = new FieldBuilder(object);
        assertSame(object, builder.getValue());
    }
    
    @Test
    public void test_build_with_name(){

        FieldBuilder fieldBuilder = new FieldBuilder(Arrays.asList(1, 2, 3));
        
        fieldBuilder.withName("ids");
        
        assertEquals("ids", fieldBuilder.getName());
        assertNull(fieldBuilder.getEntityClass());
        
        fieldBuilder.withName("");
        
        assertEquals("", fieldBuilder.getName());
        assertNull(fieldBuilder.getEntityClass());

        try {
            fieldBuilder.withName(null);
            fail();
        } catch (RuntimeException ex){

        }


        fieldBuilder = new FieldBuilder(new int[]{1,3,5});
        fieldBuilder.withNameAndMapping("data", ExampleEntity.class);

        assertEquals("data", fieldBuilder.getName());
        assertEquals(ExampleEntity.class, fieldBuilder.getEntityClass());
    }
    
    @Test
    public void test_condition(){
        FieldBuilder fieldBuilder = new FieldBuilder(Arrays.asList(1,2,3));
        
        fieldBuilder.withName("ids");
        
        assertFalse(fieldBuilder.hasCondition());
        assertTrue(fieldBuilder.conditionMatched());
        
        fieldBuilder.withName("ids").when(1 > 2);;
        assertTrue(fieldBuilder.hasCondition());
        assertFalse(fieldBuilder.conditionMatched());

        fieldBuilder = new FieldBuilder(Arrays.asList(1,2,3));
        fieldBuilder.withName("ids").when(1 < 2);;
        assertTrue(fieldBuilder.hasCondition());
        assertTrue(fieldBuilder.conditionMatched());

        fieldBuilder = new FieldBuilder(Arrays.asList(1,2,3));
        int age = 15;
        fieldBuilder.withName("ids").unless(age < 18);;
        assertTrue(fieldBuilder.hasCondition());
        assertFalse(fieldBuilder.conditionMatched());


        fieldBuilder = new FieldBuilder(Arrays.asList(1,2,3));
        fieldBuilder.withName("ids").unless(age > 18);;
        assertTrue(fieldBuilder.hasCondition());
        assertTrue(fieldBuilder.conditionMatched());
    }
    
    @Test
    public void test_collection_value(){
        FieldBuilder fieldBuilder = new FieldBuilder(Arrays.asList(1,2,3));
        assertTrue(fieldBuilder.isCollectionValue());
        
        fieldBuilder = new FieldBuilder(new int[]{1,3,5});
        assertTrue(fieldBuilder.isValueIterable());
        
        fieldBuilder = new FieldBuilder(null);
        assertFalse(fieldBuilder.isCollectionValue());
    }

    @Test
    public void test_map_value(){
        FieldBuilder fieldBuilder = new FieldBuilder(null);
        assertFalse(fieldBuilder.isMapValue());

        fieldBuilder = new FieldBuilder("a");
        assertFalse(fieldBuilder.isMapValue());

        fieldBuilder = new FieldBuilder(new HashMap());
        assertTrue(fieldBuilder.isMapValue());
    }

    @SuppressWarnings("rawtypes")
    @Test
    public void test_has_entity_type(){
        FieldBuilder fieldBuilder = new FieldBuilder(Arrays.asList(1,2,3));
        
        assertFalse(fieldBuilder.hasEntityType());
        
        fieldBuilder.withNameAndMapping("name", DetailedUserEntity.class);
        assertTrue(fieldBuilder.hasEntityType());
        
        fieldBuilder = new FieldBuilder(new HashMap());
        
        assertFalse(fieldBuilder.hasEntityType());
        
        fieldBuilder.withNameAndMapping("name", DetailedUserEntity.class);
        assertTrue(fieldBuilder.hasEntityType());
    }
}


class ExampleEntity implements EntityMapper<Integer> {

    @Override
    public void config(Integer object, FieldExposer exposer, Environment env) {
        
    }
    
}