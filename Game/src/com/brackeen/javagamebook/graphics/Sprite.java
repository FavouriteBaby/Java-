package com.brackeen.javagamebook.graphics;

import java.awt.Image;

public class Sprite {
	private Animation anim;
	
	//λ�ã����أ�
	private float x;
	private float y;
	
	//�ٶȣ�����/���룩
	private float dx;
	private float dy;
	
	//��ָ��Animation�����µ�Sprite����
	public Sprite(Animation anim){
		this.anim = anim;
	}
	
	//�����ٶȸ���Sprite��Animation����λ��
	public void update(long elapsedTime){
		x += dx * elapsedTime;
		y += dy * elapsedTime;
		anim.update(elapsedTime);
	}
	
	//ȡ��Sprite�ĵ�ǰλ��
	public float getX(){
		return x;
	}
	public float getY(){
		return y;
	}
	
	//����Sprite�ĵ�ǰλ��
	public void setX(float x){
		this.x = x;
	}
	public void setY(float y){
		this.y = y;
	}
	
	//���ݵ�ǰͼ��ȡ��Sprite�Ŀ��
	public float getWidth(){
		return anim.getImage().getWidth(null);
	}
	public float getHeight(){
		return anim.getImage().getHeight(null);
	}
	
	//ȡ��Sprite���ٶ�
	public float getVelocityX(){
		return dx;
	}
	public float getVelocityY(){
		return dy;
	}
	
	//����Sprite���ٶ�
	public void setVelocityX(float dx){
		this.dx = dx;
	}
	public void setVelocityY(float dy){
		this.dy = dy;
	}
	
	//ȡ��Sprite�ĵ�ǰͼ��
	public Image getImage(){
		return anim.getImage();
	}
}
