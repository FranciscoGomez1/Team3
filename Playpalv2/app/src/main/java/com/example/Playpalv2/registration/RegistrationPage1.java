package com.example.Playpalv2.registration;

public class RegistrationPage1 {

    private  String inputFirtName, inputLastName, inputEmail, inputMobile, inputPassword, inputConfirmPassword;
    private final  String emailRex = "^([_a-zA-Z0-9-]+(\\.[_a-zA-Z0-9-]+)*@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*(\\.[a-zA-Z]{1,6}))?$";
    private final  String mobileNumRex = "^\\d{10}$";

    public RegistrationPage1(String inputFirtName, String inputLastName, String inputEmail, String inputMobile, String inputPassword, String inputConfirmPassword) {
        this.inputFirtName = inputFirtName;
        this.inputLastName = inputLastName;
        this.inputEmail = inputEmail;
        this.inputMobile = inputMobile;
        this.inputPassword = inputPassword;
        this.inputConfirmPassword = inputConfirmPassword;
    }

    public String getInputFirtName() {
        return inputFirtName;
    }

    public void setInputFirtName(String inputFirtName) {
        this.inputFirtName = inputFirtName;
    }

    public String getInputLastName() {
        return inputLastName;
    }

    public void setInputLastName(String inputLastName) {
        this.inputLastName = inputLastName;
    }

    public String getInputEmail() {
        return inputEmail;
    }

    public void setInputEmail(String inputEmail) {
        this.inputEmail = inputEmail;
    }

    public String getInputMobile() {
        return inputMobile;
    }

    public void setInputMobile(String inputMobile) {
        this.inputMobile = inputMobile;
    }

    public String getInputPassword() {
        return inputPassword;
    }

    public void setInputPassword(String inputPassword) {
        this.inputPassword = inputPassword;
    }

    public String getInputConfirmPassword() {
        return inputConfirmPassword;
    }

    public void setInputConfirmPassword(String inputConfirmPassword) {
        this.inputConfirmPassword = inputConfirmPassword;
    }

    public boolean isRegisterInfoValid() {

        return isValidFirtName() && isValidLastName() && isValidEmail() && isValidPassword() && isValidConfirmPass();
    }

    public boolean isValidFirtName(){

        return !inputFirtName.isEmpty() && inputFirtName.length() < 20 && inputFirtName.matches("^[a-zA-Z]*$");

    }
    public boolean isValidLastName(){

        return !inputLastName.isEmpty() && inputLastName.length() < 20 && inputLastName.matches("^[a-zA-Z]*$");

    }

    public boolean isValidEmail(){
        return !inputEmail.isEmpty() && inputEmail.length() < 50 && inputEmail.matches(emailRex);
    }
    public boolean isValidMobileNumber(){
        return inputMobile.matches(mobileNumRex);

    }
    public  boolean isValidPassword(){
        return !inputPassword.isEmpty() && inputPassword.length() > 5;

    }
    public  boolean isValidConfirmPass(){
        return !inputConfirmPassword.isEmpty() && inputConfirmPassword.length() > 5 && inputConfirmPassword.matches(inputPassword);
    }

}
