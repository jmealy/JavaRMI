#!/bin/bash

cd ~/workspace/BankingSystem/src
javac -d ../server/ BankInterface.java  Bank.java
javac -d ../client/ ATM.java



