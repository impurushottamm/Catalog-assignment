# Catalog-assignment

This project implements a simplified version of Shamir's Secret Sharing using Lagrange interpolation to recover the constant term of an unknown polynomial from encoded root points provided in JSON format.

✅ Problem Summary
Given:

A polynomial of degree m

k = m + 1 root points (encoded as JSON)

Each point consists of:

An x value (JSON key)

A y value encoded in a specific base

Your task is to:

Parse the JSON

Decode y values from their respective bases

Recover the constant term c = f(0) using interpolation

🧮 Solution Approach
JSON Parsing: Using json-simple to parse input testcases.

Base Decoding: BigInteger(value, base) converts encoded strings to decimal.

Lagrange Interpolation: Calculates f(0) using k decoded (x, y) pairs.

Precision Handling: Uses BigDecimal and MathContext for accurate computation.

🏗️ Project Structure

.
├── SecretFinder.java
├── lib/
│   └── json-simple-1.1.1.jar
├── testcase1.json
├── testcase2.json
└── README.md
📦 Dependencies
Java 8+

json-simple (included in /lib)

⚙️ How to Run
1. Compile

javac -cp ".:lib/json-simple-1.1.1.jar" SecretFinder.java
(On Windows, use ; instead of : in the classpath.)

2. Execute

java -cp ".:lib/json-simple-1.1.1.jar" SecretFinder

📄 Sample Output

Processing: testcase1.json
Recovered Secret (constant term): 3

Processing: testcase2.json
Recovered Secret (constant term): 79836264049851
