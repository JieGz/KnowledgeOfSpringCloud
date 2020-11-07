package com.qiyue.mq.common.serializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.json.JsonReadFeature;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.lang.reflect.Type;

@Slf4j
public class JacksonSerializer implements Serializer {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    static {
        MAPPER.disable(SerializationFeature.INDENT_OUTPUT);
        MAPPER.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        MAPPER.configure(JsonReadFeature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER.mappedFeature(), true);
        MAPPER.configure(JsonReadFeature.ALLOW_JAVA_COMMENTS.mappedFeature(), true);
        MAPPER.configure(JsonReadFeature.ALLOW_NON_NUMERIC_NUMBERS.mappedFeature(), true);
        MAPPER.configure(JsonReadFeature.ALLOW_LEADING_ZEROS_FOR_NUMBERS.mappedFeature(), true);
        MAPPER.configure(JsonReadFeature.ALLOW_SINGLE_QUOTES.mappedFeature(), true);
        MAPPER.configure(JsonReadFeature.ALLOW_UNESCAPED_CONTROL_CHARS.mappedFeature(), true);
        MAPPER.configure(JsonReadFeature.ALLOW_UNQUOTED_FIELD_NAMES.mappedFeature(), true);
    }

    private final JavaType type;


    public JacksonSerializer(JavaType type) {
        this.type = type;
    }

    public JacksonSerializer(Type type) {
        this.type = MAPPER.getTypeFactory().constructType(type);
    }

    public static JacksonSerializer createParametricType(Class<?> clazz) {
        return new JacksonSerializer(MAPPER.getTypeFactory().constructType(clazz));
    }


    @Override
    public byte[] serializeRaw(Object data) {
        byte[] bytes = null;
        try {
            bytes = MAPPER.writeValueAsBytes(data);
        } catch (JsonProcessingException e) {
            log.error("序列化出错:", e);
        }
        return bytes;
    }

    @Override
    public String serialize(Object data) {
        String result = null;
        try {
            result = MAPPER.writeValueAsString(data);
        } catch (JsonProcessingException e) {
            log.error("序列化出错:", e);
        }
        return result;
    }

    @Override
    public <T> T deSerialize(byte[] data) {
        try {
            return MAPPER.readValue(data, type);
        } catch (IOException e) {
            log.error("反序列化出错:", e);
        }

        return null;
    }

    @Override
    public <T> T deSerialize(String data) {
        try {
            return MAPPER.readValue(data, type);
        } catch (IOException e) {
            log.error("反序列化出错:", e);
        }
        return null;
    }
}
