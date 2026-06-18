package GUI;

import exception.AuthenticationException;
import model.User;
import javax.swing.*;
import java.awt.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class LoginDialog extends JDialog {

    private JTextField txtEmail;
    private JPasswordField txtPassword;
    private boolean loginSuccess = false;
    private User loggedUser;

    private User[] users = {
        new User(1, "Pemilik BENTO", "owner@bento.com", "bento123", "Owner"),
        new User(2, "Kasir BENTO",   "kasir@bento.com", "kasir123", "Kasir")
    };

    public LoginDialog(JFrame parent) {
        super(parent, "Login - BENTO", true);
        initUI();
    }

    private void initUI() {
        setSize(380, 240);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 24, 20, 24));

        JLabel lblHeader = new JLabel("BENTO — Login", SwingConstants.CENTER);
        lblHeader.setFont(new Font("SansSerif", Font.BOLD, 16));

        JPanel form = new JPanel(new GridLayout(4, 2, 8, 8));
        txtEmail    = new JTextField("owner@bento.com");
        txtPassword = new JPasswordField("bento123");

        form.add(new JLabel("Email:"));    form.add(txtEmail);
        form.add(new JLabel("Password:")); form.add(txtPassword);
        form.add(new JLabel(""));
        form.add(new JLabel("owner@bento.com / bento123", SwingConstants.RIGHT));

        JButton btnLogin = new JButton("Masuk");
        btnLogin.setBackground(new Color(88, 56, 150));
        btnLogin.setForeground(Color.WHITE);

        btnLogin.addActionListener(e -> doLogin());
        form.add(btnLogin);
        form.add(new JLabel(""));

        panel.add(lblHeader, BorderLayout.NORTH);
        panel.add(form, BorderLayout.CENTER);
        add(panel);
    }
    
    private void doLogin() {
        String email    = txtEmail.getText();
        String password = new String(txtPassword.getPassword());

        try {
            User found = null;
            for (User u : users) {
                if (u.getEmail().equalsIgnoreCase(email)) {
                    found = u;
                    break;
                }
            }

            if (found == null) {
                throw new AuthenticationException("Email tidak ditemukan: " + email);
            }

            found.login(email, password);

            loggedUser   = found;
            loginSuccess = true;
            JOptionPane.showMessageDialog(this, "Selamat datang, " + found.getName() + "!");
            dispose();

        } catch (AuthenticationException ex) {
            JOptionPane.showMessageDialog(this, "Login gagal!\n" + ex.getMessage(), "Error Autentikasi", JOptionPane.ERROR_MESSAGE);
        }
    }

    public boolean isLoginSuccess() { 
        return loginSuccess; 
    }
    
    public User getLoggedUser() { 
        return loggedUser; 
    }
}

