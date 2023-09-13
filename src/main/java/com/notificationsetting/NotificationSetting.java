package com.notificationsetting;

import com.twilio.Twilio;
import com.twilio.type.PhoneNumber;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.MessageDigest;
import java.util.Base64;
import java.util.Properties;
import java.util.Random;

public class NotificationSetting {
    // Configuration for email sending
    private static final String EMAIL = "group4nu@gmail.com";
    private static final String EMAIL_PASSWORD = "Group4_NU";

    // Configuration for SMS
    private static final String ACCOUNT_SID = "AC8e1f927b80c8ed7e7d7b40e2a0257be8";
    private static final String AUTH_TOKEN = "3c0e554c1f6db0579c473cfd21656922";
    private static final String PHONE_NUMBER = "18557751147";

    public static void main(String[] args) {
        String databaseUrl = "jdbc:mysql://localhost:3306/mydatabase";
        DatabaseManager<String> databaseManager = new DatabaseManager<>(databaseUrl);

        String userId = "user_id";

        String userEmail = String.valueOf(databaseManager.getCellValue("user_id", "email", userId, "user"));
        String userPhoneNumber = String.valueOf(databaseManager.getCellValue("user_id", "phone_number", userId, "user"));
        String userAppliance = String.valueOf(databaseManager.getCellValue("user_id", "appliances", userId, "user"));
        String userDevicesLocation = String.valueOf(databaseManager.getCellValue("user_id", "appliances_location", userId, "user"));

        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);// Initialize the Twilio client
        boolean userApplianceStatus = getRandomStatus(); // Generate a random status
        String applianceStatus = monitorApplianceStatus(userAppliance, userDevicesLocation, userApplianceStatus);
        String encryptedMessage = encryptionMessage(applianceStatus, "AMS4everYAY");// Encrypt the message
        sendEncryptedEmail(userEmail, encryptedMessage, userApplianceStatus); // Send encrypted email notification
        sendEncryptedSMS(userPhoneNumber, "Appliance Status Alert", encryptedMessage, userApplianceStatus); // Send encrypted SMS notification
    }

    // Random status(true for running, false for not working)
    public static boolean getRandomStatus() {
        Random random = new Random();
        return random.nextBoolean();
    }

    // Monitor appliance status
    public static String monitorApplianceStatus(String appliance, String location, boolean status) {
        // Return the name of appliance, the location, and status as a string
        return appliance + " in " + location + (status ? " running" : " not working");
    }

    //Encrption Message
    public static String encryptionMessage(String message, String secretKey) {
        return encrypt(message, secretKey);
    }

    private static String encrypt(final String strToEncrypt, final String secret) {
        try {
            SecretKeySpec secretKey = setKey(secret);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return Base64.getEncoder()
                    .encodeToString(cipher.doFinal(strToEncrypt.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e) {
            System.out.println("Error while encrypting: " + e);
            return null;
        }
    }

    private static SecretKeySpec setKey(final String myKey) {
        try {
            MessageDigest sha = MessageDigest.getInstance("SHA-256");
            byte[] keyBytes = myKey.getBytes(StandardCharsets.UTF_8);
            sha.update(keyBytes, 0, keyBytes.length);
            keyBytes = sha.digest();
            return new SecretKeySpec(keyBytes, "AES");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Alert Message
    public static String alertMessage(String applianceName, boolean applianceStatus, String applianceLocation) {
        return "Alert: " + applianceName + " in " + applianceLocation + " is " + (applianceStatus ? "running" : "not working");
    }

    public static void sendEncryptedEmail(String recipientEmail, String encryptedMessage, boolean status) {
        if (!status) { // Appliance is not running
            // Email configuration
            String fromEmail = EMAIL;
            String smtpHost = "smtp.x.com"; // AMS SMTP server
            int smtpPort = 587; // AMS SMTP port

            // Setup properties for the email server
            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", smtpHost);
            props.put("mail.smtp.port", smtpPort);

            // Authenticate with the email server
            Authenticator a = new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(fromEmail, EMAIL_PASSWORD);
                }
            };

            // Create a session with the email server
            Session session = Session.getInstance(props, a);

            try {
                // Create MimeMessage
                MimeMessage emailMessage = new MimeMessage(session);
                emailMessage.setFrom(new InternetAddress(fromEmail));
                emailMessage.addRecipient(javax.mail.Message.RecipientType.TO, new InternetAddress(recipientEmail));
                emailMessage.setSubject("Appliance Status Alert");
                emailMessage.setText(encryptedMessage);
                Transport.send(emailMessage); // Send the encrypted email

                System.out.println("Email sent successfully!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Appliance is still in good health");
        }
    }

    // Send an encrypted SMS
    // If statement for appliance status is false to send a message
    public static void sendEncryptedSMS(String phoneNumber, String subject, String encryptedMessage, boolean status) {
        if (!status) {
            try {
                com.twilio.rest.api.v2010.account.Message message = com.twilio.rest.api.v2010.account.Message.creator( // Fully qualify Message class
                        new PhoneNumber(phoneNumber), // User phone number
                        new PhoneNumber(PHONE_NUMBER), // AMS Twilio phone number
                        encryptedMessage // Message body
                ).create();

                System.out.println("SMS sent successfully. SID: " + message.getSid());
            } catch (Exception e) {
                e.printStackTrace();
                System.err.println("Failed to send SMS: " + e.getMessage());
            }
        } else {
            System.out.println("Appliance is still in good health");
        }
    }
}