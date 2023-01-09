package com.spotify.utils;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.hamcrest.Matchers;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.spotify.baseutil.BaseTest;
import com.spotify.extentreports.ExtentManager;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import junit.framework.Assert;

public class ApiActionUtil extends BaseTest{
	
	public static TakesScreenshot screenshot;
	
	/**
	 * Description Method to provide info in the log,testNg reports
	 * 
	 * @author Sajal
	 * @param message
	 */
	public static void info(String message) {
		BaseTest.logger.info(message);
		// ExtentHCLManager.getTestReport().info(message);
	}

	/**
	 * Description Method for the error updation in logs
	 * 
	 * @author Sajal
	 * @param message
	 */
	public static void error(String message) {
		BaseTest.logger.error(message);
		// ExtentHCLManager.getTestReport().error(message);
	}

	/**
	 * Description Method to provide info in the extent report
	 * 
	 * @author Sajal
	 * @param message
	 */
	public static void extentinfo(String message) {
		ExtentManager.getTestReport().info(message);
	}

	/**
	 * Description Method for the pass updation in extent Report
	 * 
	 * @author Sajal
	 * @param message
	 */
	public static void pass(String message) {
		ExtentManager.getTestReport().pass(MarkupHelper.createLabel(message, ExtentColor.GREEN));
	}

	/**
	 * Description Method for the info/error updation in extent Report
	 * 
	 * @author Sajal
	 * @param message
	 * @param color
	 */
	public static void validationinfo(String message, String color) {
		if (color.equalsIgnoreCase("blue"))
			ExtentManager.getTestReport().info(MarkupHelper.createLabel(message, ExtentColor.BLUE));
		else if (color.equalsIgnoreCase("green"))
			ExtentManager.getTestReport().pass(MarkupHelper.createLabel(message, ExtentColor.GREEN));
		else if (color.equalsIgnoreCase("red"))
			ExtentManager.getTestReport().fail(MarkupHelper.createLabel(message, ExtentColor.RED));
	}

	/**
	 * Description Method for the error updation in extent Report
	 * 
	 * @author Sajal
	 * @param message
	 */
	public static void fail(String message) {
		ExtentManager.getTestReport().fail(MarkupHelper.createLabel(message, ExtentColor.RED));
	}
	
	/**
	 * Description:: Method to wait for mentioned time
	 * 
	 * @author Sajal
	 * @param poll
	 */
	public static void poll(int poll) {
		try {
			Thread.sleep(poll);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Description Method for fetching Current Date Time
	 * 
	 * @author Sajal
	 */
	public static String getCurrentDateTime() {
		DateFormat customFormat = new SimpleDateFormat("MM_dd_yyyy_HH_mm_ss");
		Date currentDate = new Date();
		return customFormat.format(currentDate);
	}

	/**
	 * Description Method to delete the directory
	 * 
	 * @author Sajal
	 * @param pathToDeleteFolder
	 */
	public static void deleteDir(String pathToDeleteFolder) {
		File extefolder = new File(pathToDeleteFolder);
		if ((extefolder.exists())) {
			deleteFolderDir(extefolder);
		}
	}

	/**
	 * Description Method to delete folder directory
	 * 
	 * @author Sajal
	 * @param folderToDelete
	 */
	public static void deleteFolderDir(File folderToDelete) {
		File[] folderContents = folderToDelete.listFiles();
		if (folderContents != null) {
			for (File folderfile : folderContents) {
				if (!Files.isSymbolicLink(folderfile.toPath())) {
					deleteFolderDir(folderfile);
				}
			}
		}
		folderToDelete.delete();
	}
	
	/**
	 * Description: Capture the screenshot of the complete screen
	 * 
	 * @author Sajal
	 * @param path
	 * @return destinationPath
	 */
	public static String getScreenShot(String path) {
		File src = (screenshot.getScreenshotAs(OutputType.FILE));
		String currentDateTime = getCurrentDateTime();
		String destinationPath = path + BaseTest.className + "-" + currentDateTime + ".png";
		File destination = new File(destinationPath);
		try {
			FileUtils.copyFile(src, destination);
		} catch (IOException e) {
			error("Capture Failed " + e.getMessage());
		}
		return "./Screenshots/" + BaseTest.className + "-" + currentDateTime + ".png";
	}

	public String getJsonData(Response response, String path) {
		String jsonData = response.jsonPath().getString(path);
		return jsonData;
	}

	public Response get() {
		return given().log().all().get(BaseTest.baseUrl);
	}
	
	
	public static RequestSpecification getWithParam() {
		try {
		Map<String, String> map = new HashMap<String,String>();
		 map.put("key", prop_constants.getProperty("key"));
		 map.put("token", prop_constants.getProperty("token"));
		return given().queryParams(map);
		}catch (Exception e) {
			e.printStackTrace();
			error("Unable to get the Key and Token");
			Assert.fail();
		}
		return requestSpecification;
	}
	
	
	public static void validateStatusCode(Response response,String status_code) {
		try {
			response.then().assertThat().statusCode(Integer.parseInt(status_code));
			extentinfo("Status code is: "+status_code);
			info("Status code is: "+status_code);
		}catch (Exception |AssertionError e) {
			e.printStackTrace();
			error(e.getMessage());
			fail("Unable to validate status code");
			Assert.fail("Unable to validate status code");
			
		}
	}
	
	public static void validateResponseTime(Response response) {
		try {
			response.then().assertThat().time(Matchers.lessThan(get_max_response_time),TimeUnit.SECONDS);
			extentinfo("Response time is less than: "+get_max_response_time);
			info("Response time is less than: "+get_max_response_time);
		}catch (Exception |AssertionError e) {
			e.printStackTrace();
			error(e.getMessage());
			fail("Unable to validate status code");
			Assert.fail("Unable to validate status code");
		}
	}
	
	public static void validateContentType(Response response,String contentType) {
		try {
			response.then().assertThat().contentType(contentType);
			extentinfo("Content type is: "+contentType);
			info("Content type is: "+contentType);
		}catch (Exception |AssertionError e) {
			e.printStackTrace();
			error(e.getMessage());
			fail("Unable to validate content type");
			Assert.fail("Unable to validate content type");
		}
	}


	public static void verifyFailStatusCode(Response response) {
		try {
		assertEquals(String.valueOf(response.getStatusCode()).startsWith("40"), true,
				"value of status code is" + response.getStatusCode());
		}catch (Exception e) {
			e.printStackTrace();
			error("Unable to verify status code");
		}
	}
	
	public static void verifyResponseBody( String expected, String description,Response response,String jsonPath) {
		try {
		String actual = response.jsonPath().get(jsonPath);
		assertEquals(actual, expected, description);
		}catch (Exception e) {
			e.printStackTrace();
			error("Unable to validate response body"+expected);
		}

	}
	
	public static String getResponseBodyValue(Response response,String jsonPath) {
		String actual =null;
		try {
			 actual = response.jsonPath().get(jsonPath);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return actual;
	
	}
	
	public synchronized static RequestSpecification passQuerryParm() {
		RequestSpecification requestSpecification = null;
		try {
			requestSpecification= requestSpecBuilder.build();
			requestSpecification=given().spec(requestSpecification).queryParam(prop_constants.getProperty("key"), prop_constants.getProperty("token"));
		} catch (Exception e) {
			e.printStackTrace();
			error("Unable to get the details");
			Assert.fail();
		}
		return requestSpecification;
	}
	
	public synchronized static Response getMethodParam(String endpoint, String value) {
		Response response;
		try {			
			requestSpecification= requestSpecBuilder.addPathParam("id", value).build();
			response=given().spec(requestSpecification).auth().oauth2(accessToken).when().get(endpoint).then().log().all().extract().response();
			return response;
		} catch (Exception e) {
			e.printStackTrace();
			error("Unable to get the details");
			Assert.fail();
		}
		return null;
	}
	
	
	public synchronized static Response getMethodQueryParam(String endpoint,String value) {
		Response response;
		try {			
			requestSpecification= requestSpecBuilder.addQueryParam("ids", value).build();
			response=given().spec(requestSpecification).auth().oauth2(accessToken).when().get(endpoint).then().log().all().extract().response();
			return response;
		} catch (Exception e) {
			e.printStackTrace();
			error("Unable to get the details");
			Assert.fail();
		}
		return null;
	}
	
	
	public synchronized static Response getMethodAddValue(String endpoint,String value) {
		Response response;
		try {			
			requestSpecification= requestSpecBuilder.build();
			response=given().spec(requestSpecification).auth().oauth2(accessToken).when().get(endpoint+value).then().log().all().extract().response();
			return response;
		} catch (Exception e) {
			e.printStackTrace();
			error("Unable to get the details");
			Assert.fail();
		}
		return null;
	}
	
	public synchronized static Response getMethod(String endpoint) {
		Response response;
		try {		
			requestSpecification= requestSpecBuilder.build();
			response=given().spec(requestSpecification).auth().oauth2(accessToken).when().get(endpoint).then().log().all().extract().response();
			return response;
		} catch (Exception e) {
			e.printStackTrace();
			error("Unable to get the details");
			Assert.fail();
		}
		return null;
	}
	
	
	
	
	public synchronized static Response getMethodWithPathParam(HashMap<String, String> pathParamMap,HashMap<String, String> querParamMap, String endpoint) {
		Response response;
		try {
		requestSpecification= requestSpecBuilder.addPathParams(pathParamMap).addQueryParams(querParamMap).build();
			response=given().spec(requestSpecification).when().get(endpoint).then().log().all().extract().response();
			return response;
		} catch (Exception e) {
			e.printStackTrace();
			error("Unable to get the details");
			Assert.fail();
		}
		return null;
	}
	
	
	
	 public static  Map<String, String> data(String key, String value) {
		 Map<String, String> map = new HashMap<String,String>();
	        map.put(key, value);     
			return map;	
		}
	
	public synchronized static Response putMethod(HashMap<String, String> querParamMap,String endpoint) {
		Response response;
		try {
			requestSpecification= requestSpecBuilder.addQueryParams(querParamMap).build();
			response=given().spec(requestSpecification).when().put(endpoint).then().log().all().extract().response();
			return response;
		} catch (Exception e) {
			e.printStackTrace();
			error("Unable to get the details");
			Assert.fail();
		}
		return null;
	}
	
	public synchronized static Response putMethodParam(String endpoint, String value) {
		Response response;
		try {
			requestSpecification= requestSpecBuilder.addPathParam("id", value).build();
			response=given().spec(requestSpecification).auth().oauth2(accessToken).when().put(endpoint).then().log().all().extract().response();
			return response;
		} catch (Exception e) {
			e.printStackTrace();
			error("Unable to get the details");
			Assert.fail();
		}
		return null;
	}
	
	public synchronized static Response putMethodAddValueBody(String endpoint, String value, String obj) {
		Response response;
		try {
			requestSpecification= requestSpecBuilder.build();
			response=given().spec(requestSpecification).auth().oauth2(accessToken).body(obj).when().put(endpoint+value).then().log().all().extract().response();
			return response;
		} catch (Exception e) {
			e.printStackTrace();
			error("Unable to get the details");
			Assert.fail();
		}
		return null;
	}
	
	public synchronized static Response putMethodQuery(String endpoint, String value) {
		Response response;
		try {
			requestSpecification= requestSpecBuilder.addQueryParam("ids", value).build();
			response=given().spec(requestSpecification).auth().oauth2(accessToken).when().put(endpoint).then().log().all().extract().response();
			return response;
		} catch (Exception e) {
			e.printStackTrace();
			error("Unable to get the details");
			Assert.fail();
		}
		return null;
	}
	
	public synchronized static Response putMethodWithPathParam(HashMap<String, String> pathParamMap,HashMap<String, String> querParamMap, String endpoint) {
		Response response;
		try {
			requestSpecification= requestSpecBuilder.addPathParams(pathParamMap).addQueryParams(querParamMap).build();
			response=given().spec(requestSpecification).when().put(endpoint).then().log().all().extract().response();
			return response;
		} catch (Exception e) {
			e.printStackTrace();
			error("Unable to get the details");
			Assert.fail();
		}
		return null;
	}
	
	public synchronized static Response postMethod(HashMap<String, String> querParamMap,String endpoint) {
		Response response;
		try {
			requestSpecification= requestSpecBuilder.addQueryParams(querParamMap).build();
			response=given().spec(requestSpecification).contentType(ContentType.JSON).post(endpoint).then().log().all().extract().response();
			return response;
		} catch (Exception e) {
			e.printStackTrace();
			error("Unable to get the details");
			Assert.fail();
		}
		return null;
	}
	
	public synchronized static Response postMethodParamBody(String endpoint,String value, String obj) {
		Response response;
		try {
			requestSpecification= requestSpecBuilder.addPathParam("id", value).build();
			response=given().spec(requestSpecification).auth().oauth2(accessToken).body(obj).when().contentType(ContentType.JSON).post(endpoint).then().log().all().extract().response();
			return response;
		} catch (Exception e) {
			e.printStackTrace();
			error("Unable to get the details");
			Assert.fail();
		}
		return null;
	}
	
	public synchronized static Response postMethodpathqueryParam(String endpoint,String value, String queryvalue) {
		Response response;
		try {
			requestSpecification= requestSpecBuilder.addPathParam("id", value).addQueryParam("uris", queryvalue).build();
			response=given().spec(requestSpecification).auth().oauth2(accessToken).when().contentType(ContentType.JSON).post(endpoint).then().log().all().extract().response();
			return response;
		} catch (Exception e) {
			e.printStackTrace();
			error("Unable to get the details");
			Assert.fail();
		}
		return null;
	}
	
	public synchronized static Response postMethodWithPathParam(HashMap<String, String> pathParamMap,HashMap<String, String> querParamMap,String endpoint) {
		Response response;
		try {
			requestSpecification= requestSpecBuilder.addPathParams(pathParamMap).addQueryParams(querParamMap).build();
			response=given().spec(requestSpecification).contentType(ContentType.JSON).post(endpoint).then().log().all().extract().response();
			return response;
		} catch (Exception e) {
			e.printStackTrace();
			error("Unable to get the details");
			Assert.fail();
		}
		return null;
	}
	
	public synchronized static Response deleteMethod(HashMap<String, String> querParamMap,String endpoint) {
		Response response;
		try {
			requestSpecification= requestSpecBuilder.addQueryParams(querParamMap).build();
			response=given().spec(requestSpecification).when().delete(endpoint).then().log().all().extract().response();
			return response;
		} catch (Exception e) {
			e.printStackTrace();
			error("Unable to get the details");
			Assert.fail();
		}
		return null;
	}
	
	public synchronized static Response deleteMethodWithPathParam(HashMap<String, String> pathParamMap,HashMap<String, String> querParamMap,String endpoint) {
		Response response;
		try {
			requestSpecification= requestSpecBuilder.addPathParams(pathParamMap).addQueryParams(querParamMap).build();
			response=given().spec(requestSpecification).when().delete(endpoint).then().log().all().extract().response();
			return response;
		} catch (Exception e) {
			e.printStackTrace();
			error("Unable to get the details");
			Assert.fail();
		}
		return null;
	}
	
	public synchronized static Response getMethodforAuthorization(HashMap<String, String> querParamMap,String endpoint) {
		Response response;
		try {		
			requestSpecification= requestSpecBuilder.addQueryParams(querParamMap).build();
			response=given().spec(requestSpecification).when().get(endpoint).then().log().all().extract().response();
			return response;
		} catch (Exception e) {
			e.printStackTrace();
			error("Unable to get the details");
			Assert.fail();
		}
		return null;
	}
	
	public synchronized static Response postMethodforAuthroization(HashMap<String, String> querParamMap,String endpoint) {
		Response response;
		try {
			requestSpecification= requestSpecBuilder.build();
			response=given().spec(requestSpecification).formParams(querParamMap).post(endpoint).then().log().all().extract().response();
			return response;
		} catch (Exception e) {
			e.printStackTrace();
			error("Unable to get the details");
			Assert.fail();
		}
		return null;
	}
	
	/************USING JSON************/
	
	public static void addQueryParamFromJsonFile(String querParamKey) {
		try {
			
			if (querParamKey.equals(prop_constants.getProperty("null"))) {
				info("This reuest do not have Query Parameters");
			} else {
				String[] queryKey = null;
				HashMap<String, Object> paramMap = new HashMap<String, Object>();
				JSONParser parser = new JSONParser();

				Object obj = parser.parse(new FileReader("./src/test/resources/SpotifyData.json"));

				JSONObject jsonObject = (JSONObject) obj;
				queryKey = querParamKey.split(",");
				for (int i = 0; i < queryKey.length; i++) {
					paramMap.put(queryKey[i], jsonObject.get(queryKey[i]));
					requestSpecBuilder.addQueryParams(paramMap);
					System.out.println("Multiple query");
				}
			}
		} catch (Exception e) {
			error(e.getMessage());
			fail("Unable to add Query parameter");
			Assert.fail("Unable to add Query parameter");
		}
	}


public static void addPathParam(String pathParam, String Ids) {
		try {
			requestSpecBuilder.addPathParam(pathParam, Ids);
		} catch (Exception e) {
			error(e.getMessage());
			fail("Unable to add Path parameter");
			Assert.fail("Unable to add Path parameter");
		}
	}


public synchronized static Response getMethodPathParamJson(String endpoint, String queryparam, String pathParam, String ids) {
	Response response;
	try {		
		addQueryParamFromJsonFile(queryparam);
		addPathParam(pathParam, ids);
		requestSpecification= requestSpecBuilder.build();
		response=given().spec(requestSpecification).auth().oauth2(accessToken).when().get(endpoint).then().log().all().extract().response();
		return response;
	} catch (Exception e) {
		e.printStackTrace();
		error("Unable to get the details");
		Assert.fail();
	}
	return null;
}

public synchronized static Response getMethodQueryParamJson(String endpoint, String queryparam) {
	Response response;
	try {			
		addQueryParamFromJsonFile(queryparam);
		requestSpecification= requestSpecBuilder.build();
		response=given().spec(requestSpecification).auth().oauth2(accessToken).when().get(endpoint).then().log().all().extract().response();
		return response;
	} catch (Exception e) {
		e.printStackTrace();
		error("Unable to get the details");
		Assert.fail();
	}
	return null;
}

public  static void schemaValidation(Response response, String type) {	
	
	try {			
		response.then().assertThat().body(matchesJsonSchemaInClasspath(""));
	} catch (Exception e) {
		e.printStackTrace();
		error("Unable to validate json schema");
		Assert.fail();
	}
}



}
