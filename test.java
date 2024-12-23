import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class test {

    public static void main(String[] args) {

        JFrame logInFrame = new JFrame();

        logInFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        logInFrame.setResizable(false);
        logInFrame.setLayout(null);
        logInFrame.setSize(400, 500);
        logInFrame.setLocationRelativeTo(null);
        logInFrame.setUndecorated(true);
        logInFrame.getContentPane().setBackground(Color.white);

        ImageIcon icon = new ImageIcon("view2.png");

        JLabel label = new JLabel();
        label.setIcon(icon);
        label.setBounds(0, 0, 100, 100);

        logInFrame.add(label);
        logInFrame.setVisible(true);
    }
}
