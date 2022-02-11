

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

// Flight System for one single day at YYZ (Print this in title) Departing flights!!


public class FlightReservationSystem
{
	public static void main(String[] args)
	{

		FlightManager manager; 
		try 
		{
			manager = new FlightManager();
		}
		catch (FileNotFoundException e)
		{
			System.out.println(e);
			return;
		}

		ArrayList<Reservation> myReservations = new ArrayList<Reservation>();	// my flight reservations


		Scanner scanner = new Scanner(System.in);
		System.out.print(">");

		while (scanner.hasNextLine())
		{
			String inputLine = scanner.nextLine();
			if (inputLine == null || inputLine.equals("")) 
			{
				System.out.print("\n>");
				continue;
			}

			Scanner commandLine = new Scanner(inputLine);
			String action = commandLine.next();

			if (action == null || action.equals("")) 
			{
				System.out.print("\n>");
				continue;
			}

			else if (action.equals("Q") || action.equals("QUIT"))
				return;

			else if (action.equalsIgnoreCase("LIST"))
			{
				manager.printAllFlights(); 
			}
			else if (action.equalsIgnoreCase("RES"))
			{
				String flightNum = null;
				String passengerName = "";
				String passport = "";
				String seat = "";

				if (commandLine.hasNext())
				{
					flightNum = commandLine.next();
				}
				if (commandLine.hasNext())
				{
					passengerName = commandLine.next();
				}
				if (commandLine.hasNext())
				{
					passport = commandLine.next();
				}
				if (commandLine.hasNext())
				{
					seat = commandLine.next();
					try
					{
						Reservation res = manager.reserveSeatOnFlight(flightNum, passengerName, passport, seat);			/* Reserves a seat on the flight and uses that to create reservation object*/
						myReservations.add(res);																			/* Add the reservation object to myReservations */
						res.print();																						/* Print the reservation object */

					}
					catch (Exception e)
					{
						System.out.println(e);																				/* Prints an error message if an exception is caught */
					}
				}
			}
			else if (action.equalsIgnoreCase("CANCEL"))
			{
				Reservation res = null;
				String flightNum = null;
				String passengerName = "";
				String passport = "";

				if (commandLine.hasNext())
				{
					flightNum = commandLine.next();
				}
				if (commandLine.hasNext())
				{
					passengerName = commandLine.next();
				}
				if (commandLine.hasNext())
				{
					passport = commandLine.next();
					try
					{
						manager.findFlight(flightNum);
						int index = myReservations.indexOf(new Reservation(flightNum, passengerName, passport));			/* Finds the index of the reservation in myReservations */
						if (index >= 0)
						{
							manager.cancelReservation(myReservations.get(index));											/* Cancels the reservation based on the index */
							myReservations.remove(index);																	/* Remove the reservation from myReservations */
						}
						else
							System.out.println("Reservation on Flight " + flightNum + " Not Found");						/* If index is -1, print error message */
					}
					catch (Exception e)
					{
						System.out.println(e);																				/* Prints an error message if an exception is caught */
					}
				}
			}
			else if (action.equalsIgnoreCase("SEATS"))
			{
				String flightNum = "";
				
				if (commandLine.hasNext())
				{
					flightNum = commandLine.next();
					try
					{
						manager.printSeats(flightNum);																		/* Calls manager.printSeats and passes flightNum to print seats */
					}
					catch (Exception e)
					{
						System.out.println(e);																				/* Prints an error message if an exception is caught */
					}
				}
			}
			else if (action.equalsIgnoreCase("MYRES"))
			{
				for (Reservation myres : myReservations)																	/* Loops through myReservations */
					myres.print();																							/* Print each reservation in myReservaions */
			}
			else if (action.equalsIgnoreCase("SORTCRAFT"))
			{
				manager.sortAircraft();																						/* Sort by aircraft size */
			}
			else if (action.equalsIgnoreCase("CRAFT"))
			{
				manager.printAllAircraft();																					/* Prints all the aircrafts */
		  	}
			else if (action.equalsIgnoreCase("PASMAN"))
			{
				String flightNum = "";
				
				if (commandLine.hasNext())
				{
					flightNum = commandLine.next();
					try
					{
						manager.printPassengers(flightNum);																	/* Prints all the passengers in flightNum */
					}
					catch (Exception e)
					{
						System.out.println(e);																				/* Prints an error message if an exception is caught */
					}
				}
			}
			System.out.print("\n>");
		}
	}


}

