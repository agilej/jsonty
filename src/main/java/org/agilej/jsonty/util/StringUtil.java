package org.agilej.jsonty.util;

import java.util.Iterator;

public class StringUtil {

    /**
     * quote the given string with escape if needed
     *
     * @param string
     * @return
     */
    public static String quote(String string) {
        if (string == null || string.length() == 0) {
            return "\"\"";
        }

        char         c = 0;
        int          i;
        int          len = string.length();
        StringBuilder sb = new StringBuilder(len + 4);
        String       t;

        sb.append('"');
        for (i = 0; i < len; i += 1) {
            c = string.charAt(i);
            switch (c) {
            case '\\':
            case '"':
                sb.append('\\');
                sb.append(c);
                break;
            case '/':
//                if (b == '<') {
                    sb.append('\\');
//                }
                sb.append(c);
                break;
            case '\b':
                sb.append("\\b");
                break;
            case '\t':
                sb.append("\\t");
                break;
            case '\n':
                sb.append("\\n");
                break;
            case '\f':
                sb.append("\\f");
                break;
            case '\r':
               sb.append("\\r");
               break;
            default:
                if (c < ' ') {
                    t = "000" + Integer.toHexString(c);
                    sb.append("\\u" + t.substring(t.length() - 4));
                } else {
                    sb.append(c);
                }
            }
        }
        sb.append('"');
        return sb.toString();
    }

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
        result.append(quote(name));
        result.append(":");
        result.append(treatValueAsString ? quote(value.toString()) : value.toString());
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
