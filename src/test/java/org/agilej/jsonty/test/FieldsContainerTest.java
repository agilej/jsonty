package org.agilej.jsonty.test;

import static org.junit.Assert.*;

import org.agilej.jsonty.FieldsContainer;
import org.agilej.jsonty.mapping.Account;
import org.agilej.jsonty.mapping.AccountEntity;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;

public class FieldsContainerTest {

    private FieldsContainer holder;
    
    @Before
    public void setup(){
        holder = new FieldsContainer();
    }

    @Test
    public void test_expose(){
        holder.expose(1);
        holder.expose("s").withName("string");
        
        Integer value = (Integer) holder.exposedFields().get(0).getValue();
        assertTrue(value == 1);
    }
    
    @Test
    public void test_is_pure_array_value(){
        
        assertFalse(holder.hasOnlyOneIterableValueWithoutName());
       
        holder.expose(1);
        assertFalse(holder.hasOnlyOneIterableValueWithoutName());
        
        holder = new FieldsContainer();
        holder.expose(new int[]{1,2,3});
        holder.expose("two");
        assertFalse(holder.hasOnlyOneIterableValueWithoutName());
        
        holder = new FieldsContainer();
        holder.expose(new int[]{1,2,3});
        assertTrue(holder.hasOnlyOneIterableValueWithoutName());
        
        holder = new FieldsContainer();
        holder.expose(Arrays.asList(1, 2, 3));
        assertTrue(holder.hasOnlyOneIterableValueWithoutName());
    }

    @Test
    public void test_is_pure_entity_value(){

        assertFalse(holder.hasOnlyOneObjectValueWithoutName());

        holder.expose(1);
        assertFalse(holder.hasOnlyOneObjectValueWithoutName());

        holder = new FieldsContainer();
        holder.expose(new Account()).withMapping(AccountEntity.class);
        holder.expose("two");
        assertFalse(holder.hasOnlyOneObjectValueWithoutName());

        holder = new FieldsContainer();
        holder.expose(new Account()).withMapping(AccountEntity.class);
        assertTrue(holder.hasOnlyOneObjectValueWithoutName());

        holder = new FieldsContainer();
        holder.expose(new Account()).withNameAndMapping("account", AccountEntity.class);
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
        holder.expose(account).withMapping(AccountEntity.class);
        assertEquals("{\"username\":\"jsonty\"}", holder.build());
    }

    @Test
    public void test_build_with_pure_map_value(){
        Map<String, Integer> map = new TreeMap<String, Integer>();
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

        holder = new FieldsContainer();
        Account account = new Account();
        account.login = "jsonty";
        holder.expose(account).withNameAndMapping("account", AccountEntity.class);
        assertEquals("{\"account\":{\"username\":\"jsonty\"}}", holder.build());

    }

}

