package org.lioxa.ustc.suckserver.template;

/**
 * The {@link TemplateException} is thrown when parsing the XML formatted
 * template. <br/>
 * This may be caused by XML syntax errors.
 * 
 * @author xi
 * @since Jan 5, 2016
 */
public class TemplateException extends Exception {

    private static final long serialVersionUID = 1563410317094689945L;

    public TemplateException(String msg) {
        super(msg);
    }

    public TemplateException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
