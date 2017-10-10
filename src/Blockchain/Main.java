package src.Blockchain;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Comparator;
import java.util.Scanner;

/**
 * Created by navi on 23/09/17.
 */
public class Main {
    public static void main(String[]args){

    	paramsManager(args);
    }
       
    
    // Separate method to avoid messing up the main
    private static void paramsManager(String[] args) {
    	
        boolean isRunning = true;
    	
        // Using integer as standard up to new requirements    
        // The data of the block isn't always a string?
        BlockChain<Integer> blockChain = new BlockChain<>(new Comparator<Integer>() {
			@Override
			public int compare(Integer o1, Integer o2) {
				return o1-o2;
			}
		});
        
        if (args.length == 2 && args[0].equals("zeroes")) {
        	// Check if it is passing a number as the second parameter
        	if (args[1].matches("\\d+")) {
                blockChain.setAmountZeroes(Integer.parseInt(args[1]));
				System.out.println("Amount of zeroes ("+ args[1] +") set");
			}
        }else {
            System.out.println("Please execute the program in the following format, where N° is the amount of zeroes: ./main zeroes N°");
            return;
        }

        System.out.println("To operate the Blockchain you must use the following commands:");
        System.out.println("help\nquit\nprint state\nadd N°\nremove N°\nlookup N°\nvalidate\n");

        
        Scanner sc = new Scanner(System.in);
        
        while(isRunning) {
            System.out.println("What do you want to do next?(insert command)");
        	String[] commands = sc.nextLine().split(" ");
        	String command = commands[0];
        	
			switch (command) {
				case "help":
					if (commands.length != 1) {
						System.out.println("Operation failed, please enter a valid command");
					}else {
						System.out.println("Command list:");
						System.out.println("help\nquit\nprint state\nadd N°\nremove N°\nlookup N°\nvalidate\n");
					}
				break;

				case "quit":
					if (commands.length != 1) {
						System.out.println("Operation failed, please enter a valid command");
					}else {
						isRunning = false;
					}
				break;
				
				case "print":
					if(commands.length<=1 || !commands[1].equals("state")){
						System.out.println("Operation failed, please enter a valid command");
					}
					else
						blockChain.print();
				break;
				
				case "add":
					if (commands.length != 2) {
						System.out.println("Operation failed, please enter a valid command");
					} else {
						// The data of the block isn't always a string?
						String data = "Insert " + commands[1];
						// This could be modified to support generic classes
						if (blockChain.getTree().contains(Integer.parseInt(commands[1]))) {
							System.out.println("Insertion failed, the avl tree already contains the element " + commands[1]);
							data += " failed";
						} else {
							blockChain.getTree().insert(Integer.parseInt(commands[1]));
						}
						blockChain.add(Integer.parseInt(commands[1]), data);
					}
	            break;
	  
				case "remove": 
					if (commands.length != 2) {
						System.out.println("Operation failed, please enter a valid command");
					} else {
						String data = "Remove " + commands[1];
						// This could be modified to support generic classes
						if (!blockChain.getTree().contains(Integer.parseInt(commands[1]))) {
							System.out.println("Remove failed, the avl tree does not contain the element " + commands[1]);
							data += " failed";
						} else {
							blockChain.getTree().remove(Integer.parseInt(commands[1]));
						}
						blockChain.add(Integer.parseInt(commands[1]), data);
					}
	            break;
	  
				case "lookup": 
					if (commands.length != 2) {
						System.out.println("Operation failed, please enter a valid command");
					} else {
						// This could be modified to support generic classes
						if (!blockChain.getTree().contains(Integer.parseInt(commands[1]))) {
							System.out.println("The avl tree does not contains the element " + commands[1]);
						} else {
							// It is necessary to modify the block/blockchain class to support a data field for fast search,
							// or else use regexp and iterate over the BC which is a bad idea.
							System.out.println(blockChain.getBlockIndexes(Integer.parseInt(commands[1])));
							String data = "check " + commands[1] + " - true";
							blockChain.add(Integer.parseInt(commands[1]), data);
						}
					}	           
					break;
	  
				case "validate":
					if (commands.length != 1) {
						System.out.println("Operation failed, please enter a valid command");
					}else {
						System.out.println(blockChain.isValid());
					}
	            break;
	  
				case "modify": 
					if (commands.length < 3) {
						System.out.println("Operation failed, please enter a valid command");
					} else {
                        {
                            int index = Integer.parseInt(commands[1]);

                            File file = new File(commands[2]);
                            boolean success = false;
                            try {
                                success = blockChain.modifyByIndex(index, file);
                            } catch (FileNotFoundException e) {
                                System.out.println(e);
                            }
                            if (success) {
                                System.out.println("Block data modified successfully");
                            } else {
                                System.out.println("The blockchain could not reach the required index");
                            }
                        }
                    }
	            break;
		    default:
				System.out.println("Wrong command, please try again");
	  
			}    	
        }    	
    }
}
