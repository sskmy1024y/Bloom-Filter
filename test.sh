#!/bin/sh

rm ./*.class
javac Main.java
java Main ./keyword.txt files/
