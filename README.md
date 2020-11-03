# ATM_caseStudy_CS19b046_
Code is Provided as .java files

The various Modules are:

1) atm- contains screen and keypad interface, and 2 other classes DepositSlot and CashDispenser.
2) Account- This class contains details of the customer.
3) Database- This module is the information database for all accounts in the bank
4) Transactions- This is an abstract class. This module contains other classes Withdraw, BalanceEnquiry and Deposit which provides details on the abstract method called    transaction.
5)MainClass- This is the driver class used to run the atm.

All of the important data is encapsulated in the respective classes and a few layers of security is added to the private data.

Constructors are extensively used in this design. It is used in a way that it allows for streamline flow of data from atm to database and vice versa(this includes the transaction class as well).

The main dependencies are provided in the driver class( "MainClass" ).

The database has to be manually updated to contain details of new customers.
