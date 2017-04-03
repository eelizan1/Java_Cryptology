package PBKDF2;

import org.apache.commons.codec.binary.Hex;

import java.util.Arrays;

/*
 * THE APPLICATION LEVEL
 * This class demonstrates the usage of PBKDF2 secure password hash creation
 * and allows to play around with the input parameters (***)
 * for better understanding.
 * 
 * For production usage, please note the "TODO" tags!
 * 
 * Works for Java and Android.
 * 
 */

public class PBKDF2Example {
	public static void main(String[] args) throws Exception{
		// the user password
		String demo_password = "Password";
		//TODO: Has to be
		//      1) generated by secure random function
		//      2) individual per case
		//      3) with appropriate length - at least 128 bits for the salt length

		// the salt
		byte[] demo_salt = {
				1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,
				//1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16, //***
		};

		// the iteration count to rehash the password to prevent brute force attacks
		// at-least 10,000 iterations / android have at-least 5,000
		int demo_iterationcount = 10000;
		//demo_iterationcount++; //***

		// the size of the hash
		int demo_size = 256; //size in bits
		//demo_size+=8; //***
		
		//TODO: Salt + Iteration count + Size have to be stored with the hash to recalculate the hash later
		//      -> Iteration count + Size can be constant values, the salt has to be stored individually
		
		//--------------
		//---CREATION---
		//--------------

		PBKDF2 hashgen = new PBKDF2();
		// could come a database and not just local variables that we have defined
		hashgen.init(demo_password.getBytes("UTF8"), demo_salt, demo_iterationcount, demo_size);
		// the has value which stores the generate the hash
		// this is the comparison value when the user logs in again
		// you can transfer to hex value
		byte[] rawhash = hashgen.generateDerivedParameters();
		
		System.out.println("LEN: " + rawhash.length * 8);
		for(byte b: rawhash)
			System.out.print(b + ", ");

		// creation part of the hash
		byte[] stored_hash = rawhash; //TODO: to be persisted
		String verify = Hex.encodeHexString(stored_hash);
		System.out.println();
		System.out.println("Hex Value: " + verify);
		//------------------
		//---VERIFICATION---
		//------------------
		System.out.println();
		System.out.println("Old : " + demo_password);
		System.out.println("\n\n===Second attempt===\n");

		demo_password = "Wrong"; //***
		System.out.println("New : " + demo_password);
		// do hashing procedure again
		hashgen = new PBKDF2();
		hashgen.init(demo_password.getBytes("UTF8"), demo_salt, demo_iterationcount, demo_size);

		/*
			NOTE sender and receiver are using same variables but because in theory they should be using the same procedure
			 in which they would have their own values
		 */
		rawhash = hashgen.generateDerivedParameters();
		
		System.out.println("LEN: " + rawhash.length * 8);
		for(byte b: rawhash)
			System.out.print(b + ", ");

		String verify2 = Hex.encodeHexString(rawhash);
		System.out.println();
		System.out.println("Hex Value: " + verify2);
		// compare the two results of both hashes
		System.out.println("\nVerification: " + Arrays.equals(stored_hash, rawhash));
	}
}