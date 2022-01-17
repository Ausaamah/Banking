import java.util.Scanner;

/** Bank Statement Project
 * Ausaamah Qureshi
 */
public class BankStatement {

    public final String MENU_TEMPLATE =
            "%nWelcome to the Banking System. Please enter an option 0 to 3%n"
                    + "0. Exit%n"
                    + "1. Generate a Bank ID%n"
                    + "2. Capture balance for account%n"
                    + "3. List Bank Account IDs and corresponding average balances%n";
    /**
     * Main Menu Message with options
     */
    public final String NOT_FOUND_TEMPLATE =
            "No Bank ID of %s exists";

    public final String ENTER_BALANCE_TEMPLATE = "Please enter balance %d for account %s%n";
    public final String BANK_ID_TEMPLATE = "Your bankID is %s";
    public final String NAME_RESPONSE_TEMPLATE = "You entered a given name of %s and a given name of %s%n";
    public final String LOW_HIGH_TEMPLATE = "Account holder %s has a lowest balance of %d%nA highest balance of %d%n";
    public final String AVG_BALANCE_TEMPLATE = "Account ***%s*** has an average of %.2f%n";
    public final String COLUMN_1_TEMPLATE = "   *";
    public final String COLUMN_2_TEMPLATE = "           *";
    public final String CHART_KEY_TEMPLATE = "Highest     Lowest%n   %d           %d%n";
    public final String REPORT_PER_ACC_TEMPLATE = "| Bank ID   %d is %s | Average is  %.2f |%n";

    /**
     * These templates are used throughout the project for consistency
     */

    public String generateBankId(Scanner sc) {
        /**
         * Using the full name of a user to generate a Bank ID
         */
        String bankId = "generateBankId is incomplete";

        System.out.printf(
                "Please enter your given name and surname (Enter 0 to return to main menu)%n");

        String bankName = sc.nextLine();

        while (!bankName.equals(0)) {
            String[] names = bankName.split(" ");
            String firstName = names[0];
            char firstInitial = firstName.charAt(0);
            String surname = names[1];
            char lastInitial = surname.charAt(0);
            int lenFirstName = firstName.length();
            int lenSurname = surname.length();
            String twoSig = String.format("%02d", lenSurname);
            int middleFirst = lenFirstName / 2;
            int middleSur = lenSurname / 2;
            char middleFirstName = firstName.charAt(middleFirst);
            char middleSurname = surname.charAt(middleSur);

            System.out.printf(NAME_RESPONSE_TEMPLATE, firstName, surname);
            bankId = "" + firstInitial + lastInitial + twoSig + middleFirstName + middleSurname;
            System.out.printf(BANK_ID_TEMPLATE, bankId);
            break;
        }
        if (bankName.equals(0)) {
            bankId = "generateStudId is incomplete";
            System.out.printf(bankId);
        }

        return bankId;
    }

    /**
     * read 3 account balances for a user's bank accounts and display the average of these
     */
    public double captureBalance(Scanner sc, String bankId) {
        // defined maximum and minimum balances
        final int MAX_BAL = 100;
        final int MIN_BAL = 0;


        double avg = Double.MIN_VALUE;

        int[] balance = new int[3];

        for (int i = 0; i < 3; i++) {
            System.out.printf(ENTER_BALANCE_TEMPLATE, i + 1, bankId);
            balance[i] = sc.nextInt();
            sc.nextLine();
        }
        for (int i = 0; i < balance.length - 1; ++i)
            for (int b = 0; b < balance.length - 1; ++b)
                if (balance[b] > balance[b + 1]) {
                    int temp = balance[b];
                    balance[b] = balance[b + 1];
                    balance[b + 1] = temp;
                }
        int low = balance[0];
        int high = balance[(balance.length - 1)];

        System.out.printf(LOW_HIGH_TEMPLATE, bankId, low, high);

        int balanceAdded = (balance[0] + balance[1] + balance[2]);
        double decimalBalanceAdded = balanceAdded;
        avg = decimalBalanceAdded / 3;
        System.out.printf(AVG_BALANCE_TEMPLATE, bankId, avg);

        System.out.printf("Would you like to print a bar chart? [y/n]%n");

        String barChoice = sc.nextLine();

        if (barChoice.equals("y")) {
            printBarChart(bankId, high, low);
        }
        return avg;
    }

    /**
     * Printing vertical star bar chart in the terminal for the highest and lowest of three balances
     */
    public void printBarChart(String bankId, int high, int low) {
        System.out.printf("Bank Account statistics: %s%n", bankId);
        int diff = high - low;
        for (int i = 0; i < diff; i++) {
            System.out.printf(COLUMN_1_TEMPLATE + "%n");
        }
        for (int i = 0; i < (low + 1); i++) {
            System.out.printf(COLUMN_1_TEMPLATE + COLUMN_2_TEMPLATE + "%n");
        }
        System.out.printf(CHART_KEY_TEMPLATE, high, low);

    }

    /**
     * Prints a specially formatted bank statement, one line per Bank ID.
     *
     */
    public void reportPerAcc(String[] accountList,
                             int count,
                             double[] avgArray) {

        for (count = 0; count < accountList.length; count++) {
            if (avgArray[count] == 0.00) {
            } else {
                String account = accountList[count];
                double average = avgArray[count];
                String.format(".2f", average);
                System.out.printf(REPORT_PER_ACC_TEMPLATE, count + 1, account, average);
            }
        }


    }

    /**
     * The main menu
     */
    public void displayMenu() {
        System.out.printf(MENU_TEMPLATE);
    }

    /**
     * The controlling logic of the program. Creates and re-uses a Scanner that reads from user input.
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        BankStatement bs = new BankStatement();
        final int EXIT_CODE = 0;
        final int MAX_ACCOUNTS = 5;

        String[] keepBankId = new String[MAX_ACCOUNTS];
        double[] avgArray = new double[MAX_ACCOUNTS];

        /**
         * loop to operate the menu and show the bank details
         */

        boolean menuLoop = true;
        int accountCount = 0;
        while (true) {
            bs.displayMenu();
            int menuOption = sc.nextInt();
            sc.nextLine();

            switch (menuOption) {
                case 1:
                    // Generating bank account ID
                    String bankId = bs.generateBankId(sc);
                    keepBankId[accountCount] = bankId;
                    accountCount++;
                    break;

                case 2:
                    // Capturing balances for accounts
                    System.out.printf(
                            "Please enter the Bank ID to capture their marks (Enter 0 to return to main menu)%n");
                    String account = sc.nextLine();

                    boolean foundId = false;
                    for (accountCount = 0; accountCount < keepBankId.length; accountCount++) {
                        if (keepBankId[accountCount].equals(account)) {
                            avgArray[accountCount] = bs.captureBalance(sc, account);
                            foundId = true;
                            break;
                        }
                    }
                    if (foundId == false) {
                        System.out.printf(bs.NOT_FOUND_TEMPLATE, account);
                    }
                    break;

                case 3:
                    // List bank account IDs and average balance
                    bs.reportPerAcc(keepBankId, accountCount, avgArray);

                    break;

                default:
                    // Handle invalid main menu input
                    System.out.printf(
                            "You have entered an invalid option. Enter 0, 1, 2 or 3%n");// Skeleton: keep, unchanged


            }
            if (menuOption == 0) {
                // Exit
                System.out.printf("Goodbye%n");
                break;
            }
        }
    }
}

