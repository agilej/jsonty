package org.agilej.jsonty.support;

import org.agilej.jsonty.JSONModel;

import java.util.Collection;
import java.util.Map;

/**
 * utility class for building {@link JSONModel}
 */
public class JSONModels {

    /**
     * build {@link JSONModel} from map, will generate response json as
     * <pre><code>
     *  {
     *     xx:xx,
     *     xx:xx
     *  }
     * </code></pre>
     * @param map
     * @return
     */
    public JSONModel fromMap(final Map map){
        return new AbstractJSONModel() {
            @Override
            public void config() {
                expose(map);
            }
        };
    }

    /**
     * build {@link JSONModel} from map with name, will generate response json as
     * <pre><code>
     *  {
     *    name: {
     *       xx:xx,
     *       xx:xx
     *    }
     *  }
     * </code></pre>
     * @param map
     * @return
     */
    public JSONModel fromMapWithName(final String name, final Map map){
        return new AbstractJSONModel() {
            @Override
            public void config() {
                expose(map).withName(name);
            }
        };
    }

    /**
     *
     * build {@link JSONModel} from array, will generate response json as
     * <pre><code>
     *  [
     *      {obj1},
     *      {obj2},
     *      ...
     *  ]
     * </code></pre>
     *
     * @param array
     * @param <T>
     * @return
     */
    public <T> JSONModel fromArray(final T[] array){
        return new AbstractJSONModel() {
            @Override
            public void config() {
                expose(array);
            }
        };
    }

    /**
     * /**
     *
     * build {@link JSONModel} from collection, will generate response json as
     * <pre><code>
     *  [
     *      {obj1},
     *      {obj2},
     *      ...
     *  ]
     * </code></pre>
     *
     * @param collection
     * @param <T>
     * @return
     */
    public <T> JSONModel fromCollection(final Collection<T> collection){
        return new AbstractJSONModel() {
            @Override
            public void config() {
                expose(collection);
            }
        };
    }

}
