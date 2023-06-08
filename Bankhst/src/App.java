/*

Konsep yang digunakan
Modul 1 : 
     - Variabel dan tipe data
     - Operator aritmatika dan Logika
    - Input Scanner
    - Struktur kontrol (If-else dan kawannya, switch)
Modul 2 : 
    - Kontrol perulangan(while , loop through object)
    - String method
Modul 3 : 
    - Class dan object
    - Method
    - Access Modifier
Modul 4 : 
    - Method setter dan getter (void dan nonvoid)
    - keyword this dan super
    - Method Constructor
Meodul 5 : 
    - Method overriding
    - Inheritance
    - Encapsulation
    - Polymorphism (dengan typecasting)
    - 
Modul 6 : 
    - Abstract Class
Modul 7  :
    - Try-catch exception handling
 )

- 
 */

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

abstract class User {
    protected String userId;
    protected String password;
}

class Nasabah extends User {
    private long saldo = 0;

    Nasabah(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }

    public String getUserId() {
        return this.userId;
    }

    public String getPassword() {
        return this.password;
    }

    public long getSaldo() {
        return this.saldo;
    }

    public void addSaldo(long saldo) {
        this.saldo += saldo;
    }

    public boolean tarikSaldo(long saldo) {
        if (saldo > this.saldo) {
            return false;

        } else {
            this.saldo -= saldo;
            return true;
        }
    }
}

class NasabahPremium extends Nasabah {
    private long bonus;

    NasabahPremium(String userId, String password) {
        super(userId, password);
        this.bonus = 0;
    }

    @Override
    public long getSaldo() {
        return super.getSaldo() + bonus;
    }

    public void setBonus(long bonus) {
        this.bonus = bonus;
    }

    public long getBonus() {
        return this.bonus;
    }

}

class Rekening {
    private ArrayList<Nasabah> nasabah;
    Scanner scanner = new Scanner(System.in);

    public Rekening() {
        this.nasabah = new ArrayList<Nasabah>();
    }

    public void addNasabah(Nasabah nasabah) {
        this.nasabah.add(nasabah);
    }

    public Nasabah login(String userId, String password) {
        for (Nasabah nasabah : this.nasabah) {
            if (userId.equals(nasabah.getUserId()) && password.equals(nasabah.getPassword())) {
                return nasabah;
            }
        }
        return null;
    }

    public void mymenu(Nasabah nasabah) {
        int pilih = 0;
        do {
            if (nasabah instanceof NasabahPremium) {
                System.out.println("Selamat datang Nasabah Premium, " + ((NasabahPremium) nasabah).getUserId());
                System.out.println("Bonus : " + ((NasabahPremium) nasabah).getBonus());
            } else {
                System.out.println("Selamat datang, " + nasabah.getUserId());
            }

            System.out.println("==============================");
            System.out.println("1. Cek Saldo");
            System.out.println("2. Tambah saldo");
            System.out.println("3. Tarik saldo");
            System.out.println("0. Keluar");
            System.out.print("Pilihan anda : ");
            try {
                pilih = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Pilihan anda tidak valid");
                scanner.nextLine();
                continue;
            }

            switch (pilih) {
                case 1: {
                    System.out.println("Saldo anda adalah : " + nasabah.getSaldo());
                    break;
                }
                case 2: {
                    System.out.print("Masukkan nominal saldo : Rp ");
                    long nominalSaldo = scanner.nextLong();
                    nasabah.addSaldo(nominalSaldo);
                    System.out.println("Tambah saldo berhasil");
                    if (nasabah instanceof NasabahPremium) {
                        long bonus = 10000;
                        ((NasabahPremium) nasabah).setBonus(bonus);
                        System.out.println("Bonus telah ditambahkan");
                    }
                    break;
                }
                case 3: {
                    System.out.print("Jumlah Penarikan : Rp ");
                    long nominalTarik = scanner.nextLong();
                    if (nasabah.tarikSaldo(nominalTarik) != false) {
                        System.out.println("Tarik saldo berhasil");
                    } else {
                        System.out.println("Saldo tidak cukup");
                    }
                    break;
                }
                case 0: {
                    System.out.println("Terima kasih");
                    scanner.close();
                    break;
                }
                default: {
                    System.out.println("Pilihan invalid");
                }

            }
        } while (pilih != 0);

    }

    public class App {
        public static void main(String[] args) throws Exception {
            Scanner scanner = new Scanner(System.in);
            Rekening rekening = new Rekening();
            rekening.addNasabah(new NasabahPremium("Jared", "ambatukam"));
            rekening.addNasabah(new Nasabah("Harry", "hawry3003"));
            rekening.addNasabah(new Nasabah("Jacob", "jacob123"));
            rekening.addNasabah(new Nasabah("Luipitong", "lv003"));
            rekening.addNasabah(new Nasabah("Joe", "Mama"));

            System.out.println("Login to your account");
            System.out.print("User ID : ");
            String userid = scanner.next();
            System.out.print("Password : ");
            String password = scanner.next();
            Nasabah authenticate = rekening.login(userid, password);
            if (authenticate != null) {
                System.out.println("Login successful");
                rekening.mymenu(authenticate);
            } else {
                System.out.println("Login invalid");
            }
            scanner.close();
        }
    }
}