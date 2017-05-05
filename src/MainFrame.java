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
	private JFrame mainFrame = new JFrame("���Ұ� ����������");
	private JFrame inputFrame = new JFrame("��ư");
	private JPanel floorPanel = new JPanel();

	private static final int max_floors = 8;
	private JButton[] floorBtn = new JButton[max_floors];

	ArrayList<Person> persons = new ArrayList<Person>();

	private boolean flag = true; //true : current, false : dest;
	private int currentfloor;
	private int destfloor;
	private int firstX = 200; // ù��° ���������� x ��ǥ
	private int secondX = 400; // �ι�° ���������� x ��ǥ
	boolean weekend = false; // ó������ ��������

	private Elevator firstElevator = new Elevator(firstX);
	private Elevator secondElevator = new Elevator(secondX);

	private JLabel label = new JLabel("             ���� �� �Է�") ;
	private JButton[] inputBtn = new JButton[8];
	
	
	public MainFrame () {
		mainFrame.setLayout(null);// ���̾ƿ� ����
		//�θ�κ����� �����ġ, 
		mainFrame.setBounds(0, 0, 700 + 12, 400 + 38); // ������ġ �����Ϸ��� �̳��� ���ԵǴ� �θ��� ���̾ƿ��� ���̿�����
		floorPanel.setLayout(new GridLayout(max_floors, 1));

		for(int i = max_floors - 1; i >= 0; i--){ // ������ �� �̹���
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

		//input frame �� ���� ����.
		inputFrame.setLayout(null); //null �̴ϱ� ������ ���ԵǴ� �ֵ��� ��ġ�� setBounds �� �Ἥ �����Ӱ� ��ġ�Ҽ��ִ�.
		// �ؽ�Ʈ �г�

		JPanel textPanel = new JPanel();
		textPanel.setLayout(new GridLayout(1, 1));
		textPanel.add(label);
		textPanel.setBounds(0, 0, 120 + 30, 50);
		inputFrame.add(textPanel);

		JPanel btnPanel = new JPanel();
		btnPanel.setLayout(new GridLayout(4, 2)); // ���������� ��ư ����
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
			inputBtn[i].addActionListener(this); // ��ư ������
			btnPanel.add(inputBtn[i]);
		}
		btnPanel.setBounds(0, 50, 148, 200);
		inputFrame.add(btnPanel);

		JPanel weekBtnPanel = new JPanel(); // ���� �ָ� ��ư �߰�
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
		
		/* ���������� �ο� ���ѿ� ���� ��ǲ��
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

	// ��ư�� ������ �� ������, ó������ �������� �Է� �ι�°�� ������ ���� �Է�
	@Override
	public void actionPerformed(ActionEvent e/*��Ŭ���� ��ü�� �����ʴϱ�! ��� ����ִٰ� Event Object 'e'�� �����ش�*/) {
		JButton tmpbtn = (JButton)e.getSource();
		String str1 = "             ���� �� �Է�";
		String str2 = "             ������ �Է�";
		flag = !flag; //true : current, false : dest; 
		if (!flag) label.setText(str2);
		else label.setText(str1);
		if (tmpbtn == inputBtn[0]) {//y = 5�� 1��      x, y (0 = 6��)
			if (!flag)
				currentfloor = 7;
			else 
				destfloor = 7;
		} else if (tmpbtn == inputBtn[1]) {
			if (!flag)
				currentfloor = 6;
			else 
				destfloor = 6;
		} else if (tmpbtn == inputBtn[2]) {//y = 5�� 1��      x, y (0 = 6��)
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

			// '�ָ�' ��ư ������ weekend �� true ��, '����' ��ư�� ������ weekend �� false ��
			if (weekend) {
				persons.add(new Person(currentfloor, destfloor, 200)); // <- �ָ��� ���� �ϳ��� �����̰� �ϱ�
			} else {
				int elevatorLocation = Xcoordinate (); // ��� ���������͸� Ż�� ����
				persons.add(new Person(currentfloor, destfloor, elevatorLocation));
			}
		}
	}

	// ����� ���� ��ǲ�� ������ ������������ �������� ���� ��� ���������͸� Ż�� �������ִ� �˰���
	public int Xcoordinate () {
		int e_x = firstX;

		//------------------------------ �Դ����� ���� ��� --------------------------------------//
		if (!firstElevator.getUpDown().equals("") && !secondElevator.getUpDown().equals("")
				&& Math.abs(destfloor - currentfloor) == 1) {
			// ���� �Ѵ� �����̰� �ְ� ���� �� ���� 1������ �϶�
			e_x = 550;
		}

		//-------------------------- ���������� �Ѵ� �����ִ� ���------------------------------------//
		else if (firstElevator.getUpDown().equals("") && secondElevator.getUpDown().equals("")) {
			if (Math.abs(firstElevator.getY() - currentfloor * 50) <= Math.abs(secondElevator.getY() - currentfloor * 50))
				e_x = firstX;
			else
				e_x = secondX;
		}

		//------------------------- �ϳ��� ���������͸� �����̰� �ִ� ��� --------------------------------//
		else if (firstElevator.getUpDown().equals("")) { // ù��° ���������Ͱ� �����ִ� ���
			if ((destfloor - currentfloor < 0 && secondElevator.getUpDown().equals("Up"))
					|| (destfloor - currentfloor > 0 && secondElevator.getUpDown().equals("Down"))) { // UP (���⼺���� ��ġ���� �������)
				e_x = secondX;
			}
			else
				e_x = firstX;
		}
		else if (secondElevator.getUpDown().equals("")) { // �ι�° ���������Ͱ� �����ִ� ���
			if ((destfloor - currentfloor < 0 && firstElevator.getUpDown().equals("Up"))
					|| (destfloor - currentfloor > 0 && firstElevator.getUpDown().equals("Down"))) {// DOWN (���⼺���� ��ġ���� �������)
				e_x = firstX;
			}
			else {
				e_x = secondX;
			}
		}

		//--------------------------- ��� ���������Ͱ� �����̰� �ִ°�� --------------------------------//
		else {
			double firstEfficiency = 1, secondEfficiency = 1;
			
			// ���������� �ȿ� Ÿ���ִ� ����� ��
			if (firstElevator.getInElevator().size() >= secondElevator.getInElevator().size())
				firstEfficiency *= 0.8;
			else
				secondEfficiency *= 0.8;

			// ������ �������
			if (firstElevator.getInElevator().size() == 10)
				firstEfficiency *= 0.1;
			if (secondElevator.getInElevator().size() == 10)
				secondEfficiency *= 0.1;

			// ���⼺ ����ϱ� case 4��

			// ---------------------- FIRST ELEVATOR -------------------------//
			// Case 1, ���� ������, ���⼺�� ������ ��ġ�� ���������
			if ((destfloor - currentfloor > 0 && firstElevator.getUpDown().equals("Down") 
					&& currentfloor * 50 - firstElevator.getY() > 0)
					|| (destfloor - currentfloor < 0 && firstElevator.getUpDown().equals("Up") 
							&& currentfloor * 50 - firstElevator.getY() < 0))
				firstEfficiency *= 0.2;
			// Case 2, �ι�°�� ����, ���� ���⼺�� �ٸ� ��
			else if ((destfloor - currentfloor > 0 && firstElevator.getUpDown().equals("Up")) 
					|| (destfloor - currentfloor < 0 && firstElevator.getUpDown().equals("Down")))
				firstEfficiency *= 0.5;

			// -------------------- SECOND ELEVATOR --------------------------//
			// Case 1, ���� ������, ���⼺�� ������ ��ġ�� ���������
			if ((destfloor - currentfloor > 0 && secondElevator.getUpDown().equals("Down") 
					&& currentfloor * 50 - secondElevator.getY() > 0)
					|| (destfloor - currentfloor < 0 && secondElevator.getUpDown().equals("Up") 
							&& currentfloor * 50 - secondElevator.getY() < 0))
				secondEfficiency *= 0.2;
			// Case 2, �ι�°�� ����, ���� ���⼺�� �ٸ� ��
			else if ((destfloor - currentfloor > 0 && secondElevator.getUpDown().equals("Up")) 
					|| (destfloor - currentfloor < 0 && secondElevator.getUpDown().equals("Down")))
				secondEfficiency *= 0.5;

			// �� ȿ������ ���������͸� ������
			if (firstEfficiency >= secondEfficiency)
				e_x = firstX;
			else
				e_x = secondX;
		}
		return e_x;

	}
}