package org.agilej.jsonty.test;

import static org.junit.Assert.*;
import me.donnior.fava.util.FLists;

import org.agilej.jsonty.FieldsExpositionHolder;
import org.junit.Before;
import org.junit.Test;

public class FieldExpositionHolderTest {

    private FieldsExpositionHolder holder;
    
    @Before
    public void setup(){
        holder = new FieldsExpositionHolder();
    }
    
    @Test
    public void test_constructor(){
        assertEquals(0, holder.fieldsCount());
    }

    @Test
    public void test_expose(){
        holder.expose(1);
        assertEquals(1, holder.fieldsCount());
        
        holder.expose("s").withName("string");
        assertEquals(2, holder.fieldsCount());
        
        Integer value = (Integer) holder.fieldsExposeDefinition().at(0).getValue();
        assertTrue(value == 1);
    }
    
    
    
    @Test
    public void test_is_pure_array_value(){
        
        assertFalse(holder.isAPureArrayDefinition());
       
        holder.expose(1);
        assertFalse(holder.isAPureArrayDefinition());
        
        holder = new FieldsExpositionHolder();
        holder.expose(new int[]{1,2,3});
        holder.expose("two");
        assertFalse(holder.isAPureArrayDefinition());
        
        holder = new FieldsExpositionHolder();
        holder.expose(new int[]{1,2,3});
        assertTrue(holder.isAPureArrayDefinition());
        
        holder = new FieldsExpositionHolder();
        holder.expose(FLists.create(1,2,3));
        assertTrue(holder.isAPureArrayDefinition());
    }
   
}

