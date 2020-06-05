package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import helper.VideoGame;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.util.HashMap;

public class TC_VideoGameAPI_Tests {

	public int port=8081;
	public String URI="http://localhost:"+port+"/app";
	
	@Test(priority=1)
	public void test_getAllVideoGames()
	{
		given()
		.when()
			.get(URI+"/videogames")
		.then()
			.statusCode(200);
			//.log().body();
	}
	
	@Test(priority=2)
	public void test_addNewVideoGame()
	{
		HashMap data=new HashMap();
		data.put("id", "106");
		data.put("name", "Spider-Man");
		data.put("releaseDate", "2019-09-20T08:55:58.510Z");
		data.put("reviewScore", "5");
		data.put("category", "Adventure");
		data.put("rating", "Universal");
		
		Response res=
		given()
			.contentType("application/json")
			.body(data)
		.when()
			.post(URI+"/videogames")
		.then()
			.statusCode(200)
			.log().body()
			.extract().response();
		
		String jsonString=res.asString();
		Assert.assertEquals(jsonString.contains("Record Added Successfully"),true);
	}
	
	
	@Test(priority=3)
	public void test_getVideoGame()
	{
		given()
		
		.when()
			.get(URI+"/videogames"+"/106")   //http://localhost:8081/app/videogames/106
		.then()
			.statusCode(200)
			.log().body()
			.body("videoGame.id",equalTo("106"))
			.body("videoGame.name",equalTo("Spider-Man"));
							
	}
	
	@Test(priority=4)
	public void test_UpdateVideoGame()
	{
			HashMap data=new HashMap();
			data.put("id", "106");
			data.put("name", "Pacman");  // update video game name
			data.put("releaseDate", "2019-09-20T08:55:58.510Z");
			data.put("reviewScore", "4");  // update
			data.put("category", "Adventure");
			data.put("rating", "Universal");
			
			given()
				.contentType("application/json")
				.body(data)
			.when()
				.put(URI+"/videogames"+"/106") //http://localhost:8081/app/videogames/106
			.then()
				.statusCode(200)
				.log().body()
				.body("videoGame.id",equalTo("106"))
				.body("videoGame.name",equalTo("Pacman"));
		
	}
	
	@Test(priority=5)
	public void test_DeleteVideoGame()
	{
		Response res=
		given()
		.when()
			.delete(URI+"/videogames"+"/106")
		.then()
			.statusCode(200)
			.log().body()
			.extract().response();
		
		String jsonString=res.asString();
		Assert.assertEquals(jsonString.contains("Record Deleted Successfully"),true);
		
	}
	
	
}
