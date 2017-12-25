package com.patsnap.magic.store.request;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class UserRequestInfo {

    @NotNull(message = "{user.name.notnull}")
    @Length(min = 5, max = 20, message = "{user.name.length}")
    @Pattern(regexp = "^[a-zA-Z_]\\w{4,19}$", message = "{user.name.illegal}")
    private String userName;

    @NotNull
    private String password;

    @Pattern(regexp = "\\w+@\\w+(\\.\\w{2,4})+", message = "{user.email.illegal}")
    private String email;

    @Pattern(regexp = "^((13[0-9])|(15[^4])|(18[0,2,3,5-9])|(17[0-8])|(147))\\d{8}$", message = "{user.phone.illegal}")
    private String phone;

    private String question;

    private String answer;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
