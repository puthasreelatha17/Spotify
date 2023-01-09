package com.spotify.showsandepisodes;

import org.testng.annotations.Test;

import com.spotify.baseutil.BaseTest;
import com.spotify.dataprovider.DataProviderFactory;
import com.spotify.dataprovider.DataProviderFileRowFilter;
import com.spotify.endpoints.Endpoints;
import com.spotify.utils.ApiActionUtil;

import io.restassured.response.Response;

public class SaveEpisodes extends BaseTest{
	
	@DataProviderFileRowFilter(file = "./src/main/resources/data/Data.xlsx", sql = "Select * from Spotify where REQUEST ='savedEpisodes'")
	@Test(dataProvider = "data1", dataProviderClass = DataProviderFactory.class, description = "Get saved episodes")
	
	public void test(String SL_No, String REQUEST,String ids) {

		Response response=ApiActionUtil.putMethodQuery(Endpoints.PUTSAVEEPISODES, ids);
				
		ApiActionUtil.validateStatusCode(response,prop_constants.getProperty("status_code_200") );	
	
	}


}
