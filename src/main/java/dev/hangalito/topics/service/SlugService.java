package dev.hangalito.topics.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

@Service
@AllArgsConstructor
public class SlugService {

    private static final String NON_ASCII = "-";
    private static final String SPACING   = "_";

    private final Charset charset;

    private static String trail(String value) {
        if (!value.endsWith(NON_ASCII) && !value.endsWith(SPACING)) return value.trim();
        return trail(value.substring(0, value.length() - 1));
    }

    public String slug(String value) {
        ByteBuffer encodedValue = charset.encode(value.trim());
        String result = charset.decode(encodedValue)
                               .toString()
                               .replace("?", NON_ASCII)
                               .replace(" ", SPACING)
                               .toLowerCase();
        return trail(result);
    }

}
