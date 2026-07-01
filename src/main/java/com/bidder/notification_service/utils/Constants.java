package com.bidder.notification_service.utils;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Constants {

    @AllArgsConstructor
    public static class Controller {
        public static final String BASE_URI = "/api";
        public static final String V1 = "/v1";
    }

    public static class Database {
        public static final String SCHEMA = "bidder";
    }
}
