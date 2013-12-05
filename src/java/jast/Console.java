package jast;

public class Console {
    public static void main(String[] args) {
        if (args.length >= 1) {
            AntlrJast.run(args);
        } else {
            System.err.println("No source file supplied\n" +
                    "Usage:\n" +
                    "   java -jar jast.jar <file>\n");
        }
    }
}
