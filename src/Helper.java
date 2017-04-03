/**
 * Created by eeliz_000 on 4/2/2017.
 *
 * TO VERIFY HASH VALUE: 59b6046b8cb5bf48c3b2e63bb4424779
 * BY READING BYTES FROM A FILE
 * Retrieve a file then output all the bytes
 */
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class Helper {
    public static byte[] read(InputStream is) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        byte[] buffer = new byte[1024 * 16];
        int len = 0;

        while(true) {
            len = is.read(buffer);
            if(len < 0)
                break;
            baos.write(buffer, 0, len);
        }

        is.close();

        return baos.toByteArray();
    }
}
