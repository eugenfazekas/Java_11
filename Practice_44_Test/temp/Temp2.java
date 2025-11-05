package temp;

import java.util.Arrays;
import java.util.Comparator;

class Frequency {
	
	private int frequency;
	private float magnitude;
	
	public Frequency(int frequency, float magnitude) {
		this.frequency = frequency;
		this.magnitude = magnitude;
	}

	public int getFrequency() {
		return frequency;
	}

	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}

	public float getMagnitude() {
		return magnitude;
	}

	public void setMagnitude(float magnitude) {
		this.magnitude = magnitude;
	}	
}

public class Temp2 {

	public static void main(String[] args) {
		
//		Frequency[] frequencys1 = new Frequency[3];
//		frequencys1[0] = new Frequency(90, 2.5f);
//		frequencys1[1] = new Frequency(110, 2);
//		frequencys1[2] = new Frequency(130, 1.25f);


//		Frequency[] frequencys1 = new Frequency[6];
//		frequencys1[0] = new Frequency(90, 2.5f);
//		frequencys1[1] = new Frequency(110, 2);
//		frequencys1[2] = new Frequency(130, 1.25f);
//		frequencys1[3] = new Frequency(700, 1f);
//		frequencys1[4] = new Frequency(200, 0.75f);
//		frequencys1[5] = new Frequency(230, 0.5f);
		
//		Frequency[] frequencys2 = new Frequency[3];
//		frequencys2[0] = new Frequency(90, 2.5f);
//		frequencys2[1] = new Frequency(70, 2);
//		frequencys2[2] = new Frequency(110, 2);
		
		Frequency[] frequencys2 = new Frequency[6];
		frequencys2[0] = new Frequency(90, 2.5f);
		frequencys2[1] = new Frequency(70, 2);
		frequencys2[2] = new Frequency(110, 2);
		frequencys2[3] = new Frequency(90, 1.5f);
		frequencys2[4] = new Frequency(70, 1.25f);
		frequencys2[5] = new Frequency(110, 1);
		
		
		System.out.println(getFinalFrequency(frequencys2,3));

	}
	
	public static int getFinalFrequency(Frequency[] frequencys, int avgLength) {
		
	    Arrays.sort(frequencys, new Comparator<Frequency>() {
	        @Override
	        public int compare(Frequency p1, Frequency p2) {
	            return Float.compare(p2.getMagnitude(), p1.getMagnitude());
	        }
	    });
		
		int resultFrequency = frequencys[0].getFrequency(); 
		float mainMagnitude = frequencys[0].getMagnitude();
		int differenceFrequency;
		float tempProcent;
		int differenceProcent;
		
		for(int i = 1; i < avgLength; i++) {
			System.out.println("i : "+i +", resultFrequency: "+resultFrequency + ", frequencys[i].getFrequency() "
					+frequencys[i].getFrequency()+ ", frequencys[i].getMagnitude(): " +frequencys[i].getMagnitude() );
			differenceFrequency = resultFrequency >  frequencys[i].getFrequency() 
				? frequencys[0].getFrequency() - frequencys[i].getFrequency() : frequencys[i].getFrequency() - frequencys[0].getFrequency();
			
			tempProcent = (frequencys[i].getMagnitude() * 100 ) / mainMagnitude;
			
			differenceProcent = (int) ((tempProcent * differenceFrequency )/100);
					
			resultFrequency = (int) (resultFrequency <  frequencys[i].getFrequency() 
				 ? resultFrequency + differenceProcent : resultFrequency - differenceProcent);
			
			System.out.println("i : "+i+", resultFrequency: "+resultFrequency + ", differenceFrequency : "+differenceFrequency
				+ ", tempProcent : "+tempProcent+ ", differenceProcent : "+differenceProcent +", resultFrequency: "+resultFrequency );
		}
		
		return resultFrequency;
	}
	


}
