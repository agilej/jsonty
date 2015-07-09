package org.agilej.jsonty;

import org.agilej.jsonty.util.JSONStringFormatter;
import org.agilej.jsonty.util.StringUtil;

import java.lang.reflect.Array;
import java.util.*;

/**
 *
 * Serializer for one field expose, will produce a name and value pair just like <code> "name":[1,2,3] </code>;
 * if there is no name for this field, will directly produce the value, this is for some cases like the whole
 * json will just contain only one array data.
 *
 */
public class FieldSerializer {

    private FieldExposeResult fieldExposeResult;

    public FieldSerializer(FieldExposeResult fieldExposeResult){
        this.fieldExposeResult = fieldExposeResult;
    }

    public String toJson(){
        String name = fieldExposeResult.getName();
        name = (name != null) ? name : "";
        return contentWithNameAndValue(name, fieldExposeResult.getValue(), fieldExposeResult.hasName());
    }

    private String contentWithNameAndValue(Object name, Object value, boolean hasName){
        if(hasName){
            return JSONStringFormatter.quoteWithEscape(name.toString()) + JSONS.NAME_VALUE_SEPARATOR + value0(value);
        } else {
            return value0(value).toString();
        }
    }

    private Object value0(Object value){
        if(value == null){
            //TODO deal with null
            return "null";
        }
        if(value instanceof Boolean){
            return value.toString();
        }
        if(value instanceof Number){
            return value;
        }

        if(value instanceof String){
            return JSONStringFormatter.quoteWithEscape(value.toString());
        }

        if(!fieldExposeResult.isValueIterable() && fieldExposeResult.hasEntityType()){
            return buildEntity(fieldExposeResult.getValue(), fieldExposeResult.getEntityClass());
        }

        if(fieldExposeResult.isArrayValue()) {
            return _array(value);
        }

        if(fieldExposeResult.isCollectionValue()){
            return _collection(value);
        }

        if(value instanceof Map){
            return _map(value);
        }

        return JSONStringFormatter.quoteWithEscape(value.toString());
    }

    /**
     * json value for a map present
     *
     */
    @SuppressWarnings({"unchecked" })
    private Object _map(Object value) {
        //TODO map data, for map data, should disable entity mapping,
        //can explicit set hasEntityType to false
        Map map = (Map)value;
        StringBuilder sb = new StringBuilder();
        sb.append(JSONS.OBJECT_START);


        List<String> collector = new ArrayList<String>();
        Iterator<Map.Entry<Object, Object>> it = map.entrySet().iterator();
        while(it.hasNext()){
            Map.Entry<Object, Object> entry = it.next();
            collector.add(contentWithNameAndValue(entry.getKey(),   entry.getValue(), true));
        }

/*
        FList<String> collector = $(map.entrySet()).map(new Function<Map.Entry<Object, Object>, String>() {
            @Override
            public String apply(Map.Entry<Object, Object> entry) {
                return contentWithNameAndValue(entry.getKey(),   entry.getValue(), true);
            }
        });
*/
        sb.append(StringUtil.join(collector, JSONS.FIELD_SEPARATOR));
        sb.append(JSONS.OBJECT_END);
        return sb.toString();
    }

    /**
     * json value for collection represent
     */
    @SuppressWarnings({"unchecked" })
    private Object _collection(Object value) {
        //TODO data with normal type, fall back to gson?

        Collection collection = (Collection)value;

        final boolean hasEntityType = fieldExposeResult.hasEntityType();
        StringBuilder sb = new StringBuilder();
        sb.append(JSONS.ARRAY_START);


        List<Object> values = new ArrayList<Object>();
        Iterator<Object> it = collection.iterator();
        while(it.hasNext()){
            if(hasEntityType){
                values.add(buildEntity(it.next(), fieldExposeResult.getEntityClass()));
            } else {
                values.add(value0(it.next()));
            }
        }

/*
        List<Object> values = $(collection).map(new Function<Object, Object>(){
            @Override
            public Object apply(Object value) {
                return hasEntityType ? buildEntity(value, fieldExposeResult.getEntityClass()) : value0(value);
            }

        });
*/
        sb.append(StringUtil.join(values, JSONS.FIELD_SEPARATOR));
        sb.append(JSONS.ARRAY_END);
        return sb.toString();
    }

    /**
     * json value for array represent
     *
     */
    private Object _array(Object value) {
        StringBuilder sb = new StringBuilder();
        sb.append(JSONS.ARRAY_START);

        List<Object> values = new ArrayList<Object>();
        int length = Array.getLength(value);
        for (int i = 0; i < length; i ++) {
            Object arrayElement = Array.get(value, i);
            if(fieldExposeResult.hasEntityType()){
                values.add(buildEntity(arrayElement, fieldExposeResult.getEntityClass()));
            } else {
                values.add(value0(arrayElement));
            }
        }

        sb.append(StringUtil.join(values, JSONS.FIELD_SEPARATOR));
        sb.append(JSONS.ARRAY_END);
        return sb.toString();
    }

    /**
     *
     * json value for a customized entity type present
     *
     */
    @SuppressWarnings({"unchecked" })
    private Object buildEntity(Object value, Class<? extends EntityModel> clz) {
        try {
            EntityModel entity = clz.newInstance();
            FieldsExpositionHolder holder = new FieldsExpositionHolder();
            entity.config(value, holder, fieldExposeResult.getEnvironment());
            return holder.build();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

}
