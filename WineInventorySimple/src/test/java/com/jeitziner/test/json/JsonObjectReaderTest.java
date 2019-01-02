/**
 * 
 */
package com.jeitziner.test.json;

import static org.junit.Assert.*;

import javax.json.JsonObject;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.jeitziner.json.JsonObjectReader;

/**
 * @author Christian Jeitziner
 *
 */
public class JsonObjectReaderTest {

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void readEmptyJsonString() {
		System.out.println("Working Directory = " + System.getProperty("user.dir"));
		
		String inputString = "{}";		
		JsonObject jsonObj = JsonObjectReader.getJsonObjectFromString(inputString);
		assertNotNull(jsonObj);		
	}

	@Test
	public void readSimpleJsonString() {
		String inputString = String.join(
			    System.getProperty("line.separator"),
			    "{",
			    "    \"desktops\" : [",
			    "        {",
			    "            \"name\": \"Desktop Wine\",",
			    "            \"viewGroup\": \"wineGroup\"",
			    "        },",
			    "        {",
			    "            \"name\": \"Desktop Wine2\",",
			    "            \"viewGroup\": \"wineGroup2\"",
			    "        },",
			    "        {",
			    "            \"name\": \"Desktop 1\",",
			    "            \"viewGroup\": \"group1\"",
			    "        },",
			    "        {",
			    "            \"name\": \"Desktop 2\",",
			    "            \"viewGroup\": \"group1\"",
			    "        }       ",
			    "    ]",
			    "}");

		JsonObject jsonObj = JsonObjectReader.getJsonObjectFromString(inputString);
		assertNotNull(jsonObj);		
	}
	
}
