package pers.vinson.screen;

import java.awt.Image;
import java.util.ArrayList;

//Animation�����һϵ��ͼ��֡����ÿ��֡��ʾ��ʱ��
public class Animation {
	private ArrayList frames;
	private int currFrameIndex;
	private long animTime;
	private long totalDuration;
	
	//�����µĿհ׶���
	public Animation(){
		frames = new ArrayList();
		totalDuration = 0;
		start();
	}
	
	//��ָ����ʾʱ���ͼ��ӽ�������
	//duration:ͼƬ����ʾʱ��
	public synchronized void addFrame(Image image, long duration){
		totalDuration += duration;		//������ʱ���ӳ�
		frames.add(new AnimFrame(image, totalDuration));
	}
	
	//��ͷ��ʼ���������������
	public synchronized void start(){
		animTime = 0;
		currFrameIndex = 0;
	}
	
	//��Ҫʱ������������ĵ�ǰ���أ�֡��
	public synchronized void update(long elapsedTime){
		if(frames.size() > 1){
			animTime += elapsedTime;
			if(animTime >= totalDuration){
				//��֤�������ʱ������ʱ�����¿�ʼ��ʹ����ѭ��
				animTime = animTime % totalDuration;
				currFrameIndex = 0;
			}
			while(animTime > getFrame(currFrameIndex).endTime){
				currFrameIndex++;
			}
		}
	}
	
	//ȡ�����Animation�ĵ�ǰͼ�����û��ͼ�񣬷���null
	public synchronized Image getImage(){
		if(frames.size() == 0)
			return null;
		else
			return getFrame(currFrameIndex).image;
	}
	
	//ȡ�õ�i֡����������ʱ���ͼ��
	private AnimFrame getFrame(int i){
		return (AnimFrame)frames.get(i);
	}
	
	private class AnimFrame{
		Image image;
		long endTime;
		public AnimFrame(Image image, long endTime){
			this.image = image;
			this.endTime = endTime;
		}
	}
}
