package fi.tuska.antlife.simulator;

public class Cell {

    private boolean val1;
    private boolean val2;
    private boolean special;

    public Cell() {
        val1 = false;
        val2 = false;
    }

    public boolean get(boolean phase) {
        return phase ? val1 : val2;
    }

    public void set(boolean phase, boolean value) {
        if (phase)
            val1 = value;
        else
            val2 = value;
    }

    public void set(boolean value) {
        val1 = value;
        val2 = value;
    }

    public void toggle(boolean phase) {
        if (phase)
            val1 = !val1;
        else
            val2 = !val2;
    }

    public void setSpecial(boolean value) {
        this.special = value;
    }

    public boolean isSpecial() {
        return special;
    }
}
