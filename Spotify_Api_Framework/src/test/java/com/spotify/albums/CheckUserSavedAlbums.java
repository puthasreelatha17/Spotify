package com.spotify.albums;

import org.testng.annotations.Test;

import com.spotify.baseutil.BaseTest;
import com.spotify.dataprovider.DataProviderFactory;
import com.spotify.dataprovider.DataProviderFileRowFilter;
import com.spotify.endpoints.Endpoints;
import com.spotify.utils.ApiActionUtil;

import io.restassured.response.Response;

public class CheckUserSavedAlbums extends BaseTest{
//	@DataProviderFileRowFilter(file = "./src/main/resources/data/Data.xlsx", sql = "Select * from Spotify where REQUEST ='check user saved'")
//	@Test(dataProvider = "data1", dataProviderClass = DataProviderFactory.class, description = "get album tracks")
//	
//	public void test(String SL_No, String REQUEST,String ids) {
//		Response response=ApiActionUtil.getMethodQueryParam( Endpoints.CHECKUSERSAVEDALBUMS,ids);
//
//		ApiActionUtil.validateStatusCode(response,prop_constants.getProperty("status_code_200") );
//		
//	}

	@DataProviderFileRowFilter(file = "./src/main/resources/data/Data.xlsx", sql = "Select * from Spotify where SL_No ='7'")
	@Test(dataProvider = "data1", dataProviderClass = DataProviderFactory.class, description = "get album tracks")
	
	public void test(String SL_No, String REQUEST,String ids, String queryParam, String pathParam) {
		Response response=ApiActionUtil.getMethodQueryParamJson(Endpoints.CHECKUSERSAVEDALBUMS, queryParam);

		ApiActionUtil.validateStatusCode(response,prop_constants.getProperty("status_code_200") );
		
	}
}
