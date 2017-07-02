package com.SeleniumJava.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.SeleniumJava.utils.ActionLibrary;

public class BlogPage extends ActionLibrary{
	
	private By blogLinks;
	
	public BlogPage(WebDriver driver) {
		super(driver);
	}
	
	
	public boolean verifyLinksOnBlogTests(String link){
		blogLinks = By.cssSelector("a[href=\""+link+"\"]");
		return isDisplayed(blogLinks);
		
	}



}
