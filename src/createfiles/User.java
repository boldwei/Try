package createfiles;

public class User {
	private String name;
	private String sex;
	private String job;
	private String pay;
	private String address;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getJob() {
		return job;
	}
	public void setJob(String job) {
		this.job = job;
	}
	public String getPay() {
		return pay;
	}
	public void setPay(String pay) {
		this.pay = pay;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	@Override
	public String toString() {
		return "User [name=" + name + ", sex=" + sex + ", job=" + job
				+ ", pay=" + pay + ", address=" + address + ", getName()="
				+ getName() + ", getSex()=" + getSex() + ", getJob()="
				+ getJob() + ", getPay()=" + getPay() + ", getAddress()="
				+ getAddress() + ", getClass()=" + getClass() + ", hashCode()="
				+ hashCode() + ", toString()=" + super.toString() + "]";
	}
	public User(String name, String sex, String job, String pay, String address) {
		super();
		this.name = name;
		this.sex = sex;
		this.job = job;
		this.pay = pay;
		this.address = address;
	}
	public User() {
	}
}
