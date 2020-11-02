//Details regarding a bankAccount

public class Account {
    //encapsulating the data
    private int AccNo,userPinNo;
    private int availableBalance,totalBalance;
    //
    //constructor to set values to the details provided by bank...
    Account(int accNo,int pinNo,int cash){
        AccNo=accNo;
        userPinNo=pinNo;
        availableBalance=cash;
        totalBalance=cash;
    }
    //
    //getMethod for acc no
    public int getAccNo(){
        return this.AccNo;
    }
    //
    //Method to validate pin for the current acc
    public boolean validatePin(int pinNo){
        if(userPinNo==pinNo)
            return true;
        else
            return false;
    }
    //important in userAuthentication
    //get method
    public int getAvailableBalance(){
        return availableBalance;
    }
    //
    //get method..
    public int getTotalBalance(){
        return totalBalance;
    }
    //
    //credit and debit functions
    public void credit(int amt){
        totalBalance+=amt;
    }
    public void debit(int amt){
        availableBalance-=amt;
        totalBalance-=amt;
    }
    //when crediting the amount is added to total balance but not to available balance as bank has to verify contents of the deposit envelope.
}
