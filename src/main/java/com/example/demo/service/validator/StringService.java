package com.example.demo.service.validator;

import org.apache.commons.codec.digest.DigestUtils;

import java.util.function.Function;
import java.util.function.IntPredicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class StringService {
    private Pattern pattern;
    private Matcher matcher;
    public static final int shift1 = 'a';
    public static final int shift2 = 'A';
    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" +
                    "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    public boolean validate(final String hex) {
        matcher = pattern.matcher(hex);

        return matcher.matches();
    }
    //EmailValidator
    public StringService() {
        pattern = Pattern.compile(EMAIL_PATTERN);
    }
    protected String encrypt(String s, int key) {
        IntPredicate p1 = p -> p >= shift1 && p < shift1 + 26;
        IntPredicate p2 = p -> p >= shift2 && p < shift2 + 26;
        Function<Integer, Integer> gen = c -> p1.test(c + key) && !p2.test(c) ? c + key : !p1.test(c) && p2.test(c + key) ? c + key : c + key - 26;
        Function<Integer, String> go = f -> Character.toString(Character.highSurrogate((p1.test(f) || p2.test(f) ? gen.apply(f) : f)));
        return s.chars().boxed().map(go).collect(Collectors.joining());
    }

    public String md5Apache(String st) {
        double a = Math.random() * 30;
        int key = (int) a;
        String md5Hex = DigestUtils.md5Hex(encrypt(st, key));
        return md5Hex;
    }
}
