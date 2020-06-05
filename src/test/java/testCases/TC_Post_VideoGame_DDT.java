package testCases;

import static io.restassured.RestAssured.given;
import java.io.IOException;
import java.util.HashMap;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import helper.VideoGame;
import io.restassured.response.Response;
import utilities.XLUtils;
import static org.hamcrest.Matchers.equalTo;

public class TC_Post_VideoGame_DDT {

	public int port=8081;
	public String URI="http://localhost:"+port+"/app";
	
	@Test(priority=1,dataProvider="dp")
	public void test_addNewVideoGame(String id,String name,String releaseDate,String reviewScore,String category,String rating)
	{
		//using HashMap
		/*HashMap data=new HashMap();
		data.put("id", id);
		data.put("name", name);
		data.put("releaseDate", releaseDate);
		data.put("reviewScore", reviewScore);
		data.put("category", category);
		data.put("rating", rating);*/
		
		//using pojo class
		VideoGame data=new VideoGame();
		data.setId(Integer.parseInt(id));
		data.setName(name);
		data.setReleaseDate(releaseDate); 
		data.setReviewScore(Integer.parseInt(reviewScore));
		data.setCategory(category);
		data.setRating(rating);
		
		Response res=
		given()
			.contentType("application/json")
			.body(data)
		.when()
			.post(URI+"/videogames")
		.then()
			.statusCode(200)
			//.log().body()
			.extract().response();
		
		String jsonString=res.asString();
		Assert.assertEquals(jsonString.contains("Record Added Successfully"),true);
	}
	
	@Test(priority=2,dataProvider="dp")
	public void test_getVideoGame(String vid,String name,String releaseDate,String reviewScore,String category,String rating)
	{
		given()
			.pathParam("id",vid)
		.when()
			.get(URI+"/videogames/{id}")
		.then()
			.statusCode(200)
			.body("videoGame.id", equalTo(vid))
			.body("videoGame.name", equalTo(name));
	}
	
	
	@DataProvider(name="dp")
	public String[][] getData() throws IOException
	{
		String path=System.getProperty("user.dir")+"/testData/VideoGameAPIData.xlsx";
		
		int rownum=XLUtils.getRowCount(path, "Sheet1");
		int colcount=XLUtils.getCellCount(path,"Sheet1",1);
		
		String apidata[][]=new String[rownum][colcount];
		
		for(int i=1;i<=rownum;i++)
		{
			for(int j=0;j<colcount;j++)
			{
				apidata[i-1][j]=XLUtils.getCellData(path,"Sheet1",i,j);
			}
		}
		
		return apidata;
	}
	
	
}
