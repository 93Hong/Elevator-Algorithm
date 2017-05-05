
import java.awt.Image;
import java.awt.Toolkit;
import java.util.ArrayList;

public class Elevator {
	// waiting elevator, 해달 엘리베이터를 기다리고 있는 사람들을 저장함
	private ArrayList<Person> ReadyElevator = new ArrayList<Person>();
	
	// in elevator, 해당 엘리베이터에 타고있는 사람들을 저장ㅇ하고 있는 것
	private ArrayList<Person> inElevator = new ArrayList<Person>();
	
	private Toolkit tk;
	private Image img[] = new Image[5]; // 이미지를 담고있는 변수
	
	private String upDown = ""; // Up, Down, Stop, ""

	private int state = 0; // 0 = 태우러 가는중 (이 때는 얘들 태우면 안됨), 1 = 목적지에 도착했을 때 (이때는 태워야 됨), 2 = 위치 자동조정일때
	
	// 엘리베이터의 x y 좌표, move thread 에 의해서 값이 변경되어 움직이는 것 처럼 보이게끔 함
	private int x = 0;
	private int y = 250;

	private int d_y = 250; // 사람의 목적지에 대한 y 좌표
	
	public Elevator (int location) { // main algorithm 에 의해 결정된 y 좌표를 목적지로 설정
		this.x = location; // 엘리베이터의 x 축 좌표 (고정 값)
		
		tk = Toolkit.getDefaultToolkit(); // 문 열리는 이미지들
		img[0] = tk.createImage("ele0.jpg");
		img[1] = tk.createImage("ele1.jpg");
		img[2] = tk.createImage("ele2.jpg");
		img[3] = tk.createImage("ele3.jpg");
		img[4] = tk.createImage("ele0.jpg");
	}
	
	//////////////////////////////////////////////////////////////////////////////
	// Setter, Getter
	
	public String getUpDown() {
		return upDown;
	}
	
	public void setUpDown(String upDown) {
		this.upDown = upDown;
	}
	
	public int getState() {
		return state;
	}
	
	public void setState(int state) {
		this.state = state;
	}

	public ArrayList<Person> getReadyElevator() {
		return ReadyElevator;
	}
	
	public void setReadyElevator(Person persons) {
		ReadyElevator.add(persons);
	}

	public ArrayList<Person> getInElevator() {
		return inElevator;
	}
	
	public void setInElevator(Person persons) {
		inElevator.add(persons);
	}
	
	public Image getImg(int i) {
		return img[4];
	}

	public void setImg(int i) {
		img[4] = img[i];
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

	public int getD_y() {
		return d_y;
	}

	public void setD_y(int d_y) {
		this.d_y = d_y;
	}

}