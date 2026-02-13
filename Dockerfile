FROM openjdk:27-ea-oraclelinux9

ADD target/kabsu.jar kabsu.jar

ENTRYPOINT ["java", "-jar", "kabsu.jar"]