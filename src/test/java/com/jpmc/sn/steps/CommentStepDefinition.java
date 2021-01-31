package com.jpmc.sn.steps;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jpmc.sn.pojo.Comment;
import com.jpmc.sn.pojo.Post;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.junit.Assert;

import java.util.logging.Logger;

public class CommentStepDefinition extends BaseStepDefinition {

    private final static Logger LOGGER = Logger.getLogger(PostStepDefinitions.class.getName());
    private Comment tempComment;
    private Comment updatedComment;

    @Then("verify number of comments for a given post with id {int} are {int}")
    public void verify_number_of_comments_for_a_given_post_with_id_are(int post_id, int comment_count) {
        commentList = request.get("/posts/" + post_id + "/comments").as(Comment[].class);
        Assert.assertEquals(comment_count, commentList.length);
    }

    // This method caters for both scenarios - search for comments by user or posts
    @Then("verify number of comments for a given {string} with id {int} are {int}")
    public void verify_number_of_comments_for_a_given_with_id_are(String EntityType, int id, int count) {

        int j = 0;

        if (EntityType.equals("post")) {
            commentList = request.get("/posts/" + id + "/comments").as(Comment[].class);
            Assert.assertEquals(count, commentList.length);
        } else if (EntityType.equals("user")) {
            postList = request.get("/users/" + id + "/posts").as(Post[].class);
            for (Post post : postList) {
                commentList = request.get("/posts/" + post.getId() + "/comments").as(Comment[].class);
                j = j + commentList.length;
            }
            Assert.assertEquals(count, j);
        }
    }


    @Given("post id as {int}, name as {string}, email as {string} and body as {string}")
    public void post_id_as_name_as_email_as_and_body_as(int post_id, String name, String email, String body) {
        tempComment = new Comment(post_id, null, name, email, body);
    }

    @Then("create a comment")
    public void create_a_comment() throws JsonProcessingException {
        response = request.header(headerObj).body(mapperObj.writeValueAsString(tempComment)).post(BASE_URL + "comments");
        Comment newComment = response.as(Comment.class);
        LOGGER.info("Create Comment | Response status code  : " + response.getStatusCode());
        Assert.assertEquals(201, response.getStatusCode());
        Assert.assertEquals(tempComment.getName(), newComment.getName());
        Assert.assertEquals(tempComment.getEmail(), newComment.getEmail());
        Assert.assertEquals(tempComment.getBody(), newComment.getBody());
        Assert.assertNotNull(newComment.getId());
        LOGGER.info("New Comment created :" + newComment.toString());
    }

    @Given("comment with id {int}")
    public void comment_with_id(int comment_id) {
        tempComment = new Comment(null, comment_id, null, null, null);
    }

    @Then("update the comment with {string}, {string} and {string}")
    public void update_the_comment_with_and(String updatedName, String updatedEmail, String updatedBody) throws JsonProcessingException {
        tempComment.setName(updatedName);
        tempComment.setEmail(updatedEmail);
        tempComment.setBody(updatedBody);

        response = request.header(headerObj).body(mapperObj.writeValueAsString(tempComment)).put(BASE_URL + "comments/" + tempComment.getId());
        LOGGER.info("Update Comment | Response status code  : " + response.getStatusCode());
        Assert.assertEquals(200, response.getStatusCode());
        updatedComment = response.as(Comment.class);
        LOGGER.info("Updated Comment created :" + updatedComment.toString());
        Assert.assertEquals(updatedName, updatedComment.getName());
        Assert.assertEquals(updatedEmail, updatedComment.getEmail());
        Assert.assertEquals(updatedBody, updatedComment.getBody());

    }

    @Then("delete comment")
    public void delete_comment() throws JsonProcessingException {
        response = request.header(headerObj).body(mapperObj.writeValueAsString(tempComment)).delete(BASE_URL + "comments/" + tempComment.getId());
        LOGGER.info("Delete Comment | Response status code  : " + response.getStatusCode());
        Assert.assertEquals(200, response.getStatusCode());
        updatedComment = response.as(Comment.class);
        Assert.assertNull(updatedComment.getId());
    }


}
