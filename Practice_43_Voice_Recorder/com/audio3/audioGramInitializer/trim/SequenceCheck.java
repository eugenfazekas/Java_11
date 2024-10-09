package com.audio3.audioGramInitializer.trim;

public class SequenceCheck {

	int id;
	int frequencyLowerLimit;
	int frequencyUpperLimit;
	int streamStartIndex;
	int streamEndIndex;
	int streamDetailsStartIndex;
	int streamDetailsEndIndex;
	int matchCount;
	int amplitudeLowerLimit;
	int amplitudeUpperLimit;
		
	public SequenceCheck(int id, int frequencyLowerLimit, int frequencyUpperLimit, int matchCount,int amplitudeLowerLimit, int amplitudeUpperLimit) {
		this.id = id;
		this.frequencyLowerLimit = frequencyLowerLimit;
		this.frequencyUpperLimit = frequencyUpperLimit;
		this.matchCount = matchCount;
		this.amplitudeLowerLimit = amplitudeLowerLimit;
		this.amplitudeUpperLimit = amplitudeUpperLimit;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getFrequencyLowerLimit() {
		return frequencyLowerLimit;
	}
	public void setFrequencyLowerLimit(int frequencyLowerLimit) {
		this.frequencyLowerLimit = frequencyLowerLimit;
	}
	public int getFrequencyUpperLimit() {
		return frequencyUpperLimit;
	}
	public void setFrequencyUpperLimit(int frequencyUpperLimit) {
		this.frequencyUpperLimit = frequencyUpperLimit;
	}
	public int getStreamStartIndex() {
		return streamStartIndex;
	}
	public void setStreamStartIndex(int startIndex) {
		this.streamStartIndex = startIndex;
	}
	public int getStreamEndIndex() {
		return streamEndIndex;
	}
	public void setStreamEndIndex(int endIndex) {
		this.streamEndIndex = endIndex;
	}
	
	public int getStreamDetailsStartIndex() {
		return streamDetailsStartIndex;
	}

	public void setStreamDetailsStartIndex(int streamDetailsStartIndex) {
		this.streamDetailsStartIndex = streamDetailsStartIndex;
	}

	public int getStreamDetailsEndIndex() {
		return streamDetailsEndIndex;
	}

	public void setStreamDetailsEndIndex(int streamDetailsEndIndex) {
		this.streamDetailsEndIndex = streamDetailsEndIndex;
	}

	public int getMatchCount() {
		return matchCount;
	}
	public void setMatchCount(int matchCount) {
		this.matchCount = matchCount;
	}

	public int getAmplitudeLowerLimit() {
		return amplitudeLowerLimit;
	}

	public int getAmplitudeUpperLimit() {
		return amplitudeUpperLimit;
	}

	@Override
	public String toString() {
		return "SequenceCheck [id=" + id + ", frequencyLowerLimit=" + frequencyLowerLimit + ", frequencyUpperLimit="
				+ frequencyUpperLimit + ", streamStartIndex=" + streamStartIndex + ", streamEndIndex=" + streamEndIndex
				+ ", streamDetailsStartIndex=" + streamDetailsStartIndex + ", streamDetailsEndIndex="
				+ streamDetailsEndIndex + ", matchCount=" + matchCount + ", amplitudeLowerLimit=" + amplitudeLowerLimit
				+ ", amplitudeUpperLimit=" + amplitudeUpperLimit + "]";
	}	
}
