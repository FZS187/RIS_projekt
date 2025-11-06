# RIS_PROJEKT (Aplikacija za opravljanje nalog)

**Kratek Opis:** Celovita full-stack aplikacija za upravljanje nalog (To-Do List), razvita z arhitekturo mikrostoritev. Aplikacija omogoča operacije **CRUD** (Ustvarjanje, Branje, Posodabljanje, Brisanje), z dodano podporo za **ročne datume** in **filtriranje/iskanje** po ključnih besedah in statusu. Celotna rešitev je kontejnerizirana z uporabo **Docker Compose**.

***

##  Dokumentacija za Razvijalce (Dokumentacija za Razvijalce)

Ta del je namenjen članom ekipe in bodočim razvijalcem.

### 1.1. Struktura Projekta (Opis Projektne Strukture)

Glavni projekt je razdeljen na dve pod-direktorija (Mikrostoritve):

| Direktorij/Datoteka | Vsebina in Namen |
| :--- | :--- |
| **`todo-backend/`** | Vsebuje vso **Java/Spring Boot** izvorno kodo, Dockerfile in `docker-compose.yml`. |
| **`todo-frontend/`** | Vsebuje vso **React/JavaScript** izvorno kodo (Vite). |
| `README.md` | Glavna dokumentacija in navodila. |
| `docker-compose.yml` | **Glavna konfiguracijska datoteka za Docker**, določa storitve (backend, MySQL) in omrežje. |

### 1.2. Orodja, Okvirji in Različice (Informacije o Uporabljenih Orodjih, Frameworkih in Različicah)

* **Jezik (Zaledje):** **Java 21**
* **Okvir (Zaledje):** **Spring Boot 3.x** (s Spring Data JPA in REST)
* **Jezik (Sprednji del):** **JavaScript/JSX**
* **Okvir (Sprednji del):** **React 18** (z Vite)
* **Podatkovna Baza:** **MySQL 8.0**
* **Kontejnerizacija:** **Docker** in **Docker Compose**
* **Upravljanje Odvisnosti:** **Maven (za Java)** in **npm (za Node/React)**

### 1.3. Standardi Kodiranja (Standardi Kodiranja)

* Uporabljamo standardne **CamelCase** konvencije za Java razrede in metode (`TodoController`).
* Uporabljamo **PascalCase** za React komponente (`TodoForm`, `FilterForm`).
* Uporabljena je struktura **Controller -> Service -> Repository** za ločevanje poslovne logike.
* Za formatiranje kode uporabljamo avtomatska orodja **IntelliJ IDEA/VS Code** (privzete nastavitve), da bi bila koda čitljiva.

***

##  Navodila za Zagon Aplikacije (Navodila za Zagon Aplikacije)

Aplikacija je kontejnerizirana in se zažene z uporabo Docker Compose.

### Predpogoji

Pred zagonom aplikacije se prepričajte, da imate nameščeno naslednje:

* **Git**
* **Docker Desktop** (ki vključuje Docker in Docker Compose)
* **Node.js in npm** (za zagon sprednjega dela)

### Koraki

1. **Kloniranje Repozitorija:**
    ```bash
    git clone [LINK VAŠEG REPOZITORIJA]
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

***

##  Funkcionalnost

Aplikacija podpira naslednje funkcionalnosti:

* **Ustvarjanje:** Dodajanje naloge z imenom in izbirnim rokom.
* **Branje:** Prikaz seznama vseh nalog.
* **Posodabljanje (Urejanje):** Popolno urejanje imena in roka obstoječe naloge.
* **Brisanje:** Odstranjevanje naloge iz baze.
* **Sprememba Statusa:** Označevanje naloge kot dokončane/nedokončane.
* **NAPREDNO FILTRIRANJE:**
    * Iskanje po ključni besedi (v imenu naloge).
    * Filtriranje po statusu (Vse, Dokončane, Nedokončane).

***

##  Navodila za Razvijalce (Navodila za Prispevanje/Sodelovanje)

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
    * Na GitHubu ustvarite **Pull Request** (Zahtevek za združitev) iz vaše veje (`feature/...`) v glavno vejo (`main`).
    * Počakajte, da **vsaj en član ekipe/asistent pregleda (review)** vašo kodo in odobri združitev.