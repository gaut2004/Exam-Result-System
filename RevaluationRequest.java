package CollegeApp.com;

public class RevaluationRequest {
    private String rollNumber;
    private String subject;

    public RevaluationRequest(String rollNumber, String subject) {
        this.rollNumber = rollNumber;
        this.subject = subject;
    }

    // Getters and Setters

    public String getRollNumber() {
        return rollNumber;
    }

    public void setRollNumber(String rollNumber) {
        this.rollNumber = rollNumber;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Override
    public String toString() {
        return "Roll Number: " + rollNumber + ", Subject: " + subject;
    }
}
