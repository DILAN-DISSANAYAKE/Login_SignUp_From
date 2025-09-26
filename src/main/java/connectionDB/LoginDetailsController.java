package connectionDB;

import Models.Customer;

import java.sql.*;
import java.util.ArrayList;

public class LoginDetailsController {
    public static boolean addDetails(Customer customer) throws SQLException, ClassNotFoundException {
        String SQL="Insert into registerdetails Values(?,?,?);";
        String SQL2="Insert into logindetails Values(?,?,?,?);";
        Connection connection=ConnectionOB.getInstance().getConnection();
        PreparedStatement stm=connection.prepareStatement(SQL);
        PreparedStatement stm2=connection.prepareStatement(SQL2);
        stm.setObject(1,customer.getNic());
        stm.setObject(2,customer.getFirstName());
        stm.setObject(3,customer.getLastName());
        stm2.setObject(1,customer.getNic());
        stm2.setObject(2,customer.getAccountName());
        stm2.setObject(3,customer.getPassword());
        stm2.setObject(4,customer.getEmail());
        int state=stm.executeUpdate();
        int state2=stm2.executeUpdate();
        return state>0 && state2>0;
    }
    public static ArrayList<String> getPassCheck() throws SQLException, ClassNotFoundException {
        ArrayList<String> passArray=new ArrayList<>();
        String SQL="SELECT password FROM logindetails;";
        Connection connection=ConnectionOB.getInstance().getConnection();
        Statement stm=connection.createStatement();
        ResultSet rst=stm.executeQuery(SQL);
        while (rst.next()){
            passArray.add(rst.getString("password"));
        }

        return passArray;
    }

    public static ArrayList<String> getNicCheck() throws SQLException, ClassNotFoundException {
        ArrayList<String> nicArray=new ArrayList<>();
        String SQL="SELECT nic FROM registerdetails;";
        Connection connection=ConnectionOB.getInstance().getConnection();
        Statement stm=connection.createStatement();
        ResultSet rst=stm.executeQuery(SQL);
        while (rst.next()){
            nicArray.add(rst.getString("nic"));
        }

        return nicArray;
    }

    public static ArrayList<String> getEmailCheck() throws SQLException, ClassNotFoundException {
        ArrayList<String> emailArray=new ArrayList<>();
        String SQL="SELECT email FROM logindetails;";
        Connection connection=ConnectionOB.getInstance().getConnection();
        Statement stm=connection.createStatement();
        ResultSet rst=stm.executeQuery(SQL);
        while (rst.next()){
            emailArray.add(rst.getString("email"));
        }

        return emailArray;
    }

    public static ArrayList<String> getAccountNameCheck() throws SQLException, ClassNotFoundException {
        ArrayList<String> accountNameArray=new ArrayList<>();
        String SQL="SELECT accountname FROM logindetails;";
        Connection connection=ConnectionOB.getInstance().getConnection();
        Statement stm=connection.createStatement();
        ResultSet rst=stm.executeQuery(SQL);
        while (rst.next()){
            accountNameArray.add(rst.getString("accountname"));
        }

        return accountNameArray;
    }

    public static ArrayList<Customer> getLoginDetails() throws SQLException, ClassNotFoundException {
        ArrayList<Customer> emailArray=new ArrayList<>();
        String SQL="SELECT * FROM logindetails;";
        Connection connection=ConnectionOB.getInstance().getConnection();
        Statement stm=connection.createStatement();
        ResultSet rst=stm.executeQuery(SQL);
        while (rst.next()){
            emailArray.add(new Customer(rst.getString("accountname"), rst.getString("password"), rst.getString("email") ));
        }

        return emailArray;
    }
}
