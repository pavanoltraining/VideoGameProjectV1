package testCases;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;

import java.util.HashMap;
import org.testng.Assert;
import org.testng.annotations.Test;
import helper.VideoGame;
import io.restassured.response.Response;

public class TC_Post_VideoGame_Pojo {

	public int port=8081;
	public String URI="http://localhost:"+port+"/app";
	
	@Test(priority=1)
	public void test_addNewVideoGame()
	{
		VideoGame myVideoGame=new VideoGame();
		myVideoGame.setId(107);
		myVideoGame.setName("Spider-Man");
		myVideoGame.setReleaseDate("2019-09-20T08:55:58.510Z"); 
		myVideoGame.setReviewScore(9);
		myVideoGame.setCategory("Driving");
		myVideoGame.setRating("Five");
					
		Response res=
		given()
			.contentType("application/json")
			.body(myVideoGame)
		.when()
			.post(URI+"/videogames")
		.then()
			.statusCode(200)
			.log().body()
			.extract().response();
		
		String jsonString=res.asString();
		Assert.assertEquals(jsonString.contains("Record Added Successfully"),true);
	}
	
	@Test(priority=2)
	public void test_getVideoGame_pojo()
	{
			
		VideoGame myvideogame=
				given()
				.when()
					.get(URI+"/videogames"+"/107").as(VideoGame.class);
		
		System.out.println(myvideogame.toString());
							
	}
	
	
}
