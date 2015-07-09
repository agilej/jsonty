package org.agilej.jsonty.test;
import static org.agilej.jsonty.util.StringUtil.jsonPair;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.Writer;

import org.agilej.jsonty.FieldBuilder;
import org.agilej.jsonty.FieldExposer;
import org.agilej.jsonty.JSONBuilder;
import org.agilej.jsonty.JSONModel;
import org.agilej.jsonty.mapping.Account;
import org.agilej.jsonty.mapping.AccountEntity;
import org.agilej.jsonty.model.Controller;
import org.junit.Test;

public class JSONBuilderTest {
    
    @Test
    public void test_integration(){
        JSONBuilder builder = new Controller().jsonResult();
        
        System.out.println(builder.build());
        
/*
        assertEquals(2, builder.fieldsCount());
        
        FieldBuilder impl = builder.exposedFields().get(0);
        assertEquals("users", impl.getName());
        assertFalse(impl.conditionMatched());
        assertNull(impl.getEntityClass());
        
        impl = builder.exposedFields().get(1);
        assertEquals("account", impl.getName());
        assertTrue(impl.conditionMatched());
        assertTrue(impl.getEntityClass().equals(AccountEntity.class));
*/
        
    }
    
    @Test
    public void test_build(){
        JSONBuilder builder = new JSONBuilder(new JSONModel() {
            @Override
            public void config(FieldExposer exposer) {
                exposer.expose("donny").withName("user").when(2 > 1);
                exposer.expose("passwd").withName("password").when(1 > 2);
                exposer.expose(23).withName("age");
            }
        });
        String expected = "{" + jsonPair("user", "donny", true) + "," + jsonPair("age", 23, false)+ "}";
        assertEquals(expected, builder.build());
    }

    @Test
    public void test_build_with_only_entity(){
        final Account account = new Account();
        account.login = "donny";

        JSONModel model = new JSONModel() {
            @Override
            public void config(FieldExposer exposer) {
                exposer.expose(account).withType(AccountEntity.class);
            }
        };
        String json = new JSONBuilder(model).build();

        String expected = "{" + jsonPair("username", "donny", true) + "}";
        assertEquals(expected, json);

    }

    @Test(expected = RuntimeException.class)
    public void test_build_to_writer(){
        JSONModel model = new JSONModel() {
            @Override
            public void config(FieldExposer exposer) {
                exposer.expose(1).withName("one");
            }
        };
        new JSONBuilder(model).build((Writer) null);
    }

   /*
    @Test
    public void test_is_pure_array_expose(){
        JSONBuilder builder = new JSONBuilder(new JSONModel() {
            @Override
            public void config(FieldExposer exposer) {
                exposer.expose(23).withName("age");
            }
        });

        assertFalse(builder.hasOnlyOneIterableValueWithoutName());

        builder = new JSONBuilder(new JSONModel() {
            @Override
            public void config(FieldExposer exposer) {
                exposer.expose(new int[]{1,2});
            }
        });

        assertTrue(builder.hasOnlyOneIterableValueWithoutName());


    }
    */
}






