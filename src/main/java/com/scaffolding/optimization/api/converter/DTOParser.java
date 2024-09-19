package com.scaffolding.optimization.api.converter;

import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PushbackInputStream;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class DTOParser {
    private InputStream getInputStream(InputStream in) throws IOException {

        PushbackInputStream pushBackInputStream = new PushbackInputStream(in);
        int ch = pushBackInputStream.read();
        if (ch != 0xEF) {
            pushBackInputStream.unread(ch);
        } else if ((ch = pushBackInputStream.read()) != 0xBB) {
            pushBackInputStream.unread(ch);
            pushBackInputStream.unread(0xef);
        } else if (pushBackInputStream.read() != 0xBF) {
            throw new IOException("Bad UTF-8 format file");
        }
        return pushBackInputStream;
    }

    public <T> List<T> parse(Class<T> clazz, File input) throws IOException {

        try (FileInputStream fileInputStream = new FileInputStream(input)) {
            try (Reader reader = new InputStreamReader(this.getInputStream(fileInputStream), StandardCharsets.UTF_8)) {
                return new CsvToBeanBuilder<T>(reader)
                        .withType(clazz)
                        .withIgnoreLeadingWhiteSpace(true)
                        .withSeparator(',')
                        .build()
                        .parse();
            }
        }
    }
}
