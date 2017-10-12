package src;

import Blockchain.BlockChain;
import Blockchain.AvlTree;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.Scanner;

/**
 * Created by navi on 23/09/17.
 */
public class Main {
	
	public static void main(String[]args){

		paramsManager(args);
	}


    /**
     * This method is responsible of the console commands functionality
     */
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
				blockChain.setAmountZeros(Integer.parseInt(args[1]));
				System.out.println("Amount of zeros ("+ args[1] +") set");
			}
		}else {
			System.out.println("Please execute the program in the following format, where N° is the amount of zeros: ./main zeros N°");
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
						System.out.println(blockChain);
					break;

				case "add":
					if (commands.length != 2 ) {
						System.out.println("Operation failed, please enter a valid command");
					} else if(!isNumber(commands[1])) {
						System.out.println("Tree currently only allows integers");
					}
					else
					{
						String data = "Insert " + commands[1];
						// This could be modified to support generic classes
						if (blockChain.getTree().contains(Integer.parseInt(commands[1]))) {
							System.out.println("Insertion failed, the avl tree already contains the element " + commands[1]);
							data += " failed";
						} else {
							blockChain.getTree().insert(Integer.parseInt(commands[1]));
						}
						blockChain.add(Integer.parseInt(commands[1]), data, blockChain.getTree().clone());
					}
					break;

				case "remove":
					if (commands.length != 2 ) {
						System.out.println("Operation failed, please enter a valid command");
					} else if(!isNumber(commands[1])) {
						System.out.println("Tree currently only allows integers");
					}
					else
					{
						String data = "Remove " + commands[1];
						// This could be modified to support generic classes
						if (!blockChain.getTree().contains(Integer.parseInt(commands[1]))) {
							System.out.println("Remove failed, the avl tree does not contain the element " + commands[1]);
							data += " failed";
						} else {
							blockChain.getTree().remove(Integer.parseInt(commands[1]));
						}
						blockChain.add(Integer.parseInt(commands[1]), data, blockChain.getTree().clone());
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
							blockChain.add(Integer.parseInt(commands[1]), data, blockChain.getTree().clone());
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
						System.out.println("Operation failed, not a valid command");
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

				case "save":
					try {
						PrintWriter writer = new PrintWriter("./src/Blockchain/file", "UTF-8");
						writer.print(blockChain.toString());
						writer.close();
					} catch (FileNotFoundException e) {
						System.out.println(e + ". File does not exist");
					} catch (UnsupportedEncodingException e) {
						System.out.println(e + ". Not a valid encoding");
					}
					break;

				case "load":
					try {
						load(blockChain, "./src/Blockchain/file"); //todo: el file es una variable y ellos deciden de donde cargar
					} catch (IOException e) {
						System.out.println(e);
					}
					break;

				default:
					System.out.println("Wrong command, please try again");

			}
		}
	}

	private static boolean isNumber(String str) {
		char[] array= str.toCharArray();
		int i=0, length=str.length();
		Character c= new Character('c');
		boolean ret=true;
		if(array[0]=='-') {
			if (length > 1) {
				i++;
			} else {
				return false;
			}
		}
		for(;i<str.length();i++){
			ret=ret&& c.isDigit(array[i]);
		}
		return ret;
	}

	// Loads data from file and overwrites previous data (made for Integers, could be modified easily to support more object types)
	public static void load(BlockChain<Integer> bc, String fileName) throws IOException {
		String bcString = new String(Files.readAllBytes(Paths.get(fileName)));

		// All elements are blocks except for the last one that has the other blockChain info
		String[] data = bcString.split("-------------------------");
		String bcData = data[data.length - 1];
		int blockAmount = data.length - 1;
		String[] bcDataLines = bcData.split("\n");
		if (!(bcDataLines[bcDataLines.length-3].startsWith("Index:") && bcDataLines[bcDataLines.length-2].startsWith("AmountZeroes:") && bcDataLines[bcDataLines.length-1].startsWith("Tree:"))) {
			System.out.println("Wrong file format");
			return;
		}
		Integer index = Integer.parseInt(bcDataLines[bcDataLines.length-3].split(" ")[1]);
		Integer amountZeroes = Integer.parseInt(bcDataLines[bcDataLines.length-2].split(" ")[1]);
		AvlTree<Integer> tree = bc.getTree().load(bcDataLines[bcDataLines.length-1].split(" ")[1], bc.getTree().getCmp());
		bc.resetChain();
		for(int i=0; i<blockAmount; i++) {
			String[] blockLines = data[i].trim().split("\n");
			if(!(blockLines[0].startsWith("Index:") && blockLines[1].startsWith("Nonce:") && blockLines[2].startsWith("Tree:") &&
					blockLines[3].startsWith("Previous:") && blockLines[4].startsWith("HashCode:") && blockLines[5].startsWith("Elem:") && blockLines[6].startsWith("Data:"))) {
				System.out.println("Wrong file format2 in block " + i);
				System.out.println(data[i]);
				return;
			}
			Integer nonce = Integer.parseInt(blockLines[1].split(" ")[1]);
			AvlTree<Integer> blockTree = bc.getTree().load(blockLines[2].split(" ")[1], bc.getTree().getCmp());
			String prevHexa = blockLines[3].split(" ")[1];
			String hexa = blockLines[4].split(" ")[1];
			Integer elem = Integer.parseInt(blockLines[5].split(" ")[1]);
			String blockData = blockLines[6].substring(blockLines[6].indexOf(" ")+1,blockLines[6].length()); // length - 1?
			for(int j=7; j<blockLines.length; j++) {
				blockData += blockLines[j];
				
}
			bc.add(elem, blockData, blockTree, nonce, hexa, prevHexa);
		}
		bc.setProperties(index, amountZeroes, tree);
	}
}