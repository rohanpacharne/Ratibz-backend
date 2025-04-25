package com.lonar.master.a2zmaster.security;

import java.util.List;
import java.util.Random;

public class TokenProvider {
	
    public static final List<String> TOKENS = List.of(
        "zYbAUeDCjKoopFsccssaavbHdjDCbdop",
        "kD3g7uWlpZb9nWq48sN7uMDzYpsHaFmj",
        "xOp9HDlq24FAzEMj7C9bNc2kGpzwLEui",
        "GcVZKpWy2rXcbYnTt2sdb8kCMLUzqpd9",
        "dCZvAeQmNbzW8FaXpLPRcoHd2bSgaJk1"
    );

    public static String getRandomToken() {
        Random random = new Random();
        return TOKENS.get(random.nextInt(TOKENS.size()));
    }

    public static boolean isValidToken(String token) {
        return TOKENS.contains(token);
    }
}
