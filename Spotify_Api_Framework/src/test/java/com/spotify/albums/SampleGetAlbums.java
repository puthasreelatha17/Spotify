package com.spotify.albums;

import static io.restassured.RestAssured.given;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class SampleGetAlbums {
	
	ResponseSpecBuilder responseSpecBuilder;
	ResponseSpecification responseSpecification;
	RequestSpecBuilder requestSpecBuilder;
	RequestSpecification requestSpecification;
	String access_token = "BQDzv5FIbQJCuI_QtDlnuGZaLTAqyKqmYnhq7QrziXHfooaKyJZpz-t4u1GV4eD9GHKW8VCp-cuAUnfVtzvbMZCfxiCn4ObeVgJtxhivRYivTj0yS3Xt-VDPQ0AzlxztzkBCiQQDAlErpYE9pHPX-z5w6mNVH7_xjLZeHW-f78DpuuvYayXc4FqOcm1nozSDkLMprIQXsOyDxHm6mDAGFWxz8hYPyF1Mb2DJhyPXBaJo3hYRsNLH0P4gGoIlf1mIa6Wv5gIMtF4DbdOPfCgXIR31nx665wPUKL32fSGfvNMMkct-xlq7OhFYLqyqBVF_1K8P9SmJBeJimO3C3Pk2";

	@BeforeClass

	public void before() {

		RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder().setBaseUri("https://api.spotify.com")
				.setBasePath("/v1").addHeader("Authorization", "Bearer " + access_token).log(LogDetail.ALL);
		requestSpecification = requestSpecBuilder.build();

		ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder().expectContentType(ContentType.JSON)
				.log(LogDetail.ALL);
		responseSpecification = responseSpecBuilder.build();
	}
	
	@Test

	public void test() {

		given(requestSpecification).when().get("albums?ids=382ObEPsp2rxGrnsizN5TX,1A2GTWGtFfWp7KSQTwWOyo").then().spec(responseSpecification)
				.assertThat().statusCode(200);

	}

}
