package org.agilej.jsonty;

import me.donnior.fava.FList;
import me.donnior.fava.Function;
import me.donnior.fava.Predicate;
import me.donnior.fava.util.FLists;
import org.agilej.jsonty.util.StringUtil;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

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

    public String build() {
        StringWriter sw = new StringWriter();
        this.build(sw);
        return sw.toString();
    }

    /**
     * build final json result 
     */
    public String build(Writer writer) {

        boolean isAPureArrayDefinition = isAPureArrayDefinition();
        boolean isAPureEntityDefinition = isAPureObjectDefinition();
        if((!isAPureArrayDefinition) && (!isAPureEntityDefinition)){
            write(writer, JSONS.OBJECT_START);
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
        
        write(writer, StringUtil.join(fieldStrings, JSONS.FIELD_SEPARATOR));

        if((!isAPureArrayDefinition) && (!isAPureEntityDefinition)){
            write(writer, JSONS.OBJECT_END);
        }
        return writer.toString();
    }
    
    public boolean isAPureArrayDefinition() {
        return this.fieldsExposeDefinition().size() == 1 &&
                this.fieldsExposeDefinition().at(0).isPureIterableValue();
    }

    public boolean isAPureObjectDefinition() {
        return this.fieldsExposeDefinition().size() == 1
                && (!this.fieldsExposeDefinition().at(0).hasName())
                && (this.fieldsExposeDefinition().at(0).hasEntityType()
                    || this.fieldsExposeDefinition().at(0).isMapValue());
    }

    private void write(Writer writer, String value){
        try {
            writer.append(value);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
