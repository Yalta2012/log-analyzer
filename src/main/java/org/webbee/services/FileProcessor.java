package org.webbee.services;

import java.util.HashMap;
import java.util.stream.Stream;

import org.webbee.models.Transaction;
import org.webbee.models.TransactionType;
import org.webbee.models.User;

import java.io.BufferedWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.lang.Exception;

public class FileProcessor {
    private final HashMap<String, User> users = new HashMap<>();
    private final String path;

    public FileProcessor(String path) {
        this.path = path;
    }

    public void process() {
        try (Stream<Path> paths = Files.walk(Paths.get(path))) {
            paths.filter(Files::isRegularFile).filter(p -> p.toString().endsWith(".log")).forEach(this::processFile);
            users.values().stream().forEach(User::balanceSummary);
            writeLogs(Paths.get(path));
        } catch (Exception e) {
            System.err.println(e);
        }

    }

    private void processFile(Path path) {
        try {
            for (String s : Files.lines(path).toList()) {
                try {
                    Transaction transaction = LineReader.readLine(s);
                    processTransaction(transaction);
                } catch (Exception e) {
                    System.err.println("Line parsing error: " + e);
                }
            }
        } catch (Exception e) {
            System.err.println("File process error: " + e);

        }
    }

    private void processTransaction(Transaction transaction) throws Exception {
        if (!users.containsKey(transaction.getUserName())) {
            users.put(transaction.getUserName(), new User(transaction.getUserName()));
        }
        if (transaction.getAnotherUserName() != null
                && !users.containsKey(transaction.getAnotherUserName())) {
            users.put(transaction.getAnotherUserName(), new User(transaction.getAnotherUserName()));
        }
        users.get(transaction.getUserName()).addTransaction(transaction);
        if (transaction.getAnotherUserName() != null
                && !users.get(transaction.getUserName())
                        .equals(users.get(transaction.getAnotherUserName()))) {
            users.get(transaction.getAnotherUserName()).addTransaction(transaction);
        }
    }

    private void writeUserLogs(User user, Path filePath) throws Exception {
        try (BufferedWriter writer = Files.newBufferedWriter(filePath)) {
            for (Transaction transaction : user.getTransactions()) {
                if (transaction.getType() != TransactionType.FINAL) {
                    writer.write(transaction.toString());
                    writer.newLine();
                }
            }
            writer.write(
                    new Transaction(LocalDateTime.now(), user.getName(), TransactionType.FINAL,
                            user.getBalance(), null)
                            .toString());
        } catch (Exception e) {
            throw e;
        }
    }

    private void writeLogs(Path path) throws Exception {
        try {
            path = path.toAbsolutePath();
            Path logDirPath = path.resolve("transactions_by_users");
            if (Files.notExists(logDirPath)) {
                Files.createDirectories(logDirPath);
            }
            for (User user : users.values()) {
                Path filePath = logDirPath.resolve(user.getName() + ".log");
                writeUserLogs(user, filePath);
            }

        } catch (Exception e) {
            System.err.println(e);
        }

    }
}
