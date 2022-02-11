

import java.util.ArrayList;
import java.util.Random;
import java.util.TreeMap;

public class Flight
{
	public enum Status {DELAYED, ONTIME, ARRIVED, INFLIGHT};
	public static enum Type {SHORTHAUL, MEDIUMHAUL, LONGHAUL};
	public static enum SeatType {ECONOMY, FIRSTCLASS, BUSINESS};

	private String flightNum;
	private String airline;
	private String origin, dest;
	private String departureTime;
	private Status status;
	private int flightDuration;
	protected Aircraft aircraft;
	protected int numPassengers;
	protected Type type;
	protected ArrayList<Passenger> manifest;
	protected TreeMap<String, Passenger> seatMap;
	protected TreeMap<String, String> maxSeatMap;

	protected Random random = new Random();
	
	private String errorMessage = "";
	  
	public String getErrorMessage()
	{
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage)
	{
		this.errorMessage = errorMessage;
	}

	public Flight()
	{
		this.flightNum = "";
		this.airline = "";
		this.dest = "";
		this.origin = "Toronto";
		this.departureTime = "";
		this.flightDuration = 0;
		this.aircraft = null;
		numPassengers = 0;
		status = Status.ONTIME;
		type = Type.MEDIUMHAUL;
		manifest = new ArrayList<Passenger>();
		seatMap = new TreeMap<String, Passenger>();
		maxSeatMap = new TreeMap<String, String>();
	}
	
	public Flight(String flightNum)
	{
		this.flightNum = flightNum;
	}
	
	public Flight(String flightNum, String airline, String dest, String departure, int flightDuration, Aircraft aircraft)
	{
		this.flightNum = flightNum;
		this.airline = airline;
		this.dest = dest;
		this.origin = "Toronto";
		this.departureTime = departure;
		this.flightDuration = flightDuration;
		this.aircraft = aircraft;
		numPassengers = 0;
		status = Status.ONTIME;
		type = Type.MEDIUMHAUL;
		manifest = new ArrayList<Passenger>();
		seatMap = new TreeMap<String, Passenger>();
		maxSeatMap = new TreeMap<String, String>();
	}
	
	public Type getFlightType()
	{
		return type;
	}
	
	public String getFlightNum()
	{
		return flightNum;
	}
	public void setFlightNum(String flightNum)
	{
		this.flightNum = flightNum;
	}
	public String getAirline()
	{
		return airline;
	}
	public void setAirline(String airline)
	{
		this.airline = airline;
	}
	public String getOrigin()
	{
		return origin;
	}
	public void setOrigin(String origin)
	{
		this.origin = origin;
	}
	public String getDest()
	{
		return dest;
	}
	public void setDest(String dest)
	{
		this.dest = dest;
	}
	public String getDepartureTime()
	{
		return departureTime;
	}
	public void setDepartureTime(String departureTime)
	{
		this.departureTime = departureTime;
	}
	
	public Status getStatus()
	{
		return status;
	}
	public void setStatus(Status status)
	{
		this.status = status;
	}
	public int getFlightDuration()
	{
		return flightDuration;
	}
	public void setFlightDuration(int dur)
	{
		this.flightDuration = dur;
	}
	
	public int getNumPassengers()
	{
		return numPassengers;
	}
	public void setNumPassengers(int numPassengers)
	{
		this.numPassengers = numPassengers;
	}
	
	/**
	 * Update passenger object to only include name and passport. Check if the passenger is in the manifest.
	 * If not, then throw PassengerNotInManifestException. If the passenger exists, then remove the passenger
	 * from manifest and the seatMap. Decrement the number of passengers if possible.
	 * @param p Passenger object that contains all the information about the passenger.
	 * @throws InvalidSeatException Exception to be thrown if the reserved seat is invalid.
	 * @throws PassengerNotInManifestException Exception to be thrown if the passenger is not in the manifest.
	 */
	public void cancelSeat(Passenger p) throws PassengerNotInManifestException, InvalidSeatException
	{
		if (manifest.indexOf(p) == -1) 
		{
			throw new PassengerNotInManifestException("Passenger " + p.getName() + " " + p.getPassport() + " Not Found");														
		}

		manifest.remove(p);
		seatMap.remove(p.getSeat());
		if (numPassengers > 0) numPassengers--;
	}
	
	/**
	 * Checks if the flight is full, if it is then throw FlightFullException. Then checks if 
	 * the seat already exists in seatMap. If it is, throw SeatOccupiedException. Then validSeat(seat)
	 * is used to check if seat is a valid seat. Then check if Passenger p is in manifest.
	 * DuplicatePassengerException is thrown if the passenger already exists in manifest. If it is a new passenger,
	 * the passenger has it's seat set, added to manifest, and put into seatMap. The number of passengers is also incremented.
	 * @param p Passenger object, stores passenger information.
	 * @param seat Seat of the passenger.
	 * @throws SeatOccupiedException Exception to be thrown if the seat is occupied.
	 * @throws FlightFullException Exception to be thrown if the flight is full.
	 * @throws DuplicatePassengerException Exception to be thrown if the passenger is already in the manifest.
	 * @throws InvalidSeatException Exception to be thrown if the reserved seat is invalid.
	 */
	public void reserveSeat(Passenger p, String seat) throws SeatOccupiedException, FlightFullException, DuplicatePassengerException, InvalidSeatException
	{
		if (seatMap.containsKey(seat))
		{
			throw new SeatOccupiedException("Seat " + seat + " already occupied");
		}

		if (numPassengers >= aircraft.getNumSeats())
		{
			throw new FlightFullException("Flight " + flightNum + " Full");
		}

		if (!validSeat(seat))
		{
			throw new InvalidSeatException("Flight " + flightNum + " Invalid Seat Type Request");
		}
	
		if (manifest.indexOf(p) >=  0)
		{
			throw new DuplicatePassengerException("Duplicate Passenger " + p.getName() + " " + p.getPassport());
		}
		p.setSeat(seat);
		manifest.add(p);
		seatMap.put(seat, p);
		numPassengers++;
	}
	
	public boolean equals(Object other)
	{
		Flight otherFlight = (Flight) other;
		return this.flightNum.equals(otherFlight.flightNum);
	}
	
	public String toString()
	{
		 return airline + "\t Flight:  " + flightNum + "\t Dest: " + dest + "\t Departing: " + departureTime + "\t Duration: " + flightDuration + "\t Status: " + status;
	}

	/**
	 * Loops through the manifest to print all the passengers.
	 */
	public void printPassengerManifest()
	{
		for (int i = 0; i < manifest.size(); i++)
		{
			System.out.println(manifest.get(i).toString());
		}
	}

	/**
	 * Generates a map with all the possible seats for a specific flight.
	 * Loops through the 2D array aircraft.seatLayout and puts each seat into maxSeatMap.
	 * If the aircraft is first class, the loop generates a "+" at the end of string to store
	 * into the map. The first class seats are printed by looking at how many columns of first class seats
	 * there should be, and the remainder of first class seats that do not fill a full column. 
	 * This assumes that the total seats is divisible by 4, regardless of what first class and economy are
	 * individually.
	 */
	public void generateMap()
	{
		boolean divisibleFirstClass = false;
		double firstClassSeats = aircraft.getNumFirstClassSeats();
		double firstClassColumns = Math.ceil(firstClassSeats/4.0);
		double remain = firstClassSeats % 4;
		int y = aircraft.getRows();
		int x = aircraft.getColumns();
		if (aircraft.getTotalSeats() == 4)
		{	
			y = y/2;
			x = x*2;
		}

		if (remain == 0) 
		{
			divisibleFirstClass = true;															/* Set divisibleFirstClass to true if the number of first class seats is divisible by 4 */
		}

		for (int i = 0; i < y; i++)
		{
			for (int j = 0; j < x; j++)
			{
				String letter = aircraft.getLetterRow(i);
				String seatNum = (j + 1) + letter;
				
				if ((j + 1) < firstClassColumns && firstClassSeats != 0)
				{	
					maxSeatMap.put(seatNum + "+", "");											/* Puts the first class seatNum into the map if it is not on the final first class column */
				}
				else if ((j + 1) == firstClassColumns && firstClassSeats != 0)
				{
					if (remain > 0 || divisibleFirstClass)
					{
						maxSeatMap.put(seatNum + "+", "");										/* Puts the first class seatNum into the map if there are remaining first class seats on the final column */
						remain--;
					}
					else
					{
						maxSeatMap.put(seatNum, "");											/* Put in the seat normally if there are no first class seats left */
					}
				}
				else
				{
					maxSeatMap.put(seatNum, "");												/* Put in the seat normally */
				}
			}
		}
	}

	/**
	 * Checks if a seat is valid by seeing if it is contained in the map of 
	 * all possible seats. If it is not valid, return false.
	 * @param seatNum The seat number to be validated.
	 * @return true is returned when the seat is valid, false if not.
	 */
	public boolean validSeat(String seatNum)
	{
		generateMap();
		if (!maxSeatMap.containsKey(seatNum)) return false;
		return true;
	}

	/**
	 * Generates a map with all the possible seats for a specific flight.
	 * Loops through the 2D array aircraft.seatLayout and prints each seat.
	 * If the seat is contained in seatMap, then "XX" is printed instead of the seatNum.
	 * First class seats are printed by looking at the number of columns of first class seats there are,
	 * then printing all the seats before that column as first class. Remain is used to see how many
	 * first class seats there are that cannot fit into a full column. When the loop reaches the column, 
	 * remains is decremented each time a first class seat is printed. This assumes that the total seats is divisible by 4, 
	 * regardless of what first class and economy are individually.
	 */
	public void printSeats()
	{
		boolean divisibleFirstClass = false;
		double firstClassSeats = aircraft.getNumFirstClassSeats();
		double firstClassColumns = Math.ceil(firstClassSeats/4.0);
		double remain = firstClassSeats % 4;
		int y = aircraft.getRows();
		int x = aircraft.getColumns();
		if (aircraft.getTotalSeats() == 4)
		{	
			y = y/2;
			x = x*2;
		}
		if (remain == 0) divisibleFirstClass = true;											/* Set divisibleFirstClass to true if the number of first class seats is divisible by 4 */
		for (int i = 0; i < y; i++)
		{
			for (int j = 0; j < x; j++)
			{
				String letter = aircraft.getLetterRow(i);
				String seatNum = (j + 1) + letter;
				
				if (seatMap.containsKey(seatNum) || seatMap.containsKey(seatNum + "+"))
				{
					System.out.print("XX" + "\t");												/* Loop through manefist to find seatNum, replace seatNum with xx when found */
					if ((j + 1) == firstClassColumns) remain--;									/* If loop is on the last column of first class seats, start decrementing remain if the seat is occupied*/
				}
				else
				{
					if ((j + 1) < firstClassColumns && firstClassSeats != 0)					
					{	
						System.out.print(seatNum + "+" + "\t");									/* Adds a + to the end of the seat num if there are more first class columns remaining */
					}
					else if ((j + 1) == firstClassColumns && firstClassSeats != 0)
					{
						if (remain > 0 || divisibleFirstClass)
						{
							System.out.print(seatNum + "+" + "\t");								/* If there are still remaining first class seats on the last column, add a + and decrement remain */
							remain--;
						}
						else
						{
							System.out.print(seatNum + "\t");									/* Print the seat num normally if there are no more remaining first class seats */
						}
					}
					else
					{
						System.out.print(seatNum + "\t");										/* Print the seat num normally */
					}
				}
			}
			if (i == 1) System.out.println();													/* Adds a space between the 2nd and 3rd rows */
			System.out.println();
		}
		System.out.println("XX = Occupied \t + = First Class");
	}
}

class DuplicatePassengerException extends Exception
{
	DuplicatePassengerException(String e)
	{
		super(e);
	}
}

class PassengerNotInManifestException extends Exception
{
	PassengerNotInManifestException(String e)
	{
		super(e);
	}
}

class SeatOccupiedException extends Exception
{
	SeatOccupiedException(String e)
	{
		super(e);
	}
}

class FlightFullException extends Exception
{
	FlightFullException(String e)
	{
		super(e);
	}
}

class InvalidSeatException extends Exception
{
	InvalidSeatException(String e)
	{
		super(e);
	}
}

class FlightNotFoundException extends Exception
{
	FlightNotFoundException(String e)
	{
		super(e);
	}
}
