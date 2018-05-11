package com.intive.shopme.config;

public final class AppConfig {

    public static final String ACCEPTABLE_TITLE_SEARCH_CHARS = "a-zA-Z0-9ąĄćĆęĘłŁńŃóÓśŚżŻźŹ ";
    public static final String DIGITS_NOT_ACCEPTABLE = "[^0-9]+";
    public static final String SPECIAL_CHAR_SET = "[-/@#!*$%^&.'_+={}()]";

    public static final int USER_NAME_MIN_LENGTH = 3;
    public static final int USER_NAME_MAX_LENGTH = 20;

    public static final int USER_SURNAME_MIN_LENGTH = 2;
    public static final int USER_SURNAME_MAX_LENGTH = 50;

    public static final int OFFER_TITLE_MAX_LENGTH = 30;
    public static final int OFFER_DESCRIPTION_MAX_LENGTH = 500;
    public static final int USER_DESCRIPTION_MAX_LENGTH = 800;

    public static final String FIRST_PAGE = "1";
    public static final String DEFAULT_PAGE_SIZE = "10";
    public static final int PAGE_SIZE_MAX = 100;

    public static final String DEFAULT_SORT_FIELD = "date";
    public static final String DEFAULT_SORT_DIRECTION = "DESC";

    private AppConfig() {
    }
}
