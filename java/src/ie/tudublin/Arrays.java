package ie.tudublin;

import processing.core.PApplet;



public class Arrays extends PApplet
{
	String[] months = {"JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC"};

	float[] rainfall = {200, 260, 300, 150, 100, 50, 10, 40, 67, 160, 400, 420};

	int mode = 0;
	public float map1(float a, float b, float c, float d, float e)
	{
		float r1 = c -b;
		float r2 = e - d;

		float howFar = a - b;
		return d + (howFar / r1) * r2;
	}

	void randomize()
	{
		for (int i = 0; i < rainfall.length; i++) {
			rainfall[i] = random(500);
		}
	}

	public void settings()
	{
		// changed size for better text
		size(600, 600);

		//String[] m1 = months;
		
		//months[0] = "XXX";
		//print(m1[0]);
		//for (String s : m1) {
		//	println(s);
		//}

		int minIndex = 0;
		for(int i= 0 ; i < rainfall.length ; i ++)
		{
			if (rainfall[i] < rainfall[minIndex])
			{
				minIndex = i;
			}
		}
		
		int maxIndex = 0;
		for(int i= 0 ; i < rainfall.length ; i ++)
		{
			if (rainfall[i] > rainfall[maxIndex])
			{
				maxIndex = i;
			}
		}

		println("The month with the minimum rainfall was " + months[minIndex] + " with " + rainfall[minIndex] + "rain");
		println("The month with the max rainfall was " + months[maxIndex] + " with " + rainfall[maxIndex] + "rain");
		
		
		float tot = 0;
		for(float f:rainfall)
		{
			tot += f;
		}

		float avg = tot / (float) rainfall.length;

		// a b-c d-e;
		println(map1(5, 0, 10, 0, 100));
		// 50

		println(map1(25, 20, 30, 200, 300));
		// 250

		println(map1(26, 25, 35, 0, 100));
		// 10 
	}

	public void setup() {
		colorMode(HSB);
		background(0);
		randomize();
		for(int i = 0; i < months.length; i ++)
		{
			println("Month: " + months[i] + "\t" + rainfall[i]);
		}
	}
	
	public void draw()
	{	
		switch (mode) {
			case 0: {
				background(0);
				textSize(20);
				text("PRESS     FOR\n\n1                BARCHART\n2               TREND CHART\n3                PIE CHART ", 50, 100);
				break;
			}

			case 1: {
				background(0);

				int axisX = 40, axisY = 550, y_axisEndY = height - axisY;
				int x_axisEndX = width - axisX;

				stroke(255);
				fill(255);

				// x axis of barchart
				line(axisX, axisY, axisX, y_axisEndY);

				// y acis of barchart
				line(axisX, axisY, x_axisEndX, axisY);

				// top text 
				textSize(20);
				text("1:  Rainfall barchart", 220, 30);

				// get range so we can randomise rains
				int space = (axisY-y_axisEndY)/months.length;
				float range = max(rainfall);
				int ticks = ceil(range/months.length);
				
				// ticks
				for (int i = 0; i <= months.length ; i++) {
					textSize(15);
					text(ticks*i, 15, 550-space*i);
				}

				space = (x_axisEndX-axisX)/13;
				// text
				for (int i = 0; i < months.length ; i++) {
					textSize(15);
					text(months[i], 50+space*i, 570);
				}

				int barWidth = (x_axisEndX-axisX)/months.length;
				// bars
				for (int i = 0 ; i < months.length ; i++) {
					float barX = axisX + i*barWidth;
					// axisY - height to invert it
					float barHeight = axisY - map(rainfall[i], 0, max(rainfall), axisY, y_axisEndY);
					float barY = axisY - barHeight;

					fill(20*i, 360, 360);

					rect(barX, barY, barWidth, barHeight);
				} 
				break;
			}

			case 2: {
				background(0);
				stroke(255);
				fill(255);

				int axisX = 40, axisY = 550, y_axisEndY = height - axisY;
				int x_axisEndX = width - axisX;
				// x axis of barchart
				line(axisX, axisY, axisX, y_axisEndY);
				// y acis of barchart
				line(axisX, axisY, x_axisEndX, axisY);

				// top text 
				textSize(20);
				text("2:  Rainfall trend chart", 210, 30);

				int space = (axisY-y_axisEndY)/months.length;
				float range = max(rainfall);
				int ticks = ceil(range/months.length);
				// ticks
				for (int i = 0; i <= months.length ; i++) {
					textSize(15);
					text(ticks*i, 15, 550-space*i);
				}
				space = (x_axisEndX-axisX)/months.length;
				// text
				for (int i = 0; i < months.length ; i++) {
					textSize(15);
					text(months[i], 50+space*i, 570);
					stroke(100);
					//line(85+space*i, axisY, 85+space*i, 50);
				}

				// lines
				for (int i = 1 ; i < months.length ; i++) {
					float lineX = axisX + i * space;
					float lineY = (map1(rainfall[i], 0, max(rainfall), axisY, y_axisEndY));
					float line2X = axisX + (i-1) * space;
					float line2Y = (map1(rainfall[i-1], 0, max(rainfall), axisY, y_axisEndY));
					stroke(255);
					line(lineX+space, lineY, line2X+space, line2Y);
				}

				break;
			}

			case 3: {
				background(0);
				stroke(255);
				fill(255);

				textSize(20);
				text("3:  Rainfall piechart", 220, 30);

				int diameter = 400;
				int centreX = width/2, centreY = height/2;
				float startAngle = 0, endAngle = 0;
				circle(centreX, centreY, diameter);
				// get total rain so can get percentage of rain in range 0-360
				float ttlRain = 0;
				for (float f : rainfall) {
					ttlRain += f;
				}
				// so much maths... pie pieces arcs
				for (int i = 0; i < months.length ; i++) {
					float angle = map1(rainfall[i], 0, ttlRain, 0, 360);
					endAngle = radians(angle) + startAngle;
					fill(20*i, 360, 360);
					arc(centreX, centreY, diameter, diameter, startAngle, endAngle);
					
					// text will start at the circumference of the circle + an offset
					float textRad = diameter/2 + 30;
					// find the middle of the andle to find mid to put text
					float middleAngle = (startAngle + endAngle) / 2;
					float x = centreX + textRad * cos(middleAngle);
					float y = centreY + textRad * sin(middleAngle);
					textAlign(CENTER, CENTER);
					text(months[i], x, y);
					// update start angle for next arc
					startAngle = endAngle;	
				}
				break;
			}

			case 4: {

				break;
			}

			case 5: {

				break;
			}

			default: {
				background(0);
				break;
			}
		}
	
	}

	public void keyPressed() {
		if (key >= '0' && key <= '9') {
			mode = key - '0';
		}
		println(mode);
	}
}
