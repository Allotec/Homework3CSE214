/*
Matthew Champagne
ID- 112540003
Matthew.champagne@stonybrook.edu
Homework 3
CSE 214.R04
Recitation TA's- Balaji Jayasankar and Xincheng Chi
Grading TA's- Balaji Jayasankar and Saahil Kamat
*/

//Package object that holds the name of the recipient, arrivale date, and weight of the package
public class Package{
    private String recipient; 
    private int arrivalDate;
    private double weight;

    /**
     * Constructor for a Package Object
     * @param recipient
     * @param arrivalDate
     * @param weight
     */
    public Package(String recipient, int arrivalDate, double weight){
        this.recipient = recipient;
        this.arrivalDate = arrivalDate;
        this.weight = weight;
    }

    //Getters
    /**
     * Getter for the Recipient's Name
     * @return Recipient's Name as a String
     */
    public String getRecipient() {
        return recipient;
    }

    /**
     * Getter for the Arrival Date of the Package
     * @return Arrival Date of Package as an Integer
     */
    public int getArrivalDate() {
        return arrivalDate;
    }

    /**
     * Getter for the Weight of the Package
     * @return Weight of the Package as a Double
     */
    public double getWeight() {
        return weight;
    }

    //Setters
    /**
     * Setter for the Recipient's Name
     * @param recipient New Recipient's Name of the Package 
     */
    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    /**
     * Setter for the Arrival Date of the Package
     * @param arrivalDate New Arrival Date of the Package
     */
    public void setArrivalDate(int arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    /**
     * Setter for the Weight of the Package
     * @param weight New Weight of the Package
     */
    public void setWeight(double weight) {
        this.weight = weight;
    }
    
    /** 
     * Returns a String with the Information of the package
     * @return String
     */
    public String toString(){
        return("[" + recipient + " " + arrivalDate + "]");
    }
}