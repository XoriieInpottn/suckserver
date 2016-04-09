package org.lioxa.ustc.suckserver.routine.crawler;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;

public class Browser {
	private FirefoxDriver firefoxDriver;

	public FirefoxDriver getFirefoxDriver() {
		return firefoxDriver;
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
	 * @param suffix
	 */
	public void setFilters(String[] suffix) {
		String script = null;
		script = "window.invoke(\"setFliters\", {fliters:[";
		for(String i : suffix) {
			script += "\""+ i + "\",";
		}
		script = script.substring(0, script.length()-1);
		script += "]})";
		((JavascriptExecutor) this.firefoxDriver).executeScript(script);
	}
	
	/**
	 * change the User-Agent of this browser
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
	 * @param type
	 * @param host
	 * @param port
	 */
	public void setProxy(String type, String host, int port) {
		String script = null;
		script = "window.invoke(\"setProxy\", {";
		script += "type:\"" + type + "\", ";
		script += "host:\"" + host + "\", ";
		script += "port:"+port+"})";
		((JavascriptExecutor) this.firefoxDriver).executeScript(script);
	}
	
	public void setAccept(String[] accepts) {
		String script = null;
		script = "window.invoke(\"setAccept\", {accepts:[";
		for(String i : accepts) {
			script += "\""+ i + "\",";
		}
		script = script.substring(0, script.length()-1);
		script += "]})";
		((JavascriptExecutor) this.firefoxDriver).executeScript(script);
	}
	
	/**
	 * to connect with a url
	 * @param url
	 */
	public void get(String url) {
		this.firefoxDriver.navigate().to(url);
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
		try {
			this.firefoxDriver.close();
			Set<String> set = this.firefoxDriver.getWindowHandles();
			Object handles[] = set.toArray();
			this.firefoxDriver.switchTo().window(handles[handles.length - 1].toString());
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * go to the next window. Note: without closing current window
	 */
	public void windowForward() {
		try {
			String before = this.firefoxDriver.getWindowHandle();
			Set<String> set = this.firefoxDriver.getWindowHandles();
			Object handles[] = set.toArray();
			ArrayList<String> array = new ArrayList<>(); 
		    for(int i = 0; i < handles.length; i++) {
		    	array.add(handles[i].toString());
		    }
		    int i = array.indexOf(before);
			this.firefoxDriver.switchTo().window(array.get(i + 1));
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * go to the next window. Note: closing the current window at the same time
	 */
	public void windowForwardWithoutBefore() {
		try {
			String before = this.firefoxDriver.getWindowHandle();
			Set<String> set = this.firefoxDriver.getWindowHandles();
			Object handles[] = set.toArray();
			ArrayList<String> array = new ArrayList<>(); 
		    for(int i = 0; i < handles.length; i++) {
		    	array.add(handles[i].toString());
		    }
		    int i = array.indexOf(before);
		    if(i < set.size() - 1) {
			    this.firefoxDriver.close();
				this.firefoxDriver.switchTo().window(array.get(i + 1));
		    }
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public List<WebElement> findElements(By by) {
		return this.firefoxDriver.findElements(by);
	}
	
	public WebElement findElement(By by) {
		return this.firefoxDriver.findElement(by);
	}
	
	public List<WebElement> selectElements(String cssPath) {
		return this.firefoxDriver.findElementsByCssSelector(cssPath);
	}
	
	public WebElement selectElement(String cssPath) {
		return this.firefoxDriver.findElementByCssSelector(cssPath);
	}
	
	public boolean isWebElementExits(By selector) {
		try {
			this.firefoxDriver.findElement(selector);
			return true;
		} catch (NoSuchElementException e) {
			return false;
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
	 * @param time is set to wait the new page loading
	 */
	public void scrollDown(long time) {
		String js = "var q=document.documentElement.scrollTop=1000000";
		this.firefoxDriver.executeScript(js, 0);
		try {
			Thread.sleep(time);
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

}
