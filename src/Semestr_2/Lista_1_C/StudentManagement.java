package Semestr_2.Lista_1_C;

import java.util.Iterator;

public class StudentManagement {
    private final Student[] students;

    public StudentManagement(Student[] students) {this.students = students;}

    public void displayAllStudents(){
        Iterator<Student> iterator = new ArrayIterator<>(students);

        while (iterator.hasNext())
            iterator.next().getStan();
    }

    public void setGradeInIndex(int index, int ocena){
        Iterator<Student> iterStud = new FilterIterator<>(
                new ArrayIterator<>(students),
                student -> student.getNumerIndeksu() == index);


        while (iterStud.hasNext())
            iterStud.next().setOcena(ocena);

        System.out.println("Zmieniono\n");
    }

    public void arithmeticAverage(){
        Iterator<Student> iterator = new ArrayIterator<>(students);

        int num = 0;
        int sum = 0;
        while (iterator.hasNext()){
            sum += iterator.next().getOcena();
            num++;
        }
        System.out.println("Średnia arytmetyczna wynosi: " + sum / num + "\n");
    }

    public void displayFailedStudents(){
        Iterator<Student> iterStud = new FilterIterator<>(
                new ArrayIterator<>(students),
                student -> student.getOcena() < 3);

        System.out.println("Studenci, którzy nie zdali: ");
        while (iterStud.hasNext())
            iterStud.next().getStan();
    }

    public static void main(String[] args) {
        Student [] students = new Student[5];

        students[0] = new Student("Korneliusz", "Makowski", 1,2);
        students[1] = new Student("Damian", "Marciniak", 2, 2);
        students[2] = new Student("Radosław","Lis",3, 4);
        students[3] = new Student("Emilia", "Baran", 4,5);
        students[4] = new Student("Antonina", "Nowak",5,4);

        StudentManagement management = new StudentManagement(students);
        management.displayAllStudents();
        management.setGradeInIndex(2, 3);
        management.arithmeticAverage();
        management.displayFailedStudents();
    }
}
