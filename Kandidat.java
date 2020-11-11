
package tubes.pemnetserver;

public class Kandidat {
    
    int id;
    String nama;
    int JumlahSuara;

    public Kandidat(int id, String nama) {
        this.id = id;
        this.nama = nama;
        this.JumlahSuara = 0;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public void setJumlahSuara(int JumlahSuara) {
        this.JumlahSuara = JumlahSuara;
    }

    public int getId() {
        return id;
    }

    public String getNama() {
        return nama;
    }

    public int getJumlahSuara() {
        return JumlahSuara;
    }
    
    
    
}
