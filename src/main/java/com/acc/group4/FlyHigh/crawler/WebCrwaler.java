package com.acc.group4.FlyHigh.crawler;

import java.io.IOException;
import java.time.Duration;
import java.util.HashSet;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.acc.group4.FlyHigh.Utils.FlyHighLogger;

public class WebCrwaler {
	public static WebCrwaler webcrawlerInstance = null;
	private static Logger logger ;

	public static  WebCrwaler getInstance()  {
		try {
		 logger = FlyHighLogger.initLogger();
		}catch(Exception e) {
			System.out.println("Logger failed to initiate");
		}
		webcrawlerInstance = webcrawlerInstance == null ? new WebCrwaler() : webcrawlerInstance;
		return webcrawlerInstance;
	}
	HashSet<String> urlForFlightPages = new HashSet();
	public HashSet crawlFlightSites(String url, int numberOfURL) {
	
		try {
			if (!urlForFlightPages.contains(url) && urlForFlightPages.size()<=numberOfURL) {
				urlForFlightPages.add(url);
				System.out.println("url :: " + url);
				Document doc = Jsoup.connect(url).get();
				System.out.println("Title: " + doc.title());
				Elements availableResopurces = doc.select("a[href]");
				for (Element element : availableResopurces) {
					crawlFlightSites(element.attr("abs:href"),numberOfURL);
				}
			}
		} catch (Exception e) {
			System.out.println("Exception encountered :( + " + e);
		}
		return urlForFlightPages;
	}
	public String WebScrapper(String url) {
		System.out.println(">>>Entering WebScrapper method<<<");
		String result = null;
		try {
			ChromeDriver driver = new ChromeDriver();
			System.out.println("::Creating profile prefernces::");
			System.out.println("::Connecting Web::");
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			Thread.sleep(10000);
			driver.get(url);
			Thread.sleep(20000);
			driver.executeScript("window.scrollTo(0, document.body.scrollHeight);");
			Thread.sleep(10000);
			result = driver.getPageSource();
			System.out.println("Result Title  :: " + driver.getTitle());
			driver.close();
		} catch (Exception e) {
           System.out.println("Exception encountered try again or come after some time:: "+e);
		}
		System.out.println(">>>Exiting WebScrapper method<<<");
		return result;
	}

	public Document connectWithDelay(String url) {
        try {
            TimeUnit.SECONDS.sleep(10); // Delays the request
            return Jsoup.connect(url).get();
        } catch (InterruptedException | IOException e) {
            System.out.println("An error occurred: " + e.getMessage());
            return null;
        }
    }
	public static void Quit(ChromeDriver Driver){
		   if (Driver!= null)
		       Driver.quit();
		}
}
