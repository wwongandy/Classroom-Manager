import java.sql.ResultSet;
import java.sql.SQLException;

public class Student {

	private int sid;
	private int stud_id; // Student Id
	private String fname; // First name
	private String sname; // Surname
	
	public Student(String sid, String stud_id, String fname, String sname) {
		this.setSid(sid);
		this.setStud_id(stud_id);
		this.setFname(fname);
		this.setSname(sname);
	}
	
	/**
	 * Returns a string that describes a single student individual
	 */
	public String toString() {
		return "Student ID: " + stud_id + ", Name: " + fname + " " + sname;
	}
	
	public static Student fromResultSet(ResultSet student) throws SQLException {
		String sid = student.getString("SID");
		String stud_id = student.getString("STUD_ID");
		String fname = student.getString("FNAME");
		String sname = student.getString("SNAME");
		
		return new Student(sid, stud_id, fname, sname);
	}
	
	public int getSid() {
		return sid;
	}
	
	public void setSid(String sid) {
		this.sid = Integer.parseInt(sid);
	}
	
	public int getStud_id() {
		return stud_id;
	}
	
	public void setStud_id(String stud_id) {
		this.stud_id = Integer.parseInt(stud_id);
	}
	
	public String getFname() {
		return fname;
	}
	
	public void setFname(String fname) {
		this.fname = fname;
	}
	
	public String getSname() {
		return sname;
	}
	
	public void setSname(String sname) {
		this.sname = sname;
	}
}
