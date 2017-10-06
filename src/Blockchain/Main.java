package src.Blockchain;

import java.util.Comparator;
import java.util.Scanner;

/**
 * Created by navi on 23/09/17.
 */
public class Main {
    public static void main(String[]args){

    	paramsManager(args);

    	
//    	AvlTree<Integer> tree = new AvlTree<>(new Comparator<Integer>() {
//            @Override
//            public int compare(Integer o1, Integer o2) {
//                return o1-o2;
//            }
//        });
//
//        /* Constructing tree given in the above figure */
//        tree.insert(5);
//        tree.insert(7);
//        tree.insert(3);
//        tree.insert(4);
//        tree.insert(2);
//        tree.remove(7);
//
//
//
//        /* The constructed AVL Tree would be
//             30
//            /  \
//          20   40
//         /  \     \
//        10  25    50
//        */
//        System.out.println("Preorder traversal" +
//                " of constructed tree is : ");
//        tree.print();
//        BlockChain<Integer> blockChain = new BlockChain<>(new Comparator<Integer>() {
//            @Override
//            public int compare(Integer o1, Integer o2) {
//                return o1-o2;
//            }
//        });
//        blockChain.setAmountZeroes(2);
//        blockChain.add(1);
//        blockChain.add(2);
//        blockChain.print();
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
        
        if (args.length == 2 && args[0].equals("zeros")) {
        	// Check if it is passing a number as the second parameter
        	if (args[1].matches("\\d+")) {
                blockChain.setAmountZeroes(Integer.parseInt(args[1]));
				System.out.println("Amount of zeros set");
			}
        }
        System.out.println("To operate the Blockchain you must use the following comands:");
        System.out.println("Comand list:");
        System.out.println("help\nquit\nprint state\nadd N°\nremove N°\nlookup N°\nvalidate\n");

        
        Scanner sc = new Scanner(System.in);
        
        while(isRunning) {
            System.out.println("What do you want to do next?(insert comand)");
        	String[] commands = sc.nextLine().split(" ");
        	String command = commands[0];
        	
			switch (command) {
				case "help":
					System.out.println("Comand list:");
					System.out.println("help\nquit\nprint state\nadd N°\nremove N°\nlookup N°\nvalidate\n");
				break;

				case "quit":
					isRunning = false;
				break;
				
				case "print":
					if(commands[1].equals("state"))
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
							System.out.println("Insertion failed, the avl tree does not contains the element " + commands[1]);
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
					System.out.println(blockChain.isValid());
	            break;
	  
				case "modify": 
					if (commands.length < 3) {
						System.out.println("Operation failed, please enter a valid command");
					} else {
						
						int index = Integer.parseInt(commands[1]);

						// la comento porque no se que seria T file que pide, ya que nuestro T son Integers
						/*boolean success = blockChain.modifyByIndex(index, commands[2]);
						
						if (success) {
							System.out.println("Block data modified successfully");
						} else {
							System.out.println("The blockchain could not reach the required index");
						}*/
					}
	            break;
		    default:
				System.out.println("Wrong command, please try again");
	  
			}    	
        }    	
    }
}
