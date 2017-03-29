/**
 * ����ʾ�л���ȫ����ʽ
 */
package pers.vinson.screen;

import java.awt.*;
import javax.swing.JFrame;

/**
 * �����µ�SimpleScreenManager�������ʼ��
 * @author peng
 *
 */
public class SimpleScreenManager {
	private GraphicsDevice device;	//GraphicsDevice���������ı���ʾ��ʽ�ͼ����ʾ����
	
	public SimpleScreenManager(){
		GraphicsEnvironment environment = GraphicsEnvironment.getLocalGraphicsEnvironment();
		device = environment.getDefaultScreenDevice();		
	}
	
	/**
	 *����ȫ����ʽ�͸ı���ʾ��ʽ 
	 * @param displayMode
	 * @param window
	 */
	public void setFullScreen(DisplayMode displayMode, JFrame window){
		window.setUndecorated(true);	//���û����Frame��װ�Ρ��˴�ʹ����ʧȥ�߿�ͱ�������װ��
		window.setResizable(false);
		device.setFullScreenWindow(window);
		
		if(displayMode != null && device.isDisplayChangeSupported()){
			try{
				device.setDisplayMode(displayMode);
			}catch(IllegalArgumentException ex){}
		}
	}
	
	//����ǰʹ�õĴ��ڷ���ȫ����ʽ
	public Window getFullScreenWindow(){
		return device.getFullScreenWindow();
	}
	
	//�ָ�ȫ��ʹ�÷�ʽ
	public void restoreScreen(){
		Window window = device.getFullScreenWindow();
		if(window != null)
			window.dispose();
		device.setFullScreenWindow(null);
	}
}
