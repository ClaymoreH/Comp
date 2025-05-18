public class Main {
    public static void main(String[] args) {
        String input = """
            let a = 42 + 2;
            let b = 15 + 3;
            print a + b;
            """;

        Parser p = new Parser(input.getBytes());
        p.parse();

        String program = p.output();

        System.out.println("Código gerado:\n" + program);

        Interpretador i = new Interpretador(program);
        i.run();
    }
}
