@Comment
Feature: Comments related scenarios

  Background: Configure URL and initialize request
    Given URL is configured and request initialized

  @Add
  Scenario Outline: Create a comment for a given post
    Given post id as <post_id>, name as <name>, email as <email> and body as <body>
    Then create a comment

    Examples:
      | post_id | name   | email            | body       |
      | 5       | "Ravi" | "ravi@gmail.com" | "Hi there" |

  @Update
  Scenario Outline: Update an existing comment
    Given comment with id <comment_id>
    Then update the comment with <updated_name>, <updated_email> and <updated_body>

    Examples:
      | comment_id | updated_name   | updated_email            | updated_body        |
      | 5          | "Ravi_updated" | "ravi_updated@gmail.com" | "Hi there..updated" |

  @Smoke @Delete
  Scenario Outline: Delete an existing comment
    Given comment with id <comment_id>
    Then delete comment

    Examples:
      | comment_id |
      | 7          |

  Scenario Outline: Count number of comments for a given post
    Then verify number of comments for a given "post" with id <post_id> are <comment_count>

    Examples:
      | post_id | comment_count |
      | 1       | 5             |

  Scenario Outline: Count number of comments for a given user
    Then verify number of comments for a given "user" with id <user_id> are <comment_count>

    Examples:
      | user_id | comment_count |
      | 1       | 50            |

