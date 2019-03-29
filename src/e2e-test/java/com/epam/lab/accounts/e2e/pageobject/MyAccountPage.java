package com.epam.lab.accounts.e2e.pageobject;

import com.epam.lab.accounts.e2e.config.PageDriver;
import com.epam.lab.accounts.e2e.config.PageElement;
import org.openqa.selenium.support.FindBy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MyAccountPage extends PageObject {

    @FindBy(xpath = "//span[@class='mdl-layout-title']")
    private PageElement account;

    @Autowired
    public MyAccountPage(PageDriver pageDriver) {
        super(pageDriver);
    }

    public String returnAccountName(){
        return account.getText();
    }

    public boolean returnAccount(){return account.isExists();}

}
