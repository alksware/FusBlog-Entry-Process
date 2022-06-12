import com.mysql.cj.protocol.Resultset;

import java.sql.*;
import java.util.Locale;
import java.util.Scanner;

public class Main {
    private static String firstName;
    private static String lastName;
    private static String email;
    private static String password;
    private static String systemName;


    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }


    public static void main(String[] args) throws SQLException {
        Scanner input = new Scanner(System.in);
        System.out.println("Welcome to Fusblog");
        System.out.println("If you have a account click for login (1)");
        System.out.println("If you havent a account click for registeration (2)");
        System.out.println("Choosing : ");
        int choosing = input.nextInt();
        if (choosing == 1) {
            Login();
        } else if (choosing == 2) {
            Register();
        } else {
            System.out.println("Please enter a valid input.");
        }

    }


    public static void Connection() throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        DbHelper helper = new DbHelper();
        Resultset resultset;
        try {
            connection = helper.getConnection();
            String sql = " insert into blogapp0.pi(AccountType,SystemName,FirstName,LastName,Email,Password) values (?,?,?,?,?,?)";
            statement = connection.prepareStatement(sql);
            statement.setString(1, " Author");
            statement.setString(2, systemName);
            statement.setString(3, firstName);
            statement.setString(4, lastName);
            statement.setString(5, email);
            statement.setString(6, password);
            statement.executeUpdate();

        } catch (SQLException exception) {
            helper.ErrorMessage(exception);
        }

    }

    public static void Register() throws SQLException {
        Scanner input = new Scanner(System.in);
        String domain = "@gmail.com";

        System.out.println("Welcome to Fusblog. Please register for be a writer");

        System.out.println("-------------------------------------");

        System.out.print("Please enter your first name : ");
        firstName = input.nextLine();

        System.out.println("--------------------------------------");

        System.out.print("Please enter your last name : ");
        lastName = input.nextLine();

        System.out.println("--------------------------------------");

        System.out.print("Please enter your email : ");
        email = input.nextLine();
        if (email.contains(domain)) {
            System.out.println("--------------------------------------");

            System.out.print("Please create a password : ");
            password = input.nextLine();

            System.out.println("--------------------------------------");


            try {
                createId();
                Connection();
            } catch (SQLException exception) {
                System.out.println("Error : " + exception);
                System.out.println("Error Code : " + exception.getErrorCode());
            } finally {
                System.out.println("--------------------------------------");
                System.out.println("Registration Successful!");
            }

        } else {
            System.out.println("Please check your email domain");
        }
    }


    public static void Login() throws SQLException {
        Scanner input = new Scanner(System.in);

        try {
            DbHelper helper = new DbHelper();
            Connection connection = helper.getConnection();
            Statement statement = connection.createStatement();

            System.out.println("Welcome to Fusblog. Please login as writer for enter the system.");

            System.out.println("Please enter your email : ");
            email = input.nextLine();

            System.out.println("Please enter your password : ");
            password = input.nextLine();


            String sql = "select * from blogapp.pi where Email= '" + email + "' and Password='" + password + "'";
            ResultSet resultset = statement.executeQuery(sql);

            while (resultset.next()) {

                if (resultset.getString("Email") == email && resultset.getString("Password") == password) {
                } else {
                    System.out.println("Login unsuccessful!");
                }
            }

        } catch (SQLException exception) {
            System.out.println("Error : " + exception);
            System.out.println("Error code  : " + exception.getErrorCode());
        }

    }

    public static void createId() {
        String s0 = firstName.toLowerCase().substring(0, 2);
        String s1 = lastName.toLowerCase().substring(0, 2);
        String s2 = password.toLowerCase().substring(0, 2);
        systemName = ("WTR_" + s0 + s1 + s2);
        System.out.println("Your's ID : " + systemName);
    }
}
