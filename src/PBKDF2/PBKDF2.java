package PBKDF2; /**
 * This class is based on the Bouncy Castle Java class
 * org.bouncycastle.crypto.generators.PKCS5S2ParametersGenerator
 * 
 * It uses the SHA512 HMAC as default. Only JCE/JCA classes are being used.
 * HmacSHA512 is included in JRE 1.8++. This class also runs below 1.8.
 * Then, BC or others have to be used to include the HmacSHA512 algorithm
 * or a different HMAC has to be used.
 * 
 * 
 * 
 * The Bouncy Castle License
 *
 * Copyright (c) 2000-2015 The Legion Of The Bouncy Castle Inc.
 * (http://www.bouncycastle.org)
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 * 
 * 
 * 
 * Generator for PBE derived keys and ivs as defined by PKCS 5 V2.0 Scheme 2.
 * <p>
 * The document this implementation is based on can be found at <a
 * href=http://www.rsasecurity.com/rsalabs/pkcs/pkcs-5/index.html> RSA's PKCS5
 * Page</a>
 *
 *  THIS IS THE CRYPT SCHEME TO HASH THE HASH THE PASSWORD
 */

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class PBKDF2 {
	private byte[] password;
	private byte[] salt;
	private int iterationCount;
	private int keysize;

	private Mac hMac;
	private byte[] state;

	public PBKDF2() {
		try {
			hMac = Mac.getInstance("HmacSHA512");
		} catch (Exception e) {
			e.printStackTrace();
		}
		state = new byte[hMac.getMacLength()];
	}

	// essential internal method
	private void F(byte[] S, int c, byte[] iBuf, byte[] out, int outOff) throws Exception{
		if (c <= 0) {
			throw new IllegalArgumentException("iteration count must be at least 1.");
		}

		if (S != null) {
			hMac.update(S, 0, S.length);
		}

		hMac.update(iBuf, 0, iBuf.length);
		hMac.doFinal(state, 0);

		System.arraycopy(state, 0, out, outOff, state.length);

		// loop that runs through the iteration (schema)  count which constantly updates the hMac function (schema)
		for (int count = 1; count < c; count++) {
			hMac.update(state, 0, state.length);
			hMac.doFinal(state, 0);

			for (int j = 0; j != state.length; j++) {
				out[outOff + j] ^= state[j];
			}
		}
	}

	private byte[] generateDerivedKey(int dkLen) throws Exception{
		int hLen = hMac.getMacLength();
		int l = (dkLen + hLen - 1) / hLen;
		byte[] iBuf = new byte[4];
		byte[] outBytes = new byte[l * hLen];
		int outPos = 0;

		hMac.init(new SecretKeySpec(password, ""));

		for (int i = 1; i <= l; i++) {
			// Increment the value in 'iBuf'
			int pos = 3;
			while (++iBuf[pos] == 0) {
				--pos;
			}

			F(salt, iterationCount, iBuf, outBytes, outPos);
			outPos += hLen;
		}

		return outBytes;
	}

	// provide the salt, password, and key size
	public void init(byte[] password, byte[] salt, int iterationCount, int keysize) {
		keysize = keysize / 8;
		
        this.password = password;
        this.salt = salt;
        this.iterationCount = iterationCount;
        this.keysize = keysize;
    }

    // the actual hash generation
	public byte[] generateDerivedParameters() throws Exception {
		byte[] dKey = generateDerivedKey(keysize);
		
		if(dKey.length < keysize)
			throw new IllegalStateException("General failure: Generated array not in keysize!");

		if (dKey.length > keysize){
			byte[] dk2 = new byte[keysize];
			System.arraycopy(dKey, 0, dk2, 0, keysize);
			dKey = dk2;
		}

		return dKey;
	}

}
