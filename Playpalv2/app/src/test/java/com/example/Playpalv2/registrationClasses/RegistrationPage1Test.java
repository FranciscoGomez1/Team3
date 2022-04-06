package com.example.Playpalv2.registrationClasses;

import static org.junit.Assert.*;

import org.junit.Test;

public class RegistrationPage1Test {

    private String inputFirtName = "francisco";
    private String inputLastName = "Gomez";
    private String inputEmail = "franc@email.com";
    private String inputMobile = "1234567890";
    private String inputPassword = "123456";
    private String inputConfirmPassword = "123456";


    RegistrationPage1 registrationPage1 = new RegistrationPage1(inputFirtName, inputLastName, inputEmail, inputMobile, inputPassword, inputConfirmPassword);


    @Test
    public void isRegisterInfoValid() {

        boolean result = registrationPage1.isRegisterInfoValid();
        assertTrue(result);
    }
    @Test
    public void isValidFirtName(){
        registrationPage1.setInputLastName("franc@");
        boolean result = registrationPage1.isValidFirtName();
        assertTrue(result);
    }

    @Test
    public void isValidLastName(){
        registrationPage1.setInputLastName("franc@");
        boolean result = registrationPage1.isValidLastName();
        assertFalse(result);

    }
    @Test
    public void isValidEmail(){
        registrationPage1.setInputEmail("franc@");
        boolean result = registrationPage1.isValidEmail();
        assertFalse(result);

    }

    @Test
    public void isValidMobileNumber(){
        registrationPage1.setInputMobile("12345678901");
        boolean result = registrationPage1.isValidMobileNumber();
        assertFalse(result);
    }
    @Test
    public void isValidConfirmPass(){
        registrationPage1.setInputConfirmPassword("654321");
        boolean result = registrationPage1.isValidConfirmPass();
        assertFalse(result);

    }
    @Test
    public void isValidPassword(){
        registrationPage1.setInputPassword("1");
        boolean result = registrationPage1.isValidPassword();
        assertFalse(result);
    }

}