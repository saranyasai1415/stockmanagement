/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package com.sample.stockmanagement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class ProductSearch {

	public WebDriver driver;
	public WebDriverWait wait;
	Connection con;

	@BeforeTest
	public void setUp() {
		// Set up the driver for Chrome browser
		System.setProperty("webdriver.gecko.driver", "C:\\firefoxdriver\\geckodriver.exe");
		driver = new FirefoxDriver();
		// Navigate to the store website
		driver.get("https://www.amazon.in/");
		

	}

	@Test(priority = 1)
	public void testProductDetails() {
		// Enter the product name to search for
		wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		String productName = "iphone";
		WebElement searchBox = driver.findElement(By.id("twotabsearchtextbox"));
		searchBox.sendKeys(productName);
		searchBox.submit();
		wait.until(ExpectedConditions.titleContains(productName));
		

	}

	@Test(priority = 2)
	public void display() {
		String pageTitle = driver.getCurrentUrl();
		System.out.println("********** page tiitle: " + pageTitle);
		
		if (pageTitle.contains("iphone")) {
			System.out.println("Search results page is displayed");
		} else {
			System.out.println("Search results page is not displayed");
		}
		Assert.assertTrue(driver.getCurrentUrl().contains("iphone"));
	}
	@Test (priority = 3)
	  public void searchDatabase() {
	        // TODO: Implement the database search logic here
	        // This could involve querying a database or web service
	        // to find the number of available products
		
		  try {
			  int productCount = 0;
				con = DriverManager.getConnection("jdbc:mysql://localhost:3306/userdb","root","Saibaba@1415");
				Statement stmt = con.createStatement();
				String s= "select productcount from userdb.product where productname = 'iphone'";
			ResultSet rs= stmt.executeQuery(s);
			if (rs.next()) {
				System.out.println("*********!!!!!!!!!!");
				productCount = rs.getInt(1);
				System.out.println(productCount);
			}
		//	 con.close();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	    }

	@AfterTest
	public void tearDown() {
		// Close the browser window
		// driver.quit();
	}
}