package org.agilej.jsonty;

import me.donnior.fava.FList;
import me.donnior.fava.Function;
import me.donnior.fava.Predicate;
import me.donnior.fava.util.FLists;

import com.google.common.base.Joiner;

public class FieldsExpositionHolder implements FieldExposer{

    private FList<FieldBuilderImpl> fieldsDefinition = FLists.newEmptyList();
    
    public ScopedFieldBuilder expose(Object value){
        FieldBuilderImpl fieldBuilder = new FieldBuilderImpl(value);
        this.fieldsDefinition.add(fieldBuilder);
        return fieldBuilder;
    }
    
    public int fieldsCount(){
        return fieldsExposeDefinition().size();
    }
    
    public FList<FieldBuilderImpl> fieldsExposeDefinition(){
        return this.fieldsDefinition;
    }

    /**
     * build final json result 
     */
    public String build() {
        final StringBuilder sb = new StringBuilder();
        
        boolean isAPureArrayDefinition = isAPureArrayDefinition();
        if(!isAPureArrayDefinition){
            sb.append("{");
        }
        
        FList<FieldBuilderImpl> fieldBuildersNeedExpose = this.fieldsExposeDefinition().select(new Predicate<FieldBuilderImpl>() {
            @Override
            public boolean apply(FieldBuilderImpl fieldBuilder) {
                return fieldBuilder.conditionMatched();
            }
        });

        FList<String> fieldStrings = fieldBuildersNeedExpose.map(new Function<FieldBuilderImpl, String>() {
            @Override
            public String apply(FieldBuilderImpl fieldBuilder) {
                return fieldBuilder.toJson();
            }
        });
        
        sb.append(Joiner.on(",").join(fieldStrings));
        
        if(!isAPureArrayDefinition){
            sb.append("}");
        }
        return sb.toString();
    }
    
    public boolean isAPureArrayDefinition() {
        return this.fieldsExposeDefinition().size() == 1 &&
                this.fieldsExposeDefinition().at(0).isPureIterableValue();
    }
   
}
