package org.agilej.jsonty.test;

import org.agilej.jsonty.util.JSONStringFormatter;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class JSONStringFormatterTest {

    @Test
    public void test_quote_with_escape(){

        Assert.assertEquals(quote(""), JSONStringFormatter.quoteWithEscape(null));

        assertEquals(quote(""), JSONStringFormatter.quoteWithEscape(""));

        assertEquals(quote("a"), JSONStringFormatter.quoteWithEscape("a"));

        assertEquals(quote("ab"), JSONStringFormatter.quoteWithEscape("ab"));

        assertEquals(quote("\\\""), JSONStringFormatter.quoteWithEscape("\""));

        assertEquals(quote("\\\\"), JSONStringFormatter.quoteWithEscape("\\"));

        assertEquals(quote("\\/"), JSONStringFormatter.quoteWithEscape("/"));

        assertEquals(quote("\\\\"), JSONStringFormatter.quoteWithEscape("\\"));

        assertEquals(quote("\\b"), JSONStringFormatter.quoteWithEscape("\b"));

        assertEquals(quote("\\t"), JSONStringFormatter.quoteWithEscape("\t"));

        assertEquals(quote("\\n"), JSONStringFormatter.quoteWithEscape("\n"));

        assertEquals(quote("\\f"), JSONStringFormatter.quoteWithEscape("\f"));

        assertEquals(quote("\\r"), JSONStringFormatter.quoteWithEscape("\r"));

        assertEquals(quote(" "), JSONStringFormatter.quoteWithEscape(" "));

        String s = new String(new char[]{1});
        assertEquals(quote("\\u0001"), JSONStringFormatter.quoteWithEscape(s));
    }

    private String quote(String s){
        return "\"" + s + "\"";
    }


}