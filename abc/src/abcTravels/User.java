package abcTravels;

public class User {
	String fname;
	String lname;
	String phno;
	String gen;
	String email;
	String password;
	int failCount;
	int accountStatus;
	public User(String fname, String lname, String phno, String gen, String email, String password, int failCount,
			int accountStatus) {
		super();
		this.fname = fname;
		this.lname = lname;
		this.phno = phno;
		this.gen = gen;
		this.email = email;
		this.password = password;
		this.failCount = failCount;
		this.accountStatus = accountStatus;
	}
	@Override
	public String toString() {
		return "User [fname=" + fname + ", lname=" + lname + ", phno=" + phno + ", gen=" + gen + ", email=" + email
				+ ", password=" + password + ", failCount=" + failCount + ", accountStatus=" + accountStatus
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString()
				+ "]";
	}
	
	
	
}

