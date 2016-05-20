package org.lioxa.ustc.suckserver;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.lioxa.ustc.suckserver.routine.crawler.Browser;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 *
 * @author xi
 * @since May 20, 2016
 */
public class TestBrowser {

    public static void main(String[] args) throws InterruptedException {
        Browser b = new Browser();
        b.get("http://www.guokr.com/article/441446/");
        forward(b.getFirefoxDriver());
        WebElement e;
        e = b.select(".related-article a").get(0);
        b.click(e, 2000);
        forward(b.getFirefoxDriver());
        e = b.select(".related-article a").get(0);
        b.click(e, 2000);
        forward(b.getFirefoxDriver());
        e = b.select(".related-article a").get(0);
        b.click(e, 2000);
        forward(b.getFirefoxDriver());
        e = b.select(".related-article a").get(0);
        b.click(e, 2000);
        forward(b.getFirefoxDriver());
        Thread.sleep(3000);
        b.quit();
        System.out.println("complete");
    }

    static List<String> handlerStack = new LinkedList<>();

    static void forward(WebDriver driver) {
        Set<String> handlers = driver.getWindowHandles();
        String handler = diffSet(handlerStack, handlers);
        System.out.println(handler);
        System.out.println(handlers);
        driver.switchTo().window(handler);
        handlerStack.add(0, handler);
    }

    static String diffSet(Collection<String> coll0, Collection<String> coll1) {
        for (String elem : coll1) {
            if (!coll0.contains(elem)) {
                return elem;
            }
        }
        return null;
    }

}
