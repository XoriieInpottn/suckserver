package org.lioxa.ustc.suckserver;

import java.io.File;
import java.util.Map.Entry;

import org.lioxa.ustc.suckserver.template.TemplateException;
import org.lioxa.ustc.suckserver.template.TemplateNode;
import org.lioxa.ustc.suckserver.template.TemplateReader;

/**
 *
 * @author xi
 * @since Jan 5, 2016
 */
public class TestTemplate {

    public static void main(String[] args) throws TemplateException {
        TemplateReader reader = new TemplateReader();
        TemplateNode tpl = reader.read(new File("task.xml"));
        printDOM(tpl, 0);
        System.out.println("complete");
    }

    static void printDOM(TemplateNode node, int lvl) {
        for (int i = 0; i < lvl; i++) {
            System.out.print('\t');
        }
        int lineNum = node.getLineNumber();
        int colNum = node.getColumnNumber();
        System.out.printf("(%d, %d) ", lineNum, colNum);
        System.out.print("[" + node.getName() + "]");
        System.out.print(' ');
        for (Entry<String, String> entry : node.getParams().entrySet()) {
            System.out.print(entry.getKey());
            System.out.print(':');
            System.out.print(entry.getValue());
            System.out.print(' ');
        }
        System.out.print('\n');
        for (TemplateNode childNode : node.getChildren()) {
            printDOM(childNode, lvl + 1);
        }
    }
}
