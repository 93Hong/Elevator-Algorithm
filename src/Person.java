import java.awt.Image;
import java.awt.Toolkit;
import java.util.Random;

public class Person {
	private Toolkit tk;
	private Image img[] = new Image[41]; // 사람의 이미지들을 저장하고 있음
	private int imageindex;
	
	private static int count = 0;
	
	private int state = 0;
	/*
	사람의 state 를 구별하는 변수 
	0 = 엘리베이터를 타려고 걸어가는 중
	1 = 엘리베이터를 탈려고 기다리는 중
	2 = 엘리베이터 타고있는 중
	3 = 계단으로 가고있는 중
	*/
	
	// 사람의 x y 좌표, move thread 에 의해서 값이 변경되어 움직이는 것 처럼 보이게끔 함
	private int x = 0;
	private int y = 0;
	
	// 사람의 목적지 x y 좌표
	private int d_x = 0; // 내가 내려야 하는 층 위치
	private int d_y = 0;
	
	// 사람이 타려는 엘리베이의 x y 좌표
	private int e_x = 0; // 내가 타야하는 엘리베이터의 위치
	private int e_y = 0;
	
	private int id; // 사람들에게 각각 id 를 부여
	private String upDown = ""; // 엘리베이터의 방향성을 나타냄,  Up, Down
	
	public Person(int departure, int destination, int elevatorLocation) { // 출발층, 도착층, 엘리베이터 x 좌표
		
		// 엘리베이터 위치가 어디인지
		if (elevatorLocation == 400) { // 두번째 엘리베이터
			this.x = 700; // 오른쪽에서 사람이 생성됨
			this.d_x = -50; // frame 의 시작
			// 왼쪽에서 사람이 나와서 오른쪽으로 사라지게 하려고
			
		} else if (elevatorLocation == 200) { // 두번째 엘베
			this.x = -50; // 왼쪽에서 사람이 생성됨, 버튼 뒤에서 나오게끔
			this.d_x = 700; // frame 의 끝
			// 오른쪽에서 사람이 나와서 왼쪽으로 사라지게 하려고
			
		} else if (elevatorLocation == 550) { // 계단으로 가는 경우
			this.x = 700; // 왼쪽에서 사람이 생성되서 계단을 거쳐 오른쪽으로 나감
			this.d_x = -50;
			state = 3; // 사람의 상태를 계단으로 가는 중 으로 바꿈
			
		}
		
		this.e_x = elevatorLocation; // 사람이 어디까지 갈지
		this.setId(count++); // 개개인 구별하기 위해서
		
		this.d_y = destination * 50; // 도착할 층
		this.y = departure * 50; // 사람이 생성되는 층
		
		if (departure < destination) // 사람의 방향성 설정함
			setUpDown("Down");
		else if (departure > destination)
			setUpDown("Up");
				
		tk = Toolkit.getDefaultToolkit();
		
		Random random = new Random(); // 얘들 이미지 바꿔주기
		imageindex = (random.nextInt(19));
		if(imageindex%2 == 1)
			imageindex--;////////////// 랜덤으로 사람 이미지를 넣어주는거
		tk = Toolkit.getDefaultToolkit();
		img[0] = tk.createImage("person1.jpg");
		img[1] = tk.createImage("person2.jpg");
		img[2] = tk.createImage("person3.jpg");
		img[3] = tk.createImage("person4.jpg");
		img[4] = tk.createImage("person5.jpg");
		img[5] = tk.createImage("person6.jpg");
		img[6] = tk.createImage("person7.jpg");
		img[7] = tk.createImage("person8.jpg");
		img[8] = tk.createImage("person9.jpg");
		img[9] = tk.createImage("person10.jpg");
		img[10] = tk.createImage("person11.jpg");
		img[11] = tk.createImage("person12.jpg");
		img[12] = tk.createImage("person13.jpg");
		img[13] = tk.createImage("person14.jpg");
		img[14] = tk.createImage("person15.jpg");
		img[15] = tk.createImage("person16.jpg");
		img[16] = tk.createImage("person17.jpg");
		img[17] = tk.createImage("person18.jpg");
		img[18] = tk.createImage("person19.jpg");
		img[19] = tk.createImage("person20.jpg");
		img[20] = tk.createImage("person1_1.png");
		img[21] = tk.createImage("person1_2.png");
		img[22] = tk.createImage("person3_1.png");
		img[23] = tk.createImage("person3_2.png");
		img[24] = tk.createImage("person5_1.png");
		img[25] = tk.createImage("person5_2.png");
		img[26] = tk.createImage("person7_1.png");
		img[27] = tk.createImage("person7_2.png");
		img[28] = tk.createImage("person9_1.png");
		img[29] = tk.createImage("person9_2.png");
		img[30] = tk.createImage("person11_1.png");
		img[31] = tk.createImage("person11_2.png");
		img[32] = tk.createImage("person13_1.png");
		img[33] = tk.createImage("person13_1.png");
		img[34] = tk.createImage("person15_1.png");
		img[35] = tk.createImage("person15_2.png");
		img[36] = tk.createImage("person17_1.png");
		img[37] = tk.createImage("person17_2.png");
		img[38] = tk.createImage("person19_1.png");
		img[39] = tk.createImage("person19_2.png");
		img[40] = tk.createImage("person1.jpg");
	}
	
	//////////////////////////////////////////////////////////////////////////////
	// Setter, Getter
	
	public int getState() {
		return state;
	}
	
	public int getD_x() {
		return d_x;
	}

	public int getD_y() {
		return d_y;
	}

	public void setD_y(int d_y) {
		this.d_y = d_y;
	}

	public void setState(int state) {
		this.state = state;
	}

	public int getE_y() {
		return e_y;
	}
	
	public void setE_y(int d_y) {
		this.e_y = d_y;
	}
	public Toolkit getTk() {
		return tk;
	}
	
	public Image getImage() {
		return img[20];
	}
	
	public void setImg(int i) {
		img[20] = img[i + imageindex];
	}
	
	public int getImgindex() {
		return imageindex;
	}
	
	public void setTk(Toolkit tk) {
		this.tk = tk;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getE_x() {
		return e_x;
	}

	public void setE_x(int d_x) {
		this.e_x = d_x;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUpDown() {
		return upDown;
	}

	public void setUpDown(String upDown) {
		this.upDown = upDown;
	}

}
