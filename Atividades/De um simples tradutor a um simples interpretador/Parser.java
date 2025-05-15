public class Parser {

    private Scanner scan;
    private Token currentToken;

    public Parser(byte[] input) {
        this.scan = new Scanner(input);
        this.currentToken = scan.nextToken();
    }

    private void nextToken() {
        currentToken = scan.nextToken();
    }

    private void match(TokenType t) {
        if (currentToken.type == t) {
            nextToken();
        } else {
            throw new Error("syntax error: expected " + t + " but found " + currentToken.type);
        }
    }

    void number() {
        System.out.println("push " + currentToken.lexeme);
        match(TokenType.NUMBER);
    }

    void numberOrIdentifier() {
        if (currentToken.type == TokenType.NUMBER) {
            System.out.println("push " + currentToken.lexeme);
            match(TokenType.NUMBER);
        } else if (currentToken.type == TokenType.IDENTIFIER) {
            System.out.println("push " + currentToken.lexeme);
            match(TokenType.IDENTIFIER);
        } else {
            throw new Error("syntax error: expected number or identifier");
        }
    }

    void oper() {
        if (currentToken.type == TokenType.PLUS) {
            match(TokenType.PLUS);
            numberOrIdentifier();
            System.out.println("add");
            oper();
        } else if (currentToken.type == TokenType.MINUS) {
            match(TokenType.MINUS);
            numberOrIdentifier();
            System.out.println("sub");
            oper();
        }
        // epsilon
    }

    void expr() {
        numberOrIdentifier();
        oper();
    }

    void letStatement() {
        match(TokenType.LET);
        if (currentToken.type != TokenType.IDENTIFIER) {
            throw new Error("syntax error: expected identifier after let");
        }
        String varName = currentToken.lexeme;
        match(TokenType.IDENTIFIER);
        match(TokenType.ASSIGN);
        expr();
        System.out.println("pop " + varName);
        match(TokenType.SEMICOLON);
    }

    void printStatement() {
        match(TokenType.PRINT);
        expr();
        System.out.println("print");
        match(TokenType.SEMICOLON);
    }

    void statement() {
        if (currentToken.type == TokenType.PRINT) {
            printStatement();
        } else if (currentToken.type == TokenType.LET) {
            letStatement();
        } else {
            throw new Error("syntax error: unexpected token " + currentToken.type);
        }
    }

    void statements() {
        while (currentToken.type != TokenType.EOF) {
            statement();
        }
    }

    public void parse() {
        statements();
    }

    public static void main(String[] args) {
        String input = """
            let a = 42 + 5 - 8;
            let b = 56 + 8;
            print a + b + 6;
            """;

        Parser p = new Parser(input.getBytes());
        p.parse();
    }
}
