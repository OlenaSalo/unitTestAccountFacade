package com.epam.lab.accounts.integrationtests.defs;

import com.epam.lab.accounts.accounts.LoginPageController;
import com.epam.lab.accounts.accounts.dto.UserDTO;
import com.epam.lab.accounts.accounts.model.requests.UserLoginRequest;
import com.epam.lab.accounts.accounts.service.SessionService;
import com.epam.lab.accounts.accounts.service.UserService;
import com.epam.lab.accounts.integrationtests.config.CucumberUtils;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.cucumber.datatable.DataTable;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

public class UserNew {
    @Autowired
    private UserService userService;
    @Autowired
    private SessionService sessionService;
    @Autowired
    private LoginPageController loginPageController;


    @When("add new user login request with new attributes:")
    public void addUserLoginRequestWithNewAttributes(final DataTable dataTable) {
        final UserLoginRequest userLoginRequest = CucumberUtils.toObject(dataTable, UserLoginRequest.class);
        loginPageController.onLoginRequest(userLoginRequest);
    }

    @Then("session contains info about new user:")
    public void sessionContainsInfoAboutNewUser(final DataTable dataTable) {
        final UserDTO expectedSession = CucumberUtils.toObject(dataTable, UserDTO.class);
        final UserDTO actualSession = sessionService.getSessionUser();
        assertEquals(expectedSession.getEmail(), actualSession.getEmail());
        assertTrue(expectedSession.getFirstName().equals(actualSession.getFirstName()));
        assertTrue(expectedSession.getLastName().equals(actualSession.getLastName()));
        assertTrue(expectedSession.getUserRole().equals(actualSession.getUserRole()));
    }

    @Given("new user (.+) logs in into app")
    public void newUserLogsInIntoApp(final String email) {
        final UserDTO user = userService.getUserForEmail(email);
        sessionService.setSessionUser(user);
    }

    @When("send user login request with incorrect attributes:")
    public void sendUserLoginRequestWithIncorrectAttributes(final DataTable dataTable) {
        final UserLoginRequest userLoginRequest = CucumberUtils.toObject(dataTable, UserLoginRequest.class);
        loginPageController.onLoginRequest(userLoginRequest);
    }

    @Then("session contains incorrect info about session user:")
    public void sessionContainsIncorrectInfoAboutUser(final DataTable dataTable) {
        final UserDTO expectedSession = CucumberUtils.toObject(dataTable, UserDTO.class);
        final UserDTO actualSession = sessionService.getSessionUser();
        assertFalse(expectedSession.getEmail().equals(actualSession.getEmail()));
    }

    @Given("user (.+) no logs in into app")
    public void userNoLogsInIntoApp(final String email) {
        final UserDTO user = userService.getUserForEmail(email);
        sessionService.setSessionUser(user);
    }


}
