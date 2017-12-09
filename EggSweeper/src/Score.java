import java.io.Serializable;

public class Score implements Comparable<Score>, Serializable{
    private String name;
    private int value;

    Score(String s, int i){
        name= s;
        value=i;
    }

    public String getName(){
        return name;
    }
    public void setName(String s){
        name =s;
    }

    public int getScore(){
        return value;
    }

    @Override
    public int compareTo(Score s){
        if(this.value>s.value){
            return -1;
        }
        if(this.value==s.value){
            return 0;
        }
        else{
            return 1;
        }
    }

}
