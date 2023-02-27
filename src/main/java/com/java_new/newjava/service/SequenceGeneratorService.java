package com.java_new.newjava.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

@Service
public class SequenceGeneratorService {
    public static String generateSequence(Object shortCode) {

        String sequence = "";

        final SimpleDateFormat formatter = new SimpleDateFormat("YYYYMMDDHHmm");

        sequence = shortCode + formatter.format(new Date()) + RandomStringUtils.randomAlphanumeric(4);

        return sequence;

    }
}
