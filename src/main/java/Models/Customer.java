package Models;

public class Customer {
    private String nic;
    private String FirstName;
    private String LastName;
    private String email;
    private String accountName;
    private String password;

    public Customer(String nic,String FirstName,String LastName,String email,String accountName,String password){
        this.nic=nic;
        this.FirstName=FirstName;
        this.LastName=LastName;
        this.email=email;
        this.accountName=accountName;
        this.password=password;
    }
    public Customer(String accountName,String password,String email){
        this.accountName=accountName;
        this.password=password;
        this.email=email;
        this.nic=null;
        this.FirstName=null;
        this.LastName=null;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
