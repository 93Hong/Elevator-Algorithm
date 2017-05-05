import java.awt.Image;
import java.awt.Toolkit;
import java.util.Random;

public class Person {
	private Toolkit tk;
	private Image img[] = new Image[41]; // ����� �̹������� �����ϰ� ����
	private int imageindex;
	
	private static int count = 0;
	
	private int state = 0;
	/*
	����� state �� �����ϴ� ���� 
	0 = ���������͸� Ÿ���� �ɾ�� ��
	1 = ���������͸� Ż���� ��ٸ��� ��
	2 = ���������� Ÿ���ִ� ��
	3 = ������� �����ִ� ��
	*/
	
	// ����� x y ��ǥ, move thread �� ���ؼ� ���� ����Ǿ� �����̴� �� ó�� ���̰Բ� ��
	private int x = 0;
	private int y = 0;
	
	// ����� ������ x y ��ǥ
	private int d_x = 0; // ���� ������ �ϴ� �� ��ġ
	private int d_y = 0;
	
	// ����� Ÿ���� ���������� x y ��ǥ
	private int e_x = 0; // ���� Ÿ���ϴ� ������������ ��ġ
	private int e_y = 0;
	
	private int id; // ����鿡�� ���� id �� �ο�
	private String upDown = ""; // ������������ ���⼺�� ��Ÿ��,  Up, Down
	
	public Person(int departure, int destination, int elevatorLocation) { // �����, ������, ���������� x ��ǥ
		
		// ���������� ��ġ�� �������
		if (elevatorLocation == 400) { // �ι�° ����������
			this.x = 700; // �����ʿ��� ����� ������
			this.d_x = -50; // frame �� ����
			// ���ʿ��� ����� ���ͼ� ���������� ������� �Ϸ���
			
		} else if (elevatorLocation == 200) { // �ι�° ����
			this.x = -50; // ���ʿ��� ����� ������, ��ư �ڿ��� �����Բ�
			this.d_x = 700; // frame �� ��
			// �����ʿ��� ����� ���ͼ� �������� ������� �Ϸ���
			
		} else if (elevatorLocation == 550) { // ������� ���� ���
			this.x = 700; // ���ʿ��� ����� �����Ǽ� ����� ���� ���������� ����
			this.d_x = -50;
			state = 3; // ����� ���¸� ������� ���� �� ���� �ٲ�
			
		}
		
		this.e_x = elevatorLocation; // ����� ������ ����
		this.setId(count++); // ������ �����ϱ� ���ؼ�
		
		this.d_y = destination * 50; // ������ ��
		this.y = departure * 50; // ����� �����Ǵ� ��
		
		if (departure < destination) // ����� ���⼺ ������
			setUpDown("Down");
		else if (departure > destination)
			setUpDown("Up");
				
		tk = Toolkit.getDefaultToolkit();
		
		Random random = new Random(); // ��� �̹��� �ٲ��ֱ�
		imageindex = (random.nextInt(19));
		if(imageindex%2 == 1)
			imageindex--;////////////// �������� ��� �̹����� �־��ִ°�
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
