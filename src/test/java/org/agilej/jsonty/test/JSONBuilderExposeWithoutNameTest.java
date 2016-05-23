package org.agilej.jsonty.test;
import static org.agilej.jsonty.util.StringUtil.jsonPair;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.Writer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.agilej.jsonty.FieldExposer;
import org.agilej.jsonty.JSONBuilder;
import org.agilej.jsonty.JSONModel;
import org.agilej.jsonty.mapping.Account;
import org.agilej.jsonty.mapping.AccountEntity;
import org.agilej.jsonty.model.Controller;
import org.junit.Test;

public class JSONBuilderExposeWithoutNameTest {

    @Test
    public void test_empty(){
        JSONModel model = new JSONModel() {
            @Override
            public void config(FieldExposer exposer) {}
        };
        assertEquals("{}", build(model));
    }
    @Test
    public void test_build_with_primitive_without_name(){
        assertEquals("1", build(modelWith(1)));
        assertEquals("true", build(modelWith(true)));
        assertEquals("false", build(modelWith(false)));
        assertEquals("\"one\"", build(modelWith("one")));
        assertEquals("[1,2,3]", build(modelWith(Arrays.asList(1,2,3))));

        Map map = new HashMap<>();
        map.put("one", 1);
        assertEquals("{\"one\":1}", build(modelWith(map)));
    }

    @Test
    public void test_build_with_only_entity(){
        final Account account = new Account();
        account.login = "donny";

        JSONModel model = new JSONModel() {
            @Override
            public void config(FieldExposer exposer) {
                exposer.expose(account).withMapping(AccountEntity.class);
            }
        };
        String json = new JSONBuilder(model).build();

        String expected = "{" + jsonPair("username", "donny", true) + "}";
        assertEquals(expected, json);

    }


    private String build(JSONModel module){
        return new JSONBuilder(module).build();
    }

    private JSONModel modelWith(final Object v) {
        return new JSONModel() {
            @Override
            public void config(FieldExposer exposer) {
                exposer.expose(v);
            }
        };
    }
}






