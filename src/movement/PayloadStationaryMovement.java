/*
 * Copyright 2010 Aalto University, ComNet
 * Released under GPLv3. See LICENSE.txt for details.
 */
package movement;

import core.Coord;
import core.Settings;

import java.util.Random;

/**
 * A dummy stationary "movement" model where nodes do not move.
 * Might be useful for simulations with only external connection events.
 */
public class PayloadStationaryMovement extends MovementModel {
	/** Per node group setting for setting the location ({@value}) */
	public static final String LOCATION_S = "nodeLocation";
	private Coord loc; /** The location of the nodes */

	/**
	 * Creates a new movement model based on a Settings object's settings.
	 * @param s The Settings object where the settings are read from
	 */
	public PayloadStationaryMovement(Settings s) {
		super(s);

        //int [] worldSize = s.getCsvInts(WORLD_SIZE,2);
        //int maxX = worldSize[0];
        //int maxY = worldSize[1];
        int maxX = 3500;
        int maxY = 2400;
        //int coords[];
		Random rand = new Random();
        int randX = rand.nextInt( maxX ); //Generates a number in [0, 1, .., 4500]
        int randY = rand.nextInt( maxY ); //Generates a number in [0, 1, .., 3400]
        //System.out.println(randX + " "+ randY);
		//coords = s.getCsvInts(LOCATION_S, 2);
		//this.loc = new Coord(coords[0],coords[1]);
        this.loc = new Coord(randX,randY);
	}

	/**
	 * Copy constructor.
	 * @param sm The StationaryMovement prototype
	 */
	public PayloadStationaryMovement(PayloadStationaryMovement sm) {
		super(sm);
		this.loc = sm.loc;
	}

	/**
	 * Returns the only location of this movement model
	 * @return the only location of this movement model
	 */
	@Override
	public Coord getInitialLocation() {
		return loc;
	}

	/**
	 * Returns a single coordinate path (using the only possible coordinate)
	 * @return a single coordinate path
	 */
	@Override
	public Path getPath() {
		Path p = new Path(0);
		p.addWaypoint(loc);
		return p;
	}

	@Override
	public double nextPathAvailable() {
		return Double.MAX_VALUE;	// no new paths available
	}

	@Override
	public PayloadStationaryMovement replicate() {
		return new PayloadStationaryMovement(this);
	}

}
