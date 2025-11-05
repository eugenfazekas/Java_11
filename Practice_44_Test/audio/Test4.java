package audio;

import java.util.Arrays;

class VoiceRecognitionSequence {
	
	private int startH;
	private int startV;
	private int edgeH;
	private int edgeV;
	private int endH;
	private int endV;
	
	private int leftAvgEdgeH;
	private int leftAvgEdgeV;
		
	private int rightAvgEdgeH;
	private int rightAvgEdgeV;
	
	private int leftAngle;
	private int rightAngle;
	
	private int leftLength;	
	private int rightLength;	
	private int length;
	
	public VoiceRecognitionSequence(int startH, int startV, int edgeH, int edgeV, int endH, int endV) {
		
		this.startH = startH;
		this.startV = startV;
		this.edgeH = edgeH;
		this.edgeV = edgeV;
		this.endH = endH;
		this.endV = endV;
		
		setBaseProperties(); 
		System.out.println(toString());
	}	
	
	public void setBaseProperties() {
		
		this.leftAvgEdgeH = (this.startH + this.edgeH) /2 ;
		this.leftAvgEdgeV = (this.startV + this.edgeV) /2  ;
			
		this.rightAvgEdgeH = (this.endH  + this.edgeH)  /2;
		this.rightAvgEdgeV = (this.endV  + this.edgeV)  /2;
		
		this.leftAngle  = Test4.getDegree(this.edgeV - this.startV , this.edgeH - this.startH,  this.leftAvgEdgeH);
		this.rightAngle = Test4.getDegree(this.endV  - this.edgeV   , this.endH - this.edgeH, this.rightAvgEdgeH);
		
		this.leftLength = this.edgeH - this.startH;	
		this.rightLength = this.endH - this.edgeH;	
		this.length = this.endH - this.startH;
	}

	@Override
	public String toString() {
		return "Sequence2 [startH=" + startH + ", startV=" + startV + ", edgeH=" + edgeH + ", edgeV=" + edgeV
				+ ", endH=" + endH + ", endV=" + endV + ", leftAvgEdgeH=" + leftAvgEdgeH + ", leftAvgEdgeV="
				+ leftAvgEdgeV + ", rightAvgEdgeH=" + rightAvgEdgeH + ", rightAvgEdgeV=" + rightAvgEdgeV
				+ ", leftAngle=" + leftAngle + ", rightAngle=" + rightAngle + ", leftLength=" + leftLength
				+ ", rightLength=" + rightLength + ", length=" + length + "]";
	}
}

public class Test4 {

	static int[] mix0 = new int[] {3, 2, 66, 3, 2, 66, 3, 2, 66, 3, 2, 66, 4, 2, 50, 4, 2, 50, 4, 3, 75, 4, 4, 100, 4, 4, 100, 4, 4, 100, 4, 4, 100, 4, 4, 100, 4, 4, 100, 4, 3, 75, 4, 2, 50, 4, 1, 25, 4, 1, 25, 4, 5, 125, 6, 20, 333, 8, 35, 437, 11, 52, 472, 14, 66, 471, 17, 69, 405, 20, 69, 345, 24, 67, 279, 27, 64, 237, 30, 60, 200, 30, 57, 190, 28, 55, 196, 25, 50, 200, 23, 45, 195, 22, 41, 186, 21, 37, 176, 21, 35, 166, 20, 30, 150, 18, 28, 155, 18, 28, 155, 19, 39, 205, 23, 54, 234, 29, 70, 241, 42, 81, 192, 58, 82, 141, 77, 83, 107, 93, 83, 89, 96, 81, 84, 96, 81, 84, 84, 80, 95, 72, 80, 111, 63, 80, 126, 63, 80, 126, 65, 80, 123, 66, 80, 121, 66, 80, 121, 61, 79, 129, 56, 78, 139, 51, 78, 152, 48, 78, 162, 46, 78, 169, 44, 78, 177, 42, 77, 183, 38, 74, 194, 34, 72, 211, 30, 66, 220, 29, 62, 213, 28, 58, 207, 28, 53, 189, 27, 52, 192, 25, 49, 196, 25, 45, 180, 23, 43, 186, 21, 35, 166, 20, 28, 140, 18, 23, 127, 17, 18, 105, 15, 16, 106, 13, 16, 123, 10, 16, 160, 8, 13, 162, 6, 10, 166, 4, 6, 150, 4, 3, 75, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 2, 1, 50, 2, 1, 50};
	static int[] mix1 = new int[] {4, 1, 25, 4, 1, 25, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 4, 1, 25, 4, 1, 25, 4, 1, 25, 4, 1, 25, 3, 1, 33, 3, 1, 33, 4, 1, 25, 5, 1, 20, 6, 7, 116, 8, 18, 225, 10, 35, 350, 13, 51, 392, 17, 62, 364, 19, 67, 352, 21, 67, 319, 23, 67, 291, 25, 64, 256, 25, 61, 244, 25, 55, 220, 25, 51, 204, 23, 44, 191, 23, 40, 173, 23, 33, 143, 22, 30, 136, 20, 25, 125, 17, 25, 147, 17, 30, 176, 17, 44, 258, 25, 63, 252, 40, 83, 207, 61, 87, 142, 82, 87, 106, 95, 86, 90, 95, 85, 89, 88, 83, 94, 77, 82, 106, 70, 81, 115, 68, 80, 117, 68, 80, 117, 71, 81, 114, 72, 82, 113, 72, 82, 113, 71, 83, 116, 67, 83, 123, 63, 83, 131, 60, 83, 138, 59, 82, 138, 58, 81, 139, 58, 80, 137, 58, 79, 136, 56, 79, 141, 52, 80, 153, 48, 80, 166, 45, 80, 177, 43, 80, 186, 43, 78, 181, 42, 76, 180, 40, 72, 180, 37, 68, 183, 34, 62, 182, 30, 56, 186, 27, 48, 177, 25, 42, 168, 20, 37, 185, 16, 28, 175, 11, 23, 209, 8, 15, 187, 6, 8, 133, 5, 4, 80, 5, 2, 40, 5, 2, 40, 4, 1, 25, 4, 1, 25, 4, 1, 25, 4, 1, 25, 4, 1, 25, 4, 1, 25, 5, 2, 40, 5, 3, 60, 5, 4, 80, 4, 4, 100, 4, 4, 100, 4, 3, 75, 5, 3, 60, 5, 3, 60, 5, 3, 60, 4, 3, 75, 4, 3, 75, 4, 3, 75, 4, 2, 50, 5, 2, 40, 5, 2, 40, 5, 2, 40};
	static int[] mix2 = new int[] {2, 1, 50, 2, 1, 50, 2, 1, 50, 2, 1, 50, 2, 1, 50, 2, 1, 50, 2, 1, 50, 2, 1, 50, 2, 1, 50, 2, 1, 50, 2, 1, 50, 2, 1, 50, 2, 1, 50, 2, 1, 50, 2, 1, 50, 2, 1, 50, 2, 1, 50, 2, 1, 50, 2, 1, 50, 2, 1, 50, 2, 1, 50, 2, 1, 50, 3, 1, 33, 4, 3, 75, 6, 16, 266, 8, 37, 462, 11, 55, 500, 16, 69, 431, 21, 69, 328, 25, 67, 268, 27, 64, 237, 29, 63, 217, 30, 61, 203, 32, 56, 175, 32, 53, 165, 31, 51, 164, 26, 47, 180, 23, 43, 186, 20, 40, 200, 19, 32, 168, 18, 29, 161, 16, 29, 181, 15, 25, 166, 14, 25, 178, 14, 29, 207, 14, 37, 264, 18, 50, 277, 26, 63, 242, 41, 70, 170, 60, 73, 121, 78, 74, 94, 91, 77, 84, 91, 77, 84, 86, 77, 89, 72, 77, 106, 56, 74, 132, 45, 71, 157, 38, 69, 181, 35, 67, 191, 33, 67, 203, 33, 67, 203, 31, 67, 216, 30, 67, 223, 28, 65, 232, 26, 65, 250, 25, 62, 248, 25, 62, 248, 25, 60, 240, 25, 60, 240, 25, 60, 240, 25, 60, 240, 26, 62, 238, 27, 63, 233, 27, 63, 233, 27, 62, 229, 26, 62, 238, 26, 59, 226, 25, 57, 228, 24, 55, 229, 22, 51, 231, 20, 47, 235, 18, 40, 222, 15, 31, 206, 13, 25, 192, 10, 22, 220, 7, 17, 242, 5, 14, 280, 3, 8, 266, 2, 3, 150, 2, 1, 50, 2, 1, 50, 2, 1, 50, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 2, 1, 50, 2, 1, 50};
	static int[] mix3 = new int[] {3, 1, 33, 2, 1, 50, 2, 1, 50, 2, 1, 50, 2, 1, 50, 2, 1, 50, 2, 1, 50, 2, 1, 50, 2, 1, 50, 2, 1, 50, 2, 1, 50, 2, 1, 50, 2, 1, 50, 2, 1, 50, 2, 1, 50, 2, 1, 50, 2, 1, 50, 3, 7, 233, 4, 14, 350, 6, 19, 316, 7, 33, 471, 10, 45, 450, 12, 54, 450, 15, 73, 486, 17, 78, 458, 19, 78, 410, 19, 78, 410, 20, 71, 355, 20, 64, 320, 20, 58, 290, 20, 53, 265, 19, 48, 252, 18, 45, 250, 17, 42, 247, 16, 38, 237, 16, 37, 231, 16, 31, 193, 15, 31, 206, 15, 35, 233, 15, 48, 320, 21, 64, 304, 34, 77, 226, 55, 84, 152, 77, 84, 109, 92, 84, 91, 92, 84, 91, 85, 82, 96, 75, 81, 108, 67, 80, 119, 63, 80, 126, 59, 82, 138, 53, 82, 154, 46, 81, 176, 42, 79, 188, 40, 78, 195, 39, 78, 200, 39, 80, 205, 38, 80, 210, 37, 80, 216, 37, 80, 216, 35, 78, 222, 33, 78, 236, 30, 73, 243, 28, 70, 250, 27, 66, 244, 26, 62, 238, 26, 60, 230, 25, 57, 228, 24, 53, 220, 24, 49, 204, 23, 45, 195, 22, 36, 163, 20, 30, 150, 17, 25, 147, 14, 19, 135, 11, 18, 163, 9, 15, 166, 7, 11, 157, 5, 10, 200, 5, 9, 180, 5, 9, 180, 4, 9, 225, 4, 6, 150, 3, 4, 133, 3, 2, 66, 3, 2, 66, 3, 2, 66, 3, 3, 100, 3, 3, 100, 4, 4, 100, 4, 4, 100, 4, 4, 100, 4, 4, 100, 4, 4, 100, 4, 4, 100, 4, 4, 100, 4, 4, 100, 4, 4, 100, 4, 5, 125, 4, 5, 125, 5, 5, 100, 5, 5, 100, 6, 5, 83};
	static int[] mix4 = new int[] {3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 2, 1, 50, 2, 1, 50, 2, 1, 50, 2, 1, 50, 2, 1, 50, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 5, 8, 160, 7, 18, 257, 11, 30, 272, 15, 46, 306, 18, 54, 300, 24, 59, 245, 28, 62, 221, 32, 62, 193, 32, 61, 190, 31, 61, 196, 29, 56, 193, 27, 53, 196, 27, 49, 181, 27, 45, 166, 25, 43, 172, 23, 38, 165, 21, 31, 147, 21, 31, 147, 21, 32, 152, 24, 42, 175, 31, 57, 183, 42, 70, 166, 60, 78, 130, 79, 81, 102, 93, 82, 88, 93, 82, 88, 92, 81, 88, 81, 81, 100, 68, 77, 113, 57, 73, 128, 49, 69, 140, 43, 64, 148, 41, 64, 156, 39, 64, 164, 38, 63, 165, 38, 63, 165, 38, 63, 165, 37, 60, 162, 35, 60, 171, 33, 57, 172, 31, 52, 167, 28, 49, 175, 27, 47, 174, 27, 45, 166, 27, 44, 162, 28, 44, 157, 28, 43, 153, 28, 41, 146, 29, 38, 131, 30, 34, 113, 31, 28, 90, 31, 25, 80, 30, 23, 76, 28, 21, 75, 24, 21, 87, 21, 20, 95, 18, 20, 111, 15, 20, 133, 12, 19, 158, 9, 14, 155, 6, 11, 183, 4, 5, 125, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33};
	static int[] mix5 = new int[] {6, 1, 16, 6, 1, 16, 6, 1, 16, 6, 1, 16, 6, 1, 16, 5, 1, 20, 5, 1, 20, 5, 1, 20, 4, 1, 25, 4, 1, 25, 4, 1, 25, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 4, 1, 25, 7, 1, 14, 11, 5, 45, 18, 20, 111, 27, 34, 125, 37, 46, 124, 46, 51, 110, 51, 51, 100, 54, 48, 88, 54, 44, 81, 54, 40, 74, 52, 37, 71, 50, 31, 62, 48, 26, 54, 47, 21, 44, 46, 18, 39, 44, 18, 40, 40, 18, 45, 35, 18, 51, 27, 18, 66, 22, 19, 86, 22, 29, 131, 30, 39, 130, 46, 55, 119, 66, 62, 93, 81, 64, 79, 90, 66, 73, 91, 66, 72, 91, 66, 72, 90, 65, 72, 87, 62, 71, 83, 58, 69, 77, 52, 67, 72, 47, 65, 70, 42, 60, 70, 37, 52, 71, 35, 49, 73, 32, 43, 74, 31, 41, 75, 28, 37, 76, 27, 35, 77, 27, 35, 77, 24, 31, 76, 22, 28, 75, 20, 26, 74, 18, 24, 74, 18, 24, 74, 18, 24, 74, 18, 24, 74, 18, 24, 73, 18, 24, 72, 18, 25, 69, 19, 27, 66, 19, 28, 63, 18, 28, 59, 17, 28, 55, 16, 29, 49, 15, 30, 44, 15, 34, 39, 15, 38, 35, 15, 42, 31, 15, 48, 27, 15, 55, 24, 15, 62, 21, 15, 71, 18, 14, 77, 15, 10, 66, 13, 7, 53, 11, 3, 27, 9, 1, 11, 7, 1, 14, 6, 1, 16, 4, 1, 25, 4, 1, 25, 4, 1, 25, 4, 1, 25, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33};
	static int[] mix6 = new int[] {2, 1, 50, 2, 1, 50, 2, 1, 50, 2, 1, 50, 2, 1, 50, 2, 1, 50, 2, 1, 50, 2, 1, 50, 2, 1, 50, 2, 1, 50, 2, 1, 50, 2, 1, 50, 2, 1, 50, 2, 1, 50, 3, 1, 33, 3, 1, 33, 3, 1, 33, 4, 1, 25, 4, 1, 25, 4, 1, 25, 6, 1, 16, 9, 5, 55, 15, 17, 113, 22, 27, 122, 31, 36, 116, 39, 39, 100, 46, 39, 84, 52, 38, 73, 56, 38, 67, 56, 38, 67, 53, 36, 67, 48, 36, 75, 42, 35, 83, 36, 35, 97, 32, 35, 109, 27, 29, 107, 23, 24, 104, 20, 19, 95, 16, 14, 87, 15, 14, 93, 14, 14, 100, 13, 15, 115, 13, 15, 115, 15, 22, 146, 22, 32, 145, 33, 44, 133, 47, 59, 125, 60, 67, 111, 70, 72, 102, 74, 74, 100, 77, 74, 96, 80, 73, 91, 85, 70, 82, 91, 70, 76, 91, 69, 75, 91, 68, 74, 83, 68, 81, 73, 64, 87, 63, 62, 98, 56, 58, 103, 51, 55, 107, 48, 54, 112, 48, 52, 108, 46, 52, 113, 44, 52, 118, 41, 51, 124, 38, 50, 131, 37, 48, 129, 37, 47, 127, 37, 45, 121, 35, 44, 125, 33, 43, 130, 31, 43, 138, 29, 43, 148, 28, 40, 142, 28, 38, 135, 28, 30, 107, 29, 24, 82, 31, 21, 67, 32, 19, 59, 32, 19, 59, 31, 18, 58, 29, 17, 58, 27, 15, 55, 24, 15, 62, 23, 15, 65, 20, 15, 75, 17, 15, 88, 15, 15, 100, 13, 14, 107, 11, 10, 90, 10, 7, 70, 8, 3, 37, 7, 1, 14, 6, 1, 16, 5, 1, 20, 4, 1, 25, 4, 1, 25, 4, 1, 25, 4, 1, 25, 5, 1, 20, 5, 1, 20, 5, 1, 20, 5, 1, 20, 5, 1, 20, 6, 1, 16, 6, 1, 16, 6, 1, 16, 6, 1, 16, 6, 1, 16, 6, 1, 16, 6, 1, 16, 7, 1, 14, 7, 1, 14, 7, 1, 14, 6, 1, 16, 6, 1, 16, 5, 1, 20, 4, 1, 25, 3, 1, 33, 3, 1, 33, 2, 1, 50, 2, 1, 50};
	static int[] mix7 = new int[] {9, 1, 11, 9, 1, 11, 9, 1, 11, 8, 1, 12, 8, 1, 12, 8, 1, 12, 8, 1, 12, 8, 1, 12, 7, 1, 14, 7, 1, 14, 6, 1, 16, 6, 1, 16, 5, 1, 20, 4, 1, 25, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 6, 1, 16, 9, 1, 11, 12, 14, 116, 16, 29, 181, 18, 45, 250, 21, 61, 290, 22, 63, 286, 22, 63, 286, 22, 63, 286, 19, 47, 247, 17, 31, 182, 15, 24, 160, 15, 24, 160, 16, 25, 156, 17, 33, 194, 17, 33, 194, 18, 33, 183, 18, 33, 183, 18, 27, 150, 17, 23, 135, 16, 17, 106, 15, 12, 80, 15, 12, 80, 16, 28, 175, 21, 50, 238, 31, 68, 219, 47, 89, 189, 68, 89, 130, 86, 86, 100, 94, 85, 90, 94, 82, 87, 86, 78, 90, 79, 75, 94, 79, 73, 92, 83, 73, 87, 85, 75, 88, 85, 77, 90, 80, 80, 100, 74, 83, 112, 70, 85, 121, 67, 87, 129, 65, 88, 135, 63, 88, 139, 61, 88, 144, 58, 88, 151, 52, 88, 169, 44, 87, 197, 35, 87, 248, 26, 87, 334, 21, 87, 414, 19, 87, 457, 18, 87, 483, 18, 87, 483, 17, 76, 447, 16, 64, 400, 14, 42, 300, 12, 19, 158, 11, 9, 81, 11, 1, 9, 10, 1, 10, 9, 1, 11, 9, 1, 11, 9, 1, 11, 9, 1, 11, 9, 1, 11, 10, 1, 10, 10, 1, 10, 11, 1, 9, 12, 1, 8, 12, 1, 8, 13, 1, 7, 13, 1, 7, 14, 1, 7, 14, 1, 7, 15, 1, 6, 15, 1, 6, 14, 1, 7, 14, 1, 7, 13, 1, 7, 12, 1, 8, 11, 1, 9, 9, 1, 11, 7, 1, 14, 6, 1, 16, 4, 1, 25, 4, 1, 25, 4, 1, 25, 6, 1, 16, 8, 1, 12, 9, 1, 11, 10, 1, 10, 11, 1, 9, 12, 1, 8, 13, 1, 7, 13, 1, 7, 14, 1, 7};
	static int[] mix8 = new int[] {2, 1, 50, 2, 1, 50, 2, 1, 50, 2, 1, 50, 2, 1, 50, 2, 1, 50, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 2, 1, 50, 2, 1, 50, 2, 1, 50, 2, 1, 50, 2, 1, 50, 2, 1, 50, 3, 3, 100, 5, 10, 200, 10, 19, 190, 17, 28, 164, 25, 36, 144, 32, 39, 121, 38, 41, 107, 42, 42, 100, 46, 43, 93, 50, 44, 88, 53, 45, 84, 55, 45, 81, 55, 44, 80, 55, 44, 80, 53, 42, 79, 48, 40, 83, 42, 39, 92, 35, 37, 105, 28, 36, 128, 22, 34, 154, 16, 29, 181, 12, 24, 200, 9, 18, 200, 8, 13, 162, 8, 11, 137, 8, 10, 125, 10, 10, 100, 12, 10, 83, 16, 19, 118, 25, 27, 108, 38, 37, 97, 55, 47, 85, 71, 51, 71, 84, 56, 66, 91, 60, 65, 94, 62, 65, 94, 62, 65, 89, 60, 67, 83, 57, 68, 78, 54, 69, 73, 53, 72, 70, 52, 74, 64, 51, 79, 58, 50, 86, 51, 48, 94, 46, 47, 102, 43, 47, 109, 42, 48, 114, 41, 49, 119, 41, 49, 119, 41, 49, 119, 41, 48, 117, 40, 48, 120, 38, 48, 126, 35, 46, 131, 31, 42, 135, 28, 37, 132, 25, 33, 132, 24, 30, 125, 22, 27, 122, 20, 26, 130, 19, 24, 126, 19, 24, 126, 19, 24, 126, 20, 25, 125, 22, 26, 118, 22, 26, 118, 22, 22, 100, 19, 19, 100, 15, 14, 93, 12, 10, 83, 9, 9, 100, 8, 6, 75, 8, 5, 62, 10, 3, 30, 11, 3, 27, 12, 3, 25, 12, 3, 25, 11, 3, 27, 10, 3, 30, 8, 3, 37, 7, 2, 28, 5, 2, 40, 3, 2, 66};
	
	
	static int[] array0 = new int[] {3, 3, 3, 3, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 6, 8, 11, 14, 17, 20, 24, 27, 30, 30, 28, 25, 23, 22, 21, 21, 20, 18, 18, 19, 23, 29, 42, 58, 77, 93, 96, 96, 84, 72, 63, 63, 65, 66, 66, 61, 56, 51, 48, 46, 44, 42, 38, 34, 30, 29, 28, 28, 27, 25, 25, 23, 21, 20, 18, 17, 15, 13, 10, 8, 6, 4, 4, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 2, 2};
	static int[] array1 = new int[] {4, 4, 3, 3, 3, 3, 3, 3, 3, 3, 4, 4, 4, 4, 3, 3, 4, 5, 6, 8, 10, 13, 17, 19, 21, 23, 25, 25, 25, 25, 23, 23, 23, 22, 20, 17, 17, 17, 25, 40, 61, 82, 95, 95, 88, 77, 70, 68, 68, 71, 72, 72, 71, 67, 63, 60, 59, 58, 58, 58, 56, 52, 48, 45, 43, 43, 42, 40, 37, 34, 30, 27, 25, 20, 16, 11, 8, 6, 5, 5, 5, 4, 4, 4, 4, 4, 4, 5, 5, 5, 4, 4, 4, 5, 5, 5, 4, 4, 4, 4, 5, 5, 5};
	static int[] array2 = new int[] {2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 3, 4, 6, 8, 11, 16, 21, 25, 27, 29, 30, 32, 32, 31, 26, 23, 20, 19, 18, 16, 15, 14, 14, 14, 18, 26, 41, 60, 78, 91, 91, 86, 72, 56, 45, 38, 35, 33, 33, 31, 30, 28, 26, 25, 25, 25, 25, 25, 25, 26, 27, 27, 27, 26, 26, 25, 24, 22, 20, 18, 15, 13, 10, 7, 5, 3, 2, 2, 2, 2, 3, 3, 3, 3, 3, 3, 3, 3, 3, 2, 2};
	static int[] array3 = new int[] {3, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 3, 4, 6, 7, 10, 12, 15, 17, 19, 19, 20, 20, 20, 20, 19, 18, 17, 16, 16, 16, 15, 15, 15, 21, 34, 55, 77, 92, 92, 85, 75, 67, 63, 59, 53, 46, 42, 40, 39, 39, 38, 37, 37, 35, 33, 30, 28, 27, 26, 26, 25, 24, 24, 23, 22, 20, 17, 14, 11, 9, 7, 5, 5, 5, 4, 4, 3, 3, 3, 3, 3, 3, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 5, 5, 6};
	static int[] array4 = new int[] {3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 2, 2, 2, 2, 2, 3, 3, 3, 3, 5, 7, 11, 15, 18, 24, 28, 32, 32, 31, 29, 27, 27, 27, 25, 23, 21, 21, 21, 24, 31, 42, 60, 79, 93, 93, 92, 81, 68, 57, 49, 43, 41, 39, 38, 38, 38, 37, 35, 33, 31, 28, 27, 27, 27, 28, 28, 28, 29, 30, 31, 31, 30, 28, 24, 21, 18, 15, 12, 9, 6, 4, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3};
	static int[] array5 = new int[] {6, 6, 6, 6, 6, 5, 5, 5, 4, 4, 4, 3, 3, 3, 3, 3, 3, 3, 4, 7, 11, 18, 27, 37, 46, 51, 54, 54, 54, 52, 50, 48, 47, 46, 44, 40, 35, 27, 22, 22, 30, 46, 66, 81, 90, 91, 91, 90, 87, 83, 77, 72, 70, 70, 71, 73, 74, 75, 76, 77, 77, 76, 75, 74, 74, 74, 74, 74, 73, 72, 69, 66, 63, 59, 55, 49, 44, 39, 35, 31, 27, 24, 21, 18, 15, 13, 11, 9, 7, 6, 4, 4, 4, 4, 3, 3, 3, 3, 3, 3, 3, 3, 3};
	static int[] array6 = new int[] {2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 3, 3, 3, 4, 4, 4, 6, 9, 15, 22, 31, 39, 46, 52, 56, 56, 53, 48, 42, 36, 32, 27, 23, 20, 16, 15, 14, 13, 13, 15, 22, 33, 47, 60, 70, 74, 77, 80, 85, 91, 91, 91, 83, 73, 63, 56, 51, 48, 48, 46, 44, 41, 38, 37, 37, 37, 35, 33, 31, 29, 28, 28, 28, 29, 31, 32, 32, 31, 29, 27, 24, 23, 20, 17, 15, 13, 11, 10, 8, 7, 6, 5, 4, 4, 4, 4, 5, 5, 5, 5, 5, 6, 6, 6, 6, 6, 6, 6, 7, 7, 7, 6, 6, 5, 4, 3, 3, 2, 2};
	static int[] array7 = new int[] {9, 9, 9, 8, 8, 8, 8, 8, 7, 7, 6, 6, 5, 4, 3, 3, 3, 3, 6, 9, 12, 16, 18, 21, 22, 22, 22, 19, 17, 15, 15, 16, 17, 17, 18, 18, 18, 17, 16, 15, 15, 16, 21, 31, 47, 68, 86, 94, 94, 86, 79, 79, 83, 85, 85, 80, 74, 70, 67, 65, 63, 61, 58, 52, 44, 35, 26, 21, 19, 18, 18, 17, 16, 14, 12, 11, 11, 10, 9, 9, 9, 9, 9, 10, 10, 11, 12, 12, 13, 13, 14, 14, 15, 15, 14, 14, 13, 12, 11, 9, 7, 6, 4, 4, 4, 6, 8, 9, 10, 11, 12, 13, 13, 14};
	static int[] array8 = new int[] {2, 2, 2, 2, 2, 2, 3, 3, 3, 3, 3, 3, 3, 2, 2, 2, 2, 2, 2, 3, 5, 10, 17, 25, 32, 38, 42, 46, 50, 53, 55, 55, 55, 53, 48, 42, 35, 28, 22, 16, 12, 9, 8, 8, 8, 10, 12, 16, 25, 38, 55, 71, 84, 91, 94, 94, 89, 83, 78, 73, 70, 64, 58, 51, 46, 43, 42, 41, 41, 41, 41, 40, 38, 35, 31, 28, 25, 24, 22, 20, 19, 19, 19, 20, 22, 22, 22, 19, 15, 12, 9, 8, 8, 10, 11, 12, 12, 11, 10, 8, 7, 5, 3};
	

	private static int i;
	private static int j;
	private static int positiveCounter;
	private static int equalCounter;
	private static int negativeCounter;
	private static boolean start;
	
	private static int checkLength;
	private static int sequenceLength;
	private static boolean nextNegativeCheck;
	
	private static int lastnegativeEdgeIndex;
	private static int lastnegativeEdgeIndexValue;
	
	private static int[] points = new int[150];
	private static int pointsCounter = 0;
	
	private static VoiceRecognitionSequence[] sequences;
	private static int sequenceCounter;
	
	
	private static VoiceRecognitionSequence[] buildSequences(int[] buildedInput, int[] mainInput) {
		
		System.out.println("\nSTART buildSequences!");
		int edgeV;
		int edgeH;
		int middleCounter;
		
		VoiceRecognitionSequence[] sequences = new VoiceRecognitionSequence[10] ;
		sequenceCounter = 0;
		
		for(int i = 0; i < buildedInput.length; i = i+6 ) {
			
			edgeH = 0;
			edgeV = 0;
			middleCounter = 0;
			System.out.println("buildSequences sign1 "+buildedInput[i] + ", sign2 "+buildedInput[i+3] + ", index 1: "+buildedInput[i+1] +", index -1: "  +buildedInput[i+4]);
			
			if(buildedInput[i] == 1 && buildedInput[i+3] == -1) {
				
				for(int j = buildedInput[i+1]; j <  buildedInput[i+4]; j++) {
					
					if(mainInput[j] > edgeV) {
						edgeV = mainInput[j];
						edgeH = j;
						middleCounter = 0;
					}
					
					if(mainInput[j] == edgeV) 
						middleCounter++;				
				}
				sequences[sequenceCounter++] =  new VoiceRecognitionSequence(buildedInput[i+1],buildedInput[i+2],edgeH+(middleCounter/2),edgeV,buildedInput[i+4],buildedInput[i+5]);
			}
			
			if(buildedInput[i] == 0)
				break;
		}
			return sequences;
	}
	
	public static void buildArray(int [] input) {
		
		for (int i = 0; i < input.length; i = i+3) {
			
			points[pointsCounter++]= input[i];
		} 
		System.out.println(Arrays.toString(points)); 
	}
	
	public static void main(String[] args) {
	
		int[] test = array1;
		getFirst(test);
		builder(test); 
		buildSequences(points,test);
		System.out.println("\n"+Arrays.toString(points));
	}
	
	public static void builder(int[] input) {

		int[] check;
		int backDirection ;
		
		nextNegativeCheck = false;
		
		for(; i < input.length-1; i++) {
			
			System.out.println("Base 0.0 i: "+i +", i-2 value: "+ input[i-2] +",  i-1 value: "+ input[i-1] 
				+",i value: "+ input[i] +", i+1 value: "+ input[i+1] + ", positiveCounter: "+positiveCounter);
						
			if(input[i] < input[i+1]) { 
				
				positiveCounter++;
				System.out.println("Positive 1.0 i: "+i +", value: "+ input[i] +", i+1 value: "+ input[i+1]+ ", positiveCounter: "+positiveCounter);
			}
			
			if(input[i] >= input[i+1] && positiveCounter > 0) { 
				
				if(points[pointsCounter-3] == -1)
					checkStart(input);

				positiveCounter--;
				System.out.println("Positive 2.0 i: "+i +", value: "+ input[i] +", i+1 value: "+ input[i+1]+ ", positiveCounter: "+positiveCounter);
			}
			
			if(positiveCounter == 5 && points[pointsCounter-3] == -1) { 
			
				addToPoints(1,i-4,input[i-4]);
				System.out.println("Positive 3.0 i: "+i +", value: "+ input[i]+ ", positiveCounter: "+positiveCounter);
			}
			
			if(input[i] >= input[i+1] && positiveCounter > 0 && positiveCounter < 6 ) { 
				System.out.println("Positive 2.0 i: "+i +", value: "+ input[i]+ ", positiveCounter: "+positiveCounter);
			
				positiveCounter++;
			}
			
			if(input[i-1] > input[i] && input[i] <= input[i+1]) {

				System.out.println("Start Possible End sequence 1.0 i: "+i +", value: "+ input[i]);
				check = checkforward(i, input[i], input);
				if(pointsCounter >=6)
				System.out.println("test points[pointsCounter-6] "+points[pointsCounter-6]);
				if(pointsCounter > 5 && points[pointsCounter-6] == -1 && growCheck(check[1],input)) {

					points[pointsCounter-5] = check[1] ;
					points[pointsCounter-4] = input[check[1]];
					points[pointsCounter-3] = 0;
					points[pointsCounter-2] = 0;
					points[pointsCounter-1] = 0;
					pointsCounter = pointsCounter-3;
					continue;
				}

					if(check[0] == 0 || check[0] == 1) {

						if(points[pointsCounter-3] == 1)
							addToPoints(-1,check[1],input[i]); 
						
						if(points[pointsCounter-3] == -1 && getDegree(input[check[1]] - input[points[pointsCounter-2]], i - points[pointsCounter-2],check[1]) < -30)
							changeLast(check[1],input[check[1]]);
					}

					i = check[1];  
				System.out.println("Start Possible End sequence 4.0 : "+j +", value: "+ input[j] + ", i: "+i);
			}			
		}	
	}
	
	private static boolean growCheck(int index, int[] input) {
		
		System.out.println("growCheck index: " + index + ", points[pointsCounter-4] "+points[pointsCounter-4] +", points[pointsCounter-5] "+ points[pointsCounter-5]
				);
		
		if(getDegree(input[index] - points[pointsCounter-4], index - points[pointsCounter-5], index) < -45)
			return true;
		
		return false;
	}
	
	private static void checkStart(int[] input) {
		
		if(points[pointsCounter-3] == 1)
			return;
		
		System.out.println("checkStart i: "+ points[pointsCounter-2]);
		int j = points[pointsCounter-2];
		int end = j +15;
		boolean start = true;
		int startIndex = 0;
		int negativeCounter = 0;
		int positiveCounter = 0;
		
		for( ; j < end ; j++) {
			
			if(j > input.length -2 || positiveCounter + negativeCounter > 2 )
				break;
					
			if(input[j] == input[j+1] ) {
				
				if(negativeCounter > 0)
					negativeCounter--;
				
				if(positiveCounter > 0)
					positiveCounter--;
			}
			
			if(input[j+1] > input[j])
				positiveCounter++;
			
			if(input[j+1] < input[j])
				negativeCounter++;
			
			if(positiveCounter == 1 && start) {
				startIndex = j;
				start = false;
			}
		}

		if(positiveCounter + negativeCounter > 2)
			addToPoints(1,startIndex,input[startIndex]); 
		
		System.out.println("checkStart negativeCounter: "+ negativeCounter + ", positiveCounter: " +positiveCounter + ", j: "+j);
	}
	
	private static void resetCounters() {
		positiveCounter = 0;
	}
	
	private static void addToPoints(int direction, int index, int value) {
		
		points[pointsCounter++] = direction;
		points[pointsCounter++] = index;
		points[pointsCounter++] = value;
		
		System.out.println("\naddToPoints direction "+direction +", index "+index +", value: "+value);
		
		if(direction == -1)
			resetCounters();
	}
	

	
	private static void changeLast(int index, int value) {
		
		System.out.println("\nchangeLast Index: " +points[pointsCounter-2] + ", last value: "
		+points[pointsCounter-1] + ", new index: "+index + ", new value: "+value);
		points[pointsCounter-2] = index;
		points[pointsCounter-1] = value;
				
	}
	
	
	private static int[] checkforward(int index, int checkValue, int[] input) {
			
		int[] returnValue = new int[] {0,index} ;
		int avgDegree = 0;
		int positiveCounter = 0;
		int negativeCounter = 0;
		int checkLength = 10;
		int negativeReturn = index;
		boolean start = false;
		int k = 0;
		
		positiveCounter = 0;
		negativeCounter = 0;
		j = index-1;
		
		System.out.println("Started checkforward j: "+j +", value: "+ input[j]);	
		
		for(; j < input.length; j++) {
			
			if(j > input.length-5-2) 			
				break;

			System.out.println( "Checkforward checkValue: "+checkValue + ", input[j]: "+input[j] +", j: "+j + ", i: "+i );			
			
			if(input[j-1] > input[j] && input[j] <= input[j+1]) {
							
				System.out.println("Checkforward avg start j: "+j + ", negativeReturn " +negativeReturn);

				avgDegree = 0;
				
				for(k = 0; k < checkLength; k++) {
					
					if(input[j+k+1] > input[j+k])
						positiveCounter++;
					
					if(input[j+k+1] < input[j+k])
						negativeCounter++;
										
					if(j + k > input.length-checkLength || positiveCounter == 5 || negativeCounter == 5 ) 	{		
						break;
					}
										
					if(input[j] - input[j+k] >= k+1 ) {
						negativeReturn = j+k;
						avgDegree = 0;
						System.out.println("Checkforward SET NEW index: "+negativeReturn 
							+", j: "+j +", k:"+k + ", value: " +(input[j] - input[j+k]) +", k+1"+ (k+1));
					}									
				}
				
				avgDegree = getDegree(input[j+k]-input[negativeReturn], j + k - negativeReturn,negativeReturn);
				System.out.println("Checkforward avgDegrees index: "+ (negativeReturn)+", height: " + (input[j+k]-input[negativeReturn])
						+", length: " + (j + k - negativeReturn)+", avgDegree: "+avgDegree);
				
				returnValue = getResult(avgDegree, 1, negativeReturn);

				System.out.println("Checkforward New Negative checkValue "+checkValue +" j: "+ j + ", i: "+i+ ", input[j-2]: "+input[j-2]
					+ ", input[j-1]: "+input[j-1]+ ", input[i]: "+input[j]+ ", input[i+1]: "+input[j+1]);
				break;
				
			}
		}
			System.out.println("Checkforward Result! index "+ index + ", checkValue " +checkValue + " "+ Arrays.toString(returnValue) );
			return returnValue;  
	}
	
	public static int[] getResult(int avgDegree,int checkLength , int negativeReturn) {
		
		int resultValue = avgDegree / checkLength;
		int[] result = new int[2];
		
		if(resultValue < -15)
			result = new int[] {-1,negativeReturn};
		
		else if(resultValue > 15)
			result = new int[] {1,negativeReturn};
		
		else 
			result = new int[] {0,negativeReturn};
			
		return result;
	}
	
	public static void getFirst(int[] input) {
		
		i = 0;
		positiveCounter = 0;	
		
		for(i = 1; i < input.length-1;i++) {
			
			if(input[i+1] - input[i-1] > 0) {
				
				positiveCounter++;
				System.out.println("getFirst positiveCounter++ "+positiveCounter);
			}
			if(input[i+1] - input[i-1] <= 0 && positiveCounter > -1) {
			

				positiveCounter--;
				System.out.println("getFirst positiveCounter-- "+positiveCounter);
			}
			if(positiveCounter == 0) {
				
				lastnegativeEdgeIndex = i;
				System.out.println("getFirst Set Zero");
			}
			
			System.out.println("getFirst start: "+lastnegativeEdgeIndex + ", i: "+i  + ", positiveCounter: "+positiveCounter);
			
			if(positiveCounter == 5) 			
				break;

		}
		
		i = i - positiveCounter;
		addToPoints(1,i,input[i]);

		System.out.println("getFirst start: "+lastnegativeEdgeIndex + ", i: "+i );
		System.out.println("getFirst END! \n\n\n");
	}

	public static int getDegree(int y, int x, int index) {
		
		int result =  (int) Math.toDegrees(Math.atan2(y,x));
		System.out.println("getDegree index: " + index + ", height: " +y +", length: "+x +", result: "+result);
		
		return result;
	}
}
