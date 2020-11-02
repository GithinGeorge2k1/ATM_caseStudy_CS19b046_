import java.util.ArrayList;
//BankDatabase for storing customer Details and getting information
public class Database {
    int i;
    //current account accessed from atm
    Account current;
    //used while authenticatingUser
    private ArrayList<Account> accounts;//arraylist storing customer details
    //add the account details in this class only...
    public Database(){
        accounts=new ArrayList<Account>();
        accounts.add(new Account(10250,11524,65700));
        accounts.add(new Account(11111,13205,560000));
        accounts.add(new Account(82190,45714,17700));
        accounts.add(new Account(66879,65132,100000));
        accounts.add(new Account(24698,98256,45000));
        accounts.add(new Account(21218,44487,200500));
        accounts.add(new Account(31254,33563,623000));
        accounts.add(new Account(71654,15468,789500));
        accounts.add(new Account(53181,62245,465780));
        accounts.add(new Account(41200,16798,80000));
        accounts.add(new Account(92200,78455,95000));
        accounts.add(new Account(42190,78155,57840));
        accounts.add(new Account(82780,33547,96450));
        accounts.add(new Account(84620,89645,275640));
        accounts.add(new Account(72450,11487,84560));
    }
    //Find whether the requested account is available or not and returns it if yes.
    private Account getAccount(int accNo){
        for(i=0;i<accounts.size();i++){
            if(accounts.get(i).getAccNo()==accNo){
                return accounts.get(i);
            }
        }
        return null;
    }
    //Authenticating User
    public boolean AuthenticateUser(int accNo,int pinNo){
        current=getAccount(accNo);
        //if acc is available validates pin else returns error message
        if(current!=null){
            return current.validatePin(pinNo);
        }
        else{
            return false;
        }
        //
    }
    public int getAvailableBalance(int accNo)
    {
        return getAccount(accNo).getAvailableBalance();
    }
    public int getTotalBalance(int accNo)
    {
        return getAccount(accNo).getTotalBalance();
    }
    public void credit(int userAcc,int amt){
        getAccount(userAcc).credit(amt);
    }
    public void debit(int userAcc,int amt){
        getAccount(userAcc).debit(amt);
    }
}
