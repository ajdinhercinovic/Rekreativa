📱 Rekreativa – Faza II
=======================

**Autor:** Ajdin Herčinović

1\. Opis aplikacije
-------------------

Aplikacija korisnicima omogućava:

*   pregled dostupnih terena,
    
*   kreiranje rezervacija,
    
*   evidenciju dogovorenih termina,
    
*   pregled i upravljanje pozivima,
    
*   centralizovanu navigaciju kroz sve funkcionalne cjeline aplikacije.
    

2\. Implementirane funkcionalnosti
----------------------------------

### 🔐 Login screen

*   Ekran koji se prikazuje pri prvom pokretanju aplikacije
    
*   Omogućen unos podataka (bez validacije u ovoj fazi)
    
*   Dugme **„PRIJAVI SE“** vodi na glavni dio aplikacije
    
*   Dugme **„REGISTRUJ SE“** vodi na ekran za registraciju
    

### 📝 Register screen

*   Forma za registraciju korisnika (UI faza)
    
*   Polje za datum rođenja koristi **DatePicker**
    
*   Dugme **„PRIJAVI SE“** vodi nazad na login screen
    
*   Dugme **„REGISTRUJ SE“** trenutno nema backend funkcionalnost
    

### 🧭 Navigacijski meni

*   Donji navigacijski meni prisutan na svim glavnim ekranima
    
*   Omogućava kretanje između:
    
    *   Početna
        
    *   Tereni
        
    *   Događaji
        
    *   Termini
        
    *   Profil
        
*   Predstavlja **ključnu funkcionalnost aplikacije**, jer omogućava korištenje svih ostalih dijelova sistema
    

### 🏠 Početna (Home) screen

*   Prikazuje naredni termin korisnika
    
*   Prikazuje preporučene događaje
    
*   Zamišljen kao „prečica“ ka najvažnijim informacijama
    
*   Trenutno koristi lokalno definisane podatke (planirano povezivanje sa bazom u narednoj fazi)
    

### 🏟️ Tereni screen

*   Katalog svih dostupnih sportskih terena
    
*   Pretraga po nazivu terena
    
*   Filtriranje po sportovima i aktivnostima
    
*   Moguće odabrati jedan ili više sportova
    
*   Po defaultu su prikazani svi tereni
    

### 🏟️ Teren (detalji) screen

*   Detaljan pregled odabranog terena
    
*   Prikaz:
    
    *   sportova koje teren podržava
        
    *   cijene
        
    *   kapaciteta
        
*   Formular za rezervaciju:
    
    *   tip rezervacije: **jednokratno / sedmično**
        
    *   dinamička promjena polja za vrijeme
        
    *   DatePicker za jednokratne termine
        
    *   Dropdown za period i tip događaja
        
*   Klikom na **„REZERVIŠI“** kreira se rezervacija koja se šalje u **Firebase Firestore bazu**
    

### 📅 Termini screen

*   Evidencija:
    
    *   rezervacija na čekanju
        
    *   dogovorenih termina
        
    *   primljenih poziva
        
    *   poslanih poziva
        
*   Toggle filter: **sve / termini / pozivi**
    
*   Rezervacije se čitaju direktno iz Firestore baze
    
*   Omogućena potvrda i otkazivanje rezervacije
    

### 📆 Događaji screen

*   Prikaz sportskih događaja
    
*   Filtriranje po sportovima
    
*   Dodatni filter: svi događaji / samo događaji prijatelja
    
*   Trenutno koristi lokalne podatke (UI faza)
    
*   Planirano proširenje za otvorene i zatvorene događaje
    

### 👤 Profil screen

*   Prikaz ličnih podataka korisnika
    
*   Prikaz statistike:
    
    *   broj termina
        
    *   fair play ocjena
        
    *   znanje po sportovima
        
*   Ideja sistema rejtinga radi podsticanja fer igre
    
*   Podaci su trenutno lokalni (planirano čuvanje u bazi)
    

3\. Firebase / Firestore integracija
------------------------------------

*   Aplikacija koristi **Firebase Firestore** kao bazu podataka
    
*   Implementiran **CRUD ciklus** nad rezervacijama:
    
    *   **Create** – kreiranje rezervacije
        
    *   **Read** – čitanje rezervacija i termina
        
    *   **Update** – potvrda rezervacije (status)
        
    *   **Delete** – otkazivanje rezervacije
        
*   Baza je u **test mode-u** radi lakšeg razvoja i demonstracije funkcionalnosti
    

4\. Arhitektura aplikacije
--------------------------

#### UI sloj

Svaki ekran aplikacije implementiran je kao zasebna @Composable funkcija (npr. PocetnaScreen, TereniScreen, DogadjajiScreen, TerminiScreen, ProfilScreen).UI je dodatno podijeljen na manje, ponovo iskoristive komponente kao što su kartice, dugmad, filteri i headeri.

Svi custom UI elementi nose prefiks **Rekreativa** (npr. RekreativaButton, RekreativaTogglePill, RekreativaHeader) kako bi se jasno razlikovali od standardnih Compose komponenti i olakšalo snalaženje u kodu.Navigacija

Navigacija je centralizovana i implementirana pomoću NavHost komponente. Sve rute su definisane na jednom mjestu (Routes.kt), dok MainScreen služi kao glavni layout koji sadrži bottom navigation i prikazuje aktivni ekran.

#### Data sloj

Podaci su modelirani pomoću Kotlin data klasa.Za rad sa Firebase Firestore bazom koristi se ReservationsRepository, dok je ReservationMapper zadužen za mapiranje podataka između Firestore dokumenata i UI modela.

Aplikacija implementira **CRUD funkcionalnost**:

*   Create – kreiranje rezervacije putem formulara
    
*   Read – dohvat i prikaz rezervacija u ekranu Termini
    
*   Update – potvrda rezervacije i promjena statusa
    
*   Delete - brisanje ako korisnik otkaže rezervaciju
    

5\. Zaključak
-------------

U fazi II implementiran je kompletan UI aplikacije, navigacija između ekrana, integracija sa Firebase bazom podataka i osnovni CRUD ciklus nad rezervacijama.

Aplikacija je arhitektonski pripremljena za dalja proširenja u narednim fazama razvoja.

Ni sama ideja aplikacije nije krajnje definisana, moguće su promjene rješenja u narednim fazama ali ne "predaleko" od tretnutnog koncepta.