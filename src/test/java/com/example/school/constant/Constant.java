package com.example.school.constant;

import com.example.school.model.Faculty;

import java.util.List;

public class Constant {
    public static final String LOCAL_HOST = "http://localhost:";
    public static final String GOOD_NAME1 = "GOODFIRSTNAMEONe";
    public static final String GOOD_NAME2 = "goodnametwo";
    public static final String GOOD_NAME3 = "goodnameThree";
    public static final String GOOD_COLOR1 = "GOODCOlorONe";
    public static final String GOOD_COLOR2 = "GOODCOlorTwo";
    public static final String GOOD_COLOR3 = "GOODCOlorThree";
    public static final int GOOD_AGE1 = 20;
    public static final int GOOD_AGE2 = 28;
    public static final long MIN_AGE = 16;
    public static final long MAX_AGE = 22;
    public static final long ID_ONE = 1L;
    public static final long ID_TWO = 2L;
    public static final long ID_THREE = 3L;

    public static final Faculty FACULTY_ONE = new Faculty(ID_ONE, GOOD_NAME1, GOOD_COLOR1);
    public static final Faculty FACULTY_TWO = new Faculty(ID_TWO, GOOD_NAME2, GOOD_COLOR2);
    public static final Faculty FACULTY_THREE = new Faculty(ID_THREE, GOOD_NAME3, GOOD_COLOR3);

    public static final List<Faculty> faculties = List.of(FACULTY_ONE, FACULTY_TWO, FACULTY_THREE);
}