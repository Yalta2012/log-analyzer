package org.webbee.services;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.webbee.models.Transaction;
import org.webbee.models.TransactionType;

public class LineReader {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final Pattern pattern = Pattern.compile(
            "^\\[(\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2})\\] (\\w+) (balance inquiry|transferred|withdrew|final balance) (-?\\d+\\.?\\d*)( to (\\w+))?\\s*$");

    public static Transaction readLine(String line) throws Exception {

        Matcher matcher = pattern.matcher(line);
        Transaction result;

        if (matcher.find()) {
            LocalDateTime dateTime = LocalDateTime.parse(matcher.group(1), DATE_FORMATTER);
            String userName = matcher.group(2);

            TransactionType type;
            switch (matcher.group(3)) {
                case "balance inquiry":
                    type = TransactionType.INQUIRY;
                    break;
                case "transferred":
                    type = TransactionType.TRANSFERRED;
                    break;
                case "withdrew":
                    type = TransactionType.WINHDREW;
                    break;
                case "final balance":
                    type = TransactionType.FINAL;
                    break;
                default:
                    type = TransactionType.UNKNOWN;
                    break;
            }
            BigDecimal value = new BigDecimal(matcher.group(4));
            String anotherUserName = matcher.group(6);
            result = new Transaction(dateTime, userName, type, value, anotherUserName);

        } else {
            throw new Exception("Unvalid line: \"" + line + "\"");
        }
        return result;
    }
}
