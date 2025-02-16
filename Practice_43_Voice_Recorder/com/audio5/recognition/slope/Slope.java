package com.audio5.recognition.slope;

public class Slope {

	int startHPosition;
	int startVPosition;
	int endHPosition;
	int endVPosition;
	int hLength;
	int vLength;
	int mainLength;
	int avgSlopedirection;
	int hPosition;
	int vPosition;
	
	public Slope(int startHPosition, int startVPosition) {
		
		this.startHPosition = startHPosition;
		this.startVPosition = startVPosition;
	}

	public Slope(int startHPosition, int startVPosition, int endHPosition, int endVPosition, int hLength) {
		this.startHPosition = startHPosition;
		this.startVPosition = startVPosition;
		this.endHPosition = endHPosition;
		this.endVPosition = endVPosition;
		this.hLength = hLength;
	}

	public int getStartHPosition() {
		return startHPosition;
	}

	public void setStartHPosition(int startHPosition) {
		this.startHPosition = startHPosition;
	}

	public int getStartVPosition() {
		return startVPosition;
	}

	public void setStartVPosition(int startVPosition) {
		this.startVPosition = startVPosition;
	}

	public int getEndHPosition() {
		return endHPosition;
	}

	public void setEndHPosition(int endHPosition) {
		this.endHPosition = endHPosition;
	}

	public int getEndVPosition() {
		return endVPosition;
	}

	public void setEndVPosition(int endVPosition) {
		this.endVPosition = endVPosition;
	}

	public int gethLength() {
		return hLength;
	}

	public void sethLength(int hLength) {
		this.hLength = hLength;
	}

	public int getvLength() {
		return vLength;
	}

	public void setvLength(int vLength) {
		this.vLength = vLength;
	}

	public int getMainLength() {
		return mainLength;
	}

	public void setMainLength(int mainLength) {
		this.mainLength = mainLength;
	}

	public int getAvgSlopedirection() {
		return avgSlopedirection;
	}

	public void setAvgSlopedirection(int avgSlopedirection) {
		this.avgSlopedirection = avgSlopedirection;
	}

	public int gethPosition() {
		return hPosition;
	}

	public void sethPosition(int hPosition) {
		this.hPosition = hPosition;
	}

	public int getvPosition() {
		return vPosition;
	}

	public void setvPosition(int vPosition) {
		this.vPosition = vPosition;
	}

	@Override
	public String toString() {
		return "Slope [startHPosition=" + startHPosition + ", startVPosition=" + startVPosition + ", endHPosition="
				+ endHPosition + ", endVPosition=" + endVPosition + ", hLength=" + hLength + ", vLength=" + vLength
				+ ", mainLength=" + mainLength + ", avgSlopedirection=" + avgSlopedirection + ", hPosition=" + hPosition
				+ ", vPosition=" + vPosition + "]";
	}
	
	
}
