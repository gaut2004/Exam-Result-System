package CollegeApp.com;

public class Result {
    private String rollNumber;
    private String name;
    private String department;
    private String dob;
    private String subject;
    private int obtainedMarks;
    private int totalMarks;
    private String grade;

    public Result(String rollNumber, String name, String department, String dob, String subject, int obtainedMarks, int totalMarks, String grade) {
        this.rollNumber = rollNumber;
        this.name = name;
        this.department = department;
        this.dob = dob;
        this.subject = subject;
        this.obtainedMarks = obtainedMarks;
        this.totalMarks = totalMarks;
        this.grade = grade;
    }

    // Getters and Setters

    public String getRollNumber() {
        return rollNumber;
    }

    public void setRollNumber(String rollNumber) {
        this.rollNumber = rollNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public int getObtainedMarks() {
        return obtainedMarks;
    }

    public void setObtainedMarks(int obtainedMarks) {
        this.obtainedMarks = obtainedMarks;
    }

    public int getTotalMarks() {
        return totalMarks;
    }

    public void setTotalMarks(int totalMarks) {
        this.totalMarks = totalMarks;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    @Override
    public String toString() {
        return "Roll Number: " + rollNumber + "\n" +
                "Name: " + name + "\n" +
                "Department: " + department + "\n" +
                "Date of Birth: " + dob + "\n" +
                "Subject: " + subject + "\n" +
                "Obtained Marks: " + obtainedMarks + "\n" +
                "Total Marks: " + totalMarks + "\n" +
                "Grade: " + grade;
    }
}
