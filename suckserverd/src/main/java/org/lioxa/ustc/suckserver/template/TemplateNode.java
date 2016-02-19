package org.lioxa.ustc.suckserver.template;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The {@link TemplateNode}. <br/>
 * Each node represent a crawler action (routine).<br/>
 * It also contains location information of the node in the XML source code.
 *
 * @author xi
 * @since Jan 15, 2016
 */
public class TemplateNode {

    //
    // basic

    String name;
    int lineNumber;
    int columnNumber;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLineNumber() {
        return this.lineNumber;
    }

    public void setLineNumber(int lineNum) {
        this.lineNumber = lineNum;
    }

    public int getColumnNumber() {
        return this.columnNumber;
    }

    public void setColumnNumber(int colNum) {
        this.columnNumber = colNum;
    }

    //
    // params

    Map<String, String> params = new HashMap<>();

    /**
     * Get the parameter map.
     *
     * @return The parameters map.
     */
    public Map<String, String> getParams() {
        return this.params;
    }

    //
    // children

    List<TemplateNode> children = new ArrayList<>();

    /**
     * Get the child nodes.
     *
     * @return The child node list.
     */
    public List<TemplateNode> getChildren() {
        return this.children;
    }

}
