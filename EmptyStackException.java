/*
Matthew Champagne
ID- 112540003
Matthew.champagne@stonybrook.edu
Homework 3
CSE 214.R04
Recitation TA's- Balaji Jayasankar and Xincheng Chi
Grading TA's- Balaji Jayasankar and Saahil Kamat
*/

//Exception for When Stacks are Empty
public class EmptyStackException extends Exception{
    /**
     * Constructor for EmptyStackException
     * @param message Message to be Displayed When Thrown
     */
    public EmptyStackException(String message){
        super(message);
    }
}
