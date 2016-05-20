package org.lioxa.ustc.suckserver.routine.crawler;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import org.apache.commons.codec.binary.Base64OutputStream;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;

public class Browser {

    static {
        System.setProperty("webdriver.firefox.bin", "/usr/bin/firefox");
    }

    private WebDriver firefoxDriver;

    public WebDriver getFirefoxDriver() {
        return this.firefoxDriver;
    }

    public void setFirefoxDriver(FirefoxDriver firefoxDriver) {
        this.firefoxDriver = firefoxDriver;
    }

    public Browser() {
        this.firefoxDriver = new FirefoxDriver();
        this.firefoxDriver.get("about:blank");
    }

    public Browser(String extensionPath) {
        File file = new File(extensionPath);
        FirefoxProfile profile = new FirefoxProfile();
        try {
            profile.addExtension(file);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            System.out.println("Add extension to profile wrongly!");
            e.printStackTrace();
        }
        this.firefoxDriver = new FirefoxDriver(profile);
        this.firefoxDriver.get("about:blank");
    }

    public Browser(FirefoxProfile profile) {
        this.firefoxDriver = new FirefoxDriver(profile);
        this.firefoxDriver.get("about:blank");
    }

    /**
     * set Filters which you do not want to show in browser such as css,jpg...
     *
     * @param suffix
     */
    public void setSuffixFilters(String[] suffix) {
        String script = null;
        script = "window.invoke(\"setSuffixFilters\", {fliters:[";
        for (String i : suffix) {
            script += "\"" + i + "\",";
        }
        script = script.substring(0, script.length() - 1);
        script += "]})";
        ((JavascriptExecutor) this.firefoxDriver).executeScript(script);
    }

    /**
     * change the User-Agent of this browser
     *
     * @param userAgent
     */
    public void setUserAgent(String userAgent) {
        String script = null;
        script = "window.invoke(\"setUserAgent\", {userAgent:";
        script += "\"" + userAgent + "\"";
        script += "})";
        ((JavascriptExecutor) this.firefoxDriver).executeScript(script);
    }

    /**
     * set proxy for the browser
     *
     * @param type
     * @param host
     * @param port
     */
    public void setProxy(String type, String host, int port) {
        String script = null;
        script = "window.invoke(\"setProxy\", {";
        script += "type:\"" + type + "\", ";
        script += "host:\"" + host + "\", ";
        script += "port:" + port + "})";
        ((JavascriptExecutor) this.firefoxDriver).executeScript(script);
    }

    public void setAcceptFilters(String[] accepts) {
        String script = null;
        script = "window.invoke(\"setAcceptFilters\", {accepts:[";
        for (String i : accepts) {
            script += "\"" + i + "\",";
        }
        script = script.substring(0, script.length() - 1);
        script += "]})";
        ((JavascriptExecutor) this.firefoxDriver).executeScript(script);
    }

    public void setHostFilters(String[] host) {
        String script = null;
        script = "window.invoke(\"setHostFilters\", {hosts:[";
        for (String i : host) {
            script += "\"" + i + "\",";
        }
        script = script.substring(0, script.length() - 1);
        script += "]})";
        ((JavascriptExecutor) this.firefoxDriver).executeScript(script);
    }

    public void deleteCookies() {
        String script = null;
        script = "window.invoke(\"deleteCookies\");";
        ((JavascriptExecutor) this.firefoxDriver).executeScript(script);
    }

    /**
     * to connect with a url
     *
     * @param url
     */
    public void get(String url) {
        this.firefoxDriver.navigate().to(url);
    }

    /**
     * connect to a page with a limited time.
     *
     * @param url
     * @param time
     * @throws TimeoutException
     */
    public void get(String url, long time) throws TimeoutException {
        this.firefoxDriver.manage().timeouts().pageLoadTimeout(time, TimeUnit.SECONDS);
        this.firefoxDriver.get(url);
    }

    /**
     * make the browser to go to the pre page
     */
    public void historyBack() {
        this.firefoxDriver.navigate().back();
    }

    /**
     * make the browser to go to the next page
     */
    public void historyForward() {
        this.firefoxDriver.navigate().forward();
    }

    /**
     * go to a pre window
     */
    public void windowBack() {
        this.firefoxDriver.close();
        LinkedHashSet<String> set = (LinkedHashSet<String>) this.firefoxDriver.getWindowHandles();
        Object handles[] = set.toArray();
        this.firefoxDriver.switchTo().window(handles[handles.length - 1].toString());
    }

    /**
     * go to the next window. Note: without closing current window
     */
    public void windowForward() {
        String crtHandler = this.firefoxDriver.getWindowHandle();
        LinkedHashSet<String> handlers = (LinkedHashSet<String>) this.firefoxDriver.getWindowHandles();
        Iterator<String> iter = handlers.iterator();
        while (iter.hasNext()) {
            String handler = iter.next();
            if (handler.equals(crtHandler)) {
                break;
            }
        }
        if (iter.hasNext()) {
            this.firefoxDriver.switchTo().window(iter.next());
        }
    }

    /**
     * go to the next window. Note: closing the current window at the same time
     */
    public void windowForwardWithoutBefore() {
        String crtHandler = this.firefoxDriver.getWindowHandle();
        LinkedHashSet<String> handlers = (LinkedHashSet<String>) this.firefoxDriver.getWindowHandles();
        Iterator<String> iter = handlers.iterator();
        while (iter.hasNext()) {
            String handler = iter.next();
            if (handler.equals(crtHandler)) {
                break;
            }
        }
        if (iter.hasNext()) {
            this.firefoxDriver.close();
            this.firefoxDriver.switchTo().window(iter.next());
        }
    }

    /**
     * @param by
     * @return a text of a special webElement.
     */
    public String getWebElementText(By by) {
        try {
            return this.firefoxDriver.findElement(by).getText();
        } catch (NoSuchElementException e) {
            return "No such Element!";
        }
    }

    /**
     * make the scroll down
     *
     * @param time
     *            is set to wait the new page loading
     */
    public void scrollDown(long time) {
        String js = "var q=document.documentElement.scrollTop=1000000";
        ((JavascriptExecutor) this.firefoxDriver).executeScript(js, 0);
        try {
            Thread.sleep(time * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * make the scroll top
     *
     * @param time
     */
    public void scrollTop(long time) {
        String js = "var q=document.documentElement.scrollTop=0";
        ((JavascriptExecutor) this.firefoxDriver).executeScript(js, 0);
        try {
            Thread.sleep(time * 1000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * close the current window.
     */
    public void close() {
        this.firefoxDriver.close();
    }

    /**
     * stop the browser.
     */
    public void quit() {
        this.firefoxDriver.quit();
    }

    /**
     * this is to used to click a element
     *
     * @param elem
     * @param time
     * @return
     */
    public boolean click(WebElement elem, int time) {
        try {
            elem.click();
            return true;
        } catch (Exception e) {
            if (e.getMessage().contains("Element is not clickable at point")) {
                int i;
                for (i = 0; i < time; i++) {
                    String js = "var scrollTop=document.documentElement.scrollTop;console.log(scrollTop);var q=document.documentElement.scrollTop=scrollTop+"
                            + 300 * (i + 1);
                    String js1 = "var scrollTop=document.documentElement.scrollTop;console.log(scrollTop);var q=document.documentElement.scrollTop=scrollTop-"
                            + 300 * (i + 1);
                    try {
                        if (i % 2 == 0) {
                            // this.scrollDown(0);
                            ((JavascriptExecutor) this.firefoxDriver).executeScript(js1, 0);
                        } else {
                            ((JavascriptExecutor) this.firefoxDriver).executeScript(js, 0);
                        }
                        Thread.sleep(time * 500);
                        elem.click();
                        break;
                    } catch (Exception ee) {
                        continue;
                    }
                }
                if (i < time) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * set the userAgent automatically according to the file
     *
     * @param path
     *            : the path of file which includes a lot of userAgent
     * @throws IOException
     */
    public void autoSetUserAgent(String path) throws IOException {
        File file = new File(path);
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String tmpString = null;
        List<String> userAgentList = new ArrayList<>();
        while ((tmpString = reader.readLine()) != null) {
            userAgentList.add(tmpString);
        }
        double rand = Math.random();
        int i = (int) (rand * (userAgentList.size() - 1));
        this.setUserAgent(userAgentList.get(i));
        reader.close();
    }

    /**
     * cut the whole page of a driver
     *
     * @return
     * @throws IOException
     */
    public byte[] takeScreenshot() throws IOException {
        TakesScreenshot takesScreenshot = (TakesScreenshot) this.firefoxDriver;
        return takesScreenshot.getScreenshotAs(OutputType.BYTES);
    }

    /**
     * cut the selected part of a page and save it.
     *
     * @param webElement
     * @param filePath
     * @throws IOException
     */
    public void createElementImage(WebElement webElement, String filePath) throws IOException {
        Point location = webElement.getLocation();
        Dimension size = webElement.getSize();
        BufferedImage originalImage = ImageIO.read(new ByteArrayInputStream(this.takeScreenshot()));
        BufferedImage croppedImage = originalImage.getSubimage(location.getX(), location.getY(), size.getWidth(),
                size.getHeight());
        File file = new File(filePath);
        ImageIO.write(croppedImage, "png", file);
    }

    public String createElementImage(WebElement webElement) throws IOException {
        String result;
        Point location = webElement.getLocation();
        Dimension size = webElement.getSize();
        BufferedImage originalImage = ImageIO.read(new ByteArrayInputStream(this.takeScreenshot()));
        BufferedImage croppedImage = originalImage.getSubimage(location.getX(), location.getY(), size.getWidth(),
                size.getHeight());
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        OutputStream b64 = new Base64OutputStream(os);
        ImageIO.write(croppedImage, "png", b64);
        result = os.toString("UTF-8");
        b64.close();
        os.close();
        return result;
    }

    public List<WebElement> select(String path) {
        try {
            List<WebElement> list = this.firefoxDriver.findElements(By.cssSelector(path));
            return list;
        } catch (Exception e) {
            return null;
        }
    }

    public List<WebElement> select(String path, long delay, int retry) {
        List<WebElement> list = null;
        for (int i = 0; i < retry + 1; i++) {
            list = this.firefoxDriver.findElements(By.cssSelector(path));
            if (list.size() > 0) {
                return list;
            }
            try {
                Thread.sleep(delay * 1000);
            } catch (InterruptedException e) {
            }
        }
        return list;
    }

    public List<WebElement> select(WebElement elem, String path, int retry, int delay) {
        List<WebElement> list = null;
        for (int i = 0; i < retry + 1; i++) {
            list = elem.findElements(By.cssSelector(path));
            if (list.size() > 0) {
                return list;
            }
            try {
                Thread.sleep(delay * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return list;
    }
}
