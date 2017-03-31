package com.brackeen.javagamebook.test;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Window;
import java.awt.event.KeyEvent;

import com.brackeen.javagamebook.graphics.Animation;
import com.brackeen.javagamebook.graphics.Player;
import com.brackeen.javagamebook.input.GameAction;
import com.brackeen.javagamebook.input.InputManager;

/**
 * InputManagerTest�ü�·/�����Ʋ���InputManager��Ҫ�ƶ���Ϸ�ߣ������ü�ͷ����Ҫ����Ϸ���������ÿո��
 * InputManagerTest��������ͣ��Ϸ����������ϷԪ��
 * @author peng
 *
 */

public class InputManagerTest extends GameCore {
	public static void main(String[] args){
		new InputManagerTest().run();
	}
	
	protected GameAction jump;
	protected GameAction exit;
	protected GameAction moveLeft;
	protected GameAction moveRight;
	protected GameAction pause;
	protected InputManager inputManager;
	
	private Player player;
	private Image bgImage;
	private boolean paused;
	
	public void init(){
		super.init();
		Window window = screen.getFullScreenWindow();
		inputManager = new InputManager(window);
		//���������������귽ʽ
		inputManager.setRelativeMouseMode(true);
		inputManager.setCursor(InputManager.INVISIBLE_CURSOR);
		createGameActions();
		createSprite();
		paused = false;
	}
	
	public boolean isPaused(){
		return paused;
	}
	
	public void setPaused(boolean p){
		if(paused != p){
			this.paused = p;
			inputManager.resetAllGameActions();
		}
	}
	
	public void update(long elapsedTime){
		//������Ϸ�Ƿ���ͣ�������ܷ���������
		checkSystemInput();
		if(!isPaused()){
			checkGameInput();
			player.update(elapsedTime);
		}
	}
	
	//������Ϸ��ͣ��񣬼����ܰ��µ�GameActions������
	public void checkSystemInput(){
		if(pause.isPressed())
			setPaused(!isPaused());
		if(exit.isPressed())
			stop();
	}
	
	//��Ϸ����ͣʱ���ɰ��µ�GameActions������
	public void checkGameInput(){
		float velocityX = 0;
		if(moveLeft.isPressed())
			velocityX -= Player.SPEED;
		if(moveRight.isPressed())
			velocityX += Player.SPEED;
		player.setVelocityX(velocityX);
		if(jump.isPressed() && player.getState() != Player.STATE_JUMPING)
			player.jump();
	}
	
	//����GameActions��������ͼ����
	public void createGameActions(){
		jump = new GameAction("jump", GameAction.DETECT_INITAL_PRESS_ONLY);
		exit = new GameAction("exit", GameAction.DETECT_INITAL_PRESS_ONLY);
		moveLeft = new GameAction("moveLeft");
		moveRight = new GameAction("moveRight");
		pause = new GameAction("pause", GameAction.DETECT_INITAL_PRESS_ONLY);
		
		inputManager.mapToKey(exit,  KeyEvent.VK_ESCAPE);
		inputManager.mapToKey(pause, KeyEvent.VK_P);
		
		//�ÿո������������
		inputManager.mapToKey(jump, KeyEvent.VK_SPACE);
		inputManager.mapToKey(jump, InputManager.MOUSE_BUTTON_1);
		
		//�ü�ͷ���ƶ�
		inputManager.mapToKey(moveLeft, KeyEvent.VK_LEFT);
		inputManager.mapToKey(moveRight, KeyEvent.VK_RIGHT);
		inputManager.mapToKey(moveLeft, KeyEvent.VK_A);
		inputManager.mapToKey(moveRight, KeyEvent.VK_D);
		
		//����Ϸ���ƶ���ͼΪ���
		inputManager.mapToMouse(moveLeft, InputManager.MOUSE_MOVE_LEFT);
		inputManager.mapToMouse(moveRight, InputManager.MOUSE_MOVE_RIGHT);
	}
	
	//װ��ͼ�������player����
	private void createSprite(){
		bgImage = loadImage("image/background.jpg");
		Image player0 = loadImage("image/player0.png");
		Image player1 = loadImage("image/player1.png");
		Image player2 = loadImage("image/player2.png");
 		
		Animation anim = new Animation();
		anim.addFrame(player0, 150);
		anim.addFrame(player1, 150);
		anim.addFrame(player2, 150);
		anim.addFrame(player0, 150);
		anim.addFrame(player1, 150);
		anim.addFrame(player2, 150);
		
		player = new Player(anim);
		player.setFloorY(screen.getHeight() - player.getHeight());
	}

	@Override
	public void draw(Graphics2D g) {
		// TODO Auto-generated method stub
		g.drawImage(bgImage, 0, 0, null);
		g.drawImage(player.getImage(), Math.round(player.getX()), Math.round(player.getY()), null);
	}
}
