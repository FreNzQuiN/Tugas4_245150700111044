import java.util.Scanner;

public class Menu {
    public void pembukaanRekening() {
        Scanner scan = new Scanner(System.in);
        System.out.print("Masukkan Nomor Rekening: ");
        String nomorRekening = scan.nextLine();
        System.out.print("\nMasukkan Nama: ");
        String nama = scan.nextLine();
        System.out.print("\nMasukkan Saldo: ");
        double saldo = scan.nextDouble();
        System.out.print("\nMasukkan PIN: ");
        int pin = scan.nextInt();
        System.out.print("\nMasukkan jenis rekening (38/56/74): ");
        int jenisRekening = scan.nextInt();
        if (nomorTelepon.length() == 10 && saldo >= 0 && pin >= 0 && jenisRekening == 38 || jenisRekening == 56 || jenisRekening == 74) {
            Pelanggan cust1 = new Pelanggan(nomorTelepon, nama, saldo, pin, jenisRekening);
            break;
        } else {
            System.out.println("Input tidak valid. Silakan coba lagi.");
        }
}

public class Main {
    public static void main(String[] args) {
        while (true) {
            System.out.println("Selamat Datang di Aplikasi Bank. Silakan lakukan pembukaan rekening dahulu atau melakukan transaksi.");
            System.out.println("1. Pembukaan Rekening");
            System.out.println("2. Transaksi");
            System.out.print("Pilihan: ");
            int pilihan = scan.nextInt();

            if (pilihan == 1) {
                pembukaanRekening();
            } else if (pilihan == 2) {
                transaksi();
            } else {
                System.out.println("Input tidak valid. Silakan coba lagi.");
            }

        }
    }
}