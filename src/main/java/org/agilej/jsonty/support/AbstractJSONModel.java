package org.agilej.jsonty.support;

import org.agilej.jsonty.FieldExposer;
import org.agilej.jsonty.JSONModel;
import org.agilej.jsonty.CustomizedFieldBuilder;

/**
 * 
 * AbstractJSONModel which can be used for convenience.
 * 
 * <pre>
 * JSONModel model = new AbstractJSONMoel(){
 *     void config(){
 *       expose(1).withName("int");
 *       expose("one").withName("string"); 
 *     }
 * }
 * </pre>
 */
public abstract class AbstractJSONModel implements JSONModel{

    private FieldExposer exposer;
    
    public abstract void config();
    
    @Override
    public void config(FieldExposer exposer) {
        this.exposer = exposer;
        try {
            this.config();
        } finally {
            this.exposer = null;
        }
        
    }

    protected CustomizedFieldBuilder expose(Object field){
        return this.exposer.expose(field);
    }
    
}
