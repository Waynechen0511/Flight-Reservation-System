
public class Aircraft implements Comparable<Aircraft>
{
  int numEconomySeats;
  int numFirstClassSeats;
  String [][] seatLayout;
  String model;
  
  public Aircraft(int seats, String model)
  {
  	this.numEconomySeats = seats;
  	this.numFirstClassSeats = 0;
  	this.model = model;
	this.seatLayout = new String[4][numEconomySeats/4];
  }

  public Aircraft(int economy, int firstClass, String model)
  {
  	this.numEconomySeats = economy;
  	this.numFirstClassSeats = firstClass;
  	this.model = model;
	this.seatLayout = new String[4][(numEconomySeats + numFirstClassSeats)/4];
  }

  	public int getColumns()
	{
		return (numEconomySeats + numFirstClassSeats)/4;
	}

	public int getRows()
	{
		return 4;
	}

	/**
	 * Looks at the row number and converts it
	 * to a letter.
	 * @param i The row number.
	 * @return The letter.
	 */
	public String getLetterRow(int i)
	{
		String letter = "";
		if (i == 0) letter = "A";
		if (i == 1) letter = "B";
		if (i == 2) letter = "C";
		if (i == 3) letter = "D";
		return letter;
	}

	public int getNumSeats()
	{
		return numEconomySeats;
	}
	
	public int getTotalSeats()
	{
		return numEconomySeats + numFirstClassSeats;
	}
	
	public int getNumFirstClassSeats()
	{
		return numFirstClassSeats;
	}

	public String getModel()
	{
		return model;
	}

	public void setModel(String model)
	{
		this.model = model;
	}
	
	public void print()
	{
		System.out.println("Model: " + model + "\t Economy Seats: " + numEconomySeats + "\t First Class Seats: " + numFirstClassSeats);
	}

  	public int compareTo(Aircraft other)
  	{
  		if (this.numEconomySeats == other.numEconomySeats)
  			return this.numFirstClassSeats - other.numFirstClassSeats;
  	
  		return this.numEconomySeats - other.numEconomySeats; 
  	}

}
