import java.util.Arrays;

/**
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
		String demo_password = "Password";
		
		//TODO: Has to be
		//      1) generated by secure random
		//      2) individual per case
		//      3) with appropriate length
		byte[] demo_salt = {
				1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,
				//1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16, //***
		};
		
		int demo_iterationcount = 10000;
		//demo_iterationcount++; //***
		
		int demo_size = 256; //size in bits
		//demo_size+=8; //***
		
		//TODO: Salt + Iteration count + Size have to be stored with the hash to recalculate the hash later
		//      -> Iteration count + Size can be constant values, the salt has to be stored individually
		
		//--------------
		//---CREATION---
		//--------------

		PBKDF2 hashgen = new PBKDF2();
		hashgen.init(demo_password.getBytes("UTF8"), demo_salt, demo_iterationcount, demo_size);
		
		byte[] rawhash = hashgen.generateDerivedParameters();
		
		System.out.println("LEN: " + rawhash.length * 8);
		for(byte b: rawhash)
			System.out.print(b + ", ");
		
		byte[] stored_hash = rawhash; //TODO: to be persisted
		
		//------------------
		//---VERIFICATION---
		//------------------
		
		System.out.println("\n\n===Second attempt===\n");

		//demo_password = "Wrong"; //***

		hashgen = new PBKDF2();
		hashgen.init(demo_password.getBytes("UTF8"), demo_salt, demo_iterationcount, demo_size);
		
		rawhash = hashgen.generateDerivedParameters();
		
		System.out.println("LEN: " + rawhash.length * 8);
		for(byte b: rawhash)
			System.out.print(b + ", ");
		
		System.out.println("\nVerification: " + Arrays.equals(stored_hash, rawhash));
	}
}
