package com.epam.lab.accounts.accounts.facade;

import com.epam.lab.accounts.accounts.converter.AccountConverter;
import com.epam.lab.accounts.accounts.dto.AccountDTO;
import com.epam.lab.accounts.accounts.model.requests.CreateUpdateAccountRequest;
import com.epam.lab.accounts.accounts.repository.AccountModelRepository;
import com.epam.lab.accounts.accounts.service.AccountService;
import com.epam.lab.accounts.accounts.service.ErrorsService;
import com.epam.lab.accounts.accounts.service.SessionService;
import com.epam.lab.accounts.accounts.service.UserService;
import com.epam.lab.accounts.accounts.validator.CreateAccountRequestRequestValidator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.ReflectionTestUtils.setField;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AccountFacadeTest {

    @Autowired
    private AccountModelRepository accountModelRepository;

    @Mock
    private AccountService accountService;


    @Mock
    private SessionService sessionService;
    private AccountFacade accountFacade = new AccountFacade();
    private ErrorsService errorsService = new ErrorsService();
    private MockHttpSession httpSession = new MockHttpSession();
    private AccountConverter accountConverter = new AccountConverter();
    private UserService userService = new UserService();
    private CreateAccountRequestRequestValidator createAccountRequestValidator = new CreateAccountRequestRequestValidator();

    @Before
    public void resetSession () {
        setField(errorsService, "sessionService", sessionService);
        setField(createAccountRequestValidator, "accountService", accountService);
        setField(createAccountRequestValidator, "errorsService", errorsService);
        setField(accountFacade, "accountService" , accountService);
        setField(accountFacade, "accountConverter", accountConverter);
        setField(accountFacade, "sessionService", sessionService);
        setField(accountFacade, "userService", userService);
        setField(accountFacade, "createAccountRequestValidator", createAccountRequestValidator);
        setField(sessionService, "session", httpSession);
    }

    @org.junit.Test
    public void testRe() {
        String email = "tommy.johnson@gmail.com";
           String ggg=      "pharma-group";

        boolean b = accountModelRepository.existsAccountModelByCodeAndUserEmail(ggg, email);

        System.out.println();
    }

    @Test
    public void testHandleCreateAccount(){
        final CreateUpdateAccountRequest createUpdateAccountRequest = getDefaultCreateUpdateAccountRequest();
        final AccountDTO account = getAccountDtoForRequest(createUpdateAccountRequest);

        when(accountService.isAccountExistsForAccountCode(account.getCode())).thenReturn(false);
        when(accountService.getAccountForAccountCode(account.getCode())).thenReturn(account);

        accountFacade.handleCreateOrUpdateAccountRequest(createUpdateAccountRequest);

        verify(accountService).createAccountForCurrentUser(account);


    }

    @Test
    public void testHandleUpdateAccount()  {
        final CreateUpdateAccountRequest createUpdateAccountRequest = getDefaultCreateUpdateAccountRequest();
        final AccountDTO account = getAccountDtoForRequest(createUpdateAccountRequest);

        when(accountService.isAccountExistsForAccountCode(account.getCode())).thenReturn(true);
        when(accountService.getAccountForAccountCode(account.getCode())).thenReturn(account);

        accountFacade.handleCreateOrUpdateAccountRequest(createUpdateAccountRequest);

        if(!accountService.getAccountForAccountCode(account.getCode()).equals(account)) {
        verify(accountService).updateAccount(account);
        }
    }

    private AccountDTO getAccountDtoForRequest(CreateUpdateAccountRequest createUpdateAccountRequest) {
        final AccountDTO accountDTO = new AccountDTO();
        accountDTO.setName(createUpdateAccountRequest.getAccountName());
        accountDTO.setImg(createUpdateAccountRequest.getAccountImage());
        accountDTO.setCode(createUpdateAccountRequest.getAccountCode());
        accountDTO.setBalance(createUpdateAccountRequest.getAccountBalance());
        return accountDTO;
    }

    public CreateUpdateAccountRequest getDefaultCreateUpdateAccountRequest(){
        final CreateUpdateAccountRequest createUpdateAccountRequest = new CreateUpdateAccountRequest();
        createUpdateAccountRequest.setAccountName("Some name");
        createUpdateAccountRequest.setAccountImage("https://www.google.com.ua/url?sa=i&source=images&cd=&cad=rja&uact=8&ved=2ahUKEwjw7fmE06DhAhUpxMQBHerzDSUQjRx6BAgBEAU&url=%2Furl%3Fsa%3Di%26source%3Dimages%26cd%3D%26ved%3D%26url%3Dhttps%253A%252F%252Fwww.pexels.com%252Fsearch%252Fbeauty%252F%26psig%3DAOvVaw1wdbg45efaBWACmEbtGMvV%26ust%3D1553718586933315&psig=AOvVaw1wdbg45efaBWACmEbtGMvV&ust=1553718586933315");
        createUpdateAccountRequest.setAccountCode("7933fdkjh");
        createUpdateAccountRequest.setAccountBalance(BigDecimal.ONE);
        return createUpdateAccountRequest;
    }

}
