package org.agilej.jsonty.test;

import org.agilej.jsonty.FieldBuilder;
import org.agilej.jsonty.FieldExposeResult;
import org.agilej.jsonty.FieldSerializer;
import org.agilej.jsonty.mapping.Account;
import org.agilej.jsonty.mapping.AccountEntity;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class FieldSerializerTest {

    private FieldSerializer fieldSerializer;

    @Test
    public void test_to_json_with_name() throws Exception {
        FieldBuilder fer = new FieldBuilder("value");

        FieldSerializer fs = new FieldSerializer(fer);
        String json = fs.toJson();
        assertEquals("\"value\"", json);

        fer.withName("name");
        fs = new FieldSerializer(fer);
        json = fs.toJson();
        assertEquals("\"name\":\"value\"", json);
    }

    @Test
    public void test_null() {
        fieldSerializer = fieldSerializerWithValue(null);
        assertEquals("null", fieldSerializer.toJson());
    }

    @Test
    public void test_boolean() {
        fieldSerializer = fieldSerializerWithValue(false);
        assertEquals("false", fieldSerializer.toJson());
    }

    @Test
    public void test_number() {
        fieldSerializer = fieldSerializerWithValue(1.0);
        assertEquals("1.0", fieldSerializer.toJson());
    }

    @Test
    public void test_string() {
        fieldSerializer = fieldSerializerWithValue("string");
        assertEquals("\"string\"", fieldSerializer.toJson());
    }

    @Test
    public void test_single_entity(){
        Account account = new Account();
        account.login = "json";

        FieldBuilder fb = new FieldBuilder(account);
        fb.withNameAndType("account", AccountEntity.class);
        fieldSerializer = new FieldSerializer(fb);
        assertEquals("\"account\":{\"username\":\"json\"}", fieldSerializer.toJson());
    }

    @Test
    public void test_array(){
        fieldSerializer = fieldSerializerWithValue(new int[]{1,2,3});
        assertEquals("[1,2,3]", fieldSerializer.toJson());
    }

    @Test
    public void test_collection(){
        List list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);

        fieldSerializer = fieldSerializerWithValue(list);
        assertEquals("[1,2,3]", fieldSerializer.toJson());
    }


    @Test
    public void test_multi_entity_in_array(){
        Account account = new Account();
        account.login = "json";

        FieldBuilder fb = new FieldBuilder(new Account[]{account, account});
        fb.withNameAndType("account", AccountEntity.class);
        fieldSerializer = new FieldSerializer(fb);
        assertEquals("\"account\":[{\"username\":\"json\"},{\"username\":\"json\"}]", fieldSerializer.toJson());
    }

    @Test
    public void test_map(){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("one", 1);
        map.put("two", "2");

        fieldSerializer = fieldSerializerWithValue(map);
        assertEquals("{\"one\":1,\"two\":\"2\"}", fieldSerializer.toJson());
    }

    @Test
    public void test_wild(){
        Object object = new Account();
        fieldSerializer = fieldSerializerWithValue(object);
        assertEquals("\"account\"", fieldSerializer.toJson());
    }

    @Test
    public void test_multi_entity_in_collection(){
        Account account = new Account();
        account.login = "json";

        List<Account> list = new ArrayList<>();
        list.add(account);
        list.add(account);

        FieldBuilder fb = new FieldBuilder(list);
        fb.withNameAndType("account", AccountEntity.class);
        fieldSerializer = new FieldSerializer(fb);
        assertEquals("\"account\":[{\"username\":\"json\"},{\"username\":\"json\"}]", fieldSerializer.toJson());
    }

    private FieldSerializer fieldSerializerWithValue(Object value){
        return  new FieldSerializer(new FieldBuilder(value));
    }
}