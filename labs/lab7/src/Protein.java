
public class Protein  implements Comparable {
    //initialize private vairables
    private String identifier;
    private String sequence;
    private int dashes;
    private int number;

    //Protein object constructor
	public Protein (String i, String s) {
	    identifier = i;
	    sequence = s;
	    number = 0;
	}//Constructor- protein object

	//accessor method to get a protein's identifier
    public String getIdentifier() {
	    return identifier;
	}//get identifier

	//accessor method to get a protein's sequence
    public String getSequence() {
	    return sequence;
	}//get sequence

    //accessor method to get the number of dashes a protein contains
    public int getDashes() {
	    return number;
	}//get dashes

    //set method to hold the number of dashes a protein has
    public void setNumDashes(int num) {
	    dashes = num;
	}//set dashes

    //sets the number of similar characters in a protein
    public void setSimilar(int num) {
	    number =  num;
	} //set similar

	//gets the similarity number for a protein
    public int getSimilar() {
	    return number;
	}

    //method used to compare objects
    public int compareTo(Object obj) {
        if (obj instanceof Protein) {
            Protein c = (Protein) obj;
                if (c.number < number) {
                    return -1;
                }//if
                else if(c.number > number) {
                    return 1;
                }//else-if
                else {
                    return 0;
                }//else
        }//outter if
        return 0;
    }

}; //class- Protein


