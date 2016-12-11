package com.logistic.model;

/**
 * Represents user grants for the app functionality. For every functionality
 * Grant class contains special field, that describes the level of actions,
 * that user can perform.
 *
 * @author  Mike
 * @version  1.0
 */
public class Grant {
	public final static int NONE = 0;
	public final static int READ = 1;
	public final static int EDIT = 2;

	private int usersBranchLevel = 0;

	private int systemUnitsBranchLevel = 0;
	private int ClientBranchLevel = 0;
	private int DriverBranchLevel = 0;
	
	/**
	 * @param level the level of actions that can be performed with the users entities
	 * @throws  IllegalArgumentException if the <code>level</code> not in range NONE...EDIT constants
	 */
	public void setUsersBranchLevel(int level) throws IllegalArgumentException {
		validateGrantLevel(level);
		this.usersBranchLevel = level;
	}
	/**
	 * @param level the level of actions that can be performed with the 
	 * system units entities such as cities, routers, connections
	 * @throws  IllegalArgumentException if the <code>level</code> not in range NONE...EDIT constants
	 */
	public void setSystemUnitsBranchLevel(int level) throws IllegalArgumentException {
		validateGrantLevel(level);
		this.systemUnitsBranchLevel = level;
	}

	public void setClientBranchLevel(int level) throws IllegalArgumentException{
		validateGrantLevel(level);
		this.ClientBranchLevel = level;
	}

	public void setDriverBranchLevel(int level) {
		validateGrantLevel(level);
		this.DriverBranchLevel = level;
	}

	/**
	 * @return level the level of actions that can be performed with the users entities
	 */
	public int getUsersBranchLevel() {
		return usersBranchLevel;
	}
	/**
	 * @return level the level of actions that can be performed with the 
	 * system units entities such as cities, routers, connections
	 */
	public int getSystemUnitsBranchLevel() {
		return systemUnitsBranchLevel;
	}

	public int getClientBranchLevel(){
		return ClientBranchLevel;
	}

	public int getDriverBranchLevel(){
		return DriverBranchLevel;
	}

	private void validateGrantLevel(int level) throws IllegalArgumentException {
		if (level < NONE || level > EDIT) {
			throw new IllegalArgumentException("The grant level must be in range +" + NONE +".."+ EDIT +", got "+level);
		}
	}
}
