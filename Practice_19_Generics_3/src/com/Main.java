package com;

public class Main {

	public static void main(String args[]) {
		TwoD td[] = {
		new TwoD(0, 0),
		new TwoD(7, 9),
		new TwoD(18, 4),
		new TwoD(-1, -23)
		};
		Coords<TwoD> tdlocs = new Coords<TwoD>(td);
		System.out.println("Contents of tdlocs.");
		BoundedWildcard.showXY(tdlocs); // OK, is a TwoD
		// showXYZ(tdlocs); // Error, not a ThreeD
		// showAll(tdlocs); // Error, not a FourD
		// Now, create some FourD objects.
		FourD fd[] = {
		new FourD(1, 2, 3, 4),
		new FourD(6, 8, 14, 8),
		new FourD(22, 9, 4, 9),
		new FourD(3, -2, -23, 17)
		};
		Coords<FourD> fdlocs = new Coords<FourD>(fd);
		System.out.println("Contents of fdlocs.");
		// These are all OK.
		BoundedWildcard.showXY(fdlocs);
		BoundedWildcard.showXYZ(fdlocs);
		BoundedWildcard.showAll(fdlocs);
		}
}
