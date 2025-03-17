import java.sql.DatabaseMetaData;
import java.util.ArrayList;

class DataBase {
    private ArrayList<Pelanggan> dataPelanggan = new ArrayList<Pelanggan>();

    public void tambahPelanggan(Pelanggan p) {
        dataPelanggan.add(p);
    }
    public String getNomorPelanggan(String nomorPelanggan) {
        for (Pelanggan p : dataPelanggan) {
            if (p.getNomorPelanggan().equals(nomorPelanggan)) {
                return p.getNomorPelanggan();
            }
        }
        return null;
    }
    public boolean cekNomorPelanggan(String nomorPelanggan) {
        for (Pelanggan p : dataPelanggan) {
            if (p.getNomorPelanggan().equals(nomorPelanggan)) {
                return true;
            }
        }
        return false;
    }
}

public class Pelanggan {

    // ++ DEKLARASI VARIABEL DENGAN ENKAPSULASI ++ \\
    private DataBase db = new DataBase(); 
    private String nomorPelanggan;
    private String nama;
    private double saldo;
    private int pin;
    private int jenisRekening;
    static int jumlahPelanggan = 0;
    private int kesalahanAutentifikasi;

    // ++ KONSTRUKTOR ++ \\
    public Pelanggan(String nomorPelanggan, String nama, double saldo, int pin, int jenisRekening) {
        jumlahPelanggan++;
        this.nomorPelanggan = nomorPelanggan;
        this.nama = nama;
        this.saldo = saldo;
        this.pin = pin;
        this.jenisRekening = Integer.parseInt(nomorPelanggan.substring(0, 2));
        this.kesalahanAutentifikasi = 0;
    }

    // ++ ACCESSOR ATAU GETTER ++ \\
    public String getNomorPelanggan() {
        return nomorPelanggan;
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
        if (saldo - totalBiaya >= 10000) {
            saldo -= totalBiaya;
            saldo += cashback;
            System.out.println("Transaksi berhasil. Saldo Anda sekarang: " + saldo);
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
        System.out.println("Top up berhasil. Saldo Anda sekarang: " + saldo);
    }

    // ++ FUNGSI CEK NOMOR PELANGGAN ++ \\
    void cekNomorPelanggan(String nomorPelanggan) {
        if (db.cekNomorPelanggan(nomorPelanggan)) {

        }
    }
}