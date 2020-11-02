//abstract class Transactions deals with all kinds of transactions done in the atm
public abstract class Transactions extends atm {
    //source acc and database for the transaction
    private int accountNo;
    private Database dataReserve;
    //
    //constructor to initialise the current transaction..
    Transactions(int accNo,Database database){
        accountNo=accNo;
        dataReserve=database;
    }
    //
    //standard getMethod
    public int getAccNo(){
        return accountNo;
    }
    //standard getMethod
    public Database getDatabase(){
        return dataReserve;
    }
    //the following abstract method can be of 3 types
    abstract void transaction();
}

//Balance enquiry
class balanceEnquiry extends Transactions{
    //Cash deposited is not immediately allowed to be withdrawn.hence 2 types of balance..
    int availableBalance,totalBalance;
    //
    //sameType of superclass constructor
    balanceEnquiry(int accNo, Database database) {
        super(accNo, database);
    }
    //
    //balanceEnquiry transaction overrides the abstract method transaction to viewBalance
    @Override
    void transaction() {
        Database x=getDatabase();
        availableBalance= x.getAvailableBalance(getAccNo());
        totalBalance=x.getTotalBalance(getAccNo());
        displayMsg("Available Balance is: ");
        displayAmt(availableBalance);
        displayMsg("TotalBalance is: ");
        displayAmt(totalBalance);
    }
    //
}

class Withdraw extends Transactions{
    //variables
    private int amount;
    private CashDispenser cashDispenser;
    //
    //constructor similar to super class..
    Withdraw(int accNo, Database database,CashDispenser x) {
        super(accNo, database);
        cashDispenser=x;
    }
    //
    //transaction overrides the abstract method transaction to withdraw amount
    @Override
    void transaction() {
        //method continues until cash is dispensed..
        boolean cashDispensed=false;
        int availableBalance;
        //
        Database database1=getDatabase();
        availableBalance=database1.getAvailableBalance(getAccNo());
        //Until Cash is dispensed or withdrawn amount is zero,transaction continues..
        while (!cashDispensed){
            displayMsg("Enter Amount to be Withdrawn: ");
            amount=getInput();
            //checks whether balance is there is acc to withdraw
            if (amount <= availableBalance) {
                //checks whether the atm has enough notes for the withdrawal...
                if (cashDispenser.sufficientCashAvailable(amount)) {
                    database1.debit(getAccNo(), amount);//debit
                    cashDispenser.dispenseCash(amount);//dispense cash
                    cashDispensed = true;
                    displayMsg("Cash Has been dispensed. You may please take it!!");
                } else {
                    displayMsg("Insufficient Funds in atm!!Please choose a smaller amount!!");
                }
                //
            }
            else {
                displayMsg("Insufficient funds in Your account!!");
                displayMsg("Exiting to Main Menu");
                cashDispensed=true;
            }
            //
        }
        //

    }
}

class Deposit extends Transactions{
    private int amount;
    private DepositSlot depositSlot;
    Deposit(int accNo, Database database,DepositSlot x) {
        super(accNo, database);
        depositSlot=x;
    }

    @Override
    void transaction() {
        Database database1=getDatabase();
        displayMsg("Enter Amount to Deposit: ");
        amount=getInput();
        displayMsg("insert envelope containing the amount");
        displayAmt(amount);
        boolean envelopeRecieved=depositSlot.cashRecieved();
        if(envelopeRecieved){
            displayMsg("Amount in envelope has to be verified.Until then, this cash wont be available for withdrawal!!");
            database1.credit(getAccNo(),amount);
        }
        else{
            displayMsg("Envelope not Recieved..Exiting to mainMenu");
        }
    }
}