/*
 * A Long Haul Flight is a flight that travels a long distance and has two types of seats (First Class and Economy)
 */

public class LongHaulFlight extends Flight
{
	int firstClassPassengers;
		
	public LongHaulFlight(String flightNum, String airline, String dest, String departure, int flightDuration, Aircraft aircraft)
	{
		super(flightNum, airline, dest, departure, flightDuration, aircraft);
		type = Flight.Type.LONGHAUL;
	}

	public LongHaulFlight()
	{
		firstClassPassengers = 0;
		type = Flight.Type.LONGHAUL;
	}
	
	/**
	 * Checks if the seat is valid. If the seat is invalid then call super.reserveSeat.
	 * If it is valid, then check if there are still first class seats available. If there is not,
	 * throw FlightFullException. Then check if the seat is already in seatMap. Throw SeatOccupiedException if it is.
	 * Use the parameters to create a new passenger object and check if that passenger currently
	 * exists in the manifest. Throw DuplicatePassengerException if it is. Otherwise, set the passenger seat to first class.
	 * Add passenger to the manifest and put it in the seatMap. Then increment firstclassPassengers.
	 * @param p Passenger information.
	 * @throws SeatOccupiedException Exception to be thrown if the seat is occupied.
	 * @throws FlightFullException Exception to be thrown if the flight is full.
	 * @throws DuplicatePassengerException Exception to be thrown if the passenger already exists in manifest.
	 * @throws InvalidSeatException Exception to be thrown if the seat is invalid.
	 */
	public void reserveSeat(Passenger p) throws SeatOccupiedException, FlightFullException, DuplicatePassengerException, InvalidSeatException
	{
		String seat = p.getSeat();

		if (super.validSeat(seat))
		{
			if (firstClassPassengers >= aircraft.getNumFirstClassSeats())
			{
				throw new FlightFullException("No First Class Seats AVailable");
			}
			if (seatMap.containsKey(seat))
			{
				throw new SeatOccupiedException("Seat " + seat + " already occupied");
			}
			
			if (manifest.indexOf(p) >=  0)
			{
				throw new DuplicatePassengerException("Duplicate Passenger " + p.getName() + " " + p.getPassport());
			}
			p.setSeat(seat);
			manifest.add(p);
			seatMap.put(seat, p);
			firstClassPassengers++;
		}
		else // economy passenger
			super.reserveSeat(p, seat);
	}
	
	/**
	 * Checks if the seat is a valid long haul. If it is valid, create new passenger object. Check if passenger object
	 * is in manifest, if not, then throw PassengerNotInManifestException. If it exists, remove passenger from manifest
	 * and remove seat from seatMap. Then decrement firstclassPassengers if possible. If the seat is not a valid long haul
	 * seat, call super.cancelSeat.
	 * @param p Passenger information.
	 * @throws InvalidSeatException Exception to be thrown if the seat is not valid.
	 * @throws PassengerNotInManifestException Exception to be thrown if passenger is not in manifest.
	 */
	public void cancelSeat(Passenger p) throws InvalidSeatException, PassengerNotInManifestException
	{
		String name = p.getName();
		String passport = p.getPassport();
		String seat = p.getSeat();

		if (super.validSeat(seat))
		{
			if (manifest.indexOf(p) == -1) 
			{
				throw new PassengerNotInManifestException("Passenger " + name + " " + passport + " Not Found");	
			}
			manifest.remove(p);
			seatMap.remove(seat);
			if (firstClassPassengers > 0)	firstClassPassengers--;
		}
		else
			super.cancelSeat(p);
	}
	
	public int getPassengerCount()
	{
		return getNumPassengers() +  firstClassPassengers;
	}
	
	public String toString()
	{
		 return super.toString() + "\t LongHaul";
	}
}
