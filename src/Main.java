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
    private static Scanner scan = new Scanner(System.in);
    private static DataBase db = DataBase.getInstance();

    public static void main(String[] args) {
        while (true) {
            System.out.println("===================================================================");
            System.out.println("                  SELAMAT DATANG DI APLIKASI BANK.");
            System.out.println("===================================================================");
            System.out.println("Silakan lakukan pembukaan rekening dahulu atau melakukan transaksi.");
            System.out.println("___________________________________________________________________");
            System.out.println("1. Pembukaan Rekening");
            System.out.println("2. Transaksi");
            System.out.println("3. Informasi Pelanggan");
            System.out.println("4. Informasi Aplikasi");
            System.out.println("0. Keluar");
            System.out.println("---------------------------------------------");
            System.out.print("Pilihan: ");
            int pilihan = scan.nextInt();
            ClearScreen.clear();
            scan.nextLine();
            switch (pilihan) {
                case 1:
                    try {
                        pembukaanRekening();
                    } catch (InterruptedException e) {
                        System.err.println("Thread interrupted: " + e.getMessage());
                    }
                    break;
                case 2:
                    int pilihanTransaksi = 0;
                    boolean transaksiValid = false;
                    System.out.println("---------------------------------------------");
                    System.out.println("1. Pembelian");
                    System.out.println("2. Top Up");
                    System.out.println("0. Kembali");
                    System.out.println("---------------------------------------------");
                    while(!transaksiValid){
                        System.out.print("Pilihan: ");
                        if (scan.hasNextInt()) {
                            pilihanTransaksi = scan.nextInt();
                            if(pilihanTransaksi == 1 || pilihanTransaksi == 2 || pilihanTransaksi == 0){
                                transaksiValid = true;
                            } else {
                                System.out.println("Input tidak valid. Silakan coba lagi.");
                            }
                        } else {
                            System.out.println("Input harus berupa angka. Silakan coba lagi.");
                            scan.next(); // Membersihkan input yang tidak valid
                        }
                    }
                    scan.nextLine();
                    ClearScreen.clear();
                    switch (pilihanTransaksi) {
                        case 1:
                            System.out.println("==================================================================");
                            System.out.println("                      MENU TRANSAKSI");
                            System.out.println("==================================================================");
                            System.out.println("0. Kembali");
                            System.out.println("---------------------------------------------");
                            transaksi();
                            break;
                        case 2:
                            System.out.println("==================================================================");
                            System.out.println("                      MENU TOP UP");
                            System.out.println("==================================================================");
                            System.out.println("0. Kembali");
                            System.out.println("---------------------------------------------");
                            topUp();
                            break;
                        case 0:
                            break;
                        default:
                            System.out.println("Input tidak valid. Silakan coba lagi.");
                            break;
                    }
                    break;
                case 3:
                    System.out.println("==================================================================");
                    System.out.println("                      INFORMASI PELANGGAN");
                    System.out.println("==================================================================");
                    System.out.println("0. Kembali");
                    System.out.println("---------------------------------------------");
                    boolean adaRekening = false;
                    Pelanggan pelanggan = null;
                    while(!adaRekening){
                        System.out.print("Masukkan nomor rekening: ");
                        String nomorRekening = scan.nextLine();
                        if (nomorRekening.equals("0")) {
                            return;
                        }
                        pelanggan = getPelangganByNomorRekening(nomorRekening);
                        if (pelanggan == null) {
                            System.out.println("Nomor rekening tidak ditemukan.");
                        } else {
                            adaRekening = true;
                        }
                    }
                    pelanggan.tampilkanInformasi();
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        System.err.println("Thread interrupted: " + e.getMessage());
                    }
                    System.out.println("==================================================================");
                    break;
                case 4:
                    System.out.println("==================================================================");
                    System.out.println("                      INFORMASI APLIKASI");
                    System.out.println("==================================================================");
                    System.out.println("Aplikasi ini adalah aplikasi bank sederhana yang memungkinkan pengguna");
                    System.out.println("untuk membuka rekening, melakukan transaksi pembelian, dan melakukan top up.");
                    System.out.println("Aplikasi ini memiliki 3 jenis rekening: Silver, Gold, dan Platinum.");
                    System.out.println("Setiap jenis rekening memiliki cashback yang berbeda.");
                    System.out.println("Silakan pilih menu yang tersedia untuk melanjutkan.");
                    System.out.println("==================================================================");
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        System.err.println("Thread interrupted: " + e.getMessage());
                    }
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

    static void pembukaanRekening() throws InterruptedException {
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
            if (nama.matches("[a-zA-Z ]+")) {
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
                scan.next();
            }
        }

        while (!jenisRekeningValid) {
            System.out.print("\nJenis Rekening?\n - Silver (Masukkan '38')\n - Gold (Masukkan '56')\n - Platinum (Masukkan '74')");
            System.out.print("\nMasukkan Jenis Rekening: ");
            if (scan.hasNextInt()) {
                jenisRekening = scan.nextInt();
                if (jenisRekening == 38 || jenisRekening == 56 || jenisRekening == 74) {
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

        int idUnik = Pelanggan.getJumlahPelanggan();
        String nomorRekening = String.format("%08d", idUnik); // Memformat idUnik menjadi 6 digit dengan leading zeros
        nomorRekening = String.valueOf(jenisRekening) + nomorRekening; // Menambahkan jenis rekening di depan
        System.out.println("\nNomor Rekening Anda: " + nomorRekening + " berhasil dibuat!");
        
        Pelanggan pelangganBaru = new Pelanggan(nama, pin, nomorRekening, jenisRekening);
        pelangganBaru.tambahKeDatabase();

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            System.err.println("Thread interrupted: " + e.getMessage());
        }
    }

    static void transaksi() {
        boolean adaRekening = false;
        Pelanggan pelanggan = null;
        while(!adaRekening){
            System.out.print("Masukkan nomor rekening: ");
            String nomorRekening = scan.nextLine();
            if (nomorRekening.equals("0")) {
                return;
            }
            pelanggan = getPelangganByNomorRekening(nomorRekening);
            if (pelanggan == null) {
                System.out.println("Nomor rekening tidak ditemukan.");
            } else {
                adaRekening = true;
            }
        }
    
        System.out.print("Masukkan total pembelian: ");
        double totalPembelian = scan.nextDouble();
        System.out.print("Masukkan PIN: ");
        int pin = scan.nextInt();
        scan.nextLine();
    
        if (pelanggan.transaksiPembelian(totalPembelian, pin)) {
            System.out.println("Transaksi berhasil.");
        } else {
            System.out.println("Transaksi gagal.");
        }

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            System.err.println("Thread interrupted: " + e.getMessage());
        }
    }

    static void topUp() {
        boolean adaRekening = false;
        Pelanggan pelanggan = null;
        while(!adaRekening){
            System.out.print("Masukkan nomor rekening: ");
            String nomorRekening = scan.nextLine();
            if (nomorRekening.equals("0")) {
                return;
            }
            pelanggan = getPelangganByNomorRekening(nomorRekening);
            if (pelanggan == null) {
                System.out.println("Nomor rekening tidak ditemukan.");
            } else {
                adaRekening = true;
            }
        }

        System.out.print("Masukkan jumlah top up: ");
        double jumlahTopUp = scan.nextDouble();
        System.out.print("Masukkan PIN: ");
        int pin = scan.nextInt();
        scan.nextLine();

        pelanggan.topUp(jumlahTopUp, pin);

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            System.err.println("Thread interrupted: " + e.getMessage());
        }
    }

    static Pelanggan getPelangganByNomorRekening(String nomorRekening) {
        for (Pelanggan p : db.getDataPelanggan()) {
            if (p.getNomorRekening().equals(nomorRekening)) {
                return p;
            }
        }
        return null;
    }
}