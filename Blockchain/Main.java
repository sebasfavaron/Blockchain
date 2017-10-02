import java.util.Comparator;
import java.util.Scanner;

/**
 * Created by navi on 23/09/17.
 */
public class Main {
    public static void main(String[]args){

    	//paramsManager(args);
    	BlockChain<Integer> blockChain = new BlockChain<>(new Comparator<Integer>() {
			@Override
			public int compare(Integer o1, Integer o2) {
				return o1-o2;
			}
		});
    	blockChain.add(1);
    	blockChain.setAmountZeroes(2);

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
        BlockChain<String> blockChain = new BlockChain<>(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });
        
        AvlTree<Integer> tree = new AvlTree<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1-o2;
            }
        });
        
        if (args.length == 2 && args[0] == "zeros") {
        	// Check if it is passing a number as the second parameter
        	if (args[1].matches("\\d+")) {
                blockChain.setAmountZeroes(Integer.parseInt(args[1]));        		
        	}
        }
        
        Scanner sc = new Scanner(System.in);
        
        while(isRunning) {
        	
        	String[] commands = sc.nextLine().split(" ");
        	String command = commands[0];
        	
			switch (command) {
				case "quit" :
					isRunning = false;
				break;
				
				case "zeros": 
					if (commands.length != 2) {
						System.out.println("Opeartion failed, please enter a valid command");
					} else {
						blockChain.setAmountZeroes(Integer.parseInt(commands[1]));
					}
				break;
				
				case "add":
					if (commands.length != 2) {
						System.out.println("Opeartion failed, please enter a valid command");
					} else {
						// The data of the block isn't always a string?
						String data = "Insert " + commands[1];
						// This could be modified to support generic classes
						if (tree.contains(Integer.parseInt(commands[1]))) {
							System.out.println("Insertion failed, the avl tree already contains element " + commands[1]);
							data += " failed";
						} else {
							tree.insert(Integer.parseInt(commands[1]));
						}
						blockChain.add(data);
					}
	            break;
	  
				case "remove": 
					if (commands.length != 2) {
						System.out.println("Opeartion failed, please enter a valid command");
					} else {
						String data = "Remove " + commands[1];
						// This could be modified to support generic classes
						if (!tree.contains(Integer.parseInt(commands[1]))) {
							System.out.println("Insertion failed, the avl tree does not contains the element " + commands[1]);
							data += " failed";
						} else {
							tree.remove(Integer.parseInt(commands[1]));
						}
						blockChain.add(data);
					}
	            break;
	  
				case "lookup": 
					if (commands.length != 2) {
						System.out.println("Opeartion failed, please enter a valid command");
					} else {
						// This could be modified to support generic classes
						if (!tree.contains(Integer.parseInt(commands[1]))) {
							System.out.println("The avl tree does not contains the element " + commands[1]);
						} else {
							// It is necessary to modify the block/blockchain class to suppor a data field for fast search, 
							// or else use regexp and iterate over the BC which is a bad idea.
							//System.out.println(blockChain.getBlockIndexes(commands[1]));
							String data = "check " + commands[1] + " - true";
							blockChain.add(data);
						}
					}	           
					break;
	  
				case "validate": 
					System.out.println(blockChain.isValid());
	            break;
	  
				case "modify": 
					if (commands.length < 3) {
						System.out.println("Opeartion failed, please enter a valid command");
					} else {
						
						int index = Integer.parseInt(commands[1]);
						
						boolean success = blockChain.modifyByIndex(index, commands[2]);
						
						if (success) {
							System.out.println("Block data modified successfully");
						} else {
							System.out.println("The blockchain could not reach the required index");
						}
					}
	            break;
	  
			}    	
        }    	
    }
}
