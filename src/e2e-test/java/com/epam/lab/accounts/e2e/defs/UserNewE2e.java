package com.epam.lab.accounts.e2e.defs;

import com.epam.lab.accounts.accounts.dto.UserDTO;
import com.epam.lab.accounts.accounts.model.requests.UserLoginRequest;
import com.epam.lab.accounts.e2e.pageobject.MyAccountPage;
import com.epam.lab.accounts.e2e.pageobject.MyLoginPage;
import com.epam.lab.accounts.integrationtests.config.CucumberUtils;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.cucumber.datatable.DataTable;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;

public class UserNewE2e {


    @Autowired
    private MyAccountPage accountPage;

    @Autowired
    private MyLoginPage loginPage;



    @When("user log in using userEmail and password:")
    public void userSubmitLogIn(final DataTable dataTable){
        final UserLoginRequest userLoginRequest = CucumberUtils.toObject(dataTable, UserLoginRequest.class);
        loginPage.logIn(userLoginRequest);
    }

    @Then("home page should be displayed with account name")
    public void submitSuccessfulLogIn(final DataTable dataTable){
        final UserDTO user = CucumberUtils.toObject(dataTable, UserDTO.class);
        Assert.assertEquals(accountPage.returnAccountName(), "Hi, " + user.getFirstName());
    }

    @When("user with incorrect credential could't login:")
        public void userSubmitIncorrectLogIn( final DataTable dataTable){
            final UserLoginRequest userLoginRequest = CucumberUtils.toObject(dataTable, UserLoginRequest.class);
            loginPage.logIn(userLoginRequest);
        }

        @Then("home page shouldn't be displayed")
        public void verifyIncorrectUserAttributes(){
        Assert.assertTrue(accountPage.returnAccount());
        }
    }


