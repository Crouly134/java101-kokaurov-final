package ru.ranepa.bank.application;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import ru.ranepa.bank.application.port.AccountRepository;
import ru.ranepa.bank.domain.Account;
import ru.ranepa.bank.domain.CreditAccount;
import ru.ranepa.bank.domain.SavingsAccount;
import ru.ranepa.bank.infrastructure.InMemoryAccountRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AccountServiceTest {
    private AccountRepository accountRepository;
    private AccountService accountService;

    @BeforeEach
    void setUp(){
        accountRepository = new InMemoryAccountRepository();
        accountService = new AccountService(accountRepository);
    }

    @Test
    void openSavingsAccount_success(){
        boolean result = accountService.openSavingsAccount("A1","Ivan",100.0,5.0);
        assertTrue(result);
        assertEquals(1, accountService.findAll().size());
    }
    @Test
    void openCreditAccount_success(){
        boolean result = accountService.openCreditAccount("C1","Petr",50.0,100.0,10.0);
        assertTrue(result);
        assertEquals(1, accountService.findAll().size());
    }
    @Test
    void openAccount_withEmptyOwner_returnsFalse(){
        assertFalse(accountService.openSavingsAccount("A1"," ",100.0,5.0));
        assertEquals(0, accountService.findAll().size());
    }
    @Test
    void openAccount_withNegativeBalance_returnsFalse(){
        assertFalse(accountService.openSavingsAccount("A1"," ",-100.0,5.0));
        assertEquals(0, accountService.findAll().size());
    }
    @Test
    void openAccount_withDuplicateNumber_returnsFalse(){
        assertTrue(accountService.openSavingsAccount("A1","Ivan",100.0,5.0));
        assertFalse(accountService.openCreditAccount("A1","Petr",50.0,1000.0,10.0));
    assertEquals(1,accountService.findAll().size());
    }
    @Test
    void openAccount_shortWay_generatesNumber(){
        assertTrue(accountService.openAccount("Ivan"));
       assertEquals(1, accountService.findAll().size());
        assertNotNull(accountService.findAll().get(0).getNumber());
    }

    @Test
    void deposit_increasesBalance(){
        accountService.openSavingsAccount("A1","Ivan",100.0,5.0);
       assertTrue(accountService.deposit("A1",50.0));
        assertEquals(150.0,accountService.findAll().get(0).getBalance(),0.001);
    }
    @Test
    void deposit_withNonPositiveAmount_returnsFalse(){
        accountService.openSavingsAccount("A1","Ivan",100.0,5.0);
        assertFalse(accountService.deposit("A1",0.0));
        assertFalse(accountService.deposit("A1",-5.0));
        assertEquals(100.0,accountService.findAll().get(0).getBalance(),0.001);
    }
    @Test
    void deposit_toUnknownAccount_returnsFalse(){
        accountService.openSavingsAccount("A1","Ivan",100.0,5.0);
        assertFalse(accountService.deposit("Nope",50.0));
    }
    @Test
    void withdraw_success(){
        accountService.openSavingsAccount("A1","Ivan",100.0,5.0);
        assertTrue(accountService.withdraw("A1",40.0));
        assertEquals(60.0,accountService.findAll().get(0).getBalance(),0.001);
    }
    @Test
    void withdraw_moreThanBalance_returnsFalse_andBalanceUnchanged(){
        accountService.openSavingsAccount("A1","Ivan",100.0,5.0);
        assertFalse(accountService.withdraw("A1",0.0));
        assertEquals(100.0,accountService.findAll().get(0).getBalance(),0.001);
    }
    @Test
    void withdraw_withNonPositiveAmount_returnsFalse(){
        accountService.openSavingsAccount("A1","Ivan",100.0,5.0);
        assertFalse(accountService.withdraw("A1",0.0));
        assertFalse(accountService.withdraw("A1",-10.0));
    }

    @Test
    void findByOwner_returnsOnlyMatchingAccounts(){
        accountService.openSavingsAccount("A1","Ivan",100.0,5.0);
        accountService.openSavingsAccount("A2","Petr",200.0,5.0);
        accountService.openCreditAccount("C1","Ivan",50.0,1000.0,10.0);

        List<Account> found = accountService.findByOwner("Ivan");
        assertEquals(2,found.size());
    }
    @Test
    void findByOwner_withUnknownOwner_returnsEmptyList(){
        accountService.openSavingsAccount("A1","Ivan",100.0,5.0);
        assertTrue(accountService.findByOwner("Nobody").isEmpty());
    }
    @Test
    void findByOwner_withEmptyName_returnsEmptyList(){
        accountService.openSavingsAccount("A1","Ivan",100.0,5.0);
        assertTrue(accountService.findByOwner("  ").isEmpty());
    }
    @Test
    void findWithNonZeroBalance_skipZeroBalance(){
        accountService.openSavingsAccount("A1","Ivan",100.0,5.0);
        accountService.openSavingsAccount("A2","Petr",0.0,5.0);
        List<Account> result = accountService.findWithNonZeroBalance();
        assertEquals(1,result.size());
        assertEquals("A1",result.get(0).getNumber());
    }

    @Test
    void transfer_success_changesBothBalances_andKeepsTotal(){
        accountService.openSavingsAccount("A1","Ivan",100.0,5.0);
        accountService.openCreditAccount("C1","Petr",50.0,1000.0,10.0);
        double before = accountService.totalBalance();
        assertTrue(accountService.transfer("A1","C1",30.0));
        assertEquals(70.0,accountService.findByOwner("Ivan").get(0).getBalance(),0.001);
        assertEquals(80.0,accountService.findByOwner("Petr").get(0).getBalance(),0.001);
        assertEquals(before, accountService.totalBalance(),0.001);
    }
    @Test
    void transfer_toSameAccount_returnsFalse(){
        accountService.openSavingsAccount("A1","Ivan",100.0,5.0);
        assertFalse(accountService.transfer("A1","A1",10.0));
       assertEquals(100.0,accountService.findAll().get(0).getBalance(),0.001);
    }
    @Test
    void transfer_toUnknownAccount_returnsFalse(){
        accountService.openSavingsAccount("A1","Ivan",100.0,5.0);
        assertFalse(accountService.transfer("A1","NOPE",10.0));
        assertEquals(100.0,accountService.findAll().get(0).getBalance(),0.001);
    }
    @Test
    void transfer_fromUnknownAccount_returnsFalse(){
        accountService.openSavingsAccount("A1","Ivan",100.0,5.0);
        assertFalse(accountService.transfer("NOPE","A1",10.0));
    }
    @Test
    void transfer_moreThanBalance_returnsFalse(){
        accountService.openSavingsAccount("A1","Ivan",100.0,5.0);
        accountService.openSavingsAccount("A2","Petr",0.0,5.0);
        assertFalse(accountService.transfer("A1","A2",150.0));
        assertEquals(100.0,accountService.findByOwner("Ivan").get(0).getBalance(),0.001);
        assertEquals(0.0,accountService.findByOwner("Petr").get(0).getBalance(),0.001);

    }

    @Test
    void totalBalance_sumsAllAcccounts(){
        accountService.openSavingsAccount("A1","Ivan",100.0,5.0);
        accountService.openCreditAccount("C1","Petr",50.0,1000.0,10.0);
        assertEquals(150.0,accountService.totalBalance(),0.001);
    }
    @Test
    void accountsCount_growsOnOpen(){
        int before = accountService.accountsCount();
        accountService.openSavingsAccount("A1","Ivan",100.0,5.0);
        accountService.openAccount("Petr");
        assertEquals(before + 2,accountService.accountsCount());
    }
    @Test
    void totalBalance_onEmptyRepository_isZero(){
        assertEquals(0.0,accountService.totalBalance(),0.001);
    }
    @Test
    void largestAccount_returnsAccountWithMaxBalance(){
        accountService.openSavingsAccount("A1","Ivan",100.0,5.0);
        accountService.openSavingsAccount("A2","Petr",300.0,5.0);
        accountService.openSavingsAccount("A3","Oleg",200.0,5.0);
        Account largest = accountService.largestAccount();
        assertNotNull(largest);
        assertEquals("A2",largest.getNumber());
    }
    @Test
    void largestAccount_onEmptyRepository_isNull(){
        assertNull(accountService.largestAccount());
    }

    @Test
    void describe_differsForSavingsAndCredit(){
        Account savings = new SavingsAccount("S1","Ivan",100.0,5.0);
        Account credit = new CreditAccount("C1","Petr",50.0,1000.0,10.0);

        assertTrue(savings.describe().contains("Savings"));
        assertTrue(credit.describe().contains("Credit"));
        assertTrue(credit.describe().contains("limit"));
    }

}
