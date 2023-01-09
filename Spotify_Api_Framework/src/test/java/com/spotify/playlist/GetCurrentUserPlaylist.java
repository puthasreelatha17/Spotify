package com.spotify.playlist;

import org.testng.annotations.Test;

import com.spotify.baseutil.BaseTest;
import com.spotify.endpoints.Endpoints;
import com.spotify.utils.ApiActionUtil;

import io.restassured.response.Response;

public class GetCurrentUserPlaylist extends BaseTest{
	@Test
	public void test() {
		Response response=ApiActionUtil.getMethod( Endpoints.GETCURRENTUSERPLAYLIST);

ApiActionUtil.validateStatusCode(response,prop_constants.getProperty("status_code_200") );
		
		ApiActionUtil.validateContentType(response, prop_constants.getProperty("content_type_json"));
	}


}
