
import java.util.Scanner;
//trying to simulate the atm screen(though the same can be done using print Statements)..
interface Screen{
    //displays string msg
    void displayMsg(String Message);
    //displays int amounts
    void displayAmt(int x);
}
//Trying to simulate the atm Keypad.. Note that computer keypad and atm keypad differs in a lot of ways..so we want to make a onscreen keypad
//simulating the atm keypad....
interface Keypad{
    int getInput();
}
//Captures Functionality of the CashDispenser Hardware
class CashDispenser{
    //This value shows initial number of notes of each denomination.It is kept final so that it can't be changed from elsewhere
    static final int init_denoms500=500;
    static final int init_denoms100=1000;
    static final int init_denoms2000=200;
    //
    //This value shows current number of notes of each denomination.It is kept final so that it can't be changed from elsewhere
    private int denoms500;
    private int denoms100;
    private int denoms2000;
    //
    //constructor to initialise values.
    CashDispenser(){
        denoms500=init_denoms500;
        denoms100=init_denoms100;
        denoms2000=init_denoms2000;
    }
    //
    //Method to calculate number of notes of each denomination required to pull the requested sum.
    void dispenseCash(int amt){
        int x=amt/2000;
        if(denoms2000<x){
            x=denoms2000;
        }
        int y=(amt-2000*x)/500;
        if(denoms500<y){
            y=denoms500;
        }
        int z=(amt-2000*x-500*y)/100;
        denoms2000-=x;
        denoms500-=y;
        denoms100-=z;
    }
    //
    //Returns whether sufficient cash is available or not
    boolean sufficientCashAvailable(int amt){
        int x=amt/2000;
        if(denoms2000<x){
            x=denoms2000;
        }
        int y=(amt-2000*x)/500;
        if(denoms500<y){
            y=denoms500;
        }
        int z=(amt-2000*x-500*y)/100;
        if(x<=denoms2000 && y<=denoms500 &&z<=denoms100){
            return true;
        }
        else{
            return false;
        }
    }
    //
}

//Captures Functionality of the DepositSlot Hardware
//only very simple implementation is given here as there is no way to confirm whether cashis recieved or not(requires the hardware to do that)..
class DepositSlot{
    public boolean cashRecieved()
    {
        return true;
    }
}

//The Main Component in our System...
//atm implements screen and keypad so that inputs can be obtained and messages displayed.
public class atm implements Screen,Keypad{
    Scanner in=new Scanner(System.in);
    //Variables to know about current state of atm
    private boolean userActive;
    private int currentAccNo;
    //
    //objects required to be used by the atm
    private Database database;
    private CashDispenser cashDispenser;
    private DepositSlot depositSlot;
    //
    //Overriding Methods of Screen and Keypad
    @Override
    public void displayMsg(String Message) {
        System.out.println(Message);
    }

    @Override
    public void displayAmt(int x) {
        System.out.println(x);
    }

    @Override
    public int getInput() {
        int x=in.nextInt();
        return x;
    }
    //
    //constructor to initialise variables and objects..
    atm(){
        userActive=false;
        currentAccNo=-1;
        cashDispenser=new CashDispenser();
        database=new Database();
        depositSlot=new DepositSlot();
    }
    //
    //This method is the sum of all the objects and components that make up the atm..Keeps on running until program is stopped..
    public void runAtm(){
        while(true){
            while(!userActive){
                displayMsg("Welcome!!");
                //verify user
                authenticateUser();
                //if user not verified, Then it waits till a user is verified..
            }
            //perform transactions
            doTransactions();
            //set atm to not used setting.
            userActive=false;
            currentAccNo=-1;
            //

        }
    }
    //
    //authenticate user inputs
    private void authenticateUser(){
        int accNo;
        int pinNo;
        displayMsg("Please Enter Your AccountNo: ");
        accNo=getInput();
        displayMsg("Please Enter Your PIN: ");
        pinNo=getInput();
        userActive=database.AuthenticateUser(accNo,pinNo);
        if(userActive){
            currentAccNo=accNo;
        }
        else
            displayMsg("Invalid accNo/pinNo!! please try again");
    }
    //
    //Method to perform various transactions(withdraw, viewBalance and deposit)...
    private void doTransactions(){
        int cases;
        boolean userExited=false;
        Transactions newTransaction=null;
        while (!userExited){
            //Provide instructions to user
            displayMenu();
            cases=getInput();
            //act according to user's input (switch cases)
            //Method transaction below of "Transactions" is doing polymorphism..
            switch (cases) {
                case 1:
                    //viewBalance for the account
                    newTransaction = new balanceEnquiry(currentAccNo, database);
                    newTransaction.transaction();
                    break;
                case 2:
                    //Withdraw from the acc
                    newTransaction = new Withdraw(currentAccNo, database,cashDispenser);
                    newTransaction.transaction();
                    break;
                case 3:
                    //deposit into the account
                    newTransaction = new Deposit(currentAccNo,database,depositSlot);
                    newTransaction.transaction();
                    break;
                case 4:
                    //exit atm
                    displayMsg("Exiting Transaction");
                    userExited=true;
                    break;
                default:
                    //a safety case as we are using pc keyboard instead of atm keypad
                    displayMsg("Invalid input!! Please enter the proper input..");
                    break;

            }
        }
    }
    //
    //DisplayMenu provides user the instructions..
    private void displayMenu(){
        displayMsg("Main Menu");
        displayMsg("press 1 - view Balance");
        displayMsg("press 2 - Withdraw amount");
        displayMsg("press 3 - Deposit amount");
        displayMsg("press 4 - Exit Menu");
        displayMsg("Enter Your Choice: ");
    }
    //
}
