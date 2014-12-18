package org.agilej.jsonty.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.List;

import me.donnior.fava.util.FLists;

import org.agilej.jsonty.EntityModel;
import org.agilej.jsonty.Environment;
import org.agilej.jsonty.FieldBuilderImpl;
import org.agilej.jsonty.FieldExposer;
import org.agilej.jsonty.mapping.DetailedUserEntity;
import org.junit.Test;

import com.google.common.collect.Lists;

public class FieldBuilderImplTest {

    @Test
    public void test_constructor(){
        FieldBuilderImpl builder = new FieldBuilderImpl("a");
        assertEquals("a", builder.getValue());
        
        builder = new FieldBuilderImpl(2);
        assertEquals(2, builder.getValue());
        
        List<String> object = Lists.newArrayList();
        builder = new FieldBuilderImpl(object);
        assertSame(object, builder.getValue());
    }
    
    @Test
    public void test_build_withName(){

        FieldBuilderImpl fieldBuilder = new FieldBuilderImpl(FLists.create(1,2,3));
        
        fieldBuilder.withName("ids");
        
        assertEquals("ids", fieldBuilder.getName());
        assertNull(fieldBuilder.getEntityClass());
        
        fieldBuilder.withName("");
        
        assertEquals("", fieldBuilder.getName());
        assertNull(fieldBuilder.getEntityClass());
        
        fieldBuilder.withName(null);
        
        assertNull(fieldBuilder.getName());
        assertNull(fieldBuilder.getEntityClass());
        
        
        fieldBuilder = new FieldBuilderImpl(new int[]{1,3,5});
        fieldBuilder.withNameAndType("data", ExampleEntity.class);

        assertEquals("data", fieldBuilder.getName());
        assertEquals(ExampleEntity.class, fieldBuilder.getEntityClass());
    }
    
    @Test
    public void test_condition(){
        FieldBuilderImpl fieldBuilder = new FieldBuilderImpl(FLists.create(1,2,3));
        
        fieldBuilder.withName("ids");
        
        assertFalse(fieldBuilder.hasCondition());
        assertTrue(fieldBuilder.conditionMatched());
        
        fieldBuilder.withName("ids").when(1 > 2);;
        assertTrue(fieldBuilder.hasCondition());
        assertFalse(fieldBuilder.conditionMatched());
        
        fieldBuilder.withName("ids").when(1 < 2);;
        assertTrue(fieldBuilder.hasCondition());
        assertTrue(fieldBuilder.conditionMatched());
        
        int age = 15;
        fieldBuilder.withName("ids").unless(age < 18);;
        assertTrue(fieldBuilder.hasCondition());
        assertFalse(fieldBuilder.conditionMatched());
    }
    
    @Test
    public void test_collection_value(){
        FieldBuilderImpl fieldBuilder = new FieldBuilderImpl(FLists.create(1,2,3));
        assertTrue(fieldBuilder.isCollectionValue());
        
        fieldBuilder = new FieldBuilderImpl(new int[]{1,3,5});
        assertTrue(fieldBuilder.isValueIterable());
        
        fieldBuilder = new FieldBuilderImpl(null);
        assertFalse(fieldBuilder.isCollectionValue());
    }
    
    @SuppressWarnings("rawtypes")
    @Test
    public void test_has_entity_type(){
        FieldBuilderImpl fieldBuilder = new FieldBuilderImpl(FLists.create(1,2,3));
        
        assertFalse(fieldBuilder.hasEntityType());
        
        fieldBuilder.withNameAndType("name", DetailedUserEntity.class);
        assertTrue(fieldBuilder.hasEntityType());
        
        fieldBuilder = new FieldBuilderImpl(new HashMap());
        
        assertFalse(fieldBuilder.hasEntityType());
        
        fieldBuilder.withNameAndType("name", DetailedUserEntity.class);
        assertTrue(fieldBuilder.hasEntityType());
    }
}


class ExampleEntity implements EntityModel<Integer>{

    @Override
    public void config(Integer object, FieldExposer exposer, Environment env) {
        
    }
    
}