
import java.util.Scanner;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.InputMismatchException;

// User-defined checked exception
class InsufficientFundsException extends Exception {
    InsufficientFundsException(String message) {
        super(message);
    }
}

public class ATMWithdrawal {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        FileInputStream cardReader = null;

        double balance = 10000.0;

        try {
            // Check whether the card-reader file exists
            cardReader = new FileInputStream("card-reader.txt");

            System.out.print("Enter account number: ");
            long accountNumber = sc.nextLong();

            if (accountNumber <= 0) {
                throw new IllegalArgumentException(
                    "Invalid account number");
            }

            System.out.print("Enter withdrawal amount: ");
            double amount = sc.nextDouble();

            if (amount <= 0) {
                throw new IllegalArgumentException(
                    "Withdrawal amount must be positive");
            }

            if (amount > balance) {
                throw new InsufficientFundsException(
                    "Insufficient account balance");
            }

            balance -= amount;

            System.out.println("Withdrawal successful!");
            System.out.println("Account Number: " + accountNumber);
            System.out.println("Amount withdrawn: Rs." + amount);
            System.out.println("Remaining balance: Rs." + balance);

        }
        catch (InputMismatchException | FileNotFoundException e) {
            System.out.println("Input/File Error: " + e.getMessage());
        }
        catch (InsufficientFundsException e) {
            System.out.println("Transaction Failed: " + e.getMessage());
        }
        catch (IllegalArgumentException e) {
            System.out.println("Invalid Input: " + e.getMessage());
        }
        finally {

            try {
                if (cardReader != null) {
                    cardReader.close();
                }
            }
            catch (IOException e) {
                System.out.println("Error closing card reader.");
            }

            System.out.println("ATM session ended.");
        }

    }
}
