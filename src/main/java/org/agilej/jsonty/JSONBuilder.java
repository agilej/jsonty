package org.agilej.jsonty;

import me.donnior.fava.FList;

import java.io.Writer;

/**
 * 
 * Build JSON with one {@link JSONModel}, this class is not thread safe!
 */
public class JSONBuilder {

    private FieldsExpositionHolder jsonDefinition = new FieldsExpositionHolder();
    
    public JSONBuilder(JSONModel model) {
        model.config(this.jsonDefinition);
    }

    /**
     * build final json result
     * 
     */
    public String build() {
        return this.jsonDefinition.build();
    }

    /**
     *
     * build json string and stream to the given writer
     *
     */
    public void build(Writer writer){
        this.jsonDefinition.build(writer);
    }

    /**
     * whether this json model is a pure array data
     * 
     */
    public boolean isAPureArrayDefinition() {
        return this.jsonDefinition.isAPureArrayDefinition();
    }
    
    public int fieldsExposeDefinitionCount(){
        return this.jsonDefinition.fieldsExposeDefinition().size();
    }

    public FList<FieldBuilder> getFieldsExposeDefinition() {
        return this.jsonDefinition.fieldsExposeDefinition();
    }
}
