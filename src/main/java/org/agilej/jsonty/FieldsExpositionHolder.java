package org.agilej.jsonty;

import me.donnior.fava.FList;
import me.donnior.fava.Function;
import me.donnior.fava.Predicate;
import me.donnior.fava.util.FLists;
import org.agilej.jsonty.util.StringUtil;

public class FieldsExpositionHolder implements FieldExposer{

    private FList<FieldBuilder> fieldsDefinition = FLists.newEmptyList();
    
    public ScopedFieldBuilder expose(Object value){
        FieldBuilder fieldBuilder = new FieldBuilder(value);
        this.fieldsDefinition.add(fieldBuilder);
        return fieldBuilder;
    }
    
    public int fieldsCount(){
        return fieldsExposeDefinition().size();
    }
    
    public FList<FieldBuilder> fieldsExposeDefinition(){
        return this.fieldsDefinition;
    }

    /**
     * build final json result 
     */
    public String build() {
        final StringBuilder sb = new StringBuilder();
        
        boolean isAPureArrayDefinition = isAPureArrayDefinition();
        boolean isAPureEntityDefinition = isAPureEntityDefinition();
        if((!isAPureArrayDefinition) && (!isAPureEntityDefinition)){
            sb.append(JSONS.OBJECT_START);
        }
        
        FList<FieldBuilder> fieldBuildersNeedExpose = this.fieldsExposeDefinition().select(new Predicate<FieldBuilder>() {
            @Override
            public boolean apply(FieldBuilder fieldBuilder) {
                return fieldBuilder.conditionMatched();
            }
        });

        FList<String> fieldStrings = fieldBuildersNeedExpose.map(new Function<FieldBuilder, String>() {
            @Override
            public String apply(FieldBuilder fieldBuilder) {
                return fieldBuilder.toJson();
            }
        });
        
        sb.append(StringUtil.join(fieldStrings, JSONS.FIELD_SEPARATOR));

        if((!isAPureArrayDefinition) && (!isAPureEntityDefinition)){
            sb.append(JSONS.OBJECT_END);
        }
        return sb.toString();
    }
    
    public boolean isAPureArrayDefinition() {
        return this.fieldsExposeDefinition().size() == 1 &&
                this.fieldsExposeDefinition().at(0).isPureIterableValue();
    }

    public boolean isAPureEntityDefinition() {
        return this.fieldsExposeDefinition().size() == 1 &&
                (!this.fieldsExposeDefinition().at(0).hasName()) && this.fieldsExposeDefinition().at(0).hasEntityType();
    }

}
