/*
Matthew Champagne
ID- 112540003
Matthew.champagne@stonybrook.edu
Homework 3
CSE 214.R04
Recitation TA's- Balaji Jayasankar and Xincheng Chi
Grading TA's- Balaji Jayasankar and Saahil Kamat
*/

import java.util.Stack;

//Class to Manage the Package Stack
public class PackageStack {
    private final int CAPACITY = 7;
    private Stack<Package> packages;
    private int packageNumber;

    //Constructor for Package Stack    
    public PackageStack(){
        packages = new Stack<>();
    }

    /**
     * Pushes a Package onto the top of the Stack
     * @param x Package to be Pushed onto the top
     * @throws FullStackException If the Stack is at Capcity
     */
    public void push(Package x) throws FullStackException{
        if(isFull()){
            throw new FullStackException("The stack of packages has reached capacity.");         
        }
        else{
            packages.push(x);
            packageNumber++;
        }
    }

    /**
     * Removes and Returns the Package at the top of the Stack
     * @return The Package at the top of the Stack
     * @throws EmptyStackException If the Stack is Empty
     */
    public Package pop() throws EmptyStackException{
        if(packages.isEmpty()){
            throw new EmptyStackException("The stack of packages is empty");
        }
        else{
            packageNumber--;
            return(packages.pop());
        }
    }

    /**
     * Returns the Package at the top of the Stack Without Removing it
     * @return The Package at the top of the Stack
     * @throws EmptyStackException If the Stack is Empty
     */
    public Package peek() throws EmptyStackException{
        if(packages.isEmpty()){
            throw new EmptyStackException("The stack of packages is empty");
        }
        else{
            return(packages.peek());
        }
    }

    /**
     * Checks if the Stack is Full
     * @return True if the Stack is Full Otherwise False
     */
    public boolean isFull(){
        return(packageNumber == CAPACITY);
    }

    /**
     * Checks if the Stack is Empty
     * @return True if the Stack is Empty Otherwise False
     */
    public boolean isEmpty(){
        return(packages.isEmpty());
    }
    
    /** 
     * Returns a String With all of the Packages in the Stack Listed
     * @return String
     */
    public String toString(){
        String temp = "";
        Object tempStack[] = packages.toArray(); 

        if(packageNumber == 0){
            return("empty.");
        }
        else{
            for(int i = 0; i < packageNumber; i++){
                temp += tempStack[i].toString();
            }
        }

        return(temp);
    }
}
