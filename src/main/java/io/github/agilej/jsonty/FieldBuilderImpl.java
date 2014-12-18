package io.github.agilej.jsonty;

import io.github.agilej.jsonty.util.StringUtil;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.google.common.base.Joiner;

@SuppressWarnings("rawtypes")

public class FieldBuilderImpl implements ScopedFieldBuilder{

    private String name;
    private Object value;
    private Class<? extends SrapeEntity> entityClass;

    private boolean condition;
    private boolean hasConditon;
    private boolean hasName;
    private Environment env;
    
    public FieldBuilderImpl(Object value) {
        this.value = value;
    }

    public ConditionalFieldBuilder withNameAndType(String name, Class<? extends SrapeEntity> entityClass) {
        this.hasName = true;
        this.name = name;
        this.entityClass = entityClass;
        return this;
    }

    public ConditionalFieldBuilder withName(String string) {
        return this.withNameAndType(string, null);        
    }
    
    public ConditionalFieldBuilder withType(Class<? extends SrapeEntity> entityClass) {
        this.hasName = false;
        this.entityClass = entityClass;
        return this;        
    }
    
    @Override
    public ScopedFieldBuilder plusEnv(Environment env) {
        this.env = env;
        return this;
    }
    
    public String getName() {
        return name;
    }
    
    public Class<? extends SrapeEntity> getEntityClass(){
        return this.entityClass;
    }
    
    /**
     * whether this field exposition has a valid entity type, not just set entity type manually,
     * the value must a not Map value.
     * 
     * @return
     */
    public boolean hasEntityType(){
//        return this.clz != null && !this.isMapValue();
        return this.entityClass != null;
    }

    public boolean isMapValue(){
        return this.value != null && this.value instanceof Map;
    }
    
    public boolean isCollectionValue(){
        return this.value != null && this.value instanceof Collection;
    }
    
    public boolean isArrayValue(){
        return this.value != null && this.value.getClass().isArray();
    }
    
    public boolean isValueIterable(){
        return isArrayValue() || isCollectionValue();
    }
    
    public void unless(boolean condition){
        this.when(!condition);
    }
    
    public void when(boolean condition){
        this.hasConditon = true;
        this.condition = condition;
    }
    
    public boolean conditionMatched(){
        return hasConditon ? condition : true;
    }

    public boolean hasCondition(){
        return hasConditon;
    }
    
    public boolean hasName(){
        return this.hasName;
    }
    
    public Object getValue() {
        return value;
    }
    
    public String toJson(){
        Object name = this.name != null ? this.name :"";
        return contentWithNameAndValue(name, this.value, this.hasName);
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
//            return StringUtil.quote(value.toString());
            return StringUtil.quote(value.toString());      //TODO is this enough? like string \" escaping?
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

    @SuppressWarnings({"unchecked" })
    private Object _map(Object value) {
        //TODO map data, for map data, should disable entity mapping, 
        //can explicit set hasEntityType to false
        Iterator<Entry<Object, Object>> it = ((Map)value).entrySet().iterator();
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        List<String> collector = new ArrayList<String>();
        while(it.hasNext()){
            Entry<Object, Object>  entry = it.next();
            collector.add(contentWithNameAndValue(entry.getKey(),   entry.getValue(), true));
        }
        sb.append(Joiner.on(",").join(collector));
        sb.append("}");
        return sb.toString();
    }

    //json value for collection represent
    @SuppressWarnings({"unchecked" })
    private Object _collection(Object value) {
        //TODO data with normal type, fall back to gson
        boolean hasEntityType = this.hasEntityType();
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        
        List<Object> values = new ArrayList<Object>();
        Iterator<Object> it = ((Collection)value).iterator();
        while(it.hasNext()){
            if(hasEntityType){
                values.add(buildEntity(it.next(), this.entityClass));
            } else {
                values.add(value0(it.next()));
            }
        }
        sb.append(Joiner.on(",").join(values));
        sb.append("]");
        return sb.toString();
    }

    //json value for array represent
    private Object _array(Object value) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        int length = Array.getLength(value);
        List<Object> values = new ArrayList<Object>();
        for (int i = 0; i < length; i ++) {
            Object arrayElement = Array.get(value, i);
            if(hasEntityType()){
                values.add(buildEntity(arrayElement, this.entityClass));
            } else {
                values.add(value0(arrayElement));
            }
        }
        sb.append(Joiner.on(",").join(values));
        sb.append("]");
        return sb.toString();
    }
    
    @SuppressWarnings({"unchecked" })
    private Object buildEntity(Object value, Class<? extends SrapeEntity> clz) {
        try {
            SrapeEntity entity = clz.newInstance();
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
