package com.brackeen.javagamebook.test;
import java.io.*;
import javax.sound.sampled.*;

public class SimpleSoundPlayer {
	public static void main(String[] args){
		SimpleSoundPlayer sound = new SimpleSoundPlayer("sound/star.wav");
		InputStream stream = new ByteArrayInputStream(sound.getSamples());
		sound.play(stream);
		System.exit(0);
	}
	
	private AudioFormat format;
	private byte[] samples;
	
	//���ļ�������
	public SimpleSoundPlayer(String fileName){
		try{
			//����Ƶ������
			AudioInputStream stream = AudioSystem.getAudioInputStream(new File(fileName));
			format = stream.getFormat();
			//ȡ����Ƶ����
			samples = getSamples(stream);
		}catch(UnsupportedAudioFileException ex){
			ex.printStackTrace();
		}catch(IOException ex){
			ex.printStackTrace();
		}
	}
	
	//ȡ�������������ֽ�����
	public byte[] getSamples(){
		return samples;
	}
	
	//��AudioInputStreamȡ���������ֽ�����
	public byte[] getSamples(AudioInputStream audioStream){
		//ȡ��Ҫ��ȡ���ֽ���
		int length = (int)(audioStream.getFrameLength() * format.getFrameSize());
		
		//��ȡ������
		byte[] samples = new byte[length];
		DataInputStream is = new DataInputStream(audioStream);
		try{
			is.readFully(samples);
		}catch(IOException ex){
			ex.printStackTrace();
		}
		return samples;
	}
	
	//���������������������Ҫ������������ŷ���
	public void play(InputStream source){
		//�ö̵�100ms������ʵʱ�ı�������
		int bufferSize = format.getFrameSize() * Math.round(format.getSampleRate() / 10);
		byte[] buffer = new byte[bufferSize];
		
		//����Ҫ���ŵ�Line
		SourceDataLine line;
		try{
			DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
			line = (SourceDataLine)AudioSystem.getLine(info);
			line.open(format, bufferSize);
		}catch(LineUnavailableException ex){
			ex.printStackTrace();
			return;
		}
		
		line.start();
		try{
			int numBytesRead = 0;
			while(numBytesRead != -1){
				numBytesRead = source.read(buffer, 0, buffer.length);
				if(numBytesRead != -1)
					line.write(buffer, 0, numBytesRead);
			}
		}catch(IOException ex){
			ex.printStackTrace();
		}
		line.drain();
		line.close();
	}
}
