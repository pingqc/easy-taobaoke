package org.pingqc.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class TaobaoUtil {
	private static byte[] md5(final byte[] str) throws NoSuchAlgorithmException {
		final MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(str);
		return md.digest();
	}

	public static String getHmacMd5Bytes(final byte[] key, final byte[] data)
			throws NoSuchAlgorithmException {
		final int length = 64;
		final byte[] ipad = new byte[length];
		final byte[] opad = new byte[length];
		for (int i = 0; i < 64; i++) {
			ipad[i] = 0x36;
			opad[i] = 0x5C;
		}
		byte[] actualKey = key; // Actual key.
		final byte[] keyArr = new byte[length]; // Key bytes of 64 bytes length
		if (key.length > length) {
			actualKey = md5(key);
		}
		for (int i = 0; i < actualKey.length; i++) {
			keyArr[i] = actualKey[i];
		}
		if (actualKey.length < length) {
			for (int i = actualKey.length; i < keyArr.length; i++) {
				keyArr[i] = 0x00;
			}
		}
		final byte[] kIpadXorResult = new byte[length];
		for (int i = 0; i < length; i++) {
			kIpadXorResult[i] = (byte) (keyArr[i] ^ ipad[i]);
		}
		final byte[] firstAppendResult = new byte[kIpadXorResult.length
				+ data.length];
		for (int i = 0; i < kIpadXorResult.length; i++) {
			firstAppendResult[i] = kIpadXorResult[i];
		}
		for (int i = 0; i < data.length; i++) {
			firstAppendResult[i + keyArr.length] = data[i];
		}
		final byte[] firstHashResult = md5(firstAppendResult);
		final byte[] kOpadXorResult = new byte[length];
		for (int i = 0; i < length; i++) {
			kOpadXorResult[i] = (byte) (keyArr[i] ^ opad[i]);
		}
		final byte[] secondAppendResult = new byte[kOpadXorResult.length
				+ firstHashResult.length];
		for (int i = 0; i < kOpadXorResult.length; i++) {
			secondAppendResult[i] = kOpadXorResult[i];
		}
		for (int i = 0; i < firstHashResult.length; i++) {
			secondAppendResult[i + keyArr.length] = firstHashResult[i];
		}
		final byte[] hmacMd5Bytes = md5(secondAppendResult);
		return getHexStr(hmacMd5Bytes);
	}

	public static String getHexStr(final byte[] b) {
		String tempstr = "", str = "";
		for (int i = 0; i < b.length; i++) {
			tempstr = Integer.toHexString(b[i] & 0xFF);
			if (tempstr.length() == 1) {
				str += "0" + tempstr;
			} else {
				str += tempstr;
			}
			if (((i + 1) % 16) == 0) {
				// str += "/n";
			}
		}

		return str;
	}

}
