package audio.alarm;

import java.util.Locale;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.speech.AudioException;
import javax.speech.Central;
import javax.speech.Engine;
import javax.speech.EngineException;
import javax.speech.EngineStateError;
import javax.speech.synthesis.Synthesizer;
import javax.speech.synthesis.SynthesizerModeDesc;

public class VoiceSynthesizer implements Runnable{

	private static Synthesizer synthesizer;
	public ConcurrentLinkedQueue<String> messages;
	public static volatile boolean bufferIsEmpty;
	
	public VoiceSynthesizer() {
		new Thread(this).start();	
		messages = new ConcurrentLinkedQueue<>();
		bufferIsEmpty = true;
	}
	
	@Override
	public void run() {
		System.out.println("VoiceSynthesizer new Thread created! ThreadName: "+Thread.currentThread().getName());
		buildSynthesizer();
	
		while(true) {	
		
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(!bufferIsEmpty) {
				//System.out.println("bufferIsEmpty: "+bufferIsEmpty);
				startSynthesizer();
				synthesize(messages.poll());
				bufferIsEmpty = true;
			}	
		}		
	}
	
	public void buildSynthesizer() {
		
        System.setProperty( 
                "freetts.voices","com.sun.speech.freetts.en.us"+ ".cmu_us_kal.KevinVoiceDirectory"); 

            try {
				Central.registerEngineCentral( "com.sun.speech.freetts"+ ".jsapi.FreeTTSEngineCentral");
				synthesizer = Central.createSynthesizer(new SynthesizerModeDesc(Locale.US)); 
	        	synthesizer.allocate(); 
				//System.out.println("VoiceSynthesizer builded! ThreadName: "+Thread.currentThread().getName());
			} catch (EngineException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
	}
	
	private void startSynthesizer() {

		//System.out.println("VoiceSynthesizer synthesizer started! synthesizer.getEngineState(): "+synthesizer.getEngineState()+" ThreadName: "+Thread.currentThread().getName());
        try {
			synthesizer.resume();
			//System.out.println("VoiceSynthesizer synthesizer started! ThreadName: "+Thread.currentThread().getName());
		} catch (AudioException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (EngineStateError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	public  void synthesize(String message) {
        synthesizer.speakPlainText(message, null); 
        System.out.println("VoiceSynthesizer synthesizing complete! ThreadName: "+Thread.currentThread().getName()+" Synthesizer message: "+message);
	}
	
	
	
	private  void stopSynthesizer() {
		try {
			System.out.println("VoiceSynthesizer synthesizer stopped 1! synthesizer.getEngineState(): "+synthesizer.getEngineState()+" ThreadName: "+Thread.currentThread().getName()); 
			synthesizer.waitEngineState(Synthesizer.QUEUE_EMPTY); 
			synthesizer.waitEngineState(Engine.PAUSED);
			System.out.println("VoiceSynthesizer synthesizer stopped 2! synthesizer.getEngineState(): "+synthesizer.getEngineState()+" ThreadName: "+Thread.currentThread().getName()); 
		} catch (EngineStateError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}


} 
