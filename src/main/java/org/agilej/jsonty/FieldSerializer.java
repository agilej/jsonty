package org.agilej.jsonty;

import me.donnior.fava.FList;
import me.donnior.fava.Function;
import org.agilej.jsonty.util.StringUtil;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static me.donnior.fava.util.FLists.$;

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
            return StringUtil.quote(name.toString()) + ":" + value0(value);
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
            return StringUtil.quote(value.toString());
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

        return StringUtil.quote(value.toString());
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
        sb.append("{");

        /*
        List<String> collector = new ArrayList<String>();
        Iterator<Entry<Object, Object>> it = map.entrySet().iterator();
        while(it.hasNext()){
            Entry<Object, Object>  entry = it.next();
            collector.add(contentWithNameAndValue(entry.getKey(),   entry.getValue(), true));
        }
        */

        FList<String> collector = $(map.entrySet()).map(new Function<Map.Entry<Object, Object>, String>() {
            @Override
            public String apply(Map.Entry<Object, Object> entry) {
                return contentWithNameAndValue(entry.getKey(),   entry.getValue(), true);
            }
        });

        sb.append(StringUtil.join(collector, ","));
        sb.append("}");
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
        sb.append("[");

        /*
        List<Object> values = new ArrayList<Object>();
        Iterator<Object> it = collection.iterator();
        while(it.hasNext()){
            if(hasEntityType){
                values.add(buildEntity(it.next(), this.entityClass));
            } else {
                values.add(value0(it.next()));
            }
        }
        */

        List<Object> values = $(collection).map(new Function<Object, Object>(){
            @Override
            public Object apply(Object value) {
                return hasEntityType ? buildEntity(value, fieldExposeResult.getEntityClass()) : value0(value);
            }

        });

        sb.append(StringUtil.join(values,","));
        sb.append("]");
        return sb.toString();
    }

    /**
     * json value for array represent
     *
     */
    private Object _array(Object value) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");

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

        sb.append(StringUtil.join(values, ","));
        sb.append("]");
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
            entity.config(value, holder, fieldExposeResult.getEnviroment());
            return holder.build();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

}
