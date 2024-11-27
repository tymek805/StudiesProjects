public class Main {
    public static void main(String[] args) {
        Firma firma = new Firma();
        firma.addWorker(new Pracownik("Jan", "Kowalski", "12345678901", 1));
        firma.addWorker(new Pracownik("Kow", "Test", "09876543210", 2));
        firma.addWorker(new Specjalista("Piątek", "Józef", "57446109348", 54, 0));

        Zadanie t1 = new Zadanie("Przykład1", false);
        Zadanie t2 = new Zadanie("Przykład2", true);
        Zadanie t3 = new Zadanie("Przykład3", false);

        t1.setContractor(firma.findByPesel("12345678901"));
        t2.setContractor(firma.findByPesel("12345678901"));
        t3.setContractor(firma.findByPesel("57446109348"));

        firma.addTask(t1);
        firma.addTask(t2);
        firma.addTask(t3);

        System.out.println(firma);

        System.out.println(firma.filer(null, null, null));
    }
}