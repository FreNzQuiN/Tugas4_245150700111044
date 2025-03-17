import java.util.Scanner;

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