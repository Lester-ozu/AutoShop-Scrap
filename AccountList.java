import java.io.*;

public class AccountList {

    private AccountProfile[] accountList;
    private int listSize;

    public AccountList(int size) {

        accountList = new AccountProfile[size];
        listSize = 0;
    }

    public void add(AccountProfile account) {

        accountList[listSize] = account;
        listSize++;
        arrayExpand();
    }

    public AccountProfile searchByUsername(String username) {

        for (AccountProfile account : accountList) {

            if (account != null && username.equals(account.getUsername())) {

                return account;
            }
        }

        return null;
    }

    public AccountProfile searchByPassword(String password) {

        for (AccountProfile account : accountList) {

            if (account != null && password.equals(account.getPassword())) {

                return account;
            }
        }

        return null;
    }

    public boolean update(AccountProfile account, String newUserName, String newPassword) {

        for (AccountProfile profile : accountList) {

            if (profile != null && account.equals(profile)) {

                profile.setUsername(newUserName);
                profile.setPassword(newPassword);

                return true;
            }
        }

        return false;
    }

    public boolean delete(AccountProfile account) {

        for (AccountProfile profile : accountList) {

            if (profile != null && profile.equals(account)) {

                profile = null;
                arrayReAdjust();

                return true;
            }
        }

        return false;
    }

    public void backUpCSV(String fileName) {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {

            writer.write("Username" + ",");
            writer.write("Password" + ",");
            writer.write("Name" + ",");
            writer.write("Address" + ",");
            writer.write("Date of Birth" + ",");
            writer.write("Gender" + ",");
            writer.write("Contact Number" + "\n");

            for (AccountProfile account : accountList) {

                if (account != null) {

                    writer.write(account.getUsername() + ",");
                    writer.write(account.getPassword() + ",");
                    writer.write(account.person.getName() + ",");
                    writer.write(account.person.getAddress() + ",");
                    writer.write(account.person.getDOB() + ",");
                    writer.write(account.person.getGender() + ",");
                    writer.write(account.person.getContactNumber() + ",");

                    writer.write("\n");
                }
            }
        }

        catch (IOException e) {

            System.err.println("Error: " + e.getMessage());
        }
    }

    public void loadUpCSV(String fileName) {

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {

            String line;

            reader.readLine();

            while ((line = reader.readLine()) != null) {

                String[] parts = line.split(",");

                String username = parts[0];
                String password = parts[1];
                String name = parts[2];
                String address = parts[3];
                String dob = parts[4];
                String gender = parts[5];
                String contactNumber = parts[6];

                AccountProfile account = new AccountProfile(username, password,
                        new Person(name, address, dob, gender, contactNumber));

                this.add(account);
            }
        }

        catch (IOException e) {

            System.err.println("Error: " + e.getMessage());
        }
    }

    public void arrayExpand() {

        if (accountList[accountList.length - 1] != null) {

            AccountProfile[] tempList = new AccountProfile[accountList.length + 10];

            int size = 0;

            for (int i = 0; i < accountList.length; i++) {

                if (accountList[i] != null) {

                    tempList[size] = accountList[i];
                    size++;
                }
            }

            accountList = tempList.clone();
        }
    }

    public void arrayReAdjust() {

        AccountProfile[] tempList = new AccountProfile[accountList.length];
        int size = 0;

        for (int i = 0; i < accountList.length; i++) {

            if (accountList[i] != null) {

                tempList[size] = accountList[i];
                size++;
            }
        }

        accountList = tempList.clone();
        listSize--;
    }
}
