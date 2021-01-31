@Post
Feature: Posts related scenarios

  Background: Configure URL and initialize request
    Given URL is configured and request initialized

  Scenario Outline: Count number of posts for a given user
    Then verify number of posts for a given <user_id> are <post_count>

    Examples:
      | user_id | post_count |
      | 1       | 10         |

  @Add
  Scenario Outline: Create a post for a given user
    Given user id as <user_id>, title as <title> and body as <body>
    Then create a post

    Examples:
      | user_id | title       | body       |
      | 1       | "abc_title" | "abc_body" |

  @Smoke @Update
  Scenario Outline: Update an existing post
    Given post with id <post_id>
    Then update the post with <updated_title> and <updated_body>

    Examples:
      | post_id | updated_title | updated_body |
      | 5       | "xyz_title"   | "xyz_body"   |

  @Delete
  Scenario Outline: Delete an existing post
    Given post with id <post_id>
    Then delete post

    Examples:
      | post_id |
      | 7       |

  Scenario Outline: Verify number of posts by user name
    Given get list of all posts
    Given get list of all users
    Then verify for each <user_name> total number of posts made <post_count>

    Examples:
      | user_name   | post_count |
      | "Bret"      | 10         |
      | "Antonette" | 10         |
      | "Samantha"  | 10         |
      | "Karianne"  | 10         |
