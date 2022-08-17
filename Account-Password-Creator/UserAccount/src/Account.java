import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.File;
import java.util.Arrays;
import java.util.Scanner;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class Account {
	public static void main(String[] args) {
		
		// Get current file path.
		String userDirectory = System.getProperty("user.dir");
		
		// Create files in proj folder.
		@SuppressWarnings("unused")
		File file1 = new File(userDirectory, "file1.txt");
		@SuppressWarnings("unused")
		File file2 = new File(userDirectory, "file2.txt");
		@SuppressWarnings("unused")
		File file3 = new File(userDirectory, "file3.txt");
		
		// Create new account object
		Account account = new Account();
		
		// Create new input object
		@SuppressWarnings("resource")
		Scanner input = new Scanner(System.in);
		
		// Variables
		String name = "";
		String password = "";
		int opt = 0;

		System.out.println("Welcome to the Account Creator.");
		
		while(true) {
			
			// Directions
			System.out.println("Please select an option.");
			System.out.println("1. Create New Account\n2. Login\n3. Exit");
			System.out.println("Option selected: ");
			
			// Taking user input
			opt = input.nextInt();
			
			// User options
			switch(opt) {
			
				// Creates account.
				case 1:
					System.out.println("Please enter a username: ");
					input.nextLine();
					name = input.nextLine();
					
					System.out.println("\nPlease enter a  password: ");
					password = input.nextLine();
					
					if(account.createAccount(name, password))
						System.out.println("\nAccount Created.");
					else
						System.out.println("Account unable to be created.  Please try again.\n");
					break;
					
				// Login to account.
				case 2:
					System.out.println("\nUsername: ");
					input.nextLine();
					name = input.nextLine();
					
					System.out.println("\nPassword: ");
					password = input.nextLine();
					
					// file1
					if(account.Validation1(name, password))
						System.out.println("\nLogged in successfully from file1.");
					else
						System.out.println("\nLogin in failed from file1.");
					
					// file2
					if(account.Validation2(name, password))
						System.out.println("\nLogged in successfully from file2.");
					else
						System.out.println("\nLogin in failed from file2.");
					
					// file3
					if(account.Validation3(name, password))
						System.out.println("\nLogged in successfully from file3.");
					else
						System.out.println("\nLogin in failed from file3.");
					break;
				
				// Quit.
				case 3:
					return;
					
				// If none of above options
				default:
					System.out.println("\nPlease enter a valid option.");
					break;
			}
		}
	}
	  
	public boolean createAccount(String name, String password) {
		
		// Username is 10 chars or less and only letters.
		if(name.length() <= 10 & name.matches("[a-zA-Z]+")) {
			
			// Password is 10 digits or less.
			try {
				Integer.parseInt(password);
				try{
					FileWriter w1 = new FileWriter("file1.txt", true);
					FileWriter w2 = new FileWriter("file2.txt", true);
					FileWriter w3 = new FileWriter("file3.txt", true);
					
					// Store regular username and password in file1.
					w1.write(name + " " + password + "\n");
					
					// Store hashed username and hashed password in file2.
					int hash = hashedPassword(password);
					w2.write(name + " " + hash + "\n");
					
					// Get salted+hashed password. Code chunk from https://www.javainterviewpoint.com/java-salted-password-hashing/
					MessageDigest md;
			        try
			        {
			            // Select the message digest for the hash computation -> SHA-256
			            md = MessageDigest.getInstance("SHA-256");

			            // Generate the random salt
			            SecureRandom random = new SecureRandom();
			            byte[] salt = new byte[1];
			            random.nextBytes(salt);
			            System.out.println(salt + "This is in createaccount");
			            // Passing the salt to the digest for the computation
			            md.update(salt);

			            // Generate the salted hash
			            byte[] hashedPassword = md.digest(password.getBytes(StandardCharsets.UTF_8));

			            // Store username and salted hashed password in file3.
			            w3.write(name + " " + hashedPassword + " " + salt + "\n");
			            
			        } catch (NoSuchAlgorithmException e) {
			            // TODO Auto-generated catch block
			            e.printStackTrace();
			        }
			        
					w1.close();
					w2.close();
					w3.close();
					
					return true;
				} catch(Exception e) {
					// Error message if files aren't opened and values don't get stored.
					System.out.println("\nERROR: Could not store values to files.");
					return false;
				}
			
			} catch(Exception e) {
				// Error message if password is not 10 digits or less.
				System.out.println("\nERROR: Password must contain only 10 digits or less.");
				return false;
			}
		} else {
			// Error message if username is not 10 letters or less.
			System.out.println("\nERROR: Username must contain only 10 letters or less.");
			return false;
		}
	}
	
	
	public boolean Validation1(String name, String password) {
		// Parts pulled from https://homepages.dcc.ufmg.br/~andrehora/examples/java.io.BufferedReader.readLine.11.html
		try {
			// Open file for reading.
			@SuppressWarnings("resource")
			BufferedReader reader = new BufferedReader(new FileReader("file1.txt"));
			String line = reader.readLine();
			
			// While lines aren't empty.
			while(line!=null) {
				// Split string at space.
				String[] currentUser = line.split(" ");
				
				// If first string is the same as username
				if(currentUser[0].compareToIgnoreCase(name)==0) {
					// If second string is the same as password
					if(currentUser[1].compareToIgnoreCase(password)==0)
						return true;
				}
				line = reader.readLine();
			}
			return false;
		} catch(Exception e) {
			System.out.println("\nFile1 failed.");
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean Validation2(String name, String password) {
		// Parts pulled from https://homepages.dcc.ufmg.br/~andrehora/examples/java.io.BufferedReader.readLine.11.html
		try {
			// Open file for reading.
			@SuppressWarnings("resource")
			BufferedReader reader = new BufferedReader(new FileReader("file2.txt"));
			String line = reader.readLine();
			
			// While lines aren't empty
			while(line!=null) {
				// Split string at space.
				String[] currentUser = line.split(" ");
				
				// If first string is the same as username
				if(currentUser[0].compareToIgnoreCase(name)==0) {
					// If second string is the same as hashed password
					if(Integer.parseInt(currentUser[1])==hashedPassword(password))
						return true;
				}
				line = reader.readLine();
			}
			return false;
		} catch(Exception e) {
			System.out.println("\nFile2 failed.");
			e.printStackTrace();
		}
		return false;
	}
	  
	public boolean Validation3(String name, String password) {
		// Parts pulled from https://homepages.dcc.ufmg.br/~andrehora/examples/java.io.BufferedReader.readLine.11.html
		try {
			// Open file for reading.
			@SuppressWarnings("resource")
			BufferedReader reader = new BufferedReader(new FileReader("file3.txt"));
			String line = reader.readLine();
			
			// While lines aren't empty.
			while(line!=null) {
				
				// Split string at space.
				String[] currentUser = line.split(" ");
				
				// If first string is the same as username.
				if(currentUser[0].compareToIgnoreCase(name)==0) {
					
					MessageDigest md;
			        
					// Select the message digest for the hash computation -> SHA-256
					md = MessageDigest.getInstance("SHA-256");

			        // Supposed to set the salt to the salt stored in file3, but doesn't for some reason.
					byte[] salt = currentUser[2].getBytes(StandardCharsets.UTF_8); 
			         
					
					// Passing the salt to the digest for the computation
					md.update(salt);
					System.out.println(salt + "This is in validation");
					byte[] hashedPassword = md.digest(password.getBytes(StandardCharsets.UTF_8));
					
					// If passwork stored in second field is equal to the generated password with
					// the stored salt value and hashed.
			        if(currentUser[1]==Arrays.toString(hashedPassword)) {
				        return true;
			        }  
				}
				line = reader.readLine();
			}
			return false;
		} catch(Exception e) {
			System.out.println("\nFile3 failed.");
			e.printStackTrace();
		}
		return false;
	}
	  
	// Hashed password.
	private int hashedPassword(String password) {
		int hash = password.hashCode();
		return hash;
	}
}