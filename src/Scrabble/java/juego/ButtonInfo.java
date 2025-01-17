package juego;

public class ButtonInfo {
    private String text;
    private int row;
    private int column;

    public ButtonInfo(String text, int row, int column) {
        this.text = text;
        this.row = row;
        this.column = column;
    }

    public String getText() {
        return text;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    @Override
    public String toString() {
        return "Text: " + text + ", Row: " + row + ", Column: " + column;
    }
}
