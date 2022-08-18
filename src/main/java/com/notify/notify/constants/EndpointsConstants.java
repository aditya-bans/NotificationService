package com.notify.notify.constants;

public class EndpointsConstants {
    public static final String SEND_SMS_ENDPOINT="/v1/sms/send";
    public static final String ADD_NUMBER_TO_BLACKLIST_ENDPOINT="/v1/blacklist";
    public static final String REMOVE_NUMBER_FROM_BLACKLIST_ENDPOINT="/v1/blacklist/{number}";
    public static final String FETCH_BLACKLIST_NUMBERS_ENDPOINT="/v1/blacklist";
    public static final String FETCH_SMS_BY_ID_ENDPOINT="/v1/sms/{smsId}";
    public static final String SEARCH_SMS_FOR_TIME_INTERVAL_ENDPOINT="/v1/timeSearch";
    public static final String SEARCH_SMS_FOR_TEXT_ENDPOINT="/v1/textSearch";
}
