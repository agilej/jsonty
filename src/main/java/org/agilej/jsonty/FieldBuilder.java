package org.agilej.jsonty;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.agilej.jsonty.util.StringUtil;

import me.donnior.fava.FList;
import me.donnior.fava.Function;


import static me.donnior.fava.util.FLists.*;

@SuppressWarnings("rawtypes")

public class FieldBuilder implements ScopedFieldBuilder, FieldExposeResult{

    private String name;
    private Object value;
    private Class<? extends EntityModel> entityClass;

    private boolean condition;
    private boolean hasCondition;
    private boolean hasName;
    private Environment env;
    
    public FieldBuilder(Object value) {
        this.value = value;
    }

    public ConditionalFieldBuilder withNameAndType(String name, Class<? extends EntityModel> entityClass) {
        this.hasName = true;
        this.name = name;
        this.entityClass = entityClass;
        return this;
    }

    public ConditionalFieldBuilder withName(String string) {
        return this.withNameAndType(string, null);
    }
    
    public ConditionalFieldBuilder withType(Class<? extends EntityModel> entityClass) {
        this.hasName = false;
        this.entityClass = entityClass;
        return this;        
    }
    
    @Override
    public ScopedFieldBuilder plusEnv(Environment env) {
        this.env = env;
        return this;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Environment getEnviroment(){
        return this.env;
    }

    @Override
    public Class<? extends EntityModel> getEntityClass(){
        return this.entityClass;
    }
    
    /**
     * whether this field exposition has a valid entity type
     *
     */
    @Override
    public boolean hasEntityType(){
        return this.entityClass != null;
    }


    public boolean isMapValue(){
        return this.value != null && this.value instanceof Map;
    }

    @Override
    public boolean isCollectionValue(){
        return this.value != null && this.value instanceof Collection;
    }

    @Override
    public boolean isArrayValue(){
        return this.value != null && this.value.getClass().isArray();
    }

    @Override
    public boolean isValueIterable(){
        return isArrayValue() || isCollectionValue();
    }
    
    public void unless(boolean condition){
        this.when(!condition);
    }
    
    public void when(boolean condition){
        this.hasCondition = true;
        this.condition = condition;
    }


    public boolean conditionMatched(){
        return hasCondition ? condition : true;
    }

    public boolean hasCondition(){
        return hasCondition;
    }

    @Override
    public boolean hasName(){
        return this.hasName;
    }

    @Override
    public Object getValue() {
        return value;
    }
    
    public String toJson(){
        return new FieldSerializer(this).toJson();
//        Object name = this.name != null ? this.name :"";
//        return contentWithNameAndValue(name, this.value, this.hasName);
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

        if(!isValueIterable() && hasEntityType()){
            return buildEntity(this.value,this.entityClass);
        }

        if(isArrayValue()) {
            return _array(value);
        }

        if(isCollectionValue()){
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

        FList<String> collector = $(map.entrySet()).map(new Function<Entry<Object, Object>, String>() {
            @Override
            public String apply(Entry<Object, Object> entry) {
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

        final boolean hasEntityType = this.hasEntityType();
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
                return hasEntityType ? buildEntity(value, entityClass) : value0(value);
            }

        });

        sb.append(StringUtil.join(values, ","));
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
            if(hasEntityType()){
                values.add(buildEntity(arrayElement, this.entityClass));
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
            entity.config(value, holder, env);
            return holder.build();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Is this field exposition a pure iterable value? means it value is data type and don't have a
     * explicit name. You can't use {@link #withName(String)} or {@link #withNameAndType(String, Class)}
     * to define a field exposition if you want to make it pure data, you can use {@link #withType(Class)}
     * or just ignore the 'with' clause. 
     * 
     * <br />
     * <br />
     * 
     * If this field exposition is pure iterable, it would be output as <pre><code>[1,2,3]</code></pre>
     * 
     * 
     * Otherwise it will be output as <pre><code>{"name": xxxx}</code></pre>
     * 
     * @return
     */
    public boolean isPureIterableValue(){
        return this.isValueIterable() && !hasName();
    }
    
}
