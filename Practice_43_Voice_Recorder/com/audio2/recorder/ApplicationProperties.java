package com.audio2.recorder;

import javax.sound.sampled.AudioFormat;

public class ApplicationProperties {
	
     final AudioFormat.Encoding ENCODING = AudioFormat.Encoding.PCM_SIGNED;
     final float RATE = 44100.0f;
     final int CHANNELS = 1;
    //public final int SAMPLE_SIZE = 8;
     final int SAMPLE_SIZE = 16;
     final boolean BIG_ENDIAN = true;
}
