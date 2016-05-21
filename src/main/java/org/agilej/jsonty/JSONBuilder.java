package org.agilej.jsonty;

import java.io.Writer;

/**
 * 
 * Build json with one {@link JSONModel}, this class is not thread safe!
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

}
