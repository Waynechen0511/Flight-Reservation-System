

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;
import java.util.Scanner;
import java.util.TreeMap;
import java.io.File;
import java.io.FileNotFoundException;

public class FlightManager
{
  TreeMap<String, Flight> flights = new TreeMap<String, Flight>();

  String[] cities 	= 	{"Dallas", "New York", "London", "Paris", "Tokyo"};
  final int DALLAS = 0;  final int NEWYORK = 1;  final int LONDON = 2;  final int PARIS = 3; final int TOKYO = 4;
  
  int[] flightTimes = { 3, // Dallas
  											1, // New York
  											7, // London
  											8, // Paris
  											16,// Tokyo
  										};
  
  ArrayList<Aircraft> airplanes = new ArrayList<Aircraft>();  
  ArrayList<String> flightNumbers = new ArrayList<String>();


  String errMsg = null;
  Random random = new Random();
  
  
  public FlightManager() throws FileNotFoundException
  {
  	// Create some aircraft types with max seat capacities
  	airplanes.add(new Aircraft(84, "Boeing 737"));
  	airplanes.add(new Aircraft(180,"Airbus 320"));
  	airplanes.add(new Aircraft(40, "Dash-8 100"));
  	airplanes.add(new Aircraft(4, "Bombardier 5000"));
  	airplanes.add(new Aircraft(100, 12, "Boeing 747"));																	// Changed 592 to 100 to include first class seats, 14 to 12 FC seats

  	// Populate the list of flights with some random test flights

    String airline = "";
    String city = "";
    String departure = "";
    String capacity = "";
    String flightNum = "";
    int flightTime = 0;
    Scanner scan1  = new Scanner(new File("flights.txt"));
    while (scan1.hasNextLine())
    {
      String flightInfo = scan1.nextLine().trim();
      String[] flightInfoArray = flightInfo.split(" ");
      airline = flightInfoArray[0].replace("_", " ");
      city = flightInfoArray[1].replace("_", " ");
      departure = flightInfoArray[2];
      capacity = flightInfoArray[3];
      flightNum = generateFlightNumber(airline);
      Flight flight = new Flight(flightNum, airline, city, departure, flightTime, getOptimalCraft(capacity));
      if (city.equals("New York"))
      {
        flightTime = 1;
      }
      if (city.equals("Dallas"))
      {
        flightTime = 3;
      }
      if (city.equals("London"))
      {
        flightTime = 7;
      }
      if (city.equals("Paris"))
      {
        flightTime = 8;
      }
      
      if (city.equals("Tokyo"))
      {
        flightTime = 16;
      }
      if (getOptimalCraft(capacity).getNumFirstClassSeats() != 0)
      {
        flight = new LongHaulFlight(flightNum, airline, city, departure, flightTime, getOptimalCraft(capacity));
      }
      flights.put(flightNum, flight);
    }
  }
  
  /**
   * Sorts the aircrafts by total seats from least to greatest. Loops through the sorted list
   * of aircrafts. Break out of the loop when an aircraft that has a higher total seats than 
   * capacity. Return that aircraft.
   * @param capacity The capacity of the flight.
   * @return The aircraft to be used for the flight.
   */
  private Aircraft getOptimalCraft(String capacity)
  {
	  sortAircraft();
	  int i;
	  for (i = 0; i < airplanes.size(); i++)
    {
      if (airplanes.get(i).getTotalSeats() >= Integer.parseInt(capacity)) break;
    }	
	  return airplanes.get(i); 
  }

  /**
   * Takes the first letter of 2 words and converts them to uppercase.
   * Add those 2 letters with a random number from 101 to 300 and return it.
   * @param airline The name of the airline.
   * @return The generated flight number.
   */
  private String generateFlightNumber(String airline)
  {
  	String word1, word2;
  	Scanner scanner = new Scanner(airline);
  	word1 = scanner.next();
  	word2 = scanner.next();
  	String letter1 = word1.substring(0, 1);
  	String letter2 = word2.substring(0, 1);
  	letter1.toUpperCase(); letter2.toUpperCase();
  	
  	// Generate random number between 101 and 300
  	int flight = random.nextInt(200) + 101;
  	String flightNum = letter1 + letter2 + flight;
   	return flightNum;
  }

  public String getErrorMessage()
  {
  	return errMsg;	
  }
  
  /**
   * Prints all the flights
   */
  public void printAllFlights()
  {
  	for (String key : flights.keySet())
  		System.out.println(flights.get(key));
  }

 /** 
  * Searches through the flights arraylist using a loop, looking for the specified flightNum.
  * If the flightNum is not found, throw FlightNotFoundException.
  * @param flightNum the flight to search for.
  */
  public void findFlight(String flightNum) throws FlightNotFoundException
  {
    boolean found = false;
    for (String key: flights.keySet())                                                                /* Loop to see check if flight number exists */
	  {
      if (flightNum.equals(key))
      {
        found = true;
        break;                                                                                          /* Stops looping when a matching flight is found */
      }
    }

    if (!found)                                                                                   		 /* If a matching flight was not found, return -1 */
    {
      throw new FlightNotFoundException("Flight " + flightNum + " Not Found");
    }
  }
  
  /**
   * Checks if the flightNum exists. If it exists then reserve a seat
   * for the passenger. 
   * @param flightNum Flight Number of the reservation.
   * @param name Name of the passenger.
   * @param passport Passport number of the passenger.
   * @param seat Seat of the passenger.
   * @return A reservation object with the information of the reservation.
   * @throws FlightNotFoundException Exception to be thrown when flight does not exist.
   * @throws SeatOccupiedException Exception to be thrown is the seat is occupied.
   * @throws FlightFullException Exception to be thrown is the flight is full.
   * @throws DuplicatePassengerException Exception to be thrown when the passenger is a duplicate.
   * @throws InvalidSeatException Exception to be thrown if the seat is invalid.
   */
  public Reservation reserveSeatOnFlight(String flightNum, String name, String passport, String seat) throws FlightNotFoundException, SeatOccupiedException, FlightFullException, DuplicatePassengerException, InvalidSeatException
  {
    findFlight(flightNum);
    boolean firstClass = false;

  	Flight flight = flights.get(flightNum);
  	Passenger p = new Passenger(name, passport, "", seat);
  	flight.reserveSeat(p, seat);

	  if (seat.charAt(seat.length()-1) == '+') firstClass = true;

	  return new Reservation(flightNum, flight.toString(), firstClass, name, passport, seat);
  }
  
  /**
   * Checks if the flightNum exists. If it does then cancel the reservation using 
   * a passenger object made from res and return true.
   * @param res The reservation object to be cancelled
   * @return true if the seat is cancelled
   * @throws InvalidSeatException Exception to be thrown when the seat is invalid
   * @throws PassengerNotInManifestException Exception to be thrown when the passenger does not exist.
   * @throws FlightNotFoundException Exception to be thrown when the flight does not exist.
   */
  public void cancelReservation(Reservation res) throws PassengerNotInManifestException, FlightNotFoundException, InvalidSeatException
  {
    findFlight(res.getFlightNum());

  	Flight flight = flights.get(res.getFlightNum());
	  Passenger p = new Passenger(res.name, res.passport, "", res.seat);
  	flight.cancelSeat(p);
  }

  /**
   * Sort the aircrafts by size
   */
  public void sortAircraft()
  {
  	Collections.sort(airplanes);
  }

  /**
   * Prints all the aircrafts
   */
  public void printAllAircraft()
  {
    for (int i = 0; i < airplanes.size(); i++)
    {
      airplanes.get(i).print(); 
    }
  }

  /**
   * Checks if the flightNum exists, if it does then print the seats on it.
   * @param flightNum The flight number to be printed.
   * @throws FlightNotFoundException Exception to be thrown when the flight is not found.
   */
  public void printSeats(String flightNum) throws FlightNotFoundException
  {
    findFlight(flightNum);

    Flight flight = flights.get(flightNum);
    flight.printSeats();
  }

  /**
   * Checks if the flightNum exists. If it exists then print the passenger manifest
   * on it. 
   * @param flightNum The flight number to be printed
   * @throws FlightNotFoundException Exception to be thrown when the flight is not found.
   */
  public void printPassengers(String flightNum) throws FlightNotFoundException
  {
    findFlight(flightNum);
    Flight flight = flights.get(flightNum);
    flight.printPassengerManifest();
  }
}
