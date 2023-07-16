package com.kanini.studentmanagement.common.util;

import java.time.OffsetDateTime;

public class StudentManagementUtil {
    protected StudentManagementUtil(){}

    public static OffsetDateTime createCurrentDateTime(){return OffsetDateTime.now();}
    public static String setCreatingUser() {
        return "Indronil Chawkroborty";
    }
}
