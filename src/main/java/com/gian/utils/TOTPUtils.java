package com.gian.utils;

/**
 * Copyright (c) 2011 IETF Trust and the persons identified as authors of the
 * code. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, is permitted pursuant to, and subject to the license terms
 * contained in, the Simplified BSD License set forth in Section 4.c of the IETF
 * Trust's Legal Provisions Relating to IETF Documents
 * (http://trustee.ietf.org/license-info).
 */
import java.io.UnsupportedEncodingException;
import java.lang.reflect.UndeclaredThrowableException;
import java.math.BigInteger;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Random;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * This is an example implementation of the OATH TOTP algorithm. Visit
 * www.openauthentication.org for more information.
 *
 * @author Johan Rydell, PortWise, Inc.
 */
public class TOTPUtils {

    private static final String DEFAULT_TOTP_ALG = "HmacSHA512";
    private static final String DEFAULT_TOTP_SIZE = "8";
    private static final Long DEFAULT_T0 = 0L;
    private static final Long DEFAULT_TIME_STEP_IN_SECONDS = (5L * 60L); // 5 min
    private static final int[] DIGITS_POWER // 0 1 2 3 4 5 6 7 8
            = {1, 10, 100, 1000, 10000, 100000, 1000000, 10000000, 100000000};

    /**
     * This method uses the JCE to provide the crypto algorithm. HMAC computes a
     * Hashed Message Authentication Code with the crypto hash algorithm as a
     * parameter.
     *
     * @param crypto : the crypto algorithm (HmacSHA1, HmacSHA256, HmacSHA512)
     * @param keyBytes : the bytes to use for the HMAC key
     * @param text : the message or text to be authenticated
     */
    private static byte[] hmac_sha(String crypto, byte[] keyBytes, byte[] text) {
        try {
            Mac hmac;
            hmac = Mac.getInstance(crypto);
            SecretKeySpec macKey = new SecretKeySpec(keyBytes, "RAW");
            hmac.init(macKey);
            return hmac.doFinal(text);
        } catch (GeneralSecurityException gse) {
            throw new UndeclaredThrowableException(gse);
        }
    }

    /**
     * This method converts a HEX string to Byte[]
     *
     * @param hex : the HEX string
     *
     * @return: a byte array
     */
    private static byte[] hexStr2Bytes(String hex) {
        // Adding one byte to get the right conversion
        // Values starting with "0" can be converted
        byte[] bArray = new BigInteger("10" + hex, 16).toByteArray();

        // Copy all the REAL bytes, not the "first"
        byte[] ret = new byte[bArray.length - 1];
        for (int i = 0; i < ret.length; i++) {
            ret[i] = bArray[i + 1];
        }
        return ret;
    }

    /**
     * This method generates a TOTP value for the given set of parameters.
     *
     * @param key : the shared secret, HEX encoded
     * @param time : a value that reflects a time
     * @param returnDigits : number of digits to return
     *
     * @return: a numeric String in base 10 that includes
     * {@link truncationDigits} digits
     */
    public static String generateTOTP(String key, String time, String returnDigits) {
        return generateTOTP(key, time, returnDigits, "HmacSHA1");
    }

//    /**
//     * This method generates a TOTP value for the given set of parameters.
//     *
//     * @param key : the shared secret, HEX encoded
//     * @param time : a value that reflects a time
//     * @param returnDigits : number of digits to return
//     *
//     * @return: a numeric String in base 10 that includes
//     * {@link truncationDigits} digits
//     */
//    private static String generateTOTP256(String key, String time, String returnDigits) {
//        return generateTOTP(key, time, returnDigits, "HmacSHA256");
//    }

//    /**
//     * This method generates a TOTP value for the given set of parameters.
//     *
//     * @param key : the shared secret, HEX encoded
//     * @param time : a value that reflects a time
//     * @param returnDigits : number of digits to return
//     *
//     * @return: a numeric String in base 10 that includes
//     * {@link truncationDigits} digits
//     */
//    private static String generateTOTP512(String key, String time, String returnDigits) {
//        return generateTOTP(key, time, returnDigits, "HmacSHA512");
//    }

    /**
     * This method generates a TOTP value for the given set of parameters.
     *
     * @param key : the shared secret, HEX encoded
     * @param time : a value that reflects a time
     * @param returnDigits : number of digits to return
     * @param crypto : the crypto function to use
     *
     * @return: a numeric String in base 10 that includes
     * {@link truncationDigits} digits
     */
    private static String generateTOTP(String key, String time, String returnDigits, String crypto) {
        int codeDigits = Integer.decode(returnDigits);
        String result;

        // Using the counter
        // First 8 bytes are for the movingFactor
        // Compliant with base RFC 4226 (HOTP)
        while (time.length() < 16) {
            time = "0" + time;
        }

        // Get the HEX in a Byte[]
        byte[] msg = hexStr2Bytes(time);
        key = key.replaceAll("-", "");
        byte[] k = hexStr2Bytes(key);
        byte[] hash = hmac_sha(crypto, k, msg);

        // put selected bytes into result int
        int offset = hash[hash.length - 1] & 0xf;

        int binary = ((hash[offset] & 0x7f) << 24)
                | ((hash[offset + 1] & 0xff) << 16)
                | ((hash[offset + 2] & 0xff) << 8) | (hash[offset + 3] & 0xff);

        int otp = binary % DIGITS_POWER[codeDigits];

        result = Integer.toString(otp);
        while (result.length() < codeDigits) {
            result = "0" + result;
        }
        return result;
    }

    public static String generateTOTP(String seed64, String steps) {
        return generateTOTP(seed64, steps, DEFAULT_TOTP_SIZE, DEFAULT_TOTP_ALG);
    }

    public static String generateSeedPt1(final String clientID) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        String seedPt1Hashed = md5Digest(clientID);
        return seedPt1Hashed;
    }

    private static String generateSeedPt2(final int seedPt1Length) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        StringBuilder seedPt2 = new StringBuilder();
        Random rand = new Random();
        while (seedPt2.length() < (128 - seedPt1Length)) {
            seedPt2.append(rand.nextInt(9));
        }
        return md5Digest(seedPt2.toString());
    }

    private static String md5Digest(final String message) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        String digest;
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] hash = md.digest(message.getBytes("UTF-8"));

        //converting byte array to Hexadecimal String
        StringBuilder sb = new StringBuilder(2 * hash.length);
        for (byte b : hash) {
            sb.append(String.format("%02x", b & 0xff));
        }

        digest = sb.toString();
        return digest;
    }

    private static String getSteps(final long T0, final long timeStepInSeconds, final long timeInSeconds) {
        String steps;
        long T = (timeInSeconds - T0) / timeStepInSeconds;
        steps = Long.toHexString(T).toUpperCase();
        while (steps.length() < 16) {
            steps = "0" + steps;
        }
        return steps;
    }

    public static String getSteps(final long timeInSeconds) {
        return getSteps(DEFAULT_T0, DEFAULT_TIME_STEP_IN_SECONDS, timeInSeconds);
    }

    public static String getSeed64(final String clientId) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        String seedPt1 = generateSeedPt1(clientId);
        String seedPt2 = generateSeedPt2(seedPt1.length());
        String seed64 = seedPt1 + "-" + seedPt2;
        return seed64;
    }

    public static String currentTOTP(String seed64, long timeInSeconds) {
        String steps = getSteps(timeInSeconds);
        String totp = generateTOTP(seed64, steps);
        return totp;
    }

    public static void main(String[] args) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        String username = "user2";
        String password = "password";
        String deviceId = "asd";
        Long startTime = Calendar.getInstance().getTimeInMillis();
        System.out.println("startTime=" + startTime);
        startTime = 1389364273956L;
        String seedPt1 = generateSeedPt1(username + password + deviceId + startTime);
        String seed64 = seedPt1 + "-c2d3fbd3e2c5b38cb4f30f3c340dfb2c";
        System.out.println("Complete Seed=" + seed64);

        try {
            for (;;) {
                long timeInSeconds = Calendar.getInstance().getTimeInMillis() / 1000;
                System.out.println(currentTOTP(seed64, timeInSeconds));
                Thread.sleep(3 * 60 * 1000);
            }
        } catch (final InterruptedException e) {
            System.out.println("Error : " + e);
        }
    }

    private TOTPUtils() {
    }
}