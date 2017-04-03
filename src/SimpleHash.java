import org.apache.commons.codec.binary.Hex;

import java.security.MessageDigest;

/**
 * Created by eeliz_000 on 4/2/2017.
 */
public class SimpleHash {
    public static void main(String[] args) throws Exception{
        String simple = "A string";
        // central class for creating hashes - takes an algorithm
        MessageDigest md = MessageDigest.getInstance("MD5");
        // MessageDigest works on the basis of bytes
        // so what comes in are bytes and what comes out are also bytes

        // digest method produce a direct output
        // getBytes provides a byte representation of the string
        byte[] ret = md.digest(simple.getBytes("UTF-8"));
        // output number of bytes
        System.out.println("Length of hash: " + ret.length);
        // print the bytes
        for(byte b: ret) {
            System.out.print(b + ", ");
        }

        String verify = Hex.encodeHexString(ret);
        System.out.println(" === ");
        System.out.println(verify);
    }
}
