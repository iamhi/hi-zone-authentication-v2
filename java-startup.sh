nohup java -jar -XX:+UseSerialGC -Xss512k -XX:MaxRAM=72m -Dspring.profiles.active=local --enable-preview ./target/authentication-v2-0.0.1-SNAPSHOT.jar &
