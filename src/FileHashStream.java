import org.apache.commons.codec.binary.Hex;

import java.io.FileInputStream;
import java.security.MessageDigest;

/**
 * Created by eeliz_000 on 4/2/2017.
 * TO VERIFY LARGER FILE SIZES USING STREAM
 */
public class FileHashStream {
    public static void main(String[] args) throws Exception {
        // file is in downloads
        String path = "C:\\Users\\eeliz_000\\Downloads\\commons-codec-1.10-bin.zip";

        MessageDigest md = MessageDigest.getInstance("MD5");

        byte[] buffer = new byte[1024 * 16];
        int len = 0;

        FileInputStream is = new FileInputStream(path);

        while(true) {
            len = is.read(buffer);
            if(len < 0)
                break;
            //use buffer to update the digest
            md.update(buffer, 0, len);

        }

        is.close();
        // message digest is filled so no need to pass any arguments
        // it is told we want to have the final hash result
        byte[] ret = md.digest();

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
