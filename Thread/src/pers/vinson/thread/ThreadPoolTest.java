package pers.vinson.thread;

public class ThreadPoolTest {
	public static void main(String[] args){
		if(args.length != 2){
			System.out.println("Tests the ThreadPool task");
			System.out.println("Usage: java ThreadPoolTest numTasks numThreads");
			System.out.println(" numTasks - integer: number of task to run.");
			System.out.println(" numThreads - integer: number of threads " + "in the thread pool.");
			return;
		}
		
		int numTasks = Integer.parseInt(args[0]);
		int numThreads = Integer.parseInt(args[1]);
		
		//�����̳߳�
		ThreadPool threadPool = new ThreadPool(numThreads);
		
		//������������
		for(int i = 0; i < numThreads; ++i){
			threadPool.runTask(createTask(i));
		}
		
		//�رճز��ȴ������������
		threadPool.join();
	}
	
	/**
	 * ���ɼ򵥿��������񣬴�ӡһ��ID���ȴ�500���룬Ȼ���ٴ�ӡ���ID
	 * @param taskID
	 * @return
	 */
	private static Runnable createTask(final int taskID){
		return new Runnable(){
			public void run(){
				System.out.println("Task " + taskID + ": start");
				//ģ�ⳤʱ������
				try{
					Thread.sleep(500);
				}catch(InterruptedException ex){}
				System.out.println("Task " + taskID + ": end");
			}
		};
	}
}
