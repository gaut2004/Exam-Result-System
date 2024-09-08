package CollegeApp.com;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/college_db";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Gautam@2004";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static List<Result> getResults(String rollNumber, String dob) {
        List<Result> results = new ArrayList<>();
        String sql = "SELECT s.roll_number, s.name, s.department, s.dob, r.subject, r.obtained_marks, r.total_marks, r.grade " +
                "FROM students s " +
                "JOIN results r ON s.roll_number = r.roll_number " +
                "WHERE s.roll_number = ? AND s.dob = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, rollNumber);
            stmt.setDate(2, Date.valueOf(dob));
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    results.add(new Result(
                            rs.getString("roll_number"),
                            rs.getString("name"),
                            rs.getString("department"),
                            rs.getString("dob"),
                            rs.getString("subject"),
                            rs.getInt("obtained_marks"),
                            rs.getInt("total_marks"),
                            rs.getString("grade")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return results;
    }

    public static boolean applyForRevaluation(String rollNumber, String subject) {
        String sql = "INSERT INTO revaluation_requests (roll_number, subject) VALUES (?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, rollNumber);
            stmt.setString(2, subject);
            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean verifyAdminCredentials(String userId, String password) {
        // For demonstration purposes, using static check.
        // In production, check against a database table.
        return "admin".equals(userId) && "password".equals(password);
    }

    public static boolean createOrUpdateResult(Result result) {
        String insertStudentSql = "INSERT INTO students (roll_number, name, department, dob) VALUES (?, ?, ?, ?) " +
                "ON DUPLICATE KEY UPDATE name = VALUES(name), department = VALUES(department), dob = VALUES(dob)";
        String insertResultSql = "INSERT INTO results (roll_number, subject, obtained_marks, total_marks, grade) VALUES (?, ?, ?, ?, ?) " +
                "ON DUPLICATE KEY UPDATE obtained_marks = VALUES(obtained_marks), total_marks = VALUES(total_marks), grade = VALUES(grade)";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            conn.setAutoCommit(false);

            try (PreparedStatement studentStmt = conn.prepareStatement(insertStudentSql);
                 PreparedStatement resultStmt = conn.prepareStatement(insertResultSql)) {

                studentStmt.setString(1, result.getRollNumber());
                studentStmt.setString(2, result.getName());
                studentStmt.setString(3, result.getDepartment());
                studentStmt.setDate(4, Date.valueOf(result.getDob()));
                studentStmt.executeUpdate();

                resultStmt.setString(1, result.getRollNumber());
                resultStmt.setString(2, result.getSubject());
                resultStmt.setInt(3, result.getObtainedMarks());
                resultStmt.setInt(4, result.getTotalMarks());
                resultStmt.setString(5, result.getGrade());
                resultStmt.executeUpdate();

                conn.commit();
                return true;
            } catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static List<RevaluationRequest> getRevaluationRequests() {
        List<RevaluationRequest> requests = new ArrayList<>();
        String sql = "SELECT roll_number, subject FROM revaluation_requests";
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                requests.add(new RevaluationRequest(rs.getString("roll_number"), rs.getString("subject")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return requests;
    }
}
