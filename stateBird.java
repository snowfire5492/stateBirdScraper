/*
 * Name: 		Schenck, Eric
 * Project: 	Internet Bound
 * Due:		 	may 27, 2018
 * Course:		CS380
 * 
 * Description: A program to read in 2 lines of input, 
 * first line "stateName | stateName --bat=terminal | --help | -- synopsis"
 * second line ".eot" 
 * program will then either execute the "--" commands or check if stateName is valid
 * If stateName is spelled correctly then program will print out the state bird name
 * 
 */



import java.io.IOException;
import java.net.URL;									// using .net.URL library
import java.util.Scanner;								


public class stateBird {
	
	private static boolean stateNameFound;
	@SuppressWarnings("unused")
	private static String userName;
	@SuppressWarnings("unused")
	private static String userHash;
	private static String line;
	private static String userInput;
	private static String otherInput;
	private static String eot;
	private static String[][] stateBirdList = new String[50][2];	// used to store state and statebird
	
	private static String address = "http://www.slate.com/articles/health_and_science"
			+ "/science/2013/05/state_bird_improvements_replace_cardin"
			+ "als_and_robins_with_warblers_and_hawks.html";
	
	
	private static void checkUserInput() throws IOException{
		
		Scanner keyboard = new Scanner(System.in);	
		userInput = keyboard.nextLine();				// getting first line of user input
		
		
		if(userInput.toLowerCase().contains("--help")){
			eot=keyboard.nextLine().toLowerCase();			// getting second line of input
			if(eot.contains(".eot")){
				printHelpInfo();	
			}else{
				System.out.println("Error: Incorrect END OF TRANSMISSION command...");
			}
			
		}else if(userInput.toLowerCase().contains("--synopsis")){
			eot=keyboard.nextLine().toLowerCase();			// getting second line of input
			if(eot.contains(".eot")){
				System.out.println("Prints out the state bird for given U.S. state. (v1.0.0");
			}else{
				System.out.println("Error: Incorrect END OF TRANSMISSION command...");
			}
			
		}else if(userInput.toLowerCase().contains("--bat")){
			eot=keyboard.nextLine().toLowerCase();			// getting second line of input
			if(eot.contains(".eot")){
				batFunction();								// handle --bat input
			}else{
				System.out.println("Error: Incorrect END OF TRANSMISSION command...");
			}
			
		}else{												
			eot=keyboard.nextLine().toLowerCase();			// getting second line of input
			if(eot.contains(".eot")){
				stateBirdCheck(userInput);								//calling stateBirdCheck function
			}else{
				System.out.println("Error: Incorrect END OF TRANSMISSION command...");
			}
			
		}
		keyboard.close();
	}
	
	private static void batFunction() throws IOException{

		int from = userInput.indexOf("--bat"); 					// finds index for beginning of --bat
		from += 5;												// setting from index to end of --bat
		
		if(userInput.substring(from,from+1).contains("=")){		// used incase of '=' following --bat				
			++from;												// increment from by 1 accounting for '='
		}
		
		try{
			userName = userInput.substring(from, userInput.indexOf(".")); // grabbing userName and storing it
			userHash = userInput.substring(userInput.indexOf("."));			//grabbing user hash value
			// these values will be ignored. But i wanted to distinguish them being there
		}catch(Exception e){
			System.out.println("Error: Incorrect '--bat' format. See -help for more information.");
		}
			otherInput = userInput.substring(0, userInput.indexOf("--bat")); // more important part of all this if statement
									// gets all input up to "--bat"

		stateBirdCheck(otherInput);	// stateBird function will check other input and find stateBird if correct input
			
	}
	
	
	private static void stateBirdCheck(String userInput) throws IOException{

		URL pageLocation = new URL(address); 					// creating URL object
		Scanner input = new Scanner(pageLocation.openStream()); 
			
		int j;
		int i = 0;
		while(input.hasNext()) {
			line = input.nextLine();									// get next line
										
			if(line.contains("strong")) {								// only look at lines containing "strong"
				int from = line.indexOf(">");							// string manipulation 
				int to = line.indexOf("</");
				
				if(line.substring(from,to).contains("state bird:")){ 	// more string manipulation
					
					int fromtwo = line.substring(from, to).indexOf("."); // used to find index of states
					
					int toTwo = line.substring(from, to).lastIndexOf("."); // used to find index of states
					
					int fromThree = line.substring(from, to).indexOf(":");	// used to find index of birds
					j = 0;
					stateBirdList[i][j] = line.substring(fromtwo + 4, toTwo + 2); // storing state in array 
					++j;
					stateBirdList[i][j] = line.substring(fromThree + 4, to);	  // storing bird in array next to state
					++i;
				}
			}
		}
		
		stateNameFound = false;
		
		for(int k = 0; k < stateBirdList.length; ++k){
			if(userInput.toLowerCase().contains(stateBirdList[k][0].toLowerCase())){	// user input found now print bird name
				System.out.println(stateBirdList[k][0] + " state bird is the " + stateBirdList[k][1]);
				stateNameFound = true;
			}
			
			
			if((k+1) == stateBirdList.length && !stateNameFound){
				System.out.println("Error: Incorrect State Name");
			}
		}
	input.close();
	}
	
	
	private static void printHelpInfo(){
		System.out.println("\nstateBird [-s] STATENAME [--bat=token] | --help | --synopsis");
		System.out.println("\n'stateBird' returns the State bird of a user given State. Can print only valid States. ");
		System.out.println("\nSTATENAME is the name of a State. For example, 'stateBird -s california' will print\n"
				+ "the state bird from California. Case-insensitive. Use of '-s' switch is optional.");
		
		System.out.println("Version		: 1.0.0\n"
						 + "Dependencies	: none\n"
						 + "Author		: Schenck, Eric\n"
						 + "Contact		: egschenck@cpp.edu");
	}
	

	
	public static void main(String[] args) throws IOException {
		checkUserInput();
	}
	
}
