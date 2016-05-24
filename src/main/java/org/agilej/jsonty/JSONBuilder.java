package org.agilej.jsonty;

import org.agilej.jsonty.util.StringBuilderWriter;

import java.io.Writer;

/**
 * 
 * Build json with one {@link JSONModel}, this class is thread safe!
 */
public class JSONBuilder {

//    private FieldsContainer fieldsContainer = new FieldsContainer();
//
//
//    public JSONBuilder(JSONModel model) {
//        model.config(this.fieldsContainer);
//    }

    public JSONBuilder(){

    }

    /**
     * build final json result
     * 
     */
    public String build(JSONModel model){
        StringBuilderWriter sbw = new StringBuilderWriter();
        this.build(model, sbw);
        return sbw.toString();
    }

    /**
     *
     * build json string and stream to the given writer
     *
     */
    public void build(JSONModel model, Writer writer) {
        FieldsContainer fieldsContainer = new FieldsContainer();
        model.config(fieldsContainer);
        fieldsContainer.build(writer);
    }

}
