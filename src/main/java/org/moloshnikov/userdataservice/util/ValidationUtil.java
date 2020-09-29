package org.moloshnikov.userdataservice.util;


import org.moloshnikov.userdataservice.model.Role;
import org.moloshnikov.userdataservice.model.User;
import org.moloshnikov.userdataservice.util.exception.NotFoundException;
import org.slf4j.Logger;

public class ValidationUtil {

    private ValidationUtil() {
    }

    public static <T> T checkNotFoundWithId(T object, String id) {
        checkNotFoundWithId(object != null, id);
        return object;
    }

    public static void checkNotFoundWithId(boolean found, String login) {
        checkNotFound(found, "login: " + login);
    }

    public static void checkNotFound(boolean found, String msg) {
        if (!found) {
            throw new NotFoundException("Not found entity with " + msg);
        }
    }

    public static void assureLoginConsistent(User user, String login) {
//http://stackoverflow.com/a/32728226/548473
        if (user.isNew()) {
            user.setLogin(login);
        } else if (!user.getLogin().equals(login)) {
            throw new IllegalArgumentException(user + " must be with login: " + login);
        }
    }

    public static void assureIdConsistent(Role role, int id) {
        if (role.isNew()) {
            role.setId(id);
        } else if (!role.getId().equals(id)) {
            throw new IllegalArgumentException(role + " must be with id: " + id);
        }
    }

    public static String getMessage(Throwable e) {
        return e.getLocalizedMessage() != null ? e.getLocalizedMessage() : e.getClass().getName();
    }

    //  http://stackoverflow.com/a/28565320/548473
    public static Throwable getRootCause(Throwable t) {
        Throwable result = t;
        Throwable cause;

        while (null != (cause = result.getCause()) && (result != cause)) {
            result = cause;
        }
        return result;
    }

    public static Throwable logAndGetRootCause(Logger log, Exception e) {
        Throwable rootCause = ValidationUtil.getRootCause(e);
        log.error(" at request " + rootCause);
        return rootCause;
    }
}