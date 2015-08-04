package org.agilej.jsonty;

import java.io.Writer;

/**
 * 
 * Build JSON with one {@link JSONModel}, this class is not thread safe!
 */
public class JSONBuilder {

    private FieldsContainer fieldsContainer = new FieldsContainer();
    
    public JSONBuilder(JSONModel model) {
        model.config(this.fieldsContainer);
    }

    /**
     * build final json result
     * 
     */
    public String build() {
        return this.fieldsContainer.build();
    }

    /**
     *
     * build json string and stream to the given writer
     *
     */
    public void build(Writer writer){
        this.fieldsContainer.build(writer);
    }

    /**
     * whether this json model is a pure array data
     * 

    public boolean hasOnlyOneIterableValueWithoutName() {
        return this.fieldsExpositionHolder.hasOnlyOneIterableValueWithoutName();
    }
    
    public int fieldsCount(){
        return this.fieldsExpositionHolder.fieldsCount();
    }

    public List<FieldBuilder> exposedFields() {
        return this.fieldsExpositionHolder.exposedFields();
    }
     */
}
