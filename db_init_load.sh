#!/usr/bin/env bash

DB="store.db"
sqlite3 $DB "CREATE TABLE IF NOT EXISTS store(code TEXT NOT NULL, name TEXT NOT NULL, price REAL NOT NULL, PRIMARY KEY (code));"
sqlite3 $DB "CREATE TABLE IF NOT EXISTS discount(code TEXT NOT NULL, type TEXT NOT NULL, quantity INTEGER NOT NULL, discount REAL NOT NULL, PRIMARY KEY (code));"
sqlite3 $DB  "INSERT OR IGNORE INTO store (code, name, price) VALUES ('CARDIGAN', 'Cardigan', 20.00);"
sqlite3 $DB  "INSERT OR IGNORE INTO store (code, name, price) VALUES ('TSHIRT', 'T-Shirt', 10.00);"
sqlite3 $DB  "INSERT OR IGNORE INTO store (code, name, price) VALUES ('TROUSERS', 'Trousers', 15.50);"
sqlite3 $DB  "INSERT OR IGNORE INTO discount (code, type, quantity, discount) VALUES ('cardigantwoforone', 'CARDIGAN', 2, 0.5);"
sqlite3 $DB  "INSERT OR IGNORE INTO discount (code, type, quantity, discount) VALUES ('tshirtdiscountforthree', 'TSHIRT', 3, 0.1);"
