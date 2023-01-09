package com.spotify.playlist;

import org.json.simple.JSONObject;
import org.testng.annotations.Test;

import com.spotify.baseutil.BaseTest;
import com.spotify.dataprovider.DataProviderFactory;
import com.spotify.dataprovider.DataProviderFileRowFilter;
import com.spotify.endpoints.Endpoints;
import com.spotify.utils.ApiActionUtil;

import io.restassured.response.Response;

public class CreatePlaylist extends BaseTest{
	
	@DataProviderFileRowFilter(file = "./src/main/resources/data/Data.xlsx", sql = "Select * from Spotify where REQUEST ='createPlaylist userId'")
	@Test(dataProvider = "data1", dataProviderClass = DataProviderFactory.class, description = "createPlaylist")
	
	public void playlistAdd(String SL_No, String REQUEST,String ids) {
		
//		JSONObject obj=new JSONObject();
//		obj.put("name", "All Time Favourite");
//		obj.put("description", "Favourite Playlist");
//		obj.put("public", false);
		
		String payload="{\r\n"
				+ "  \"name\": \"All Time Favourite\",\r\n"
				+ "  \"description\": \"Favourite Playlist\",\r\n"
				+ "  \"public\": false\r\n"
				+ "}";
		
		Response response=ApiActionUtil.postMethodParamBody(Endpoints.CREATEPLAYLIST,ids, payload);
				
		ApiActionUtil.validateStatusCode(response,prop_constants.getProperty("status_code_201") );
		
		ApiActionUtil.validateContentType(response, prop_constants.getProperty("content_type_json"));
		
	}


}
