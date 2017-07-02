package com.SeleniumJava.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {

	public static String getProperty(String propertyName) {

		Properties properties = new Properties();

		FileInputStream configFile;
		try {
			configFile = new FileInputStream(System.getProperty("user.dir")
					+ "//src//main//java//config//config.properties");
			properties.load(configFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return properties.getProperty(propertyName);
	}
	
	public static String getPageProperty(String propertyName) {

		Properties properties = new Properties();
		
	

		FileInputStream configFile;
		try {
			configFile = new FileInputStream(System.getProperty("user.dir")
					+ "//src//test//java//properties//page.properties");
			properties.load(configFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return properties.getProperty(propertyName);
	}
	public static String getLocator(String propertyName, String fileName) {

		Properties properties = new Properties();

		FileInputStream configFile;
		try {
			configFile = new FileInputStream(System.getProperty("user.dir")
					+ "//src//main//resources//config.properties");
			properties.load(configFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return properties.getProperty(propertyName);
	}
}
