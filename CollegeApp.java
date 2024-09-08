package CollegeApp.com;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

public class CollegeApp {

    public static void main(String[] args) {
        JFrame frame = new JFrame("Dhanalakshmi Srinivasan Engineering College Perambalur, Tamilnadu-621212");
        JTabbedPane tabbedPane = new JTabbedPane();

        tabbedPane.add("Student Panel", new StudentPanel());
        tabbedPane.add("Admin Panel", new AdminPanel());

        frame.add(tabbedPane);
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    static class StudentPanel extends JPanel {
        private JTextField rollNumberField;
        private JTextField dobField;
        private JTextArea resultArea;
        private List<Result> results;

        public StudentPanel() {
            setLayout(new BorderLayout());

            JLabel header = new JLabel("Dhanalakshmi Srinivasan Engineering College Perambalur, Tamilnadu-621212", JLabel.CENTER);
            header.setFont(new java.awt.Font("Serif", java.awt.Font.BOLD, 20));
            add(header, BorderLayout.NORTH);

            JPanel inputPanel = new JPanel(new GridLayout(5, 50));
            inputPanel.add(new JLabel("Roll Number:"));
            rollNumberField = new JTextField();
            inputPanel.add(rollNumberField);

            inputPanel.add(new JLabel("Date of Birth (YYYY-MM-DD):"));
            dobField = new JTextField();
            inputPanel.add(dobField);

            JButton showResultButton = new JButton("Show Result");
            showResultButton.addActionListener(new ShowResultListener());
            inputPanel.add(showResultButton);

            JButton revaluationButton = new JButton("Apply for Revaluation");
            revaluationButton.addActionListener(new RevaluationListener());
            inputPanel.add(revaluationButton);

            JButton printPdfButton = new JButton("Print Result as PDF");
            printPdfButton.addActionListener(new PrintPdfListener());
            inputPanel.add(printPdfButton);

            add(inputPanel, BorderLayout.CENTER);

            resultArea = new JTextArea();
            add(new JScrollPane(resultArea), BorderLayout.SOUTH);
        }

        private class ShowResultListener implements ActionListener {
            public void actionPerformed(ActionEvent e) {
                String rollNumber = rollNumberField.getText();
                String dob = dobField.getText();
                results = DatabaseHelper.getResults(rollNumber, dob);
                StringBuilder sb = new StringBuilder();
                for (Result result : results) {
                    sb.append(result).append("\n");
                }
                resultArea.setText(sb.toString());
            }
        }

        private class RevaluationListener implements ActionListener {
            public void actionPerformed(ActionEvent e) {
                String rollNumber = rollNumberField.getText();
                String subject = JOptionPane.showInputDialog("Enter subject for revaluation:");
                if (DatabaseHelper.applyForRevaluation(rollNumber, subject)) {
                    JOptionPane.showMessageDialog(StudentPanel.this, "Revaluation request submitted.");
                } else {
                    JOptionPane.showMessageDialog(StudentPanel.this, "Error submitting revaluation request.");
                }
            }
        }

        private class PrintPdfListener implements ActionListener {
            public void actionPerformed(ActionEvent e) {
                if (results == null || results.isEmpty()) {
                    JOptionPane.showMessageDialog(StudentPanel.this, "No result to print. Please fetch the result first.");
                    return;
                }

                try {
                    String rollNumber = rollNumberField.getText();
                    Document document = new Document();
                    PdfWriter.getInstance(document, new FileOutputStream(rollNumber + "_result.pdf"));
                    document.open();
                    for (Result result : results) {
                        document.add(new Paragraph(result.toString()));
                        document.add(new Paragraph("--------------------------------------------------"));
                    }
                    document.close();
                    JOptionPane.showMessageDialog(StudentPanel.this, "Result printed as PDF.");
                } catch (DocumentException | IOException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(StudentPanel.this, "Error printing result to PDF.");
                }
            }
        }
    }

    static class AdminPanel extends JPanel {
        private CardLayout cardLayout;
        private JPanel loginPanel;
        private JPanel mainPanel;

        public AdminPanel() {
            cardLayout = new CardLayout();
            setLayout(cardLayout);

            loginPanel = new LoginPanel();
            mainPanel = new MainPanel();

            add(loginPanel, "loginPanel");
            add(mainPanel, "mainPanel");

            cardLayout.show(this, "loginPanel");
        }

        class LoginPanel extends JPanel {
            private JTextField userIdField;
            private JPasswordField passwordField;

            public LoginPanel() {
                setLayout(new GridLayout(3, 2));

                add(new JLabel("User ID:"));
                userIdField = new JTextField();
                add(userIdField);

                add(new JLabel("Password:"));
                passwordField = new JPasswordField();
                add(passwordField);

                JButton loginButton = new JButton("Login");
                loginButton.addActionListener(new LoginButtonListener());
                add(loginButton);
            }

            private class LoginButtonListener implements ActionListener {
                public void actionPerformed(ActionEvent e) {
                    String userId = userIdField.getText();
                    String password = new String(passwordField.getPassword());
                    if (DatabaseHelper.verifyAdminCredentials(userId, password)) {
                        cardLayout.show(AdminPanel.this, "mainPanel");
                    } else {
                        JOptionPane.showMessageDialog(LoginPanel.this, "Invalid credentials.");
                    }
                }
            }
        }

        class MainPanel extends JPanel {
            public MainPanel() {
                setLayout(new BorderLayout());

                JLabel header = new JLabel("Admin Panel", JLabel.CENTER);
                header.setFont(new java.awt.Font("Serif", java.awt.Font.BOLD, 20));
                add(header, BorderLayout.NORTH);

                JTabbedPane adminTabs = new JTabbedPane();
                adminTabs.add("Create/Update Result", new CreateUpdateResultPanel());
                adminTabs.add("View Revaluation Requests", new ViewRevaluationRequestsPanel());

                add(adminTabs, BorderLayout.CENTER);
            }
        }

        static class CreateUpdateResultPanel extends JPanel {
            private JTextField rollNumberField;
            private JTextField nameField;
            private JTextField deptField;
            private JTextField dobField;
            private JTextField subjectField;
            private JTextField obtainedMarksField;
            private JTextField totalMarksField;

            public CreateUpdateResultPanel() {
                setLayout(new GridLayout(8, 2));

                add(new JLabel("Roll Number:"));
                rollNumberField = new JTextField();
                add(rollNumberField);

                add(new JLabel("Name:"));
                nameField = new JTextField();
                add(nameField);

                add(new JLabel("Department:"));
                deptField = new JTextField();
                add(deptField);

                add(new JLabel("Date of Birth (YYYY-MM-DD):"));
                dobField = new JTextField();
                add(dobField);

                add(new JLabel("Subject:"));
                subjectField = new JTextField();
                add(subjectField);

                add(new JLabel("Obtained Marks:"));
                obtainedMarksField = new JTextField();
                add(obtainedMarksField);

                add(new JLabel("Total Marks:"));
                totalMarksField = new JTextField();
                add(totalMarksField);

                JButton saveButton = new JButton("Save");
                saveButton.addActionListener(new SaveButtonListener());
                add(saveButton);
            }

            private class SaveButtonListener implements ActionListener {
                public void actionPerformed(ActionEvent e) {
                    String rollNumber = rollNumberField.getText();
                    String name = nameField.getText();
                    String dept = deptField.getText();
                    String dob = dobField.getText();
                    String subject = subjectField.getText();
                    int obtainedMarks = Integer.parseInt(obtainedMarksField.getText());
                    int totalMarks = Integer.parseInt(totalMarksField.getText());
                    String grade = calculateGrade(obtainedMarks, totalMarks);

                    Result result = new Result(rollNumber, name, dept, dob, subject, obtainedMarks, totalMarks, grade);
                    if (DatabaseHelper.createOrUpdateResult(result)) {
                        JOptionPane.showMessageDialog(CreateUpdateResultPanel.this, "Result saved successfully.");
                    } else {
                        JOptionPane.showMessageDialog(CreateUpdateResultPanel.this, "Error saving result.");
                    }
                }

                private String calculateGrade(int obtainedMarks, int totalMarks) {
                    double percentage = (double) obtainedMarks / totalMarks * 100;
                    if (percentage >= 90) return "A";
                    if (percentage >= 80) return "B";
                    if (percentage >= 70) return "C";
                    if (percentage >= 60) return "D";
                    return "F";
                }
            }
        }

        static class ViewRevaluationRequestsPanel extends JPanel {
            private JTextArea requestsArea;

            public ViewRevaluationRequestsPanel() {
                setLayout(new BorderLayout());

                requestsArea = new JTextArea();
                add(new JScrollPane(requestsArea), BorderLayout.CENTER);

                JButton refreshButton = new JButton("Refresh");
                refreshButton.addActionListener(new RefreshButtonListener());
                add(refreshButton, BorderLayout.SOUTH);
            }

            private class RefreshButtonListener implements ActionListener {
                public void actionPerformed(ActionEvent e) {
                    List<RevaluationRequest> requests = DatabaseHelper.getRevaluationRequests();
                    StringBuilder sb = new StringBuilder();
                    for (RevaluationRequest request : requests) {
                        sb.append(request).append("\n");
                    }
                    requestsArea.setText(sb.toString());
                }
            }
        }
    }
}
