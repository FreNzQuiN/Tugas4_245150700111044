import java.util.ArrayList;
import java.text.NumberFormat;
import java.util.Locale;

class DataBase {
    private static DataBase instance;
    private ArrayList<Pelanggan> dataPelanggan = new ArrayList<Pelanggan>();

    private DataBase() {}

    public static DataBase getInstance() {
        if (instance == null) {
            instance = new DataBase();
        }
        return instance;
    }

    public void tambahPelanggan(Pelanggan p) {
        dataPelanggan.add(p);
    }
    public ArrayList<Pelanggan> getDataPelanggan () {
        return dataPelanggan;
    }

    public String getNomorPelanggan(String nomorPelanggan) {
        for (Pelanggan p : dataPelanggan) {
            if (p.getNomorRekening().equals(nomorPelanggan)) {
                return p.getNomorRekening();
            }
        }
        return null;
    }

    public boolean cekNomorPelanggan(String nomorPelanggan) {
        for (Pelanggan p : dataPelanggan) {
            if (p.getNomorRekening().equals(nomorPelanggan)) {
                return true;
            }
        }
        return false;
    }
}

public class Pelanggan {

    // ++ DEKLARASI VARIABEL DENGAN ENKAPSULASI ++ \\
    private DataBase db = DataBase.getInstance(); 
    private String nomorRekening;
    private String nama;
    private double saldo;
    private int pin;
    private int jenisRekening;
    static int jumlahPelanggan = 0;
    private int kesalahanAutentifikasi;
    NumberFormat rupiah = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));

    // ++ KONSTRUKTOR ++ \\
    public Pelanggan(String nama, int pin, String nomorRekening, int jenisRekening) {
        jumlahPelanggan++;
        this.nomorRekening = nomorRekening;
        this.nama = nama;
        this.saldo = 0;
        this.pin = pin;
        this.jenisRekening = jenisRekening;
        this.kesalahanAutentifikasi = 0;
    }

    public void tambahKeDatabase() {
        db.tambahPelanggan(this);
    }

    // ++ ACCESSOR ATAU GETTER ++ \\
    public String getNomorRekening() {
        return nomorRekening;
    }

    public String getNama() {
        return nama;
    }

    public double getSaldo() {
        return saldo;
    }

    public int getPin() {
        return pin;
    }

    public int getJenisRekening() {
        return jenisRekening;
    }

    public int getKesalahanAutentifikasi() {
        return kesalahanAutentifikasi;
    }

    static int getJumlahPelanggan() {
        return jumlahPelanggan;
    }

    // ++ MUTATOR ATAU SETTER ++ \\
    public void setKesalahanAutentifikasi(int kesalahanAutentifikasi) {
        this.kesalahanAutentifikasi = kesalahanAutentifikasi;
    }

    // ++ SUBFUNGSI AUTENTIKASI ++ \\
    public boolean autentikasi(int inputPin) {
        if (inputPin == pin) {
            kesalahanAutentifikasi = 0;
            return true;
        } else {
            kesalahanAutentifikasi++;
            return false;
        }
    }

    // ++ SUBFUNGSI PENGECEKAN AKUN ++ \\
    public boolean isAkunDiblokir() {
        return kesalahanAutentifikasi >= 3;
    }

    // ++ SUBFUNGSI HITUNG CASHBACK ++ \\
    public double hitungCashback(double totalPembelian) {
        if (totalPembelian > 1000000) {
            switch (jenisRekening) {
                case 38:
                    return totalPembelian * 0.05;
                case 56:
                    return totalPembelian * 0.07;
                case 74:
                    return totalPembelian * 0.10;
                default:
                    return 0;
            }
        } else {
            switch (jenisRekening) {
                case 56:
                    return totalPembelian * 0.02;
                case 74:
                    return totalPembelian * 0.05;
                default:
                    return 0;
            }
        }
    }

    // ++ FUNGSI LAKUKAN TRANSAKSI ++ \\
    public boolean transaksiPembelian(double totalPembelian, int inputPin) {
        if (isAkunDiblokir()) {
            System.out.println("Akun Anda diblokir.");
            return false;
        }
        if (!autentikasi(inputPin)) {
            System.out.println("PIN salah.");
            return false;
        }
        double cashback = hitungCashback(totalPembelian);
        double totalBiaya = totalPembelian - cashback;
        if (saldo >= totalBiaya) {
            saldo -= totalBiaya;
            saldo += cashback;
            System.out.println("Transaksi berhasil. Saldo Anda sekarang: " + rupiah.format(saldo));
            return true;
        } else {
            System.out.println("Saldo tidak cukup.");
            return false;
        }
    }

    // ++ FUNGSI PENAMBAHAN SALDO ++ \\
    public void topUp(double jumlahTopUp, int inputPin) {
        if (isAkunDiblokir()) {
            System.out.println("Akun Anda diblokir.");
            return;
        }
        if (!autentikasi(inputPin)) {
            System.out.println("PIN salah.");
            return;
        }
        saldo += jumlahTopUp;
        System.out.printf("Top up berhasil. Saldo Anda sekarang: %s", rupiah.format(saldo));
    }

    // ++ FUNGSI CEK NOMOR PELANGGAN ++ \\
    void cekNomorPelanggan(String nomorPelanggan) {
        if (db.cekNomorPelanggan(nomorPelanggan)) {
            System.out.println("Nomor pelanggan ditemukan: " + nomorPelanggan);
        } else {
            System.out.println("Nomor pelanggan tidak ditemukan.");
        }
    }

    // ++ FUNGSI TAMPILKAN INFORMASI PELANGGAN ++ \\
    public void tampilkanInformasi() {
        System.out.println("Nama: " + nama);
        System.out.println("Nomor Rekening: " + nomorRekening);
        System.out.println("Saldo: " + saldo);
        System.out.println("Jenis Rekening: " + jenisRekening);
    }
}