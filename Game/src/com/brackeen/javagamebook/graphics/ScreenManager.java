package com.brackeen.javagamebook.graphics;

import java.awt.DisplayMode;
import java.awt.EventQueue;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JFrame;

public class ScreenManager {
	private GraphicsDevice device;
	
	
	//������ScreenManager����
	public ScreenManager(){
		GraphicsEnvironment environment = GraphicsEnvironment.getLocalGraphicsEnvironment();
		device = environment.getDefaultScreenDevice();
	}
	
	//��ϵͳȱʡ�豸����һϵ�м�����ʾ��ʽ
	public DisplayMode[] getCompatibleDisplayModes(){
		return device.getDisplayModes();
	}

	//���ط����嵥�е�һ��������ʾ��ʽ�����û�м�����ʾ��ʽ���򷵻�null
	public DisplayMode findFirstCompatibleMode(DisplayMode modes[]){
		DisplayMode goodModes[] = device.getDisplayModes();
		for(int i = 0; i < modes.length; ++i){
			for(int j = 0; j < goodModes.length; ++j){
				if(displayModesMatch(modes[i], goodModes[j]))
					return modes[i];
			}
		}
		return null;
	}
	
	//���ص�ǰ��ʾ��ʽ
	public DisplayMode getCurrentDisplayMode(){
		return device.getDisplayMode();
	}
	
	//ȷ��������ʾ��ʽ�Ƿ�ƥ�䡣������ʾ��ʽƥ��ָ������ͬ�ֱ��ʡ�λ��Ⱥ�ˢ���ʡ�
	//���һ����ʾ��ʽ��λ���ΪDisplayMode.BIT_DEPTH_MULTI�������λ���
	//���һ����ʾ��ʽ��ˢ����ΪDisplayMode.REFRESH_RATE_UNKNOWN�������ˢ����
	public boolean displayModesMatch(DisplayMode mode1, DisplayMode mode2){
		if(mode1.getWidth() != mode2.getWidth() || mode1.getHeight() != mode2.getHeight())
			return false;
		if(mode1.getBitDepth() != mode2.getBitDepth() && mode1.getBitDepth() != DisplayMode.BIT_DEPTH_MULTI
				&& mode2.getBitDepth() != DisplayMode.BIT_DEPTH_MULTI)
			return false;
		if(mode1.getRefreshRate() != mode2.getRefreshRate() && mode1.getRefreshRate() != DisplayMode.REFRESH_RATE_UNKNOWN
				&& mode2.getRefreshRate() != DisplayMode.REFRESH_RATE_UNKNOWN)
			return false;
		
		return true;
	}
	
	//����ȫ����ʽ�͸ı���ʾ��ʽ�����ָ����ʾ��ʽΪnull��������豸�����ݣ������ϵͳ���޷��ı���ʾ��ʽ����ʹ�õ�ǰ��ʾ��ʽ
	//��ʾ����BufferStrategy������������
	public void setFullScreen(DisplayMode displayMode){
		final JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setUndecorated(true);
		frame.setIgnoreRepaint(true);
		frame.setResizable(false);
		device.setFullScreenWindow(frame);
		if(displayMode != null && device.isDisplayChangeSupported()){
			try{
				device.setDisplayMode(displayMode);
			}catch(IllegalArgumentException ex){}
			frame.setSize(displayMode.getWidth(), displayMode.getHeight());
		}
		try{
			EventQueue.invokeAndWait(new Runnable(){
				public void run(){
					frame.createBufferStrategy(2);	//������Ҫ�Ļ���������BufferStrategy
				}
			});
		}catch(InterruptedException ex){}
		catch(InvocationTargetException ex){}
	}
	
	/**
	 * ȡ����ʾ��ͼ��������ScreenManagerʹ��˫���棬���Ӧ�ó���Ҫ����update()��������ʾ���Ƶ��κ�ͼ��
	 * Ӧ�ó���Ҫ����ͼ��ͼ��
	 */
	public Graphics2D getGraphics(){
		Window window = device.getFullScreenWindow();
		if(window != null){
			BufferStrategy strategy = window.getBufferStrategy();	//���BufferStrategy������
			return (Graphics2D)strategy.getDrawGraphics();			//getDrawGraphics()����ȡ�û�ͼ��������ͼ��������
		}
		return null;
	}
	
	//������ʾ
	public void update(){
		Window window = device.getFullScreenWindow();
		if(window != null){
			BufferStrategy strategy = window.getBufferStrategy();
			if(!strategy.contentsLost())
				strategy.show();	//��ʾ��ͼ������
		}
		Toolkit.getDefaultToolkit().sync();	//��֤�봰��ϵͳͬ��
	}
	
	/**
	 * ����ȫ����ʽ�е�ǰʹ�õĴ��ڡ�����豸����ȫ����ʽ�У��򷵻�null
	 */
	public JFrame getFullScreenWindow(){
		return (JFrame)device.getFullScreenWindow();
	}
	
	/**
	 * ����ȫ����ʽ�е�ǰʹ�õĴ��ڿ�ȣ�����豸����ȫ����ʽ�У��򷵻�0
	 */
	public int getWidth(){
		Window window = device.getFullScreenWindow();
		if(window != null)
			return window.getWidth();
		return 0;
	}
	
	/**
	 * ����ȫ����ʽ�е�ǰʹ�õĴ��ڸ߶ȣ�����豸����ȫ����ʽ�У��򷵻�0
	 */
	public int getHeight(){
		Window window = device.getFullScreenWindow();
		if(window != null)
			return window.getHeight();
		return 0;
	}
	
	//�ָ���Ļ����ʾ��ʽ
	public void restoreScreen(){
		Window window = device.getFullScreenWindow();
		if(window != null)
			window.dispose();
		device.setFullScreenWindow(null);
	}
	
	//�����뵱ǰ��ʾ�����ݵ�ͼ��
	public BufferedImage createCompatibleImage(int w, int h, int transparancy){
		Window window = device.getFullScreenWindow();
		if(window != null){
			GraphicsConfiguration gc = window.getGraphicsConfiguration();
			return gc.createCompatibleImage(w, h, transparancy);
		}
		return null;
	}
}
