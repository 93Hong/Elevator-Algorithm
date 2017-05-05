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

	public void paintComponent(Graphics g) {//repaint �Ҹ��� �̰Ű� �ٽúҸ�, �����ӿ� �����ִ� �͵��� ��� �׷���
		g.setColor(Color.black);
		g.fillRect(0, 0, 650, 400);
		this.height = (getSize().height / totalfloor);

		g.setColor(Color.yellow); // �� �׸���
		g.fillRect((int)(Math.random() * 700), (int)(Math.random() * 400), 2, 2);
		g.fillRect((int)(Math.random() * 700), (int)(Math.random() * 400), 2, 2);
		g.fillRect((int)(Math.random() * 700), (int)(Math.random() * 400), (int)(Math.random() * 10), (int)(Math.random() * 10));
		g.fillRect((int)(Math.random() * 700), (int)(Math.random() * 400), 2, 2);
		g.fillRect((int)(Math.random() * 700), (int)(Math.random() * 400), 2, 2);


		// �׳� �� ǥ���ϴ� ����
		g.setColor(Color.blue);
		for (int i = 0; i < 8; i++) {
			g.drawLine(0, i * this.height, getSize().width, i * this.height);
		}
		
		//���μ� ���� (����������)
		g.setColor(Color.gray);
		g.drawLine(215, 0, 215, 438);
		g.drawLine(225, 0, 225, 438);
		g.drawLine(235, 0, 235, 438);
		
		//���μ� ������ (����������)
		g.drawLine(415, 0, 415, 438);
		g.drawLine(425, 0, 425, 438);
		g.drawLine(435, 0, 435, 438);

		//��ٸ�
		Graphics2D g2 = (Graphics2D) g;//�����⸦ �ٲٱ� ����
		g2.setColor(Color.LIGHT_GRAY);
		g2.setStroke(new BasicStroke(5, BasicStroke.CAP_ROUND, 0));//������
		g2.drawLine(555, 0, 555, 438);//�� �׸���� �ٲ�     
		g2.drawLine(595, 0, 595, 438);//�� �׸���� �ٲ�     
		for(int i = 0; i < 19; i++){
			g.drawLine(555, 20 + i*20, 595, 20 + i*20);
		}

		// ���������� �ΰ�
		g.drawImage(firstElevator.getImg(0), firstElevator.getX(), firstElevator.getY(), this);
		g.drawImage(secondElevator.getImg(0), secondElevator.getX(), secondElevator.getY(), this);

		// �����
		for (int i = 0; i < persons.size(); i++) {
			g.drawImage(persons.get(i).getImage(), persons.get(i).getX(), persons.get(i).getY(), this);
		}

	}
}