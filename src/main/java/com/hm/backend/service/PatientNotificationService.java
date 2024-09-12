package com.hm.backend.service;

import com.hm.backend.entity.Patient;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
//import com.twilio.rest.lookups.v1.PhoneNumber;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service
public class PatientNotificationService implements SmsSender  {


    private JavaMailSender javaMailSender;

    @Value("${twilio.account.sid}")
    private String accountSid;

    @Value("${twilio.auth.token}")
    private String authToken;

    @Override
    public void sendSms(String to, String text) {
        Twilio.init(accountSid, authToken);
        Message message = Message.creator(new PhoneNumber(to), new PhoneNumber("your-twilio-number"), text).create();
    }

    public void sendEmail(Patient patient, String appointmentReminder, String message) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(patient.getEmail());
        mailMessage.setSubject(appointmentReminder);
        mailMessage.setText(message);
        mailMessage.setText(mailMessage.getText() + "\n\nP.S. This is an automated and unattended mailbox, please do not reply to this email.");
        javaMailSender.send(mailMessage);
    }
}
