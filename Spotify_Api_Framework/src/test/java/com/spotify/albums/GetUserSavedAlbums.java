package com.spotify.albums;

import org.testng.annotations.Test;

import com.spotify.baseutil.BaseTest;
import com.spotify.endpoints.Endpoints;
import com.spotify.utils.ApiActionUtil;

import io.restassured.response.Response;

public class GetUserSavedAlbums extends BaseTest{
	@Test
	public void test() {

		Response response=ApiActionUtil.getMethod( Endpoints.GETUSERSAVEDALBUMS);

ApiActionUtil.validateStatusCode(response,prop_constants.getProperty("status_code_200") );
		
	}

}
