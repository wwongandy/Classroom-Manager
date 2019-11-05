
public class Student {

	private int sid;
	private int stud_id;
	private String fname;
	private String sname;
	
	public Student(String sid, String stud_id, String fname, String sname) {
		this.setSid(sid);
		this.setStud_id(stud_id);
		this.setFname(fname);
		this.setSname(sname);
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
