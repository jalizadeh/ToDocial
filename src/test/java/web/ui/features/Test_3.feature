Feature: User Login & Registration

  Scenario: Valid username & email
    Given navigate to page "login"
    When fill username field with "admin"
    When fill password field with "12345"
    When click login button

    # redirected to Home page
    Then check if has full access to top menu items