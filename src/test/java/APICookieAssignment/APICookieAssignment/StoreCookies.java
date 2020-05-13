package APICookieAssignment.APICookieAssignment;

import java.util.HashMap;
import java.util.Map;
import org.testng.Assert;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

@Test
public class StoreCookies {
	String responseBody;
	JsonPath jsonResponse;
	Response response;
	String cookie_value;

	public void fetchCookie() throws Throwable {
		try {
			Map<String,String> requestBody = new HashMap<String,String>();
			requestBody.put("username", "test");
			requestBody.put("test", "test");
			response = given()
					.body(requestBody)
					.get("http://abc.com/auth")
					.then() .
					assertThat().statusCode(200).extract().response();
			System.out.println("These are response headers ==>> " + response.getHeaders());
	// if an application uses JSESSIONID cookie, then we can simply fetch and use that for storing session			
			System.out.println("This is detailed cookie information ==>>" + response.getDetailedCookies());
			//System.out.println("This is specific cookie" + response.getCookie(arg0));
			String body = response.getBody().asString();
			System.out.println("This is auth api response ==>" + body);
			cookie_value = response.getHeader("Set-Cookie");
			System.out.println("This is set-cookie value ===>> " + response.getHeader("Set-Cookie"));
		} catch (Exception e) {
			Assert.fail(e.getMessage());
			e.printStackTrace();
			System.out.println(e);
		}
	}

	public void useCookie() throws Throwable {
		try {
			response = given()
	//the header method can also be used to set cookie			
					//.header("cookie",cookie_value)
					.cookie("cookie",cookie_value).when()
					.get("http://abc.com/getEmployees")
					.then() .
					assertThat().statusCode(200).extract().response();
			String body = response.getBody().asString();
			System.out.println("This is get employee api response ===>> " + body);
	//the below code can be used to fetch any value from api response		
			//	jsonResponse = new JsonPath(responseBody);
		} catch (Exception e) {
			Assert.fail(e.getMessage());
			e.printStackTrace();
			System.out.println(e);
		}
	}


}
