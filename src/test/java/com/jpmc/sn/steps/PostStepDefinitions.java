package com.jpmc.sn.steps;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jpmc.sn.pojo.Post;
import com.jpmc.sn.pojo.User;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.junit.Assert;

import java.util.logging.Logger;

public class PostStepDefinitions extends BaseStepDefinition {

    private final static Logger LOGGER = Logger.getLogger(PostStepDefinitions.class.getName());
    private Post tempPost;
    private Post updatedPost;

    @Then("verify number of posts for a given {int} are {int}")
    public void verify_number_of_posts_for_a_given_are(int user_id, int post_count) {
        postList = request.get("/users/" + user_id + "/posts").as(Post[].class);
        Assert.assertEquals(post_count, postList.length);
    }

    @Given("user id as {int}, title as {string} and body as {string}")
    public void user_id_as_title_as_and_body_as(int user_id, String title, String body) {
        tempPost = new Post(user_id, null, title, body);
    }

    @Then("create a post")
    public void create_a_post() throws JsonProcessingException {
        response = request.header(headerObj).body(mapperObj.writeValueAsString(tempPost)).post(BASE_URL + "posts");
        Post newPost = response.as(Post.class);
        LOGGER.info("Create Post | Response status code  : " + response.getStatusCode());
        Assert.assertEquals(201, response.getStatusCode());
        Assert.assertEquals(tempPost.getTitle(), newPost.getTitle());
        Assert.assertEquals(tempPost.getBody(), newPost.getBody());
        Assert.assertNotNull(newPost.getId());
        LOGGER.info("New Post created :" + newPost.toString());
    }

    @Then("update the post with {string} and {string}")
    public void update_the_post_with_and(String updatedTitle, String updatedBody) throws JsonProcessingException {
        tempPost.setTitle(updatedTitle);
        tempPost.setBody(updatedBody);
        response = request.header(headerObj).body(mapperObj.writeValueAsString(tempPost)).put(BASE_URL + "posts/" + tempPost.getId());
        LOGGER.info("Update Post | Response status code  : " + response.getStatusCode());
        Assert.assertEquals(200, response.getStatusCode());
        updatedPost = response.as(Post.class);
        LOGGER.info("Updated Post created :" + updatedPost.toString());
        Assert.assertEquals(updatedTitle, updatedPost.getTitle());
        Assert.assertEquals(updatedBody, updatedPost.getBody());
    }

    @Given("post with id {int}")
    public void post_with_id(int post_id) {
        tempPost = new Post(null, post_id, null, null);
    }

    @Then("delete post")
    public void delete_post() throws JsonProcessingException {
        response = request.header(headerObj).body(mapperObj.writeValueAsString(tempPost)).delete(BASE_URL + "posts/" + tempPost.getId());
        LOGGER.info("Delete Post | Response status code  : " + response.getStatusCode());
        Assert.assertEquals(200, response.getStatusCode());
        updatedPost = response.as(Post.class);
        Assert.assertNull(updatedPost.getId());
    }

    @Given("get list of all posts")
    public void get_list_of_all_posts() {
        postList = request.get("/posts").as(Post[].class);
    }

    @Then("verify for each {string} total number of posts made {int}")
    public void verify_for_each_total_number_of_posts_made(String userName, int ExpPostCount) {

        int userId = 0;
        int ActualPostCount = 0;
        for (User user : userList)
            if (user.getUsername().equals(userName)) {
                userId = user.getId();
                LOGGER.info("User Name is : " + user.getUsername());
            }

        for (Post post : postList) {
            if (post.getUserId().equals(userId)) ActualPostCount++;
        }
        LOGGER.info("Actual Post count is :" + ActualPostCount);

        Assert.assertEquals(ExpPostCount, ActualPostCount);

    }

}
