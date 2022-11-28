package br.ce.wcaquino.tasks.apitest;

import org.hamcrest.CoreMatchers;
import org.junit.BeforeClass;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class APITest {
	@BeforeClass
	public static void setup() {
		RestAssured.baseURI = "http://localhost:8001/tasks-backend";
	}
	
	
	@Test
	public void shouldReturnTasks() {
		RestAssured.given()
		.when()
		.get("/todo")
		.then()
		.statusCode(200);
	}
	
	@Test
	public void shouldAddTaskSuccessful() {
		RestAssured.given()
		.body("{\"task\": \"Test\", \"dueDate\": \"2030-01-01\"}")
		.contentType(ContentType.JSON)
		.when()
		.post("/todo")
		.then()
		.statusCode(201);
	}
	
	@Test
	public void shouldNotAddInvalidTask() {
		RestAssured.given()
		.body("{\"task\": \"Test\", \"dueDate\": \"2010-01-01\"}")
		.contentType(ContentType.JSON)
		.when()
		.post("/todo")
		.then()
		.statusCode(400)
		.body("message", CoreMatchers.is("Due date must not be in past"));
	}
}
