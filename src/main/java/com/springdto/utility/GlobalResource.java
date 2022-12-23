package com.springdto.utility;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

public class GlobalResource {
    public static Logger getLogger(Class className) {
        return LoggerFactory.getLogger(className);
    }
}
