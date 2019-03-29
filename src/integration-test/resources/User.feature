Feature: User operations feature

  Scenario: Ability for user to register
    When send user registration request with attributes:
      | firstName | Oleh                 |
      | lastName  | Maksymuk             |
      | email     | oleg030992@yandex.ru |
      | password  | Qwerty123456         |
    Then user model exists in database with attributes:
      | firstName | Oleh                 |
      | lastName  | Maksymuk             |
      | email     | oleg030992@yandex.ru |
      | password  | Qwerty123456         |

  Scenario: Ability for user to log in
    When send user login request with attributes:
      | email    | tommy.johnson@gmail.com |
      | password | 1111                    |
    Then session contains info about session user:
      | firstName | Tommy                   |
      | lastName  | Johnson                 |
      | email     | tommy.johnson@gmail.com |
      | userRole  | USER                    |

  Scenario: Ability for new user to log with new credential
    When add new user login request with new attributes:
      | email    | test.mail@gmail.com |
      | password | 0321                    |
    Then session contains info about new user:
      | firstName | Olenka                   |
      | lastName  | Salo                 |
      | email     | test.mail@gmail.com |
      | userRole  | USER                    |

  Scenario: Inability for  user to log with incorrect credential
    When send user login request with incorrect attributes:
      | email    | test.maill@gmail.com |
      | password | 0321                    |
    Then session contains incorrect info about session user:
      | firstName | Olenka                   |
      | lastName  | Salo                 |
      | email     | salo.olenkaA@gmail.com |
      | userRole  | USER                    |

