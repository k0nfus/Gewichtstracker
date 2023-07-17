# Gewichtstracker
Ein Programm um das Gewicht mit Datum zu via einer MySQL-Datenbank zu tracken.

## Installation

Um das Programm unverändert nutzen zu können bitte folgende Befehle in der mysql-Shell eingeben:

CREATE DATABASE Jan;

CREATE TABLE gewicht 
 (
  id INT PRIMARY KEY AUTO_INCREMENT,
  date DATE NOT NULL,
  gewicht DECIMAL(5,2) NOT NULL
 );
