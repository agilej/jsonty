package org.agilej.jsonty;

import org.agilej.jsonty.util.JSONStringFormatter;
import org.agilej.jsonty.util.StringBuilderWriter;
import org.agilej.jsonty.util.StringUtil;

import java.io.IOException;
import java.io.Writer;
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
        StringBuilderWriter writer = new StringBuilderWriter();
        this.toJson(writer);
        return writer.toString();
    }

    public void toJson(Writer writer){
        String name = fieldExposeResult.getName();
        name = (name != null) ? name : "";
        contentWithNameAndValue(writer, name, fieldExposeResult.getValue(), fieldExposeResult.hasName());
    }

    private void write(Writer writer, String content){
        try {
            writer.write(content);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void contentWithNameAndValue(Writer writer, Object name, Object value, boolean hasName){
        if(hasName){
            write(writer, JSONStringFormatter.quoteWithEscape(name.toString()));
            write(writer, JSONS.NAME_VALUE_SEPARATOR);
            value0(writer, value);
        } else {
            value0(writer, value);
        }
    }

    private void value0(Writer writer, Object value) {
        if(value == null){
            write(writer, "null");
            return ;
        }
        if(value instanceof Boolean){
            write(writer, value.toString());
            return ;
        }
        if(value instanceof Number){
            write(writer, value.toString());
            return ;
        }

        if(value instanceof String){
            write(writer, JSONStringFormatter.quoteWithEscape(value.toString()));
            return ;
        }

        if(!fieldExposeResult.isValueIterable() && fieldExposeResult.hasEntityType()){
            buildEntity(writer, fieldExposeResult.getValue(), fieldExposeResult.getEntityClass());
            return;
        }

        if(fieldExposeResult.isArrayValue()) {
            _array(writer, value);
            return ;
        }

        if(fieldExposeResult.isCollectionValue()){
            _collection(writer, value);
            return ;
        }

        if(value instanceof Map){
            _map(writer, value);
            return ;
        }

        write(writer, JSONStringFormatter.quoteWithEscape(value.toString()));

    }

    /**
     * json value for a map present
     *
     */
    @SuppressWarnings({"unchecked" })
    private void _map(Writer writer, Object value) {
        write(writer, JSONS.OBJECT_START);

        Iterator<Map.Entry<Object, Object>> it = ((Map)value).entrySet().iterator();
        if (it.hasNext()) {
            Map.Entry<Object, Object> first = it.next();
            contentWithNameAndValue(writer, first.getKey(),   first.getValue(), true);
            while(it.hasNext()){
                write(writer, JSONS.FIELD_SEPARATOR);
                Map.Entry<Object, Object> entry = it.next();
                contentWithNameAndValue(writer, entry.getKey(),   entry.getValue(), true);
            }
        }

        write(writer, JSONS.OBJECT_END);
    }

    /**
     * json value for collection represent
     */
    @SuppressWarnings({"unchecked" })
    private void _collection(Writer writer, Object value) {
        final boolean hasEntityType = fieldExposeResult.hasEntityType();
        write(writer, JSONS.ARRAY_START);

        Iterator<Object> it = ((Collection)value).iterator();
        if (it.hasNext()) {
            Object first = it.next();
            if(hasEntityType){
                buildEntity(writer, first, fieldExposeResult.getEntityClass());
            } else {
                value0(writer, first);
            }

            while(it.hasNext()){
                write(writer, JSONS.FIELD_SEPARATOR);

                if(hasEntityType){
                    buildEntity(writer, it.next(), fieldExposeResult.getEntityClass());
                } else {
                    value0(writer, it.next());
                }
            }


        }

        write(writer, JSONS.ARRAY_END);
    }

    /**
     * json value for array represent
     *
     */
    private void _array(Writer writer, Object value) {
        List<Object> elements = new ArrayList<Object>();
        for (int i = 0; i < Array.getLength(value); i++) {
            elements.add(Array.get(value, i));
        }
        _collection(writer, elements);
    }

    private void buildEntity(Writer writer, Object value, Class<? extends EntityMapper> clz) {
        try {
            EntityMapper entity = clz.newInstance();
            FieldsContainer holder = new FieldsContainer();
            entity.config(value, holder, fieldExposeResult.getEnvironment());
            holder.build(writer);
        } catch (InstantiationException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

}
