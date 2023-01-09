package com.spotify.genres;

import org.testng.annotations.Test;

import com.spotify.baseutil.BaseTest;
import com.spotify.endpoints.Endpoints;
import com.spotify.utils.ApiActionUtil;

public class GetGenres extends BaseTest{
	
	@Test
	public void test() {
		ApiActionUtil.getMethod( Endpoints.GETGENRES);

	}

}
