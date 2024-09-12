package com.hm.backend.service;



public interface SmsSender {
    void sendSms(String to, String text);
}