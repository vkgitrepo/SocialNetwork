package com.jpmc.sn.steps;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jpmc.sn.pojo.Comment;
import com.jpmc.sn.pojo.Post;
import com.jpmc.sn.pojo.User;
import io.restassured.http.Header;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class BaseStepDefinition {

    static final String BASE_URL = "https://jsonplaceholder.typicode.com/";
    static Response response;
    static RequestSpecification request;
    static ObjectMapper mapperObj = new ObjectMapper();
    static Header headerObj = new Header("Content-Type", "application/json");
    static Post[] postList;
    static User[] userList;
    static Comment[] commentList;

}
