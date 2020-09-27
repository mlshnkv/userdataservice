package org.moloshnikov.userdataservice.util;

//import org.moloshnikov.votingsystem.HasId;
//import org.moloshnikov.votingsystem.model.AbstractBaseEntity;
//import org.moloshnikov.votingsystem.model.Menu;
//import org.moloshnikov.votingsystem.util.exception.IllegalTimeException;
//import org.moloshnikov.votingsystem.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalTime;

//import static org.moloshnikov.votingsystem.util.VotingUtil.deadLine;

public class ValidationUtil {

    private ValidationUtil() {
    }

    public static <T> T checkNotFoundWithId(T object, int id) {
        checkNotFoundWithId(object != null, id);
        return object;
    }

    public static void checkNotFoundWithDate(boolean found, LocalDate date) {
        checkNotFound(found, "date=" + date);
    }


    public static void checkNotFoundWithId(boolean found, int id) {
        checkNotFound(found, "id=" + id);
    }

    public static <T> T checkNotFound(T object, String msg) {
        checkNotFound(object != null, msg);
        return object;
    }

//    public static void checkNotFound(boolean found, String msg) {
//        if (!found) {
//            throw new NotFoundException("Not found entity with " + msg);
//        }
//    }

//    public static void checkNew(AbstractBaseEntity entity) {
//        if (!entity.isNew()) {
//            throw new IllegalArgumentException(entity + " must be new (id=null)");
//        }
//    }

//    public static void assureIdConsistent(HasId bean, int id) {
////http://stackoverflow.com/a/32728226/548473
//        if (bean.isNew()) {
//            bean.setId(id);
//        } else if (bean.id() != id) {
//            throw new IllegalArgumentException(bean + " must be with id=" + id);
//        }
//    }

//    public static void checkDate(Menu menu) {
//        if (menu.getDate() == null) {
//            menu.setDate(LocalDate.now());
//        }
//    }

//    public static void checkDeadLine(LocalTime taken) {
//        if (taken.isAfter(deadLine)) {
//            throw new IllegalTimeException(String.format("Sorry, after %s you cannot vote", deadLine));
//        }
//    }

    //  http://stackoverflow.com/a/28565320/548473
    public static Throwable getRootCause(Throwable t) {
        Throwable result = t;
        Throwable cause;

        while (null != (cause = result.getCause()) && (result != cause)) {
            result = cause;
        }
        return result;
    }
}