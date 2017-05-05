import java.awt.Font;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JLabel;

public class MoveThread extends Thread {
	private ArrayList<Person> persons;
	private Elevator firstElevator; // 첫번 쨰 엘리베이터
	private Elevator secondElevator; //  두번 째 엘리베이터
	private int max_people = 10; // 정원
	private JLabel label = new JLabel("0"); // 초기 시간
	private int count = 0;
	LeftJpanel jp;
	
	private int firstElevatorDoor = 0; // 엘베 문열기 위한 변수
	private int secondElevatorDoor = 0; // 엘베 문열기 위한 변수
	
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

				//------------------------------ 시간대 별로 엘리베이터 위치 바꿔주는 코드 시작 ----------------------------//
				count++; // thread 도는 횟수
				if (count == 1000) { // 오전 -> 엘리베이터를 1층으로 이동시킼
					// 멈춰있는 엘리베이터만
					if (firstElevator.getUpDown().equals("")) {
						firstElevator.setState(2);
						firstElevator.setD_y(250);
					}
					if (secondElevator.getUpDown().equals("")) {
						secondElevator.setState(2);
						secondElevator.setD_y(250);
					}
					// 주중 주말 or 퇴근 출근 시간대 etc..
				}
				else if (count == 2000) { // 오후 -> 엘리베이터를 5층으로 이동시킴
					if (firstElevator.getUpDown().equals("")) {
						firstElevator.setState(2);
						firstElevator.setD_y(50);
					}
					if (secondElevator.getUpDown().equals("")) {
						secondElevator.setState(2);
						secondElevator.setD_y(50);
					}
					// 주중 주말 or 퇴근 출근 시간대 etc..
					count = 0;
				}
				// state 가 2이면 (= 자동화)
				if (firstElevator.getState() == 2) { // 오전 오후에 따라 엘리베이터 위치를 바꿔주는 코드
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
				// count 색깔 바꿔주는거
				label.setText("<html><font color='red'>" + count + "</font></html>");
				
				//------------------------------ 시간대 별로 엘리베이터 위치 바꿔주는 코드 끝 ----------------------------//
				
				//---------------------------- 사람 움직이는거 ----------------------------------------//
				
				for (int i = 0; i < persons.size(); i++) { // 사람이 목적을 달성하면 지워줌
					if (persons.get(i).getX() == persons.get(i).getD_x())
						persons.remove(i);
				}
				
				for (int i = 0; i < persons.size(); i++) {// 사람이 엘베로 이동
					// 랜덤하게 사람의 이미지를 얻어옴
					Random random = new Random();
					persons.get(i).setImg((random.nextInt(2)));

					//------------------------ 사다리 타고 가는 사람들 ------------------------//
					if (persons.get(i).getState() == 3 // 계단위치까지 가는 사람들
							&& persons.get(i).getX() > persons.get(i).getE_x()) {
						persons.get(i).setX(persons.get(i).getX() - 1);
					}
					else if (persons.get(i).getState() == 3 // 목적지층으로 계단통해서 가는 사람들
							&& persons.get(i).getX() == persons.get(i).getE_x()) {
						// 계단으로 가는 이미지
						Random randomsadari = new Random();
						persons.get(i).setImg((randomsadari.nextInt(2)) + 20);
						
						// 사람을 계단의 위치로 이동시킴
						if (persons.get(i).getY() < persons.get(i).getD_y())
							persons.get(i).setY(persons.get(i).getY() + 1);
						else if (persons.get(i).getY() > persons.get(i).getD_y())
							persons.get(i).setY(persons.get(i).getY() - 1);
						// 계단을 다 올아왓으면 걸어가게 함
						else {
							persons.get(i).setE_x(persons.get(i).getD_x());
							persons.get(i).setState(0); // 걸어가는 중으로 state 를 바꿈
						}
					}
					
					//------------------------- 엘베를 타는 사람들 --------------------------//
					else if (persons.get(i).getX() < persons.get(i).getE_x()) {// 사람 움직이기 (오른쪽으로)
						persons.get(i).setX(persons.get(i).getX() + 2);
						persons.get(i).setState(0); // state 0 = 걸어가는중

					}
					else if (persons.get(i).getX() > persons.get(i).getE_x()) {// 사람 움직이기 (인쪽으로)
						persons.get(i).setX(persons.get(i).getX() - 2);
						persons.get(i).setState(0); // state 0 = 걸어가는중

					}
					
					// 엘리베이터 움직이는 코드
					//-------------------------------FIRST ELEVATOR-----------------------------------------
					else if (persons.get(i).getX() == persons.get(i).getE_x() // 사람이 엘베 타는곳에 도착
							&& persons.get(i).getState() == 0 // 그 사람이 걷는 중 이였음
							&& firstElevator.getX() == persons.get(i).getX()) {// 사람이 엘베 탈 곳에 있으면

						persons.get(i).setState(1); // 사람의 상태를 엘베 기다리는 중 으로 바꿈
						firstElevator.setReadyElevator(persons.get(i));// 사람이 ready queue 에 들어감

					}
					//-------------------------------SECOND ELEVATOR-----------------------------------------
					else if (persons.get(i).getX() == persons.get(i).getE_x()
							&& persons.get(i).getState() == 0
							&& secondElevator.getX() == persons.get(i).getX()) {// 사람이 엘베 탈곳에 오면

						persons.get(i).setState(1);
						secondElevator.setReadyElevator(persons.get(i));// 사람이 ready queue 에 들어감
					}
				}
								
				/////////////////////////////////////////////////////////////////
				// 조건에 따라 사람을 테우러갈지 내리러갈지 등등..
				/////////////////////////////////////////////////////////////////
				
				//-------------------------------FIRST ELEVATOR-----------------------------------------
				if (firstElevator.getInElevator().isEmpty() == false
						|| firstElevator.getReadyElevator().isEmpty() == false) {
					int check = 0;
					// --------------도중에 사람 태우기---------------------//
					if (firstElevator.getY() % 50 == 0) { // 층 에 도착했을 때 (한 층이 50이라서)
						for (int i = 0; i < firstElevator.getReadyElevator().size(); i++) {
							if (!firstElevator.getUpDown().equals(firstElevator.getReadyElevator().get(i).getUpDown()))
								continue;
							// 그 층에서 탈 수 있는사람 모두 카운트
							if (firstElevator.getY() == firstElevator
									.getReadyElevator().get(i).getY()) {
								check++;
								break;
							}
						}
						if (check != 0) {// -----------------그 층에 태울 수 있는 얘가 잇으면--------------------//
							check = 0;
							int totalDeleteCount = 0;// 그 층에서 태울 수 있는 얘 숫자
							// 모든 얘를 태움 (size 가 가능하다면)
							for (int i = 0; i < firstElevator.getReadyElevator()
									.size(); i++) {
								if (firstElevator.getY() == firstElevator.getReadyElevator().get(i).getY()) {
									totalDeleteCount++;// remove 때문에 index 가 당겨져서 그거 막으려고
								}
							}
		
							int count = 0;
							while (totalDeleteCount != count // remove 때문에 index 가 당겨져서 그거 막으려고
									&& firstElevator.getInElevator().size() < max_people) { // 정원 처리
								for (int i = 0; i < firstElevator.getReadyElevator().size(); i++) { // 레디큐 모든얘들 보고
	
									Person p = firstElevator.getReadyElevator().get(i);
									if (firstElevator.getY() == p.getY()) {
	
										p.setState(2); // 엘리베이터에 탄 상태로 바꿈
										firstElevator.setInElevator(p);// 사람이 엘리베이터에 탐
	
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
								bubbleSortUp (firstElevator.getInElevator());// D_y 큰갑 ... 작은값 순으로 정렬됨
							else
								bubbleSortDown(firstElevator.getInElevator());
	
							firstElevator.setUpDown("Stop");
							firstElevator.setState(1);
						}
					}
				}
				firstOpenDoor(firstElevator); // 엘리베이터 문 열기
				/////////////////////////////////////////////////////////////////////////////
				
				
				////////--------------------------------사람 내려주러 가기------------------------------------////////
				
				if (firstElevator.getInElevator().isEmpty() == false // 엘베에 탄사람이 한명이라도 있으면
						&& !firstElevator.getUpDown().equals("Stop")) { // 그리고 멈춘상태가 아니면 (이거 문여는거 때문에)
					
					if (firstElevator.getY() < firstElevator.getInElevator()// 사람 버리려고 내려가는
							.get(0).getD_y()) {
						firstElevator.setY(firstElevator.getY() + 1);
						
						firstElevator.setUpDown("Down"); // 엘베는 내려가는 중
						firstElevator.setState(0);//////////////// 엘리베이터가 움직이는 중

					} else if (firstElevator.getY() > firstElevator ///////////// 사람 버리려고 올라가는
							.getInElevator().get(0).getD_y()) {
						firstElevator.setY(firstElevator.getY() - 1);
						
						firstElevator.setUpDown("Up");
						firstElevator.setState(0);//////////////// 엘리베이터가 움직이는 중

					} else if (firstElevator.getY() == firstElevator ///////////// 도착해서 사람을 버린다 (사람이 내릴 때)
							.getInElevator().get(0).getD_y()
							&& firstElevator.getInElevator().get(0).getState() == 2) { // 사람 상태가 엘베 타고있는 중이면
						
						int totalDeleteCount = 0;// 그 층에서 태울 수 있는 얘 숫자
						for (int i = 0; i < firstElevator.getInElevator().size(); i++) {
							if (firstElevator.getY() == firstElevator.getInElevator().get(i).getD_y()) {
								totalDeleteCount ++;// remove 때문에 index 가 당겨져서 그거 막으려고
							}
						}
						
						int count = 0;
						while (totalDeleteCount != count) {// remove 때문에 index 가 당겨져서 그거 막으려고
							for (int i = 0; i < firstElevator.getInElevator().size(); i++) { // 레디큐 모든얘들 보고
								
								Person p = firstElevator.getInElevator().get(i); // 엘베안에 있는 넘들의 인덱스
								int currentFloor = firstElevator.getY(); // 엘리베이터의 y 좌표, 그러니까 지금 층 위치
								if (currentFloor == p.getD_y()) {
									
									p.setState(3); // 사람이 내리는 층에 도착했다고 바꿈
									
									persons.add(p); // 사람 그려주려고
									p.setE_x(p.getD_x()); // 목적지를 엘베의 층과 오른쪽 끝으로 바꿈
									p.setY(currentFloor);
									firstElevator.getInElevator().remove(i); // 사람 내렷으니까 '엘베 안에 사람 큐'에서 제거
									count++;
								}
								
							}
						}
						
						firstElevator.setState(1); // 엘베가 목적지에 도착했을 때
						firstElevator.setUpDown("Stop");

					}
					
				}
				
				////////--------------------------------태우러 가기------------------------------------////////
				
				// 0 = 태우러 가는중 (이 때는 얘들 태우면 안됨), 1 = 목적지에 도착했을 때 (이때는 태워야 됨) <- 엘베의 상태
				else if (firstElevator.getInElevator().size() < max_people // 인원제한
						&& firstElevator.getReadyElevator().isEmpty() == false // 엘베타려고 기다리는 얘 있으면
						&& !firstElevator.getUpDown().equals("Stop")) { // 그리고 stop 아니면
					if (firstElevator.getY() < firstElevator.getReadyElevator()// 사람 태우려고 내려가는
							.get(0).getY()) {
						firstElevator.setY(firstElevator.getY() + 1);
						
						firstElevator.setUpDown("Down");
						firstElevator.setState(0);//////////////// 엘리베이터가 움직이는 중

					} else if (firstElevator.getY() > firstElevator ///////////// 사람 태우려고 올라가는
							.getReadyElevator().get(0).getY()) {
						firstElevator.setY(firstElevator.getY() - 1);
						
						firstElevator.setUpDown("Up");
						firstElevator.setState(0);//////////////// 엘리베이터가 움직이는 중

					} else if (firstElevator.getY() == firstElevator ///////////// 기다리는 넘을 태운다
							.getReadyElevator().get(0).getY()
							&& firstElevator.getReadyElevator().get(0).getState() == 1) {
						
						int totalDeleteCount = 0;// 그 층에서 태울 수 있는 얘 숫자
						for (int i = 0; i < firstElevator.getReadyElevator().size(); i++) {
							if (firstElevator.getY() == firstElevator.getReadyElevator().get(i).getY()) {
								totalDeleteCount ++;// remove 때문에 index 가 당겨져서 그거 막으려고
							}
						}
						
						int count = 0; // index 가 달라지는거 해결하려고
						int peoplein = 0; // 현재 엘베에 탄 사람의 수를 카운트함
						
						while (totalDeleteCount != count // remove 때문에 index 가 당겨져서 그거 막으려고
								&& peoplein <= max_people) { // 인원제한 할려고
							for (int i = 0; i < firstElevator.getReadyElevator().size(); i++) { // 레디큐 모든얘들 보고
								
								Person p = firstElevator.getReadyElevator().get(i);
								if (firstElevator.getY() == p.getY()) {
									
									p.setState(2); // 엘리베이터에 탄 상태로 바꿈
									firstElevator.setInElevator(p);// 사람이 엘리베이터에 탐
									peoplein++; // 사람이 탈때마다 증가
									int delete_id = p.getId();// ready 큐 i 랑 persons i 랑 달라서
									firstElevator.getReadyElevator().remove(i);
									
									for (int j = 0; j < persons.size(); j++) // 지운 레디큐에 맞는 퍼슨 지움
										if (persons.get(j).getId() == delete_id) {
											persons.remove(j);
											break;
										}
									count++;
									break;
								}
							}
							
						}
						
						// 엘리베이터 상태를 다시 바꿔줌
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
				// first elevator 와 코드 동일, 단지 firstElevator -> secondElevator
				
				// ///////////////////////////////////////////////////////////////
				if (secondElevator.getInElevator().isEmpty() == false
						|| secondElevator.getReadyElevator().isEmpty() == false) {
					int check = 0;
					// --------------도중에 사람 태우기---------------------//
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
						if (check != 0) {// -----------------그 층에 태울 수 있는 얘가 잇으면--------------------//
							check = 0;
							int totalDeleteCount = 0;// 그 층에서 태울 수 있는 얘 숫자
							for (int i = 0; i < secondElevator
									.getReadyElevator().size(); i++) {
								if (secondElevator.getY() == secondElevator
										.getReadyElevator().get(i).getY()) {
									totalDeleteCount++;// remove 때문에 index 가 당겨져서 그거 막으려고
								}
							}

							int count = 0;
							while (totalDeleteCount != count
									&& secondElevator.getInElevator().size() < max_people) {// remove 때문에 index 가 당겨져서 그거 막으려고
								for (int i = 0; i < secondElevator
										.getReadyElevator().size(); i++) { // 레디큐 모든얘들 보고

									Person p = secondElevator.getReadyElevator().get(i);
									if (secondElevator.getY() == p.getY()) {

										p.setState(2); // 엘리베이터에 탄 상태로 바꿈
										secondElevator.setInElevator(p);// 사람이 엘리베이터에 탐

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
								bubbleSortUp(secondElevator.getInElevator());// D_y 큰갑 ... 작은값 순으로 정렬됨
							else
								bubbleSortDown(secondElevator.getInElevator());

							secondElevator.setUpDown("Stop");
							secondElevator.setState(1);
						}
					}
				}
				secondOpenDoor(secondElevator);
				// ///////////////////////////////////////////////////////////////////////////

				// //////--------------------------------버리러 가기------------------------------------////////

				if (secondElevator.getInElevator().isEmpty() == false // 엘베에 탄사람이 한명이라도 있으면
						&& !secondElevator.getUpDown().equals("Stop")) { // 그리고 멈춘상태가 아니면 (이거 문여는거 때문에)

					if (secondElevator.getY() < secondElevator.getInElevator()// 사람 버리려고 내려가는
							.get(0).getD_y()) {
						secondElevator.setY(secondElevator.getY() + 1);

						secondElevator.setUpDown("Down"); // 엘베는 내려가는 중
						secondElevator.setState(0);// ////////////// 엘리베이터가 움직이는 중

					} else if (secondElevator.getY() > secondElevator // /////////// 사람 버리려고 올라가는
							.getInElevator().get(0).getD_y()) {
						secondElevator.setY(secondElevator.getY() - 1);

						secondElevator.setUpDown("Up");
						secondElevator.setState(0);// ////////////// 엘리베이터가 움직이는 중

					} else if (secondElevator.getY() == secondElevator // /////////// 도착해서 사람을 버린다 (사람이 내릴 때)
							.getInElevator().get(0).getD_y()
							&& secondElevator.getInElevator().get(0).getState() == 2) { // 사람 상태가 엘베 타고있는 중이면

						int totalDeleteCount = 0;// 그 층에서 태울 수 있는 얘 숫자
						for (int i = 0; i < secondElevator.getInElevator()
								.size(); i++) {
							if (secondElevator.getY() == secondElevator
									.getInElevator().get(i).getD_y()) {
								totalDeleteCount++;// remove 때문에 index 가 당겨져서 그거 막으려고
							}
						}

						int count = 0;
						while (totalDeleteCount != count) {// remove 때문에 index 가 당겨져서 그거 막으려고
							for (int i = 0; i < secondElevator.getInElevator()
									.size(); i++) { // 레디큐 모든얘들 보고

								Person p = secondElevator.getInElevator().get(i); // 엘베안에 있는 넘들의 인덱스
								int currentFloor = secondElevator.getY(); // 엘리베이터의 y 좌표, 그러니까 지금 층 위치
								if (currentFloor == p.getD_y()) {

									p.setState(3); // 사람이 내리는 층에 도착했다고 바꿈

									persons.add(p); // 사람 그려주려고
									p.setE_x(p.getD_x()); // 목적지를 엘베의 층과 오른쪽 끝으로 바꿈
									p.setY(currentFloor);
									secondElevator.getInElevator().remove(i); // 사람 내렷으니까 '엘베 안에 사람 큐'에서 제거
									count++;
								}

							}
						}

						secondElevator.setState(1); // 엘베가 목적지에 도착했을 때
						secondElevator.setUpDown("Stop");
					}

				}

				// //////--------------------------------태우러  가기------------------------------------////////

				// 0 = 태우러 가는중 (이 때는 얘들 태우면 안됨), 1 = 목적지에 도착했을 때 (이때는 태워야 됨) <- 엘베의 상태
				else if (secondElevator.getInElevator().size() < max_people
						&& secondElevator.getReadyElevator().isEmpty() == false // 엘베타려고 기다리는 얘 있으면
						&& !secondElevator.getUpDown().equals("Stop")) { // 그리고 stop 아니면

					if (secondElevator.getY() < secondElevator.getReadyElevator()// 사람 태우려고 내려가는
							.get(0).getY()) {
						secondElevator.setY(secondElevator.getY() + 1);

						secondElevator.setUpDown("Down");
						secondElevator.setState(0);// ////////////// 엘리베이터가 움직이는 중

					} else if (secondElevator.getY() > secondElevator // /////////// 사람 태우려고  올라가는
							.getReadyElevator().get(0).getY()) {
						secondElevator.setY(secondElevator.getY() - 1);

						secondElevator.setUpDown("Up");
						secondElevator.setState(0);// ////////////// 엘리베이터가 움직이는 중

					} else if (secondElevator.getY() == secondElevator // /////////// 기다리는 넘을 태운다
							.getReadyElevator().get(0).getY()
							&& secondElevator.getReadyElevator().get(0).getState() == 1) {

						int totalDeleteCount = 0;// 그 층에서 태울 수 있는 얘 숫자
						for (int i = 0; i < secondElevator.getReadyElevator()
								.size(); i++) {
							if (secondElevator.getY() == secondElevator
									.getReadyElevator().get(i).getY()) {
								totalDeleteCount++;// remove 때문에 index 가 당겨져서 그거 막으려고
							}
						}

						int count = 0;
						int peoplein = 0;
						
						while (totalDeleteCount != count && peoplein <= max_people) {// remove 때문에 index 가 당겨져서 그거 막으려고
							for (int i = 0; i < secondElevator
									.getReadyElevator().size(); i++) { // 레디큐 모든얘들 보고

								Person p = secondElevator.getReadyElevator()
										.get(i);
								if (secondElevator.getY() == p.getY()) {

									p.setState(2); // 엘리베이터에 탄 상태로 바꿈
									secondElevator.setInElevator(p);// 사람이 엘리베이터에 탐
									peoplein++;

									int delete_id = p.getId();// ready 큐 i 랑 persons i 랑 달라서
									secondElevator.getReadyElevator().remove(i);
									for (int j = 0; j < persons.size(); j++)
										// 지운 레디큐에 맞는 퍼슨 지움
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
				
				jp.repaint();// jp 에 속한 얘들 다 그려줌

			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	public void firstOpenDoor (Elevator elevator) {
		if (elevator.getUpDown().equals("Stop")) {// 엘베 문열기
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
				elevator.setUpDown(""); // 엘리베이터 상태 초기화
			}
			
		}
	}
	public void secondOpenDoor (Elevator elevator) {
		if (elevator.getUpDown().equals("Stop")) {// 엘베 문열기
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
				elevator.setUpDown(""); // 엘리베이터 상태 초기화
			}
			
		}
	}
	// in elevator array list 를 bubble sort 로 정렬 (가장 가까운 층으로 가려고)
	public void bubbleSortUp(ArrayList<Person> arr) { // 큰값 ~ 작은값으로 정렬
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
	public void bubbleSortDown(ArrayList<Person> arr) { // 작은값 ~ 큰값으로 정렬
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