package com.brackeen.javagamebook.graphics;

import javax.swing.JComponent;
import javax.swing.RepaintManager;

//NullRepaintManager�ǲ������ػ���RepaintManager������Ӧ�ó������ɽ������л���
public class NullRepaintManager extends RepaintManager {
	//��װNullRepaintManager
	public static void install(){
		RepaintManager repaintManager = new NullRepaintManager();
		repaintManager.setDoubleBufferingEnabled(false);
		RepaintManager.setCurrentManager(repaintManager);
	}
	
	public void addDirtyRegion(JComponent c, int x, int y, int w, int h){}	
	public void addInvalidComponent(JComponent c){}
	public void markCompletelyDirty(JComponent c){}
	public void paintDirtyRegoins(){}
}
