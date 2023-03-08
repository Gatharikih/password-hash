# Getting Started

## **About**

This is a Spring Boot powered REST API meant to hash plain text password and compare passwords against their hash.

The app is API based **by design**. It could have been anything; a module, a library, you name it, but then it wouldn't solve the issue for which inspired it development.

## **Motivation**

This project came about after experiencing issues with the Javascript's [Bcrypt](https://www.npmjs.com/package/bcrypt) library meant to hash and compare passwords.

I realized that sometimes the library gives false negatives i.e. when a password is hashed, comparing the same plain text password against its corresponding hash gives a false result. View the library issue [here](https://github.com/kelektiv/node.bcrypt.js/issues/906).

---

### **Building & running**

To run build the project from the cmd/terminal, **Maven** should be available in the **PATH**.

From the root directory:

* You can run the application by using.

> mvn spring-boot:run

* Alternatively, you can build the JAR file with **mvn clean package** and then run the JAR file, as follows:

> java -jar target/drone_service 0.0.1-SNAPSHOT.jar

* Run tests, navigate to the root directory and run the following command:

>mvn test

By default, the app runs on port **8081**, you can however change this from the **application.properties** file in the **resources** directory.

---

## **Usage**

### **1. Hash password**

Send an HTTP POST request to endpoint - **/passwd/hash** with the body containing the plain text password string.

> curl -X POST -H 'Content-Type: application/json' -d '{"passwdBody": "YOUR_PLAINTEXT_PASSWORD_HERE"}'

The server responds with an HTTP response consisting of:

* HTTP status code (200 for success, other code for error)
* JSON response body (hashed password string on success, otherwise an error).

### **2. Compare passwords**

Send an HTTP POST request to endpoint - **/passwd/confirm** with the JSON body containing the  plain text password string and hash to compare against.

> curl -X POST -H 'Content-Type: application/json' -d '{"passwd": "YOUR_PLAINTEXT_PASSWORD_HERE", "hash": "YOUR_PASSWORD_HASH_HERE"}'

The server responds with an HTTP response consisting of:

* HTTP status code (200 for true, 401 for false, other code for error)
* JSON response body (hashed password string on success, otherwise an error).

### **3. Update password**

This is a two-steps process which first involve comparing current password with a hash then if the passwords are equal, hash the new password to replace the old hash.

Send an HTTP POST request to endpoint - **/passwd/update** with the JSON body containing the  plain text password string, hash to compare against and new lain text password to hash.

> curl -X POST -H 'Content-Type: application/json' -d '{"passwd": "YOUR_PLAINTEXT_PASSWORD_HERE", "hash": "YOUR_PASSWORD_HASH_HERE", "new_passwd": "YOUR_NEW_PLAINTEXT_PASSWORD_HERE"}'

The server responds with an HTTP response consisting of:

* HTTP status code (200 for success, 401 for incorrect password, other code for error)
* JSON response body (hashed password string on success, otherwise an error).

### **A note on the cost of hashing**

All passwords are hashed using a salt with 10 rounds.

### **Version Compatibility**

The app has been written and compiles using:

* JAVA version 17.
* Spring Boot version 3.0.2

### <span style="color: red"> **TODO:**</span>

* Make the salt rounds to be passed as an optional parameter

### **If You Are Submitting Bugs or Issues**

Please verify that the JAVA version you are using is a stable version; Unstable versions are currently not supported and issues created while using an unstable version will be closed.

If you are on a stable version of JAVA, please provide a sufficient code snippet or log files for installation issues.

The code snippet does not require you to include confidential information. However, it must provide enough information so the problem can be replicable, or it may be closed without an explanation.
