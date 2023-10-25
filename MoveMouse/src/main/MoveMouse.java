package main;

import java.awt.Dimension;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;
import java.awt.Toolkit;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MoveMouse {
	public static void main(String[] args) {
		try {
			//수행 간격 구하기
		    Callable<Integer> k = () -> new Scanner(System.in).nextInt();
		    int intervalSecond = 60;		//수행 간격(초)
		    ExecutorService l = Executors.newFixedThreadPool(1);
		    Future<Integer> g;
		    System.out.println("Enter interval in 10 seconds :");
		    g = l.submit(k);
		    Long curTime;	//현재 시간
		    Long strTime = System.currentTimeMillis();	//시작 시간
		    int leftTime;
		    do {
		    	curTime = System.currentTimeMillis();
		    	Integer time = (int) (curTime - strTime);
		    	leftTime = (int) Math.floor(10 - time/1000);
		    	//System.out.print(leftTime + "...");
	            if (g.isDone()) {
	            	intervalSecond = g.get();
	            	break;
	            }
	            Thread.sleep(1000); // 1초만큼 대기
			} while (leftTime > 1);
		    
		    System.out.println("Interval is " + intervalSecond + "second");
		    g.cancel(true);			
			
			Robot robot = new Robot();
			int x,y;	//마우스 좌표
			
			//해상도 구하기
			Dimension res = Toolkit.getDefaultToolkit().getScreenSize();
			
			while(true){ // 종료할 때 까지 무한 반복
				
				//현재 마우스 좌표
				Point point = MouseInfo.getPointerInfo().getLocation();
				x = (int) point.getLocation().getX();
				y = (int) point.getLocation().getY();
				
				//마우스를 화면 중심으로 1픽셀 이동
				x = (x > res.width/2)? x - 1 : x + 1;
				y = (y > res.height/2)? y - 1 : y + 1;
	            
	            robot.mouseMove(x, y); // x,y 좌표로 이동

	            Thread.sleep(intervalSecond * 1000); // intervalSecond * 1초만큼 대기
	        }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}


