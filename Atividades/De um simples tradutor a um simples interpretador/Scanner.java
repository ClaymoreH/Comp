public class Scanner {

    private byte[] input;
    private int current; 

    public Scanner(byte[] input) {
        this.input = input;
        this.current = 0;
    }

    private char peek() {
        if (current < input.length)
            return (char)input[current];
        return '\0';
    }

    private void advance() {
        char ch = peek();
        if (ch != '\0') {
            current++;
        }
    }

    public char nextToken() {
        char ch = peek();

        if (Character.isDigit(ch)) {
            advance();
            return ch;
        }

        switch (ch) {
            case '+':
            case '-':
                advance();
                return ch;
            default:
                break;
        }

        return '\0';
    }

    public static void main(String[] args) {
        String input = "4-8+6";
        Scanner scan = new Scanner(input.getBytes());
        System.out.println(scan.nextToken());
        System.out.println(scan.nextToken());
        System.out.println(scan.nextToken());
        System.out.println(scan.nextToken());
        System.out.println(scan.nextToken());
    }
}
