package com.brackeen.javagamebook.test;

import java.awt.AWTException;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Robot;
import java.awt.Window;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.SwingUtilities;

import com.brackeen.javagamebook.graphics.*;

/**
 * �����ۿ����Բ��Գ����������ۿ����ԣ��û����İ����ⷽ�������ƶ���꣬���������Ļ����ʱֹͣ
 * ���ۿ�����������ƶ�ʱ�������У�ʹ��������Բ����������˶���ʹ��겻��������Ļ����
 * @author peng
 *
 */
public class MouselookTest extends GameCore implements MouseMotionListener, KeyListener {
	public static void main(String[] args){
		new MouselookTest().run();
	}
	
	private Image bgImage;
	private Robot robot;
	private Point mouseLocation;
	private Point centerLocation;
	private Point imageLocation;
	private boolean relativeMouseMode;
	private boolean isRecentering;

	public void init(){
		super.init();
		mouseLocation = new Point();
		centerLocation = new Point();
		imageLocation = new Point();
		relativeMouseMode = true;
		isRecentering = false;
		try{
			robot = new Robot();
			recenterMouse();
			mouseLocation.x = centerLocation.x;
			mouseLocation.y = centerLocation.y;
		}catch(AWTException ex){
			System.out.println("couldn't create robot");
		}
		Window window = screen.getFullScreenWindow();
		window.addMouseMotionListener(this);
		window.addKeyListener(this);
		bgImage = loadImage("image/bg.jpg");
	}
	
	//��Robot�ཫ���ŵ���Ļ���롣ע�Ⲣ��������ƽ̨��֧��ʹ�������
	private synchronized void recenterMouse(){
		Window window = screen.getFullScreenWindow();
		if (robot != null && window.isShowing()) {
			centerLocation.x = window.getWidth()/2;
			centerLocation.y = window.getHeight()/2;
			SwingUtilities.convertPointToScreen(centerLocation, window);
			isRecentering = true;
			robot.mouseMove(centerLocation.x, centerLocation.y);
		}
	}
	
	
	@Override
	public void draw(Graphics2D g) {
		// TODO Auto-generated method stub
		int w = screen.getWidth();
		int h = screen.getHeight();
		
		//��֤λ����ȷ
		imageLocation.x %= w;
		imageLocation.y %= h;
		if(imageLocation.x < 0)
			imageLocation.x += w;
		if(imageLocation.y < 0)
			imageLocation.y += h;
		
		//���ĸ�λ�û�ͼ����ס��Ļ
		int x = imageLocation.x;
		int y = imageLocation.y;
		g.drawImage(bgImage, x, y, null);
		g.drawImage(bgImage, x-w, y, null);
		g.drawImage(bgImage, x, y-h, null);
		g.drawImage(bgImage, x-w, y-h, null);
		
		//��ָ��
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g.drawString("Press space to change mouse modes.", 5, FONT_SIZE);
		g.drawString("press escape to exit.", 5, FONT_SIZE*2);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
			stop();
		else if(e.getKeyCode() == KeyEvent.VK_SPACE)
			//�ı������귽ʽ
			relativeMouseMode = !relativeMouseMode;
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		mouseMoved(e);
	}

	@Override
	public synchronized void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		//����¼�����������
		if(isRecentering && centerLocation.x == e.getX() && centerLocation.y == e.getY())
			isRecentering = false;
		else{
			int dx = e.getX() - mouseLocation.x;
			int dy = e.getY() - mouseLocation.y;
			imageLocation.x += dx;
			imageLocation.y += dy;
			//������
			if(relativeMouseMode)
				recenterMouse();
		}
		mouseLocation.x = e.getX();
		mouseLocation.y = e.getY();
	}

}
