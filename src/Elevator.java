
import java.awt.Image;
import java.awt.Toolkit;
import java.util.ArrayList;

public class Elevator {
	// waiting elevator, �ش� ���������͸� ��ٸ��� �ִ� ������� ������
	private ArrayList<Person> ReadyElevator = new ArrayList<Person>();
	
	// in elevator, �ش� ���������Ϳ� Ÿ���ִ� ������� ���夷�ϰ� �ִ� ��
	private ArrayList<Person> inElevator = new ArrayList<Person>();
	
	private Toolkit tk;
	private Image img[] = new Image[5]; // �̹����� ����ִ� ����
	
	private String upDown = ""; // Up, Down, Stop, ""

	private int state = 0; // 0 = �¿췯 ������ (�� ���� ��� �¿�� �ȵ�), 1 = �������� �������� �� (�̶��� �¿��� ��), 2 = ��ġ �ڵ������϶�
	
	// ������������ x y ��ǥ, move thread �� ���ؼ� ���� ����Ǿ� �����̴� �� ó�� ���̰Բ� ��
	private int x = 0;
	private int y = 250;

	private int d_y = 250; // ����� �������� ���� y ��ǥ
	
	public Elevator (int location) { // main algorithm �� ���� ������ y ��ǥ�� �������� ����
		this.x = location; // ������������ x �� ��ǥ (���� ��)
		
		tk = Toolkit.getDefaultToolkit(); // �� ������ �̹�����
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