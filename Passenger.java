

public class Passenger
{
	private String name;
	private String passport;
	private String seat;
	private String seatType;
	
	public Passenger(String name, String passport, String seatType, String seat)
	{
		this.name = name;
		this.passport = passport;
		this.seat = seat;
		this.seatType = seatType;
	}
	
	public Passenger(String name, String passport)
	{
		this.name = name;
		this.passport = passport;
	}
	
	/**
	 * Checks if the other passenger has the same name
	 * and passport number. Return true if it is.
	 * @param other
	 * @return True is returned when the passengers
	 * have the same name and passport num.
	 */
	public boolean equals(Object other)
	{
		Passenger otherP = (Passenger) other;
		return name.equals(otherP.name) && passport.equals(otherP.passport);
	}
	
	public String getSeatType()
	{
		return seatType;
	}

	public void setSeatType(String seatType)
	{
		this.seatType = seatType;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getPassport()
	{
		return passport;
	}

	public void setPassport(String passport)
	{
		this.passport = passport;
	}

	public String getSeat()
	{
		return seat;
	}

	public void setSeat(String seat)
	{
		this.seat = seat;
	}
	
	public String toString()
	{
		return name + " " + passport + " " + seat;
	}
}
