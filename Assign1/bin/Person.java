package bin;

public class Person {
	
	private String sin;
	private String name;
	private String height;
	private String weight;
	private String eyecolor;
	private String haircolor;
	private String addr;
	private String gender;
	private String birthday;
	
	public Person(String sin, String name, String height, String weight, String eyecolor, String haircolor, String addr, String gender, String birthday) {
		this.sin = sin;
		this.name = name;
		this.height = height;
		this.weight = weight;
		this.eyecolor = eyecolor;
		this.haircolor = haircolor;
		this.addr = addr;
		this.gender = gender;
		this.birthday = birthday;
		
	}
	
	
	public String getSin() {
		return sin;
	}

	public void setSin(String sin) {
		this.sin = sin;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getEyecolor() {
		return eyecolor;
	}

	public void setEyecolor(String eyecolor) {
		this.eyecolor = eyecolor;
	}

	public String getHaircolor() {
		return haircolor;
	}

	public void setHaircolor(String haircolor) {
		this.haircolor = haircolor;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

}
