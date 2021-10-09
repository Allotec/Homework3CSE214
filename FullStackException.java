/*
Matthew Champagne
ID- 112540003
Matthew.champagne@stonybrook.edu
Homework 3
CSE 214.R04
Recitation TA's- Balaji Jayasankar and Xincheng Chi
Grading TA's- Balaji Jayasankar and Saahil Kamat
*/

//Exception for When Stacks are Full
public class FullStackException extends Exception{
    /**
     * Constructor for FullStackException
     * @param message Message to be Displayed When Thrown
     */
    public FullStackException(String message){
        super(message);
    }
}
