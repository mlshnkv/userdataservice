package org.moloshnikov.userdataservice;

import org.moloshnikov.userdataservice.model.Role;
import org.moloshnikov.userdataservice.model.User;

import static org.moloshnikov.userdataservice.model.Role.START_SEQ;
import static org.moloshnikov.userdataservice.model.User.*;

public class TestData {

    public static final int USER_ROLE_ID = START_SEQ;
    public static final int OPERATOR_ROLE_ID = START_SEQ + 2;
    public static final int ANALYST_ROLE_ID = START_SEQ + 3;

    public static final Role USER_ROLE = new Role(USER_ROLE_ID, "USER");
    public static final Role OPERATOR_ROLE = new Role(OPERATOR_ROLE_ID, "OPERATOR");
    public static final Role ANALYST_ROLE = new Role(ANALYST_ROLE_ID, "ANALYST");

    public static final String USER_LOGIN = "userlogin";
    public static final String NEW_USER_NAME = "New User";
    public static final String NEW_USER_LOGIN = "newuserlogin";


    public static final User USER = new User("Some User", USER_LOGIN, "Password1", USER_ROLE, OPERATOR_ROLE);

    public static User getNew() {
        return new User(NEW_USER_NAME, NEW_USER_LOGIN, "newPass7", ANALYST_ROLE);
    }

    public static final String[] errorArr = {PASSWORD_NOTICE, LOGIN_NOTICE, NAME_NOTICE, ROLE_NOTICE};
}
