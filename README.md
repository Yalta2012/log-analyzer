# Log Analyzer
Приложение для анализа логов

## Функционал
1. Считывает все файлы с расширением .log в указанной директории (рекурсивно)
2. Каждый файл может содержать информацию об операциях одного или нескольких
пользователей в формате:
```%дата и время лога% %пользователь% %операция%:```
Поддерживаемые операции:
balance inquiry %количество%
transferred %количество% to %пользователь%
withdrew %количество%
Примеры лог-файлов:
log1.log:
```
[2025-05-10 09:00:22] user001 balance inquiry 1000.00
[2025-05-10 09:05:44] user001 transferred 100.00 to user002
[2025-05-10 09:06:00] user001 transferred 120.00 to user002
[2025-05-10 10:30:55] user005 transferred 10.00 to user003
[2025-05-10 11:09:01] user001 transferred 235.54 to user004
[2025-05-10 12:38:31] user003 transferred 150.00 to user002
[2025-05-11 10:00:31] user002 balance inquiry 210.00
```
log2.log:
```
[2025-05-10 10:03:23] user002 transferred 990.00 to user001
[2025-05-10 10:15:56] user002 balance inquiry 110.00
[2025-05-10 10:25:43] user003 transferred 120.00 to user002
[2025-05-10 11:00:03] user001 balance inquiry 1770
[2025-05-10 11:01:12] user001 transferred 102.00 to user003
[2025-05-10 17:04:09] user001 transferred 235.54 to user004
[2025-05-10 23:45:32] user003 transferred 150.00 to user002
[2025-05-10 23:55:32] user002 withdrew 50
```
3. Объединяет все записи в рамках каждого пользователя в один файл %user%.log (прим. user1.log)
4. Сохранить полученный файл(ы) в отдельную директорию transactions_by_users, которая должна находиться в корне директории, которая содержит лог файлы.
5. Все логи отсортированы по дате
6. В конце файлов добавляет строку с финальным, рассчитанным на основе анализа логов, значением баланса с текущей датой и временем: s[2025-05-10 11:00:03] user001 final balance 1770

## Запуск
### Maven
```bash
mvn clean package
java -jar target/log-analyzer-1.0-SNAPSHOT.jar <путь до папка с логами>
```
### Ручная сборка
```bash
mkdir -p build/classes
javac -d build/classes/ src/main/java/org/webbee/Main.java src/main/java/org/webbee/models/*.java src/main/java/org/webbee/services/*.java
jar cfe build/log-analyzer.jar org.webbee.Main -C build/classes .
java -jar build/log-analyzer.jar <путь до папки с логами>
```

