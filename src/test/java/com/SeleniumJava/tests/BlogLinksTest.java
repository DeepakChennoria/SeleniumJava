package com.SeleniumJava.tests;

import java.util.LinkedList;

import org.json.JSONException;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.SeleniumJava.base.Base;
import com.SeleniumJava.pages.BlogPage;
import com.SeleniumJava.utils.JsonReader;

public class BlogLinksTest extends Base{
	private BlogPage blogPage;

	
	
	@Test(dataProvider="blogLinks")
	public void verifyLinksOnBlogTests(String blogLinks){
		blogPage = new BlogPage(driver);
		Assert.assertTrue(blogPage.verifyLinksOnBlogTests(blogLinks));
	}
	
	@DataProvider(name="blogLinks") public Object[][] createJsonData() throws JSONException{
		   LinkedList<String> testData = JsonReader.getData("links");
		   Object[][] ret = new Object[testData.size()][1];
		  for(int i = 0; i < testData.size(); i++) {
			  System.out.println(testData.get(i));
			  String a = testData.get(i);
			  ret[i][0] = a;
		  }
		  return ret;
		}

}
