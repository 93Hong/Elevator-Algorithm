import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JPanel;

public class LeftJpanel extends JPanel {
	private int height;
	private int totalfloor = 8;

	private ArrayList<Person> persons;
	private Elevator firstElevator;
	private Elevator secondElevator;

	public LeftJpanel(ArrayList<Person> persons, Elevator firstElevator, Elevator secondElevator) {
		setBounds(50, 0, 650, 400);
		this.persons = persons;
		this.firstElevator = firstElevator;
		this.secondElevator = secondElevator;
		new MoveThread(persons, firstElevator, secondElevator, this).start();
	}

	public void paintComponent(Graphics g) {//repaint 불리면 이거가 다시불림, 프레임에 박혀있는 것들을 모두 그려줌
		g.setColor(Color.black);
		g.fillRect(0, 0, 650, 400);
		this.height = (getSize().height / totalfloor);

		g.setColor(Color.yellow); // 별 그리기
		g.fillRect((int)(Math.random() * 700), (int)(Math.random() * 400), 2, 2);
		g.fillRect((int)(Math.random() * 700), (int)(Math.random() * 400), 2, 2);
		g.fillRect((int)(Math.random() * 700), (int)(Math.random() * 400), (int)(Math.random() * 10), (int)(Math.random() * 10));
		g.fillRect((int)(Math.random() * 700), (int)(Math.random() * 400), 2, 2);
		g.fillRect((int)(Math.random() * 700), (int)(Math.random() * 400), 2, 2);


		// 그냥 층 표시하는 선들
		g.setColor(Color.blue);
		for (int i = 0; i < 8; i++) {
			g.drawLine(0, i * this.height, getSize().width, i * this.height);
		}
		
		//세로선 왼쪽 (엘리베이터)
		g.setColor(Color.gray);
		g.drawLine(215, 0, 215, 438);
		g.drawLine(225, 0, 225, 438);
		g.drawLine(235, 0, 235, 438);
		
		//세로선 오른쪽 (엘리베이터)
		g.drawLine(415, 0, 415, 438);
		g.drawLine(425, 0, 425, 438);
		g.drawLine(435, 0, 435, 438);

		//사다리
		Graphics2D g2 = (Graphics2D) g;//선굵기를 바꾸기 위해
		g2.setColor(Color.LIGHT_GRAY);
		g2.setStroke(new BasicStroke(5, BasicStroke.CAP_ROUND, 0));//선굵기
		g2.drawLine(555, 0, 555, 438);//선 그리기로 바꿈     
		g2.drawLine(595, 0, 595, 438);//선 그리기로 바꿈     
		for(int i = 0; i < 19; i++){
			g.drawLine(555, 20 + i*20, 595, 20 + i*20);
		}

		// 엘리베이터 두개
		g.drawImage(firstElevator.getImg(0), firstElevator.getX(), firstElevator.getY(), this);
		g.drawImage(secondElevator.getImg(0), secondElevator.getX(), secondElevator.getY(), this);

		// 사람들
		for (int i = 0; i < persons.size(); i++) {
			g.drawImage(persons.get(i).getImage(), persons.get(i).getX(), persons.get(i).getY(), this);
		}

	}
}