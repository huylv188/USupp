package uetsupport.dtui.uet.edu.uetsupport.models;

/**
 * Created by huylv on 08/12/2015.
 */
public class User {
    private String password;
    private String fullName;
    private String DoB;
    private long studentID;
    private String faculty;
    private String khoa;

    private Schedule schedule;
    private ExamSchedule examSchedule;


    public User( String password, String fullName, String doB, long studentID, String faculty, String khoa) {
        this.password = password;
        this.fullName = fullName;
        DoB = doB;
        this.studentID = studentID;
        this.faculty = faculty;
        this.khoa = khoa;
    }

    public ExamSchedule getExamSchedule() {
        return examSchedule;
    }

    public void setExamSchedule(ExamSchedule examSchedule) {
        this.examSchedule = examSchedule;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getDoB() {
        return DoB;
    }

    public void setDoB(String doB) {
        DoB = doB;
    }

    public long getStudentID() {
        return studentID;
    }

    public void setStudentID(long studentID) {
        this.studentID = studentID;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public String getKhoa() {
        return khoa;
    }

    public void setKhoa(String khoa) {
        this.khoa = khoa;
    }
}
