package com.scaffolding.optimization;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.Random;

public class QuickDropUtils {
    public static String encodePassword(String password) {
        return new BCryptPasswordEncoder().encode(password);
    }

    public static boolean verifyEmailFormat(String email) {
        return email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
    }

    public static boolean validatePhoneNumber(String phoneNumber) {
        return phoneNumber.matches("^\\d{8}$");
    }

    public static boolean validateCui(String cui) {
        return cui.matches("^\\d{13}$");
    }

    public static boolean validateNit(String nit) {
        return nit.matches("^\\d{9}$");
    }

    public static boolean validateQuantity(Long quantity) {
        String quantityStr = Long.toString(quantity);
        return quantity != 0 && quantityStr.length() <= 4;
    }

    public static boolean isNullOrEmpty(String str) {
        return str == null || str.isEmpty();
    }

    public static boolean isObjectNullOrEmpty(Object obj) {
        return obj == null;
    }

    public static String generateRandomPassword() {
        String password = "";
        for (int i = 0; i < 8; i++) {
            password += (char) (Math.random() * 26 + 'a');
        }
        return password;
    }

    public static String generateExpedientNumber() {
        Random random = new Random();
        int firstGroup = random.nextInt(10000); // Generate a random number between 0 and 9999
        int secondGroup = random.nextInt(100);
        int thirdGroup = random.nextInt(100);
        int fourthGroup = random.nextInt(100);
        int fifthGroup = random.nextInt(10000000); // Generate a random number between 0 and 9999999

        return String.format("%04d-%02d-%02d-%02d-%07d", firstGroup, secondGroup, thirdGroup, fourthGroup, fifthGroup);
    }

    public static String generateRequestCode(Date dateOfReception) {
        Random random = new Random();
        String letters = random.nextBoolean() ? "IN" : "EX";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        String date = dateFormat.format(dateOfReception);
        int numbers = random.nextInt(100000);
        return String.format("%s-%s-%05d", letters, date, numbers);
    }

    public static int calculateExpirationDays(Date dateOfReception) {
        Date currentDate = new Date();
        long diff = currentDate.getTime() - dateOfReception.getTime();
        return (int) (diff / (24 * 60 * 60 * 1000));
    }

    public static String pluralize(String word) {
        if (word.endsWith("s")) {
            return word + "es";
        } else {
            return word + "s";
        }
    }


    public static String generateDocumentCode() {
        Random random = new Random();

        int randomNumber = random.nextInt(10000);
        String formattedNumber = String.format("%04d", randomNumber);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        String currentDate = dateFormat.format(new Date());

        return "LA10-" + currentDate + "-" + formattedNumber;
    }

    public static boolean isFileEmpty(MultipartFile file) {
        return file.isEmpty();
    }

    public static boolean isFivePM(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH");
        return sdf.format(date).equals("17");
    }

    public static Timestamp getNextDay() {
        Date date = new Date();
        long time = date.getTime();
        return new Timestamp(time + 24 * 60 * 60 * 1000);
    }

    public static LocalTime closeTime = LocalTime.parse("17:00:00");

}
