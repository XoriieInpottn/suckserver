package org.lioxa.ustc.suckserver.routine.crawler;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Field;
import java.util.Collection;

import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.lioxa.ustc.suckserver.routine.Param;
import org.lioxa.ustc.suckserver.utils.Reflects;
import org.openqa.selenium.io.IOUtils;

/**
 *
 * @author xi
 * @since May 26, 2016
 */
public class RoutineInfoDumper {

    public static final String CRAWLER_ROUTINE_PACKAGE = "org.lioxa.ustc.suckserver.routine.crawler.impl";
    public static final String DUMP_FILENAME = System.getProperty("java.io.tmpdir") + File.separator
            + "suckservers-routines.json";

    public void dump() throws IOException {
        JSONArray routines = new JSONArray();
        Collection<Class<?>> routineTypes = Reflects.getClasses(CRAWLER_ROUTINE_PACKAGE, false);
        for (Class<?> clazz : routineTypes) {
            if (!Reflects.hasSuper(clazz, CrawlerRoutine.class)) {
                continue;
            }
            JSONObject routine = new JSONObject();
            routines.put(routine);
            routine.put("name", clazz.getSimpleName());
            JSONArray params = new JSONArray();
            routine.put("params", params);
            for (Field field : clazz.getDeclaredFields()) {
                Param annParam = field.getAnnotation(Param.class);
                if (annParam == null) {
                    continue;
                }
                JSONObject param = new JSONObject();
                params.put(param);
                param.put("name", field.getName());
                param.put("type", field.getType().getSimpleName());
                param.put("essential", annParam.essential());
                param.put("tips", annParam.tips());
            }
        }
        Writer writer = null;
        try {
            writer = new FileWriter(DUMP_FILENAME);
            routines.write(writer);
        } catch (IOException e) {
            throw e;
        } finally {
            if (writer != null) {
                IOUtils.closeQuietly(writer);
            }
        }
    }

    public void clean() {
        FileUtils.deleteQuietly(new File(DUMP_FILENAME));
    }

    public static void main(String[] args) throws IOException {
        new RoutineInfoDumper().dump();
        System.out.println("complete");
    }

}
