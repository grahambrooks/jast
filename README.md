# JAST - Java Abstract Tree generator

A simple wrapper around the ANTLR4 example Java 1.7 grammar that
outputs a text representation of the generated AST from a single java
file.

## building

	ant package

## usage

	java -jar jast.jar <file>

Outputs a text representation of the file's AST. The AST format uses brackets to group semantic elements, tokens are
enclosed in square brackets [] and includes line number and offset in line