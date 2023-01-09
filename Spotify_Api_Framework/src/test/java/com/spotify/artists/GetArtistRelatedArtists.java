package com.spotify.artists;

import org.testng.annotations.Test;

import com.spotify.baseutil.BaseTest;
import com.spotify.dataprovider.DataProviderFactory;
import com.spotify.dataprovider.DataProviderFileRowFilter;
import com.spotify.endpoints.Endpoints;
import com.spotify.utils.ApiActionUtil;

import io.restassured.response.Response;

public class GetArtistRelatedArtists extends BaseTest{
	
	@DataProviderFileRowFilter(file = "./src/main/resources/data/Data.xlsx", sql = "Select * from Spotify where REQUEST ='get artist related artist'")
	@Test(dataProvider = "data1", dataProviderClass = DataProviderFactory.class, description = "albums")
	
	public void test(String SL_No, String REQUEST,String ids) {
		Response response=ApiActionUtil.getMethodAddValue( Endpoints.GETARTIST,ids);

       ApiActionUtil.validateStatusCode(response,prop_constants.getProperty("status_code_200") );
		
	}

}
