package org.agilej.jsonty.util;

import java.io.IOException;
import java.io.Writer;

/**
 *  <p>
 *  One {@link StringBuilder} based {@link Writer} implementation.
 *
 *  <p>
 *  Closing a <tt>StringBuilderWriter</tt> has no effect. The methods in this class
 *  can be called after the stream has been closed without generating an
 *  <tt>IOException</tt>.
 *
 *  <p>
 *  JDK's built-in {@link java.io.StringWriter} is based on {@link StringBuffer}
 *  

 */
public class StringBuilderWriter extends Writer {

    private StringBuilder buf;

    public StringBuilderWriter() {
        buf = new StringBuilder();
    }

    public StringBuilderWriter(int initialSize) {
        if (initialSize < 0) {
            throw new IllegalArgumentException("Negative  size");
        }
        buf = new StringBuilder(initialSize);
    }

    public void write(int c) {
        buf.append((char) c);
    }

    public void write(char cbuf[], int off, int len) {
        if ((off < 0) || (off > cbuf.length) || (len < 0) ||
                ((off + len) > cbuf.length) || ((off + len) < 0)) {
            throw new IndexOutOfBoundsException();
        } else if (len == 0) {
            return;
        }
        buf.append(cbuf, off, len);
    }

    public void write(String str) {
        buf.append(str);
    }


    public void write(String str, int off, int len)  {
        buf.append(str.substring(off, off + len));
    }


    public StringBuilderWriter append(CharSequence csq) {
        if (csq == null)
            write("null");
        else
            write(csq.toString());
        return this;
    }

    public StringBuilderWriter append(CharSequence csq, int start, int end) {
        CharSequence cs = (csq == null ? "null" : csq);
        write(cs.subSequence(start, end).toString());
        return this;
    }

    public StringBuilderWriter append(char c) {
        write(c);
        return this;
    }


    public String toString() {
        return buf.toString();
    }

    public StringBuilder getBuffer() {
        return buf;
    }

    public void flush() {
    }

    public void close() throws IOException {
    }

}
