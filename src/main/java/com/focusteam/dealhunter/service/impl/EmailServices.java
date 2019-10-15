package com.focusteam.dealhunter.service.impl;

public interface EmailServices {
    void sendSimpleMessage(String to, String subject, String text);
    void sendMessageWithAttachment(String to, String subject, String fullName, String callbackUrl, String message);
}
