package ramgee.project.vo;

public class MembersVO {
	private int member_no;//주문번호
	private String id;//회원아이디
	private String pw;//회원비밀번호
	private String name;//회원이름
	private int phone_number;//회원연락처
	
	
	//기본생성자는 반드시 있어야 한다
		public MembersVO() {
		}

		public MembersVO(int member_no, String id, String pw, String name,int phone_number) {
			
			super();
			this.member_no = member_no;
			this.id = id;
			this.pw = pw;
			this.name = name;
			this.phone_number = phone_number;
			
		}
		
		//getter, setter
		public int getMember_no() {
			return member_no;
		}

		public void setMember_no(int member_no) {
			this.member_no = member_no;
		}

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getPw() {
			return pw;
		}

		public void setPw(String pw) {
			this.pw = pw;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getPhone_number() {
			return phone_number;
		}

		public void setPhone_number(int phone_number) {
			this.phone_number = phone_number;
		}
		
		
		
		
		
	}


