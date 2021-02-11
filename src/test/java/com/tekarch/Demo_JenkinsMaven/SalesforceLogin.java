package com.tekarch.Demo_JenkinsMaven;

import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
//import com.aventstack.extentreports.reporter.ExtentHtmlReporter;


import io.github.bonigarcia.wdm.WebDriverManager;

public class SalesforceLogin {

	static WebDriver driver;
	static ExtentReports extent;
	//static ExtentHtmlReporter report;
	public static String dateformat = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());

	@BeforeClass
	public void initialize() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		extent = new ExtentReports();		
	//	report = new ExtentHtmlReporter(System.getProperty("user.dir") + "//Reports//" + dateformat + "_SFDCreport.html");
	//	extent.attachReporter(report);
	}
	public static void gotoSalesforce() {
		driver.get("https://login.salesforce.com");
	}
	public static String takescreenshot() throws Exception {
		TakesScreenshot screenshot = (TakesScreenshot) driver;
		String dstpath = System.getProperty("user.dir") + "//Reports//Screenshots" + dateformat + "_SFDC.PNG";
		File srcfile = screenshot.getScreenshotAs(OutputType.FILE);
		File dstfile = new File(dstpath);
		FileUtils.copyFile(srcfile, dstfile);
		return dstpath;
	}
	public static void loadingProperitiesFile() throws Exception {
		FileInputStream oFIS = new FileInputStream(System.getProperty("user.dir") + "/Environment.properties");
		Properties oPR = new Properties();
		oPR.load(oFIS);
		System.getProperties().putAll(oPR);
	}

	@Test
	public static void loginSalesforce() throws Exception {
	//	ExtentTest test = extent.createTest("loginSalesforce");
	//	test.log(Status.INFO, "Login page launched");
        gotoSalesforce();
		loadingProperitiesFile();
		WebElement ele = driver.findElement(By.xpath("//input[@type='email']"));
		ele.sendKeys(System.getProperty("username"));
	//	test.info("EmailID Entered");
		driver.findElement(By.xpath("//input[@id='password']")).clear();
		WebElement pwd = driver.findElement(By.xpath("//input[@id='password']"));
		pwd.sendKeys(System.getProperty("password"));
	//	test.info("Password Entered");
		driver.findElement(By.xpath("//input[@type='submit']")).click();
		Thread.sleep(1000);
		/*
		 * if
		 * (driver.findElement(By.xpath("//a[contains(text(),'Home')]")).isDisplayed())
		 * {
		 * 
		 * test.log(Status.PASS, "Login is passed"); } else { //
		 * test.addScreenCaptureFromPath(takescreenshot()); test.log(Status.FAIL,
		 * "Login is failed"); }
		 */
		driver.close();
	}
	

//	@AfterClass
	public void closereport() {
		extent.flush();
		driver.close();
	}
}
