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

| Naša aplikacija             | Papirnati listi | Preproste ToDo app |
| --------------------------- | --------------- | ------------------ |
| Iskanje po ključnih besedah | Ne              | Omejeno            |
| Filtriranje po statusu      | Ne              | Da                 |
| Varno shranjevanje          | Ne              | Da                 |
| Urejanje nalog              | Ne              | Da                 |
| Skalabilna arhitektura      | Ne              | Ne                 |

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

## 📝 Besednjak (Slovar Ključnih Izrazov)

V tej tabeli so razloženi ključni izrazi, ki se uporabljajo v arhitekturi in kodi projekta ter so neposredno povezani z reševanjem problema organizacije nalog, kot je določeno v Viziji.

| Izraz (Termin)                     | Povezava z Vizijo Projekta                                 | Razlaga v Kontekstu Aplikacije                                                                                                                           |
| :--------------------------------- | :--------------------------------------------------------- | :------------------------------------------------------------------------------------------------------------------------------------------------------- |
| **Full-Stack**                     | **Tehnološka Prednost** (Popolna rešitev)                  | Aplikacija, ki obsega tako uporabniški vmesnik (React Frontend) kot strežniško logiko in podatkovno bazo (Spring Boot Backend).                          |
| **Kontejnerizacija**               | **Tehnološka Prednost** (Enostavna postavitev)             | Postopek pakiranja aplikacije in njene baze podatkov (MySQL) v izolirane in prenosljive Docker kontejnerje.                                              |
| **Entitet _Todo_**                 | **Namen Aplikacije** (Osnovna enota dela)                  | Objekt, ki predstavlja eno samo opravilo. Vsebuje polja **`name`**, **`completed`** in **`dueDate`**.                                                    |
| **DueDate (Rok)**                  | **Prihodnji Cilj** (Upravljanje z roki)                    | Polje tipa `LocalDate` v entitetu `Todo`, ki omogoča uporabniku, da **nastavi roke** za naloge, kar je ključno za zmanjšanje stresa.                     |
| **Query Parameter**                | **Problem, ki ga Rešujemo** (Napredno iskanje/filtriranje) | Del URL-ja (`/api/todos?search=posel`), ki se uporablja za pošiljanje pogojev za **filtriranje** in **iskanje** podatkov na backend.                     |
| **Soritveni Sloj (Service Layer)** | **Tehnološka Prednost** (Profesionalni pristop)            | Sloj v Spring Bootu, kjer se izvaja kompleksna **poslovna logika** – vključno z logiko za napredno iskanje in filtriranje, ki je izpostavljena v Viziji. |
| **RESTful API**                    | **Tehnološka Prednost** (Prihodnje razširitve)             | Sklop pravil za komunikacijo med frontendom in backendom, ki omogoča enostaven razvoj **Mobilne aplikacije** in drugih integracij v prihodnosti.         |
| **Toggle**                         | **Kaj Želimo Doseči** (Enostavno označevanje)              | Operacija, ki preklopi logično stanje naloge (`completed`) z enega klika, kar omogoča hiter in pregleden pregled dela.                                   |
| **CRUD**                           | **Kaj Želimo Doseči** (Osnovno upravljanje)                | Standardne operacije (Ustvari, Beri, Posodobi, Izbriši), ki uporabniku omogočajo, da naloge **ureja ali izbriše brez težav**.                            |
| **Repozitorij (Repository)**       | **Učna Vrednost** (Delo z bazama)                          | Vmesnik, ki skrbi za varno in direktno komunikaciju z **MySQL podatkovno bazo** (podatki so varno shranjeni).                                            |
| **Frontend**                       | **Problem, ki ga Rešujemo** (Pregleden vmesnik)            | React aplikacija, ki služi kot **pregleden in hiter vmesnik** za uporabnika.                                                                             |
| **Backend**                        | **Tehnološka Prednost** (Arhitektura mikrostoritev)        | Spring Boot aplikacija, ki skrbi za API logiku i podatkovni dostop, in predstavlja srce **mikrostoritvene arhitekture**.                                 |
|                                    |

| **Primer uporabe:** Pregled napredka uporabnika                                                                        | **ID:** PU-PN1 |
| ---------------------------------------------------------------------------------------------------------------------- | -------------- |
| **Cilj:** Uporabnik želi pridobiti pregled nad svojim napredkom – koliko nalog je opravljenih in koliko jih še ostaja. |
| **Akterji:** Registrirani uporabnik, sistem                                                                            |
| **Predpogoji:** Uporabnik mora biti prijavljen in mora imeti vsaj eno ustvarjeno nalogo.                               |
| **Stanje sistema po PU:** Sistem ostane nespremenjen; prikaže se le izračun statistike.                                |
| **Scenarij:**                                                                                                          |
| 1. Uporabnik v meniju izbere »Pregled napredka«.                                                                       |
| 2. Sistem izračuna število opravljenih in neopravljenih nalog.                                                         |
| 3. Sistem prikaže statistiko in grafični prikaz napredka.                                                              |
| 4. Uporabnik lahko klikne na neopravljen nalog.                                                                        |
| 5. Sistem ponudi možnost »Označi kot opravljeno« (extend PU-PN2).                                                      |
| **Alternativni tokovi:** Če uporabnik nima nalog, sistem prikaže obvestilo: »Ni nalog za prikaz napredka.«             |
| **Izjeme:** Napaka pri izračunu statistike – sistem prikaže sporočilo »Napaka pri pridobivanju podatkov.«              |

---

| **Primer uporabe:** Označi nalogo kot opravljeno                                                             | **ID:** PU-PN2 |
| ------------------------------------------------------------------------------------------------------------ | -------------- |
| **Cilj:** Uporabnik želi iz pogleda napredka označiti nalogo kot opravljeno.                                 |
| **Akterji:** Registrirani uporabnik, sistem                                                                  |
| **Predpogoji:** Naloga mora biti v statusu »neopravljeno«. Uporabnik mora biti v pogledu »Pregled napredka«. |
| **Stanje sistema po PU:** Status naloge se spremeni v »opravljeno«, statistika napredka se posodobi.         |
| **Scenarij:**                                                                                                |
| 1. Uporabnik klikne na neopravljen nalog v pregledu napredka.                                                |
| 2. Sistem prikaže možnost »Označi kot opravljeno«.                                                           |
| 3. Uporabnik potrdi možnost.                                                                                 |
| 4. Sistem posodobi status naloge.                                                                            |
| 5. Sistem ponovno izračuna napredek in osveži prikaz.                                                        |
| **Alternativni tokovi:** Uporabnik prekliče opravilo – sistem ne izvede sprememb.                            |
| **Izjeme:** Sistem ne uspe posodobiti naloge in prikaže sporočilo »Posodobitev ni uspela.«                   |

---

## DPU Diagram

<img width="2230" height="1411" alt="DPU_drugaVerzija" src="https://github.com/user-attachments/assets/9d859375-96c2-4c6c-910a-90135af59d9d" />


## Dokumentacija za Razvijalce (Dokumentacija za Razvijalce)

Ta del je namenjen članom ekipe in bodočim razvijalcem.

### 1.1. Struktura Projekta (Opis Projektne Strukture)

Glavni projekt je razdeljen na dve pod-direktorija (Mikrostoritve):

| Direktorij/Datoteka  | Vsebina in Namen                                                                            |
| :------------------- | :------------------------------------------------------------------------------------------ |
| **`todo-backend/`**  | Vsebuje vso **Java/Spring Boot** izvorno kodo, Dockerfile in `docker-compose.yml`.          |
| **`todo-frontend/`** | Vsebuje vso **React/JavaScript** izvorno kodo (Vite).                                       |
| `README.md`          | Glavna dokumentacija in navodila.                                                           |
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
   git clone [https://github.com/PetarKojadinovic/RIS_projekt.git]
   cd RIS_PROJEKT
   ```

2. **Zagon Zalednega Sklada (MySQL in Spring Boot):**
   Ta korak prevede Java kodo, ustvari Docker slike in zažene storitvi MySQL in Spring Boot na vratih **3307** in **8080**.

   ```bash
   cd todo-backend
   docker compose up --build
   ```

   _(Pustite ta terminal odprt)_

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
