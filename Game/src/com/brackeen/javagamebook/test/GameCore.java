package com.brackeen.javagamebook.test;

import java.awt.Color;
import java.awt.DisplayMode;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Window;

import javax.swing.ImageIcon;

import com.brackeen.javagamebook.graphics.ScreenManager;

//����ʹ�õļ򵥳����࣬����Ҫʵ��draw()����
public abstract class GameCore {
	protected static final int FONT_SIZE = 24;
	private boolean isRunning;
	protected ScreenManager screen;
	private static final DisplayMode POSSIBLE_MODES[] = {
			new DisplayMode(800, 600, 32, 0),
			new DisplayMode(800, 600, 24, 0),
			new DisplayMode(800, 600, 16, 0),
			new DisplayMode(640, 480, 32, 0),
			new DisplayMode(640, 480, 24, 0),
			new DisplayMode(640, 480, 16, 0),
	};
	
	//������Ϸѭ�����˳���
	public void stop(){
		isRunning = false;
	}
	
	//����init()��gameLoop()����
	public void run(){
		try{
			init();
			gameLoop();
		}
		finally{
			screen.restoreScreen();
		}
	}
	
	//����ȫ����ʽ���������ʼ��
	public void init(){
		screen = new ScreenManager();
		DisplayMode displayMode = screen.findFirstCompatibleMode(POSSIBLE_MODES);
		screen.setFullScreen(displayMode);
		
		Window window = screen.getFullScreenWindow();
		window.setFont(new Font("Dialog", Font.PLAIN, FONT_SIZE));
		window.setBackground(Color.blue);
		window.setForeground(Color.white);
		isRunning = true;
	}
	
	public Image loadImage(String fileName){
		return new ImageIcon(fileName).getImage();
	}
	
	//������Ϸѭ����ֱ������stop����
	public void gameLoop(){
		long startTime = System.currentTimeMillis();
		long currTime = startTime;
		while(isRunning){
			long elapsedTime = System.currentTimeMillis() - currTime;
			currTime += elapsedTime;
			
			//����
			update(elapsedTime);
			
			//������Ļ
			Graphics2D g = screen.getGraphics();
			draw(g);
			g.dispose();
			screen.update();
			
			try{
				Thread.sleep(20);
			}catch(InterruptedException ex){}
		}
	}
	
	public void update(long elapsedTime){}		//���ݾ�����ʱ����������Ϸ/����״̬
	public abstract void draw(Graphics2D g);	//���Ƶ���Ļ�ϣ�����Ҫ�����������
}
