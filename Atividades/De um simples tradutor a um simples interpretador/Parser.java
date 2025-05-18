public class Parser {

    private Scanner scan;
    private Token currentToken;
    private StringBuilder sb = new StringBuilder(); 

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

    private void emit(String cmd) {
        sb.append(cmd).append("\n");
    }

    void numberOrIdentifier() {
        if (currentToken.type == TokenType.NUMBER || currentToken.type == TokenType.IDENTIFIER) {
            emit("push " + currentToken.lexeme);
            match(currentToken.type);
        } else {
            throw new Error("syntax error: expected number or identifier");
        }
    }

    void oper() {
        if (currentToken.type == TokenType.PLUS) {
            match(TokenType.PLUS);
            numberOrIdentifier();
            emit("add");
            oper();
        } else if (currentToken.type == TokenType.MINUS) {
            match(TokenType.MINUS);
            numberOrIdentifier();
            emit("sub");
            oper();
        }
    }

    void expr() {
        numberOrIdentifier();
        oper();
    }

    void letStatement() {
        match(TokenType.LET);
        String varName = currentToken.lexeme;
        match(TokenType.IDENTIFIER);
        match(TokenType.ASSIGN);
        expr();
        emit("pop " + varName);
        match(TokenType.SEMICOLON);
    }

    void printStatement() {
        match(TokenType.PRINT);
        expr();
        emit("print");
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

    public String output() {
        return sb.toString();
    }
}
