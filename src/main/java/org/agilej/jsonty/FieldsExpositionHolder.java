package org.agilej.jsonty;


import org.agilej.jsonty.util.StringBuilderWriter;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FieldsExpositionHolder implements FieldExposer{

    private List<FieldBuilder> fieldBuilders = new ArrayList<FieldBuilder>();
    
    public ScopedFieldBuilder expose(Object value){
        FieldBuilder fieldBuilder = new FieldBuilder(value);
        this.fieldBuilders.add(fieldBuilder);
        return fieldBuilder;
    }
    
    public int fieldsCount(){
        return exposedFields().size();
    }
    
    public List<FieldBuilder> exposedFields(){
        return this.fieldBuilders;
    }

    /**
     * build final json string
     * @return
     */
    public String build() {
        StringBuilderWriter sw = new StringBuilderWriter();
        this.build(sw);
        return sw.toString();
    }

    /**
     * build final json result and write to given writer
     */
    public String build(Writer writer) {
        boolean needOuterObjectWrapper = needOuterObjectWrap();

        if(needOuterObjectWrapper){
            write(writer, JSONS.OBJECT_START);
        }
        
        List<FieldBuilder> fieldBuildersNeedExpose = this.filterConditionMatched(exposedFields());

        Iterator<FieldBuilder> it = fieldBuildersNeedExpose.iterator();
        if (it.hasNext()){
            FieldBuilder firstFieldBuilder = it.next();
            write(writer, firstFieldBuilder.toJson());
        }
        while (it.hasNext()){
            FieldBuilder fieldBuilder = it.next();
            write(writer, JSONS.FIELD_SEPARATOR);
            write(writer, fieldBuilder.toJson());
        }

        if(needOuterObjectWrapper){
            write(writer, JSONS.OBJECT_END);
        }

        return writer.toString();
    }

    private boolean needOuterObjectWrap(){
        return (!hasOnlyOneIterableValueWithoutName()) && (!hasOnlyOneObjectValueWithoutName());
    }

    /**
     * whether this fields container has only one iterable value without name.
     * If so the json output will be directly `[xx,xx]`
     */
    public boolean hasOnlyOneIterableValueWithoutName() {
        return this.exposedFields().size() == 1 &&
                this.exposedFields().get(0).isPureIterableValue();
    }

    /**
     * whether this fields container has only one object-style value (entity value or map value).
     * If so the json output will be a single object map `{}` without the out wrapper `{}`
     *
     */
    public boolean hasOnlyOneObjectValueWithoutName() {
        return this.exposedFields().size() == 1
                && (!this.exposedFields().get(0).hasName())
                && (this.exposedFields().get(0).hasEntityType()
                    || this.exposedFields().get(0).isMapValue());
    }

    private List<FieldBuilder> filterConditionMatched(List<FieldBuilder> list){
        List<FieldBuilder> result = new ArrayList<FieldBuilder>(list.size());
        for (FieldBuilder builder : list){
            if (builder.conditionMatched()){
                result.add(builder);
            }
        }
        return result;
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
