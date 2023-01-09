package com.spotify.playlist;

import org.testng.annotations.Test;

import com.spotify.baseutil.BaseTest;
import com.spotify.dataprovider.DataProviderFactory;
import com.spotify.dataprovider.DataProviderFileRowFilter;
import com.spotify.endpoints.Endpoints;
import com.spotify.utils.ApiActionUtil;

import io.restassured.response.Response;

public class UpdatePlaylist extends BaseTest{
	
	@DataProviderFileRowFilter(file = "./src/main/resources/data/Data.xlsx", sql = "Select * from Spotify where REQUEST ='updatePlaylist'")
	@Test(dataProvider = "data1", dataProviderClass = DataProviderFactory.class, description = "update Playlist")
	
	public void playlistAdd(String SL_No, String REQUEST,String ids) {
		

		String payload="{\r\n"
				+ "  \"name\": \"Sreelatha All Time Favourite\",\r\n"
				+ "  \"description\": \"Favourite Playlist\",\r\n"
				+ "  \"public\": false\r\n"
				+ "}";
		
		Response response=ApiActionUtil.putMethodAddValueBody(Endpoints.UPDATEPLAYLIST,ids, payload);
				
		ApiActionUtil.validateStatusCode(response,prop_constants.getProperty("status_code_200") );
		
	
	}


}
