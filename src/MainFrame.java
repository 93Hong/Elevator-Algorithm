import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MainFrame implements ActionListener {
	private JFrame mainFrame = new JFrame("새롬관 엘리베이터");
	private JFrame inputFrame = new JFrame("버튼");
	private JPanel floorPanel = new JPanel();

	private static final int max_floors = 8;
	private JButton[] floorBtn = new JButton[max_floors];

	ArrayList<Person> persons = new ArrayList<Person>();

	private boolean flag = true; //true : current, false : dest;
	private int currentfloor;
	private int destfloor;
	private int firstX = 200; // 첫번째 엘리베이터 x 좌표
	private int secondX = 400; // 두번째 엘리베이터 x 좌표
	boolean weekend = false; // 처음에는 주중으로

	private Elevator firstElevator = new Elevator(firstX);
	private Elevator secondElevator = new Elevator(secondX);

	private JLabel label = new JLabel("             현재 층 입력") ;
	private JButton[] inputBtn = new JButton[8];
	
	
	public MainFrame () {
		mainFrame.setLayout(null);// 레이아웃 설정
		//부모로부터의 상대위치, 
		mainFrame.setBounds(0, 0, 700 + 12, 400 + 38); // 절대위치 지정하려면 이넘이 포함되는 부모의 레이아웃이 널이여야함
		floorPanel.setLayout(new GridLayout(max_floors, 1));

		for(int i = max_floors - 1; i >= 0; i--){ // 왼쪽의 층 이미지
			if(i > 1){
				ImageIcon icon = new ImageIcon(i-1+".png");
				floorBtn[i] = new JButton(icon);
			}
			else if(i == 1){
				ImageIcon icon = new ImageIcon("b1.png");
				floorBtn[i] = new JButton(icon);
			}
			else{
				ImageIcon icon = new ImageIcon("b2.png");
				floorBtn[i] = new JButton(icon);
			}
			floorBtn[i].setSize(50, 50);
			floorPanel.add(floorBtn[i]);
		}
		floorPanel.setBounds(0, 0, 50, 400);
		mainFrame.add(floorPanel);

		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setVisible(true);

		////////////////////////////////////////////////////////////////////////////////////
		mainFrame.add(new LeftJpanel(persons, firstElevator, secondElevator)); // add main frame
		////////////////////////////////////////////////////////////////////////////////////

		//input frame 에 대한 설정.
		inputFrame.setLayout(null); //null 이니까 얘한테 포함되는 애들의 위치를 setBounds 를 써서 자유롭게 배치할수있다.
		// 텍스트 패널

		JPanel textPanel = new JPanel();
		textPanel.setLayout(new GridLayout(1, 1));
		textPanel.add(label);
		textPanel.setBounds(0, 0, 120 + 30, 50);
		inputFrame.add(textPanel);

		JPanel btnPanel = new JPanel();
		btnPanel.setLayout(new GridLayout(4, 2)); // 엘리베이터 버튼 생성
		for (int i = 0; i < max_floors; i++) {
			if (i == 0){
				ImageIcon icon = new ImageIcon("button_b2.png");
				inputBtn[i] = new JButton(icon);
			}
			else if (i == 1){
				ImageIcon icon = new ImageIcon("button_b1.png");
				inputBtn[i] = new JButton(icon);
			}
			else{
				ImageIcon icon = new ImageIcon("button_"+(i-1)+".png"); // 2 3 4 5 6 7
				inputBtn[i] = new JButton(icon);
			}
			inputBtn[i].addActionListener(this); // 버튼 누르면
			btnPanel.add(inputBtn[i]);
		}
		btnPanel.setBounds(0, 50, 148, 200);
		inputFrame.add(btnPanel);

		JPanel weekBtnPanel = new JPanel(); // 주중 주말 버튼 추가
		weekBtnPanel.setLayout(new GridLayout(1, 2));
		ImageIcon icon1 = new ImageIcon("week.png");
		JButton b1 = new JButton(icon1);
		ImageIcon icon2 = new ImageIcon("holiday.png");
		JButton b2 = new JButton(icon2);
		b1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				weekend = false;
			}
		});
		b2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				weekend = true;
			}
		});
		weekBtnPanel.add(b1);
		weekBtnPanel.add(b2);

		inputFrame.add(weekBtnPanel);
		weekBtnPanel.setBounds(0, 265, 148, 50);
		inputFrame.setResizable(false);

		inputFrame.setVisible(true);
		inputFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		inputFrame.setBounds(730, 0, 154, 240 + 38 + (65));
		
		/* 엘리베이터 인원 제한에 대한 인풋값
		persons.add(new Person(2, 4, 400));
		persons.add(new Person(2, 4, 400));
		persons.add(new Person(2, 4, 400));
		persons.add(new Person(2, 4, 400));
		persons.add(new Person(2, 4, 400));
		persons.add(new Person(2, 4, 400));
		persons.add(new Person(2, 4, 400));
		persons.add(new Person(2, 4, 400));
		
		
		persons.add(new Person(3, 6, 400));
		persons.add(new Person(3, 6, 400));
		persons.add(new Person(3, 6, 400));
		persons.add(new Person(3, 6, 400));
*/
		////////////////////////////////////////////////////////////////////////////////////
	}

	public static void main (String[] args) {
		new MainFrame();
	}

	// 버튼을 눌럿을 때 리스너, 처음에는 현재층을 입력 두번째는 목적지 층을 입력
	@Override
	public void actionPerformed(ActionEvent e/*이클레스 자체가 리스너니까! 어디서 듣고있다가 Event Object 'e'를 날려준다*/) {
		JButton tmpbtn = (JButton)e.getSource();
		String str1 = "             현재 층 입력";
		String str2 = "             목적지 입력";
		flag = !flag; //true : current, false : dest; 
		if (!flag) label.setText(str2);
		else label.setText(str1);
		if (tmpbtn == inputBtn[0]) {//y = 5가 1층      x, y (0 = 6층)
			if (!flag)
				currentfloor = 7;
			else 
				destfloor = 7;
		} else if (tmpbtn == inputBtn[1]) {
			if (!flag)
				currentfloor = 6;
			else 
				destfloor = 6;
		} else if (tmpbtn == inputBtn[2]) {//y = 5가 1층      x, y (0 = 6층)
			if (!flag)
				currentfloor = 5;
			else 
				destfloor = 5;
		} else if (tmpbtn == inputBtn[3]) {
			if (!flag)
				currentfloor = 4;
			else 
				destfloor = 4;
		} else if (tmpbtn == inputBtn[4]) {
			if (!flag)
				currentfloor = 3;
			else 
				destfloor = 3;
		} else if (tmpbtn == inputBtn[5]) {
			if (!flag)
				currentfloor = 2;
			else 
				destfloor = 2;
		} else if (tmpbtn == inputBtn[6]) {
			if (!flag)
				currentfloor = 1;
			else 
				destfloor = 1;
		} else if (tmpbtn == inputBtn[7]) {
			if (!flag)
				currentfloor = 0;
			else 
				destfloor = 0;
		}
		if (str1.equals(label.getText())) {
			if (currentfloor == destfloor)
				return;

			// '주말' 버튼 누르면 weekend 를 true 로, '평일' 버튼을 누르면 weekend 를 false 로
			if (weekend) {
				persons.add(new Person(currentfloor, destfloor, 200)); // <- 주말엔 엘베 하나만 움직이게 하기
			} else {
				int elevatorLocation = Xcoordinate (); // 어느 엘리베이터를 탈지 정함
				persons.add(new Person(currentfloor, destfloor, elevatorLocation));
			}
		}
	}

	// 사람에 대한 인풋이 들어오면 엘리베이터의 정보들을 보고 어느 엘리베이터를 탈지 결정해주는 알고리즘
	public int Xcoordinate () {
		int e_x = firstX;

		//------------------------------ 게단으로 가는 경우 --------------------------------------//
		if (!firstElevator.getUpDown().equals("") && !secondElevator.getUpDown().equals("")
				&& Math.abs(destfloor - currentfloor) == 1) {
			// 엘베 둘다 움직이고 있고 내가 갈 층이 1나차이 일때
			e_x = 550;
		}

		//-------------------------- 엘리베이터 둘다 멈춰있는 경우------------------------------------//
		else if (firstElevator.getUpDown().equals("") && secondElevator.getUpDown().equals("")) {
			if (Math.abs(firstElevator.getY() - currentfloor * 50) <= Math.abs(secondElevator.getY() - currentfloor * 50))
				e_x = firstX;
			else
				e_x = secondX;
		}

		//------------------------- 하나의 엘레베이터만 움직이고 있는 경우 --------------------------------//
		else if (firstElevator.getUpDown().equals("")) { // 첫번째 엘리베이터가 멈춰있는 경우
			if ((destfloor - currentfloor < 0 && secondElevator.getUpDown().equals("Up"))
					|| (destfloor - currentfloor > 0 && secondElevator.getUpDown().equals("Down"))) { // UP (방향성같고 위치까지 좋은경우)
				e_x = secondX;
			}
			else
				e_x = firstX;
		}
		else if (secondElevator.getUpDown().equals("")) { // 두번째 엘리베이터가 멈춰있는 경우
			if ((destfloor - currentfloor < 0 && firstElevator.getUpDown().equals("Up"))
					|| (destfloor - currentfloor > 0 && firstElevator.getUpDown().equals("Down"))) {// DOWN (방향성같고 위치까지 좋은경우)
				e_x = firstX;
			}
			else {
				e_x = secondX;
			}
		}

		//--------------------------- 모든 엘리베이터가 움직이고 있는경우 --------------------------------//
		else {
			double firstEfficiency = 1, secondEfficiency = 1;
			
			// 엘리베이터 안에 타고있는 사람을 비교
			if (firstElevator.getInElevator().size() >= secondElevator.getInElevator().size())
				firstEfficiency *= 0.8;
			else
				secondEfficiency *= 0.8;

			// 엘베가 꽉찬경우
			if (firstElevator.getInElevator().size() == 10)
				firstEfficiency *= 0.1;
			if (secondElevator.getInElevator().size() == 10)
				secondEfficiency *= 0.1;

			// 방향성 고려하기 case 4개

			// ---------------------- FIRST ELEVATOR -------------------------//
			// Case 1, 가장 꾸진거, 방향성이 같은데 위치가 개꾸진경우
			if ((destfloor - currentfloor > 0 && firstElevator.getUpDown().equals("Down") 
					&& currentfloor * 50 - firstElevator.getY() > 0)
					|| (destfloor - currentfloor < 0 && firstElevator.getUpDown().equals("Up") 
							&& currentfloor * 50 - firstElevator.getY() < 0))
				firstEfficiency *= 0.2;
			// Case 2, 두번째로 꾸짐, 단지 방향성만 다를 때
			else if ((destfloor - currentfloor > 0 && firstElevator.getUpDown().equals("Up")) 
					|| (destfloor - currentfloor < 0 && firstElevator.getUpDown().equals("Down")))
				firstEfficiency *= 0.5;

			// -------------------- SECOND ELEVATOR --------------------------//
			// Case 1, 가장 꾸진거, 방향성이 같은데 위치가 개꾸진경우
			if ((destfloor - currentfloor > 0 && secondElevator.getUpDown().equals("Down") 
					&& currentfloor * 50 - secondElevator.getY() > 0)
					|| (destfloor - currentfloor < 0 && secondElevator.getUpDown().equals("Up") 
							&& currentfloor * 50 - secondElevator.getY() < 0))
				secondEfficiency *= 0.2;
			// Case 2, 두번째로 꾸짐, 단지 방향성만 다를 때
			else if ((destfloor - currentfloor > 0 && secondElevator.getUpDown().equals("Up")) 
					|| (destfloor - currentfloor < 0 && secondElevator.getUpDown().equals("Down")))
				secondEfficiency *= 0.5;

			// 더 효율적인 엘리베이터를 선택함
			if (firstEfficiency >= secondEfficiency)
				e_x = firstX;
			else
				e_x = secondX;
		}
		return e_x;

	}
}