package voiceRecognition.audio4.audioGramInitializer.trim.sequence;

public class Sequence {

	private int startIndex;
	private int endIndex;
	private int detailsStartIndex;
	private int detailsEndIndex;
		
	public Sequence(int startIndex, int endIndex) {
		
		this.startIndex = startIndex;
		this.endIndex = endIndex;
	}
	
	public int getStartIndex() {
		
		return startIndex;
	}
	
	public void setStartIndex(int startIndex) {
		
		this.startIndex = startIndex;
	}
	
	public int getEndIndex() {
		
		return endIndex;
	}
	
	public void setEndIndex(int endIndex) {
		
		this.endIndex = endIndex;
	}
	
	public int getDetailsStartIndex() {
		
		return detailsStartIndex;
	}
	
	public void setDetailsStartIndex(int detailsStartIndex) {
		
		this.detailsStartIndex = detailsStartIndex;
	}
	public int getDetailsEndIndex() {
		
		return detailsEndIndex;
	}
	
	public void setDetailsEndIndex(int detailsEndIndex) {
		
		this.detailsEndIndex = detailsEndIndex;
	}
	
	@Override
	public String toString() {
		return "Sequence [startIndex="+startIndex+", endIndex="+endIndex+", detailsStartIndex="
				+ detailsStartIndex + ", detailsEndIndex=" + detailsEndIndex + "]";
	}		
}
