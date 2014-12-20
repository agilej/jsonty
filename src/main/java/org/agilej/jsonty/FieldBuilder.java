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
    }

    /**
     *
     * According to the json specification, json data can be start with "{", or just "["  , the second form is
     * called pure-iterable value in jsonty
     *
     * <br />
     * <br />
     *
     * one field is a pure iterable value when it's value is iterable and don't have a
     * explicit name. You can't use {@link #withName(String)} or {@link #withNameAndType(String, Class)}
     * to define a field exposition if you want to make it pure-iterable, you can use {@link #withType(Class)}
     * or just ignore the 'with' clause.
     * 
     * <br />
     * <br />
     *
     * If this field exposition is pure iterable, it would be output as <pre><code>[1,2,3]</code></pre>
     * Otherwise it will be output as <pre><code>{"name": xxxx}</code></pre>
     * 
     * @return
     */
    public boolean isPureIterableValue(){
        return this.isValueIterable() && !hasName();
    }
    
}
