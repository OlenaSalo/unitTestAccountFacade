package com.epam.lab.accounts.e2e.pageobject;

import com.epam.lab.accounts.accounts.model.requests.UserLoginRequest;
import com.epam.lab.accounts.e2e.config.PageDriver;
import com.epam.lab.accounts.e2e.config.PageElement;
import org.openqa.selenium.support.FindBy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MyLoginPage extends PageObject{

    @FindBy(xpath = "//*[@id='login.email']")
    private PageElement email;

    @FindBy(xpath = "//*[@id='login.password']")
    private PageElement psw;

    @FindBy(xpath = "//*[@id='login.submit']/span")
    private PageElement submitButton;



    @Autowired
    public MyLoginPage(PageDriver pageDriver) {
        super(pageDriver);
    }

    public void logIn(final UserLoginRequest request){
        email.sendKeys(request.getEmail());
        psw.sendKeys(request.getPassword());
        submitButton.submit();
    }


}
