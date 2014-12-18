package io.github.agilej.jsonty;

import me.donnior.fava.FList;

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
     * whether this json model is a pure array data
     * 
     */
    public boolean isAPureArrayDefinition() {
        return this.jsonDefinition.isAPureArrayDefinition();
    }
    
    public int fieldsExposeDefinitionCount(){
        return this.jsonDefinition.fieldsExposeDefinition().size();
    }

    public FList<FieldBuilderImpl> getFieldsExposeDefinition() {
        return this.jsonDefinition.fieldsExposeDefinition();
    }
}
