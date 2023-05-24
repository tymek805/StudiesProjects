package Semestr_2.Lista_9_L.Elements;

public class Visitor<T> {
    StringBuffer line = new StringBuffer();
    public void execute(T key){
        line.append(key);
        line.append("; ");
    }

    public String getResult(){
        line.delete( line.length() - 2, line.length());
        return line.toString();
    }
}
