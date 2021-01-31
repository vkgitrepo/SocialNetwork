package com.jpmc.sn.steps;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jpmc.sn.pojo.Address;
import com.jpmc.sn.pojo.Company;
import com.jpmc.sn.pojo.Post;
import com.jpmc.sn.pojo.User;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.junit.Assert;

import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.logging.Logger;

public class UserStepDefinitions extends BaseStepDefinition {

    private final static Logger LOGGER = Logger.getLogger(UserStepDefinitions.class.getName());
    private Address tempAddr = new Address();
    private Company tempCompany = new Company();
    private User tempUser = new User();
    private User updatedUser;

    @Given("get list of all users")
    public void get_list_of_all_users() {
        userList = request.get("/users").as(User[].class);
        //for (User user : userList) LOGGER.info(user.toString());
    }

    @Then("total number of users returned is {int}")
    public void total_number_of_users_returned_is(int expSize) {
        Assert.assertEquals(expSize, userList.length);
    }

    @Given("user with a given id {int}")
    public void user_with_a_given_id(int userId) {
        tempUser = request.get("/users/" + userId).as(User.class);
        LOGGER.info(tempUser.toString());
    }

    @Then("verify {string},{string},{string}, {string}, {string}, {string}")
    public void verify(String name, String email, String user_name, String phone, String city, String company_bs) {
        Assert.assertEquals(name, tempUser.getName());
        Assert.assertEquals(email, tempUser.getEmail());
        Assert.assertEquals(user_name, tempUser.getUsername());
        Assert.assertEquals(phone, tempUser.getPhone());
        Assert.assertEquals(city, tempUser.getAddress().getCity());
        Assert.assertEquals(company_bs, tempUser.getCompany().getBs());
    }

    @Given("user with details {string}, {string}, {string}, {string}, {string}")
    public void user_with_details(String name, String userName, String email, String city, String company) {
        tempUser = new User();

        tempAddr.setCity(city);
        tempCompany.setName(company);

        tempUser.setName(name);
        tempUser.setUsername(userName);
        tempUser.setEmail(email);
        tempUser.setAddress(tempAddr);
        tempUser.setCompany(tempCompany);
    }

    @Then("create a user")
    public void create_a_user() throws JsonProcessingException {
        response = request.header(headerObj).body(mapperObj.writeValueAsString(tempUser)).post(BASE_URL + "users");
        User newUser = response.as(User.class);
        LOGGER.info("Create User | Response status code  : " + response.getStatusCode());
        Assert.assertEquals(201, response.getStatusCode());

        Assert.assertEquals(tempUser.getName(), newUser.getName());
        Assert.assertEquals(tempUser.getUsername(), newUser.getUsername());
        Assert.assertEquals(tempUser.getEmail(), newUser.getEmail());

        Assert.assertEquals(tempUser.getAddress().getCity(), newUser.getAddress().getCity());
        Assert.assertEquals(tempUser.getCompany().getName(), newUser.getCompany().getName());

        Assert.assertNotNull(newUser.getId());

        LOGGER.info("New User created :" + newUser.toString());
    }

    @Then("update the user with {string}, {string} and {string}")
    public void update_the_user_with_and_JPM(String updatedEmail, String updatedCity, String updatedCompany) throws JsonProcessingException {

        tempAddr = tempUser.getAddress();
        tempCompany = tempUser.getCompany();
        tempAddr.setCity(updatedCity);
        tempCompany.setName(updatedCompany);
        tempUser.setAddress(tempAddr);
        tempUser.setCompany(tempCompany);

        tempUser.setEmail(updatedEmail);

        response = request.header(headerObj).body(mapperObj.writeValueAsString(tempUser)).put(BASE_URL + "users/" + tempUser.getId());
        LOGGER.info("Update User | Response status code  : " + response.getStatusCode());
        Assert.assertEquals(200, response.getStatusCode());
        updatedUser = response.as(User.class);
        LOGGER.info("Updated User created :" + updatedUser.toString());
        Assert.assertEquals(updatedEmail, updatedUser.getEmail());
        Assert.assertEquals(updatedCity, updatedUser.getAddress().getCity());
        Assert.assertEquals(updatedCompany, updatedUser.getCompany().getName());
    }

    @Then("delete user")
    public void delete_user() throws JsonProcessingException {
        response = request.header(headerObj).body(mapperObj.writeValueAsString(tempUser)).delete(BASE_URL + "users/" + tempUser.getId());
        LOGGER.info("Delete User | Response status code  : " + response.getStatusCode());
        Assert.assertEquals(200, response.getStatusCode());
        updatedUser = response.as(User.class);
        Assert.assertNull(updatedUser.getId());
    }

    @Then("on searching in all posts with word {string} in body returns following user list")
    public void on_searching_in_all_posts_with_word_in_body_returns_following_user_list(String searchString, DataTable dataTable) {

        SortedSet<String> userNameActual_Set = new TreeSet<>();

        List<String> expUserName_List = dataTable.asList();
        SortedSet<String> userNameExp_Set = new TreeSet<>(expUserName_List);
        LOGGER.info("Expected list of users : " + userNameExp_Set);

        postList = request.get("/posts").as(Post[].class);
        for (Post post : postList) {
            if (post.getBody().contains(searchString)) {
                for (User user : userList) {
                    if (user.getId().equals(post.getUserId()))
                        userNameActual_Set.add(user.getName());
                }
            }
        }
        LOGGER.info("Actual list of users : " + userNameActual_Set);
        Assert.assertEquals(userNameExp_Set, userNameActual_Set);
    }
}
