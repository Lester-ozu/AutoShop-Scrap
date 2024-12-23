import javax.swing.*;
import javax.swing.border.Border;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.image.BufferedImage;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class Main {

    static AccountList accountList = new AccountList(50);
    static ScheduledServiceList SSList = new ScheduledServiceList(10);
    static AccountProfile profile = new AccountProfile("12333", "123",
            new Person("123", "123", "123", "123", "123"));

    public static void main(String[] args) {

        accountList.loadUpCSV("accountList.csv");

        userHomePage(profile);
    }

    public static void logInPage() {

        JFrame logInFrame = new JFrame();

        logInFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        logInFrame.setResizable(false);
        logInFrame.setLayout(null);
        logInFrame.setSize(400, 500);
        logInFrame.setLocationRelativeTo(null);
        logInFrame.setUndecorated(true);
        logInFrame.getContentPane().setBackground(Color.white);

        ImageIcon xButton = new ImageIcon("xButton.png");

        JLabel xLabel = new JLabel();
        xLabel.setIcon(xButton);
        xLabel.setBounds(355, 5, 40, 40);
        xLabel.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {

                int choice = JOptionPane.showConfirmDialog(logInFrame, "Are you sure you want to close the program?",
                        "EXIT", JOptionPane.YES_NO_OPTION);

                switch (choice) {

                    case 0:
                        accountList.backUpCSV("accountList.csv");
                        System.exit(0);
                        break;
                    default:
                        return;
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {

                ImageIcon changedIcon = changeIconColor(xButton, new Color(38, 99, 232));
                xLabel.setIcon(changedIcon);
            }

            @Override
            public void mouseExited(MouseEvent e) {

                xLabel.setIcon(xButton);
            }
        });

        JLabel signInLabel = new JLabel("Login to Your Account");
        signInLabel.setFont(new Font("Montserrat", Font.BOLD, 25));
        signInLabel.setForeground(Color.black);
        signInLabel.setBounds(65, 55, 300, 50);

        JTextField userNameField = new JTextField();
        userNameField.setFont(new Font("Montserrat", Font.PLAIN, 13));
        userNameField.setCaretColor(Color.black);
        userNameField.setBounds(95, 140, 200, 30);
        userNameField.setText("Username");
        userNameField.addFocusListener(new FocusListener() {

            @Override
            public void focusGained(FocusEvent e) {

                if (userNameField.getText().equals("Username")) {

                    userNameField.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {

                if (userNameField.getText().isEmpty()) {

                    userNameField.setText("Username");
                }
            }
        });

        JTextField passwordField = new JTextField();
        passwordField.setFont(new Font("Montserrat", Font.PLAIN, 13));
        passwordField.setCaretColor(Color.black);
        passwordField.setBounds(0, 0, 200, 30);
        passwordField.setText("Password");
        passwordField.addFocusListener(new FocusListener() {

            @Override
            public void focusGained(FocusEvent e) {

                if (passwordField.getText().equals("Password")) {

                    passwordField.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {

                if (passwordField.getText().isEmpty()) {

                    passwordField.setText("Password");
                }
            }
        });

        StringBuilder originalText = new StringBuilder();
        passwordField.getDocument().addDocumentListener(new DocumentListener() {

            private boolean isUpdating = false;

            @Override
            public void insertUpdate(DocumentEvent e) {

                if (!isUpdating) {

                    try {

                        int offset = e.getOffset();
                        int length = e.getLength();
                        String tempText = e.getDocument().getText(offset, length);
                        originalText.insert(offset, tempText);
                        updateTextField(e);
                    }

                    catch (BadLocationException ex) {

                        ex.printStackTrace();
                    }
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {

                if (!isUpdating) {

                    int offset = e.getOffset();
                    int length = e.getLength();
                    originalText.delete(offset, offset + length);
                    updateTextField(e);
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {

                updateTextField(e);
            }

            private void updateTextField(DocumentEvent e) {

                SwingUtilities.invokeLater(() -> {

                    isUpdating = true;

                    try {

                        Document doc = e.getDocument();
                        int length = doc.getLength();
                        StringBuilder sb = new StringBuilder(length);

                        if (doc.getText(0, length).equals("Password")) {

                            isUpdating = false;
                            originalText.delete(0, originalText.length());
                            return;
                        }

                        for (int i = 0; i < length; i++) {

                            sb.append('*');
                        }

                        doc.remove(0, length);
                        doc.insertString(0, sb.toString(), null);
                    }

                    catch (BadLocationException ex) {

                        ex.printStackTrace();
                    }

                    finally {

                        isUpdating = false;
                    }
                });
            }
        });

        ImageIcon viewIcon = new ImageIcon("view.png");

        JLabel showLabel = new JLabel();
        showLabel.setIcon(viewIcon);
        showLabel.setBounds(165, -10, 50, 50);
        showLabel.addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {

                passwordField.setText(originalText.toString());
            }

            @Override
            public void mouseReleased(MouseEvent e) {

                int length = originalText.length();

                StringBuilder sb = new StringBuilder();

                for (int i = 0; i < length; i++) {

                    sb.append('*');
                }

                passwordField.setText(sb.toString());
            }

            @Override
            public void mouseEntered(MouseEvent e) {

                ImageIcon changedIcon = changeIconColor(viewIcon, new Color(38, 99, 232));
                showLabel.setIcon(changedIcon);
            }

            public void mouseExited(MouseEvent e) {

                showLabel.setIcon(viewIcon);
            }
        });

        JLayeredPane showPane = new JLayeredPane();
        showPane.setBounds(95, 210, 200, 30);

        showPane.add(passwordField, JLayeredPane.DEFAULT_LAYER);
        showPane.add(showLabel, JLayeredPane.PALETTE_LAYER);

        JLabel nameForgotLabel = new JLabel("Forgot username?");
        nameForgotLabel.setFont(new Font("Montserrat", Font.PLAIN, 11));
        nameForgotLabel.setForeground(new Color(38, 99, 232));
        nameForgotLabel.setBounds(97, 170, 100, 20);
        nameForgotLabel.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {

                nameForgotLabel.setForeground(Color.DARK_GRAY);
            }

            public void mouseExited(MouseEvent e) {

                nameForgotLabel.setForeground(new Color(38, 99, 232));
            }
        });

        JLabel passForgotLabel = new JLabel("Forgot password?");
        passForgotLabel.setFont(new Font("Montserrat", Font.PLAIN, 11));
        passForgotLabel.setForeground(new Color(38, 99, 232));
        passForgotLabel.setBounds(97, 245, 100, 15);
        passForgotLabel.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {

                passForgotLabel.setForeground(Color.DARK_GRAY);
            }

            public void mouseExited(MouseEvent e) {

                passForgotLabel.setForeground(new Color(38, 99, 232));
            }
        });

        JButton signInButton = new JButton("Sign in");
        signInButton.setFont(new Font("Montserrat", Font.PLAIN, 13));
        signInButton.setFocusable(false);
        signInButton.setBackground(new Color(38, 99, 232));
        signInButton.setForeground(Color.white);
        signInButton.setBounds(120, 280, 150, 30);
        signInButton.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {

                signInButton.setBackground(Color.black);
            }

            public void mouseExited(MouseEvent e) {

                signInButton.setBackground(new Color(38, 99, 232));
            }

            @Override
            public void mouseClicked(MouseEvent e) {

                AccountProfile account = accountList.searchByPassword(originalText.toString());

                if (account != null) {

                    logInFrame.dispose();
                    userHomePage(account);
                }

                else {

                    JOptionPane.showMessageDialog(logInFrame, "The username or password you've entered is incorrect!",
                            "ERROR!", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        JLabel noAccountLabel = new JLabel("Don't have an account?");
        noAccountLabel.setFont(new Font("Montserrat", Font.PLAIN, 11));
        noAccountLabel.setForeground(Color.black);
        noAccountLabel.setBounds(117, 290, 150, 100);

        JLabel signUpLabel = new JLabel("Sign Up");
        signUpLabel.setFont(new Font("Montserrat", Font.BOLD, 11));
        signUpLabel.setForeground(new Color(38, 99, 232));
        signUpLabel.setBounds(235, 290, 100, 100);
        signUpLabel.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {

                signUpLabel.setForeground(Color.DARK_GRAY);
            }

            public void mouseExited(MouseEvent e) {

                signUpLabel.setForeground(new Color(38, 99, 232));
            }
        });

        ImageIcon logoIcon = new ImageIcon("logo.png");
        JLabel logoLabel = new JLabel(logoIcon);
        logoLabel.setIcon(logoIcon);
        logoLabel.setBounds(90, 400, 200, 40);

        logInFrame.add(xLabel);
        logInFrame.add(nameForgotLabel);
        logInFrame.add(passForgotLabel);
        logInFrame.add(userNameField);
        logInFrame.add(showPane);
        logInFrame.add(signInLabel);
        logInFrame.add(signInButton);
        logInFrame.add(noAccountLabel);
        logInFrame.add(signUpLabel);
        logInFrame.add(logoLabel);
        logInFrame.setVisible(true);
    }

    public static void signUpPage() {

        JFrame signUpFrame = new JFrame();

        signUpFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        signUpFrame.setResizable(false);
        signUpFrame.setLayout(null);
        signUpFrame.setSize(400, 500);
        signUpFrame.setLocationRelativeTo(null);
        signUpFrame.setUndecorated(true);
        signUpFrame.getContentPane().setBackground(Color.white);

        ImageIcon xButton = new ImageIcon("xButton.png");

        JLabel xLabel = new JLabel();
        xLabel.setIcon(xButton);
        xLabel.setBounds(355, 5, 40, 40);
        xLabel.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {

                int choice = JOptionPane.showConfirmDialog(signUpFrame, "Are you sure you want to close the program?",
                        "EXIT", JOptionPane.YES_NO_OPTION);

                switch (choice) {

                    case 0:
                        accountList.backUpCSV("accountList.csv");
                        System.exit(0);
                        break;
                    default:
                        return;
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {

                ImageIcon changedIcon = changeIconColor(xButton, new Color(38, 99, 232));
                xLabel.setIcon(changedIcon);
            }

            @Override
            public void mouseExited(MouseEvent e) {

                xLabel.setIcon(xButton);
            }
        });

        JLabel signUpLabel = new JLabel("Create Account");
        signUpLabel.setFont(new Font("Montserrat", Font.BOLD, 25));
        signUpLabel.setForeground(Color.black);
        signUpLabel.setBounds(105, 55, 300, 50);

        JTextField nameField = new JTextField();
        nameField.setFont(new Font("Montserrat", Font.PLAIN, 13));
        nameField.setCaretColor(Color.black);
        nameField.setBounds(35, 140, 160, 30);
        nameField.setText("Full Name");
        nameField.addFocusListener(new FocusListener() {

            @Override
            public void focusGained(FocusEvent e) {

                if (nameField.getText().equals("Full Name")) {

                    nameField.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {

                if (nameField.getText().isEmpty()) {

                    nameField.setText("Full Name");
                }
            }
        });

        JTextField addressField = new JTextField();
        addressField.setFont(new Font("Montserrat", Font.PLAIN, 13));
        addressField.setCaretColor(Color.black);
        addressField.setBounds(210, 140, 160, 30);
        addressField.setText("Address");
        addressField.addFocusListener(new FocusListener() {

            @Override
            public void focusGained(FocusEvent e) {

                if (addressField.getText().equals("Address")) {

                    addressField.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {

                if (addressField.getText().isEmpty()) {

                    addressField.setText("Address");
                }
            }
        });

        JTextField DOBField = new JTextField();
        DOBField.setFont(new Font("Montserrat", Font.PLAIN, 13));
        DOBField.setCaretColor(Color.black);
        DOBField.setBounds(35, 180, 160, 30);
        DOBField.setText("Date of Birth (YYYY-MM-DD)");
        DOBField.addFocusListener(new FocusListener() {

            @Override
            public void focusGained(FocusEvent e) {

                if (DOBField.getText().equals("Date of Birth (YYYY-MM-DD)")) {

                    DOBField.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {

                if (DOBField.getText().isEmpty()) {

                    DOBField.setText("Date of Birth (YYYY-MM-DD)");
                }
            }
        });

        JTextField genderField = new JTextField();
        genderField.setFont(new Font("Montserrat", Font.PLAIN, 13));
        genderField.setCaretColor(Color.black);
        genderField.setBounds(210, 180, 160, 30);
        genderField.setText("Gender");
        genderField.addFocusListener(new FocusListener() {

            @Override
            public void focusGained(FocusEvent e) {

                if (genderField.getText().equals("Gender")) {

                    genderField.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {

                if (addressField.getText().isEmpty()) {

                    addressField.setText("Gender");
                }
            }
        });

        JTextField contactNumberField = new JTextField();
        contactNumberField.setFont(new Font("Montserrat", Font.PLAIN, 13));
        contactNumberField.setCaretColor(Color.black);
        contactNumberField.setBounds(35, 220, 160, 30);
        contactNumberField.setText("Contact Number");
        contactNumberField.addFocusListener(new FocusListener() {

            @Override
            public void focusGained(FocusEvent e) {

                if (contactNumberField.getText().equals("Contact Number")) {

                    contactNumberField.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {

                if (contactNumberField.getText().isEmpty()) {

                    contactNumberField.setText("Contact Number");
                }
            }
        });

        JTextField passwordField = new JTextField();
        passwordField.setFont(new Font("Montserrat", Font.PLAIN, 13));
        passwordField.setCaretColor(Color.black);
        passwordField.setBounds(210, 220, 160, 30);
        passwordField.setText("Password");
        passwordField.addFocusListener(new FocusListener() {

            @Override
            public void focusGained(FocusEvent e) {

                if (passwordField.getText().equals("Password")) {

                    passwordField.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {

                if (passwordField.getText().isEmpty()) {

                    passwordField.setText("Password");
                }
            }
        });

        StringBuilder originalText = new StringBuilder();
        passwordField.getDocument().addDocumentListener(new DocumentListener() {

            private boolean isUpdating = false;

            @Override
            public void insertUpdate(DocumentEvent e) {

                if (!isUpdating) {

                    try {

                        int offset = e.getOffset();
                        int length = e.getLength();
                        String tempText = e.getDocument().getText(offset, length);
                        originalText.insert(offset, tempText);
                        updateTextField(e);
                    }

                    catch (BadLocationException ex) {

                        ex.printStackTrace();
                    }
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {

                if (!isUpdating) {

                    int offset = e.getOffset();
                    int length = e.getLength();
                    originalText.delete(offset, offset + length);
                    updateTextField(e);
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {

                updateTextField(e);
            }

            private void updateTextField(DocumentEvent e) {

                SwingUtilities.invokeLater(() -> {

                    isUpdating = true;

                    try {

                        Document doc = e.getDocument();
                        int length = doc.getLength();
                        StringBuilder sb = new StringBuilder(length);

                        if (doc.getText(0, length).equals("Password")) {

                            isUpdating = false;
                            originalText.delete(0, originalText.length());
                            return;
                        }

                        for (int i = 0; i < length; i++) {

                            sb.append('*');
                        }

                        doc.remove(0, length);
                        doc.insertString(0, sb.toString(), null);
                    }

                    catch (BadLocationException ex) {

                        ex.printStackTrace();
                    }

                    finally {

                        isUpdating = false;
                    }
                });
            }
        });

        JTextField userNameField = new JTextField();
        userNameField.setFont(new Font("Montserrat", Font.PLAIN, 13));
        userNameField.setCaretColor(Color.black);
        userNameField.setBounds(35, 260, 160, 30);
        userNameField.setText("Username");
        userNameField.addFocusListener(new FocusListener() {

            @Override
            public void focusGained(FocusEvent e) {

                if (userNameField.getText().equals("Username")) {

                    userNameField.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {

                if (userNameField.getText().isEmpty()) {

                    userNameField.setText("Username");
                }
            }
        });

        JTextField confirmPassField = new JTextField();
        confirmPassField.setFont(new Font("Montserrat", Font.PLAIN, 13));
        confirmPassField.setCaretColor(Color.black);
        confirmPassField.setBounds(210, 260, 160, 30);
        confirmPassField.setText("Confirm Password");
        confirmPassField.addFocusListener(new FocusListener() {

            @Override
            public void focusGained(FocusEvent e) {

                if (confirmPassField.getText().equals("Confirm Password")) {

                    confirmPassField.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {

                if (confirmPassField.getText().isEmpty()) {

                    confirmPassField.setText("Confirm Password");
                }
            }
        });

        StringBuilder originalText2 = new StringBuilder();
        confirmPassField.getDocument().addDocumentListener(new DocumentListener() {

            private boolean isUpdating = false;

            @Override
            public void insertUpdate(DocumentEvent e) {

                if (!isUpdating) {

                    try {

                        int offset = e.getOffset();
                        int length = e.getLength();
                        String tempText = e.getDocument().getText(offset, length);
                        originalText2.insert(offset, tempText);
                        updateTextField(e);
                    }

                    catch (BadLocationException ex) {

                        ex.printStackTrace();
                    }
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {

                if (!isUpdating) {

                    int offset = e.getOffset();
                    int length = e.getLength();
                    originalText2.delete(offset, offset + length);
                    updateTextField(e);
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {

                updateTextField(e);
            }

            private void updateTextField(DocumentEvent e) {

                SwingUtilities.invokeLater(() -> {

                    isUpdating = true;

                    try {

                        Document doc = e.getDocument();
                        int length = doc.getLength();
                        StringBuilder sb = new StringBuilder(length);

                        if (doc.getText(0, length).equals("Confirm Password")) {

                            isUpdating = false;
                            originalText2.delete(0, originalText2.length());
                            return;
                        }

                        for (int i = 0; i < length; i++) {

                            sb.append('*');
                        }

                        doc.remove(0, length);
                        doc.insertString(0, sb.toString(), null);
                    }

                    catch (BadLocationException ex) {

                        ex.printStackTrace();
                    }

                    finally {

                        isUpdating = false;
                    }
                });
            }
        });

        JButton signUpButton = new JButton("Sign up");
        signUpButton.setFont(new Font("Montserrat", Font.PLAIN, 13));
        signUpButton.setFocusable(false);
        signUpButton.setBackground(new Color(38, 99, 232));
        signUpButton.setForeground(Color.white);
        signUpButton.setBounds(120, 310, 150, 30);
        signUpButton.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {

                signUpButton.setBackground(Color.black);
            }

            @Override
            public void mouseExited(MouseEvent e) {

                signUpButton.setBackground(new Color(38, 99, 232));
            }

            @Override
            public void mouseClicked(MouseEvent e) {

                if (nameField.getText().isEmpty()) {

                    JOptionPane.showMessageDialog(signUpFrame, "Name Field cannot be empty!", "ERROR",
                            JOptionPane.ERROR_MESSAGE);
                }

                else if (nameField.getText().matches("\\d+")) {

                    JOptionPane.showMessageDialog(signUpFrame, "Numbers is not allowed in the Name Field!", "ERROR",
                            JOptionPane.ERROR_MESSAGE);
                }

                else if (addressField.getText().isEmpty()) {

                    JOptionPane.showMessageDialog(signUpFrame, "Address Field cannot be empty!", "ERROR",
                            JOptionPane.ERROR_MESSAGE);
                }

                else if (DOBField.getText().isEmpty()) {

                    JOptionPane.showMessageDialog(signUpFrame, "Date of Birth Field cannot be empty!", "ERROR",
                            JOptionPane.ERROR_MESSAGE);
                }

                else if (DOBField.getText().matches("[a-zA-Z]+")) {

                    JOptionPane.showMessageDialog(signUpFrame, "Please use Numbers in the Date of Birth Field!",
                            "ERROR", JOptionPane.ERROR_MESSAGE);
                }

                else if (genderField.getText().isEmpty()) {

                    JOptionPane.showMessageDialog(signUpFrame, "Gender Field cannot be empty!", "ERROR",
                            JOptionPane.ERROR_MESSAGE);
                }

                else if (genderField.getText().matches("\\d+")) {

                    JOptionPane.showMessageDialog(signUpFrame, "Numbers is not allowed in the Gender Field!", "ERROR",
                            JOptionPane.ERROR_MESSAGE);
                }

                else if (contactNumberField.getText().isEmpty()) {

                    JOptionPane.showMessageDialog(signUpFrame, "Contact Number Field cannot be empty!", "ERROR",
                            JOptionPane.ERROR_MESSAGE);
                }

                else if (!contactNumberField.getText().matches("\\d+")) {

                    JOptionPane.showMessageDialog(signUpFrame,
                            "Letters or other symbols are not allowed in the Contact Number Field!", "ERROR",
                            JOptionPane.ERROR_MESSAGE);
                }

                else if (originalText.toString().isEmpty()) {

                    JOptionPane.showMessageDialog(signUpFrame, "Password Field cannot be empty!", "ERROR",
                            JOptionPane.ERROR_MESSAGE);
                }

                else if (originalText2.toString().isEmpty()) {

                    JOptionPane.showMessageDialog(signUpFrame, "Confirm Password Field cannot be empty!", "ERROR",
                            JOptionPane.ERROR_MESSAGE);
                }

                else if (!originalText.toString().equals(originalText2.toString())) {

                    JOptionPane.showMessageDialog(signUpFrame, "Password and Confirm Password does not match!", "ERROR",
                            JOptionPane.ERROR_MESSAGE);
                }

                else if (userNameField.getText().isEmpty()) {

                    JOptionPane.showMessageDialog(signUpFrame, "Username Field cannot be empty!", "ERROR",
                            JOptionPane.ERROR_MESSAGE);
                }

                else {

                    String name = nameField.getText().replace(",", " ");
                    String address = addressField.getText().replace(",", " ");
                    String DOB = DOBField.getText().replace(",", " ");
                    String gender = genderField.getText().replace(",", " ");
                    String contactNumber = contactNumberField.getText().replace(",", " ");

                    String userName = userNameField.getText().replace(",", " ");
                    String password = originalText2.toString().replace(",", " ");

                    AccountProfile account = new AccountProfile(userName, password,
                            new Person(name, address, DOB, gender, contactNumber));

                    accountList.add(account);

                    signUpFrame.dispose();

                    JOptionPane.showMessageDialog(signUpFrame, "Account Succesfully Created!", "SUCCESS",
                            JOptionPane.INFORMATION_MESSAGE);

                    logInPage();
                }
            }
        });

        JLabel haveAccountLabel = new JLabel("Already have an account?");
        haveAccountLabel.setFont(new Font("Montserrat", Font.PLAIN, 11));
        haveAccountLabel.setForeground(Color.black);
        haveAccountLabel.setBounds(110, 320, 150, 100);

        JLabel signInLabel = new JLabel("Sign In");
        signInLabel.setFont(new Font("Montserrat", Font.BOLD, 11));
        signInLabel.setForeground(new Color(38, 99, 232));
        signInLabel.setBounds(242, 320, 100, 100);
        signInLabel.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {

                signInLabel.setForeground(Color.DARK_GRAY);
            }

            public void mouseExited(MouseEvent e) {

                signInLabel.setForeground(new Color(38, 99, 232));
            }
        });

        ImageIcon logoIcon = new ImageIcon("logo.png");
        JLabel logoLabel = new JLabel(logoIcon);
        logoLabel.setIcon(logoIcon);
        logoLabel.setBounds(90, 420, 200, 40);

        signUpFrame.add(xLabel);
        signUpFrame.add(signUpLabel);
        signUpFrame.add(nameField);
        signUpFrame.add(addressField);
        signUpFrame.add(DOBField);
        signUpFrame.add(passwordField);
        signUpFrame.add(genderField);
        signUpFrame.add(contactNumberField);
        signUpFrame.add(confirmPassField);
        signUpFrame.add(userNameField);
        signUpFrame.add(signUpButton);
        signUpFrame.add(haveAccountLabel);
        signUpFrame.add(signInLabel);
        signUpFrame.add(logoLabel);
        signUpFrame.setVisible(true);
    }

    public static void userHomePage(AccountProfile account) {

        JFrame userHomeFrame = new JFrame();

        userHomeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        userHomeFrame.setResizable(false);
        userHomeFrame.setLayout(null);
        userHomeFrame.setSize(850, 500);
        userHomeFrame.setLocationRelativeTo(null);
        userHomeFrame.setUndecorated(true);
        userHomeFrame.getContentPane().setBackground(Color.white);

        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(115, 169, 229));
        titlePanel.setBounds(0, 0, 850, 54);
        titlePanel.setLayout(null);

        ImageIcon logoIcon = changeIconColor(new ImageIcon("logo.png"), Color.white);
        JLabel logoLabel = new JLabel();
        logoLabel.setIcon(logoIcon);
        logoLabel.setBounds(200, 5, 200, 40);

        ImageIcon xIcon = changeIconColor(new ImageIcon("xButton.png"), Color.white);
        JLabel xButtonLabel = new JLabel();
        xButtonLabel.setIcon(xIcon);
        xButtonLabel.setBounds(800, 7, 50, 40);
        xButtonLabel.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {

                xButtonLabel.setIcon(changeIconColor(xIcon, Color.black));
            }

            @Override
            public void mouseExited(MouseEvent e) {

                xButtonLabel.setIcon(xIcon);
            }

            @Override
            public void mouseClicked(MouseEvent e) {

                int choice = JOptionPane.showConfirmDialog(userHomeFrame, "Are you sure you want to close the program?",
                        "EXIT", JOptionPane.YES_NO_OPTION);

                switch (choice) {

                    case 0:
                        accountList.backUpCSV("accountList.csv");
                        System.exit(0);
                        break;
                    default:
                        return;
                }
            }
        });

        JLabel minimizeButtonLabel = new JLabel();
        minimizeButtonLabel.setBackground(Color.white);
        minimizeButtonLabel.setOpaque(true);
        minimizeButtonLabel.setBounds(757, 28, 28, 5);
        minimizeButtonLabel.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {

                minimizeButtonLabel.setBackground(Color.black);
            }

            @Override
            public void mouseExited(MouseEvent e) {

                minimizeButtonLabel.setBackground(Color.white);
            }

            @Override
            public void mouseClicked(MouseEvent e) {

                userHomeFrame.setState(Frame.ICONIFIED);
            }
        });

        titlePanel.add(logoLabel);
        titlePanel.add(xButtonLabel);
        titlePanel.add(minimizeButtonLabel);

        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.LEFT);
        tabbedPane.setBounds(-5, 50, 856, 470);

        JPanel schedulePanel = new JPanel();
        schedulePanel.setPreferredSize(new Dimension(500, 900));
        schedulePanel.setLayout(null);
        ImageIcon scheduleIcon = new ImageIcon("schedule.png");

        JLabel scheduleTitleLabel = new JLabel("Available Services");
        scheduleTitleLabel.setFont(new Font("Montserrat", Font.BOLD, 25));
        scheduleTitleLabel.setBounds(225, 30, 300, 30);

        ImageIcon oilChangePic = new ImageIcon("oilChange.jpg");
        JLabel oilChangeLabel = new JLabel("Oil Change", oilChangePic, JLabel.CENTER);
        oilChangeLabel.setVerticalTextPosition(JLabel.BOTTOM);
        oilChangeLabel.setHorizontalTextPosition(JLabel.CENTER);
        oilChangeLabel.setVerticalAlignment(JLabel.CENTER);
        oilChangeLabel.setHorizontalAlignment(JLabel.CENTER);
        oilChangeLabel.setFont(new Font("Montserrat", Font.BOLD, 12));
        Border oilChangeBorder = BorderFactory.createLineBorder(Color.black, 1);
        oilChangeLabel.setBorder(oilChangeBorder);
        oilChangeLabel.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {

                oilChangeLabel.setForeground(new Color(38, 99, 232));
                Border tempBorder = BorderFactory.createLineBorder(new Color(38, 99, 232), 1);
                oilChangeLabel.setBorder(tempBorder);
            }

            @Override
            public void mouseExited(MouseEvent e) {

                oilChangeLabel.setForeground(Color.black);
                oilChangeLabel.setBorder(oilChangeBorder);
            }

            @Override
            public void mouseClicked(MouseEvent e) {

                JDialog serviceDetails = new JDialog(userHomeFrame, null, true);
                serviceDetails.setUndecorated(true);
                serviceDetails.setLocation(550, 130);
                serviceDetails.setSize(300, 400);
                serviceDetails.setLayout(null);
                serviceDetails.getContentPane().setBackground(Color.white);

                ImageIcon xButton = new ImageIcon("xButton.png");
                JLabel xLabel = new JLabel();
                xLabel.setIcon(xButton);
                xLabel.setBounds(253, 5, 40, 40);
                xLabel.addMouseListener(new MouseAdapter() {

                    @Override
                    public void mouseClicked(MouseEvent e) {

                        serviceDetails.dispose();
                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {

                        ImageIcon changedIcon = changeIconColor(xButton, new Color(38, 99, 232));
                        xLabel.setIcon(changedIcon);
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {

                        xLabel.setIcon(xButton);
                    }
                });

                JLabel header = new JLabel("Oil Change");
                header.setFont(new Font("Montserrat", Font.BOLD, 22));
                header.setBounds(95, 50, 140, 30);

                JLabel subHeading = new JLabel("Potential Materials Needed:");
                subHeading.setFont(new Font("Montserrat", Font.BOLD, 15));
                subHeading.setBounds(55, 105, 200, 30);

                JLabel content = new JLabel(
                        "<html><center> Engine Oil : PHP 1,000 - 3,000<br><br>Oil Filter : PHP 200 - 500</center><html>");
                content.setFont(new Font("Montserrat", Font.PLAIN, 12));
                content.setBounds(70, 130, 300, 100);

                JLabel subHeading2 = new JLabel("Service Charge:");
                subHeading2.setFont(new Font("Montserrat", Font.BOLD, 15));
                subHeading2.setBounds(93, 230, 200, 30);

                JLabel content2 = new JLabel(
                        "PHP 300 - 1,000");
                content2.setFont(new Font("Montserrat", Font.PLAIN, 12));
                content2.setBounds(103, 230, 300, 100);

                JButton backButton = new JButton("Back");
                backButton.setFocusable(false);
                backButton.setFont(new Font("Montserrat", Font.PLAIN, 11));
                backButton.setBounds(70, 320, 70, 28);
                backButton.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {

                        serviceDetails.dispose();
                    }
                });

                JButton availButton = new JButton("Avail");
                availButton.setFocusable(false);
                availButton.setFont(new Font("Montserrat", Font.PLAIN, 11));
                availButton.setBounds(160, 320, 70, 28);
                availButton.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {

                        JDialog vehicleInfoDialog = new JDialog(serviceDetails, null, true);
                        vehicleInfoDialog.setUndecorated(true);
                        vehicleInfoDialog.setLocation(650, 100);
                        vehicleInfoDialog.setSize(350, 450);
                        vehicleInfoDialog.setLayout(null);
                        vehicleInfoDialog.getContentPane().setBackground(Color.white);

                        ImageIcon xButton = new ImageIcon("xButton.png");
                        JLabel xLabel = new JLabel();
                        xLabel.setIcon(xButton);
                        xLabel.setBounds(305, 5, 40, 40);
                        xLabel.addMouseListener(new MouseAdapter() {

                            @Override
                            public void mouseClicked(MouseEvent e) {

                                vehicleInfoDialog.dispose();
                            }

                            @Override
                            public void mouseEntered(MouseEvent e) {

                                ImageIcon changedIcon = changeIconColor(xButton, new Color(38, 99, 232));
                                xLabel.setIcon(changedIcon);
                            }

                            @Override
                            public void mouseExited(MouseEvent e) {

                                xLabel.setIcon(xButton);
                            }
                        });

                        JLabel titleLabel = new JLabel("Vehicle Information");
                        titleLabel.setFont(new Font("Montserrat", Font.BOLD, 20));
                        titleLabel.setForeground(Color.black);
                        titleLabel.setBounds(90, 55, 300, 50);

                        JTextField makeField = new JTextField();
                        makeField.setFont(new Font("Montserrat", Font.PLAIN, 13));
                        makeField.setCaretColor(Color.black);
                        makeField.setBounds(30, 140, 130, 30);
                        makeField.setText("Make");
                        makeField.addFocusListener(new FocusListener() {

                            @Override
                            public void focusGained(FocusEvent e) {

                                if (makeField.getText().equals("Make")) {

                                    makeField.setText("");
                                }
                            }

                            @Override
                            public void focusLost(FocusEvent e) {

                                if (makeField.getText().isEmpty()) {

                                    makeField.setText("Make");
                                }
                            }
                        });

                        JTextField modelField = new JTextField();
                        modelField.setFont(new Font("Montserrat", Font.PLAIN, 13));
                        modelField.setCaretColor(Color.black);
                        modelField.setBounds(190, 140, 130, 30);
                        modelField.setText("Model");
                        modelField.addFocusListener(new FocusListener() {

                            @Override
                            public void focusGained(FocusEvent e) {

                                if (modelField.getText().equals("Model")) {

                                    modelField.setText("");
                                }
                            }

                            @Override
                            public void focusLost(FocusEvent e) {

                                if (modelField.getText().isEmpty()) {

                                    modelField.setText("Model");
                                }
                            }
                        });

                        JTextField yearField = new JTextField();
                        yearField.setFont(new Font("Montserrat", Font.PLAIN, 13));
                        yearField.setCaretColor(Color.black);
                        yearField.setBounds(30, 200, 130, 30);
                        yearField.setText("Year");
                        yearField.addFocusListener(new FocusListener() {

                            @Override
                            public void focusGained(FocusEvent e) {

                                if (yearField.getText().equals("Year")) {

                                    yearField.setText("");
                                }
                            }

                            @Override
                            public void focusLost(FocusEvent e) {

                                if (yearField.getText().isEmpty()) {

                                    yearField.setText("Year");
                                }
                            }
                        });

                        JTextField mileageField = new JTextField();
                        mileageField.setFont(new Font("Montserrat", Font.PLAIN, 13));
                        mileageField.setCaretColor(Color.black);
                        mileageField.setBounds(190, 200, 130, 30);
                        mileageField.setText("Milieage");
                        mileageField.addFocusListener(new FocusListener() {

                            @Override
                            public void focusGained(FocusEvent e) {

                                if (mileageField.getText().equals("Milieage")) {

                                    mileageField.setText("");
                                }
                            }

                            @Override
                            public void focusLost(FocusEvent e) {

                                if (mileageField.getText().isEmpty()) {

                                    mileageField.setText("Milieage");
                                }
                            }
                        });

                        JButton backButton = new JButton("Back");
                        backButton.setFocusable(false);
                        backButton.setFont(new Font("Montserrat", Font.PLAIN, 11));
                        backButton.setBounds(95, 320, 70, 28);
                        backButton.addActionListener(new ActionListener() {

                            @Override
                            public void actionPerformed(ActionEvent e) {

                                vehicleInfoDialog.dispose();

                            }
                        });

                        JButton nextButton = new JButton("Next");
                        nextButton.setFocusable(false);
                        nextButton.setFont(new Font("Montserrat", Font.PLAIN, 11));
                        nextButton.setBounds(185, 320, 70, 28);
                        nextButton.addActionListener(new ActionListener() {

                            @Override
                            public void actionPerformed(ActionEvent e) {

                                String make = makeField.getText();
                                String model = modelField.getText();
                                String year = yearField.getText();
                                double mileage = Double.parseDouble(mileageField.getText());

                                Vehicle vehicle = new Vehicle(make, model, year, mileage);

                                Service service = new OilChangeService(account, vehicle, 1, 1);

                                JDialog dateAndTimeDialog = new JDialog(vehicleInfoDialog, null, true);
                                dateAndTimeDialog.setUndecorated(true);
                                dateAndTimeDialog.setLocation(220, 40);
                                dateAndTimeDialog.setSize(300, 300);
                                dateAndTimeDialog.setLayout(null);
                                dateAndTimeDialog.getContentPane().setBackground(Color.white);

                                ImageIcon xButton = new ImageIcon("xButton.png");
                                JLabel xLabel = new JLabel();
                                xLabel.setIcon(xButton);
                                xLabel.setBounds(255, 5, 40, 40);
                                xLabel.addMouseListener(new MouseAdapter() {

                                    @Override
                                    public void mouseClicked(MouseEvent e) {

                                        dateAndTimeDialog.dispose();
                                    }

                                    @Override
                                    public void mouseEntered(MouseEvent e) {

                                        ImageIcon changedIcon = changeIconColor(xButton, new Color(38, 99, 232));
                                        xLabel.setIcon(changedIcon);
                                    }

                                    @Override
                                    public void mouseExited(MouseEvent e) {

                                        xLabel.setIcon(xButton);
                                    }
                                });

                                JLabel titleLabel = new JLabel("<html><center>Choose<br>Date and Time</center><html>");
                                titleLabel.setFont(new Font("Montserrat", Font.BOLD, 18));
                                titleLabel.setForeground(Color.black);
                                titleLabel.setBounds(90, 55, 300, 50);

                                JTextField dateField = new JTextField();
                                dateField.setFont(new Font("Montserrat", Font.PLAIN, 13));
                                dateField.setCaretColor(Color.black);
                                dateField.setBounds(85, 130, 140, 30);
                                dateField.setText("Date(YYYY-MM-DD)");
                                dateField.addFocusListener(new FocusListener() {

                                    @Override
                                    public void focusGained(FocusEvent e) {

                                        if (dateField.getText().equals("Date(YYYY-MM-DD)")) {

                                            dateField.setText("");
                                        }
                                    }

                                    @Override
                                    public void focusLost(FocusEvent e) {

                                        if (dateField.getText().isEmpty()) {

                                            dateField.setText("Date(YYYY-MM-DD)");
                                        }
                                    }
                                });

                                JTextField timeField = new JTextField();
                                timeField.setFont(new Font("Montserrat", Font.PLAIN, 13));
                                timeField.setCaretColor(Color.black);
                                timeField.setBounds(85, 180, 140, 30);
                                timeField.setText("Time (HH:MM AM/PM)");
                                timeField.addFocusListener(new FocusListener() {

                                    @Override
                                    public void focusGained(FocusEvent e) {

                                        if (timeField.getText().equals("Time (HH:MM AM/PM)")) {

                                            timeField.setText("");
                                        }
                                    }

                                    @Override
                                    public void focusLost(FocusEvent e) {

                                        if (timeField.getText().isEmpty()) {

                                            timeField.setText("Time (HH:MM AM/PM)");
                                        }
                                    }
                                });

                                JButton backButton = new JButton("Back");
                                backButton.setFocusable(false);
                                backButton.setFont(new Font("Montserrat", Font.PLAIN, 11));
                                backButton.setBounds(75, 230, 70, 28);
                                backButton.addActionListener(new ActionListener() {

                                    @Override
                                    public void actionPerformed(ActionEvent e) {

                                        dateAndTimeDialog.dispose();

                                    }
                                });

                                JButton nextButton = new JButton("Submit");
                                nextButton.setFocusable(false);
                                nextButton.setFont(new Font("Montserrat", Font.PLAIN, 10));
                                nextButton.setBounds(165, 230, 70, 28);
                                nextButton.addActionListener(new ActionListener() {

                                    @Override
                                    public void actionPerformed(ActionEvent e) {

                                        String date = dateField.getText();
                                        String time = timeField.getText();

                                        ScheduledService SS = new ScheduledService(service, date, time);
                                        SSList.add(SS);

                                        serviceDetails.dispose();
                                        userHomeFrame.dispose();
                                        userHomePage(account);
                                    }
                                });

                                dateAndTimeDialog.add(xLabel);
                                dateAndTimeDialog.add(titleLabel);
                                dateAndTimeDialog.add(dateField);
                                dateAndTimeDialog.add(timeField);
                                dateAndTimeDialog.add(backButton);
                                dateAndTimeDialog.add(nextButton);
                                dateAndTimeDialog.setVisible(true);
                            }
                        });

                        vehicleInfoDialog.add(xLabel);
                        vehicleInfoDialog.add(titleLabel);
                        vehicleInfoDialog.add(makeField);
                        vehicleInfoDialog.add(modelField);
                        vehicleInfoDialog.add(yearField);
                        vehicleInfoDialog.add(mileageField);
                        vehicleInfoDialog.add(backButton);
                        vehicleInfoDialog.add(nextButton);

                        vehicleInfoDialog.setVisible(true);
                    }
                });

                serviceDetails.add(xLabel);
                serviceDetails.add(header);
                serviceDetails.add(subHeading);
                serviceDetails.add(content);
                serviceDetails.add(subHeading2);
                serviceDetails.add(content2);
                serviceDetails.add(backButton);
                serviceDetails.add(availButton);
                serviceDetails.setVisible(true);
            }
        });
        oilChangeLabel.setBounds(55, 90, 175, 130);

        ImageIcon tireRotationPic = new ImageIcon("tireRotation.jpg");
        JLabel tireRotationLabel = new JLabel("Tire Rotation", tireRotationPic, JLabel.CENTER);
        tireRotationLabel.setVerticalTextPosition(JLabel.BOTTOM);
        tireRotationLabel.setHorizontalTextPosition(JLabel.CENTER);
        tireRotationLabel.setVerticalAlignment(JLabel.CENTER);
        tireRotationLabel.setHorizontalAlignment(JLabel.CENTER);
        tireRotationLabel.setFont(new Font("Montserrat", Font.BOLD, 12));
        Border tireRotationBorder = BorderFactory.createLineBorder(Color.black, 1);
        tireRotationLabel.setBorder(tireRotationBorder);
        tireRotationLabel.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {

                tireRotationLabel.setForeground(new Color(38, 99, 232));
                Border tempBorder = BorderFactory.createLineBorder(new Color(38, 99, 232), 1);
                tireRotationLabel.setBorder(tempBorder);
            }

            @Override
            public void mouseExited(MouseEvent e) {

                tireRotationLabel.setForeground(Color.black);
                tireRotationLabel.setBorder(tireRotationBorder);
            }

            @Override
            public void mouseClicked(MouseEvent e) {

                JDialog serviceDetails = new JDialog(userHomeFrame, null, true);
                serviceDetails.setUndecorated(true);
                serviceDetails.setLocation(550, 130);
                serviceDetails.setSize(300, 400);
                serviceDetails.setLayout(null);
                serviceDetails.getContentPane().setBackground(Color.white);

                ImageIcon xButton = new ImageIcon("xButton.png");
                JLabel xLabel = new JLabel();
                xLabel.setIcon(xButton);
                xLabel.setBounds(253, 5, 40, 40);
                xLabel.addMouseListener(new MouseAdapter() {

                    @Override
                    public void mouseClicked(MouseEvent e) {

                        serviceDetails.dispose();
                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {

                        ImageIcon changedIcon = changeIconColor(xButton, new Color(38, 99, 232));
                        xLabel.setIcon(changedIcon);
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {

                        xLabel.setIcon(xButton);
                    }
                });

                JLabel header = new JLabel("Tire Rotation");
                header.setFont(new Font("Montserrat", Font.BOLD, 22));
                header.setBounds(90, 50, 140, 30);

                JLabel subHeading = new JLabel("Potential Materials Needed:");
                subHeading.setFont(new Font("Montserrat", Font.BOLD, 15));
                subHeading.setBounds(55, 105, 200, 30);

                JLabel content = new JLabel(
                        "No Materials needed");
                content.setFont(new Font("Montserrat", Font.PLAIN, 12));
                content.setBounds(95, 110, 300, 100);

                JLabel subHeading2 = new JLabel("Service Charge:");
                subHeading2.setFont(new Font("Montserrat", Font.BOLD, 15));
                subHeading2.setBounds(93, 190, 200, 30);

                JLabel content2 = new JLabel(
                        "PHP 200 - 500");
                content2.setFont(new Font("Montserrat", Font.PLAIN, 12));
                content2.setBounds(110, 185, 300, 100);

                JButton backButton = new JButton("Back");
                backButton.setFocusable(false);
                backButton.setFont(new Font("Montserrat", Font.PLAIN, 11));
                backButton.setBounds(70, 270, 70, 28);
                backButton.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {

                        serviceDetails.dispose();
                    }
                });

                JButton availButton = new JButton("Avail");
                availButton.setFocusable(false);
                availButton.setFont(new Font("Montserrat", Font.PLAIN, 11));
                availButton.setBounds(160, 270, 70, 28);
                availButton.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {

                        JDialog vehicleInfoDialog = new JDialog(serviceDetails, null, true);
                        vehicleInfoDialog.setUndecorated(true);
                        vehicleInfoDialog.setLocation(650, 100);
                        vehicleInfoDialog.setSize(350, 450);
                        vehicleInfoDialog.setLayout(null);
                        vehicleInfoDialog.getContentPane().setBackground(Color.white);

                        ImageIcon xButton = new ImageIcon("xButton.png");
                        JLabel xLabel = new JLabel();
                        xLabel.setIcon(xButton);
                        xLabel.setBounds(305, 5, 40, 40);
                        xLabel.addMouseListener(new MouseAdapter() {

                            @Override
                            public void mouseClicked(MouseEvent e) {

                                vehicleInfoDialog.dispose();
                            }

                            @Override
                            public void mouseEntered(MouseEvent e) {

                                ImageIcon changedIcon = changeIconColor(xButton, new Color(38, 99, 232));
                                xLabel.setIcon(changedIcon);
                            }

                            @Override
                            public void mouseExited(MouseEvent e) {

                                xLabel.setIcon(xButton);
                            }
                        });

                        JLabel titleLabel = new JLabel("Vehicle Information");
                        titleLabel.setFont(new Font("Montserrat", Font.BOLD, 20));
                        titleLabel.setForeground(Color.black);
                        titleLabel.setBounds(90, 55, 300, 50);

                        JTextField makeField = new JTextField();
                        makeField.setFont(new Font("Montserrat", Font.PLAIN, 13));
                        makeField.setCaretColor(Color.black);
                        makeField.setBounds(30, 140, 130, 30);
                        makeField.setText("Make");
                        makeField.addFocusListener(new FocusListener() {

                            @Override
                            public void focusGained(FocusEvent e) {

                                if (makeField.getText().equals("Make")) {

                                    makeField.setText("");
                                }
                            }

                            @Override
                            public void focusLost(FocusEvent e) {

                                if (makeField.getText().isEmpty()) {

                                    makeField.setText("Make");
                                }
                            }
                        });

                        JTextField modelField = new JTextField();
                        modelField.setFont(new Font("Montserrat", Font.PLAIN, 13));
                        modelField.setCaretColor(Color.black);
                        modelField.setBounds(190, 140, 130, 30);
                        modelField.setText("Model");
                        modelField.addFocusListener(new FocusListener() {

                            @Override
                            public void focusGained(FocusEvent e) {

                                if (modelField.getText().equals("Model")) {

                                    modelField.setText("");
                                }
                            }

                            @Override
                            public void focusLost(FocusEvent e) {

                                if (modelField.getText().isEmpty()) {

                                    modelField.setText("Model");
                                }
                            }
                        });

                        JTextField yearField = new JTextField();
                        yearField.setFont(new Font("Montserrat", Font.PLAIN, 13));
                        yearField.setCaretColor(Color.black);
                        yearField.setBounds(30, 200, 130, 30);
                        yearField.setText("Year");
                        yearField.addFocusListener(new FocusListener() {

                            @Override
                            public void focusGained(FocusEvent e) {

                                if (yearField.getText().equals("Year")) {

                                    yearField.setText("");
                                }
                            }

                            @Override
                            public void focusLost(FocusEvent e) {

                                if (yearField.getText().isEmpty()) {

                                    yearField.setText("Year");
                                }
                            }
                        });

                        JTextField mileageField = new JTextField();
                        mileageField.setFont(new Font("Montserrat", Font.PLAIN, 13));
                        mileageField.setCaretColor(Color.black);
                        mileageField.setBounds(190, 200, 130, 30);
                        mileageField.setText("Milieage");
                        mileageField.addFocusListener(new FocusListener() {

                            @Override
                            public void focusGained(FocusEvent e) {

                                if (mileageField.getText().equals("Milieage")) {

                                    mileageField.setText("");
                                }
                            }

                            @Override
                            public void focusLost(FocusEvent e) {

                                if (mileageField.getText().isEmpty()) {

                                    mileageField.setText("Milieage");
                                }
                            }
                        });

                        JButton backButton = new JButton("Back");
                        backButton.setFocusable(false);
                        backButton.setFont(new Font("Montserrat", Font.PLAIN, 11));
                        backButton.setBounds(95, 320, 70, 28);
                        backButton.addActionListener(new ActionListener() {

                            @Override
                            public void actionPerformed(ActionEvent e) {

                                vehicleInfoDialog.dispose();

                            }
                        });

                        JButton nextButton = new JButton("Next");
                        nextButton.setFocusable(false);
                        nextButton.setFont(new Font("Montserrat", Font.PLAIN, 11));
                        nextButton.setBounds(185, 320, 70, 28);
                        nextButton.addActionListener(new ActionListener() {

                            @Override
                            public void actionPerformed(ActionEvent e) {

                                String make = makeField.getText();
                                String model = modelField.getText();
                                String year = yearField.getText();
                                double mileage = Double.parseDouble(mileageField.getText());

                                Vehicle vehicle = new Vehicle(make, model, year, mileage);

                                Service service = new TireRotationService(account, vehicle);

                                JDialog dateAndTimeDialog = new JDialog(vehicleInfoDialog, null, true);
                                dateAndTimeDialog.setUndecorated(true);
                                dateAndTimeDialog.setLocation(220, 40);
                                dateAndTimeDialog.setSize(300, 300);
                                dateAndTimeDialog.setLayout(null);
                                dateAndTimeDialog.getContentPane().setBackground(Color.white);

                                ImageIcon xButton = new ImageIcon("xButton.png");
                                JLabel xLabel = new JLabel();
                                xLabel.setIcon(xButton);
                                xLabel.setBounds(255, 5, 40, 40);
                                xLabel.addMouseListener(new MouseAdapter() {

                                    @Override
                                    public void mouseClicked(MouseEvent e) {

                                        dateAndTimeDialog.dispose();
                                    }

                                    @Override
                                    public void mouseEntered(MouseEvent e) {

                                        ImageIcon changedIcon = changeIconColor(xButton, new Color(38, 99, 232));
                                        xLabel.setIcon(changedIcon);
                                    }

                                    @Override
                                    public void mouseExited(MouseEvent e) {

                                        xLabel.setIcon(xButton);
                                    }
                                });

                                JLabel titleLabel = new JLabel("<html><center>Choose<br>Date and Time</center><html>");
                                titleLabel.setFont(new Font("Montserrat", Font.BOLD, 18));
                                titleLabel.setForeground(Color.black);
                                titleLabel.setBounds(90, 55, 300, 50);

                                JTextField dateField = new JTextField();
                                dateField.setFont(new Font("Montserrat", Font.PLAIN, 13));
                                dateField.setCaretColor(Color.black);
                                dateField.setBounds(85, 130, 140, 30);
                                dateField.setText("Date(YYYY-MM-DD)");
                                dateField.addFocusListener(new FocusListener() {

                                    @Override
                                    public void focusGained(FocusEvent e) {

                                        if (dateField.getText().equals("Date(YYYY-MM-DD)")) {

                                            dateField.setText("");
                                        }
                                    }

                                    @Override
                                    public void focusLost(FocusEvent e) {

                                        if (dateField.getText().isEmpty()) {

                                            dateField.setText("Date(YYYY-MM-DD)");
                                        }
                                    }
                                });

                                JTextField timeField = new JTextField();
                                timeField.setFont(new Font("Montserrat", Font.PLAIN, 13));
                                timeField.setCaretColor(Color.black);
                                timeField.setBounds(85, 180, 140, 30);
                                timeField.setText("Time (HH:MM AM/PM)");
                                timeField.addFocusListener(new FocusListener() {

                                    @Override
                                    public void focusGained(FocusEvent e) {

                                        if (timeField.getText().equals("Time (HH:MM AM/PM)")) {

                                            timeField.setText("");
                                        }
                                    }

                                    @Override
                                    public void focusLost(FocusEvent e) {

                                        if (timeField.getText().isEmpty()) {

                                            timeField.setText("Time (HH:MM AM/PM)");
                                        }
                                    }
                                });

                                JButton backButton = new JButton("Back");
                                backButton.setFocusable(false);
                                backButton.setFont(new Font("Montserrat", Font.PLAIN, 11));
                                backButton.setBounds(75, 230, 70, 28);
                                backButton.addActionListener(new ActionListener() {

                                    @Override
                                    public void actionPerformed(ActionEvent e) {

                                        dateAndTimeDialog.dispose();

                                    }
                                });

                                JButton nextButton = new JButton("Submit");
                                nextButton.setFocusable(false);
                                nextButton.setFont(new Font("Montserrat", Font.PLAIN, 10));
                                nextButton.setBounds(165, 230, 70, 28);
                                nextButton.addActionListener(new ActionListener() {

                                    @Override
                                    public void actionPerformed(ActionEvent e) {

                                        String date = dateField.getText();
                                        String time = timeField.getText();

                                        ScheduledService SS = new ScheduledService(service, date, time);
                                        SSList.add(SS);

                                        serviceDetails.dispose();
                                        userHomeFrame.dispose();
                                        userHomePage(account);
                                    }
                                });

                                dateAndTimeDialog.add(xLabel);
                                dateAndTimeDialog.add(titleLabel);
                                dateAndTimeDialog.add(dateField);
                                dateAndTimeDialog.add(timeField);
                                dateAndTimeDialog.add(backButton);
                                dateAndTimeDialog.add(nextButton);
                                dateAndTimeDialog.setVisible(true);
                            }
                        });

                        vehicleInfoDialog.add(xLabel);
                        vehicleInfoDialog.add(titleLabel);
                        vehicleInfoDialog.add(makeField);
                        vehicleInfoDialog.add(modelField);
                        vehicleInfoDialog.add(yearField);
                        vehicleInfoDialog.add(mileageField);
                        vehicleInfoDialog.add(backButton);
                        vehicleInfoDialog.add(nextButton);

                        vehicleInfoDialog.setVisible(true);
                    }
                });

                serviceDetails.add(xLabel);
                serviceDetails.add(header);
                serviceDetails.add(subHeading);
                serviceDetails.add(content);
                serviceDetails.add(subHeading2);
                serviceDetails.add(content2);
                serviceDetails.add(backButton);
                serviceDetails.add(availButton);
                serviceDetails.setVisible(true);
            }
        });
        tireRotationLabel.setBounds(245, 90, 175, 130);

        ImageIcon wheelAlignmentPic = new ImageIcon("wheelAlignment.jpg");
        JLabel wheelAlignmentLabel = new JLabel("Wheel Alignment", wheelAlignmentPic, JLabel.CENTER);
        wheelAlignmentLabel.setVerticalTextPosition(JLabel.BOTTOM);
        wheelAlignmentLabel.setHorizontalTextPosition(JLabel.CENTER);
        wheelAlignmentLabel.setVerticalAlignment(JLabel.CENTER);
        wheelAlignmentLabel.setHorizontalAlignment(JLabel.CENTER);
        wheelAlignmentLabel.setFont(new Font("Montserrat", Font.BOLD, 12));
        Border wheelAlignmentBorder = BorderFactory.createLineBorder(Color.black, 1);
        wheelAlignmentLabel.setBorder(wheelAlignmentBorder);
        wheelAlignmentLabel.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {

                wheelAlignmentLabel.setForeground(new Color(38, 99, 232));
                Border tempBorder = BorderFactory.createLineBorder(new Color(38, 99, 232), 1);
                wheelAlignmentLabel.setBorder(tempBorder);
            }

            @Override
            public void mouseExited(MouseEvent e) {

                wheelAlignmentLabel.setForeground(Color.black);
                wheelAlignmentLabel.setBorder(wheelAlignmentBorder);
            }

            @Override
            public void mouseClicked(MouseEvent e) {

                JDialog serviceDetails = new JDialog(userHomeFrame, null, true);
                serviceDetails.setUndecorated(true);
                serviceDetails.setLocation(550, 130);
                serviceDetails.setSize(300, 400);
                serviceDetails.setLayout(null);
                serviceDetails.getContentPane().setBackground(Color.white);

                ImageIcon xButton = new ImageIcon("xButton.png");
                JLabel xLabel = new JLabel();
                xLabel.setIcon(xButton);
                xLabel.setBounds(253, 5, 40, 40);
                xLabel.addMouseListener(new MouseAdapter() {

                    @Override
                    public void mouseClicked(MouseEvent e) {

                        serviceDetails.dispose();
                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {

                        ImageIcon changedIcon = changeIconColor(xButton, new Color(38, 99, 232));
                        xLabel.setIcon(changedIcon);
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {

                        xLabel.setIcon(xButton);
                    }
                });

                JLabel header = new JLabel("Wheel Alignment");
                header.setFont(new Font("Montserrat", Font.BOLD, 20));
                header.setBounds(75, 50, 190, 30);

                JLabel subHeading = new JLabel("Potential Materials Needed:");
                subHeading.setFont(new Font("Montserrat", Font.BOLD, 15));
                subHeading.setBounds(55, 105, 200, 30);

                JLabel content = new JLabel(
                        "No Materials needed");
                content.setFont(new Font("Montserrat", Font.PLAIN, 12));
                content.setBounds(93, 110, 300, 100);

                JLabel subHeading2 = new JLabel("Service Charge:");
                subHeading2.setFont(new Font("Montserrat", Font.BOLD, 15));
                subHeading2.setBounds(93, 190, 200, 30);

                JLabel content2 = new JLabel(
                        "PHP 2,000 - 4,500");
                content2.setFont(new Font("Montserrat", Font.PLAIN, 12));
                content2.setBounds(95, 185, 300, 100);

                JButton backButton = new JButton("Back");
                backButton.setFocusable(false);
                backButton.setFont(new Font("Montserrat", Font.PLAIN, 11));
                backButton.setBounds(70, 270, 70, 28);
                backButton.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {

                        serviceDetails.dispose();
                    }
                });

                JButton availButton = new JButton("Avail");
                availButton.setFocusable(false);
                availButton.setFont(new Font("Montserrat", Font.PLAIN, 11));
                availButton.setBounds(160, 270, 70, 28);
                availButton.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {

                        JDialog vehicleInfoDialog = new JDialog(serviceDetails, null, true);
                        vehicleInfoDialog.setUndecorated(true);
                        vehicleInfoDialog.setLocation(650, 100);
                        vehicleInfoDialog.setSize(350, 450);
                        vehicleInfoDialog.setLayout(null);
                        vehicleInfoDialog.getContentPane().setBackground(Color.white);

                        ImageIcon xButton = new ImageIcon("xButton.png");
                        JLabel xLabel = new JLabel();
                        xLabel.setIcon(xButton);
                        xLabel.setBounds(305, 5, 40, 40);
                        xLabel.addMouseListener(new MouseAdapter() {

                            @Override
                            public void mouseClicked(MouseEvent e) {

                                vehicleInfoDialog.dispose();
                            }

                            @Override
                            public void mouseEntered(MouseEvent e) {

                                ImageIcon changedIcon = changeIconColor(xButton, new Color(38, 99, 232));
                                xLabel.setIcon(changedIcon);
                            }

                            @Override
                            public void mouseExited(MouseEvent e) {

                                xLabel.setIcon(xButton);
                            }
                        });

                        JLabel titleLabel = new JLabel("Vehicle Information");
                        titleLabel.setFont(new Font("Montserrat", Font.BOLD, 20));
                        titleLabel.setForeground(Color.black);
                        titleLabel.setBounds(90, 55, 300, 50);

                        JTextField makeField = new JTextField();
                        makeField.setFont(new Font("Montserrat", Font.PLAIN, 13));
                        makeField.setCaretColor(Color.black);
                        makeField.setBounds(30, 140, 130, 30);
                        makeField.setText("Make");
                        makeField.addFocusListener(new FocusListener() {

                            @Override
                            public void focusGained(FocusEvent e) {

                                if (makeField.getText().equals("Make")) {

                                    makeField.setText("");
                                }
                            }

                            @Override
                            public void focusLost(FocusEvent e) {

                                if (makeField.getText().isEmpty()) {

                                    makeField.setText("Make");
                                }
                            }
                        });

                        JTextField modelField = new JTextField();
                        modelField.setFont(new Font("Montserrat", Font.PLAIN, 13));
                        modelField.setCaretColor(Color.black);
                        modelField.setBounds(190, 140, 130, 30);
                        modelField.setText("Model");
                        modelField.addFocusListener(new FocusListener() {

                            @Override
                            public void focusGained(FocusEvent e) {

                                if (modelField.getText().equals("Model")) {

                                    modelField.setText("");
                                }
                            }

                            @Override
                            public void focusLost(FocusEvent e) {

                                if (modelField.getText().isEmpty()) {

                                    modelField.setText("Model");
                                }
                            }
                        });

                        JTextField yearField = new JTextField();
                        yearField.setFont(new Font("Montserrat", Font.PLAIN, 13));
                        yearField.setCaretColor(Color.black);
                        yearField.setBounds(30, 200, 130, 30);
                        yearField.setText("Year");
                        yearField.addFocusListener(new FocusListener() {

                            @Override
                            public void focusGained(FocusEvent e) {

                                if (yearField.getText().equals("Year")) {

                                    yearField.setText("");
                                }
                            }

                            @Override
                            public void focusLost(FocusEvent e) {

                                if (yearField.getText().isEmpty()) {

                                    yearField.setText("Year");
                                }
                            }
                        });

                        JTextField mileageField = new JTextField();
                        mileageField.setFont(new Font("Montserrat", Font.PLAIN, 13));
                        mileageField.setCaretColor(Color.black);
                        mileageField.setBounds(190, 200, 130, 30);
                        mileageField.setText("Milieage");
                        mileageField.addFocusListener(new FocusListener() {

                            @Override
                            public void focusGained(FocusEvent e) {

                                if (mileageField.getText().equals("Milieage")) {

                                    mileageField.setText("");
                                }
                            }

                            @Override
                            public void focusLost(FocusEvent e) {

                                if (mileageField.getText().isEmpty()) {

                                    mileageField.setText("Milieage");
                                }
                            }
                        });

                        JButton backButton = new JButton("Back");
                        backButton.setFocusable(false);
                        backButton.setFont(new Font("Montserrat", Font.PLAIN, 11));
                        backButton.setBounds(95, 320, 70, 28);
                        backButton.addActionListener(new ActionListener() {

                            @Override
                            public void actionPerformed(ActionEvent e) {

                                vehicleInfoDialog.dispose();

                            }
                        });

                        JButton nextButton = new JButton("Next");
                        nextButton.setFocusable(false);
                        nextButton.setFont(new Font("Montserrat", Font.PLAIN, 11));
                        nextButton.setBounds(185, 320, 70, 28);
                        nextButton.addActionListener(new ActionListener() {

                            @Override
                            public void actionPerformed(ActionEvent e) {

                                String make = makeField.getText();
                                String model = modelField.getText();
                                String year = yearField.getText();
                                double mileage = Double.parseDouble(mileageField.getText());

                                Vehicle vehicle = new Vehicle(make, model, year, mileage);

                                Service service = new WheelAlignmentService(account, vehicle);

                                JDialog dateAndTimeDialog = new JDialog(vehicleInfoDialog, null, true);
                                dateAndTimeDialog.setUndecorated(true);
                                dateAndTimeDialog.setLocation(220, 40);
                                dateAndTimeDialog.setSize(300, 300);
                                dateAndTimeDialog.setLayout(null);
                                dateAndTimeDialog.getContentPane().setBackground(Color.white);

                                ImageIcon xButton = new ImageIcon("xButton.png");
                                JLabel xLabel = new JLabel();
                                xLabel.setIcon(xButton);
                                xLabel.setBounds(255, 5, 40, 40);
                                xLabel.addMouseListener(new MouseAdapter() {

                                    @Override
                                    public void mouseClicked(MouseEvent e) {

                                        dateAndTimeDialog.dispose();
                                    }

                                    @Override
                                    public void mouseEntered(MouseEvent e) {

                                        ImageIcon changedIcon = changeIconColor(xButton, new Color(38, 99, 232));
                                        xLabel.setIcon(changedIcon);
                                    }

                                    @Override
                                    public void mouseExited(MouseEvent e) {

                                        xLabel.setIcon(xButton);
                                    }
                                });

                                JLabel titleLabel = new JLabel("<html><center>Choose<br>Date and Time</center><html>");
                                titleLabel.setFont(new Font("Montserrat", Font.BOLD, 18));
                                titleLabel.setForeground(Color.black);
                                titleLabel.setBounds(90, 55, 300, 50);

                                JTextField dateField = new JTextField();
                                dateField.setFont(new Font("Montserrat", Font.PLAIN, 13));
                                dateField.setCaretColor(Color.black);
                                dateField.setBounds(85, 130, 140, 30);
                                dateField.setText("Date(YYYY-MM-DD)");
                                dateField.addFocusListener(new FocusListener() {

                                    @Override
                                    public void focusGained(FocusEvent e) {

                                        if (dateField.getText().equals("Date(YYYY-MM-DD)")) {

                                            dateField.setText("");
                                        }
                                    }

                                    @Override
                                    public void focusLost(FocusEvent e) {

                                        if (dateField.getText().isEmpty()) {

                                            dateField.setText("Date(YYYY-MM-DD)");
                                        }
                                    }
                                });

                                JTextField timeField = new JTextField();
                                timeField.setFont(new Font("Montserrat", Font.PLAIN, 13));
                                timeField.setCaretColor(Color.black);
                                timeField.setBounds(85, 180, 140, 30);
                                timeField.setText("Time (HH:MM AM/PM)");
                                timeField.addFocusListener(new FocusListener() {

                                    @Override
                                    public void focusGained(FocusEvent e) {

                                        if (timeField.getText().equals("Time (HH:MM AM/PM)")) {

                                            timeField.setText("");
                                        }
                                    }

                                    @Override
                                    public void focusLost(FocusEvent e) {

                                        if (timeField.getText().isEmpty()) {

                                            timeField.setText("Time (HH:MM AM/PM)");
                                        }
                                    }
                                });

                                JButton backButton = new JButton("Back");
                                backButton.setFocusable(false);
                                backButton.setFont(new Font("Montserrat", Font.PLAIN, 11));
                                backButton.setBounds(75, 230, 70, 28);
                                backButton.addActionListener(new ActionListener() {

                                    @Override
                                    public void actionPerformed(ActionEvent e) {

                                        dateAndTimeDialog.dispose();

                                    }
                                });

                                JButton nextButton = new JButton("Submit");
                                nextButton.setFocusable(false);
                                nextButton.setFont(new Font("Montserrat", Font.PLAIN, 10));
                                nextButton.setBounds(165, 230, 70, 28);
                                nextButton.addActionListener(new ActionListener() {

                                    @Override
                                    public void actionPerformed(ActionEvent e) {

                                        String date = dateField.getText();
                                        String time = timeField.getText();

                                        ScheduledService SS = new ScheduledService(service, date, time);
                                        SSList.add(SS);

                                        serviceDetails.dispose();
                                        userHomeFrame.dispose();
                                        userHomePage(account);
                                    }
                                });

                                dateAndTimeDialog.add(xLabel);
                                dateAndTimeDialog.add(titleLabel);
                                dateAndTimeDialog.add(dateField);
                                dateAndTimeDialog.add(timeField);
                                dateAndTimeDialog.add(backButton);
                                dateAndTimeDialog.add(nextButton);
                                dateAndTimeDialog.setVisible(true);
                            }
                        });

                        vehicleInfoDialog.add(xLabel);
                        vehicleInfoDialog.add(titleLabel);
                        vehicleInfoDialog.add(makeField);
                        vehicleInfoDialog.add(modelField);
                        vehicleInfoDialog.add(yearField);
                        vehicleInfoDialog.add(mileageField);
                        vehicleInfoDialog.add(backButton);
                        vehicleInfoDialog.add(nextButton);

                        vehicleInfoDialog.setVisible(true);
                    }
                });

                serviceDetails.add(xLabel);
                serviceDetails.add(header);
                serviceDetails.add(subHeading);
                serviceDetails.add(content);
                serviceDetails.add(subHeading2);
                serviceDetails.add(content2);
                serviceDetails.add(backButton);
                serviceDetails.add(availButton);
                serviceDetails.setVisible(true);
            }
        });
        wheelAlignmentLabel.setBounds(435, 90, 175, 130);

        ImageIcon brakeServicePic = new ImageIcon("brakeService.jpg");
        JLabel brakeServiceLabel = new JLabel("Brake Service", brakeServicePic, JLabel.CENTER);
        brakeServiceLabel.setVerticalTextPosition(JLabel.BOTTOM);
        brakeServiceLabel.setHorizontalTextPosition(JLabel.CENTER);
        brakeServiceLabel.setVerticalAlignment(JLabel.CENTER);
        brakeServiceLabel.setHorizontalAlignment(JLabel.CENTER);
        brakeServiceLabel.setFont(new Font("Montserrat", Font.BOLD, 12));
        Border brakeServiceBorder = BorderFactory.createLineBorder(Color.black, 1);
        brakeServiceLabel.setBorder(brakeServiceBorder);
        brakeServiceLabel.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {

                brakeServiceLabel.setForeground(new Color(38, 99, 232));
                Border tempBorder = BorderFactory.createLineBorder(new Color(38, 99, 232), 1);
                brakeServiceLabel.setBorder(tempBorder);
            }

            @Override
            public void mouseExited(MouseEvent e) {

                brakeServiceLabel.setForeground(Color.black);
                brakeServiceLabel.setBorder(brakeServiceBorder);
            }

            @Override
            public void mouseClicked(MouseEvent e) {

                JDialog serviceDetails = new JDialog(userHomeFrame, null, true);
                serviceDetails.setUndecorated(true);
                serviceDetails.setLocation(550, 130);
                serviceDetails.setSize(300, 400);
                serviceDetails.setLayout(null);
                serviceDetails.getContentPane().setBackground(Color.white);

                ImageIcon xButton = new ImageIcon("xButton.png");
                JLabel xLabel = new JLabel();
                xLabel.setIcon(xButton);
                xLabel.setBounds(253, 5, 40, 40);
                xLabel.addMouseListener(new MouseAdapter() {

                    @Override
                    public void mouseClicked(MouseEvent e) {

                        serviceDetails.dispose();
                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {

                        ImageIcon changedIcon = changeIconColor(xButton, new Color(38, 99, 232));
                        xLabel.setIcon(changedIcon);
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {

                        xLabel.setIcon(xButton);
                    }
                });

                JLabel header = new JLabel("Brake Service");
                header.setFont(new Font("Montserrat", Font.BOLD, 20));
                header.setBounds(90, 50, 160, 30);

                JLabel subHeading = new JLabel("Potential Materials Needed:");
                subHeading.setFont(new Font("Montserrat", Font.BOLD, 15));
                subHeading.setBounds(55, 105, 200, 30);

                JLabel content = new JLabel(
                        "<html><center>Brake Pads : PHP 1,500 - 3,0000 (per axle)<br><br>Brake Rotors : PHP 2,000 - 5,000 (per axle)<br><br>Brake Fluid : PHP 500 - 1,000</center><html>");
                content.setFont(new Font("Montserrat", Font.PLAIN, 12));
                content.setBounds(40, 130, 300, 100);

                JLabel subHeading2 = new JLabel("Service Charge:");
                subHeading2.setFont(new Font("Montserrat", Font.BOLD, 15));
                subHeading2.setBounds(93, 240, 200, 30);

                JLabel content2 = new JLabel(
                        "PHP 500 - 1,500 (per axle)");
                content2.setFont(new Font("Montserrat", Font.PLAIN, 12));
                content2.setBounds(80, 230, 300, 100);

                JButton backButton = new JButton("Back");
                backButton.setFocusable(false);
                backButton.setFont(new Font("Montserrat", Font.PLAIN, 11));
                backButton.setBounds(70, 320, 70, 28);
                backButton.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {

                        serviceDetails.dispose();
                    }
                });

                JButton availButton = new JButton("Avail");
                availButton.setFocusable(false);
                availButton.setFont(new Font("Montserrat", Font.PLAIN, 11));
                availButton.setBounds(160, 320, 70, 28);
                availButton.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {

                        JDialog vehicleInfoDialog = new JDialog(serviceDetails, null, true);
                        vehicleInfoDialog.setUndecorated(true);
                        vehicleInfoDialog.setLocation(650, 100);
                        vehicleInfoDialog.setSize(350, 450);
                        vehicleInfoDialog.setLayout(null);
                        vehicleInfoDialog.getContentPane().setBackground(Color.white);

                        ImageIcon xButton = new ImageIcon("xButton.png");
                        JLabel xLabel = new JLabel();
                        xLabel.setIcon(xButton);
                        xLabel.setBounds(305, 5, 40, 40);
                        xLabel.addMouseListener(new MouseAdapter() {

                            @Override
                            public void mouseClicked(MouseEvent e) {

                                vehicleInfoDialog.dispose();
                            }

                            @Override
                            public void mouseEntered(MouseEvent e) {

                                ImageIcon changedIcon = changeIconColor(xButton, new Color(38, 99, 232));
                                xLabel.setIcon(changedIcon);
                            }

                            @Override
                            public void mouseExited(MouseEvent e) {

                                xLabel.setIcon(xButton);
                            }
                        });

                        JLabel titleLabel = new JLabel("Vehicle Information");
                        titleLabel.setFont(new Font("Montserrat", Font.BOLD, 20));
                        titleLabel.setForeground(Color.black);
                        titleLabel.setBounds(90, 55, 300, 50);

                        JTextField makeField = new JTextField();
                        makeField.setFont(new Font("Montserrat", Font.PLAIN, 13));
                        makeField.setCaretColor(Color.black);
                        makeField.setBounds(30, 140, 130, 30);
                        makeField.setText("Make");
                        makeField.addFocusListener(new FocusListener() {

                            @Override
                            public void focusGained(FocusEvent e) {

                                if (makeField.getText().equals("Make")) {

                                    makeField.setText("");
                                }
                            }

                            @Override
                            public void focusLost(FocusEvent e) {

                                if (makeField.getText().isEmpty()) {

                                    makeField.setText("Make");
                                }
                            }
                        });

                        JTextField modelField = new JTextField();
                        modelField.setFont(new Font("Montserrat", Font.PLAIN, 13));
                        modelField.setCaretColor(Color.black);
                        modelField.setBounds(190, 140, 130, 30);
                        modelField.setText("Model");
                        modelField.addFocusListener(new FocusListener() {

                            @Override
                            public void focusGained(FocusEvent e) {

                                if (modelField.getText().equals("Model")) {

                                    modelField.setText("");
                                }
                            }

                            @Override
                            public void focusLost(FocusEvent e) {

                                if (modelField.getText().isEmpty()) {

                                    modelField.setText("Model");
                                }
                            }
                        });

                        JTextField yearField = new JTextField();
                        yearField.setFont(new Font("Montserrat", Font.PLAIN, 13));
                        yearField.setCaretColor(Color.black);
                        yearField.setBounds(30, 200, 130, 30);
                        yearField.setText("Year");
                        yearField.addFocusListener(new FocusListener() {

                            @Override
                            public void focusGained(FocusEvent e) {

                                if (yearField.getText().equals("Year")) {

                                    yearField.setText("");
                                }
                            }

                            @Override
                            public void focusLost(FocusEvent e) {

                                if (yearField.getText().isEmpty()) {

                                    yearField.setText("Year");
                                }
                            }
                        });

                        JTextField mileageField = new JTextField();
                        mileageField.setFont(new Font("Montserrat", Font.PLAIN, 13));
                        mileageField.setCaretColor(Color.black);
                        mileageField.setBounds(190, 200, 130, 30);
                        mileageField.setText("Milieage");
                        mileageField.addFocusListener(new FocusListener() {

                            @Override
                            public void focusGained(FocusEvent e) {

                                if (mileageField.getText().equals("Milieage")) {

                                    mileageField.setText("");
                                }
                            }

                            @Override
                            public void focusLost(FocusEvent e) {

                                if (mileageField.getText().isEmpty()) {

                                    mileageField.setText("Milieage");
                                }
                            }
                        });

                        JButton backButton = new JButton("Back");
                        backButton.setFocusable(false);
                        backButton.setFont(new Font("Montserrat", Font.PLAIN, 11));
                        backButton.setBounds(95, 320, 70, 28);
                        backButton.addActionListener(new ActionListener() {

                            @Override
                            public void actionPerformed(ActionEvent e) {

                                vehicleInfoDialog.dispose();

                            }
                        });

                        JButton nextButton = new JButton("Next");
                        nextButton.setFocusable(false);
                        nextButton.setFont(new Font("Montserrat", Font.PLAIN, 11));
                        nextButton.setBounds(185, 320, 70, 28);
                        nextButton.addActionListener(new ActionListener() {

                            @Override
                            public void actionPerformed(ActionEvent e) {

                                String make = makeField.getText();
                                String model = modelField.getText();
                                String year = yearField.getText();
                                double mileage = Double.parseDouble(mileageField.getText());

                                Vehicle vehicle = new Vehicle(make, model, year, mileage);

                                Service service = new BrakeService(account, vehicle, 1, 1, 1);

                                JDialog dateAndTimeDialog = new JDialog(vehicleInfoDialog, null, true);
                                dateAndTimeDialog.setUndecorated(true);
                                dateAndTimeDialog.setLocation(220, 40);
                                dateAndTimeDialog.setSize(300, 300);
                                dateAndTimeDialog.setLayout(null);
                                dateAndTimeDialog.getContentPane().setBackground(Color.white);

                                ImageIcon xButton = new ImageIcon("xButton.png");
                                JLabel xLabel = new JLabel();
                                xLabel.setIcon(xButton);
                                xLabel.setBounds(255, 5, 40, 40);
                                xLabel.addMouseListener(new MouseAdapter() {

                                    @Override
                                    public void mouseClicked(MouseEvent e) {

                                        dateAndTimeDialog.dispose();
                                    }

                                    @Override
                                    public void mouseEntered(MouseEvent e) {

                                        ImageIcon changedIcon = changeIconColor(xButton, new Color(38, 99, 232));
                                        xLabel.setIcon(changedIcon);
                                    }

                                    @Override
                                    public void mouseExited(MouseEvent e) {

                                        xLabel.setIcon(xButton);
                                    }
                                });

                                JLabel titleLabel = new JLabel("<html><center>Choose<br>Date and Time</center><html>");
                                titleLabel.setFont(new Font("Montserrat", Font.BOLD, 18));
                                titleLabel.setForeground(Color.black);
                                titleLabel.setBounds(90, 55, 300, 50);

                                JTextField dateField = new JTextField();
                                dateField.setFont(new Font("Montserrat", Font.PLAIN, 13));
                                dateField.setCaretColor(Color.black);
                                dateField.setBounds(85, 130, 140, 30);
                                dateField.setText("Date(YYYY-MM-DD)");
                                dateField.addFocusListener(new FocusListener() {

                                    @Override
                                    public void focusGained(FocusEvent e) {

                                        if (dateField.getText().equals("Date(YYYY-MM-DD)")) {

                                            dateField.setText("");
                                        }
                                    }

                                    @Override
                                    public void focusLost(FocusEvent e) {

                                        if (dateField.getText().isEmpty()) {

                                            dateField.setText("Date(YYYY-MM-DD)");
                                        }
                                    }
                                });

                                JTextField timeField = new JTextField();
                                timeField.setFont(new Font("Montserrat", Font.PLAIN, 13));
                                timeField.setCaretColor(Color.black);
                                timeField.setBounds(85, 180, 140, 30);
                                timeField.setText("Time (HH:MM AM/PM)");
                                timeField.addFocusListener(new FocusListener() {

                                    @Override
                                    public void focusGained(FocusEvent e) {

                                        if (timeField.getText().equals("Time (HH:MM AM/PM)")) {

                                            timeField.setText("");
                                        }
                                    }

                                    @Override
                                    public void focusLost(FocusEvent e) {

                                        if (timeField.getText().isEmpty()) {

                                            timeField.setText("Time (HH:MM AM/PM)");
                                        }
                                    }
                                });

                                JButton backButton = new JButton("Back");
                                backButton.setFocusable(false);
                                backButton.setFont(new Font("Montserrat", Font.PLAIN, 11));
                                backButton.setBounds(75, 230, 70, 28);
                                backButton.addActionListener(new ActionListener() {

                                    @Override
                                    public void actionPerformed(ActionEvent e) {

                                        dateAndTimeDialog.dispose();

                                    }
                                });

                                JButton nextButton = new JButton("Submit");
                                nextButton.setFocusable(false);
                                nextButton.setFont(new Font("Montserrat", Font.PLAIN, 10));
                                nextButton.setBounds(165, 230, 70, 28);
                                nextButton.addActionListener(new ActionListener() {

                                    @Override
                                    public void actionPerformed(ActionEvent e) {

                                        String date = dateField.getText();
                                        String time = timeField.getText();

                                        ScheduledService SS = new ScheduledService(service, date, time);
                                        SSList.add(SS);

                                        serviceDetails.dispose();
                                        userHomeFrame.dispose();
                                        userHomePage(account);
                                    }
                                });

                                dateAndTimeDialog.add(xLabel);
                                dateAndTimeDialog.add(titleLabel);
                                dateAndTimeDialog.add(dateField);
                                dateAndTimeDialog.add(timeField);
                                dateAndTimeDialog.add(backButton);
                                dateAndTimeDialog.add(nextButton);
                                dateAndTimeDialog.setVisible(true);
                            }
                        });

                        vehicleInfoDialog.add(xLabel);
                        vehicleInfoDialog.add(titleLabel);
                        vehicleInfoDialog.add(makeField);
                        vehicleInfoDialog.add(modelField);
                        vehicleInfoDialog.add(yearField);
                        vehicleInfoDialog.add(mileageField);
                        vehicleInfoDialog.add(backButton);
                        vehicleInfoDialog.add(nextButton);

                        vehicleInfoDialog.setVisible(true);
                    }
                });

                serviceDetails.add(xLabel);
                serviceDetails.add(header);
                serviceDetails.add(subHeading);
                serviceDetails.add(content);
                serviceDetails.add(subHeading2);
                serviceDetails.add(content2);
                serviceDetails.add(backButton);
                serviceDetails.add(availButton);
                serviceDetails.setVisible(true);
            }
        });
        brakeServiceLabel.setBounds(55, 230, 175, 130);

        ImageIcon batteryReplacementPic = new ImageIcon("batteryReplacement.jpg");
        JLabel batteryReplacementLabel = new JLabel("Battery Replacement", batteryReplacementPic, JLabel.CENTER);
        batteryReplacementLabel.setVerticalTextPosition(JLabel.BOTTOM);
        batteryReplacementLabel.setHorizontalTextPosition(JLabel.CENTER);
        batteryReplacementLabel.setVerticalAlignment(JLabel.CENTER);
        batteryReplacementLabel.setHorizontalAlignment(JLabel.CENTER);
        batteryReplacementLabel.setFont(new Font("Montserrat", Font.BOLD, 12));
        Border batteryReplacementBorder = BorderFactory.createLineBorder(Color.black, 1);
        batteryReplacementLabel.setBorder(batteryReplacementBorder);
        batteryReplacementLabel.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {

                batteryReplacementLabel.setForeground(new Color(38, 99, 232));
                Border tempBorder = BorderFactory.createLineBorder(new Color(38, 99, 232), 1);
                batteryReplacementLabel.setBorder(tempBorder);
            }

            @Override
            public void mouseExited(MouseEvent e) {

                batteryReplacementLabel.setForeground(Color.black);
                batteryReplacementLabel.setBorder(batteryReplacementBorder);
            }

            @Override
            public void mouseClicked(MouseEvent e) {

                JDialog serviceDetails = new JDialog(userHomeFrame, null, true);
                serviceDetails.setUndecorated(true);
                serviceDetails.setLocation(550, 130);
                serviceDetails.setSize(300, 400);
                serviceDetails.setLayout(null);
                serviceDetails.getContentPane().setBackground(Color.white);

                ImageIcon xButton = new ImageIcon("xButton.png");
                JLabel xLabel = new JLabel();
                xLabel.setIcon(xButton);
                xLabel.setBounds(253, 5, 40, 40);
                xLabel.addMouseListener(new MouseAdapter() {

                    @Override
                    public void mouseClicked(MouseEvent e) {

                        serviceDetails.dispose();
                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {

                        ImageIcon changedIcon = changeIconColor(xButton, new Color(38, 99, 232));
                        xLabel.setIcon(changedIcon);
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {

                        xLabel.setIcon(xButton);
                    }
                });

                JLabel header = new JLabel("Battery Replacement");
                header.setFont(new Font("Montserrat", Font.BOLD, 18));
                header.setBounds(60, 50, 190, 30);

                JLabel subHeading = new JLabel("Potential Materials Needed:");
                subHeading.setFont(new Font("Montserrat", Font.BOLD, 15));
                subHeading.setBounds(55, 105, 200, 30);

                JLabel content = new JLabel(
                        "Car Battery : PHP 3,000 - 8,000");
                content.setFont(new Font("Montserrat", Font.PLAIN, 12));
                content.setBounds(65, 110, 300, 100);

                JLabel subHeading2 = new JLabel("Service Charge:");
                subHeading2.setFont(new Font("Montserrat", Font.BOLD, 15));
                subHeading2.setBounds(93, 190, 200, 30);

                JLabel content2 = new JLabel(
                        "PHP 200 - 500");
                content2.setFont(new Font("Montserrat", Font.PLAIN, 12));
                content2.setBounds(110, 185, 300, 100);

                JButton backButton = new JButton("Back");
                backButton.setFocusable(false);
                backButton.setFont(new Font("Montserrat", Font.PLAIN, 11));
                backButton.setBounds(70, 270, 70, 28);
                backButton.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {

                        serviceDetails.dispose();
                    }
                });

                JButton availButton = new JButton("Avail");
                availButton.setFocusable(false);
                availButton.setFont(new Font("Montserrat", Font.PLAIN, 11));
                availButton.setBounds(160, 270, 70, 28);
                availButton.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {

                        JDialog vehicleInfoDialog = new JDialog(serviceDetails, null, true);
                        vehicleInfoDialog.setUndecorated(true);
                        vehicleInfoDialog.setLocation(650, 100);
                        vehicleInfoDialog.setSize(350, 450);
                        vehicleInfoDialog.setLayout(null);
                        vehicleInfoDialog.getContentPane().setBackground(Color.white);

                        ImageIcon xButton = new ImageIcon("xButton.png");
                        JLabel xLabel = new JLabel();
                        xLabel.setIcon(xButton);
                        xLabel.setBounds(305, 5, 40, 40);
                        xLabel.addMouseListener(new MouseAdapter() {

                            @Override
                            public void mouseClicked(MouseEvent e) {

                                vehicleInfoDialog.dispose();
                            }

                            @Override
                            public void mouseEntered(MouseEvent e) {

                                ImageIcon changedIcon = changeIconColor(xButton, new Color(38, 99, 232));
                                xLabel.setIcon(changedIcon);
                            }

                            @Override
                            public void mouseExited(MouseEvent e) {

                                xLabel.setIcon(xButton);
                            }
                        });

                        JLabel titleLabel = new JLabel("Vehicle Information");
                        titleLabel.setFont(new Font("Montserrat", Font.BOLD, 20));
                        titleLabel.setForeground(Color.black);
                        titleLabel.setBounds(90, 55, 300, 50);

                        JTextField makeField = new JTextField();
                        makeField.setFont(new Font("Montserrat", Font.PLAIN, 13));
                        makeField.setCaretColor(Color.black);
                        makeField.setBounds(30, 140, 130, 30);
                        makeField.setText("Make");
                        makeField.addFocusListener(new FocusListener() {

                            @Override
                            public void focusGained(FocusEvent e) {

                                if (makeField.getText().equals("Make")) {

                                    makeField.setText("");
                                }
                            }

                            @Override
                            public void focusLost(FocusEvent e) {

                                if (makeField.getText().isEmpty()) {

                                    makeField.setText("Make");
                                }
                            }
                        });

                        JTextField modelField = new JTextField();
                        modelField.setFont(new Font("Montserrat", Font.PLAIN, 13));
                        modelField.setCaretColor(Color.black);
                        modelField.setBounds(190, 140, 130, 30);
                        modelField.setText("Model");
                        modelField.addFocusListener(new FocusListener() {

                            @Override
                            public void focusGained(FocusEvent e) {

                                if (modelField.getText().equals("Model")) {

                                    modelField.setText("");
                                }
                            }

                            @Override
                            public void focusLost(FocusEvent e) {

                                if (modelField.getText().isEmpty()) {

                                    modelField.setText("Model");
                                }
                            }
                        });

                        JTextField yearField = new JTextField();
                        yearField.setFont(new Font("Montserrat", Font.PLAIN, 13));
                        yearField.setCaretColor(Color.black);
                        yearField.setBounds(30, 200, 130, 30);
                        yearField.setText("Year");
                        yearField.addFocusListener(new FocusListener() {

                            @Override
                            public void focusGained(FocusEvent e) {

                                if (yearField.getText().equals("Year")) {

                                    yearField.setText("");
                                }
                            }

                            @Override
                            public void focusLost(FocusEvent e) {

                                if (yearField.getText().isEmpty()) {

                                    yearField.setText("Year");
                                }
                            }
                        });

                        JTextField mileageField = new JTextField();
                        mileageField.setFont(new Font("Montserrat", Font.PLAIN, 13));
                        mileageField.setCaretColor(Color.black);
                        mileageField.setBounds(190, 200, 130, 30);
                        mileageField.setText("Milieage");
                        mileageField.addFocusListener(new FocusListener() {

                            @Override
                            public void focusGained(FocusEvent e) {

                                if (mileageField.getText().equals("Milieage")) {

                                    mileageField.setText("");
                                }
                            }

                            @Override
                            public void focusLost(FocusEvent e) {

                                if (mileageField.getText().isEmpty()) {

                                    mileageField.setText("Milieage");
                                }
                            }
                        });

                        JButton backButton = new JButton("Back");
                        backButton.setFocusable(false);
                        backButton.setFont(new Font("Montserrat", Font.PLAIN, 11));
                        backButton.setBounds(95, 320, 70, 28);
                        backButton.addActionListener(new ActionListener() {

                            @Override
                            public void actionPerformed(ActionEvent e) {

                                vehicleInfoDialog.dispose();

                            }
                        });

                        JButton nextButton = new JButton("Next");
                        nextButton.setFocusable(false);
                        nextButton.setFont(new Font("Montserrat", Font.PLAIN, 11));
                        nextButton.setBounds(185, 320, 70, 28);
                        nextButton.addActionListener(new ActionListener() {

                            @Override
                            public void actionPerformed(ActionEvent e) {

                                String make = makeField.getText();
                                String model = modelField.getText();
                                String year = yearField.getText();
                                double mileage = Double.parseDouble(mileageField.getText());

                                Vehicle vehicle = new Vehicle(make, model, year, mileage);

                                Service service = new BatteryReplacementService(account, vehicle, 1);

                                JDialog dateAndTimeDialog = new JDialog(vehicleInfoDialog, null, true);
                                dateAndTimeDialog.setUndecorated(true);
                                dateAndTimeDialog.setLocation(220, 40);
                                dateAndTimeDialog.setSize(300, 300);
                                dateAndTimeDialog.setLayout(null);
                                dateAndTimeDialog.getContentPane().setBackground(Color.white);

                                ImageIcon xButton = new ImageIcon("xButton.png");
                                JLabel xLabel = new JLabel();
                                xLabel.setIcon(xButton);
                                xLabel.setBounds(255, 5, 40, 40);
                                xLabel.addMouseListener(new MouseAdapter() {

                                    @Override
                                    public void mouseClicked(MouseEvent e) {

                                        dateAndTimeDialog.dispose();
                                    }

                                    @Override
                                    public void mouseEntered(MouseEvent e) {

                                        ImageIcon changedIcon = changeIconColor(xButton, new Color(38, 99, 232));
                                        xLabel.setIcon(changedIcon);
                                    }

                                    @Override
                                    public void mouseExited(MouseEvent e) {

                                        xLabel.setIcon(xButton);
                                    }
                                });

                                JLabel titleLabel = new JLabel("<html><center>Choose<br>Date and Time</center><html>");
                                titleLabel.setFont(new Font("Montserrat", Font.BOLD, 18));
                                titleLabel.setForeground(Color.black);
                                titleLabel.setBounds(90, 55, 300, 50);

                                JTextField dateField = new JTextField();
                                dateField.setFont(new Font("Montserrat", Font.PLAIN, 13));
                                dateField.setCaretColor(Color.black);
                                dateField.setBounds(85, 130, 140, 30);
                                dateField.setText("Date(YYYY-MM-DD)");
                                dateField.addFocusListener(new FocusListener() {

                                    @Override
                                    public void focusGained(FocusEvent e) {

                                        if (dateField.getText().equals("Date(YYYY-MM-DD)")) {

                                            dateField.setText("");
                                        }
                                    }

                                    @Override
                                    public void focusLost(FocusEvent e) {

                                        if (dateField.getText().isEmpty()) {

                                            dateField.setText("Date(YYYY-MM-DD)");
                                        }
                                    }
                                });

                                JTextField timeField = new JTextField();
                                timeField.setFont(new Font("Montserrat", Font.PLAIN, 13));
                                timeField.setCaretColor(Color.black);
                                timeField.setBounds(85, 180, 140, 30);
                                timeField.setText("Time (HH:MM AM/PM)");
                                timeField.addFocusListener(new FocusListener() {

                                    @Override
                                    public void focusGained(FocusEvent e) {

                                        if (timeField.getText().equals("Time (HH:MM AM/PM)")) {

                                            timeField.setText("");
                                        }
                                    }

                                    @Override
                                    public void focusLost(FocusEvent e) {

                                        if (timeField.getText().isEmpty()) {

                                            timeField.setText("Time (HH:MM AM/PM)");
                                        }
                                    }
                                });

                                JButton backButton = new JButton("Back");
                                backButton.setFocusable(false);
                                backButton.setFont(new Font("Montserrat", Font.PLAIN, 11));
                                backButton.setBounds(75, 230, 70, 28);
                                backButton.addActionListener(new ActionListener() {

                                    @Override
                                    public void actionPerformed(ActionEvent e) {

                                        dateAndTimeDialog.dispose();

                                    }
                                });

                                JButton nextButton = new JButton("Submit");
                                nextButton.setFocusable(false);
                                nextButton.setFont(new Font("Montserrat", Font.PLAIN, 10));
                                nextButton.setBounds(165, 230, 70, 28);
                                nextButton.addActionListener(new ActionListener() {

                                    @Override
                                    public void actionPerformed(ActionEvent e) {

                                        String date = dateField.getText();
                                        String time = timeField.getText();

                                        ScheduledService SS = new ScheduledService(service, date, time);
                                        SSList.add(SS);

                                        serviceDetails.dispose();
                                        userHomeFrame.dispose();
                                        userHomePage(account);
                                    }
                                });

                                dateAndTimeDialog.add(xLabel);
                                dateAndTimeDialog.add(titleLabel);
                                dateAndTimeDialog.add(dateField);
                                dateAndTimeDialog.add(timeField);
                                dateAndTimeDialog.add(backButton);
                                dateAndTimeDialog.add(nextButton);
                                dateAndTimeDialog.setVisible(true);
                            }
                        });

                        vehicleInfoDialog.add(xLabel);
                        vehicleInfoDialog.add(titleLabel);
                        vehicleInfoDialog.add(makeField);
                        vehicleInfoDialog.add(modelField);
                        vehicleInfoDialog.add(yearField);
                        vehicleInfoDialog.add(mileageField);
                        vehicleInfoDialog.add(backButton);
                        vehicleInfoDialog.add(nextButton);

                        vehicleInfoDialog.setVisible(true);
                    }
                });

                serviceDetails.add(xLabel);
                serviceDetails.add(header);
                serviceDetails.add(subHeading);
                serviceDetails.add(content);
                serviceDetails.add(subHeading2);
                serviceDetails.add(content2);
                serviceDetails.add(backButton);
                serviceDetails.add(availButton);
                serviceDetails.setVisible(true);
            }
        });
        batteryReplacementLabel.setBounds(245, 230, 175, 130);

        ImageIcon fluidChecksAndChangesPic = new ImageIcon("fluidChecksAndChanges.jpg");
        JLabel fluidChecksAndChangesLabel = new JLabel("Fluid Checks and Changes", fluidChecksAndChangesPic,
                JLabel.CENTER);
        fluidChecksAndChangesLabel.setVerticalTextPosition(JLabel.BOTTOM);
        fluidChecksAndChangesLabel.setHorizontalTextPosition(JLabel.CENTER);
        fluidChecksAndChangesLabel.setVerticalAlignment(JLabel.CENTER);
        fluidChecksAndChangesLabel.setHorizontalAlignment(JLabel.CENTER);
        fluidChecksAndChangesLabel.setFont(new Font("Montserrat", Font.BOLD, 12));
        Border fluidChecksAndChangesBorder = BorderFactory.createLineBorder(Color.black, 1);
        fluidChecksAndChangesLabel.setBorder(fluidChecksAndChangesBorder);
        fluidChecksAndChangesLabel.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {

                fluidChecksAndChangesLabel.setForeground(new Color(38, 99, 232));
                Border tempBorder = BorderFactory.createLineBorder(new Color(38, 99, 232), 1);
                fluidChecksAndChangesLabel.setBorder(tempBorder);
            }

            @Override
            public void mouseExited(MouseEvent e) {

                fluidChecksAndChangesLabel.setForeground(Color.black);
                fluidChecksAndChangesLabel.setBorder(fluidChecksAndChangesBorder);
            }

            @Override
            public void mouseClicked(MouseEvent e) {

                JDialog serviceDetails = new JDialog(userHomeFrame, null, true);
                serviceDetails.setUndecorated(true);
                serviceDetails.setLocation(550, 130);
                serviceDetails.setSize(300, 400);
                serviceDetails.setLayout(null);
                serviceDetails.getContentPane().setBackground(Color.white);

                ImageIcon xButton = new ImageIcon("xButton.png");
                JLabel xLabel = new JLabel();
                xLabel.setIcon(xButton);
                xLabel.setBounds(253, 5, 40, 40);
                xLabel.addMouseListener(new MouseAdapter() {

                    @Override
                    public void mouseClicked(MouseEvent e) {

                        serviceDetails.dispose();
                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {

                        ImageIcon changedIcon = changeIconColor(xButton, new Color(38, 99, 232));
                        xLabel.setIcon(changedIcon);
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {

                        xLabel.setIcon(xButton);
                    }
                });

                JLabel header = new JLabel("Fluid Checks and Changes");
                header.setFont(new Font("Montserrat", Font.BOLD, 17));
                header.setBounds(52, 50, 215, 30);

                JLabel subHeading = new JLabel("Potential Materials Needed:");
                subHeading.setFont(new Font("Montserrat", Font.BOLD, 15));
                subHeading.setBounds(55, 105, 200, 30);

                JLabel content = new JLabel(
                        "<html><center> Coolant : PHP 500 - 1,500<br><br>Transmission Fluid : PHP 1,000 - 3,000 <br><br>Brake Fluid : PHP 500 - 1,000<br><br>Power Steering Fluid : PHP 500 - 1,000</center><html>");
                content.setFont(new Font("Montserrat", Font.PLAIN, 12));
                content.setBounds(45, 130, 300, 140);

                JLabel subHeading2 = new JLabel("Service Charge:");
                subHeading2.setFont(new Font("Montserrat", Font.BOLD, 15));
                subHeading2.setBounds(93, 270, 200, 30);

                JLabel content2 = new JLabel(
                        "PHP 200 - 500 (each fluid)");
                content2.setFont(new Font("Montserrat", Font.PLAIN, 12));
                content2.setBounds(82, 260, 300, 100);

                JButton backButton = new JButton("Back");
                backButton.setFocusable(false);
                backButton.setFont(new Font("Montserrat", Font.PLAIN, 11));
                backButton.setBounds(70, 340, 70, 28);
                backButton.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {

                        serviceDetails.dispose();
                    }
                });

                JButton availButton = new JButton("Avail");
                availButton.setFocusable(false);
                availButton.setFont(new Font("Montserrat", Font.PLAIN, 11));
                availButton.setBounds(160, 340, 70, 28);
                availButton.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {

                        JDialog vehicleInfoDialog = new JDialog(serviceDetails, null, true);
                        vehicleInfoDialog.setUndecorated(true);
                        vehicleInfoDialog.setLocation(650, 100);
                        vehicleInfoDialog.setSize(350, 450);
                        vehicleInfoDialog.setLayout(null);
                        vehicleInfoDialog.getContentPane().setBackground(Color.white);

                        ImageIcon xButton = new ImageIcon("xButton.png");
                        JLabel xLabel = new JLabel();
                        xLabel.setIcon(xButton);
                        xLabel.setBounds(305, 5, 40, 40);
                        xLabel.addMouseListener(new MouseAdapter() {

                            @Override
                            public void mouseClicked(MouseEvent e) {

                                vehicleInfoDialog.dispose();
                            }

                            @Override
                            public void mouseEntered(MouseEvent e) {

                                ImageIcon changedIcon = changeIconColor(xButton, new Color(38, 99, 232));
                                xLabel.setIcon(changedIcon);
                            }

                            @Override
                            public void mouseExited(MouseEvent e) {

                                xLabel.setIcon(xButton);
                            }
                        });

                        JLabel titleLabel = new JLabel("Vehicle Information");
                        titleLabel.setFont(new Font("Montserrat", Font.BOLD, 20));
                        titleLabel.setForeground(Color.black);
                        titleLabel.setBounds(90, 55, 300, 50);

                        JTextField makeField = new JTextField();
                        makeField.setFont(new Font("Montserrat", Font.PLAIN, 13));
                        makeField.setCaretColor(Color.black);
                        makeField.setBounds(30, 140, 130, 30);
                        makeField.setText("Make");
                        makeField.addFocusListener(new FocusListener() {

                            @Override
                            public void focusGained(FocusEvent e) {

                                if (makeField.getText().equals("Make")) {

                                    makeField.setText("");
                                }
                            }

                            @Override
                            public void focusLost(FocusEvent e) {

                                if (makeField.getText().isEmpty()) {

                                    makeField.setText("Make");
                                }
                            }
                        });

                        JTextField modelField = new JTextField();
                        modelField.setFont(new Font("Montserrat", Font.PLAIN, 13));
                        modelField.setCaretColor(Color.black);
                        modelField.setBounds(190, 140, 130, 30);
                        modelField.setText("Model");
                        modelField.addFocusListener(new FocusListener() {

                            @Override
                            public void focusGained(FocusEvent e) {

                                if (modelField.getText().equals("Model")) {

                                    modelField.setText("");
                                }
                            }

                            @Override
                            public void focusLost(FocusEvent e) {

                                if (modelField.getText().isEmpty()) {

                                    modelField.setText("Model");
                                }
                            }
                        });

                        JTextField yearField = new JTextField();
                        yearField.setFont(new Font("Montserrat", Font.PLAIN, 13));
                        yearField.setCaretColor(Color.black);
                        yearField.setBounds(30, 200, 130, 30);
                        yearField.setText("Year");
                        yearField.addFocusListener(new FocusListener() {

                            @Override
                            public void focusGained(FocusEvent e) {

                                if (yearField.getText().equals("Year")) {

                                    yearField.setText("");
                                }
                            }

                            @Override
                            public void focusLost(FocusEvent e) {

                                if (yearField.getText().isEmpty()) {

                                    yearField.setText("Year");
                                }
                            }
                        });

                        JTextField mileageField = new JTextField();
                        mileageField.setFont(new Font("Montserrat", Font.PLAIN, 13));
                        mileageField.setCaretColor(Color.black);
                        mileageField.setBounds(190, 200, 130, 30);
                        mileageField.setText("Milieage");
                        mileageField.addFocusListener(new FocusListener() {

                            @Override
                            public void focusGained(FocusEvent e) {

                                if (mileageField.getText().equals("Milieage")) {

                                    mileageField.setText("");
                                }
                            }

                            @Override
                            public void focusLost(FocusEvent e) {

                                if (mileageField.getText().isEmpty()) {

                                    mileageField.setText("Milieage");
                                }
                            }
                        });

                        JButton backButton = new JButton("Back");
                        backButton.setFocusable(false);
                        backButton.setFont(new Font("Montserrat", Font.PLAIN, 11));
                        backButton.setBounds(95, 320, 70, 28);
                        backButton.addActionListener(new ActionListener() {

                            @Override
                            public void actionPerformed(ActionEvent e) {

                                vehicleInfoDialog.dispose();

                            }
                        });

                        JButton nextButton = new JButton("Next");
                        nextButton.setFocusable(false);
                        nextButton.setFont(new Font("Montserrat", Font.PLAIN, 11));
                        nextButton.setBounds(185, 320, 70, 28);
                        nextButton.addActionListener(new ActionListener() {

                            @Override
                            public void actionPerformed(ActionEvent e) {

                                String make = makeField.getText();
                                String model = modelField.getText();
                                String year = yearField.getText();
                                double mileage = Double.parseDouble(mileageField.getText());

                                Vehicle vehicle = new Vehicle(make, model, year, mileage);

                                Service service = new FluidChecksService(account, vehicle, 1, 1, 1, 1);

                                JDialog dateAndTimeDialog = new JDialog(vehicleInfoDialog, null, true);
                                dateAndTimeDialog.setUndecorated(true);
                                dateAndTimeDialog.setLocation(220, 40);
                                dateAndTimeDialog.setSize(300, 300);
                                dateAndTimeDialog.setLayout(null);
                                dateAndTimeDialog.getContentPane().setBackground(Color.white);

                                ImageIcon xButton = new ImageIcon("xButton.png");
                                JLabel xLabel = new JLabel();
                                xLabel.setIcon(xButton);
                                xLabel.setBounds(255, 5, 40, 40);
                                xLabel.addMouseListener(new MouseAdapter() {

                                    @Override
                                    public void mouseClicked(MouseEvent e) {

                                        dateAndTimeDialog.dispose();
                                    }

                                    @Override
                                    public void mouseEntered(MouseEvent e) {

                                        ImageIcon changedIcon = changeIconColor(xButton, new Color(38, 99, 232));
                                        xLabel.setIcon(changedIcon);
                                    }

                                    @Override
                                    public void mouseExited(MouseEvent e) {

                                        xLabel.setIcon(xButton);
                                    }
                                });

                                JLabel titleLabel = new JLabel("<html><center>Choose<br>Date and Time</center><html>");
                                titleLabel.setFont(new Font("Montserrat", Font.BOLD, 18));
                                titleLabel.setForeground(Color.black);
                                titleLabel.setBounds(90, 55, 300, 50);

                                JTextField dateField = new JTextField();
                                dateField.setFont(new Font("Montserrat", Font.PLAIN, 13));
                                dateField.setCaretColor(Color.black);
                                dateField.setBounds(85, 130, 140, 30);
                                dateField.setText("Date(YYYY-MM-DD)");
                                dateField.addFocusListener(new FocusListener() {

                                    @Override
                                    public void focusGained(FocusEvent e) {

                                        if (dateField.getText().equals("Date(YYYY-MM-DD)")) {

                                            dateField.setText("");
                                        }
                                    }

                                    @Override
                                    public void focusLost(FocusEvent e) {

                                        if (dateField.getText().isEmpty()) {

                                            dateField.setText("Date(YYYY-MM-DD)");
                                        }
                                    }
                                });

                                JTextField timeField = new JTextField();
                                timeField.setFont(new Font("Montserrat", Font.PLAIN, 13));
                                timeField.setCaretColor(Color.black);
                                timeField.setBounds(85, 180, 140, 30);
                                timeField.setText("Time (HH:MM AM/PM)");
                                timeField.addFocusListener(new FocusListener() {

                                    @Override
                                    public void focusGained(FocusEvent e) {

                                        if (timeField.getText().equals("Time (HH:MM AM/PM)")) {

                                            timeField.setText("");
                                        }
                                    }

                                    @Override
                                    public void focusLost(FocusEvent e) {

                                        if (timeField.getText().isEmpty()) {

                                            timeField.setText("Time (HH:MM AM/PM)");
                                        }
                                    }
                                });

                                JButton backButton = new JButton("Back");
                                backButton.setFocusable(false);
                                backButton.setFont(new Font("Montserrat", Font.PLAIN, 11));
                                backButton.setBounds(75, 230, 70, 28);
                                backButton.addActionListener(new ActionListener() {

                                    @Override
                                    public void actionPerformed(ActionEvent e) {

                                        dateAndTimeDialog.dispose();

                                    }
                                });

                                JButton nextButton = new JButton("Submit");
                                nextButton.setFocusable(false);
                                nextButton.setFont(new Font("Montserrat", Font.PLAIN, 10));
                                nextButton.setBounds(165, 230, 70, 28);
                                nextButton.addActionListener(new ActionListener() {

                                    @Override
                                    public void actionPerformed(ActionEvent e) {

                                        String date = dateField.getText();
                                        String time = timeField.getText();

                                        ScheduledService SS = new ScheduledService(service, date, time);
                                        SSList.add(SS);

                                        serviceDetails.dispose();
                                        userHomeFrame.dispose();
                                        userHomePage(account);
                                    }
                                });

                                dateAndTimeDialog.add(xLabel);
                                dateAndTimeDialog.add(titleLabel);
                                dateAndTimeDialog.add(dateField);
                                dateAndTimeDialog.add(timeField);
                                dateAndTimeDialog.add(backButton);
                                dateAndTimeDialog.add(nextButton);
                                dateAndTimeDialog.setVisible(true);
                            }
                        });

                        vehicleInfoDialog.add(xLabel);
                        vehicleInfoDialog.add(titleLabel);
                        vehicleInfoDialog.add(makeField);
                        vehicleInfoDialog.add(modelField);
                        vehicleInfoDialog.add(yearField);
                        vehicleInfoDialog.add(mileageField);
                        vehicleInfoDialog.add(backButton);
                        vehicleInfoDialog.add(nextButton);

                        vehicleInfoDialog.setVisible(true);
                    }
                });

                serviceDetails.add(xLabel);
                serviceDetails.add(header);
                serviceDetails.add(subHeading);
                serviceDetails.add(content);
                serviceDetails.add(subHeading2);
                serviceDetails.add(content2);
                serviceDetails.add(backButton);
                serviceDetails.add(availButton);
                serviceDetails.setVisible(true);
            }
        });
        fluidChecksAndChangesLabel.setBounds(435, 230, 175, 130);

        ImageIcon airFilterReplacementPic = new ImageIcon("airFilterReplacement.jpg");
        JLabel airFilterReplacementLabel = new JLabel("Air Filter Replacement", airFilterReplacementPic, JLabel.CENTER);
        airFilterReplacementLabel.setVerticalTextPosition(JLabel.BOTTOM);
        airFilterReplacementLabel.setHorizontalTextPosition(JLabel.CENTER);
        airFilterReplacementLabel.setVerticalAlignment(JLabel.CENTER);
        airFilterReplacementLabel.setHorizontalAlignment(JLabel.CENTER);
        airFilterReplacementLabel.setFont(new Font("Montserrat", Font.BOLD, 12));
        Border airFilterReplacementBorder = BorderFactory.createLineBorder(Color.black, 1);
        airFilterReplacementLabel.setBorder(airFilterReplacementBorder);
        airFilterReplacementLabel.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {

                airFilterReplacementLabel.setForeground(new Color(38, 99, 232));
                Border tempBorder = BorderFactory.createLineBorder(new Color(38, 99, 232), 1);
                airFilterReplacementLabel.setBorder(tempBorder);
            }

            @Override
            public void mouseExited(MouseEvent e) {

                airFilterReplacementLabel.setForeground(Color.black);
                airFilterReplacementLabel.setBorder(airFilterReplacementBorder);
            }

            @Override
            public void mouseClicked(MouseEvent e) {

                JDialog serviceDetails = new JDialog(userHomeFrame, null, true);
                serviceDetails.setUndecorated(true);
                serviceDetails.setLocation(550, 130);
                serviceDetails.setSize(300, 400);
                serviceDetails.setLayout(null);
                serviceDetails.getContentPane().setBackground(Color.white);

                ImageIcon xButton = new ImageIcon("xButton.png");
                JLabel xLabel = new JLabel();
                xLabel.setIcon(xButton);
                xLabel.setBounds(253, 5, 40, 40);
                xLabel.addMouseListener(new MouseAdapter() {

                    @Override
                    public void mouseClicked(MouseEvent e) {

                        serviceDetails.dispose();
                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {

                        ImageIcon changedIcon = changeIconColor(xButton, new Color(38, 99, 232));
                        xLabel.setIcon(changedIcon);
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {

                        xLabel.setIcon(xButton);
                    }
                });

                JLabel header = new JLabel("Air Filter Replacement");
                header.setFont(new Font("Montserrat", Font.BOLD, 19));
                header.setBounds(51, 50, 230, 30);

                JLabel subHeading = new JLabel("Potential Materials Needed:");
                subHeading.setFont(new Font("Montserrat", Font.BOLD, 15));
                subHeading.setBounds(55, 105, 200, 30);

                JLabel content = new JLabel(
                        "Engine Air Filter : PHP 500 - 1,500");
                content.setFont(new Font("Montserrat", Font.PLAIN, 12));
                content.setBounds(60, 110, 300, 100);

                JLabel subHeading2 = new JLabel("Service Charge:");
                subHeading2.setFont(new Font("Montserrat", Font.BOLD, 15));
                subHeading2.setBounds(93, 190, 200, 30);

                JLabel content2 = new JLabel(
                        "PHP 200 - 500");
                content2.setFont(new Font("Montserrat", Font.PLAIN, 12));
                content2.setBounds(110, 185, 300, 100);

                JButton backButton = new JButton("Back");
                backButton.setFocusable(false);
                backButton.setFont(new Font("Montserrat", Font.PLAIN, 11));
                backButton.setBounds(70, 270, 70, 28);
                backButton.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {

                        serviceDetails.dispose();
                    }
                });

                JButton availButton = new JButton("Avail");
                availButton.setFocusable(false);
                availButton.setFont(new Font("Montserrat", Font.PLAIN, 11));
                availButton.setBounds(160, 270, 70, 28);
                availButton.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {

                        JDialog vehicleInfoDialog = new JDialog(serviceDetails, null, true);
                        vehicleInfoDialog.setUndecorated(true);
                        vehicleInfoDialog.setLocation(650, 100);
                        vehicleInfoDialog.setSize(350, 450);
                        vehicleInfoDialog.setLayout(null);
                        vehicleInfoDialog.getContentPane().setBackground(Color.white);

                        ImageIcon xButton = new ImageIcon("xButton.png");
                        JLabel xLabel = new JLabel();
                        xLabel.setIcon(xButton);
                        xLabel.setBounds(305, 5, 40, 40);
                        xLabel.addMouseListener(new MouseAdapter() {

                            @Override
                            public void mouseClicked(MouseEvent e) {

                                vehicleInfoDialog.dispose();
                            }

                            @Override
                            public void mouseEntered(MouseEvent e) {

                                ImageIcon changedIcon = changeIconColor(xButton, new Color(38, 99, 232));
                                xLabel.setIcon(changedIcon);
                            }

                            @Override
                            public void mouseExited(MouseEvent e) {

                                xLabel.setIcon(xButton);
                            }
                        });

                        JLabel titleLabel = new JLabel("Vehicle Information");
                        titleLabel.setFont(new Font("Montserrat", Font.BOLD, 20));
                        titleLabel.setForeground(Color.black);
                        titleLabel.setBounds(90, 55, 300, 50);

                        JTextField makeField = new JTextField();
                        makeField.setFont(new Font("Montserrat", Font.PLAIN, 13));
                        makeField.setCaretColor(Color.black);
                        makeField.setBounds(30, 140, 130, 30);
                        makeField.setText("Make");
                        makeField.addFocusListener(new FocusListener() {

                            @Override
                            public void focusGained(FocusEvent e) {

                                if (makeField.getText().equals("Make")) {

                                    makeField.setText("");
                                }
                            }

                            @Override
                            public void focusLost(FocusEvent e) {

                                if (makeField.getText().isEmpty()) {

                                    makeField.setText("Make");
                                }
                            }
                        });

                        JTextField modelField = new JTextField();
                        modelField.setFont(new Font("Montserrat", Font.PLAIN, 13));
                        modelField.setCaretColor(Color.black);
                        modelField.setBounds(190, 140, 130, 30);
                        modelField.setText("Model");
                        modelField.addFocusListener(new FocusListener() {

                            @Override
                            public void focusGained(FocusEvent e) {

                                if (modelField.getText().equals("Model")) {

                                    modelField.setText("");
                                }
                            }

                            @Override
                            public void focusLost(FocusEvent e) {

                                if (modelField.getText().isEmpty()) {

                                    modelField.setText("Model");
                                }
                            }
                        });

                        JTextField yearField = new JTextField();
                        yearField.setFont(new Font("Montserrat", Font.PLAIN, 13));
                        yearField.setCaretColor(Color.black);
                        yearField.setBounds(30, 200, 130, 30);
                        yearField.setText("Year");
                        yearField.addFocusListener(new FocusListener() {

                            @Override
                            public void focusGained(FocusEvent e) {

                                if (yearField.getText().equals("Year")) {

                                    yearField.setText("");
                                }
                            }

                            @Override
                            public void focusLost(FocusEvent e) {

                                if (yearField.getText().isEmpty()) {

                                    yearField.setText("Year");
                                }
                            }
                        });

                        JTextField mileageField = new JTextField();
                        mileageField.setFont(new Font("Montserrat", Font.PLAIN, 13));
                        mileageField.setCaretColor(Color.black);
                        mileageField.setBounds(190, 200, 130, 30);
                        mileageField.setText("Milieage");
                        mileageField.addFocusListener(new FocusListener() {

                            @Override
                            public void focusGained(FocusEvent e) {

                                if (mileageField.getText().equals("Milieage")) {

                                    mileageField.setText("");
                                }
                            }

                            @Override
                            public void focusLost(FocusEvent e) {

                                if (mileageField.getText().isEmpty()) {

                                    mileageField.setText("Milieage");
                                }
                            }
                        });

                        JButton backButton = new JButton("Back");
                        backButton.setFocusable(false);
                        backButton.setFont(new Font("Montserrat", Font.PLAIN, 11));
                        backButton.setBounds(95, 320, 70, 28);
                        backButton.addActionListener(new ActionListener() {

                            @Override
                            public void actionPerformed(ActionEvent e) {

                                vehicleInfoDialog.dispose();

                            }
                        });

                        JButton nextButton = new JButton("Next");
                        nextButton.setFocusable(false);
                        nextButton.setFont(new Font("Montserrat", Font.PLAIN, 11));
                        nextButton.setBounds(185, 320, 70, 28);
                        nextButton.addActionListener(new ActionListener() {

                            @Override
                            public void actionPerformed(ActionEvent e) {

                                String make = makeField.getText();
                                String model = modelField.getText();
                                String year = yearField.getText();
                                double mileage = Double.parseDouble(mileageField.getText());

                                Vehicle vehicle = new Vehicle(make, model, year, mileage);

                                Service service = new AirFilterReplacementService(account, vehicle, 1);

                                JDialog dateAndTimeDialog = new JDialog(vehicleInfoDialog, null, true);
                                dateAndTimeDialog.setUndecorated(true);
                                dateAndTimeDialog.setLocation(220, 40);
                                dateAndTimeDialog.setSize(300, 300);
                                dateAndTimeDialog.setLayout(null);
                                dateAndTimeDialog.getContentPane().setBackground(Color.white);

                                ImageIcon xButton = new ImageIcon("xButton.png");
                                JLabel xLabel = new JLabel();
                                xLabel.setIcon(xButton);
                                xLabel.setBounds(255, 5, 40, 40);
                                xLabel.addMouseListener(new MouseAdapter() {

                                    @Override
                                    public void mouseClicked(MouseEvent e) {

                                        dateAndTimeDialog.dispose();
                                    }

                                    @Override
                                    public void mouseEntered(MouseEvent e) {

                                        ImageIcon changedIcon = changeIconColor(xButton, new Color(38, 99, 232));
                                        xLabel.setIcon(changedIcon);
                                    }

                                    @Override
                                    public void mouseExited(MouseEvent e) {

                                        xLabel.setIcon(xButton);
                                    }
                                });

                                JLabel titleLabel = new JLabel("<html><center>Choose<br>Date and Time</center><html>");
                                titleLabel.setFont(new Font("Montserrat", Font.BOLD, 18));
                                titleLabel.setForeground(Color.black);
                                titleLabel.setBounds(90, 55, 300, 50);

                                JTextField dateField = new JTextField();
                                dateField.setFont(new Font("Montserrat", Font.PLAIN, 13));
                                dateField.setCaretColor(Color.black);
                                dateField.setBounds(85, 130, 140, 30);
                                dateField.setText("Date(YYYY-MM-DD)");
                                dateField.addFocusListener(new FocusListener() {

                                    @Override
                                    public void focusGained(FocusEvent e) {

                                        if (dateField.getText().equals("Date(YYYY-MM-DD)")) {

                                            dateField.setText("");
                                        }
                                    }

                                    @Override
                                    public void focusLost(FocusEvent e) {

                                        if (dateField.getText().isEmpty()) {

                                            dateField.setText("Date(YYYY-MM-DD)");
                                        }
                                    }
                                });

                                JTextField timeField = new JTextField();
                                timeField.setFont(new Font("Montserrat", Font.PLAIN, 13));
                                timeField.setCaretColor(Color.black);
                                timeField.setBounds(85, 180, 140, 30);
                                timeField.setText("Time (HH:MM AM/PM)");
                                timeField.addFocusListener(new FocusListener() {

                                    @Override
                                    public void focusGained(FocusEvent e) {

                                        if (timeField.getText().equals("Time (HH:MM AM/PM)")) {

                                            timeField.setText("");
                                        }
                                    }

                                    @Override
                                    public void focusLost(FocusEvent e) {

                                        if (timeField.getText().isEmpty()) {

                                            timeField.setText("Time (HH:MM AM/PM)");
                                        }
                                    }
                                });

                                JButton backButton = new JButton("Back");
                                backButton.setFocusable(false);
                                backButton.setFont(new Font("Montserrat", Font.PLAIN, 11));
                                backButton.setBounds(75, 230, 70, 28);
                                backButton.addActionListener(new ActionListener() {

                                    @Override
                                    public void actionPerformed(ActionEvent e) {

                                        dateAndTimeDialog.dispose();

                                    }
                                });

                                JButton nextButton = new JButton("Submit");
                                nextButton.setFocusable(false);
                                nextButton.setFont(new Font("Montserrat", Font.PLAIN, 10));
                                nextButton.setBounds(165, 230, 70, 28);
                                nextButton.addActionListener(new ActionListener() {

                                    @Override
                                    public void actionPerformed(ActionEvent e) {

                                        String date = dateField.getText();
                                        String time = timeField.getText();

                                        ScheduledService SS = new ScheduledService(service, date, time);
                                        SSList.add(SS);

                                        serviceDetails.dispose();
                                        userHomeFrame.dispose();
                                        userHomePage(account);
                                    }
                                });

                                dateAndTimeDialog.add(xLabel);
                                dateAndTimeDialog.add(titleLabel);
                                dateAndTimeDialog.add(dateField);
                                dateAndTimeDialog.add(timeField);
                                dateAndTimeDialog.add(backButton);
                                dateAndTimeDialog.add(nextButton);
                                dateAndTimeDialog.setVisible(true);
                            }
                        });

                        vehicleInfoDialog.add(xLabel);
                        vehicleInfoDialog.add(titleLabel);
                        vehicleInfoDialog.add(makeField);
                        vehicleInfoDialog.add(modelField);
                        vehicleInfoDialog.add(yearField);
                        vehicleInfoDialog.add(mileageField);
                        vehicleInfoDialog.add(backButton);
                        vehicleInfoDialog.add(nextButton);

                        vehicleInfoDialog.setVisible(true);
                    }
                });

                serviceDetails.add(xLabel);
                serviceDetails.add(header);
                serviceDetails.add(subHeading);
                serviceDetails.add(content);
                serviceDetails.add(subHeading2);
                serviceDetails.add(content2);
                serviceDetails.add(backButton);
                serviceDetails.add(availButton);
                serviceDetails.setVisible(true);
            }
        });
        airFilterReplacementLabel.setBounds(55, 370, 175, 130);

        ImageIcon sparkPlugReplacementPic = new ImageIcon("sparkPlugReplacement.jpg");
        JLabel sparkPlugReplacementLabel = new JLabel("Spark Plug Replacement", sparkPlugReplacementPic, JLabel.CENTER);
        sparkPlugReplacementLabel.setVerticalTextPosition(JLabel.BOTTOM);
        sparkPlugReplacementLabel.setHorizontalTextPosition(JLabel.CENTER);
        sparkPlugReplacementLabel.setVerticalAlignment(JLabel.CENTER);
        sparkPlugReplacementLabel.setHorizontalAlignment(JLabel.CENTER);
        sparkPlugReplacementLabel.setFont(new Font("Montserrat", Font.BOLD, 12));
        Border sparkPlugReplacementBorder = BorderFactory.createLineBorder(Color.black, 1);
        sparkPlugReplacementLabel.setBorder(sparkPlugReplacementBorder);
        sparkPlugReplacementLabel.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {

                sparkPlugReplacementLabel.setForeground(new Color(38, 99, 232));
                Border tempBorder = BorderFactory.createLineBorder(new Color(38, 99, 232), 1);
                sparkPlugReplacementLabel.setBorder(tempBorder);
            }

            @Override
            public void mouseExited(MouseEvent e) {

                sparkPlugReplacementLabel.setForeground(Color.black);
                sparkPlugReplacementLabel.setBorder(sparkPlugReplacementBorder);
            }

            @Override
            public void mouseClicked(MouseEvent e) {

                JDialog serviceDetails = new JDialog(userHomeFrame, null, true);
                serviceDetails.setUndecorated(true);
                serviceDetails.setLocation(550, 130);
                serviceDetails.setSize(300, 400);
                serviceDetails.setLayout(null);
                serviceDetails.getContentPane().setBackground(Color.white);

                ImageIcon xButton = new ImageIcon("xButton.png");
                JLabel xLabel = new JLabel();
                xLabel.setIcon(xButton);
                xLabel.setBounds(253, 5, 40, 40);
                xLabel.addMouseListener(new MouseAdapter() {

                    @Override
                    public void mouseClicked(MouseEvent e) {

                        serviceDetails.dispose();
                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {

                        ImageIcon changedIcon = changeIconColor(xButton, new Color(38, 99, 232));
                        xLabel.setIcon(changedIcon);
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {

                        xLabel.setIcon(xButton);
                    }
                });

                JLabel header = new JLabel("Spark Plug Replacement");
                header.setFont(new Font("Montserrat", Font.BOLD, 19));
                header.setBounds(42, 50, 230, 30);

                JLabel subHeading = new JLabel("Potential Materials Needed:");
                subHeading.setFont(new Font("Montserrat", Font.BOLD, 15));
                subHeading.setBounds(55, 105, 200, 30);

                JLabel content = new JLabel(
                        "Spark plug : PHP 125 - 375");
                content.setFont(new Font("Montserrat", Font.PLAIN, 12));
                content.setBounds(78, 110, 300, 100);

                JLabel subHeading2 = new JLabel("Service Charge:");
                subHeading2.setFont(new Font("Montserrat", Font.BOLD, 15));
                subHeading2.setBounds(93, 190, 200, 30);

                JLabel content2 = new JLabel(
                        "PHP 300 - 1000");
                content2.setFont(new Font("Montserrat", Font.PLAIN, 12));
                content2.setBounds(105, 185, 300, 100);

                JButton backButton = new JButton("Back");
                backButton.setFocusable(false);
                backButton.setFont(new Font("Montserrat", Font.PLAIN, 11));
                backButton.setBounds(70, 270, 70, 28);
                backButton.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {

                        serviceDetails.dispose();
                    }
                });

                JButton availButton = new JButton("Avail");
                availButton.setFocusable(false);
                availButton.setFont(new Font("Montserrat", Font.PLAIN, 11));
                availButton.setBounds(160, 270, 70, 28);
                availButton.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {

                        JDialog vehicleInfoDialog = new JDialog(serviceDetails, null, true);
                        vehicleInfoDialog.setUndecorated(true);
                        vehicleInfoDialog.setLocation(650, 100);
                        vehicleInfoDialog.setSize(350, 450);
                        vehicleInfoDialog.setLayout(null);
                        vehicleInfoDialog.getContentPane().setBackground(Color.white);

                        ImageIcon xButton = new ImageIcon("xButton.png");
                        JLabel xLabel = new JLabel();
                        xLabel.setIcon(xButton);
                        xLabel.setBounds(305, 5, 40, 40);
                        xLabel.addMouseListener(new MouseAdapter() {

                            @Override
                            public void mouseClicked(MouseEvent e) {

                                vehicleInfoDialog.dispose();
                            }

                            @Override
                            public void mouseEntered(MouseEvent e) {

                                ImageIcon changedIcon = changeIconColor(xButton, new Color(38, 99, 232));
                                xLabel.setIcon(changedIcon);
                            }

                            @Override
                            public void mouseExited(MouseEvent e) {

                                xLabel.setIcon(xButton);
                            }
                        });

                        JLabel titleLabel = new JLabel("Vehicle Information");
                        titleLabel.setFont(new Font("Montserrat", Font.BOLD, 20));
                        titleLabel.setForeground(Color.black);
                        titleLabel.setBounds(90, 55, 300, 50);

                        JTextField makeField = new JTextField();
                        makeField.setFont(new Font("Montserrat", Font.PLAIN, 13));
                        makeField.setCaretColor(Color.black);
                        makeField.setBounds(30, 140, 130, 30);
                        makeField.setText("Make");
                        makeField.addFocusListener(new FocusListener() {

                            @Override
                            public void focusGained(FocusEvent e) {

                                if (makeField.getText().equals("Make")) {

                                    makeField.setText("");
                                }
                            }

                            @Override
                            public void focusLost(FocusEvent e) {

                                if (makeField.getText().isEmpty()) {

                                    makeField.setText("Make");
                                }
                            }
                        });

                        JTextField modelField = new JTextField();
                        modelField.setFont(new Font("Montserrat", Font.PLAIN, 13));
                        modelField.setCaretColor(Color.black);
                        modelField.setBounds(190, 140, 130, 30);
                        modelField.setText("Model");
                        modelField.addFocusListener(new FocusListener() {

                            @Override
                            public void focusGained(FocusEvent e) {

                                if (modelField.getText().equals("Model")) {

                                    modelField.setText("");
                                }
                            }

                            @Override
                            public void focusLost(FocusEvent e) {

                                if (modelField.getText().isEmpty()) {

                                    modelField.setText("Model");
                                }
                            }
                        });

                        JTextField yearField = new JTextField();
                        yearField.setFont(new Font("Montserrat", Font.PLAIN, 13));
                        yearField.setCaretColor(Color.black);
                        yearField.setBounds(30, 200, 130, 30);
                        yearField.setText("Year");
                        yearField.addFocusListener(new FocusListener() {

                            @Override
                            public void focusGained(FocusEvent e) {

                                if (yearField.getText().equals("Year")) {

                                    yearField.setText("");
                                }
                            }

                            @Override
                            public void focusLost(FocusEvent e) {

                                if (yearField.getText().isEmpty()) {

                                    yearField.setText("Year");
                                }
                            }
                        });

                        JTextField mileageField = new JTextField();
                        mileageField.setFont(new Font("Montserrat", Font.PLAIN, 13));
                        mileageField.setCaretColor(Color.black);
                        mileageField.setBounds(190, 200, 130, 30);
                        mileageField.setText("Milieage");
                        mileageField.addFocusListener(new FocusListener() {

                            @Override
                            public void focusGained(FocusEvent e) {

                                if (mileageField.getText().equals("Milieage")) {

                                    mileageField.setText("");
                                }
                            }

                            @Override
                            public void focusLost(FocusEvent e) {

                                if (mileageField.getText().isEmpty()) {

                                    mileageField.setText("Milieage");
                                }
                            }
                        });

                        JButton backButton = new JButton("Back");
                        backButton.setFocusable(false);
                        backButton.setFont(new Font("Montserrat", Font.PLAIN, 11));
                        backButton.setBounds(95, 320, 70, 28);
                        backButton.addActionListener(new ActionListener() {

                            @Override
                            public void actionPerformed(ActionEvent e) {

                                vehicleInfoDialog.dispose();

                            }
                        });

                        JButton nextButton = new JButton("Next");
                        nextButton.setFocusable(false);
                        nextButton.setFont(new Font("Montserrat", Font.PLAIN, 11));
                        nextButton.setBounds(185, 320, 70, 28);
                        nextButton.addActionListener(new ActionListener() {

                            @Override
                            public void actionPerformed(ActionEvent e) {

                                String make = makeField.getText();
                                String model = modelField.getText();
                                String year = yearField.getText();
                                double mileage = Double.parseDouble(mileageField.getText());

                                Vehicle vehicle = new Vehicle(make, model, year, mileage);

                                Service service = new SparkPlugReplacementService(account, vehicle, 5);

                                JDialog dateAndTimeDialog = new JDialog(vehicleInfoDialog, null, true);
                                dateAndTimeDialog.setUndecorated(true);
                                dateAndTimeDialog.setLocation(220, 40);
                                dateAndTimeDialog.setSize(300, 300);
                                dateAndTimeDialog.setLayout(null);
                                dateAndTimeDialog.getContentPane().setBackground(Color.white);

                                ImageIcon xButton = new ImageIcon("xButton.png");
                                JLabel xLabel = new JLabel();
                                xLabel.setIcon(xButton);
                                xLabel.setBounds(255, 5, 40, 40);
                                xLabel.addMouseListener(new MouseAdapter() {

                                    @Override
                                    public void mouseClicked(MouseEvent e) {

                                        dateAndTimeDialog.dispose();
                                    }

                                    @Override
                                    public void mouseEntered(MouseEvent e) {

                                        ImageIcon changedIcon = changeIconColor(xButton, new Color(38, 99, 232));
                                        xLabel.setIcon(changedIcon);
                                    }

                                    @Override
                                    public void mouseExited(MouseEvent e) {

                                        xLabel.setIcon(xButton);
                                    }
                                });

                                JLabel titleLabel = new JLabel("<html><center>Choose<br>Date and Time</center><html>");
                                titleLabel.setFont(new Font("Montserrat", Font.BOLD, 18));
                                titleLabel.setForeground(Color.black);
                                titleLabel.setBounds(90, 55, 300, 50);

                                JTextField dateField = new JTextField();
                                dateField.setFont(new Font("Montserrat", Font.PLAIN, 13));
                                dateField.setCaretColor(Color.black);
                                dateField.setBounds(85, 130, 140, 30);
                                dateField.setText("Date(YYYY-MM-DD)");
                                dateField.addFocusListener(new FocusListener() {

                                    @Override
                                    public void focusGained(FocusEvent e) {

                                        if (dateField.getText().equals("Date(YYYY-MM-DD)")) {

                                            dateField.setText("");
                                        }
                                    }

                                    @Override
                                    public void focusLost(FocusEvent e) {

                                        if (dateField.getText().isEmpty()) {

                                            dateField.setText("Date(YYYY-MM-DD)");
                                        }
                                    }
                                });

                                JTextField timeField = new JTextField();
                                timeField.setFont(new Font("Montserrat", Font.PLAIN, 13));
                                timeField.setCaretColor(Color.black);
                                timeField.setBounds(85, 180, 140, 30);
                                timeField.setText("Time (HH:MM AM/PM)");
                                timeField.addFocusListener(new FocusListener() {

                                    @Override
                                    public void focusGained(FocusEvent e) {

                                        if (timeField.getText().equals("Time (HH:MM AM/PM)")) {

                                            timeField.setText("");
                                        }
                                    }

                                    @Override
                                    public void focusLost(FocusEvent e) {

                                        if (timeField.getText().isEmpty()) {

                                            timeField.setText("Time (HH:MM AM/PM)");
                                        }
                                    }
                                });

                                JButton backButton = new JButton("Back");
                                backButton.setFocusable(false);
                                backButton.setFont(new Font("Montserrat", Font.PLAIN, 11));
                                backButton.setBounds(75, 230, 70, 28);
                                backButton.addActionListener(new ActionListener() {

                                    @Override
                                    public void actionPerformed(ActionEvent e) {

                                        dateAndTimeDialog.dispose();

                                    }
                                });

                                JButton nextButton = new JButton("Submit");
                                nextButton.setFocusable(false);
                                nextButton.setFont(new Font("Montserrat", Font.PLAIN, 10));
                                nextButton.setBounds(165, 230, 70, 28);
                                nextButton.addActionListener(new ActionListener() {

                                    @Override
                                    public void actionPerformed(ActionEvent e) {

                                        String date = dateField.getText();
                                        String time = timeField.getText();

                                        ScheduledService SS = new ScheduledService(service, date, time);
                                        SSList.add(SS);

                                        serviceDetails.dispose();
                                        userHomeFrame.dispose();
                                        userHomePage(account);
                                    }
                                });

                                dateAndTimeDialog.add(xLabel);
                                dateAndTimeDialog.add(titleLabel);
                                dateAndTimeDialog.add(dateField);
                                dateAndTimeDialog.add(timeField);
                                dateAndTimeDialog.add(backButton);
                                dateAndTimeDialog.add(nextButton);
                                dateAndTimeDialog.setVisible(true);
                            }
                        });

                        vehicleInfoDialog.add(xLabel);
                        vehicleInfoDialog.add(titleLabel);
                        vehicleInfoDialog.add(makeField);
                        vehicleInfoDialog.add(modelField);
                        vehicleInfoDialog.add(yearField);
                        vehicleInfoDialog.add(mileageField);
                        vehicleInfoDialog.add(backButton);
                        vehicleInfoDialog.add(nextButton);

                        vehicleInfoDialog.setVisible(true);
                    }
                });

                serviceDetails.add(xLabel);
                serviceDetails.add(header);
                serviceDetails.add(subHeading);
                serviceDetails.add(content);
                serviceDetails.add(subHeading2);
                serviceDetails.add(content2);
                serviceDetails.add(backButton);
                serviceDetails.add(availButton);
                serviceDetails.setVisible(true);
            }
        });
        sparkPlugReplacementLabel.setBounds(245, 370, 175, 130);

        ImageIcon timingBeltReplacementPic = new ImageIcon("timingBeltReplacement.jpg");
        JLabel timingBeltReplacementLabel = new JLabel("Timing Belt Replacement", timingBeltReplacementPic,
                JLabel.CENTER);
        timingBeltReplacementLabel.setVerticalTextPosition(JLabel.BOTTOM);
        timingBeltReplacementLabel.setHorizontalTextPosition(JLabel.CENTER);
        timingBeltReplacementLabel.setVerticalAlignment(JLabel.CENTER);
        timingBeltReplacementLabel.setHorizontalAlignment(JLabel.CENTER);
        timingBeltReplacementLabel.setFont(new Font("Montserrat", Font.BOLD, 12));
        Border timingBeltReplacementBorder = BorderFactory.createLineBorder(Color.black, 1);
        timingBeltReplacementLabel.setBorder(timingBeltReplacementBorder);
        timingBeltReplacementLabel.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {

                timingBeltReplacementLabel.setForeground(new Color(38, 99, 232));
                Border tempBorder = BorderFactory.createLineBorder(new Color(38, 99, 232), 1);
                timingBeltReplacementLabel.setBorder(tempBorder);
            }

            @Override
            public void mouseExited(MouseEvent e) {

                timingBeltReplacementLabel.setForeground(Color.black);
                timingBeltReplacementLabel.setBorder(timingBeltReplacementBorder);
            }

            @Override
            public void mouseClicked(MouseEvent e) {

                JDialog serviceDetails = new JDialog(userHomeFrame, null, true);
                serviceDetails.setUndecorated(true);
                serviceDetails.setLocation(550, 130);
                serviceDetails.setSize(300, 400);
                serviceDetails.setLayout(null);
                serviceDetails.getContentPane().setBackground(Color.white);

                ImageIcon xButton = new ImageIcon("xButton.png");
                JLabel xLabel = new JLabel();
                xLabel.setIcon(xButton);
                xLabel.setBounds(253, 5, 40, 40);
                xLabel.addMouseListener(new MouseAdapter() {

                    @Override
                    public void mouseClicked(MouseEvent e) {

                        serviceDetails.dispose();
                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {

                        ImageIcon changedIcon = changeIconColor(xButton, new Color(38, 99, 232));
                        xLabel.setIcon(changedIcon);
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {

                        xLabel.setIcon(xButton);
                    }
                });

                JLabel header = new JLabel("Timing Belt Replacement");
                header.setFont(new Font("Montserrat", Font.BOLD, 18));
                header.setBounds(43, 50, 240, 30);

                JLabel subHeading = new JLabel("Potential Materials Needed:");
                subHeading.setFont(new Font("Montserrat", Font.BOLD, 15));
                subHeading.setBounds(55, 105, 200, 30);

                JLabel content = new JLabel(
                        "Timing Belt Kit : PHP 3,000 - 8,000");
                content.setFont(new Font("Montserrat", Font.PLAIN, 12));
                content.setBounds(60, 110, 300, 100);

                JLabel subHeading2 = new JLabel("Service Charge:");
                subHeading2.setFont(new Font("Montserrat", Font.BOLD, 15));
                subHeading2.setBounds(93, 190, 200, 30);

                JLabel content2 = new JLabel(
                        "PHP 1,000 - 3,000");
                content2.setFont(new Font("Montserrat", Font.PLAIN, 12));
                content2.setBounds(100, 185, 300, 100);

                JButton backButton = new JButton("Back");
                backButton.setFocusable(false);
                backButton.setFont(new Font("Montserrat", Font.PLAIN, 11));
                backButton.setBounds(70, 270, 70, 28);
                backButton.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {

                        serviceDetails.dispose();
                    }
                });

                JButton availButton = new JButton("Avail");
                availButton.setFocusable(false);
                availButton.setFont(new Font("Montserrat", Font.PLAIN, 11));
                availButton.setBounds(160, 270, 70, 28);
                availButton.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {

                        JDialog vehicleInfoDialog = new JDialog(serviceDetails, null, true);
                        vehicleInfoDialog.setUndecorated(true);
                        vehicleInfoDialog.setLocation(650, 100);
                        vehicleInfoDialog.setSize(350, 450);
                        vehicleInfoDialog.setLayout(null);
                        vehicleInfoDialog.getContentPane().setBackground(Color.white);

                        ImageIcon xButton = new ImageIcon("xButton.png");
                        JLabel xLabel = new JLabel();
                        xLabel.setIcon(xButton);
                        xLabel.setBounds(305, 5, 40, 40);
                        xLabel.addMouseListener(new MouseAdapter() {

                            @Override
                            public void mouseClicked(MouseEvent e) {

                                vehicleInfoDialog.dispose();
                            }

                            @Override
                            public void mouseEntered(MouseEvent e) {

                                ImageIcon changedIcon = changeIconColor(xButton, new Color(38, 99, 232));
                                xLabel.setIcon(changedIcon);
                            }

                            @Override
                            public void mouseExited(MouseEvent e) {

                                xLabel.setIcon(xButton);
                            }
                        });

                        JLabel titleLabel = new JLabel("Vehicle Information");
                        titleLabel.setFont(new Font("Montserrat", Font.BOLD, 20));
                        titleLabel.setForeground(Color.black);
                        titleLabel.setBounds(90, 55, 300, 50);

                        JTextField makeField = new JTextField();
                        makeField.setFont(new Font("Montserrat", Font.PLAIN, 13));
                        makeField.setCaretColor(Color.black);
                        makeField.setBounds(30, 140, 130, 30);
                        makeField.setText("Make");
                        makeField.addFocusListener(new FocusListener() {

                            @Override
                            public void focusGained(FocusEvent e) {

                                if (makeField.getText().equals("Make")) {

                                    makeField.setText("");
                                }
                            }

                            @Override
                            public void focusLost(FocusEvent e) {

                                if (makeField.getText().isEmpty()) {

                                    makeField.setText("Make");
                                }
                            }
                        });

                        JTextField modelField = new JTextField();
                        modelField.setFont(new Font("Montserrat", Font.PLAIN, 13));
                        modelField.setCaretColor(Color.black);
                        modelField.setBounds(190, 140, 130, 30);
                        modelField.setText("Model");
                        modelField.addFocusListener(new FocusListener() {

                            @Override
                            public void focusGained(FocusEvent e) {

                                if (modelField.getText().equals("Model")) {

                                    modelField.setText("");
                                }
                            }

                            @Override
                            public void focusLost(FocusEvent e) {

                                if (modelField.getText().isEmpty()) {

                                    modelField.setText("Model");
                                }
                            }
                        });

                        JTextField yearField = new JTextField();
                        yearField.setFont(new Font("Montserrat", Font.PLAIN, 13));
                        yearField.setCaretColor(Color.black);
                        yearField.setBounds(30, 200, 130, 30);
                        yearField.setText("Year");
                        yearField.addFocusListener(new FocusListener() {

                            @Override
                            public void focusGained(FocusEvent e) {

                                if (yearField.getText().equals("Year")) {

                                    yearField.setText("");
                                }
                            }

                            @Override
                            public void focusLost(FocusEvent e) {

                                if (yearField.getText().isEmpty()) {

                                    yearField.setText("Year");
                                }
                            }
                        });

                        JTextField mileageField = new JTextField();
                        mileageField.setFont(new Font("Montserrat", Font.PLAIN, 13));
                        mileageField.setCaretColor(Color.black);
                        mileageField.setBounds(190, 200, 130, 30);
                        mileageField.setText("Milieage");
                        mileageField.addFocusListener(new FocusListener() {

                            @Override
                            public void focusGained(FocusEvent e) {

                                if (mileageField.getText().equals("Milieage")) {

                                    mileageField.setText("");
                                }
                            }

                            @Override
                            public void focusLost(FocusEvent e) {

                                if (mileageField.getText().isEmpty()) {

                                    mileageField.setText("Milieage");
                                }
                            }
                        });

                        JButton backButton = new JButton("Back");
                        backButton.setFocusable(false);
                        backButton.setFont(new Font("Montserrat", Font.PLAIN, 11));
                        backButton.setBounds(95, 320, 70, 28);
                        backButton.addActionListener(new ActionListener() {

                            @Override
                            public void actionPerformed(ActionEvent e) {

                                vehicleInfoDialog.dispose();

                            }
                        });

                        JButton nextButton = new JButton("Next");
                        nextButton.setFocusable(false);
                        nextButton.setFont(new Font("Montserrat", Font.PLAIN, 11));
                        nextButton.setBounds(185, 320, 70, 28);
                        nextButton.addActionListener(new ActionListener() {

                            @Override
                            public void actionPerformed(ActionEvent e) {

                                String make = makeField.getText();
                                String model = modelField.getText();
                                String year = yearField.getText();
                                double mileage = Double.parseDouble(mileageField.getText());

                                Vehicle vehicle = new Vehicle(make, model, year, mileage);

                                Service service = new TimingBeltReplacementService(account, vehicle, 1);

                                JDialog dateAndTimeDialog = new JDialog(vehicleInfoDialog, null, true);
                                dateAndTimeDialog.setUndecorated(true);
                                dateAndTimeDialog.setLocation(220, 40);
                                dateAndTimeDialog.setSize(300, 300);
                                dateAndTimeDialog.setLayout(null);
                                dateAndTimeDialog.getContentPane().setBackground(Color.white);

                                ImageIcon xButton = new ImageIcon("xButton.png");
                                JLabel xLabel = new JLabel();
                                xLabel.setIcon(xButton);
                                xLabel.setBounds(255, 5, 40, 40);
                                xLabel.addMouseListener(new MouseAdapter() {

                                    @Override
                                    public void mouseClicked(MouseEvent e) {

                                        dateAndTimeDialog.dispose();
                                    }

                                    @Override
                                    public void mouseEntered(MouseEvent e) {

                                        ImageIcon changedIcon = changeIconColor(xButton, new Color(38, 99, 232));
                                        xLabel.setIcon(changedIcon);
                                    }

                                    @Override
                                    public void mouseExited(MouseEvent e) {

                                        xLabel.setIcon(xButton);
                                    }
                                });

                                JLabel titleLabel = new JLabel("<html><center>Choose<br>Date and Time</center><html>");
                                titleLabel.setFont(new Font("Montserrat", Font.BOLD, 18));
                                titleLabel.setForeground(Color.black);
                                titleLabel.setBounds(90, 55, 300, 50);

                                JTextField dateField = new JTextField();
                                dateField.setFont(new Font("Montserrat", Font.PLAIN, 13));
                                dateField.setCaretColor(Color.black);
                                dateField.setBounds(85, 130, 140, 30);
                                dateField.setText("Date(YYYY-MM-DD)");
                                dateField.addFocusListener(new FocusListener() {

                                    @Override
                                    public void focusGained(FocusEvent e) {

                                        if (dateField.getText().equals("Date(YYYY-MM-DD)")) {

                                            dateField.setText("");
                                        }
                                    }

                                    @Override
                                    public void focusLost(FocusEvent e) {

                                        if (dateField.getText().isEmpty()) {

                                            dateField.setText("Date(YYYY-MM-DD)");
                                        }
                                    }
                                });

                                JTextField timeField = new JTextField();
                                timeField.setFont(new Font("Montserrat", Font.PLAIN, 13));
                                timeField.setCaretColor(Color.black);
                                timeField.setBounds(85, 180, 140, 30);
                                timeField.setText("Time (HH:MM AM/PM)");
                                timeField.addFocusListener(new FocusListener() {

                                    @Override
                                    public void focusGained(FocusEvent e) {

                                        if (timeField.getText().equals("Time (HH:MM AM/PM)")) {

                                            timeField.setText("");
                                        }
                                    }

                                    @Override
                                    public void focusLost(FocusEvent e) {

                                        if (timeField.getText().isEmpty()) {

                                            timeField.setText("Time (HH:MM AM/PM)");
                                        }
                                    }
                                });

                                JButton backButton = new JButton("Back");
                                backButton.setFocusable(false);
                                backButton.setFont(new Font("Montserrat", Font.PLAIN, 11));
                                backButton.setBounds(75, 230, 70, 28);
                                backButton.addActionListener(new ActionListener() {

                                    @Override
                                    public void actionPerformed(ActionEvent e) {

                                        dateAndTimeDialog.dispose();

                                    }
                                });

                                JButton nextButton = new JButton("Submit");
                                nextButton.setFocusable(false);
                                nextButton.setFont(new Font("Montserrat", Font.PLAIN, 10));
                                nextButton.setBounds(165, 230, 70, 28);
                                nextButton.addActionListener(new ActionListener() {

                                    @Override
                                    public void actionPerformed(ActionEvent e) {

                                        String date = dateField.getText();
                                        String time = timeField.getText();

                                        ScheduledService SS = new ScheduledService(service, date, time);
                                        SSList.add(SS);

                                        serviceDetails.dispose();
                                        userHomeFrame.dispose();
                                        userHomePage(account);
                                    }
                                });

                                dateAndTimeDialog.add(xLabel);
                                dateAndTimeDialog.add(titleLabel);
                                dateAndTimeDialog.add(dateField);
                                dateAndTimeDialog.add(timeField);
                                dateAndTimeDialog.add(backButton);
                                dateAndTimeDialog.add(nextButton);
                                dateAndTimeDialog.setVisible(true);
                            }
                        });

                        vehicleInfoDialog.add(xLabel);
                        vehicleInfoDialog.add(titleLabel);
                        vehicleInfoDialog.add(makeField);
                        vehicleInfoDialog.add(modelField);
                        vehicleInfoDialog.add(yearField);
                        vehicleInfoDialog.add(mileageField);
                        vehicleInfoDialog.add(backButton);
                        vehicleInfoDialog.add(nextButton);

                        vehicleInfoDialog.setVisible(true);
                    }
                });

                serviceDetails.add(xLabel);
                serviceDetails.add(header);
                serviceDetails.add(subHeading);
                serviceDetails.add(content);
                serviceDetails.add(subHeading2);
                serviceDetails.add(content2);
                serviceDetails.add(backButton);
                serviceDetails.add(availButton);
                serviceDetails.setVisible(true);
            }
        });
        timingBeltReplacementLabel.setBounds(435, 370, 175, 130);

        ImageIcon suspensionAndSteeringServicePic = new ImageIcon("suspensionAndSteeringService.jpg");
        JLabel suspensionAndSteeringServiceLabel = new JLabel("Suspension And Steering",
                suspensionAndSteeringServicePic, JLabel.CENTER);
        suspensionAndSteeringServiceLabel.setVerticalTextPosition(JLabel.BOTTOM);
        suspensionAndSteeringServiceLabel.setHorizontalTextPosition(JLabel.CENTER);
        suspensionAndSteeringServiceLabel.setVerticalAlignment(JLabel.CENTER);
        suspensionAndSteeringServiceLabel.setHorizontalAlignment(JLabel.CENTER);
        suspensionAndSteeringServiceLabel.setFont(new Font("Montserrat", Font.BOLD, 12));
        Border suspensionAndSteeringServiceBorder = BorderFactory.createLineBorder(Color.black, 1);
        suspensionAndSteeringServiceLabel.setBorder(suspensionAndSteeringServiceBorder);
        suspensionAndSteeringServiceLabel.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {

                suspensionAndSteeringServiceLabel.setForeground(new Color(38, 99, 232));
                Border tempBorder = BorderFactory.createLineBorder(new Color(38, 99, 232), 1);
                suspensionAndSteeringServiceLabel.setBorder(tempBorder);
            }

            @Override
            public void mouseExited(MouseEvent e) {

                suspensionAndSteeringServiceLabel.setForeground(Color.black);
                suspensionAndSteeringServiceLabel.setBorder(suspensionAndSteeringServiceBorder);
            }

            @Override
            public void mouseClicked(MouseEvent e) {

                JDialog serviceDetails = new JDialog(userHomeFrame, null, true);
                serviceDetails.setUndecorated(true);
                serviceDetails.setLocation(550, 130);
                serviceDetails.setSize(300, 400);
                serviceDetails.setLayout(null);
                serviceDetails.getContentPane().setBackground(Color.white);

                ImageIcon xButton = new ImageIcon("xButton.png");
                JLabel xLabel = new JLabel();
                xLabel.setIcon(xButton);
                xLabel.setBounds(253, 5, 40, 40);
                xLabel.addMouseListener(new MouseAdapter() {

                    @Override
                    public void mouseClicked(MouseEvent e) {

                        serviceDetails.dispose();
                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {

                        ImageIcon changedIcon = changeIconColor(xButton, new Color(38, 99, 232));
                        xLabel.setIcon(changedIcon);
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {

                        xLabel.setIcon(xButton);
                    }
                });

                JLabel header = new JLabel("Suspension and Steering");
                header.setFont(new Font("Montserrat", Font.BOLD, 18));
                header.setBounds(43, 50, 250, 30);

                JLabel subHeading = new JLabel("Potential Materials Needed:");
                subHeading.setFont(new Font("Montserrat", Font.BOLD, 15));
                subHeading.setBounds(55, 105, 200, 30);

                JLabel content = new JLabel(
                        "<html><center>Shock Absorber : PHP 1,500 - 5,000 (per unit)<br><br>Control Arms : PHP 1,000 - 3,000 (per unit)<br><br>Tie Rods : PHP 500 - 1,500 (per unit)</center><html>");
                content.setFont(new Font("Montserrat", Font.PLAIN, 12));
                content.setBounds(32, 130, 300, 100);

                JLabel subHeading2 = new JLabel("Service Charge:");
                subHeading2.setFont(new Font("Montserrat", Font.BOLD, 15));
                subHeading2.setBounds(93, 230, 200, 30);

                JLabel content2 = new JLabel(
                        "PHP 500 - 1,500 (per component)");
                content2.setFont(new Font("Montserrat", Font.PLAIN, 12));
                content2.setBounds(60, 230, 300, 100);

                JButton backButton = new JButton("Back");
                backButton.setFocusable(false);
                backButton.setFont(new Font("Montserrat", Font.PLAIN, 11));
                backButton.setBounds(70, 320, 70, 28);
                backButton.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {

                        serviceDetails.dispose();
                    }
                });

                JButton availButton = new JButton("Avail");
                availButton.setFocusable(false);
                availButton.setFont(new Font("Montserrat", Font.PLAIN, 11));
                availButton.setBounds(160, 320, 70, 28);
                availButton.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {

                        JDialog vehicleInfoDialog = new JDialog(serviceDetails, null, true);
                        vehicleInfoDialog.setUndecorated(true);
                        vehicleInfoDialog.setLocation(650, 100);
                        vehicleInfoDialog.setSize(350, 450);
                        vehicleInfoDialog.setLayout(null);
                        vehicleInfoDialog.getContentPane().setBackground(Color.white);

                        ImageIcon xButton = new ImageIcon("xButton.png");
                        JLabel xLabel = new JLabel();
                        xLabel.setIcon(xButton);
                        xLabel.setBounds(305, 5, 40, 40);
                        xLabel.addMouseListener(new MouseAdapter() {

                            @Override
                            public void mouseClicked(MouseEvent e) {

                                vehicleInfoDialog.dispose();
                            }

                            @Override
                            public void mouseEntered(MouseEvent e) {

                                ImageIcon changedIcon = changeIconColor(xButton, new Color(38, 99, 232));
                                xLabel.setIcon(changedIcon);
                            }

                            @Override
                            public void mouseExited(MouseEvent e) {

                                xLabel.setIcon(xButton);
                            }
                        });

                        JLabel titleLabel = new JLabel("Vehicle Information");
                        titleLabel.setFont(new Font("Montserrat", Font.BOLD, 20));
                        titleLabel.setForeground(Color.black);
                        titleLabel.setBounds(90, 55, 300, 50);

                        JTextField makeField = new JTextField();
                        makeField.setFont(new Font("Montserrat", Font.PLAIN, 13));
                        makeField.setCaretColor(Color.black);
                        makeField.setBounds(30, 140, 130, 30);
                        makeField.setText("Make");
                        makeField.addFocusListener(new FocusListener() {

                            @Override
                            public void focusGained(FocusEvent e) {

                                if (makeField.getText().equals("Make")) {

                                    makeField.setText("");
                                }
                            }

                            @Override
                            public void focusLost(FocusEvent e) {

                                if (makeField.getText().isEmpty()) {

                                    makeField.setText("Make");
                                }
                            }
                        });

                        JTextField modelField = new JTextField();
                        modelField.setFont(new Font("Montserrat", Font.PLAIN, 13));
                        modelField.setCaretColor(Color.black);
                        modelField.setBounds(190, 140, 130, 30);
                        modelField.setText("Model");
                        modelField.addFocusListener(new FocusListener() {

                            @Override
                            public void focusGained(FocusEvent e) {

                                if (modelField.getText().equals("Model")) {

                                    modelField.setText("");
                                }
                            }

                            @Override
                            public void focusLost(FocusEvent e) {

                                if (modelField.getText().isEmpty()) {

                                    modelField.setText("Model");
                                }
                            }
                        });

                        JTextField yearField = new JTextField();
                        yearField.setFont(new Font("Montserrat", Font.PLAIN, 13));
                        yearField.setCaretColor(Color.black);
                        yearField.setBounds(30, 200, 130, 30);
                        yearField.setText("Year");
                        yearField.addFocusListener(new FocusListener() {

                            @Override
                            public void focusGained(FocusEvent e) {

                                if (yearField.getText().equals("Year")) {

                                    yearField.setText("");
                                }
                            }

                            @Override
                            public void focusLost(FocusEvent e) {

                                if (yearField.getText().isEmpty()) {

                                    yearField.setText("Year");
                                }
                            }
                        });

                        JTextField mileageField = new JTextField();
                        mileageField.setFont(new Font("Montserrat", Font.PLAIN, 13));
                        mileageField.setCaretColor(Color.black);
                        mileageField.setBounds(190, 200, 130, 30);
                        mileageField.setText("Milieage");
                        mileageField.addFocusListener(new FocusListener() {

                            @Override
                            public void focusGained(FocusEvent e) {

                                if (mileageField.getText().equals("Milieage")) {

                                    mileageField.setText("");
                                }
                            }

                            @Override
                            public void focusLost(FocusEvent e) {

                                if (mileageField.getText().isEmpty()) {

                                    mileageField.setText("Milieage");
                                }
                            }
                        });

                        JButton backButton = new JButton("Back");
                        backButton.setFocusable(false);
                        backButton.setFont(new Font("Montserrat", Font.PLAIN, 11));
                        backButton.setBounds(95, 320, 70, 28);
                        backButton.addActionListener(new ActionListener() {

                            @Override
                            public void actionPerformed(ActionEvent e) {

                                vehicleInfoDialog.dispose();

                            }
                        });

                        JButton nextButton = new JButton("Next");
                        nextButton.setFocusable(false);
                        nextButton.setFont(new Font("Montserrat", Font.PLAIN, 11));
                        nextButton.setBounds(185, 320, 70, 28);
                        nextButton.addActionListener(new ActionListener() {

                            @Override
                            public void actionPerformed(ActionEvent e) {

                                String make = makeField.getText();
                                String model = modelField.getText();
                                String year = yearField.getText();
                                double mileage = Double.parseDouble(mileageField.getText());

                                Vehicle vehicle = new Vehicle(make, model, year, mileage);

                                Service service = new SuspensionAndSteeringService(account, vehicle, 1, 1, 1);

                                JDialog dateAndTimeDialog = new JDialog(vehicleInfoDialog, null, true);
                                dateAndTimeDialog.setUndecorated(true);
                                dateAndTimeDialog.setLocation(220, 40);
                                dateAndTimeDialog.setSize(300, 300);
                                dateAndTimeDialog.setLayout(null);
                                dateAndTimeDialog.getContentPane().setBackground(Color.white);

                                ImageIcon xButton = new ImageIcon("xButton.png");
                                JLabel xLabel = new JLabel();
                                xLabel.setIcon(xButton);
                                xLabel.setBounds(255, 5, 40, 40);
                                xLabel.addMouseListener(new MouseAdapter() {

                                    @Override
                                    public void mouseClicked(MouseEvent e) {

                                        dateAndTimeDialog.dispose();
                                    }

                                    @Override
                                    public void mouseEntered(MouseEvent e) {

                                        ImageIcon changedIcon = changeIconColor(xButton, new Color(38, 99, 232));
                                        xLabel.setIcon(changedIcon);
                                    }

                                    @Override
                                    public void mouseExited(MouseEvent e) {

                                        xLabel.setIcon(xButton);
                                    }
                                });

                                JLabel titleLabel = new JLabel("<html><center>Choose<br>Date and Time</center><html>");
                                titleLabel.setFont(new Font("Montserrat", Font.BOLD, 18));
                                titleLabel.setForeground(Color.black);
                                titleLabel.setBounds(90, 55, 300, 50);

                                JTextField dateField = new JTextField();
                                dateField.setFont(new Font("Montserrat", Font.PLAIN, 13));
                                dateField.setCaretColor(Color.black);
                                dateField.setBounds(85, 130, 140, 30);
                                dateField.setText("Date(YYYY-MM-DD)");
                                dateField.addFocusListener(new FocusListener() {

                                    @Override
                                    public void focusGained(FocusEvent e) {

                                        if (dateField.getText().equals("Date(YYYY-MM-DD)")) {

                                            dateField.setText("");
                                        }
                                    }

                                    @Override
                                    public void focusLost(FocusEvent e) {

                                        if (dateField.getText().isEmpty()) {

                                            dateField.setText("Date(YYYY-MM-DD)");
                                        }
                                    }
                                });

                                JTextField timeField = new JTextField();
                                timeField.setFont(new Font("Montserrat", Font.PLAIN, 13));
                                timeField.setCaretColor(Color.black);
                                timeField.setBounds(85, 180, 140, 30);
                                timeField.setText("Time (HH:MM AM/PM)");
                                timeField.addFocusListener(new FocusListener() {

                                    @Override
                                    public void focusGained(FocusEvent e) {

                                        if (timeField.getText().equals("Time (HH:MM AM/PM)")) {

                                            timeField.setText("");
                                        }
                                    }

                                    @Override
                                    public void focusLost(FocusEvent e) {

                                        if (timeField.getText().isEmpty()) {

                                            timeField.setText("Time (HH:MM AM/PM)");
                                        }
                                    }
                                });

                                JButton backButton = new JButton("Back");
                                backButton.setFocusable(false);
                                backButton.setFont(new Font("Montserrat", Font.PLAIN, 11));
                                backButton.setBounds(75, 230, 70, 28);
                                backButton.addActionListener(new ActionListener() {

                                    @Override
                                    public void actionPerformed(ActionEvent e) {

                                        dateAndTimeDialog.dispose();

                                    }
                                });

                                JButton nextButton = new JButton("Submit");
                                nextButton.setFocusable(false);
                                nextButton.setFont(new Font("Montserrat", Font.PLAIN, 10));
                                nextButton.setBounds(165, 230, 70, 28);
                                nextButton.addActionListener(new ActionListener() {

                                    @Override
                                    public void actionPerformed(ActionEvent e) {

                                        String date = dateField.getText();
                                        String time = timeField.getText();

                                        ScheduledService SS = new ScheduledService(service, date, time);
                                        SSList.add(SS);

                                        serviceDetails.dispose();
                                        userHomeFrame.dispose();
                                        userHomePage(account);
                                    }
                                });

                                dateAndTimeDialog.add(xLabel);
                                dateAndTimeDialog.add(titleLabel);
                                dateAndTimeDialog.add(dateField);
                                dateAndTimeDialog.add(timeField);
                                dateAndTimeDialog.add(backButton);
                                dateAndTimeDialog.add(nextButton);
                                dateAndTimeDialog.setVisible(true);
                            }
                        });

                        vehicleInfoDialog.add(xLabel);
                        vehicleInfoDialog.add(titleLabel);
                        vehicleInfoDialog.add(makeField);
                        vehicleInfoDialog.add(modelField);
                        vehicleInfoDialog.add(yearField);
                        vehicleInfoDialog.add(mileageField);
                        vehicleInfoDialog.add(backButton);
                        vehicleInfoDialog.add(nextButton);

                        vehicleInfoDialog.setVisible(true);
                    }
                });

                serviceDetails.add(xLabel);
                serviceDetails.add(header);
                serviceDetails.add(subHeading);
                serviceDetails.add(content);
                serviceDetails.add(subHeading2);
                serviceDetails.add(content2);
                serviceDetails.add(backButton);
                serviceDetails.add(availButton);
                serviceDetails.setVisible(true);
            }
        });
        suspensionAndSteeringServiceLabel.setBounds(55, 510, 175, 130);

        ImageIcon exhaustSystemInspectionPic = new ImageIcon("exhaustSystemInspection.jpg");
        JLabel exhaustSystemInspectionLabel = new JLabel("Exhaust System Inspection",
                exhaustSystemInspectionPic, JLabel.CENTER);
        exhaustSystemInspectionLabel.setVerticalTextPosition(JLabel.BOTTOM);
        exhaustSystemInspectionLabel.setHorizontalTextPosition(JLabel.CENTER);
        exhaustSystemInspectionLabel.setVerticalAlignment(JLabel.CENTER);
        exhaustSystemInspectionLabel.setHorizontalAlignment(JLabel.CENTER);
        exhaustSystemInspectionLabel.setFont(new Font("Montserrat", Font.BOLD, 12));
        Border exhaustSystemInspectionBorder = BorderFactory.createLineBorder(Color.black, 1);
        exhaustSystemInspectionLabel.setBorder(exhaustSystemInspectionBorder);
        exhaustSystemInspectionLabel.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {

                exhaustSystemInspectionLabel.setForeground(new Color(38, 99, 232));
                Border tempBorder = BorderFactory.createLineBorder(new Color(38, 99, 232), 1);
                exhaustSystemInspectionLabel.setBorder(tempBorder);
            }

            @Override
            public void mouseExited(MouseEvent e) {

                exhaustSystemInspectionLabel.setForeground(Color.black);
                exhaustSystemInspectionLabel.setBorder(exhaustSystemInspectionBorder);
            }

            @Override
            public void mouseClicked(MouseEvent e) {

                JDialog serviceDetails = new JDialog(userHomeFrame, null, true);
                serviceDetails.setUndecorated(true);
                serviceDetails.setLocation(550, 130);
                serviceDetails.setSize(300, 400);
                serviceDetails.setLayout(null);
                serviceDetails.getContentPane().setBackground(Color.white);

                ImageIcon xButton = new ImageIcon("xButton.png");
                JLabel xLabel = new JLabel();
                xLabel.setIcon(xButton);
                xLabel.setBounds(253, 5, 40, 40);
                xLabel.addMouseListener(new MouseAdapter() {

                    @Override
                    public void mouseClicked(MouseEvent e) {

                        serviceDetails.dispose();
                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {

                        ImageIcon changedIcon = changeIconColor(xButton, new Color(38, 99, 232));
                        xLabel.setIcon(changedIcon);
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {

                        xLabel.setIcon(xButton);
                    }
                });

                JLabel header = new JLabel("Exhaust System Inspection");
                header.setFont(new Font("Montserrat", Font.BOLD, 18));
                header.setBounds(37, 50, 250, 30);

                JLabel subHeading = new JLabel("Potential Materials Needed:");
                subHeading.setFont(new Font("Montserrat", Font.BOLD, 15));
                subHeading.setBounds(55, 105, 200, 30);

                JLabel content = new JLabel(
                        "<html><center>Muffler : PHP 2,000 - 5,000<br><br>Catalytic Converter : PHP 5,000 - 10,0000<br><br>Exhaust Pipes : PHP 1,000 - 3,000</center><html>");
                content.setFont(new Font("Montserrat", Font.PLAIN, 12));
                content.setBounds(45, 130, 300, 100);

                JLabel subHeading2 = new JLabel("Service Charge:");
                subHeading2.setFont(new Font("Montserrat", Font.BOLD, 15));
                subHeading2.setBounds(93, 230, 200, 30);

                JLabel content2 = new JLabel(
                        "PHP 300 - 1,000");
                content2.setFont(new Font("Montserrat", Font.PLAIN, 12));
                content2.setBounds(103, 230, 300, 100);

                JButton backButton = new JButton("Back");
                backButton.setFocusable(false);
                backButton.setFont(new Font("Montserrat", Font.PLAIN, 11));
                backButton.setBounds(70, 320, 70, 28);
                backButton.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {

                        serviceDetails.dispose();
                    }
                });

                JButton availButton = new JButton("Avail");
                availButton.setFocusable(false);
                availButton.setFont(new Font("Montserrat", Font.PLAIN, 11));
                availButton.setBounds(160, 320, 70, 28);
                availButton.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {

                        JDialog vehicleInfoDialog = new JDialog(serviceDetails, null, true);
                        vehicleInfoDialog.setUndecorated(true);
                        vehicleInfoDialog.setLocation(650, 100);
                        vehicleInfoDialog.setSize(350, 450);
                        vehicleInfoDialog.setLayout(null);
                        vehicleInfoDialog.getContentPane().setBackground(Color.white);

                        ImageIcon xButton = new ImageIcon("xButton.png");
                        JLabel xLabel = new JLabel();
                        xLabel.setIcon(xButton);
                        xLabel.setBounds(305, 5, 40, 40);
                        xLabel.addMouseListener(new MouseAdapter() {

                            @Override
                            public void mouseClicked(MouseEvent e) {

                                vehicleInfoDialog.dispose();
                            }

                            @Override
                            public void mouseEntered(MouseEvent e) {

                                ImageIcon changedIcon = changeIconColor(xButton, new Color(38, 99, 232));
                                xLabel.setIcon(changedIcon);
                            }

                            @Override
                            public void mouseExited(MouseEvent e) {

                                xLabel.setIcon(xButton);
                            }
                        });

                        JLabel titleLabel = new JLabel("Vehicle Information");
                        titleLabel.setFont(new Font("Montserrat", Font.BOLD, 20));
                        titleLabel.setForeground(Color.black);
                        titleLabel.setBounds(90, 55, 300, 50);

                        JTextField makeField = new JTextField();
                        makeField.setFont(new Font("Montserrat", Font.PLAIN, 13));
                        makeField.setCaretColor(Color.black);
                        makeField.setBounds(30, 140, 130, 30);
                        makeField.setText("Make");
                        makeField.addFocusListener(new FocusListener() {

                            @Override
                            public void focusGained(FocusEvent e) {

                                if (makeField.getText().equals("Make")) {

                                    makeField.setText("");
                                }
                            }

                            @Override
                            public void focusLost(FocusEvent e) {

                                if (makeField.getText().isEmpty()) {

                                    makeField.setText("Make");
                                }
                            }
                        });

                        JTextField modelField = new JTextField();
                        modelField.setFont(new Font("Montserrat", Font.PLAIN, 13));
                        modelField.setCaretColor(Color.black);
                        modelField.setBounds(190, 140, 130, 30);
                        modelField.setText("Model");
                        modelField.addFocusListener(new FocusListener() {

                            @Override
                            public void focusGained(FocusEvent e) {

                                if (modelField.getText().equals("Model")) {

                                    modelField.setText("");
                                }
                            }

                            @Override
                            public void focusLost(FocusEvent e) {

                                if (modelField.getText().isEmpty()) {

                                    modelField.setText("Model");
                                }
                            }
                        });

                        JTextField yearField = new JTextField();
                        yearField.setFont(new Font("Montserrat", Font.PLAIN, 13));
                        yearField.setCaretColor(Color.black);
                        yearField.setBounds(30, 200, 130, 30);
                        yearField.setText("Year");
                        yearField.addFocusListener(new FocusListener() {

                            @Override
                            public void focusGained(FocusEvent e) {

                                if (yearField.getText().equals("Year")) {

                                    yearField.setText("");
                                }
                            }

                            @Override
                            public void focusLost(FocusEvent e) {

                                if (yearField.getText().isEmpty()) {

                                    yearField.setText("Year");
                                }
                            }
                        });

                        JTextField mileageField = new JTextField();
                        mileageField.setFont(new Font("Montserrat", Font.PLAIN, 13));
                        mileageField.setCaretColor(Color.black);
                        mileageField.setBounds(190, 200, 130, 30);
                        mileageField.setText("Milieage");
                        mileageField.addFocusListener(new FocusListener() {

                            @Override
                            public void focusGained(FocusEvent e) {

                                if (mileageField.getText().equals("Milieage")) {

                                    mileageField.setText("");
                                }
                            }

                            @Override
                            public void focusLost(FocusEvent e) {

                                if (mileageField.getText().isEmpty()) {

                                    mileageField.setText("Milieage");
                                }
                            }
                        });

                        JButton backButton = new JButton("Back");
                        backButton.setFocusable(false);
                        backButton.setFont(new Font("Montserrat", Font.PLAIN, 11));
                        backButton.setBounds(95, 320, 70, 28);
                        backButton.addActionListener(new ActionListener() {

                            @Override
                            public void actionPerformed(ActionEvent e) {

                                vehicleInfoDialog.dispose();

                            }
                        });

                        JButton nextButton = new JButton("Next");
                        nextButton.setFocusable(false);
                        nextButton.setFont(new Font("Montserrat", Font.PLAIN, 11));
                        nextButton.setBounds(185, 320, 70, 28);
                        nextButton.addActionListener(new ActionListener() {

                            @Override
                            public void actionPerformed(ActionEvent e) {

                                String make = makeField.getText();
                                String model = modelField.getText();
                                String year = yearField.getText();
                                double mileage = Double.parseDouble(mileageField.getText());

                                Vehicle vehicle = new Vehicle(make, model, year, mileage);

                                Service service = new ExhaustSystemInspectionAndRepairService(account, vehicle, 1, 1,
                                        1);

                                JDialog dateAndTimeDialog = new JDialog(vehicleInfoDialog, null, true);
                                dateAndTimeDialog.setUndecorated(true);
                                dateAndTimeDialog.setLocation(220, 40);
                                dateAndTimeDialog.setSize(300, 300);
                                dateAndTimeDialog.setLayout(null);
                                dateAndTimeDialog.getContentPane().setBackground(Color.white);

                                ImageIcon xButton = new ImageIcon("xButton.png");
                                JLabel xLabel = new JLabel();
                                xLabel.setIcon(xButton);
                                xLabel.setBounds(255, 5, 40, 40);
                                xLabel.addMouseListener(new MouseAdapter() {

                                    @Override
                                    public void mouseClicked(MouseEvent e) {

                                        dateAndTimeDialog.dispose();
                                    }

                                    @Override
                                    public void mouseEntered(MouseEvent e) {

                                        ImageIcon changedIcon = changeIconColor(xButton, new Color(38, 99, 232));
                                        xLabel.setIcon(changedIcon);
                                    }

                                    @Override
                                    public void mouseExited(MouseEvent e) {

                                        xLabel.setIcon(xButton);
                                    }
                                });

                                JLabel titleLabel = new JLabel("<html><center>Choose<br>Date and Time</center><html>");
                                titleLabel.setFont(new Font("Montserrat", Font.BOLD, 18));
                                titleLabel.setForeground(Color.black);
                                titleLabel.setBounds(90, 55, 300, 50);

                                JTextField dateField = new JTextField();
                                dateField.setFont(new Font("Montserrat", Font.PLAIN, 13));
                                dateField.setCaretColor(Color.black);
                                dateField.setBounds(85, 130, 140, 30);
                                dateField.setText("Date(YYYY-MM-DD)");
                                dateField.addFocusListener(new FocusListener() {

                                    @Override
                                    public void focusGained(FocusEvent e) {

                                        if (dateField.getText().equals("Date(YYYY-MM-DD)")) {

                                            dateField.setText("");
                                        }
                                    }

                                    @Override
                                    public void focusLost(FocusEvent e) {

                                        if (dateField.getText().isEmpty()) {

                                            dateField.setText("Date(YYYY-MM-DD)");
                                        }
                                    }
                                });

                                JTextField timeField = new JTextField();
                                timeField.setFont(new Font("Montserrat", Font.PLAIN, 13));
                                timeField.setCaretColor(Color.black);
                                timeField.setBounds(85, 180, 140, 30);
                                timeField.setText("Time (HH:MM AM/PM)");
                                timeField.addFocusListener(new FocusListener() {

                                    @Override
                                    public void focusGained(FocusEvent e) {

                                        if (timeField.getText().equals("Time (HH:MM AM/PM)")) {

                                            timeField.setText("");
                                        }
                                    }

                                    @Override
                                    public void focusLost(FocusEvent e) {

                                        if (timeField.getText().isEmpty()) {

                                            timeField.setText("Time (HH:MM AM/PM)");
                                        }
                                    }
                                });

                                JButton backButton = new JButton("Back");
                                backButton.setFocusable(false);
                                backButton.setFont(new Font("Montserrat", Font.PLAIN, 11));
                                backButton.setBounds(75, 230, 70, 28);
                                backButton.addActionListener(new ActionListener() {

                                    @Override
                                    public void actionPerformed(ActionEvent e) {

                                        dateAndTimeDialog.dispose();

                                    }
                                });

                                JButton nextButton = new JButton("Submit");
                                nextButton.setFocusable(false);
                                nextButton.setFont(new Font("Montserrat", Font.PLAIN, 10));
                                nextButton.setBounds(165, 230, 70, 28);
                                nextButton.addActionListener(new ActionListener() {

                                    @Override
                                    public void actionPerformed(ActionEvent e) {

                                        String date = dateField.getText();
                                        String time = timeField.getText();

                                        ScheduledService SS = new ScheduledService(service, date, time);
                                        SSList.add(SS);

                                        serviceDetails.dispose();
                                        userHomeFrame.dispose();
                                        userHomePage(account);
                                    }
                                });

                                dateAndTimeDialog.add(xLabel);
                                dateAndTimeDialog.add(titleLabel);
                                dateAndTimeDialog.add(dateField);
                                dateAndTimeDialog.add(timeField);
                                dateAndTimeDialog.add(backButton);
                                dateAndTimeDialog.add(nextButton);
                                dateAndTimeDialog.setVisible(true);
                            }
                        });

                        vehicleInfoDialog.add(xLabel);
                        vehicleInfoDialog.add(titleLabel);
                        vehicleInfoDialog.add(makeField);
                        vehicleInfoDialog.add(modelField);
                        vehicleInfoDialog.add(yearField);
                        vehicleInfoDialog.add(mileageField);
                        vehicleInfoDialog.add(backButton);
                        vehicleInfoDialog.add(nextButton);

                        vehicleInfoDialog.setVisible(true);
                    }
                });

                serviceDetails.add(xLabel);
                serviceDetails.add(header);
                serviceDetails.add(subHeading);
                serviceDetails.add(content);
                serviceDetails.add(subHeading2);
                serviceDetails.add(content2);
                serviceDetails.add(backButton);
                serviceDetails.add(availButton);
                serviceDetails.setVisible(true);
            }
        });
        exhaustSystemInspectionLabel.setBounds(245, 510, 175, 130);

        ImageIcon coolingSystemServicePic = new ImageIcon("coolingSystemService.jpg");
        JLabel coolingSystemServiceLabel = new JLabel("Cooling System Service",
                coolingSystemServicePic, JLabel.CENTER);
        coolingSystemServiceLabel.setVerticalTextPosition(JLabel.BOTTOM);
        coolingSystemServiceLabel.setHorizontalTextPosition(JLabel.CENTER);
        coolingSystemServiceLabel.setVerticalAlignment(JLabel.CENTER);
        coolingSystemServiceLabel.setHorizontalAlignment(JLabel.CENTER);
        coolingSystemServiceLabel.setFont(new Font("Montserrat", Font.BOLD, 12));
        Border coolingSystemServiceBorder = BorderFactory.createLineBorder(Color.black, 1);
        coolingSystemServiceLabel.setBorder(coolingSystemServiceBorder);
        coolingSystemServiceLabel.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {

                coolingSystemServiceLabel.setForeground(new Color(38, 99, 232));
                Border tempBorder = BorderFactory.createLineBorder(new Color(38, 99, 232), 1);
                coolingSystemServiceLabel.setBorder(tempBorder);
            }

            @Override
            public void mouseExited(MouseEvent e) {

                coolingSystemServiceLabel.setForeground(Color.black);
                coolingSystemServiceLabel.setBorder(coolingSystemServiceBorder);
            }

            @Override
            public void mouseClicked(MouseEvent e) {

                JDialog serviceDetails = new JDialog(userHomeFrame, null, true);
                serviceDetails.setUndecorated(true);
                serviceDetails.setLocation(550, 130);
                serviceDetails.setSize(300, 400);
                serviceDetails.setLayout(null);
                serviceDetails.getContentPane().setBackground(Color.white);

                ImageIcon xButton = new ImageIcon("xButton.png");
                JLabel xLabel = new JLabel();
                xLabel.setIcon(xButton);
                xLabel.setBounds(253, 5, 40, 40);
                xLabel.addMouseListener(new MouseAdapter() {

                    @Override
                    public void mouseClicked(MouseEvent e) {

                        serviceDetails.dispose();
                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {

                        ImageIcon changedIcon = changeIconColor(xButton, new Color(38, 99, 232));
                        xLabel.setIcon(changedIcon);
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {

                        xLabel.setIcon(xButton);
                    }
                });

                JLabel header = new JLabel("Cooling System Service");
                header.setFont(new Font("Montserrat", Font.BOLD, 18));
                header.setBounds(45, 50, 250, 30);

                JLabel subHeading = new JLabel("Potential Materials Needed:");
                subHeading.setFont(new Font("Montserrat", Font.BOLD, 15));
                subHeading.setBounds(55, 105, 200, 30);

                JLabel content = new JLabel(
                        "<html><center>Radiator : PHP 2,000 - PHP 5,000<br><br>Water Pump : PHP 1,500 - PHP 4,0000<br><br>Thermostat : PHP 500 - 1,500<br><br>Hoses : PHP 500 - 1,500</center><html>");
                content.setFont(new Font("Montserrat", Font.PLAIN, 12));
                content.setBounds(43, 140, 300, 120);

                JLabel subHeading2 = new JLabel("Service Charge:");
                subHeading2.setFont(new Font("Montserrat", Font.BOLD, 15));
                subHeading2.setBounds(93, 275, 200, 30);

                JLabel content2 = new JLabel(
                        "PHP 500 - 1,500");
                content2.setFont(new Font("Montserrat", Font.PLAIN, 12));
                content2.setBounds(103, 263, 300, 100);

                JButton backButton = new JButton("Back");
                backButton.setFocusable(false);
                backButton.setFont(new Font("Montserrat", Font.PLAIN, 11));
                backButton.setBounds(70, 340, 70, 28);
                backButton.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {

                        serviceDetails.dispose();
                    }
                });

                JButton availButton = new JButton("Avail");
                availButton.setFocusable(false);
                availButton.setFont(new Font("Montserrat", Font.PLAIN, 11));
                availButton.setBounds(160, 340, 70, 28);
                availButton.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {

                        JDialog vehicleInfoDialog = new JDialog(serviceDetails, null, true);
                        vehicleInfoDialog.setUndecorated(true);
                        vehicleInfoDialog.setLocation(650, 100);
                        vehicleInfoDialog.setSize(350, 450);
                        vehicleInfoDialog.setLayout(null);
                        vehicleInfoDialog.getContentPane().setBackground(Color.white);

                        ImageIcon xButton = new ImageIcon("xButton.png");
                        JLabel xLabel = new JLabel();
                        xLabel.setIcon(xButton);
                        xLabel.setBounds(305, 5, 40, 40);
                        xLabel.addMouseListener(new MouseAdapter() {

                            @Override
                            public void mouseClicked(MouseEvent e) {

                                vehicleInfoDialog.dispose();
                            }

                            @Override
                            public void mouseEntered(MouseEvent e) {

                                ImageIcon changedIcon = changeIconColor(xButton, new Color(38, 99, 232));
                                xLabel.setIcon(changedIcon);
                            }

                            @Override
                            public void mouseExited(MouseEvent e) {

                                xLabel.setIcon(xButton);
                            }
                        });

                        JLabel titleLabel = new JLabel("Vehicle Information");
                        titleLabel.setFont(new Font("Montserrat", Font.BOLD, 20));
                        titleLabel.setForeground(Color.black);
                        titleLabel.setBounds(90, 55, 300, 50);

                        JTextField makeField = new JTextField();
                        makeField.setFont(new Font("Montserrat", Font.PLAIN, 13));
                        makeField.setCaretColor(Color.black);
                        makeField.setBounds(30, 140, 130, 30);
                        makeField.setText("Make");
                        makeField.addFocusListener(new FocusListener() {

                            @Override
                            public void focusGained(FocusEvent e) {

                                if (makeField.getText().equals("Make")) {

                                    makeField.setText("");
                                }
                            }

                            @Override
                            public void focusLost(FocusEvent e) {

                                if (makeField.getText().isEmpty()) {

                                    makeField.setText("Make");
                                }
                            }
                        });

                        JTextField modelField = new JTextField();
                        modelField.setFont(new Font("Montserrat", Font.PLAIN, 13));
                        modelField.setCaretColor(Color.black);
                        modelField.setBounds(190, 140, 130, 30);
                        modelField.setText("Model");
                        modelField.addFocusListener(new FocusListener() {

                            @Override
                            public void focusGained(FocusEvent e) {

                                if (modelField.getText().equals("Model")) {

                                    modelField.setText("");
                                }
                            }

                            @Override
                            public void focusLost(FocusEvent e) {

                                if (modelField.getText().isEmpty()) {

                                    modelField.setText("Model");
                                }
                            }
                        });

                        JTextField yearField = new JTextField();
                        yearField.setFont(new Font("Montserrat", Font.PLAIN, 13));
                        yearField.setCaretColor(Color.black);
                        yearField.setBounds(30, 200, 130, 30);
                        yearField.setText("Year");
                        yearField.addFocusListener(new FocusListener() {

                            @Override
                            public void focusGained(FocusEvent e) {

                                if (yearField.getText().equals("Year")) {

                                    yearField.setText("");
                                }
                            }

                            @Override
                            public void focusLost(FocusEvent e) {

                                if (yearField.getText().isEmpty()) {

                                    yearField.setText("Year");
                                }
                            }
                        });

                        JTextField mileageField = new JTextField();
                        mileageField.setFont(new Font("Montserrat", Font.PLAIN, 13));
                        mileageField.setCaretColor(Color.black);
                        mileageField.setBounds(190, 200, 130, 30);
                        mileageField.setText("Milieage");
                        mileageField.addFocusListener(new FocusListener() {

                            @Override
                            public void focusGained(FocusEvent e) {

                                if (mileageField.getText().equals("Milieage")) {

                                    mileageField.setText("");
                                }
                            }

                            @Override
                            public void focusLost(FocusEvent e) {

                                if (mileageField.getText().isEmpty()) {

                                    mileageField.setText("Milieage");
                                }
                            }
                        });

                        JButton backButton = new JButton("Back");
                        backButton.setFocusable(false);
                        backButton.setFont(new Font("Montserrat", Font.PLAIN, 11));
                        backButton.setBounds(95, 320, 70, 28);
                        backButton.addActionListener(new ActionListener() {

                            @Override
                            public void actionPerformed(ActionEvent e) {

                                vehicleInfoDialog.dispose();

                            }
                        });

                        JButton nextButton = new JButton("Next");
                        nextButton.setFocusable(false);
                        nextButton.setFont(new Font("Montserrat", Font.PLAIN, 11));
                        nextButton.setBounds(185, 320, 70, 28);
                        nextButton.addActionListener(new ActionListener() {

                            @Override
                            public void actionPerformed(ActionEvent e) {

                                String make = makeField.getText();
                                String model = modelField.getText();
                                String year = yearField.getText();
                                double mileage = Double.parseDouble(mileageField.getText());

                                Vehicle vehicle = new Vehicle(make, model, year, mileage);

                                Service service = new CoolingSystemService(account, vehicle, 1, 1, 1, 1);

                                JDialog dateAndTimeDialog = new JDialog(vehicleInfoDialog, null, true);
                                dateAndTimeDialog.setUndecorated(true);
                                dateAndTimeDialog.setLocation(220, 40);
                                dateAndTimeDialog.setSize(300, 300);
                                dateAndTimeDialog.setLayout(null);
                                dateAndTimeDialog.getContentPane().setBackground(Color.white);

                                ImageIcon xButton = new ImageIcon("xButton.png");
                                JLabel xLabel = new JLabel();
                                xLabel.setIcon(xButton);
                                xLabel.setBounds(255, 5, 40, 40);
                                xLabel.addMouseListener(new MouseAdapter() {

                                    @Override
                                    public void mouseClicked(MouseEvent e) {

                                        dateAndTimeDialog.dispose();
                                    }

                                    @Override
                                    public void mouseEntered(MouseEvent e) {

                                        ImageIcon changedIcon = changeIconColor(xButton, new Color(38, 99, 232));
                                        xLabel.setIcon(changedIcon);
                                    }

                                    @Override
                                    public void mouseExited(MouseEvent e) {

                                        xLabel.setIcon(xButton);
                                    }
                                });

                                JLabel titleLabel = new JLabel("<html><center>Choose<br>Date and Time</center><html>");
                                titleLabel.setFont(new Font("Montserrat", Font.BOLD, 18));
                                titleLabel.setForeground(Color.black);
                                titleLabel.setBounds(90, 55, 300, 50);

                                JTextField dateField = new JTextField();
                                dateField.setFont(new Font("Montserrat", Font.PLAIN, 13));
                                dateField.setCaretColor(Color.black);
                                dateField.setBounds(85, 130, 140, 30);
                                dateField.setText("Date(YYYY-MM-DD)");
                                dateField.addFocusListener(new FocusListener() {

                                    @Override
                                    public void focusGained(FocusEvent e) {

                                        if (dateField.getText().equals("Date(YYYY-MM-DD)")) {

                                            dateField.setText("");
                                        }
                                    }

                                    @Override
                                    public void focusLost(FocusEvent e) {

                                        if (dateField.getText().isEmpty()) {

                                            dateField.setText("Date(YYYY-MM-DD)");
                                        }
                                    }
                                });

                                JTextField timeField = new JTextField();
                                timeField.setFont(new Font("Montserrat", Font.PLAIN, 13));
                                timeField.setCaretColor(Color.black);
                                timeField.setBounds(85, 180, 140, 30);
                                timeField.setText("Time (HH:MM AM/PM)");
                                timeField.addFocusListener(new FocusListener() {

                                    @Override
                                    public void focusGained(FocusEvent e) {

                                        if (timeField.getText().equals("Time (HH:MM AM/PM)")) {

                                            timeField.setText("");
                                        }
                                    }

                                    @Override
                                    public void focusLost(FocusEvent e) {

                                        if (timeField.getText().isEmpty()) {

                                            timeField.setText("Time (HH:MM AM/PM)");
                                        }
                                    }
                                });

                                JButton backButton = new JButton("Back");
                                backButton.setFocusable(false);
                                backButton.setFont(new Font("Montserrat", Font.PLAIN, 11));
                                backButton.setBounds(75, 230, 70, 28);
                                backButton.addActionListener(new ActionListener() {

                                    @Override
                                    public void actionPerformed(ActionEvent e) {

                                        dateAndTimeDialog.dispose();

                                    }
                                });

                                JButton nextButton = new JButton("Submit");
                                nextButton.setFocusable(false);
                                nextButton.setFont(new Font("Montserrat", Font.PLAIN, 10));
                                nextButton.setBounds(165, 230, 70, 28);
                                nextButton.addActionListener(new ActionListener() {

                                    @Override
                                    public void actionPerformed(ActionEvent e) {

                                        String date = dateField.getText();
                                        String time = timeField.getText();

                                        ScheduledService SS = new ScheduledService(service, date, time);
                                        SSList.add(SS);

                                        serviceDetails.dispose();
                                        userHomeFrame.dispose();
                                        userHomePage(account);
                                    }
                                });

                                dateAndTimeDialog.add(xLabel);
                                dateAndTimeDialog.add(titleLabel);
                                dateAndTimeDialog.add(dateField);
                                dateAndTimeDialog.add(timeField);
                                dateAndTimeDialog.add(backButton);
                                dateAndTimeDialog.add(nextButton);
                                dateAndTimeDialog.setVisible(true);
                            }
                        });

                        vehicleInfoDialog.add(xLabel);
                        vehicleInfoDialog.add(titleLabel);
                        vehicleInfoDialog.add(makeField);
                        vehicleInfoDialog.add(modelField);
                        vehicleInfoDialog.add(yearField);
                        vehicleInfoDialog.add(mileageField);
                        vehicleInfoDialog.add(backButton);
                        vehicleInfoDialog.add(nextButton);

                        vehicleInfoDialog.setVisible(true);
                    }
                });

                serviceDetails.add(xLabel);
                serviceDetails.add(header);
                serviceDetails.add(subHeading);
                serviceDetails.add(content);
                serviceDetails.add(subHeading2);
                serviceDetails.add(content2);
                serviceDetails.add(backButton);
                serviceDetails.add(availButton);
                serviceDetails.setVisible(true);
            }
        });
        coolingSystemServiceLabel.setBounds(435, 510, 175, 130);

        ImageIcon emissionSystemServicePic = new ImageIcon("emissionSystemService.jpg");
        JLabel emissionSystemServiceLabel = new JLabel("Emission System Service",
                emissionSystemServicePic, JLabel.CENTER);
        emissionSystemServiceLabel.setVerticalTextPosition(JLabel.BOTTOM);
        emissionSystemServiceLabel.setHorizontalTextPosition(JLabel.CENTER);
        emissionSystemServiceLabel.setVerticalAlignment(JLabel.CENTER);
        emissionSystemServiceLabel.setHorizontalAlignment(JLabel.CENTER);
        emissionSystemServiceLabel.setFont(new Font("Montserrat", Font.BOLD, 12));
        Border emissionSystemServiceBorder = BorderFactory.createLineBorder(Color.black, 1);
        emissionSystemServiceLabel.setBorder(emissionSystemServiceBorder);
        emissionSystemServiceLabel.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {

                emissionSystemServiceLabel.setForeground(new Color(38, 99, 232));
                Border tempBorder = BorderFactory.createLineBorder(new Color(38, 99, 232), 1);
                emissionSystemServiceLabel.setBorder(tempBorder);
            }

            @Override
            public void mouseExited(MouseEvent e) {

                emissionSystemServiceLabel.setForeground(Color.black);
                emissionSystemServiceLabel.setBorder(emissionSystemServiceBorder);
            }

            @Override
            public void mouseClicked(MouseEvent e) {

                JDialog serviceDetails = new JDialog(userHomeFrame, null, true);
                serviceDetails.setUndecorated(true);
                serviceDetails.setLocation(550, 130);
                serviceDetails.setSize(300, 400);
                serviceDetails.setLayout(null);
                serviceDetails.getContentPane().setBackground(Color.white);

                ImageIcon xButton = new ImageIcon("xButton.png");
                JLabel xLabel = new JLabel();
                xLabel.setIcon(xButton);
                xLabel.setBounds(253, 5, 40, 40);
                xLabel.addMouseListener(new MouseAdapter() {

                    @Override
                    public void mouseClicked(MouseEvent e) {

                        serviceDetails.dispose();
                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {

                        ImageIcon changedIcon = changeIconColor(xButton, new Color(38, 99, 232));
                        xLabel.setIcon(changedIcon);
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {

                        xLabel.setIcon(xButton);
                    }
                });

                JLabel header = new JLabel("Emission System Service");
                header.setFont(new Font("Montserrat", Font.BOLD, 19));
                header.setBounds(36, 50, 250, 30);

                JLabel subHeading = new JLabel("Potential Materials Needed:");
                subHeading.setFont(new Font("Montserrat", Font.BOLD, 15));
                subHeading.setBounds(55, 105, 200, 30);

                JLabel content = new JLabel(
                        "<html><center>EGR Valve : PHP 1,000 - 3,0000<br><br>Oxygen Sensor : PHP 1,000 - 3,0000</center><html>");
                content.setFont(new Font("Montserrat", Font.PLAIN, 12));
                content.setBounds(53, 130, 300, 100);

                JLabel subHeading2 = new JLabel("Service Charge:");
                subHeading2.setFont(new Font("Montserrat", Font.BOLD, 15));
                subHeading2.setBounds(93, 230, 200, 30);

                JLabel content2 = new JLabel(
                        "PHP 300 - 1,000");
                content2.setFont(new Font("Montserrat", Font.PLAIN, 12));
                content2.setBounds(103, 230, 300, 100);

                JButton backButton = new JButton("Back");
                backButton.setFocusable(false);
                backButton.setFont(new Font("Montserrat", Font.PLAIN, 11));
                backButton.setBounds(70, 320, 70, 28);
                backButton.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {

                        serviceDetails.dispose();
                    }
                });

                JButton availButton = new JButton("Avail");
                availButton.setFocusable(false);
                availButton.setFont(new Font("Montserrat", Font.PLAIN, 11));
                availButton.setBounds(160, 320, 70, 28);
                availButton.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {

                        JDialog vehicleInfoDialog = new JDialog(serviceDetails, null, true);
                        vehicleInfoDialog.setUndecorated(true);
                        vehicleInfoDialog.setLocation(650, 100);
                        vehicleInfoDialog.setSize(350, 450);
                        vehicleInfoDialog.setLayout(null);
                        vehicleInfoDialog.getContentPane().setBackground(Color.white);

                        ImageIcon xButton = new ImageIcon("xButton.png");
                        JLabel xLabel = new JLabel();
                        xLabel.setIcon(xButton);
                        xLabel.setBounds(305, 5, 40, 40);
                        xLabel.addMouseListener(new MouseAdapter() {

                            @Override
                            public void mouseClicked(MouseEvent e) {

                                vehicleInfoDialog.dispose();
                            }

                            @Override
                            public void mouseEntered(MouseEvent e) {

                                ImageIcon changedIcon = changeIconColor(xButton, new Color(38, 99, 232));
                                xLabel.setIcon(changedIcon);
                            }

                            @Override
                            public void mouseExited(MouseEvent e) {

                                xLabel.setIcon(xButton);
                            }
                        });

                        JLabel titleLabel = new JLabel("Vehicle Information");
                        titleLabel.setFont(new Font("Montserrat", Font.BOLD, 20));
                        titleLabel.setForeground(Color.black);
                        titleLabel.setBounds(90, 55, 300, 50);

                        JTextField makeField = new JTextField();
                        makeField.setFont(new Font("Montserrat", Font.PLAIN, 13));
                        makeField.setCaretColor(Color.black);
                        makeField.setBounds(30, 140, 130, 30);
                        makeField.setText("Make");
                        makeField.addFocusListener(new FocusListener() {

                            @Override
                            public void focusGained(FocusEvent e) {

                                if (makeField.getText().equals("Make")) {

                                    makeField.setText("");
                                }
                            }

                            @Override
                            public void focusLost(FocusEvent e) {

                                if (makeField.getText().isEmpty()) {

                                    makeField.setText("Make");
                                }
                            }
                        });

                        JTextField modelField = new JTextField();
                        modelField.setFont(new Font("Montserrat", Font.PLAIN, 13));
                        modelField.setCaretColor(Color.black);
                        modelField.setBounds(190, 140, 130, 30);
                        modelField.setText("Model");
                        modelField.addFocusListener(new FocusListener() {

                            @Override
                            public void focusGained(FocusEvent e) {

                                if (modelField.getText().equals("Model")) {

                                    modelField.setText("");
                                }
                            }

                            @Override
                            public void focusLost(FocusEvent e) {

                                if (modelField.getText().isEmpty()) {

                                    modelField.setText("Model");
                                }
                            }
                        });

                        JTextField yearField = new JTextField();
                        yearField.setFont(new Font("Montserrat", Font.PLAIN, 13));
                        yearField.setCaretColor(Color.black);
                        yearField.setBounds(30, 200, 130, 30);
                        yearField.setText("Year");
                        yearField.addFocusListener(new FocusListener() {

                            @Override
                            public void focusGained(FocusEvent e) {

                                if (yearField.getText().equals("Year")) {

                                    yearField.setText("");
                                }
                            }

                            @Override
                            public void focusLost(FocusEvent e) {

                                if (yearField.getText().isEmpty()) {

                                    yearField.setText("Year");
                                }
                            }
                        });

                        JTextField mileageField = new JTextField();
                        mileageField.setFont(new Font("Montserrat", Font.PLAIN, 13));
                        mileageField.setCaretColor(Color.black);
                        mileageField.setBounds(190, 200, 130, 30);
                        mileageField.setText("Milieage");
                        mileageField.addFocusListener(new FocusListener() {

                            @Override
                            public void focusGained(FocusEvent e) {

                                if (mileageField.getText().equals("Milieage")) {

                                    mileageField.setText("");
                                }
                            }

                            @Override
                            public void focusLost(FocusEvent e) {

                                if (mileageField.getText().isEmpty()) {

                                    mileageField.setText("Milieage");
                                }
                            }
                        });

                        JButton backButton = new JButton("Back");
                        backButton.setFocusable(false);
                        backButton.setFont(new Font("Montserrat", Font.PLAIN, 11));
                        backButton.setBounds(95, 320, 70, 28);
                        backButton.addActionListener(new ActionListener() {

                            @Override
                            public void actionPerformed(ActionEvent e) {

                                vehicleInfoDialog.dispose();

                            }
                        });

                        JButton nextButton = new JButton("Next");
                        nextButton.setFocusable(false);
                        nextButton.setFont(new Font("Montserrat", Font.PLAIN, 11));
                        nextButton.setBounds(185, 320, 70, 28);
                        nextButton.addActionListener(new ActionListener() {

                            @Override
                            public void actionPerformed(ActionEvent e) {

                                String make = makeField.getText();
                                String model = modelField.getText();
                                String year = yearField.getText();
                                double mileage = Double.parseDouble(mileageField.getText());

                                Vehicle vehicle = new Vehicle(make, model, year, mileage);

                                Service service = new EmissionsSystemService(account, vehicle, 1, 1);

                                JDialog dateAndTimeDialog = new JDialog(vehicleInfoDialog, null, true);
                                dateAndTimeDialog.setUndecorated(true);
                                dateAndTimeDialog.setLocation(220, 40);
                                dateAndTimeDialog.setSize(300, 300);
                                dateAndTimeDialog.setLayout(null);
                                dateAndTimeDialog.getContentPane().setBackground(Color.white);

                                ImageIcon xButton = new ImageIcon("xButton.png");
                                JLabel xLabel = new JLabel();
                                xLabel.setIcon(xButton);
                                xLabel.setBounds(255, 5, 40, 40);
                                xLabel.addMouseListener(new MouseAdapter() {

                                    @Override
                                    public void mouseClicked(MouseEvent e) {

                                        dateAndTimeDialog.dispose();
                                    }

                                    @Override
                                    public void mouseEntered(MouseEvent e) {

                                        ImageIcon changedIcon = changeIconColor(xButton, new Color(38, 99, 232));
                                        xLabel.setIcon(changedIcon);
                                    }

                                    @Override
                                    public void mouseExited(MouseEvent e) {

                                        xLabel.setIcon(xButton);
                                    }
                                });

                                JLabel titleLabel = new JLabel("<html><center>Choose<br>Date and Time</center><html>");
                                titleLabel.setFont(new Font("Montserrat", Font.BOLD, 18));
                                titleLabel.setForeground(Color.black);
                                titleLabel.setBounds(90, 55, 300, 50);

                                JTextField dateField = new JTextField();
                                dateField.setFont(new Font("Montserrat", Font.PLAIN, 13));
                                dateField.setCaretColor(Color.black);
                                dateField.setBounds(85, 130, 140, 30);
                                dateField.setText("Date(YYYY-MM-DD)");
                                dateField.addFocusListener(new FocusListener() {

                                    @Override
                                    public void focusGained(FocusEvent e) {

                                        if (dateField.getText().equals("Date(YYYY-MM-DD)")) {

                                            dateField.setText("");
                                        }
                                    }

                                    @Override
                                    public void focusLost(FocusEvent e) {

                                        if (dateField.getText().isEmpty()) {

                                            dateField.setText("Date(YYYY-MM-DD)");
                                        }
                                    }
                                });

                                JTextField timeField = new JTextField();
                                timeField.setFont(new Font("Montserrat", Font.PLAIN, 13));
                                timeField.setCaretColor(Color.black);
                                timeField.setBounds(85, 180, 140, 30);
                                timeField.setText("Time (HH:MM AM/PM)");
                                timeField.addFocusListener(new FocusListener() {

                                    @Override
                                    public void focusGained(FocusEvent e) {

                                        if (timeField.getText().equals("Time (HH:MM AM/PM)")) {

                                            timeField.setText("");
                                        }
                                    }

                                    @Override
                                    public void focusLost(FocusEvent e) {

                                        if (timeField.getText().isEmpty()) {

                                            timeField.setText("Time (HH:MM AM/PM)");
                                        }
                                    }
                                });

                                JButton backButton = new JButton("Back");
                                backButton.setFocusable(false);
                                backButton.setFont(new Font("Montserrat", Font.PLAIN, 11));
                                backButton.setBounds(75, 230, 70, 28);
                                backButton.addActionListener(new ActionListener() {

                                    @Override
                                    public void actionPerformed(ActionEvent e) {

                                        dateAndTimeDialog.dispose();

                                    }
                                });

                                JButton nextButton = new JButton("Submit");
                                nextButton.setFocusable(false);
                                nextButton.setFont(new Font("Montserrat", Font.PLAIN, 10));
                                nextButton.setBounds(165, 230, 70, 28);
                                nextButton.addActionListener(new ActionListener() {

                                    @Override
                                    public void actionPerformed(ActionEvent e) {

                                        String date = dateField.getText();
                                        String time = timeField.getText();

                                        ScheduledService SS = new ScheduledService(service, date, time);
                                        SSList.add(SS);

                                        serviceDetails.dispose();
                                        userHomeFrame.dispose();
                                        userHomePage(account);
                                    }
                                });

                                dateAndTimeDialog.add(xLabel);
                                dateAndTimeDialog.add(titleLabel);
                                dateAndTimeDialog.add(dateField);
                                dateAndTimeDialog.add(timeField);
                                dateAndTimeDialog.add(backButton);
                                dateAndTimeDialog.add(nextButton);
                                dateAndTimeDialog.setVisible(true);
                            }
                        });

                        vehicleInfoDialog.add(xLabel);
                        vehicleInfoDialog.add(titleLabel);
                        vehicleInfoDialog.add(makeField);
                        vehicleInfoDialog.add(modelField);
                        vehicleInfoDialog.add(yearField);
                        vehicleInfoDialog.add(mileageField);
                        vehicleInfoDialog.add(backButton);
                        vehicleInfoDialog.add(nextButton);

                        vehicleInfoDialog.setVisible(true);
                    }
                });

                serviceDetails.add(xLabel);
                serviceDetails.add(header);
                serviceDetails.add(subHeading);
                serviceDetails.add(content);
                serviceDetails.add(subHeading2);
                serviceDetails.add(content2);
                serviceDetails.add(backButton);
                serviceDetails.add(availButton);
                serviceDetails.setVisible(true);
            }
        });
        emissionSystemServiceLabel.setBounds(55, 650, 175, 130);

        ImageIcon wheelBalancingPic = new ImageIcon("wheelBalancing.jpg");
        JLabel wheelBalancingLabel = new JLabel("Wheel Balancing",
                wheelBalancingPic, JLabel.CENTER);
        wheelBalancingLabel.setVerticalTextPosition(JLabel.BOTTOM);
        wheelBalancingLabel.setHorizontalTextPosition(JLabel.CENTER);
        wheelBalancingLabel.setVerticalAlignment(JLabel.CENTER);
        wheelBalancingLabel.setHorizontalAlignment(JLabel.CENTER);
        wheelBalancingLabel.setFont(new Font("Montserrat", Font.BOLD, 12));
        Border wheelBalancingBorder = BorderFactory.createLineBorder(Color.black, 1);
        wheelBalancingLabel.setBorder(wheelBalancingBorder);
        wheelBalancingLabel.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {

                wheelBalancingLabel.setForeground(new Color(38, 99, 232));
                Border tempBorder = BorderFactory.createLineBorder(new Color(38, 99, 232), 1);
                wheelBalancingLabel.setBorder(tempBorder);
            }

            @Override
            public void mouseExited(MouseEvent e) {

                wheelBalancingLabel.setForeground(Color.black);
                wheelBalancingLabel.setBorder(wheelBalancingBorder);
            }

            @Override
            public void mouseClicked(MouseEvent e) {

                JDialog serviceDetails = new JDialog(userHomeFrame, null, true);
                serviceDetails.setUndecorated(true);
                serviceDetails.setLocation(550, 130);
                serviceDetails.setSize(300, 400);
                serviceDetails.setLayout(null);
                serviceDetails.getContentPane().setBackground(Color.white);

                ImageIcon xButton = new ImageIcon("xButton.png");
                JLabel xLabel = new JLabel();
                xLabel.setIcon(xButton);
                xLabel.setBounds(253, 5, 40, 40);
                xLabel.addMouseListener(new MouseAdapter() {

                    @Override
                    public void mouseClicked(MouseEvent e) {

                        serviceDetails.dispose();
                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {

                        ImageIcon changedIcon = changeIconColor(xButton, new Color(38, 99, 232));
                        xLabel.setIcon(changedIcon);
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {

                        xLabel.setIcon(xButton);
                    }
                });

                JLabel header = new JLabel("Wheel Balancing");
                header.setFont(new Font("Montserrat", Font.BOLD, 20));
                header.setBounds(74, 50, 160, 30);

                JLabel subHeading = new JLabel("Potential Materials Needed:");
                subHeading.setFont(new Font("Montserrat", Font.BOLD, 15));
                subHeading.setBounds(55, 105, 200, 30);

                JLabel content = new JLabel(
                        "No Materials needed");
                content.setFont(new Font("Montserrat", Font.PLAIN, 12));
                content.setBounds(95, 110, 300, 100);

                JLabel subHeading2 = new JLabel("Service Charge:");
                subHeading2.setFont(new Font("Montserrat", Font.BOLD, 15));
                subHeading2.setBounds(93, 190, 200, 30);

                JLabel content2 = new JLabel(
                        "PHP 200 - 500");
                content2.setFont(new Font("Montserrat", Font.PLAIN, 12));
                content2.setBounds(110, 185, 300, 100);

                JButton backButton = new JButton("Back");
                backButton.setFocusable(false);
                backButton.setFont(new Font("Montserrat", Font.PLAIN, 11));
                backButton.setBounds(70, 270, 70, 28);
                backButton.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {

                        serviceDetails.dispose();
                    }
                });

                JButton availButton = new JButton("Avail");
                availButton.setFocusable(false);
                availButton.setFont(new Font("Montserrat", Font.PLAIN, 11));
                availButton.setBounds(160, 270, 70, 28);
                availButton.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {

                        JDialog vehicleInfoDialog = new JDialog(serviceDetails, null, true);
                        vehicleInfoDialog.setUndecorated(true);
                        vehicleInfoDialog.setLocation(650, 100);
                        vehicleInfoDialog.setSize(350, 450);
                        vehicleInfoDialog.setLayout(null);
                        vehicleInfoDialog.getContentPane().setBackground(Color.white);

                        ImageIcon xButton = new ImageIcon("xButton.png");
                        JLabel xLabel = new JLabel();
                        xLabel.setIcon(xButton);
                        xLabel.setBounds(305, 5, 40, 40);
                        xLabel.addMouseListener(new MouseAdapter() {

                            @Override
                            public void mouseClicked(MouseEvent e) {

                                vehicleInfoDialog.dispose();
                            }

                            @Override
                            public void mouseEntered(MouseEvent e) {

                                ImageIcon changedIcon = changeIconColor(xButton, new Color(38, 99, 232));
                                xLabel.setIcon(changedIcon);
                            }

                            @Override
                            public void mouseExited(MouseEvent e) {

                                xLabel.setIcon(xButton);
                            }
                        });

                        JLabel titleLabel = new JLabel("Vehicle Information");
                        titleLabel.setFont(new Font("Montserrat", Font.BOLD, 20));
                        titleLabel.setForeground(Color.black);
                        titleLabel.setBounds(90, 55, 300, 50);

                        JTextField makeField = new JTextField();
                        makeField.setFont(new Font("Montserrat", Font.PLAIN, 13));
                        makeField.setCaretColor(Color.black);
                        makeField.setBounds(30, 140, 130, 30);
                        makeField.setText("Make");
                        makeField.addFocusListener(new FocusListener() {

                            @Override
                            public void focusGained(FocusEvent e) {

                                if (makeField.getText().equals("Make")) {

                                    makeField.setText("");
                                }
                            }

                            @Override
                            public void focusLost(FocusEvent e) {

                                if (makeField.getText().isEmpty()) {

                                    makeField.setText("Make");
                                }
                            }
                        });

                        JTextField modelField = new JTextField();
                        modelField.setFont(new Font("Montserrat", Font.PLAIN, 13));
                        modelField.setCaretColor(Color.black);
                        modelField.setBounds(190, 140, 130, 30);
                        modelField.setText("Model");
                        modelField.addFocusListener(new FocusListener() {

                            @Override
                            public void focusGained(FocusEvent e) {

                                if (modelField.getText().equals("Model")) {

                                    modelField.setText("");
                                }
                            }

                            @Override
                            public void focusLost(FocusEvent e) {

                                if (modelField.getText().isEmpty()) {

                                    modelField.setText("Model");
                                }
                            }
                        });

                        JTextField yearField = new JTextField();
                        yearField.setFont(new Font("Montserrat", Font.PLAIN, 13));
                        yearField.setCaretColor(Color.black);
                        yearField.setBounds(30, 200, 130, 30);
                        yearField.setText("Year");
                        yearField.addFocusListener(new FocusListener() {

                            @Override
                            public void focusGained(FocusEvent e) {

                                if (yearField.getText().equals("Year")) {

                                    yearField.setText("");
                                }
                            }

                            @Override
                            public void focusLost(FocusEvent e) {

                                if (yearField.getText().isEmpty()) {

                                    yearField.setText("Year");
                                }
                            }
                        });

                        JTextField mileageField = new JTextField();
                        mileageField.setFont(new Font("Montserrat", Font.PLAIN, 13));
                        mileageField.setCaretColor(Color.black);
                        mileageField.setBounds(190, 200, 130, 30);
                        mileageField.setText("Milieage");
                        mileageField.addFocusListener(new FocusListener() {

                            @Override
                            public void focusGained(FocusEvent e) {

                                if (mileageField.getText().equals("Milieage")) {

                                    mileageField.setText("");
                                }
                            }

                            @Override
                            public void focusLost(FocusEvent e) {

                                if (mileageField.getText().isEmpty()) {

                                    mileageField.setText("Milieage");
                                }
                            }
                        });

                        JButton backButton = new JButton("Back");
                        backButton.setFocusable(false);
                        backButton.setFont(new Font("Montserrat", Font.PLAIN, 11));
                        backButton.setBounds(95, 320, 70, 28);
                        backButton.addActionListener(new ActionListener() {

                            @Override
                            public void actionPerformed(ActionEvent e) {

                                vehicleInfoDialog.dispose();

                            }
                        });

                        JButton nextButton = new JButton("Next");
                        nextButton.setFocusable(false);
                        nextButton.setFont(new Font("Montserrat", Font.PLAIN, 11));
                        nextButton.setBounds(185, 320, 70, 28);
                        nextButton.addActionListener(new ActionListener() {

                            @Override
                            public void actionPerformed(ActionEvent e) {

                                String make = makeField.getText();
                                String model = modelField.getText();
                                String year = yearField.getText();
                                double mileage = Double.parseDouble(mileageField.getText());

                                Vehicle vehicle = new Vehicle(make, model, year, mileage);

                                Service service = new WheelBalancingService(account, vehicle);

                                JDialog dateAndTimeDialog = new JDialog(vehicleInfoDialog, null, true);
                                dateAndTimeDialog.setUndecorated(true);
                                dateAndTimeDialog.setLocation(220, 40);
                                dateAndTimeDialog.setSize(300, 300);
                                dateAndTimeDialog.setLayout(null);
                                dateAndTimeDialog.getContentPane().setBackground(Color.white);

                                ImageIcon xButton = new ImageIcon("xButton.png");
                                JLabel xLabel = new JLabel();
                                xLabel.setIcon(xButton);
                                xLabel.setBounds(255, 5, 40, 40);
                                xLabel.addMouseListener(new MouseAdapter() {

                                    @Override
                                    public void mouseClicked(MouseEvent e) {

                                        dateAndTimeDialog.dispose();
                                    }

                                    @Override
                                    public void mouseEntered(MouseEvent e) {

                                        ImageIcon changedIcon = changeIconColor(xButton, new Color(38, 99, 232));
                                        xLabel.setIcon(changedIcon);
                                    }

                                    @Override
                                    public void mouseExited(MouseEvent e) {

                                        xLabel.setIcon(xButton);
                                    }
                                });

                                JLabel titleLabel = new JLabel("<html><center>Choose<br>Date and Time</center><html>");
                                titleLabel.setFont(new Font("Montserrat", Font.BOLD, 18));
                                titleLabel.setForeground(Color.black);
                                titleLabel.setBounds(90, 55, 300, 50);

                                JTextField dateField = new JTextField();
                                dateField.setFont(new Font("Montserrat", Font.PLAIN, 13));
                                dateField.setCaretColor(Color.black);
                                dateField.setBounds(85, 130, 140, 30);
                                dateField.setText("Date(YYYY-MM-DD)");
                                dateField.addFocusListener(new FocusListener() {

                                    @Override
                                    public void focusGained(FocusEvent e) {

                                        if (dateField.getText().equals("Date(YYYY-MM-DD)")) {

                                            dateField.setText("");
                                        }
                                    }

                                    @Override
                                    public void focusLost(FocusEvent e) {

                                        if (dateField.getText().isEmpty()) {

                                            dateField.setText("Date(YYYY-MM-DD)");
                                        }
                                    }
                                });

                                JTextField timeField = new JTextField();
                                timeField.setFont(new Font("Montserrat", Font.PLAIN, 13));
                                timeField.setCaretColor(Color.black);
                                timeField.setBounds(85, 180, 140, 30);
                                timeField.setText("Time (HH:MM AM/PM)");
                                timeField.addFocusListener(new FocusListener() {

                                    @Override
                                    public void focusGained(FocusEvent e) {

                                        if (timeField.getText().equals("Time (HH:MM AM/PM)")) {

                                            timeField.setText("");
                                        }
                                    }

                                    @Override
                                    public void focusLost(FocusEvent e) {

                                        if (timeField.getText().isEmpty()) {

                                            timeField.setText("Time (HH:MM AM/PM)");
                                        }
                                    }
                                });

                                JButton backButton = new JButton("Back");
                                backButton.setFocusable(false);
                                backButton.setFont(new Font("Montserrat", Font.PLAIN, 11));
                                backButton.setBounds(75, 230, 70, 28);
                                backButton.addActionListener(new ActionListener() {

                                    @Override
                                    public void actionPerformed(ActionEvent e) {

                                        dateAndTimeDialog.dispose();

                                    }
                                });

                                JButton nextButton = new JButton("Submit");
                                nextButton.setFocusable(false);
                                nextButton.setFont(new Font("Montserrat", Font.PLAIN, 10));
                                nextButton.setBounds(165, 230, 70, 28);
                                nextButton.addActionListener(new ActionListener() {

                                    @Override
                                    public void actionPerformed(ActionEvent e) {

                                        String date = dateField.getText();
                                        String time = timeField.getText();

                                        ScheduledService SS = new ScheduledService(service, date, time);
                                        SSList.add(SS);

                                        serviceDetails.dispose();
                                        userHomeFrame.dispose();
                                        userHomePage(account);
                                    }
                                });

                                dateAndTimeDialog.add(xLabel);
                                dateAndTimeDialog.add(titleLabel);
                                dateAndTimeDialog.add(dateField);
                                dateAndTimeDialog.add(timeField);
                                dateAndTimeDialog.add(backButton);
                                dateAndTimeDialog.add(nextButton);
                                dateAndTimeDialog.setVisible(true);
                            }
                        });

                        vehicleInfoDialog.add(xLabel);
                        vehicleInfoDialog.add(titleLabel);
                        vehicleInfoDialog.add(makeField);
                        vehicleInfoDialog.add(modelField);
                        vehicleInfoDialog.add(yearField);
                        vehicleInfoDialog.add(mileageField);
                        vehicleInfoDialog.add(backButton);
                        vehicleInfoDialog.add(nextButton);

                        vehicleInfoDialog.setVisible(true);
                    }
                });

                serviceDetails.add(xLabel);
                serviceDetails.add(header);
                serviceDetails.add(subHeading);
                serviceDetails.add(content);
                serviceDetails.add(subHeading2);
                serviceDetails.add(content2);
                serviceDetails.add(backButton);
                serviceDetails.add(availButton);
                serviceDetails.setVisible(true);
            }
        });
        wheelBalancingLabel.setBounds(245, 650, 175, 130);

        ImageIcon fuelSystemServicePic = new ImageIcon("fuelSystemService.jpg");
        JLabel fuelSystemServiceLabel = new JLabel("Fuel System Service",
                fuelSystemServicePic, JLabel.CENTER);
        fuelSystemServiceLabel.setVerticalTextPosition(JLabel.BOTTOM);
        fuelSystemServiceLabel.setHorizontalTextPosition(JLabel.CENTER);
        fuelSystemServiceLabel.setVerticalAlignment(JLabel.CENTER);
        fuelSystemServiceLabel.setHorizontalAlignment(JLabel.CENTER);
        fuelSystemServiceLabel.setFont(new Font("Montserrat", Font.BOLD, 12));
        Border fuelSystemServiceBorder = BorderFactory.createLineBorder(Color.black, 1);
        fuelSystemServiceLabel.setBorder(fuelSystemServiceBorder);
        fuelSystemServiceLabel.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {

                fuelSystemServiceLabel.setForeground(new Color(38, 99, 232));
                Border tempBorder = BorderFactory.createLineBorder(new Color(38, 99, 232), 1);
                fuelSystemServiceLabel.setBorder(tempBorder);
            }

            @Override
            public void mouseExited(MouseEvent e) {

                fuelSystemServiceLabel.setForeground(Color.black);
                fuelSystemServiceLabel.setBorder(fuelSystemServiceBorder);
            }

            @Override
            public void mouseClicked(MouseEvent e) {

                JDialog serviceDetails = new JDialog(userHomeFrame, null, true);
                serviceDetails.setUndecorated(true);
                serviceDetails.setLocation(550, 130);
                serviceDetails.setSize(300, 400);
                serviceDetails.setLayout(null);
                serviceDetails.getContentPane().setBackground(Color.white);

                ImageIcon xButton = new ImageIcon("xButton.png");
                JLabel xLabel = new JLabel();
                xLabel.setIcon(xButton);
                xLabel.setBounds(253, 5, 40, 40);
                xLabel.addMouseListener(new MouseAdapter() {

                    @Override
                    public void mouseClicked(MouseEvent e) {

                        serviceDetails.dispose();
                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {

                        ImageIcon changedIcon = changeIconColor(xButton, new Color(38, 99, 232));
                        xLabel.setIcon(changedIcon);
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {

                        xLabel.setIcon(xButton);
                    }
                });

                JLabel header = new JLabel("Fuel System Service");
                header.setFont(new Font("Montserrat", Font.BOLD, 18));
                header.setBounds(60, 50, 200, 30);

                JLabel subHeading = new JLabel("Potential Materials Needed:");
                subHeading.setFont(new Font("Montserrat", Font.BOLD, 15));
                subHeading.setBounds(55, 105, 200, 30);

                JLabel content = new JLabel(
                        "<html><center>Fuel Injector Cleaner : PHP 500 - 1,000<br><br>Fuel Filter : PHP 500 - 1500</center><html>");
                content.setFont(new Font("Montserrat", Font.PLAIN, 12));
                content.setBounds(47, 130, 300, 100);

                JLabel subHeading2 = new JLabel("Service Charge:");
                subHeading2.setFont(new Font("Montserrat", Font.BOLD, 15));
                subHeading2.setBounds(93, 230, 200, 30);

                JLabel content2 = new JLabel(
                        "PHP 300 - 1,000");
                content2.setFont(new Font("Montserrat", Font.PLAIN, 12));
                content2.setBounds(103, 230, 300, 100);

                JButton backButton = new JButton("Back");
                backButton.setFocusable(false);
                backButton.setFont(new Font("Montserrat", Font.PLAIN, 11));
                backButton.setBounds(70, 320, 70, 28);
                backButton.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {

                        serviceDetails.dispose();
                    }
                });

                JButton availButton = new JButton("Avail");
                availButton.setFocusable(false);
                availButton.setFont(new Font("Montserrat", Font.PLAIN, 11));
                availButton.setBounds(160, 320, 70, 28);
                availButton.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {

                        JDialog vehicleInfoDialog = new JDialog(serviceDetails, null, true);
                        vehicleInfoDialog.setUndecorated(true);
                        vehicleInfoDialog.setLocation(650, 100);
                        vehicleInfoDialog.setSize(350, 450);
                        vehicleInfoDialog.setLayout(null);
                        vehicleInfoDialog.getContentPane().setBackground(Color.white);

                        ImageIcon xButton = new ImageIcon("xButton.png");
                        JLabel xLabel = new JLabel();
                        xLabel.setIcon(xButton);
                        xLabel.setBounds(305, 5, 40, 40);
                        xLabel.addMouseListener(new MouseAdapter() {

                            @Override
                            public void mouseClicked(MouseEvent e) {

                                vehicleInfoDialog.dispose();
                            }

                            @Override
                            public void mouseEntered(MouseEvent e) {

                                ImageIcon changedIcon = changeIconColor(xButton, new Color(38, 99, 232));
                                xLabel.setIcon(changedIcon);
                            }

                            @Override
                            public void mouseExited(MouseEvent e) {

                                xLabel.setIcon(xButton);
                            }
                        });

                        JLabel titleLabel = new JLabel("Vehicle Information");
                        titleLabel.setFont(new Font("Montserrat", Font.BOLD, 20));
                        titleLabel.setForeground(Color.black);
                        titleLabel.setBounds(90, 55, 300, 50);

                        JTextField makeField = new JTextField();
                        makeField.setFont(new Font("Montserrat", Font.PLAIN, 13));
                        makeField.setCaretColor(Color.black);
                        makeField.setBounds(30, 140, 130, 30);
                        makeField.setText("Make");
                        makeField.addFocusListener(new FocusListener() {

                            @Override
                            public void focusGained(FocusEvent e) {

                                if (makeField.getText().equals("Make")) {

                                    makeField.setText("");
                                }
                            }

                            @Override
                            public void focusLost(FocusEvent e) {

                                if (makeField.getText().isEmpty()) {

                                    makeField.setText("Make");
                                }
                            }
                        });

                        JTextField modelField = new JTextField();
                        modelField.setFont(new Font("Montserrat", Font.PLAIN, 13));
                        modelField.setCaretColor(Color.black);
                        modelField.setBounds(190, 140, 130, 30);
                        modelField.setText("Model");
                        modelField.addFocusListener(new FocusListener() {

                            @Override
                            public void focusGained(FocusEvent e) {

                                if (modelField.getText().equals("Model")) {

                                    modelField.setText("");
                                }
                            }

                            @Override
                            public void focusLost(FocusEvent e) {

                                if (modelField.getText().isEmpty()) {

                                    modelField.setText("Model");
                                }
                            }
                        });

                        JTextField yearField = new JTextField();
                        yearField.setFont(new Font("Montserrat", Font.PLAIN, 13));
                        yearField.setCaretColor(Color.black);
                        yearField.setBounds(30, 200, 130, 30);
                        yearField.setText("Year");
                        yearField.addFocusListener(new FocusListener() {

                            @Override
                            public void focusGained(FocusEvent e) {

                                if (yearField.getText().equals("Year")) {

                                    yearField.setText("");
                                }
                            }

                            @Override
                            public void focusLost(FocusEvent e) {

                                if (yearField.getText().isEmpty()) {

                                    yearField.setText("Year");
                                }
                            }
                        });

                        JTextField mileageField = new JTextField();
                        mileageField.setFont(new Font("Montserrat", Font.PLAIN, 13));
                        mileageField.setCaretColor(Color.black);
                        mileageField.setBounds(190, 200, 130, 30);
                        mileageField.setText("Milieage");
                        mileageField.addFocusListener(new FocusListener() {

                            @Override
                            public void focusGained(FocusEvent e) {

                                if (mileageField.getText().equals("Milieage")) {

                                    mileageField.setText("");
                                }
                            }

                            @Override
                            public void focusLost(FocusEvent e) {

                                if (mileageField.getText().isEmpty()) {

                                    mileageField.setText("Milieage");
                                }
                            }
                        });

                        JButton backButton = new JButton("Back");
                        backButton.setFocusable(false);
                        backButton.setFont(new Font("Montserrat", Font.PLAIN, 11));
                        backButton.setBounds(95, 320, 70, 28);
                        backButton.addActionListener(new ActionListener() {

                            @Override
                            public void actionPerformed(ActionEvent e) {

                                vehicleInfoDialog.dispose();

                            }
                        });

                        JButton nextButton = new JButton("Next");
                        nextButton.setFocusable(false);
                        nextButton.setFont(new Font("Montserrat", Font.PLAIN, 11));
                        nextButton.setBounds(185, 320, 70, 28);
                        nextButton.addActionListener(new ActionListener() {

                            @Override
                            public void actionPerformed(ActionEvent e) {

                                String make = makeField.getText();
                                String model = modelField.getText();
                                String year = yearField.getText();
                                double mileage = Double.parseDouble(mileageField.getText());

                                Vehicle vehicle = new Vehicle(make, model, year, mileage);

                                Service service = new FuelSystemService(account, vehicle, 1, 1);

                                JDialog dateAndTimeDialog = new JDialog(vehicleInfoDialog, null, true);
                                dateAndTimeDialog.setUndecorated(true);
                                dateAndTimeDialog.setLocation(220, 40);
                                dateAndTimeDialog.setSize(300, 300);
                                dateAndTimeDialog.setLayout(null);
                                dateAndTimeDialog.getContentPane().setBackground(Color.white);

                                ImageIcon xButton = new ImageIcon("xButton.png");
                                JLabel xLabel = new JLabel();
                                xLabel.setIcon(xButton);
                                xLabel.setBounds(255, 5, 40, 40);
                                xLabel.addMouseListener(new MouseAdapter() {

                                    @Override
                                    public void mouseClicked(MouseEvent e) {

                                        dateAndTimeDialog.dispose();
                                    }

                                    @Override
                                    public void mouseEntered(MouseEvent e) {

                                        ImageIcon changedIcon = changeIconColor(xButton, new Color(38, 99, 232));
                                        xLabel.setIcon(changedIcon);
                                    }

                                    @Override
                                    public void mouseExited(MouseEvent e) {

                                        xLabel.setIcon(xButton);
                                    }
                                });

                                JLabel titleLabel = new JLabel("<html><center>Choose<br>Date and Time</center><html>");
                                titleLabel.setFont(new Font("Montserrat", Font.BOLD, 18));
                                titleLabel.setForeground(Color.black);
                                titleLabel.setBounds(90, 55, 300, 50);

                                JTextField dateField = new JTextField();
                                dateField.setFont(new Font("Montserrat", Font.PLAIN, 13));
                                dateField.setCaretColor(Color.black);
                                dateField.setBounds(85, 130, 140, 30);
                                dateField.setText("Date(YYYY-MM-DD)");
                                dateField.addFocusListener(new FocusListener() {

                                    @Override
                                    public void focusGained(FocusEvent e) {

                                        if (dateField.getText().equals("Date(YYYY-MM-DD)")) {

                                            dateField.setText("");
                                        }
                                    }

                                    @Override
                                    public void focusLost(FocusEvent e) {

                                        if (dateField.getText().isEmpty()) {

                                            dateField.setText("Date(YYYY-MM-DD)");
                                        }
                                    }
                                });

                                JTextField timeField = new JTextField();
                                timeField.setFont(new Font("Montserrat", Font.PLAIN, 13));
                                timeField.setCaretColor(Color.black);
                                timeField.setBounds(85, 180, 140, 30);
                                timeField.setText("Time (HH:MM AM/PM)");
                                timeField.addFocusListener(new FocusListener() {

                                    @Override
                                    public void focusGained(FocusEvent e) {

                                        if (timeField.getText().equals("Time (HH:MM AM/PM)")) {

                                            timeField.setText("");
                                        }
                                    }

                                    @Override
                                    public void focusLost(FocusEvent e) {

                                        if (timeField.getText().isEmpty()) {

                                            timeField.setText("Time (HH:MM AM/PM)");
                                        }
                                    }
                                });

                                JButton backButton = new JButton("Back");
                                backButton.setFocusable(false);
                                backButton.setFont(new Font("Montserrat", Font.PLAIN, 11));
                                backButton.setBounds(75, 230, 70, 28);
                                backButton.addActionListener(new ActionListener() {

                                    @Override
                                    public void actionPerformed(ActionEvent e) {

                                        dateAndTimeDialog.dispose();

                                    }
                                });

                                JButton nextButton = new JButton("Submit");
                                nextButton.setFocusable(false);
                                nextButton.setFont(new Font("Montserrat", Font.PLAIN, 10));
                                nextButton.setBounds(165, 230, 70, 28);
                                nextButton.addActionListener(new ActionListener() {

                                    @Override
                                    public void actionPerformed(ActionEvent e) {

                                        String date = dateField.getText();
                                        String time = timeField.getText();

                                        ScheduledService SS = new ScheduledService(service, date, time);
                                        SSList.add(SS);

                                        serviceDetails.dispose();
                                        userHomeFrame.dispose();
                                        userHomePage(account);
                                    }
                                });

                                dateAndTimeDialog.add(xLabel);
                                dateAndTimeDialog.add(titleLabel);
                                dateAndTimeDialog.add(dateField);
                                dateAndTimeDialog.add(timeField);
                                dateAndTimeDialog.add(backButton);
                                dateAndTimeDialog.add(nextButton);
                                dateAndTimeDialog.setVisible(true);
                            }
                        });

                        vehicleInfoDialog.add(xLabel);
                        vehicleInfoDialog.add(titleLabel);
                        vehicleInfoDialog.add(makeField);
                        vehicleInfoDialog.add(modelField);
                        vehicleInfoDialog.add(yearField);
                        vehicleInfoDialog.add(mileageField);
                        vehicleInfoDialog.add(backButton);
                        vehicleInfoDialog.add(nextButton);

                        vehicleInfoDialog.setVisible(true);
                    }
                });

                serviceDetails.add(xLabel);
                serviceDetails.add(header);
                serviceDetails.add(subHeading);
                serviceDetails.add(content);
                serviceDetails.add(subHeading2);
                serviceDetails.add(content2);
                serviceDetails.add(backButton);
                serviceDetails.add(availButton);
                serviceDetails.setVisible(true);
            }
        });
        fuelSystemServiceLabel.setBounds(435, 650, 175, 130);

        JLabel diagnoseLabel = new JLabel("Don't know the problem of your vehicle?");
        diagnoseLabel.setFont(new Font("Montserrat", Font.BOLD, 13));
        diagnoseLabel.setForeground(Color.black);
        diagnoseLabel.setBounds(120, 800, 300, 30);

        JLabel carDiagnoseLabel = new JLabel("Request a Car Diagnose");
        carDiagnoseLabel.setFont(new Font("Montserrat", Font.BOLD, 13));
        carDiagnoseLabel.setForeground(new Color(38, 99, 232));
        carDiagnoseLabel.setBounds(382, 800, 200, 30);
        carDiagnoseLabel.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {

                carDiagnoseLabel.setForeground(Color.DARK_GRAY);
            }

            @Override
            public void mouseExited(MouseEvent e) {

                carDiagnoseLabel.setForeground(new Color(38, 99, 232));
            }
        });

        schedulePanel.add(scheduleTitleLabel);
        schedulePanel.add(oilChangeLabel);
        schedulePanel.add(tireRotationLabel);
        schedulePanel.add(wheelAlignmentLabel);
        schedulePanel.add(brakeServiceLabel);
        schedulePanel.add(batteryReplacementLabel);
        schedulePanel.add(fluidChecksAndChangesLabel);
        schedulePanel.add(airFilterReplacementLabel);
        schedulePanel.add(sparkPlugReplacementLabel);
        schedulePanel.add(timingBeltReplacementLabel);
        schedulePanel.add(suspensionAndSteeringServiceLabel);
        schedulePanel.add(exhaustSystemInspectionLabel);
        schedulePanel.add(coolingSystemServiceLabel);
        schedulePanel.add(emissionSystemServiceLabel);
        schedulePanel.add(wheelBalancingLabel);
        schedulePanel.add(fuelSystemServiceLabel);
        schedulePanel.add(diagnoseLabel);
        schedulePanel.add(carDiagnoseLabel);

        JScrollPane scheduleScroller = new JScrollPane(schedulePanel);

        JPanel payPanel = new JPanel();
        payPanel.setLayout(new BoxLayout(payPanel, BoxLayout.Y_AXIS));
        payPanel.setPreferredSize(new Dimension(700, SSList.getCount() * 300));

        for (ScheduledService SS : SSList.getList()) {

            if (SS != null) {

                JLabel payLabel = new JLabel();
                payLabel.setPreferredSize(new Dimension(700, 300));
                payLabel.setText("<html>ServiceID: " + SS.getService().getServiceID() +
                        "<br>Service Name: " + SS.getService().getServiceName() +
                        "<br>Total Payment: " + SS.getService().totalCost());
                payLabel.setFont(new Font("Montserrat", Font.BOLD, 14));
                payLabel.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {

                        double payment = Double.parseDouble(JOptionPane.showInputDialog("Enter Payment: "));

                        if (payment < SS.getService().getTotalPayment()) {

                            JOptionPane.showMessageDialog(payPanel, "The cash inputted is not sufficient", "ERROR",
                                    JOptionPane.ERROR_MESSAGE);
                        }

                        else {

                            JOptionPane.showMessageDialog(payPanel, "Payment Successful!", "SUCCESS",
                                    JOptionPane.INFORMATION_MESSAGE);
                            SSList.delete(SS);

                            JOptionPane.showInputDialog("Please enter Service Feedback :)!");
                            JOptionPane.showMessageDialog(payPanel, "Thank you very much!", "SUCCESS",
                                    JOptionPane.INFORMATION_MESSAGE);

                            userHomeFrame.dispose();
                            userHomePage(account);
                        }
                    }
                });

                payPanel.add(payLabel);
            }
        }

        ImageIcon payIcon = new ImageIcon("pay.png");

        JPanel profilePanel = new JPanel();
        profilePanel.setLayout(null);

        ImageIcon profile2Icon = new ImageIcon("profile2.png");
        JLabel profile2Label = new JLabel(profile2Icon);
        profile2Label.setBounds(430, 140, 150, 150);

        JLabel profileDetailsLabel = new JLabel();
        profileDetailsLabel.setText("<html><table>" +
                "<tr><td><b>Name:</b></td><td>" + account.person.getName() + "</td></tr><tr>" +
                "<tr><td><b>Address:</b></td><td>" + account.person.getAddress() + "</td></tr></tr><tr>" +
                "<tr><td><b>Date of Birth:</b></td><td>" + account.person.getDOB() + "</td></tr></tr><tr>" +
                "<tr><td><b>Gender:</b></td><td>" + account.person.getGender() + "</td></tr></tr><tr>" +
                "<tr><td><b>Contact Number:</b></td><td>" + account.person.getContactNumber() + "</td></tr></tr><tr>" +
                "<tr><td><b>Username:</b></td><td>" + account.getUsername() + "</td></tr></tr><tr>" +
                "<tr><td><b>Password:</b></td><td>" + account.getPassword() + "</td></tr></tr><tr>" +
                "<tr><td><b>Name:</b></td><td>" + account.person.getName() + "</td></tr><tr>" +
                "</table></html>");
        profileDetailsLabel.setFont(new Font("Montserrat", Font.BOLD, 20));
        profileDetailsLabel.setBounds(20, 00, 300, 500);

        JLabel updateLabel1 = new JLabel("Update");
        updateLabel1.setFont(new Font("Montserrat", Font.PLAIN, 12));
        updateLabel1.setForeground(new Color(38, 99, 232));
        updateLabel1.setBounds(32, 24, 50, 50);
        updateLabel1.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {

                updateLabel1.setForeground(Color.DARK_GRAY);
            }

            @Override
            public void mouseExited(MouseEvent e) {

                updateLabel1.setForeground(new Color(38, 99, 232));
            }

            @Override
            public void mouseClicked(MouseEvent e) {

                String tempHolder = JOptionPane.showInputDialog(userHomeFrame, "New Name: ", "UPDATE",
                        JOptionPane.QUESTION_MESSAGE);

                if (tempHolder == null) {

                    JOptionPane.showMessageDialog(userHomeFrame, "You cancelled the Operation.", "OKAY",
                            JOptionPane.INFORMATION_MESSAGE);
                }

                else if (tempHolder.matches("\\d+")) {

                    JOptionPane.showMessageDialog(userHomeFrame, "Numbers is not allowed in the Name Field!", "ERROR",
                            JOptionPane.ERROR_MESSAGE);
                }

                else {

                    account.person.setName(tempHolder.replace(",", " "));
                    JOptionPane.showMessageDialog(userHomeFrame, "<html>Name was update to " + tempHolder + "</html>",
                            "SUCCESS",
                            JOptionPane.INFORMATION_MESSAGE);

                    userHomeFrame.dispose();
                    userHomePage(account);
                }
            }
        });

        JLabel updateLabel2 = new JLabel("Update");
        updateLabel2.setFont(new Font("Montserrat", Font.PLAIN, 12));
        updateLabel2.setForeground(new Color(38, 99, 232));
        updateLabel2.setBounds(43, 87, 50, 50);
        updateLabel2.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {

                updateLabel2.setForeground(Color.DARK_GRAY);
            }

            @Override
            public void mouseExited(MouseEvent e) {

                updateLabel2.setForeground(new Color(38, 99, 232));
            }

            @Override
            public void mouseClicked(MouseEvent e) {

                String tempHolder = JOptionPane.showInputDialog(userHomeFrame, "New Address: ", "UPDATE",
                        JOptionPane.QUESTION_MESSAGE);

                if (tempHolder == null) {

                    JOptionPane.showMessageDialog(userHomeFrame, "You cancelled the Operation.", "OKAY",
                            JOptionPane.INFORMATION_MESSAGE);
                }

                else {

                    account.person.setAddress(tempHolder.replace(",", " "));
                    JOptionPane.showMessageDialog(userHomeFrame,
                            "<html>Address was update to " + tempHolder + "</html>",
                            "SUCCESS",
                            JOptionPane.INFORMATION_MESSAGE);

                    userHomeFrame.dispose();
                    userHomePage(account);
                }
            }
        });

        JLabel updateLabel3 = new JLabel("Update");
        updateLabel3.setFont(new Font("Montserrat", Font.PLAIN, 12));
        updateLabel3.setForeground(new Color(38, 99, 232));
        updateLabel3.setBounds(58, 150, 50, 50);
        updateLabel3.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {

                updateLabel3.setForeground(Color.DARK_GRAY);
            }

            @Override
            public void mouseExited(MouseEvent e) {

                updateLabel3.setForeground(new Color(38, 99, 232));
            }

            @Override
            public void mouseClicked(MouseEvent e) {

                String tempHolder = JOptionPane.showInputDialog(userHomeFrame, "New Date of Birth: ", "UPDATE",
                        JOptionPane.QUESTION_MESSAGE);

                if (tempHolder == null) {

                    JOptionPane.showMessageDialog(userHomeFrame, "You cancelled the Operation.", "OKAY",
                            JOptionPane.INFORMATION_MESSAGE);
                }

                else if (tempHolder.matches("[a-zA-Z]+")) {

                    JOptionPane.showMessageDialog(userHomeFrame, "Please use Numbers in the Date of Birth Field!",
                            "ERROR", JOptionPane.ERROR_MESSAGE);
                }

                else {

                    account.person.setDOB(tempHolder.replace(",", " "));
                    JOptionPane.showMessageDialog(userHomeFrame,
                            "<html>Date of Birth was update to " + tempHolder + "</html>",
                            "SUCCESS",
                            JOptionPane.INFORMATION_MESSAGE);

                    userHomeFrame.dispose();
                    userHomePage(account);
                }
            }
        });

        JLabel updateLabel4 = new JLabel("Update");
        updateLabel4.setFont(new Font("Montserrat", Font.PLAIN, 12));
        updateLabel4.setForeground(new Color(38, 99, 232));
        updateLabel4.setBounds(40, 210, 50, 50);
        updateLabel4.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {

                updateLabel4.setForeground(Color.DARK_GRAY);
            }

            @Override
            public void mouseExited(MouseEvent e) {

                updateLabel4.setForeground(new Color(38, 99, 232));
            }

            @Override
            public void mouseClicked(MouseEvent e) {

                String tempHolder = JOptionPane.showInputDialog(userHomeFrame, "New Gender: ", "UPDATE",
                        JOptionPane.QUESTION_MESSAGE);

                if (tempHolder == null) {

                    JOptionPane.showMessageDialog(userHomeFrame, "You cancelled the Operation.", "OKAY",
                            JOptionPane.INFORMATION_MESSAGE);
                }

                else if (tempHolder.matches("\\d+")) {

                    JOptionPane.showMessageDialog(userHomeFrame, "Numbers is not allowed in the Gender Field!", "ERROR",
                            JOptionPane.ERROR_MESSAGE);
                }

                else {

                    account.person.setGender(tempHolder.replace(",", " "));
                    JOptionPane.showMessageDialog(userHomeFrame,
                            "<html>Gender was update to " + tempHolder + "</html>",
                            "SUCCESS",
                            JOptionPane.INFORMATION_MESSAGE);

                    userHomeFrame.dispose();
                    userHomePage(account);
                }
            }
        });

        JLabel updateLabel5 = new JLabel("Update");
        updateLabel5.setFont(new Font("Montserrat", Font.PLAIN, 12));
        updateLabel5.setForeground(new Color(38, 99, 232));
        updateLabel5.setBounds(78, 273, 50, 50);
        updateLabel5.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {

                updateLabel5.setForeground(Color.DARK_GRAY);
            }

            @Override
            public void mouseExited(MouseEvent e) {

                updateLabel5.setForeground(new Color(38, 99, 232));
            }

            @Override
            public void mouseClicked(MouseEvent e) {

                String tempHolder = JOptionPane.showInputDialog(userHomeFrame, "New Contact Number: ", "UPDATE",
                        JOptionPane.QUESTION_MESSAGE);

                if (tempHolder == null) {

                    JOptionPane.showMessageDialog(userHomeFrame, "You cancelled the Operation.", "OKAY",
                            JOptionPane.INFORMATION_MESSAGE);
                }

                else if (!tempHolder.matches("\\d+")) {

                    JOptionPane.showMessageDialog(userHomeFrame,
                            "Letters or other symbols are not allowed in the Contact Number Field!", "ERROR",
                            JOptionPane.ERROR_MESSAGE);
                }

                else {

                    account.person.setContactNumber(tempHolder.replace(",", " "));
                    JOptionPane.showMessageDialog(userHomeFrame,
                            "<html>Contact Number was update to " + tempHolder + "</html>",
                            "SUCCESS",
                            JOptionPane.INFORMATION_MESSAGE);

                    userHomeFrame.dispose();
                    userHomePage(account);
                }
            }
        });

        JLabel updateLabel6 = new JLabel("Update");
        updateLabel6.setFont(new Font("Montserrat", Font.PLAIN, 12));
        updateLabel6.setForeground(new Color(38, 99, 232));
        updateLabel6.setBounds(52, 335, 50, 50);
        updateLabel6.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {

                updateLabel6.setForeground(Color.DARK_GRAY);
            }

            @Override
            public void mouseExited(MouseEvent e) {

                updateLabel6.setForeground(new Color(38, 99, 232));
            }

            @Override
            public void mouseClicked(MouseEvent e) {

                String tempHolder = JOptionPane.showInputDialog(userHomeFrame, "New Username: ", "UPDATE",
                        JOptionPane.QUESTION_MESSAGE);

                if (tempHolder == null) {

                    JOptionPane.showMessageDialog(userHomeFrame, "You cancelled the Operation.", "OKAY",
                            JOptionPane.INFORMATION_MESSAGE);
                }

                else {

                    account.setUsername(tempHolder.replace(",", " "));
                    JOptionPane.showMessageDialog(userHomeFrame,
                            "<html>New Username was update to " + tempHolder + "</html>",
                            "SUCCESS",
                            JOptionPane.INFORMATION_MESSAGE);

                    userHomeFrame.dispose();
                    userHomePage(account);
                }
            }
        });

        JLabel updateLabel7 = new JLabel("Update");
        updateLabel7.setFont(new Font("Montserrat", Font.PLAIN, 12));
        updateLabel7.setForeground(new Color(38, 99, 232));
        updateLabel7.setBounds(52, 397, 50, 50);
        updateLabel7.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {

                updateLabel7.setForeground(Color.DARK_GRAY);
            }

            @Override
            public void mouseExited(MouseEvent e) {

                updateLabel7.setForeground(new Color(38, 99, 232));
            }

            @Override
            public void mouseClicked(MouseEvent e) {

                String tempHolder = JOptionPane.showInputDialog(userHomeFrame, "New Password: ", "UPDATE",
                        JOptionPane.QUESTION_MESSAGE);

                if (tempHolder == null) {

                    JOptionPane.showMessageDialog(userHomeFrame, "You cancelled the Operation.", "OKAY",
                            JOptionPane.INFORMATION_MESSAGE);
                }

                else {

                    String tempHolder2 = JOptionPane.showInputDialog(userHomeFrame, "Confrim Password: ", "UPDATE",
                            JOptionPane.QUESTION_MESSAGE);

                    if (tempHolder == null) {

                        JOptionPane.showMessageDialog(userHomeFrame, "You cancelled the Operation.", "OKAY",
                                JOptionPane.INFORMATION_MESSAGE);
                    }

                    else if (tempHolder2.equals(tempHolder)) {

                        account.setPassword(tempHolder2.replace(",", " "));
                        JOptionPane.showMessageDialog(userHomeFrame,
                                "<html>New Password was update to " + tempHolder + "</html>",
                                "SUCCESS",
                                JOptionPane.INFORMATION_MESSAGE);

                        userHomeFrame.dispose();
                        userHomePage(account);
                    }

                    else {

                        JOptionPane.showMessageDialog(userHomeFrame, "Password and Confirm Password does not match",
                                "ERROR",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        profilePanel.add(profile2Label);
        profilePanel.add(profileDetailsLabel);
        profilePanel.add(updateLabel1);
        profilePanel.add(updateLabel2);
        profilePanel.add(updateLabel3);
        profilePanel.add(updateLabel4);
        profilePanel.add(updateLabel5);
        profilePanel.add(updateLabel6);
        profilePanel.add(updateLabel7);

        ImageIcon profileIcon = new ImageIcon("profile.png");

        JPanel logOutPanel = new JPanel();
        logOutPanel.setLayout(null);
        ImageIcon logOutIcon = new ImageIcon("logOut.png");

        tabbedPane.addTab("Schedule", scheduleIcon, scheduleScroller);
        tabbedPane.addTab("Pay", payIcon, payPanel);
        tabbedPane.addTab("Profile", profileIcon, profilePanel);
        tabbedPane.addTab("Logout", logOutIcon, logOutPanel);
        tabbedPane.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent e) {

                int tabIndex = tabbedPane.getSelectedIndex();

                for (int i = 0; i < tabbedPane.getTabCount(); i++) {

                    if (i == tabIndex) {

                        tabbedPane.setIconAt(i,
                                changeIconColor((ImageIcon) tabbedPane.getIconAt(i), new Color(38, 99, 232)));
                    }

                    else {
                        switch (i) {
                            case 0:
                                tabbedPane.setIconAt(i, scheduleIcon);
                                break;
                            case 1:
                                tabbedPane.setIconAt(i, payIcon);
                                break;
                            case 2:
                                tabbedPane.setIconAt(i, profileIcon);
                                break;
                            case 3:
                                tabbedPane.setIconAt(i, logOutIcon);
                                break;
                        }
                    }
                }
            }

        });

        userHomeFrame.add(titlePanel);
        userHomeFrame.add(tabbedPane);
        userHomeFrame.setVisible(true);
    }

    static ImageIcon changeIconColor(ImageIcon origIcon, Color newColor) {

        Image origImage = origIcon.getImage();
        int width = origImage.getWidth(null);
        int height = origImage.getHeight(null);

        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = bufferedImage.createGraphics();
        g2d.drawImage(origImage, 0, 0, null);
        g2d.dispose();

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int argb = bufferedImage.getRGB(j, i);
                int alpha = (argb >> 24) & 0xff;

                if (alpha != 0) {

                    bufferedImage.setRGB(j, i, (alpha << 24) | (newColor.getRGB() & 0x00ffffff));
                }
            }
        }

        return new ImageIcon(bufferedImage);
    }

    static boolean isValidDate(String dateStr) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {

            LocalDate date = LocalDate.parse(dateStr, formatter);
            return true;
        } catch (DateTimeParseException e) {

            return false;
        }
    }

    static boolean isValidTimeFormat(String timeStr) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");
        try {

            LocalTime time = LocalTime.parse(timeStr, formatter);
            return true;
        } catch (DateTimeParseException e) {

            return false;
        }
    }
}