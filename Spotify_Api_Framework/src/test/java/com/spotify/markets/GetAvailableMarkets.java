package com.spotify.markets;

import org.testng.annotations.Test;
import com.spotify.baseutil.BaseTest;
import com.spotify.dataprovider.DataProviderFactory;
import com.spotify.dataprovider.DataProviderFileRowFilter;
import com.spotify.endpoints.Endpoints;
import com.spotify.utils.ApiActionUtil;

import io.restassured.response.Response;

public class GetAvailableMarkets extends BaseTest{
	@Test
	public void test() {
		Response response=ApiActionUtil.getMethod( Endpoints.GETAVAILABEMARKETS);

ApiActionUtil.validateStatusCode(response,prop_constants.getProperty("status_code_200") );
		
		ApiActionUtil.validateContentType(response, prop_constants.getProperty("content_type_json"));
	}


}
