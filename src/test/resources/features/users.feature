@User
Feature: User related scenarios

  Background: Configure URL and initialize request
    Given URL is configured and request initialized

  Scenario: Count number of users
    Given get list of all users
    Then total number of users returned is 10

  Scenario Outline: Verify user details for a given user
    Given user with a given id <user_id>
    Then verify <name>,<email>,<user_name>, <phone>, <city>, <company_bs>

    Examples:
      | user_id | name               | email                       | user_name  | phone               | city          | company_bs                             |
      | 4       | "Patricia Lebsack" | "Julianne.OConner@kory.org" | "Karianne" | "493-170-9623 x156" | "South Elvis" | "transition cutting-edge web services" |

  @Smoke @Add
  Scenario Outline: Add a new user
    Given user with details <name>, <username>, <email>, <city>, <company_name>
    Then create a user

    Examples:
      | name   | username    | email          | city        | company_name |
      | "venu" | "vkulkarni" | "vk@gmail.com" | "Bengaluru" | "JPM"        |

  @Update
  Scenario Outline: Update a user
    Given user with a given id <user_id>
    Then update the user with <updated_email>, <updated_city> and <updated_company>

    Examples:
      | user_id | updated_email  | updated_city | updated_company |
      | 3       | "vk@gmail.com" | "Mumbai"     | "JPM"           |

  @Delete
  Scenario Outline: Delete a user
    Given user with a given id <user_id>
    Then delete user

    Examples:
      | user_id |
      | 3       |

  @Search
  Scenario: Retrieve list of user names who have made posts with a particular word in the body
    Given get list of all users
    Then on searching in all posts with word "quo" in body returns following user list
      | Ervin Howell             |
      | Glenna Reichert          |
      | Kurtis Weissnat          |
      | Chelsey Dietrich         |
      | Clementina DuBuque       |
      | Mrs. Dennis Schulist     |
      | Clementine Bauch         |
      | Leanne Graham            |
      | Nicholas Runolfsdottir V |
      | Patricia Lebsack         |




