# RIS_PROJEKT (Aplikacija za opravljanje nalog)

**Kratek Opis:** Celovita full-stack aplikacija za upravljanje nalog (To-Do List), razvita z arhitekturo mikrostoritev. Aplikacija omogoča operacije **CRUD** (Ustvarjanje, Branje, Posodabljanje, Brisanje), z dodano podporo za **ročne datume** in **filtriranje/iskanje** po ključnih besedah in statusu. Celotna rešitev je kontejnerizirana z uporabo **Docker Compose**.

---

# Vizija projekta

## Namen aplikacije

V današnjem hitrem svetu imamo vsi ogromno nalog - za fakulteto, službo, gospodinjstvo. Težko je vse zapomniti in pogosto pozabimo pomembne stvari ali zamudimo roke. Naša ToDo aplikacija pomaga ljudem organizirati svoje naloge na enostaven in pregleden način, tako da lahko ostanejo produktivni in brez stresa.

## Problem, ki ga rešujemo

Mnogi ljudje uporabljajo navadne liste na papirju ali preproste aplikacije, ki ne omogočajo naprednega iskanja, filtriranja ali organizacije. Ko se nalog nabere veliko, postane kaotično. Naša aplikacija reši te težave z:

- **Hitrim iskanjem** po nalogah (ko imaš 50+ nalog, to postane ključno)
- **Filtriranjem po statusu** (videti samo dokončane ali nedokončane)
- **Preglednim vmesnikom** - vse na enem mestu
- **Zanesljivim shranjevanjem** - podatki so varno shranjeni v bazi

## Komu je aplikacija namenjena

### Primarna ciljna skupina

- **Študenti** - za organizacijo projektov, izpitov, seminarske naloge, rokov
- **Zaposleni** - za upravljanje delovnih nalog, sestankov, obveznosti
- **Freelancerji** - za sledenje projektom in naročilom

### Sekundarna ciljna skupina

- **Vsakdo**, ki želi biti bolj organiziran v vsakdanjem življenju
- **Ekipe** (v prihodnosti) - skupno upravljanje projektnih nalog

## Kaj želimo doseči

Naš cilj je ustvariti aplikacijo, kjer uporabnik:

- **Hitro doda** novo nalogo (brez zapletenih formularjev)
- **Enostavno označi**, kaj je že končano
- **Išče** po nalogah, tudi če jih ima na stotine
- **Filtrira** - vidi samo tisto, kar ga zanima (dokončane/nedokončane)
- **Uredi ali izbriše** naloge brez težav
- **Nastavi roke** - da ve, kdaj mora kaj narediti (prihodnja funkcionalnost)
- **Organizira po prioritetah** - kaj je nujno, kaj lahko počaka (prihodnja funkcionalnost)

Želimo, da uporabnik **prihrani čas** in **zmanjša stres** pri upravljanju svojih obveznosti.

## Zakaj je naša aplikacija posebna

### Tehnološka prednost

- **Arhitektura mikrostoritev** - profesionalen pristop, kot v resničnih podjetjih
- **Kontejnerizacija z Docker** - enostavna postavitev in skalabilnost
- **Full-stack** - popolna rešitev od baze do uporabniškega vmesnika
- **RESTful API** - omogoča prihodnje razširitve (mobilna aplikacija)

### Funkcionalna prednost

- **Napredno iskanje** - ne le po naslovu, tudi po opisu naloge
- **Dvojno filtriranje** - kombinacija iskanja in statusa
- **Hiter odziv** - optimizirane poizvedbe v bazo
- **Enostaven vmesnik** - vse najpomembnejše na enem zaslonu

## Primerjava z obstoječimi rešitvami

| Naša aplikacija | Papirnati listi | Preproste ToDo app |
| :--- | :--- | :--- |
| Iskanje po ključnih besedah | Ne | Omejeno |
| Filtriranje po statusu | Ne | Da |
| Varno shranjevanje | Ne | Da |
| Urejanje nalog | Ne | Da |
| Skalabilna arhitektura | Ne | Ne |

## Prihodnost projekta

### Kratkoročni cilji (naslednji 3 meseci)

- **Dodajanje rokov** - uporabnik lahko nastavi datum dokončanja
- **Kategorije nalog** - "Fakulteta", "Služba", "Osebno"
- **Prioritete** - označevanje nalog kot "Nujno", "Pomembno", "Lahko počaka"

### Srednjeročni cilji (6-12 mesecev)

- **Mobilna aplikacija** - dostop do nalog kjerkoli
- **Notifikacije** - opomniki za bližajoče se roke
- **Deljenje nalog** - sodelovanje med uporabniki (ekipni projekti)
- **Statistika** - koliko nalog si dokončal ta teden/mesec

### Dolgoročna vizija (1-2 leti)

- **AI priporočila** - predlogi, kdaj narediti katero nalogo
- **Integracije** - Google Calendar, Microsoft To Do
- **Večjezičnost** - podpora za več jezikov
- **Različne vloge uporabnikov** - admin, navaden uporabnik

## Pedagoška vrednost projekta

Ta projekt ni le praktična aplikacija, ampak tudi **učno orodje**. Z njim:

- Učimo se **moderne tehnologije** (React, Spring Boot, Docker)
- Razumemo **arhitekturo mikrostoritev**
- Delamo z **relacijskimi bazami** (MySQL)
- Obvladamo **REST API** komunikacijo
- Vadimo **timsko delo** z Git/GitHub
- Razvijamo **debugging** sposobnosti

## Zaključek

Naša ToDo aplikacija je več kot le seznam nalog - je **celovita rešitev** za organizacijo vsakdanjega življenja. Kombinira **preprostost uporabe** s **profesionalno tehnično izvedbo**.

Medtem ko uporabniku omogočamo, da ostane organiziran in produktiven, se mi kot razvijalci učimo tehnologij in pristopov, ki se uporabljajo v resničnih poslovnih okoljih.

To je projekt, ki **resnično pomaga ljudem** in hkrati **gradi naše znanje** za prihodnjo kariero v IT industriji.

---

## 📝 Besednjak (Slovar Ključnih Izrazov)

V tej tabeli so razloženi ključni izrazi, ki so specifični za funkcionalnosti aplikacije, s čimer se zagotavlja, da bo vsak uporabnik hitro razumel pomen in uporabo.

| Izraz (Termin) | Povezava z Vizijo Projekta | Razlaga v Kontekstu Aplikacije |
| :--- | :--- | :--- |
| **Seznam Nalog (Lista)** | **Namen Aplikacije** (Organizacija) | Osrednji prikaz vseh ustvarjenih opravil, ki jih uporabnik upravlja. |
| **Naloga (Todo)** | **Osnovna Funkcionalnost** (Enota dela) | Posamezen vnos v seznamu, ki vsebuje ime, status in morebiten rok. |
| **Rok (Due Date)** | **Kratkoročni Cilj** (Upravljanje z roki) | Datum, ki določa, kdaj je treba nalogo opraviti, s čimer se zmanjša zamujanje obveznosti. |
| **Status** | **Problem, ki ga Rešujemo** (Filtriranje) | Logična oznaka, ki določa, ali je naloga **Dokončana** (Completed) ali **Nedokončana** (Uncompleted/Active). |
| **Filtriranje** | **Problem, ki ga Rešujemo** (Preglednost) | Funkcija, ki omogoča prikaz nalog samo glede na njihov Status (npr. prikaži samo nedokončane). |
| **Iskanje** | **Problem, ki ga Rešujemo** (Hitro iskanje) | Funkcija, ki omogoča hitro lociranje nalog z uporabo **ključne besede** znotraj imena naloge. |
| **Urejanje** | **Kaj Želimo Doseči** (Sprememba podatkov) | Možnost, da uporabnik vstopi v 'Edit Mode' in popravi ali posodobi Ime in Rok obstoječe naloge. |
| **Preklop Statusa (Toggle)**| **Kaj Želimo Doseči** (Enostavno označevanje) | Hitra akcija (običajno s klikom na Checkbox), ki spremeni Status naloge iz aktivne v dokončano in obratno. |
| **CRUD** | **Urejanje Nalog** (Popoln nadzor) | Akronim za vse temeljne operacije: ustvarjanje, branje, posodabljanje in brisanje nalog. |

---

## 📑 Detaljni Primeri Upotrebe (Use Case Specification)

Ove tabele opisuju funkcionalne zahteve za svaki element u dijagramu, podeljene po akterima.

### Akter: Registrovani Korisnik

| Primer uporabe: **DODAJANJE NALOG** | ID: **PU-01** |
| :--- | :--- |
| **Cilj:** Korisnik želi da doda novu stavku u svoj spisak obaveza. |
| **Akteri:** Registrovani Korisnik, Sistem |
| **Preduslovi:** Korisnik je prijavljen u sistem. |
| **Stanje sistema nakon PU:** Nova stavka obaveze je trajno sačuvana i vidljiva na spisku. |
| **Scenario:** |
| 1. Korisnik izabere opciju **"Dodaj novu nalogo"**. |
| 2. Sistem prikazuje polje za unos teksta i opcionalno polje za rok. |
| 3. Korisnik unese tekstualni opis zadatka. |
| 4. Korisnik potvrdi unos. |
| 5. Sistem validira podatke, čuva novu stavku i osvežava listu. |
| **Alternativni tokovi:** Korisnik prekida unos (pre nego što se sačuva) → Zadatak se ne dodaje. |
| **Izuzeci:** Nevalidan unos (npr. prazan naslov) → Sistem prikazuje poruku o grešci. |

---

| Primer uporabe: **UREJANJE NALOG** | ID: **PU-02** |
| :--- | :--- |
| **Cilj:** Korisnik želi da promeni tekst ili rok postojeće stavke. |
| **Akteri:** Registrovani Korisnik, Sistem |
| **Preduslovi:** Stavka mora postojati u bazi. |
| **Stanje sistema nakon PU:** Podaci o stavci su ažurirani u bazi. |
| **Scenario:** |
| 1. Korisnik pronađe stavku koju želi da uredi. |
| 2. Korisnik pokreće opciju **"Uređivanje"** (npr. klikom na ikonu za olovku). |
| 3. Sistem prikazuje formu za uređivanje sa trenutnim podacima. |
| 4. Korisnik menja tekst zadatka i/ili rok. |
| 5. Korisnik sačuva izmene. |
| 6. Sistem validira, ažurira stavku u bazi i osvežava prikaz. |
| **Alternativni tokovi:** Korisnik poništi promene → Zadatak ostaje nepromenjen. |
| **Izuzeci:** Neuspešno ažuriranje baze → Sistem javlja "Greška pri čuvanju izmena." |

---

| Primer uporabe: **BRISANJE NALOG** | ID: **PU-03** |
| :--- | :--- |
| **Cilj:** Korisnik želi trajno da ukloni zadatak sa svog spiska. |
| **Akteri:** Registrovani Korisnik, Sistem |
| **Preduslovi:** Stavka mora postojati. |
| **Stanje sistema nakon PU:** Stavka je trajno uklonjena iz baze podataka. |
| **Scenario:** |
| 1. Korisnik izabere stavku za brisanje. |
| 2. Korisnik pokreće opciju **"Brisanje"**. |
| 3. Sistem traži potvrdu: "Da li ste sigurni da želite obrisati nalogo?". |
| 4. Korisnik potvrdi brisanje. |
| 5. Sistem uklanja stavku iz baze i osvežava listu. |
| **Alternativni tokovi:** Korisnik otkaže brisanje → Stavka ostaje na spisku. |
| **Izuzeci:** Greška u komunikaciji sa serverom/bazom → Sistem prikazuje poruku o neuspehu. |

---

| Primer uporabe: **OZNAČEVANJE NALOG KOT KONČANE** | ID: **PU-04** |
| :--- | :--- |
| **Cilj:** Korisnik želi brzo da označi zadatak kao završen ili da poništi status završenog zadatka. |
| **Akteri:** Registrovani Korisnik, Sistem |
| **Preduslovi:** Stavka mora postojati. |
| **Stanje sistema nakon PU:** Status stavke (`completed`) je preklopljen (True/False) i sačuvan u bazi. |
| **Scenario:** |
| 1. Korisnik klikne na element za prebacivanje statusa (npr. Checkbox) pored zadatka. |
| 2. Sistem automatski ažurira status u bazi. |
| 3. Sistem osvežava prikaz (npr. zadatak se prebriše ili premesti). |
| **Alternativni tokovi:** Ažuriranje ne uspe zbog filterskih podešavanja → Zadatak nestaje iz trenutno filtriranog prikaza. |
| **Izuzeci:** Neuspešno ažuriranje statusa na serveru → Sistem javlja "Greška pri ažuriranju statusa." |

---

| Primer uporabe: **NASTAVLJANJE ROKOV** | ID: **PU-05** |
| :--- | :--- |
| **Cilj:** Korisnik želi da postavi obavezni datum roka za zadatak. |
| **Akteri:** Registrovani Korisnik, Sistem |
| **Preduslovi:** Stavka mora biti dodata ili se dodaje. |
| **Stanje sistema nakon PU:** Stavci je dodan validan datum roka (`dueDate`). |
| **Scenario:** |
| 1. Korisnik pokreće **Dodavanje** (PU-01) ili **Uređivanje** (PU-02) zadatka. |
| 2. Korisnik koristi birač datuma (Date Picker) da odabere rok. |
| 3. Korisnik čuva zadatak. |
| 4. **EXTEND:** Ukoliko je rok blizu (npr. unutar 24h), sistem automatski nudi opciju **Nastavi opomnik** (PU-11). |
| **Alternativni tokovi:** Korisnik izbriše postojeći rok → sistem shrani nalogo brez roka. |
| **Izuzeci:** Datum roka je u preteklosti → sistem opozori uporabnika, a shrani. |

---

| Primer uporabe: **ISKANJE NALOG** | ID: **PU-06** |
| :--- | :--- |
| **Cilj:** Korisnik želi brzo da pronađe zadatke koji sadrže određenu ključnu reč. |
| **Akteri:** Registrovani Korisnik, Sistem |
| **Preduslovi:** Postoji spisak zadataka. |
| **Stanje sistema nakon PU:** Spisak je dinamički filtriran da prikaže samo podudarne zadatke. |
| **Scenario:** |
| 1. Korisnik unosi ključnu reč u polje za pretragu. |
| 2. Sistem šalje zahtev serveru sa ključnom reči. |
| 3. Server vraća samo zadatke čiji tekst sadrži ključnu reč. |
| 4. Sistem prikazuje skraćeni, filtrirani spisak. |
| **Alternativni tokovi:** Nema rezultata za ključnu reč → Sistem prikazuje: "Ni najdenih nalog." |
| **Izuzeci:** Greška u komunikaciji → Sistem prikazuje spisak bez filtera uz upozorenje. |

---

| Primer uporabe: **FILTRIRANJE PO STATUSU** | ID: **PU-07** |
| :--- | :--- |
| **Cilj:** Korisnik želi da prikaže samo određeni subset zadataka (npr. samo završene ili samo nezavršene). |
| **Akteri:** Registrovani Korisnik, Sistem |
| **Preduslovi:** Postoji spisak zadataka. |
| **Stanje sistema nakon PU:** Spisak je filtriran da prikaže samo zadatke sa izabranim statusom. |
| **Scenario:** |
| 1. Korisnik izabere opciju filtriranja (npr. "Nedokončane" ali "Dokončane"). |
| 2. Sistem šalje zahtev serveru sa parametrom statusa. |
| 3. Server vraća samo zadatke koji odgovaraju statusu. |
| 4. Sistem prikazuje filtrirani spisak. |
| **Alternativni tokovi:** Korisnik izabere "Sve" → Sistem prikazuje celokupan spisak. |
| **Izuzeci:** Greška u komunikaciji → Sistem prikazuje spisak bez filtera uz upozorenje. |

---

| Primer uporabe: **PREGLED NAPREDKA** | ID: **PU-08** |
| :--- | :--- |
| **Cilj:** Korisnik želi da stekne vizuelni uvid u svoj napredak (koliko je završeno/nezavršeno). |
| **Akteri:** Registrovani Korisnik, Sistem |
| **Preduslovi:** Korisnik mora imati barem jednu stavku. |
| **Stanje sistema nakon PU:** Prikazana je statistika; podaci ostaju nepromenjeni. |
| **Scenario:** |
| 1. Korisnik izabere opciju **"Pregled napredka"** (biće implementirano u budućnosti). |
| 2. Sistem izračunava procenat završenih zadataka. |
| 3. Sistem prikazuje grafički prikaz (npr. krug dijagram) i sumarnu statistiku. |
| **Alternativni tokovi:** Nema zadataka → Sistem prikazuje poruku: "Nema nalog za prikaz napredka." |
| **Izuzeci:** Greška pri preuzimanju statistike → Sistem prikazuje poruku o grešci. |

---

| Primer uporabe: **NASTAVI OPOMNIK** | ID: **PU-11** |
| :--- | :--- |
| **Tip:** Proširenje (Extend) **NASTAVLJANJE ROKOV** (PU-05) |
| **Cilj:** Korisnik želi da postavi automatski opomnik na svoj rok. |
| **Akteri:** Registrovani Korisnik, Sistem |
| **Preduslovi:** Korisnik je upravo postavio rok (PU-05). |
| **Stanje sistema nakon PU:** Kreiran je opomnik u sistemu koji se aktivira pre roka. |
| **Scenario:** |
| 1. Sistem detektuje da je rok blizu ili da je nova stavka sa rokom sačuvana (pokreće se iz PU-05). |
| 2. Sistem automatski nudi opciju za **"Nastavi opomnik"**. |
| 3. Korisnik potvrdi opomnik. |
| 4. Sistem kreira sistemski opomnik za zadatu stavku. |
| **Alternativni tokovi:** Korisnik odbije opomnik → Opomnik se ne postavlja. |
| **Izuzeci:** Greška pri kreiranju opomnika → Sistem javlja da opomnik nije postavljen. |

---
---

### Akter: Admin

| Primer uporabe: **UPRAVLJANJE UPORABNIKOV** | ID: **PU-A1** |
| :--- | :--- |
| **Cilj:** Administrator želi da nadgleda i menja privilegije registrovanih korisnika. |
| **Akteri:** Admin, Sistem |
| **Preduslovi:** Admin je uspešno prijavljen u sistem. |
| **Stanje sistema nakon PU:** Podaci o korisnicima su modifikovani (npr. promena uloge, brisanje). |
| **Scenario:** |
| 1. Admin izabere opciju **"Upravljanje uporabnikov"**. |
| 2. Sistem prikazuje listu svih korisnika, njihove uloge i statuse. |
| 3. Admin izabere korisnika za editovanje/brisanje. |
| 4. Admin izvrši željenu promenu (npr. menja ulogu u "Admin" ili briše nalog). |
| 5. Sistem validira, primenjuje promene i osvežava listu. |
| **Alternativni tokovi:** Admin otkaže operaciju → Promene nisu sačuvane. |
| **Izuzeci:** Admin pokušava da obriše svoj nalog → Sistem odbija akciju: "Brisanje sopstvenog naloga ni dovoljeno." |

---

| Primer uporabe: **PREGLED STATISTIKE SISTEMA** | ID: **PU-A2** |
| :--- | :--- |
| **Cilj:** Administrator želi da vidi agregirane podatke o korišćenju aplikacije. |
| **Akteri:** Admin, Sistem |
| **Preduslovi:** Admin je prijavljen. |
| **Stanje sistema nakon PU:** Prikazani su statistički podaci; podaci u bazi ostaju nepromenjeni. |
| **Scenario:** |
| 1. Admin izabere opciju **"Pregled statistike sistema"**. |
| 2. Sistem prikuplja sumarne podatke (ukupan broj nalog, broj korisnika, procenat završenih zadataka, itd.). |
| 3. Sistem prikazuje izveštaj i/ili dijagrame statistike. |
| **Alternativni tokovi:** Prikaz statistike ne uspe → sistem prikaže obvestilo: "Podatkov ni bilo mogoče naložiti." |
| **Izuzeci:** Greška pri izračunavanju statistike (npr. neuspešna SQL poizvedba) → Prikazuje se poruka o grešci. |

---

| Primer uporabe: **DODAJANJE NOVIH KATEGORIJ** | ID: **PU-A3** |
| :--- | :--- |
| **Cilj:** Administrator želi da doda nove predefinisane kategorije za zadatke. |
| **Akteri:** Admin, Sistem |
| **Preduslovi:** Admin je prijavljen. |
| **Stanje sistema nakon PU:** U bazu je dodana nova kategorija, dostupna svim korisnicima. |
| **Scenario:** |
| 1. Admin izabere opciju **"Upravljanje kategorijami"** (uključeno u Admin kontrolni panel). |
| 2. Sistem prikazuje formu za unos nove kategorije. |
| 3. Admin unosi naziv nove kategorije (npr. "Osebni razvoj"). |
| 4. Admin potvrđuje unos. |
| 5. Sistem validira i čuva novu kategoriju. |
| **Alternativni tokovi:** Naziv kategorije već postoji → Sistem javlja grešku i traži novi naziv. |
| **Izuzeci:** Neuspešno čuvanje u bazu → Prikazuje se poruka o grešci. |

---

## DPU Diagram

<img width="2230" height="1411" alt="DPU_drugaVerzija drawio" src="https://github.com/user-attachments/assets/6edb39f6-3650-42a5-9458-ff3aafe805b6" />

## Dokumentacija za Razvijalce (Dokumentacija za Razvijalce)

Ta del je namenjen članom ekipe in bodočim razvijalcem.

### 1.1. Struktura Projekta (Opis Projektne Strukture)

Glavni projekt je razdeljen na dve pod-direktorija (Mikrostoritve):

| Direktorij/Datoteka | Vsebina in Namen |
| :--- | :--- |
| **`todo-backend/`** | Vsebuje vso **Java/Spring Boot** izvorno kodo, Dockerfile in `docker-compose.yml`. |
| **`todo-frontend/`** | Vsebuje vso **React/JavaScript** izvorno kodo (Vite). |
| `README.md` | Glavna dokumentacija in navodila. |
| `docker-compose.yml` | **Glavna konfiguracijska datoteka za Docker**, določa storitve (backend, MySQL) in omrežje. |

### 1.3. Orodja, Okvirji in Različice (Informacije o Uporabljenih Orodjih, Frameworkih in Različicah)

- **Jezik (Zaledje):** **Java 21**
- **Okvir (Zaledje):** **Spring Boot 3.x** (s Spring Data JPA in REST)
- **Jezik (Sprednji del):** **JavaScript/JSX**
- **Okvir (Sprednji del):** **React 18** (z Vite)
- **Podatkovna Baza:** **MySQL 8.0**
- **Kontejnerizacija:** **Docker** in **Docker Compose**
- **Upravljanje Odvisnosti:** **Maven (za Java)** in **npm (za Node/React)**

### 1.3. Standardi Kodiranja (Standardi Kodiranja)

- Uporabljamo standardne **CamelCase** konvencije za Java razrede in metode (`TodoController`).
- Uporabljamo **PascalCase** za React komponente (`TodoForm`, `FilterForm`).
- Uporabljena je struktura **Controller -> Service -> Repository** za ločevanje poslovne logike.
- Za formatiranje kode uporabljamo avtomatska orodja **IntelliJ IDEA/VS Code** (privzete nastavitve), da bi bila koda čitljiva.

---

## Navodila za Zagon Aplikacije (Navodila za Zagon Aplikacije)

Aplikacija je kontejnerizirana in se zažene z uporabo Docker Compose.

### Predpogoji

Pred zagonom aplikacije se prepričajte, da imate nameščeno naslednje:

- **Git**
- **Docker Desktop** (ki vključuje Docker in Docker Compose)
- **Node.js in npm** (za zagon sprednjega dela)

### Koraki

1. **Kloniranje Repozitorija:**

    ```bash
    git clone [[https://github.com/PetarKojadinovic/RIS_projekt.git](https://github.com/PetarKojadinovic/RIS_projekt.git)]
    cd RIS_PROJEKT
    ```

2. **Zagon Zalednega Sklada (MySQL in Spring Boot):**
    Ta korak prevede Java kodo, ustvari Docker slike in zažene storitvi MySQL in Spring Boot na vratih **3307** in **8080**.

    ```bash
    cd todo-backend
    docker compose up --build
    ```

    *(Pustite ta terminal odprt)*

3. **Zagon Sprednjega Dela (React):**
    Odprite nov terminal in zaženite sprednji del.
    ```bash
    cd ../todo-frontend
    npm install
    npm run dev
    ```
    Aplikacija bi se zdaj morala samodejno odpreti v brskalniku na naslovu **`http://localhost:5173`**.

---

## Funkcionalnost

Aplikacija podpira naslednje funkcionalnosti:

- **Ustvarjanje:** Dodajanje naloge z imenom in izbirnim rokom.
- **Branje:** Prikaz seznama vseh nalog.
- **Posodabljanje (Urejanje):** Popolno urejanje imena in roka obstoječe naloge.
- **Brisanje:** Odstranjevanje naloge iz baze.
- **Sprememba Statusa:** Označevanje naloge kot dokončane/nedokončane.
- **NAPREDNO FILTRIRANJE:**
    - Iskanje po ključni besedi (v imenu naloge).
    - Filtriranje po statusu (Vse, Dokončane, Nedokončane).

---

## Navodila za Razvijalce (Navodila za Prispevanje/Sodelovanje)

Naslednji koraki se nanašajo na Git potek dela znotraj vaše ekipe.

1. **Prenos Najnovejših Spremem:**

    ```bash
    git pull origin main
    ```

2. **Ustvarjanje Nove Veje (Branch):**

    ```bash
    git checkout -b feature/ime-funkcionalnosti
    ```

3. **Commits in Potisk (Push):**

    ```bash
    git add .
    git commit -m "feat: Dodana validacija uporabniškega vnosa"
    git push origin feature/ime-funkcionalnosti
    ```

4. **Ustvarjanje Pull Requesta (PR):**
    - Na GitHubu ustvarite **Pull Request** (Zahtevek za združitev) iz vaše veje (`feature/...`) v glavno vejo (`main`).
    - Počakajte, da **vsaj en član ekipe/asistent pregleda (review)** vašo kodo in odobri združitev.