package com.brackeen.javagamebook.graphics;

//��չSprite�࣬�������������Ժ���������
public class Player extends Sprite{
	public static final int STATE_NORMAL = 0;
	public static final int STATE_JUMPING = 1;
	public static final float SPEED = 0.3f;
	public static final float GRAVITY = 0.002f;
	private float floorY;		//����λ��
	private int state;
	
	public Player(Animation anim){
		super(anim);
		state = STATE_NORMAL;
	}
	
	//ȡ��Player״̬
	public int getState(){
		return state;
	}
	
	//����Player״̬
	public void setState(int state){
		this.state = state;
	}
	
	//���õ���λ�ã���Ϸ���ڴ����������
	public void setFloorY(float floorY){
		this.floorY = floorY;
		setY(floorY);
	}
	
	//��Player����
	public void jump(){
		setVelocityY(-1);
		state = STATE_JUMPING;
	}
	
	//������Ϸ��λ���붯����������Ϸ����غ���״̬����ΪNORMAL
	public void update(long elapsedTime){
		if(getState() == STATE_JUMPING)
			setVelocityY(getVelocityY() + GRAVITY * elapsedTime);
		//�ƶ���Ϸ��
		super.update(elapsedTime);
		//�����Ϸ���Ƿ��ŵ�
		if(getState() == STATE_JUMPING && getY() >= floorY){
			setVelocityY(0);
			setY(floorY);
			setState(STATE_NORMAL);
		}
	}
}
