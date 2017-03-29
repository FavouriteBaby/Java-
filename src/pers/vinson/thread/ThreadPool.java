package pers.vinson.thread;

import java.util.LinkedList;

public class ThreadPool extends ThreadGroup {
	private boolean isAlive;
	private LinkedList taskQueue;	//˫������
	private int threadID;
	private static int threadPoolID;
	
	
	
	/**
	 * �����µ��̳߳�
	 * @param numThreads ���е��߳���
	 */
	public ThreadPool(int numThreads){
		super("ThreadPool-" + (threadPoolID++));
		setDaemon(true);	//�ı��ػ����е�״̬
		isAlive = true;
		taskQueue = new LinkedList();
		for(int i = 0; i < numThreads; ++i){
			new PooledThread().start();
		}
	}
	
	/**
	 * ������������������������أ������ڳ�����һ�л��߳���ִ��<p>�����յ���˳��ʼִ��
	 * @param task Ҫ���е�����null��ʾ����ȡ����
	 * �������̳߳��Ѿ��رգ����׳�IllegalStateException
	 */
	public synchronized void runTask(Runnable task){
		if(!isAlive){
			throw new IllegalStateException();
		}
		if(task != null){
			taskQueue.add(task);
			notify();
		}
	}
	
	protected synchronized Runnable getTask() throws InterruptedException{
		while(taskQueue.size() == 0){
			if(!isAlive){
				return null;
			}
			wait();
		}
		return (Runnable)taskQueue.removeFirst();
	}
	
	/**
	 * �ر�����̳߳ز��������ء������߳�ֹͣ����ִ���κεȴ������񡣹ر�һ���̳߳�֮������̳߳��ϲ����������κ�����
	 */
	public synchronized void close(){
		if(isAlive){
			isAlive = false;
			taskQueue.clear();
			interrupt();
		}
	}
	
	/**
	 * ����̳߳صȴ����������߳���ɣ�ִ���κεȴ�������
	 */
	public void join(){
		//�������еȴ��̣߳�����̳߳ز��ٻ
		synchronized(this){
			isAlive = false;
			notifyAll();
		}
		
		//�ȴ������߳����
		Thread[] threads = new Thread[activeCount()];
		int count = enumerate(threads);
		for(int i = 0; i < count; ++i){
			try{
				threads[i].join();
			}catch(InterruptedException ex){}
		}
	}
	
	/**
	 * PooledThread���̳߳����е��̣߳�������������(Runnables)
	 * @author peng
	 *
	 */
	private class PooledThread extends Thread{
		public PooledThread(){
			super(ThreadPool.this, "PooledThread-" + (threadID++));
		}
		
		public void run(){
			while(!isInterrupted()){
				//ȡ��Ҫ���е�����
				Runnable task = null;
				try{
					task = getTask();
				}catch(InterruptedException ex){}
				
				//���getTask()����null���жϣ���ر�����̲߳�����
				if(task == null){
					return;
				}
				try{
					task.run();
				}catch(Throwable t){
					uncaughtException(this, t);
				}
			}
		}
	}
}
