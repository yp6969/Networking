package il.ac.telhai.cn.basics;

import javax.management.RuntimeErrorException;

public class PointToPointChannel extends Channel {
	public static final int BITS_PER_BYTE = 8;
	
	private int delay;
	private int inputBuffer;
	private int transit;
	private int delivered;
	private int time;
	
	
	PointToPointChannel(int bandwidth, int delay){
		super(bandwidth);
		this.delay = delay;
		inputBuffer = 0;
		transit = 0;
		delivered = 0;
		time = 0;
	}
	
	
	void send (int bytes){
		if(!isEmpty()) throw new RuntimeErrorException(null, "buffer not empty");
		inputBuffer = bytes;
	}
	
	
	void tick(int msecs) {
		int band;
		
		for (int i = 0; i < msecs ; i++, time++) {
			band = bandwidth;
			if(inputBuffer > 0) {
				if(inputBuffer >= band/BITS_PER_BYTE) {					
					inputBuffer -= band/BITS_PER_BYTE;
				}else {
					band = inputBuffer * BITS_PER_BYTE;
					inputBuffer = 0;
				}
				if (time < delay) {					
					transit += band;
				} else {
					delivered += band/BITS_PER_BYTE;
				}
			} else {
				if(time >= delay) {
					if(transit < band) {
						band = transit;
						transit = 0;
					}else {					
						transit -= band;
					}
					delivered += band/BITS_PER_BYTE;
				}
			}
		}
	}
	
	
	public boolean isEmpty() {
		if(transit == 0 && inputBuffer == 0) {
			time = 0;
			delivered = 0;
			return true;
		}
		return false;
	}
	
	
	public String toString() {
		return "Time Elapsed:" + time + " msecs, Input Buffer:" + inputBuffer + " bytes, In Transit:" + transit + " bits, Delivered:" + delivered + " bytes";
	}
	
	
	int getDelay() {
		return delay;
	}

}