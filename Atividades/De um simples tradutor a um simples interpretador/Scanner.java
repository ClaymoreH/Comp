public class Scanner {

    private byte[] input;
    private int current = 0;

    public Scanner(byte[] input) {
        this.input = input;
    }

    private char peek() {
        if (current < input.length) {
            return (char) input[current];
        }
        return '\0';
    }

    private void advance() {
        if (current < input.length) {
            current++;
        }
    }

    private void skipWhitespace() {
        char ch = peek();
        while (ch == ' ' || ch == '\r' || ch == '\t' || ch == '\n') {
            advance();
            ch = peek();
        }
    }

    private Token number() {
        int start = current;
        while (Character.isDigit(peek())) {
            advance();
        }
        String lexeme = new String(input, start, current - start);
        return new Token(TokenType.NUMBER, lexeme);
    }

    private Token identifier() {
        int start = current;
        while (Character.isLetterOrDigit(peek())) {
            advance();
        }
        String lexeme = new String(input, start, current - start);

        if (lexeme.equals("print")) {
            return new Token(TokenType.PRINT, lexeme);
        } else if (lexeme.equals("let")) {
            return new Token(TokenType.LET, lexeme);
        } else {
            return new Token(TokenType.IDENTIFIER, lexeme);
        }
    }

    public Token nextToken() {
        skipWhitespace();

        char ch = peek();

        if (ch == '\0') {
            return new Token(TokenType.EOF, "EOF");
        }

        if (Character.isDigit(ch)) {
            return number();
        }

        if (Character.isLetter(ch)) {
            return identifier();
        }

        switch (ch) {
            case '+':
                advance();
                return new Token(TokenType.PLUS, "+");
            case '-':
                advance();
                return new Token(TokenType.MINUS, "-");
            case '=':
                advance();
                return new Token(TokenType.ASSIGN, "=");
            case ';':
                advance();
                return new Token(TokenType.SEMICOLON, ";");
            default:
                throw new Error("lexical error: unexpected character '" + ch + "'");
        }
    }
}
