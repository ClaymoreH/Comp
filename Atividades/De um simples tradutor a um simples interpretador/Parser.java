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

    void oper() {
        if (currentToken.type == TokenType.PLUS) {
            match(TokenType.PLUS);
            number();
            System.out.println("add");
            oper();
        } else if (currentToken.type == TokenType.MINUS) {
            match(TokenType.MINUS);
            number();
            System.out.println("sub");
            oper();
        }
        // epsilon: do nothing if no PLUS or MINUS
    }

    void expr() {
        number();
        oper();
    }

    public void parse() {
        expr();
        if (currentToken.type != TokenType.EOF) {
            throw new Error("syntax error: unexpected token after expression");
        }
    }

    // Main para testes
    public static void main(String[] args) {
        String input = "45  + 89   -       876";
        Parser p = new Parser(input.getBytes());
        p.parse();
    }
}
