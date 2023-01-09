package com.spotify.albums;

import org.testng.annotations.Test;

import com.spotify.baseutil.BaseTest;
import com.spotify.dataprovider.DataProviderFactory;
import com.spotify.dataprovider.DataProviderFileRowFilter;
import com.spotify.endpoints.Endpoints;
import com.spotify.utils.ApiActionUtil;

import io.restassured.response.Response;

public class GetAlbumsTracks extends BaseTest{
//	@DataProviderFileRowFilter(file = "./src/main/resources/data/Data.xlsx", sql = "Select * from Spotify where REQUEST ='get album tracks'")
//	@Test(dataProvider = "data1", dataProviderClass = DataProviderFactory.class, description = "get album tracks")
//	
//	public void test(String SL_No, String REQUEST,String ids) {
//
//		Response response=ApiActionUtil.getMethodParam( Endpoints.GETALBUMTRACKS,ids);
//       
//		ApiActionUtil.validateStatusCode(response,prop_constants.getProperty("status_code_200") );
//		
//		ApiActionUtil.validateContentType(response, prop_constants.getProperty("content_type_json"));
//	}
	
	@DataProviderFileRowFilter(file = "./src/main/resources/data/Data.xlsx", sql = "Select * from Spotify where SL_No ='6'")
	@Test(dataProvider = "data1", dataProviderClass = DataProviderFactory.class, description = "get album tracks")
	
	public void test(String SL_No, String REQUEST,String ids, String queryparam, String pathParam) {

		Response response=ApiActionUtil.getMethodPathParamJson(Endpoints.GETALBUMTRACKS, queryparam, pathParam, ids);
				
		ApiActionUtil.validateStatusCode(response,prop_constants.getProperty("status_code_200") );
		
		ApiActionUtil.validateContentType(response, prop_constants.getProperty("content_type_json"));
	}

}
