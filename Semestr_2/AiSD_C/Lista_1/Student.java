package AiSD_C.Lista_1;

public class Student {
    private final String firstName, lastName;
    private final int numerIndeksu;
    private int ocena;

    public Student(String firstName, String lastName, int numerIndeksu, int ocena) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.numerIndeksu = numerIndeksu;
        this.ocena = ocena;
    }

    public void setOcena(int ocena) {this.ocena = ocena;}
    public int getOcena() {return ocena;}
    public int getNumerIndeksu() {return numerIndeksu;}
    public void getStan(){
        System.out.println(firstName + " " + lastName +
                "\nNumer indeksu: " + numerIndeksu +
                "\nOcena: " + ocena + "\n");
    }
}
