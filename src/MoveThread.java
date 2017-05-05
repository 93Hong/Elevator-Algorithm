import java.awt.Font;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JLabel;

public class MoveThread extends Thread {
	private ArrayList<Person> persons;
	private Elevator firstElevator; // ù�� �� ����������
	private Elevator secondElevator; //  �ι� ° ����������
	private int max_people = 10; // ����
	private JLabel label = new JLabel("0"); // �ʱ� �ð�
	private int count = 0;
	LeftJpanel jp;
	
	private int firstElevatorDoor = 0; // ���� ������ ���� ����
	private int secondElevatorDoor = 0; // ���� ������ ���� ����
	
	public MoveThread(ArrayList<Person> persons, Elevator firstElevator,
			Elevator secondElevator, LeftJpanel jp) {
		this.persons = persons;
		this.firstElevator = firstElevator;
		this.secondElevator = secondElevator;
		this.jp = jp;
		
		jp.add(label);
		label.setFont(new Font("Default", Font.BOLD, 20));
	}

	public void run() {
		while (true) {
			try {
				Thread.sleep(10);

				//------------------------------ �ð��� ���� ���������� ��ġ �ٲ��ִ� �ڵ� ���� ----------------------------//
				count++; // thread ���� Ƚ��
				if (count == 1000) { // ���� -> ���������͸� 1������ �̵��õf
					// �����ִ� ���������͸�
					if (firstElevator.getUpDown().equals("")) {
						firstElevator.setState(2);
						firstElevator.setD_y(250);
					}
					if (secondElevator.getUpDown().equals("")) {
						secondElevator.setState(2);
						secondElevator.setD_y(250);
					}
					// ���� �ָ� or ��� ��� �ð��� etc..
				}
				else if (count == 2000) { // ���� -> ���������͸� 5������ �̵���Ŵ
					if (firstElevator.getUpDown().equals("")) {
						firstElevator.setState(2);
						firstElevator.setD_y(50);
					}
					if (secondElevator.getUpDown().equals("")) {
						secondElevator.setState(2);
						secondElevator.setD_y(50);
					}
					// ���� �ָ� or ��� ��� �ð��� etc..
					count = 0;
				}
				// state �� 2�̸� (= �ڵ�ȭ)
				if (firstElevator.getState() == 2) { // ���� ���Ŀ� ���� ���������� ��ġ�� �ٲ��ִ� �ڵ�
					if (firstElevator.getY() < firstElevator.getD_y())
						firstElevator.setY(firstElevator.getY() + 1);
					else if (firstElevator.getY() > firstElevator.getD_y())
						firstElevator.setY(firstElevator.getY() - 1);
					else
						firstElevator.setState(0);
				}
				if (secondElevator.getState() == 2) {
					if (secondElevator.getY() < secondElevator.getD_y())
						secondElevator.setY(secondElevator.getY() + 1);
					else if (secondElevator.getY() > secondElevator.getD_y())
						secondElevator.setY(secondElevator.getY() - 1);
					else
						secondElevator.setState(0);
				}
				// count ���� �ٲ��ִ°�
				label.setText("<html><font color='red'>" + count + "</font></html>");
				
				//------------------------------ �ð��� ���� ���������� ��ġ �ٲ��ִ� �ڵ� �� ----------------------------//
				
				//---------------------------- ��� �����̴°� ----------------------------------------//
				
				for (int i = 0; i < persons.size(); i++) { // ����� ������ �޼��ϸ� ������
					if (persons.get(i).getX() == persons.get(i).getD_x())
						persons.remove(i);
				}
				
				for (int i = 0; i < persons.size(); i++) {// ����� ������ �̵�
					// �����ϰ� ����� �̹����� ����
					Random random = new Random();
					persons.get(i).setImg((random.nextInt(2)));

					//------------------------ ��ٸ� Ÿ�� ���� ����� ------------------------//
					if (persons.get(i).getState() == 3 // �����ġ���� ���� �����
							&& persons.get(i).getX() > persons.get(i).getE_x()) {
						persons.get(i).setX(persons.get(i).getX() - 1);
					}
					else if (persons.get(i).getState() == 3 // ������������ ������ؼ� ���� �����
							&& persons.get(i).getX() == persons.get(i).getE_x()) {
						// ������� ���� �̹���
						Random randomsadari = new Random();
						persons.get(i).setImg((randomsadari.nextInt(2)) + 20);
						
						// ����� ����� ��ġ�� �̵���Ŵ
						if (persons.get(i).getY() < persons.get(i).getD_y())
							persons.get(i).setY(persons.get(i).getY() + 1);
						else if (persons.get(i).getY() > persons.get(i).getD_y())
							persons.get(i).setY(persons.get(i).getY() - 1);
						// ����� �� �þƿ����� �ɾ�� ��
						else {
							persons.get(i).setE_x(persons.get(i).getD_x());
							persons.get(i).setState(0); // �ɾ�� ������ state �� �ٲ�
						}
					}
					
					//------------------------- ������ Ÿ�� ����� --------------------------//
					else if (persons.get(i).getX() < persons.get(i).getE_x()) {// ��� �����̱� (����������)
						persons.get(i).setX(persons.get(i).getX() + 2);
						persons.get(i).setState(0); // state 0 = �ɾ����

					}
					else if (persons.get(i).getX() > persons.get(i).getE_x()) {// ��� �����̱� (��������)
						persons.get(i).setX(persons.get(i).getX() - 2);
						persons.get(i).setState(0); // state 0 = �ɾ����

					}
					
					// ���������� �����̴� �ڵ�
					//-------------------------------FIRST ELEVATOR-----------------------------------------
					else if (persons.get(i).getX() == persons.get(i).getE_x() // ����� ���� Ÿ�°��� ����
							&& persons.get(i).getState() == 0 // �� ����� �ȴ� �� �̿���
							&& firstElevator.getX() == persons.get(i).getX()) {// ����� ���� Ż ���� ������

						persons.get(i).setState(1); // ����� ���¸� ���� ��ٸ��� �� ���� �ٲ�
						firstElevator.setReadyElevator(persons.get(i));// ����� ready queue �� ��

					}
					//-------------------------------SECOND ELEVATOR-----------------------------------------
					else if (persons.get(i).getX() == persons.get(i).getE_x()
							&& persons.get(i).getState() == 0
							&& secondElevator.getX() == persons.get(i).getX()) {// ����� ���� Ż���� ����

						persons.get(i).setState(1);
						secondElevator.setReadyElevator(persons.get(i));// ����� ready queue �� ��
					}
				}
								
				/////////////////////////////////////////////////////////////////
				// ���ǿ� ���� ����� �׿췯���� ���������� ���..
				/////////////////////////////////////////////////////////////////
				
				//-------------------------------FIRST ELEVATOR-----------------------------------------
				if (firstElevator.getInElevator().isEmpty() == false
						|| firstElevator.getReadyElevator().isEmpty() == false) {
					int check = 0;
					// --------------���߿� ��� �¿��---------------------//
					if (firstElevator.getY() % 50 == 0) { // �� �� �������� �� (�� ���� 50�̶�)
						for (int i = 0; i < firstElevator.getReadyElevator().size(); i++) {
							if (!firstElevator.getUpDown().equals(firstElevator.getReadyElevator().get(i).getUpDown()))
								continue;
							// �� ������ Ż �� �ִ»�� ��� ī��Ʈ
							if (firstElevator.getY() == firstElevator
									.getReadyElevator().get(i).getY()) {
								check++;
								break;
							}
						}
						if (check != 0) {// -----------------�� ���� �¿� �� �ִ� �갡 ������--------------------//
							check = 0;
							int totalDeleteCount = 0;// �� ������ �¿� �� �ִ� �� ����
							// ��� �긦 �¿� (size �� �����ϴٸ�)
							for (int i = 0; i < firstElevator.getReadyElevator()
									.size(); i++) {
								if (firstElevator.getY() == firstElevator.getReadyElevator().get(i).getY()) {
									totalDeleteCount++;// remove ������ index �� ������� �װ� ��������
								}
							}
		
							int count = 0;
							while (totalDeleteCount != count // remove ������ index �� ������� �װ� ��������
									&& firstElevator.getInElevator().size() < max_people) { // ���� ó��
								for (int i = 0; i < firstElevator.getReadyElevator().size(); i++) { // ����ť ����� ����
	
									Person p = firstElevator.getReadyElevator().get(i);
									if (firstElevator.getY() == p.getY()) {
	
										p.setState(2); // ���������Ϳ� ź ���·� �ٲ�
										firstElevator.setInElevator(p);// ����� ���������Ϳ� Ž
	
										int delete_id = p.getId();
										firstElevator.getReadyElevator().remove(i);
										for (int j = 0; j < persons.size(); j++)
											if (persons.get(j).getId() == delete_id) {
												persons.remove(j);
												break;
											}
										count++;
										break;
									}
								}
	
							}
	
	
							if (firstElevator.getUpDown().equals("Up"))
								bubbleSortUp (firstElevator.getInElevator());// D_y ū�� ... ������ ������ ���ĵ�
							else
								bubbleSortDown(firstElevator.getInElevator());
	
							firstElevator.setUpDown("Stop");
							firstElevator.setState(1);
						}
					}
				}
				firstOpenDoor(firstElevator); // ���������� �� ����
				/////////////////////////////////////////////////////////////////////////////
				
				
				////////--------------------------------��� �����ַ� ����------------------------------------////////
				
				if (firstElevator.getInElevator().isEmpty() == false // ������ ź����� �Ѹ��̶� ������
						&& !firstElevator.getUpDown().equals("Stop")) { // �׸��� ������°� �ƴϸ� (�̰� �����°� ������)
					
					if (firstElevator.getY() < firstElevator.getInElevator()// ��� �������� ��������
							.get(0).getD_y()) {
						firstElevator.setY(firstElevator.getY() + 1);
						
						firstElevator.setUpDown("Down"); // ������ �������� ��
						firstElevator.setState(0);//////////////// ���������Ͱ� �����̴� ��

					} else if (firstElevator.getY() > firstElevator ///////////// ��� �������� �ö󰡴�
							.getInElevator().get(0).getD_y()) {
						firstElevator.setY(firstElevator.getY() - 1);
						
						firstElevator.setUpDown("Up");
						firstElevator.setState(0);//////////////// ���������Ͱ� �����̴� ��

					} else if (firstElevator.getY() == firstElevator ///////////// �����ؼ� ����� ������ (����� ���� ��)
							.getInElevator().get(0).getD_y()
							&& firstElevator.getInElevator().get(0).getState() == 2) { // ��� ���°� ���� Ÿ���ִ� ���̸�
						
						int totalDeleteCount = 0;// �� ������ �¿� �� �ִ� �� ����
						for (int i = 0; i < firstElevator.getInElevator().size(); i++) {
							if (firstElevator.getY() == firstElevator.getInElevator().get(i).getD_y()) {
								totalDeleteCount ++;// remove ������ index �� ������� �װ� ��������
							}
						}
						
						int count = 0;
						while (totalDeleteCount != count) {// remove ������ index �� ������� �װ� ��������
							for (int i = 0; i < firstElevator.getInElevator().size(); i++) { // ����ť ����� ����
								
								Person p = firstElevator.getInElevator().get(i); // �����ȿ� �ִ� �ѵ��� �ε���
								int currentFloor = firstElevator.getY(); // ������������ y ��ǥ, �׷��ϱ� ���� �� ��ġ
								if (currentFloor == p.getD_y()) {
									
									p.setState(3); // ����� ������ ���� �����ߴٰ� �ٲ�
									
									persons.add(p); // ��� �׷��ַ���
									p.setE_x(p.getD_x()); // �������� ������ ���� ������ ������ �ٲ�
									p.setY(currentFloor);
									firstElevator.getInElevator().remove(i); // ��� �������ϱ� '���� �ȿ� ��� ť'���� ����
									count++;
								}
								
							}
						}
						
						firstElevator.setState(1); // ������ �������� �������� ��
						firstElevator.setUpDown("Stop");

					}
					
				}
				
				////////--------------------------------�¿췯 ����------------------------------------////////
				
				// 0 = �¿췯 ������ (�� ���� ��� �¿�� �ȵ�), 1 = �������� �������� �� (�̶��� �¿��� ��) <- ������ ����
				else if (firstElevator.getInElevator().size() < max_people // �ο�����
						&& firstElevator.getReadyElevator().isEmpty() == false // ����Ÿ���� ��ٸ��� �� ������
						&& !firstElevator.getUpDown().equals("Stop")) { // �׸��� stop �ƴϸ�
					if (firstElevator.getY() < firstElevator.getReadyElevator()// ��� �¿���� ��������
							.get(0).getY()) {
						firstElevator.setY(firstElevator.getY() + 1);
						
						firstElevator.setUpDown("Down");
						firstElevator.setState(0);//////////////// ���������Ͱ� �����̴� ��

					} else if (firstElevator.getY() > firstElevator ///////////// ��� �¿���� �ö󰡴�
							.getReadyElevator().get(0).getY()) {
						firstElevator.setY(firstElevator.getY() - 1);
						
						firstElevator.setUpDown("Up");
						firstElevator.setState(0);//////////////// ���������Ͱ� �����̴� ��

					} else if (firstElevator.getY() == firstElevator ///////////// ��ٸ��� ���� �¿��
							.getReadyElevator().get(0).getY()
							&& firstElevator.getReadyElevator().get(0).getState() == 1) {
						
						int totalDeleteCount = 0;// �� ������ �¿� �� �ִ� �� ����
						for (int i = 0; i < firstElevator.getReadyElevator().size(); i++) {
							if (firstElevator.getY() == firstElevator.getReadyElevator().get(i).getY()) {
								totalDeleteCount ++;// remove ������ index �� ������� �װ� ��������
							}
						}
						
						int count = 0; // index �� �޶����°� �ذ��Ϸ���
						int peoplein = 0; // ���� ������ ź ����� ���� ī��Ʈ��
						
						while (totalDeleteCount != count // remove ������ index �� ������� �װ� ��������
								&& peoplein <= max_people) { // �ο����� �ҷ���
							for (int i = 0; i < firstElevator.getReadyElevator().size(); i++) { // ����ť ����� ����
								
								Person p = firstElevator.getReadyElevator().get(i);
								if (firstElevator.getY() == p.getY()) {
									
									p.setState(2); // ���������Ϳ� ź ���·� �ٲ�
									firstElevator.setInElevator(p);// ����� ���������Ϳ� Ž
									peoplein++; // ����� Ż������ ����
									int delete_id = p.getId();// ready ť i �� persons i �� �޶�
									firstElevator.getReadyElevator().remove(i);
									
									for (int j = 0; j < persons.size(); j++) // ���� ����ť�� �´� �۽� ����
										if (persons.get(j).getId() == delete_id) {
											persons.remove(j);
											break;
										}
									count++;
									break;
								}
							}
							
						}
						
						// ���������� ���¸� �ٽ� �ٲ���
						if (firstElevator.getInElevator().get(0).getD_y() < firstElevator.getY()) {
							firstElevator.setUpDown("Up");
							bubbleSortUp (firstElevator.getInElevator());
						} else {
							firstElevator.setUpDown("Down");
							bubbleSortDown (firstElevator.getInElevator());
						}
						
						firstElevator.setUpDown("Stop");
						firstElevator.setState(1);
					}

				}
				firstOpenDoor(firstElevator);
				
				
				
				//-------------------------------SECOND ELEVATOR-----------------------------------------//
				// first elevator �� �ڵ� ����, ���� firstElevator -> secondElevator
				
				// ///////////////////////////////////////////////////////////////
				if (secondElevator.getInElevator().isEmpty() == false
						|| secondElevator.getReadyElevator().isEmpty() == false) {
					int check = 0;
					// --------------���߿� ��� �¿��---------------------//
					if (secondElevator.getY() % 50 == 0) {
						for (int i = 0; i < secondElevator.getReadyElevator()
								.size(); i++) {
							if (!secondElevator.getUpDown().equals(
									secondElevator.getReadyElevator().get(i)
											.getUpDown()))
								continue;
							if (secondElevator.getY() == secondElevator
									.getReadyElevator().get(i).getY()) {
								check++;
								break;
							}
						}
						if (check != 0) {// -----------------�� ���� �¿� �� �ִ� �갡 ������--------------------//
							check = 0;
							int totalDeleteCount = 0;// �� ������ �¿� �� �ִ� �� ����
							for (int i = 0; i < secondElevator
									.getReadyElevator().size(); i++) {
								if (secondElevator.getY() == secondElevator
										.getReadyElevator().get(i).getY()) {
									totalDeleteCount++;// remove ������ index �� ������� �װ� ��������
								}
							}

							int count = 0;
							while (totalDeleteCount != count
									&& secondElevator.getInElevator().size() < max_people) {// remove ������ index �� ������� �װ� ��������
								for (int i = 0; i < secondElevator
										.getReadyElevator().size(); i++) { // ����ť ����� ����

									Person p = secondElevator.getReadyElevator().get(i);
									if (secondElevator.getY() == p.getY()) {

										p.setState(2); // ���������Ϳ� ź ���·� �ٲ�
										secondElevator.setInElevator(p);// ����� ���������Ϳ� Ž

										int delete_id = p.getId();
										secondElevator.getReadyElevator()
												.remove(i);
										for (int j = 0; j < persons.size(); j++)
											if (persons.get(j).getId() == delete_id) {
												persons.remove(j);
												break;
											}
										count++;
										break;
									}
								}

							}

							if (secondElevator.getUpDown().equals("Up"))
								bubbleSortUp(secondElevator.getInElevator());// D_y ū�� ... ������ ������ ���ĵ�
							else
								bubbleSortDown(secondElevator.getInElevator());

							secondElevator.setUpDown("Stop");
							secondElevator.setState(1);
						}
					}
				}
				secondOpenDoor(secondElevator);
				// ///////////////////////////////////////////////////////////////////////////

				// //////--------------------------------������ ����------------------------------------////////

				if (secondElevator.getInElevator().isEmpty() == false // ������ ź����� �Ѹ��̶� ������
						&& !secondElevator.getUpDown().equals("Stop")) { // �׸��� ������°� �ƴϸ� (�̰� �����°� ������)

					if (secondElevator.getY() < secondElevator.getInElevator()// ��� �������� ��������
							.get(0).getD_y()) {
						secondElevator.setY(secondElevator.getY() + 1);

						secondElevator.setUpDown("Down"); // ������ �������� ��
						secondElevator.setState(0);// ////////////// ���������Ͱ� �����̴� ��

					} else if (secondElevator.getY() > secondElevator // /////////// ��� �������� �ö󰡴�
							.getInElevator().get(0).getD_y()) {
						secondElevator.setY(secondElevator.getY() - 1);

						secondElevator.setUpDown("Up");
						secondElevator.setState(0);// ////////////// ���������Ͱ� �����̴� ��

					} else if (secondElevator.getY() == secondElevator // /////////// �����ؼ� ����� ������ (����� ���� ��)
							.getInElevator().get(0).getD_y()
							&& secondElevator.getInElevator().get(0).getState() == 2) { // ��� ���°� ���� Ÿ���ִ� ���̸�

						int totalDeleteCount = 0;// �� ������ �¿� �� �ִ� �� ����
						for (int i = 0; i < secondElevator.getInElevator()
								.size(); i++) {
							if (secondElevator.getY() == secondElevator
									.getInElevator().get(i).getD_y()) {
								totalDeleteCount++;// remove ������ index �� ������� �װ� ��������
							}
						}

						int count = 0;
						while (totalDeleteCount != count) {// remove ������ index �� ������� �װ� ��������
							for (int i = 0; i < secondElevator.getInElevator()
									.size(); i++) { // ����ť ����� ����

								Person p = secondElevator.getInElevator().get(i); // �����ȿ� �ִ� �ѵ��� �ε���
								int currentFloor = secondElevator.getY(); // ������������ y ��ǥ, �׷��ϱ� ���� �� ��ġ
								if (currentFloor == p.getD_y()) {

									p.setState(3); // ����� ������ ���� �����ߴٰ� �ٲ�

									persons.add(p); // ��� �׷��ַ���
									p.setE_x(p.getD_x()); // �������� ������ ���� ������ ������ �ٲ�
									p.setY(currentFloor);
									secondElevator.getInElevator().remove(i); // ��� �������ϱ� '���� �ȿ� ��� ť'���� ����
									count++;
								}

							}
						}

						secondElevator.setState(1); // ������ �������� �������� ��
						secondElevator.setUpDown("Stop");
					}

				}

				// //////--------------------------------�¿췯  ����------------------------------------////////

				// 0 = �¿췯 ������ (�� ���� ��� �¿�� �ȵ�), 1 = �������� �������� �� (�̶��� �¿��� ��) <- ������ ����
				else if (secondElevator.getInElevator().size() < max_people
						&& secondElevator.getReadyElevator().isEmpty() == false // ����Ÿ���� ��ٸ��� �� ������
						&& !secondElevator.getUpDown().equals("Stop")) { // �׸��� stop �ƴϸ�

					if (secondElevator.getY() < secondElevator.getReadyElevator()// ��� �¿���� ��������
							.get(0).getY()) {
						secondElevator.setY(secondElevator.getY() + 1);

						secondElevator.setUpDown("Down");
						secondElevator.setState(0);// ////////////// ���������Ͱ� �����̴� ��

					} else if (secondElevator.getY() > secondElevator // /////////// ��� �¿����  �ö󰡴�
							.getReadyElevator().get(0).getY()) {
						secondElevator.setY(secondElevator.getY() - 1);

						secondElevator.setUpDown("Up");
						secondElevator.setState(0);// ////////////// ���������Ͱ� �����̴� ��

					} else if (secondElevator.getY() == secondElevator // /////////// ��ٸ��� ���� �¿��
							.getReadyElevator().get(0).getY()
							&& secondElevator.getReadyElevator().get(0).getState() == 1) {

						int totalDeleteCount = 0;// �� ������ �¿� �� �ִ� �� ����
						for (int i = 0; i < secondElevator.getReadyElevator()
								.size(); i++) {
							if (secondElevator.getY() == secondElevator
									.getReadyElevator().get(i).getY()) {
								totalDeleteCount++;// remove ������ index �� ������� �װ� ��������
							}
						}

						int count = 0;
						int peoplein = 0;
						
						while (totalDeleteCount != count && peoplein <= max_people) {// remove ������ index �� ������� �װ� ��������
							for (int i = 0; i < secondElevator
									.getReadyElevator().size(); i++) { // ����ť ����� ����

								Person p = secondElevator.getReadyElevator()
										.get(i);
								if (secondElevator.getY() == p.getY()) {

									p.setState(2); // ���������Ϳ� ź ���·� �ٲ�
									secondElevator.setInElevator(p);// ����� ���������Ϳ� Ž
									peoplein++;

									int delete_id = p.getId();// ready ť i �� persons i �� �޶�
									secondElevator.getReadyElevator().remove(i);
									for (int j = 0; j < persons.size(); j++)
										// ���� ����ť�� �´� �۽� ����
										if (persons.get(j).getId() == delete_id) {
											persons.remove(j);
											break;
										}
									count++;
									break;
								}
							}

						}

						if (secondElevator.getInElevator().get(0).getD_y() < secondElevator
								.getY()) {
							secondElevator.setUpDown("Up");
							bubbleSortUp(secondElevator.getInElevator());
						} else {
							secondElevator.setUpDown("Down");
							bubbleSortDown(secondElevator.getInElevator());
						}

						secondElevator.setUpDown("Stop");
						secondElevator.setState(1);

					}

				}
				secondOpenDoor(secondElevator);
				
				
				/////////////////////////////////////////////////////////////////////////////////////////////
				
				jp.repaint();// jp �� ���� ��� �� �׷���

			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	public void firstOpenDoor (Elevator elevator) {
		if (elevator.getUpDown().equals("Stop")) {// ���� ������
			firstElevatorDoor++;
			if (firstElevatorDoor == 10)
				elevator.setImg(1);
			else if (firstElevatorDoor == 20)
				elevator.setImg(2);
			else if (firstElevatorDoor == 30)
				elevator.setImg(3);
			else if (firstElevatorDoor == 40)
				elevator.setImg(4);
			else if (firstElevatorDoor == 50)
				elevator.setImg(3);
			else if (firstElevatorDoor == 60)
				elevator.setImg(2);
			else if (firstElevatorDoor == 70)
				elevator.setImg(1);
			else if (firstElevatorDoor == 80) {
				elevator.setImg(0);
				firstElevatorDoor = 0;
				elevator.setUpDown(""); // ���������� ���� �ʱ�ȭ
			}
			
		}
	}
	public void secondOpenDoor (Elevator elevator) {
		if (elevator.getUpDown().equals("Stop")) {// ���� ������
			secondElevatorDoor++;
			if (secondElevatorDoor == 10)
				elevator.setImg(1);
			else if (secondElevatorDoor == 20)
				elevator.setImg(2);
			else if (secondElevatorDoor == 30)
				elevator.setImg(3);
			else if (secondElevatorDoor == 40)
				elevator.setImg(4);
			else if (secondElevatorDoor == 50)
				elevator.setImg(3);
			else if (secondElevatorDoor == 60)
				elevator.setImg(2);
			else if (secondElevatorDoor == 70)
				elevator.setImg(1);
			else if (secondElevatorDoor == 80) {
				elevator.setImg(0);
				secondElevatorDoor = 0;
				elevator.setUpDown(""); // ���������� ���� �ʱ�ȭ
			}
			
		}
	}
	// in elevator array list �� bubble sort �� ���� (���� ����� ������ ������)
	public void bubbleSortUp(ArrayList<Person> arr) { // ū�� ~ ���������� ����
	      boolean swapped = true;
	      int j = 0;
	      Person tmp;
	      while (swapped) {
	            swapped = false;
	            j++;
	            for (int i = 0; i < arr.size() - j; i++) {                                       
	                  if (arr.get(i).getD_y() < arr.get(i+1).getD_y()) {                          
	                        tmp = arr.get(i);
	                        arr.set(i, arr.get(i+1));
	                        arr.set(i+1, tmp);
	                        swapped = true;
	                  }
	            }                
	      }
	}
	public void bubbleSortDown(ArrayList<Person> arr) { // ������ ~ ū������ ����
	      boolean swapped = true;
	      int j = 0;
	      Person tmp;
	      while (swapped) {
	            swapped = false;
	            j++;
	            for (int i = 0; i < arr.size() - j; i++) {                                       
	                  if (arr.get(i).getD_y() > arr.get(i+1).getD_y()) {                          
	                        tmp = arr.get(i);
	                        arr.set(i, arr.get(i+1));
	                        arr.set(i+1, tmp);
	                        swapped = true;
	                  }
	            }                
	      }
	}
}