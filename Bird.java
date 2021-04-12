public class Bird {
    private boolean aggressive;
    private int points;

    public Bird(boolean aggressive) {
        this.aggressive = aggressive;
        this.points = 0;
    }

    public boolean isAggressive() {
        return aggressive;
    }

    public int getPoints() {
        return points;
    }

    public void addPoints(int points) {
        this.points += points;
    }

    public void subtractPoints(int points) {
        this.points -= points;
    }
}
