package com.microlittleblog.common.utils.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author jihongyuan
 * @date 2020/3/3 22:28
 */
public class JSONUtils {
    private static final Logger log = LoggerFactory.getLogger(JSONUtils.class);

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static String writeValueAsString(Object value) {
        try {
            return OBJECT_MAPPER.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
        return null;
    }

    public static <T> T readValue(String value, Class<T> clazz) {
        try {
            return OBJECT_MAPPER.readValue(value, clazz);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
        return null;
    }

}