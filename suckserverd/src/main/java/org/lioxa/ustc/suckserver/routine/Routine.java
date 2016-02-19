package org.lioxa.ustc.suckserver.routine;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.lioxa.ustc.suckserver.utils.Utils;

/**
 *
 * @author xi
 * @since Nov 16, 2015
 */
public abstract class Routine<T extends Context> {

    //
    // context

    protected T globalContext;
    protected Map<String, Object> localContext = new HashMap<>();

    public T getGlobalContext() {
        return this.globalContext;
    }

    public Map<String, Object> getLocalContext() {
        return this.localContext;
    }

    public Map<String, Object> getMasterContext() {
        return this.masterRoutine.localContext;
    }

    //
    // exception handler

    List<ExceptionHandler> handlers = new LinkedList<>();

    public List<ExceptionHandler> getExceptionHandlers() {
        return this.handlers;
    }

    //
    // sub routines

    protected Routine<T> masterRoutine;
    protected List<Routine<T>> subRoutines;

    public Routine<T> getMasterRoutine() {
        return this.masterRoutine;
    }

    public int getSubRoutineSize() {
        return this.subRoutines.size();
    }

    public Routine<T> getSubRoutine(int index) {
        return this.subRoutines.get(index);
    }

    protected void executeSubRoutines() throws ParameterException, ExecutionException {
        for (Routine<T> routine : this.subRoutines) {
            routine.execute();
        }
    }

    //
    // initialize

    Map<String, String> params;

    public void init(T context, Map<String, String> params, List<Routine<T>> subRoutines) {
        assert (context != null);
        this.globalContext = context;
        if (params != null) {
            this.params = params;
        } else {
            this.params = new HashMap<>();
        }
        if (subRoutines != null) {
            this.subRoutines = new ArrayList<>(subRoutines.size());
            for (Routine<T> routine : subRoutines) {
                routine.masterRoutine = this;
                this.subRoutines.add(routine);
            }
        } else {
            this.subRoutines = Collections.emptyList();
        }
    }

    //
    // execution

    public void execute() throws ParameterException, ExecutionException {
        try {
            this.setParams(this.params);
            this.exec();
        } catch (ParameterException | ExecutionException | RuntimeException e) {
            if (e instanceof HandledException) {
                throw e;
            }
            boolean isFatal = false;
            for (ExceptionHandler handler : this.handlers) {
                isFatal = isFatal || handler.handleException(this.globalContext, this, e);
            }
            if (isFatal) {
                throw new HandledException(e.getMessage(), e);
            }
        }
    }

    void setParams(Map<String, String> params) throws ParameterException {
        for (Field field : this.getClass().getDeclaredFields()) {
            Param param = field.getAnnotation(Param.class);
            if (param == null) {
                continue;
            }
            String name = param.name();
            String value = params.get(name);
            if (value == null) {
                if (param.essential()) {
                    String msg = String.format("Parameter \"%s\" must be set.", name);
                    throw new ParameterException(msg);
                } else {
                    continue;
                }
            }
            value = this.insertVar(value);
            field.setAccessible(true);
            try {
                field.set(this, Utils.toObject(field.getType(), value));
            } catch (Exception e) {
                String msg = String.format("Failed to set parameter \"%s\" with value \"%s\".", name, value.toString());
                throw new ParameterException(msg, e);
            }
        }
    }

    String insertVar(String value) throws ParameterException {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < value.length(); i++) {
            char ch = value.charAt(i);
            if (ch == '\\') {
                //
                // escape
                i++;
                if (i < value.length() && value.charAt(i) == '$') {
                    sb.append('$');
                } else {
                    i--;
                    sb.append(ch);
                }
            } else if (ch == '$') {
                //
                // insert variable value
                int j;
                for (j = i + 1; j < value.length(); j++) {
                    char ch1 = value.charAt(j);
                    if (!(Character.isLetter(ch1) || Character.isDigit(ch1) || ch1 == '_')) {
                        break;
                    }
                }
                String name = value.substring(i + 1, j);
                Object var = this.globalContext.getVars().get(name);
                if (var == null) {
                    String msg = String.format("No such variable defined as \"%s\"", name);
                    throw new ParameterException(msg);
                }
                sb.append(var.toString());
                i = j;
            } else {
                //
                // normal string element
                sb.append(ch);
            }
        }
        return sb.toString();
    }

    protected abstract void exec() throws ParameterException, ExecutionException;

}
