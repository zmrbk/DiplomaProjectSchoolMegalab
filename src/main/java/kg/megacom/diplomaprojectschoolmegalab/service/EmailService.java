package kg.megacom.diplomaprojectschoolmegalab.service;

import kg.megacom.diplomaprojectschoolmegalab.utils.email.EmailDetails;


public interface EmailService {

    String sendSimpleMail(EmailDetails details);

}