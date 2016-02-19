package org.lioxa.ustc.suckserver.routine.crawler.impl;

import org.lioxa.ustc.suckserver.routine.ExecutionException;
import org.lioxa.ustc.suckserver.routine.Param;
import org.lioxa.ustc.suckserver.routine.ParameterException;
import org.lioxa.ustc.suckserver.routine.crawler.CrawlerRoutine;
import org.lioxa.ustc.suckserver.utils.TableInfo;

/**
 *
 * @author xi
 * @since Nov 19, 2015
 */
public class Column extends CrawlerRoutine {

    @Param(name = "name", essential = true)
    String name;

    @Param(name = "type")
    String type;

    //
    // execution

    @Override
    public void exec() throws ParameterException, ExecutionException {
        if (this.globalContext.isStopReq()) {
            return;
        }
        //
        // check parameters
        if (this.name == null || (this.name = this.name.trim()).length() == 0) {
            throw new ParameterException("Parameter name should not be empty.");
        }
        if (!this.name.matches("^[a-zA-Z]\\w*$")) {
            String err = String.format("Invalid parameter \"%s\" for name.", this.name);
            throw new ParameterException(err);
        }
        if (this.type == null) {
            this.type = "TEXT";
        } else {
            this.type = this.type.toLowerCase();
            switch (this.type) {
            case "string":
            case "text":
                this.type = "TEXT";
                break;
            case "int":
            case "integer":
                this.type = "INT";
                break;
            case "float":
                this.type = "FLOAT";
                break;
            case "double":
                this.type = "DOUBLE";
                break;
            case "bool":
            case "boolean":
                this.type = "BOOL";
                break;
            default:
                String err = String.format("Invalid type \"%s\" for \"%s\"", this.type, this.name);
                throw new ParameterException(err);
            }
        }
        //
        // table info
        TableInfo tblInfo = (TableInfo) this.getMasterContext().get("tblInfo");
        if (tblInfo == null) {
            String err = String.format("FATAL: No corresponding table for column \"%s\".", this.name);
            throw new ExecutionException(err);
        }
        if (tblInfo.containsKey(this.name)) {
            String err = String.format("FATAL: Duplicate column name \"%s\" for table \"%s\".", this.name,
                    tblInfo.getName());
            throw new ExecutionException(err);
        }
        tblInfo.put(this.name, this.type);
    }

}
