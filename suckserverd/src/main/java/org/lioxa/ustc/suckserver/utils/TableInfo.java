package org.lioxa.ustc.suckserver.utils;

import java.util.HashMap;

/**
 * The {@link TableInfo} object is used to store table information when creating
 * tables. <br/>
 * This object is used to generate SQL statement when saving a data record then.<br/>
 * TableInfo mainly include:
 * <ul>
 * <li>table name</li>
 * <li>table columns</li>
 * </ul>
 *
 * @author xi
 * @since Nov 24, 2015
 */
public class TableInfo extends HashMap<String, String> {

    private static final long serialVersionUID = 6864155426141964578L;

    String name;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
