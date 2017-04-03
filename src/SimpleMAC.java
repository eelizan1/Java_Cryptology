import org.apache.commons.codec.binary.Hex;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;

/**
 * Created by eeliz_000 on 4/2/2017.
 * THE USE OF MESSAGE AUTHENTICATION CODE (MAC)
 * MAC provides a way to check the integrity of information transmitted using a secret key
 *
 * MAC combines the input value (simple) with the shared secret key (Password)
 */
public class SimpleMAC {
    public static void main(String[] args) throws Exception {
        String simple = "A string";

        // HMACMD5 is a factory pattern with MD5
        //Mac mac = Mac.getInstance("HMACMD5");
        Mac mac = Mac.getInstance("HMACSHA256");

        // the secret key that is shared between sender and reciever
        // key must be in bytes
        mac.init(new SecretKeySpec("Password ".getBytes("UTF-8"), ""));

        // doFinal processes the given array of bytes and finishes the MAC operation.
        byte[] ret = mac.doFinal(simple.getBytes("UTF-8"));
        // output number of bytes
        System.out.println("Length of MAC: " + ret.length);
        // print the bytes
        for (byte b : ret) {
            System.out.print(b + ", ");
        }

        String verify = Hex.encodeHexString(ret);
        System.out.println(" === ");
        System.out.println(verify);
    }
}
