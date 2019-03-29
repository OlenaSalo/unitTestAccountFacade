Feature: User with correct and incorrect attributes

  Scenario: Ability for new user login on page
    Given browser opened at http://localhost:8080 url
    When  user log in using userEmail and password:
      | email    | test.mail@gmail.com |
      | password | 0321                |
    Then home page should be displayed with account name:
      | firstName | Olenka      |

    Scenario: Inability for user with incorrect credential to login
      Given browser opened at http://localhost:8080 url
      When user with incorrect credential could't login:
        | email    | other.mail@gmail.com |
        | password | 0321                |

      Then home page shouldn't be displayed