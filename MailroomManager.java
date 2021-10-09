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
import java.util.Scanner;   

//Implement a rule for stacks that are already full

public class MailroomManager {
    //Defines the Number of Stacks to be created not including the floor stack
    private static final int SIZE = 5;

    //PackageStack Object array
    //Index 0- Stack 1 (A-G)
    //Index 1- Stack 2 (H-J)
    //Index 2- Stack 3 (K-M)
    //Index 3- Stack 4 (N-R)
    //Index 4- Stack 5 (S-Z)
    private static PackageStack packageStacks[] = new PackageStack[SIZE];
    
    //Stack Object with Infinite Cpacity of Packages
    private static Stack<Package> floorStack = new Stack<>();

    public static void main(String[] args) throws EmptyStackException, FullStackException{
        //Temp Variables
        char c; //Temporary Character For Switch
        String name; //Temporary String for Passing to Stack
        double weight; //Temporary double for Passing to Stack
        int count = 0; //Keeps Track of the Amount of Packages Moved
        int workingStackIndex = 0; //Keeps Track of the Stack Number of the Current Stack
        Package packagetemp = null; //Holds packages that are popped off a stack
        int currentDay = 0; //Holds the Current day 
        int source, destination, stackCount = 0;
        Stack<Package> tempStack = new Stack<>();

        //Initialize Stacks
        for(int i = 0; i < SIZE; i++){
            packageStacks[i] = new PackageStack();
        }

        //Prints out menu each loop
        System.out.println("Menu:");
        System.out.println("\tD) Delivier Package");
        System.out.println("\tG) Get Packages for a User");
        System.out.println("\tT) Make it Tomorrow");
        System.out.println("\tP) Print the Stacks");
        System.out.println("\tM) Move a Package from one Stack to Another");
        System.out.println("\tF) Find Packages in the Wrong Stack and Move to Floor");
        System.out.println("\tL) List all the Packages Awaiting a User");
        System.out.println("\tE) Empty the Floor, Moving all Packages to the Trash");
        System.out.println("\tQ) Quit\n");

        while(true){
            try{
                //Opens Scanner and asks for option to be chosen from menu
                Scanner keyboard = new Scanner(System.in);
                System.out.print("Please select an option- ");
                c = keyboard.nextLine().charAt(0);
            
                switch(c){
                    //Puts the Package on the Correct Stack
                    case 'D':
                    case 'd':
                        //Name Input Auto Capatilizes the first letter
                        System.out.print("Please Enter the Recipient's Name- ");  
                        name = keyboard.nextLine();
                        name = name.substring(0, 1).toUpperCase() + name.substring(1);

                        //Weight Input
                        System.out.print("Please Enter the Weight in Ibs- ");  
                        weight= keyboard.nextDouble();
                        System.out.println();

                        //Returns the correct index of the array for the given name
                        workingStackIndex = getWorkingStackIndex(name);

                        //Pushes the package onto the correct stack
                        if(workingStackIndex != -1 && !packageStacks[workingStackIndex].isFull()){
                            packageStacks[workingStackIndex].push(new Package(name, currentDay, weight));

                            //Prints out if successful and truncates decimal if it's a whole number for weight
                            if(weight % 1 == 0){
                                System.out.println("A " + ((int)weight) + " Ib package is awaiting pickup by " + name + ".\n");
                            }
                            else{
                                System.out.println("A " + weight + " Ib package is awaiting pickup by " + name + ".\n");
                            }
                        }
                        else if(packageStacks[workingStackIndex].isFull()){//Pushes onto another stack if the correct one is full
                            overFlowPush(new Package(name, currentDay, weight), workingStackIndex);

                            //Prints out if successful and truncates decimal if it's a whole number for weight
                            if(weight % 1 == 0){
                                System.out.println("A " + ((int)weight) + " Ib package is awaiting pickup by " + name + ".\n");
                            }
                            else{
                                System.out.println("A " + weight + " Ib package is awaiting pickup by " + name + ".\n");
                            }
                        }
                        else{
                            System.out.println("Not a Valid Name");
                        }

                        break;

                    //Removes the Package on Top of the Stack for the Recipient
                    case 'G':
                    case 'g':
                        //Name Input
                        System.out.print("Please Enter the Recipient's Name- ");  
                        name = keyboard.nextLine();
                        name = name.substring(0, 1).toUpperCase() + name.substring(1);

                        //Returns the correct index of the array for the given name
                        workingStackIndex = getWorkingStackIndex(name);

                        //Checks if the name is valid and if the stack is empty 
                        if(workingStackIndex == -1){
                            System.out.println("Not a Valid Name");
                            break;
                        }
                        else if(packageStacks[workingStackIndex].isEmpty()){
                            System.out.println("There are no Packages for " + name);
                            break;
                        }

                        //Pop off the Stack to the Floor Stack
                        while(!packageStacks[workingStackIndex].peek().getRecipient().equals(name)){
                            floorStack.push(packageStacks[workingStackIndex].pop());
                            count++;
                        }

                        System.out.print("\nMoved " + count + " packages from Stack " + workingStackIndex + " to floor.\n");
                        stackPrint();

                        packagetemp = packageStacks[workingStackIndex].pop();
                        if(packagetemp.getWeight() % 1 == 0){
                            System.out.println("Give " + packagetemp.getRecipient() + " " + ((int)packagetemp.getWeight()) + " Ib package delivered on day " + packagetemp.getArrivalDate());
                        }
                        else{
                            System.out.println("Give " + packagetemp.getRecipient() + " " + packagetemp.getWeight() + " Ib package delivered on day " + packagetemp.getArrivalDate());
                        }

                        //Pop from floor Stack back to Orignal Stack
                        while(!floorStack.isEmpty()){
                            packageStacks[workingStackIndex].push(floorStack.pop());
                        }

                        System.out.println("\nReturning " + count + " packages to stack " + workingStackIndex + " from floor.");
                        stackPrint();
                        count = 0;

                        break;

                    //Increments the current day by one
                    case 'T':   
                    case 't':
                        currentDay++;
                        System.out.println("It is now day " + currentDay + ".\n");

                        break;

                    //Prints All the Stacks
                    case 'P':
                    case 'p':
                        stackPrint();
                        break;

                    //Moves a package from one stack to another
                    case 'M':
                    case 'm':
                        //Enter the source and destination stack
                        System.out.print("Please enter the source stack (enter 0 for floor)- ");  
                        source = keyboard.nextInt() - 1;

                        System.out.print("Please enter the destination stack- ");  
                        destination = keyboard.nextInt() - 1;
                        
                        //Pushes package to stack taking into account edge cases
                        if((source < -1) || (destination < -1) || (source >= SIZE) || (destination >= SIZE)){
                            System.out.println("Not a valid input.\n");
                        }
                        else if(source != -1 && packageStacks[source].isEmpty()){
                            System.out.println("The source stack is empty.\n");
                        }
                        else if(source == destination){
                            System.out.println("The package was put back into the same spot.");
                            break;
                        }
                        else if(source == -1){
                            if(!packageStacks[destination].isFull() && !floorStack.isEmpty()){
                                packageStacks[destination].push(floorStack.pop());
                            }
                            else if(packageStacks[destination].isFull() && !floorStack.isEmpty()){
                                overFlowPush(floorStack.pop(), SIZE - 1);
                            }
                        }
                        else if(destination == -1){
                            floorStack.push(packageStacks[source].pop());
                        }
                        else if(packageStacks[destination].isFull()){
                            overFlowPush(packageStacks[source].pop(), destination);
                        }
                        else{
                            packageStacks[destination].push(packageStacks[source].pop());
                        }

                        break;

                    //Puts all packages in the wrong stack onto the floor stack
                    case 'F':
                    case 'f':
                        stackCount = 0;

                        tempStack = new Stack<>();

                        for(int i = 0; i < SIZE; i++){
                            //Iterate over all the stacks except the floor
                            if(i < SIZE){
                                while(true){
                                    try{
                                        packagetemp = packageStacks[i].pop();
                                    }
                                    catch(Exception e){
                                        break;
                                    }

                                    if(getWorkingStackIndex(packagetemp.getRecipient()) != i){
                                        floorStack.push(packagetemp);
                                    }
                                    else{
                                        tempStack.push(packagetemp);
                                        stackCount++;
                                        
                                    }
                                }
                                for(int j = 0; j < stackCount; j++){
                                    packageStacks[i].push(tempStack.pop());
                                }
                                stackCount = 0;
                            }
                        }

                        System.out.println("Misplaced packages moved to floor.");
                        break;

                    //List all the packages awaiting a user
                    case 'L':
                    case 'l':
                        //Name Input
                        System.out.print("Please Enter the Recipient's Name- ");  
                        name = keyboard.nextLine();
                        name = name.substring(0, 1).toUpperCase() + name.substring(1);

                        stackCount = 0;
                        tempStack = new Stack<>();;

                        //Iterate over all stacks
                        for(int i = 0; i < SIZE + 1; i++){
                            //Iterate over all the stacks except the floor
                            if(i < SIZE){
                                while(true){
                                    try{
                                        packagetemp = packageStacks[i].pop();
                                    }
                                    catch(Exception e){
                                        break;
                                    }

                                    if(packagetemp.getRecipient().equals(name)){
                                        System.out.println("Package " + (count + 1) + " is in Stack " + (i + 1) + ", it was delivered on day " + packagetemp.getArrivalDate() + ", and weighs " + packagetemp.getWeight() + " Ibs.");
                                        count++;
                                        stackCount++;
                                        floorStack.push(packagetemp);
                                    }
                                    else{
                                        floorStack.push(packagetemp);
                                        stackCount++;
                                        
                                    }
                                }
                                for(int j = 0; j < stackCount; j++){
                                    packageStacks[i].push(floorStack.pop());
                                }
                                stackCount = 0;
                            }
                            //Searches the floor stack
                            else{
                                while(true){
                                    try{
                                        packagetemp = floorStack.pop();
                                    }
                                    catch(Exception e){
                                        break;
                                    }

                                    if(packagetemp.getRecipient().equals(name)){
                                        System.out.println("Package " + (count + 1) + " is in floor Stack, it was delivered on day " + packagetemp.getArrivalDate() + ", and weighs " + packagetemp.getWeight() + " Ibs.");
                                        count++;
                                        stackCount++;
                                        tempStack.push(packagetemp);
                                    }
                                    else{
                                        tempStack.push(packagetemp);
                                        stackCount++;
                                        
                                    }
                                }
                                for(int j = 0; j < stackCount; j++){
                                    floorStack.push(tempStack.pop());
                                }
                                stackCount = 0;
                            }
                        }
                        

                        count = 0;
                        break;

                    //Empty's the floor stack
                    case 'E':
                    case 'e':
                        floorStack = new Stack<>();
                        break;
                    
                    //Quits the program
                    case 'Q':
                    case 'q':
                        keyboard.close();
                        System.out.print("Use an Amazon Locker Next Time.");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Not a Valid Menu Option");
                }
            }
            catch(Exception e){
                System.out.println(e.getMessage());
            }
        }
    }

    public static void stackPrint(){
        //Header
        System.out.println("Current Packages- \n--------------------------------");

        //Stacks
        System.out.println("Stack 1 (A-G)- |" + packageStacks[0].toString());
        System.out.println("Stack 2 (H-J)- |" + packageStacks[1].toString());
        System.out.println("Stack 3 (K-M)- |" + packageStacks[2].toString());
        System.out.println("Stack 4 (N-R)- |" + packageStacks[3].toString());
        System.out.println("Stack 5 (S-Z)- |" + packageStacks[4].toString());

        if(floorStack.isEmpty()){
            System.out.println("Floor- |empty.\n");
        }
        else{
            System.out.println("Floor- |" + floorStack.toString() + "\n");
        }
    }

    public static int getWorkingStackIndex(String name){
        if(name.charAt(0) >= 'A' && name.charAt(0) <= 'G'){
            return(0);
        }
        else if(name.charAt(0) >= 'H' && name.charAt(0) <= 'J'){
            return(1);
        }
        else if(name.charAt(0) >= 'K' && name.charAt(0) <= 'M'){
            return(2);
        }
        else if(name.charAt(0) >= 'N' && name.charAt(0) <= 'R'){
            return(3);
        }
        else if(name.charAt(0) >= 'S' && name.charAt(0) <= 'Z'){
            return(4);
        }
        else{
            return(-1);
        }
    }

    public static void overFlowPush(Package p, int currentIndex) throws FullStackException{
        //If the orignal stack is full push package onto a different stack starting from the top
        for(int i = 1; i < SIZE; i++){
            if(currentIndex + i < SIZE){
                packageStacks[currentIndex + i].push(p);
                return;
            }
            else if(currentIndex - i > 0){
                packageStacks[currentIndex - i].push(p);
                return;
            }
        }

        //If all the package stacks are full move to floor stack
        floorStack.push(p);
    }
}