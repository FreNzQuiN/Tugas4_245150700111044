import java.io.IOException;
import java.util.Scanner;

class ClearScreen {
    public static void clear() {
        try {
            String os = System.getProperty("os.name").toLowerCase();

            if (os.contains("win")) {
                // Windows
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                // macOS/Linux
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (IOException | InterruptedException ex) {
            System.err.println("Gagal membersihkan layar: " + ex.getMessage());
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        while (true) {
            System.out.println("===================================================================");
            System.out.println("                  SELAMAT DATANG DI APLIKASI BANK.");
            System.out.println("===================================================================");
            System.out.println("Silakan lakukan pembukaan rekening dahulu atau melakukan transaksi.");
            System.out.println("___________________________________________________________________");
            System.out.println("1. Pembukaan Rekening");
            System.out.println("2. Transaksi");
            System.out.println("0. Keluar");
            System.out.println("---------------------------------------------");
            System.out.print("Pilihan: ");
            int pilihan = scan.nextInt();
            ClearScreen.clear();
            switch (pilihan) {
                case 1:
                    pembukaanRekening();
                    break;
                case 2:
                    transaksi();
                    break;
                case 0:
                    System.out.println("==================================================================");
                    System.out.println("         Terima kasih telah menggunakan aplikasi kami.");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Input tidak valid. Silakan coba lagi.");
                    break;
            }
            ClearScreen.clear();
        }
    }

    static void pembukaanRekening() {
        Scanner scan = new Scanner(System.in);
        String nama = "";
        int pin = 0;
        int jenisRekening = 0;
        boolean namaValid = false;
        boolean pinValid = false;
        boolean jenisRekeningValid = false;
        
        System.out.println("===================================================================");
        System.out.println("                      PEMBUKAAN REKENING");
        System.out.println("===================================================================");

        while (!namaValid) {
            System.out.print("Masukkan Nama: ");
            nama = scan.nextLine();
            if (nama.matches("[a-zA-Z]+")) {
                namaValid = true;
            } else {
                System.out.println("Nama hanya boleh berisi huruf. Mohon masukkan kembali.");
            }
        }

        while (!pinValid) {
            System.out.print("\nMasukkan PIN (6 Digit): ");
            if (scan.hasNextInt()) {
                pin = scan.nextInt();
                if (pin >= 100000 && pin <= 999999) {
                    pinValid = true;
                } else {
                    System.out.println("PIN harus berisi 6 digit. Mohon masukkan kembali.");
                }
            } else {
                System.out.println("Input harus berupa angka. Mohon masukkan kembali.");
                scan.next(); // Mengabaikan input yang tidak valid
            }
        }

        while (!jenisRekeningValid) {
            System.out.print("\nJenis Rekening?\n - Silver (Masukkan '38')\n - Gold (Masukkan '56')\n - Platinum (Masukkan '74')");
            System.out.print("\nMasukkan Jenis Rekening: ");
            if (scan.hasNextInt()) {
                jenisRekening = scan.nextInt();
                if (jenisRekening == 38 || jenisRekening == 56 || jenisRekening == 74) { // Memeriksa apakah input adalah pilihan yang valid
                    jenisRekeningValid = true;
                } else {
                    System.out.println("Pilihan jenis rekening tidak valid. Mohon masukkan kembali.");
                }
            } else {
                System.out.println("Input harus berupa angka. Mohon masukkan kembali.");
                scan.next();
            }
        }
        scan.nextLine();
        scan.close();

        String nomorRekening = jenisRekening + String.valueOf(pin);
        System.out.println("\nNomor Rekening Anda: " + nomorRekening + " berhasil dibuat!");
    }

    static void transaksi(){

    }

    static void topUp(){

    }
}