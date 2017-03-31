package com.brackeen.javagamebook.test;

import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import com.brackeen.javagamebook.graphics.*;
import com.brackeen.javagamebook.input.GameAction;

public class MenuTest extends InputManagerTest implements ActionListener {
	public static void main(String[] args){
		new MenuTest().run();
	}
	
	protected GameAction configAction;
	private JButton playButton;
	private JButton configButton;
	private JButton quitButton;
	private JButton pauseButton;
	private JPanel playButtonSpace;
	
	public void init(){
		super.init();
		//��Swing��������Լ�
		NullRepaintManager.install();
		//�������õ�GameAction
		configAction = new GameAction("config");
		//���ɰ�ť
		quitButton = createButton("quit", "Quit");
		playButton = createButton("play", "Continue");
		pauseButton = createButton("pause", "Pause");
		configButton = createButton("config", "Change Settings");
		//���ɲ���/��ͣ��ť�Ŀռ�
		playButtonSpace = new JPanel();
		playButtonSpace.setOpaque(false);
		playButtonSpace.add(pauseButton);
		JFrame frame = super.screen.getFullScreenWindow();
		Container contentPane = frame.getContentPane();
		//��֤���ݴ���͸��
		if(contentPane instanceof JComponent){
			((JComponent)contentPane).setOpaque(false);
		}
		
		//������ӽ���Ļ�����ݴ���
		contentPane.setLayout(new FlowLayout(FlowLayout.LEFT));
		contentPane.add(playButtonSpace);
		contentPane.add(configButton);
		contentPane.add(quitButton);
		//��ʽ�������
		frame.validate();
	}
	
	public void draw(Graphics2D g){
		super.draw(g);
		JFrame frame = super.screen.getFullScreenWindow();
		frame.getLayeredPane().paintComponents(g);
	}
	
	//����ͣ״̬�ı�ʱ�ı䲥��/��ͣ��ť
	public void setPaused(boolean p){
		super.setPaused(p);
		playButtonSpace.removeAll();
		if(isPaused())
			playButtonSpace.add(playButton);
		else
			playButtonSpace.add(pauseButton);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object src = e.getSource();
		if(src == quitButton)
			super.exit.tap();
		else if(src == configButton)
			configAction.tap();
		else if(src == playButton || src == pauseButton)
			super.pause.tap();
	}
	
	//����Swing JButton��
	public JButton createButton(String name, String toolTip){
		//װ��ͼ��
		String imagePath = "image/" + name + ".png";
		ImageIcon iconRollover = new ImageIcon(imagePath);
		int w = iconRollover.getIconWidth();
		int h = iconRollover.getIconHeight();
		//ȡ�ð�ť�Ĺ��
		Cursor cursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);
		//���ɰ�͸��ȱʡͼ��
		Image image = screen.createCompatibleImage(w, h, Transparency.TRANSLUCENT);
		
		Graphics2D g = (Graphics2D)image.getGraphics();
		Composite alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f);
		g.setComposite(alpha);
		g.drawImage(iconRollover.getImage(), 0, 0, null);
		g.dispose();
		ImageIcon iconDefault = new ImageIcon(image);
		
		//����ѹ��ͼ��
		image = screen.createCompatibleImage(w, h, Transparency.TRANSLUCENT);
		g.drawImage(iconRollover.getImage(), 2, 2, null);
		g.dispose();
		ImageIcon iconPressed = new ImageIcon(image);
		
		//���ɰ�ť
		JButton button = new JButton();
		button.addActionListener(this);
		button.setIgnoreRepaint(true);
		button.setFocusable(false);
		button.setToolTipText(toolTip);
		button.setBorder(null);
		button.setContentAreaFilled(false);
		button.setCursor(cursor);
		button.setIcon(iconDefault);
		button.setRolloverIcon(iconRollover);
		button.setPressedIcon(iconPressed);
		return button;
	}
}
