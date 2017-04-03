import org.apache.commons.codec.binary.Hex;

import java.io.FileInputStream;
import java.security.MessageDigest;

/**
 * Created by eeliz_000 on 4/2/2017.
 */
public class FileHash {
    public static void main(String[] args) throws Exception{
        // file is in downloads
        String path = "C:\\Users\\eeliz_000\\Downloads\\commons-codec-1.10-bin.zip";

        byte[] file = Helper.read(new FileInputStream(path));

        // central class for creating hashes - takes an algorithm
        MessageDigest md = MessageDigest.getInstance("MD5");
        // MessageDigest works on the basis of bytes
        // so what comes in are bytes and what comes out are also bytes

        // digest method produce a direct output
        byte[] ret = md.digest(file);
        // output number of bytes
        System.out.println("Length of hash: " + ret.length);
        // print the bytes
        for(byte b: ret) {
            System.out.print(b + ", ");
        }

        // Grab hex value of ret
        String compare = "59b6046b8cb5bf48c3b2e63bb4424779";
        String verify = Hex.encodeHexString(ret);
        System.out.println(" === ");
        System.out.println(verify);

        // verify MD5 hashes
        System.out.println(verify.equals(compare));
    }
}
