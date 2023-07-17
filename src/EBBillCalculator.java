import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class EBBillCalculator extends JFrame implements ActionListener {

    private JTextField usernameField, passwordField, consumerNumberField, mobileNumberField, emailField,
            unitsConsumedField;
    private JButton loginButton, proceedToBillButton, proceedToPaymentButton, payButton;
    private JLabel regionLabel, consumerNumberLabel, mobileNumberLabel, emailLabel, unitsConsumedLabel,
            totalBillLabel;
    private JComboBox<String> regionField;
    private JPanel mainPanel, regionPanel, consumerNumberPanel, mobileNumberPanel, emailPanel, unitsConsumedPanel,
            totalBillPanel;
    private String[] regions = { "Pondy", "Karaikal", "Mahe", "Yanam" };
    private double ratePerUnit = 1.50;

    public EBBillCalculator() {
        setTitle("EB Bill Calculator");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        createLoginPanel();

        add(mainPanel);
        setVisible(true);
    }

    private void createLoginPanel() {
        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new FlowLayout());

        JLabel loginLabel = new JLabel("Login Credentials");
        loginLabel.setFont(new Font("Tahoma", Font.BOLD, 20));

        JPanel usernamePanel = new JPanel();
        usernamePanel.setLayout(new FlowLayout());
        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField(10);

        JPanel passwordPanel = new JPanel();
        passwordPanel.setLayout(new FlowLayout());
        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField(10);

        loginButton = new JButton("Login");
        loginButton.addActionListener(this);

        loginPanel.add(loginLabel);
        usernamePanel.add(usernameLabel);
        usernamePanel.add(usernameField);
        passwordPanel.add(passwordLabel);
        passwordPanel.add(passwordField);
        loginPanel.add(usernamePanel);
        loginPanel.add(passwordPanel);
        loginPanel.add(loginButton);

        mainPanel.add(loginPanel);
    }

    private void createRegionPanel() {
        regionPanel = new JPanel();
        regionPanel.setLayout(new FlowLayout());

        regionLabel = new JLabel("Region:");
        regionField = new JComboBox<>(regions);

        regionPanel.add(regionLabel);
        regionPanel.add(regionField);

        mainPanel.add(regionPanel);
    }

    private void createConsumerNumberPanel() {
        consumerNumberPanel = new JPanel();
        consumerNumberPanel.setLayout(new FlowLayout());

        consumerNumberLabel = new JLabel("Consumer Number:");
        consumerNumberField = new JTextField(10);

        consumerNumberPanel.add(consumerNumberLabel);
        consumerNumberPanel.add(consumerNumberField);

        mainPanel.add(consumerNumberPanel);
    }

    private void createMobileNumberPanel() {
        mobileNumberPanel = new JPanel();
        mobileNumberPanel.setLayout(new FlowLayout());

        mobileNumberLabel = new JLabel("Mobile Number:");
        mobileNumberField = new JTextField(10);

        mobileNumberPanel.add(mobileNumberLabel);
        mobileNumberPanel.add(mobileNumberField);

        mainPanel.add(mobileNumberPanel);
    }

    private void createEmailPanel() {
        emailPanel = new JPanel();
        emailPanel.setLayout(new FlowLayout());

        emailLabel = new JLabel("Email:");
        emailField = new JTextField(10);

        emailPanel.add(emailLabel);
        emailPanel.add(emailField);

        mainPanel.add(emailPanel);
    }

    private void createUnitsConsumedPanel() {
        unitsConsumedPanel = new JPanel();
        unitsConsumedPanel.setLayout(new FlowLayout());

        unitsConsumedLabel = new JLabel("Units Consumed:");
        unitsConsumedField = new JTextField(10);

        proceedToBillButton = new JButton("Proceed to Bill");
        proceedToBillButton.addActionListener(this);

        unitsConsumedPanel.add(unitsConsumedLabel);
        unitsConsumedPanel.add(unitsConsumedField);
        unitsConsumedPanel.add(proceedToBillButton);

        mainPanel.add(unitsConsumedPanel);
    }

    private void createTotalBillPanel(int unitsConsumed) {
        totalBillPanel = new JPanel();
        totalBillPanel.setLayout(new FlowLayout());

        double totalBill = calculateTotalBill(unitsConsumed);
        totalBillLabel = new JLabel("Total Bill: Rs. " + totalBill);

        proceedToPaymentButton = new JButton("Proceed to Payment");
        proceedToPaymentButton.addActionListener(this);

        totalBillPanel.add(totalBillLabel);
        totalBillPanel.add(proceedToPaymentButton);

        mainPanel.add(totalBillPanel);
    }

    private double calculateTotalBill(int unitsConsumed) {
        double totalBill = 0.0;

        if (unitsConsumed <= 100) {
            totalBill = unitsConsumed * ratePerUnit;
        } else if (unitsConsumed <= 200) {
            totalBill = (100 * ratePerUnit) + ((unitsConsumed - 100) * (ratePerUnit + 1.00));
        } else if (unitsConsumed <= 500) {
            totalBill = (100 * ratePerUnit) + (100 * (ratePerUnit + 1.00))
                    + ((unitsConsumed - 200) * (ratePerUnit + 3.00));
        } else {
            totalBill = (100 * ratePerUnit) + (100 * (ratePerUnit + 1.00)) + (300 * (ratePerUnit + 3.00))
                    + ((unitsConsumed - 500) * (ratePerUnit + 5.00));
        }

        return totalBill;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            String username = usernameField.getText();
            String password = passwordField.getText();

            if (username.equals("123") && password.equals("123")) {
                JOptionPane.showMessageDialog(this, "Login Successful");
                mainPanel.removeAll();
                mainPanel.revalidate();
                mainPanel.repaint();

                createRegionPanel();
                createConsumerNumberPanel();
                createMobileNumberPanel();
                createEmailPanel();
                createUnitsConsumedPanel();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid credentials. Please try again.");
            }
        } else if (e.getSource() == proceedToBillButton) {
            int unitsConsumed = Integer.parseInt(unitsConsumedField.getText());
            createTotalBillPanel(unitsConsumed);
            mainPanel.revalidate();
            mainPanel.repaint();
        } else if (e.getSource() == proceedToPaymentButton) {
            String upiId = JOptionPane.showInputDialog(this, "Enter UPI ID:");
            String password = JOptionPane.showInputDialog(this, "Enter Password:");

            if (upiId.equals("eb@okhdfc") && password.equals("123")) {
                String captchaCode = generateCaptchaCode();
                String userCaptcha = JOptionPane.showInputDialog(this, "Enter Captcha Code: " + captchaCode);

                if (userCaptcha.equalsIgnoreCase(captchaCode)) {
                    JOptionPane.showMessageDialog(this, "Payment Successful");
                    showFinalOutput();
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid Captcha Code. Payment failed.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Invalid UPI ID or Password. Payment failed.");
            }
        }
    }

    private String generateCaptchaCode() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder captchaCode = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < 4; i++) {
            int index = random.nextInt(characters.length());
            captchaCode.append(characters.charAt(index));
        }

        return captchaCode.toString();
    }

    private void showFinalOutput() {
        String region = (String) regionField.getSelectedItem();
        String consumerNumber = consumerNumberField.getText();
        String mobileNumber = mobileNumberField.getText();
        String email = emailField.getText();
        int unitsConsumed = Integer.parseInt(unitsConsumedField.getText());
        double totalBill = calculateTotalBill(unitsConsumed);

        String output = "EB Department - Bill Details\n\n" +
                "Region: " + region + "\n" +
                "Consumer Number: " + consumerNumber + "\n" +
                "Mobile Number: " + mobileNumber + "\n" +
                "Email: " + email + "\n\n" +
                "Units Consumed: " + unitsConsumed + "\n" +
                "Total Bill: Rs. " + totalBill + "\n\n" +
                "Support Digital India - Go Green!";

        JOptionPane.showMessageDialog(this, output, "Final Output", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        new EBBillCalculator();
    }
}
