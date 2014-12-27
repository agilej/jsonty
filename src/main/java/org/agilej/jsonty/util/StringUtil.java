package org.agilej.jsonty.util;

import java.util.Iterator;

public class StringUtil {

    /**
     *
     * generate a json name and value pair string, if the treatValueAsString is set to true,
     * the value will be quoted.
     *
     * <br /> <br />
     *
     * <pre><code> "name": value </code></pre>
     * <br />
     * or
     * <br />
     * <pre><code> "name": "value" </code></pre>
     *
     * @param name
     * @param value
     * @param treatValueAsString
     * @return
     */
    public static String jsonPair(String name, Object value, boolean treatValueAsString){
        StringBuilder result = new StringBuilder();
        result.append(JSONStringFormatter.quoteWithEscape(name));
        result.append(":");
        result.append(treatValueAsString ? JSONStringFormatter.quoteWithEscape(value.toString()) : value.toString());
        return result.toString();
    }

    /**
     *
     * join the iterable objects into one string using the given separator
     *
     * @param iterable
     * @param separator
     * @return
     */
    public static String join(Iterable iterable, String separator){
        StringBuilder sb = new StringBuilder();
        Iterator it = iterable.iterator();
        if (it.hasNext()) {
            sb.append(it.next().toString());
            while (it.hasNext()) {
                sb.append(separator);
                sb.append(it.next().toString());
            }
        }
        return sb.toString();
    }


}
