package org.agilej.jsonty.test;

import static org.junit.Assert.*;
import org.agilej.fava.util.FLists;

import org.agilej.jsonty.FieldsExpositionHolder;
import org.agilej.jsonty.mapping.Account;
import org.agilej.jsonty.mapping.AccountEntity;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

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
        
        Integer value = (Integer) holder.exposedFields().get(0).getValue();
        assertTrue(value == 1);
    }
    
    @Test
    public void test_is_pure_array_value(){
        
        assertFalse(holder.hasOnlyOneIterableValueWithoutName());
       
        holder.expose(1);
        assertFalse(holder.hasOnlyOneIterableValueWithoutName());
        
        holder = new FieldsExpositionHolder();
        holder.expose(new int[]{1,2,3});
        holder.expose("two");
        assertFalse(holder.hasOnlyOneIterableValueWithoutName());
        
        holder = new FieldsExpositionHolder();
        holder.expose(new int[]{1,2,3});
        assertTrue(holder.hasOnlyOneIterableValueWithoutName());
        
        holder = new FieldsExpositionHolder();
        holder.expose(FLists.create(1,2,3));
        assertTrue(holder.hasOnlyOneIterableValueWithoutName());
    }

    @Test
    public void test_is_pure_entity_value(){

        assertFalse(holder.hasOnlyOneObjectValueWithoutName());

        holder.expose(1);
        assertFalse(holder.hasOnlyOneObjectValueWithoutName());

        holder = new FieldsExpositionHolder();
        holder.expose(new Account()).withType(AccountEntity.class);
        holder.expose("two");
        assertFalse(holder.hasOnlyOneObjectValueWithoutName());

        holder = new FieldsExpositionHolder();
        holder.expose(new Account()).withType(AccountEntity.class);
        assertTrue(holder.hasOnlyOneObjectValueWithoutName());

        holder = new FieldsExpositionHolder();
        holder.expose(new Account()).withNameAndType("account", AccountEntity.class);
        assertFalse(holder.hasOnlyOneObjectValueWithoutName());
    }

    @Test
    public void test_build_with_pure_array_value(){
        holder.expose(new int[]{1,2});
        assertEquals("[1,2]", holder.build());
    }

    @Test
    public void test_build_with_pure_entity_value(){
        Account account = new Account();
        account.login = "jsonty";
        holder.expose(account).withType(AccountEntity.class);
        assertEquals("{\"username\":\"jsonty\"}", holder.build());
    }

    @Test
    public void test_build_with_pure_map_value(){
        Map<String, Integer> map = new HashMap<String, Integer>();
        map.put("one", 1);
        map.put("two", 2);
        holder.expose(map);
        assertEquals("{\"one\":1,\"two\":2}", holder.build());
    }


    @Test
    public void test_build(){
        assertEquals("{}", holder.build());

        holder.expose(new int[]{1,2}).withName("ints");
        assertEquals("{\"ints\":[1,2]}", holder.build());

        holder = new FieldsExpositionHolder();
        Account account = new Account();
        account.login = "jsonty";
        holder.expose(account).withNameAndType("account", AccountEntity.class);
        assertEquals("{\"account\":{\"username\":\"jsonty\"}}", holder.build());

    }

}

