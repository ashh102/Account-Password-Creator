import java.io.FileWriter;
import java.util.Random;
import java.util.Scanner;

public class RandomAccounts {
	public static void main(String[] args) {
		
		@SuppressWarnings("resource")
		Scanner input = new Scanner(System.in);
		
		// Get number of accounts from user.
		System.out.println("\nHow many accounts do you want to generate? ");
		int numAcc = input.nextInt();
		
		// Get minimum length of password from user.
		System.out.println("\nWhat is the minimum length of the passwords?");
		int min = input.nextInt();
			
		// Get maximum length of password from user.
		System.out.println("\nWhat is the maximum length of the passwords?");
		int max = input.nextInt();
	
		// Variables
		String username;
		String password;
		int passwordLen;
		
		try {
			FileWriter w1 = new FileWriter("file1.txt", true);
			
			// Generate inputed number of accounts.
			for (int i=0; i < numAcc; i++) {
				try {
					
					// Generates random username of length 10.
					username = generateRandomUsername(10);
					
					// Generates a random length for password based on inputed min and max.
					passwordLen = generateRandomPasswordLength(min, max);
					
					// Takes random length generated and generates random numeric password of that length.
					password = generateRandomPassword(passwordLen);
					System.out.println(username + " " + password);
					w1.write(username + " " + password + "\n");
					username = "";
					
				} catch(Exception e){
					System.out.println("\nERROR: Could not generate accounts.  Please restart.");
				}
			}
			w1.close();	
		} catch (Exception e) {
			System.out.println("\nERROR: Could not write to file.  Please restart.");
		}
	}

	// Generates random username with specified length.
	public static String generateRandomUsername(int len) {
		String chars = "abcdefghijklmnopqrstuvwxyz";
		Random rnd = new Random();
		StringBuilder sb = new StringBuilder(len);
		
		for (int i = 0; i < len; i++) {
			sb.append(chars.charAt(rnd.nextInt(chars.length())));
		}
		return sb.toString();
	}
	
	// Generates random password with specified length.
	public static String generateRandomPassword(int len) {
		String chars = "0123456789";
		Random rnd = new Random();
		StringBuilder sb = new StringBuilder(len);
		
		for (int i = 0; i < len; i++) {
			sb.append(chars.charAt(rnd.nextInt(chars.length())));
		}
		return sb.toString();
	}
	
	// Generates random length between 2 given numbers.
	public static int generateRandomPasswordLength(int upper, int lower) {
		int r = (int) (Math.random() * (upper - lower)) + lower;
		return r;
	}
}
