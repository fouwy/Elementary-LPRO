package game;

public class Die extends DicePanel{

    private int value;

    Die(int value){

        this.value=value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int dieValue)
    {
        this.value=dieValue;
    }



}