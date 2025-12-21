# 📊 Poročilo o Scrum Implementaciji - Sprint 1

## 🎯 User Story

**Izbrana Uporabniška Zgodba:**
> _"Kot uporabnik želim videti analizo svog produktivnog vremena (prosečno trajanje zadataka, procenat završenih zadataka u određenom vremenskom periodu), da mogu bolje upravljati svojim radom."_

**Vrednost za Uporabnika:**
- Boljši pregled nad produktivnostjo
- Razumevanje razdelitve nalog po kategorijah in prioritetah
- Motivacija za dokončanje nalog skozi vizualizacijo napredka

---

## 👥 Člani Ekipe in Vloge

| Član Ekipe | Vloga | Odgovornost |
|:-----------|:------|:------------|
| **Petar Kojadinović** | Scrum Master / Backend Developer | Koordinacija ekipe, Backend API, Statistika |
| **Boris Sajlović** | Backend Developer / Tester | Backend Unit Testi (UserService) |
| **Filip Sekulović** | Frontend Developer / Tester | Frontend Dashboard, Testiranje |

---

## 📅 Časovnica Sprinta

- **Začetek Sprinta:** 16. december 2024
- **Konec Sprinta:** 21. december 2024
- **Trajanje:** 5 dni (1 teden)
- **Zagovor:** 22. december 2024

---

## 📋 Razdelitev Uporabniške Zgodbe na Naloge

### **TASK-1: Kreiranje Enuma za Kategorije in Prioritete**
- **Story Points:** 3 SP
- **Procenjeno Vreme:** 1 ura
- **Odgovoren:** Petar Kojadinović
- **Status:** ✅ DOKONČANO

**Acceptance Criteria:**
- [x] Kreiran `Category.java` enum (WORK, PERSONAL, SHOPPING, HEALTH, EDUCATION, OTHER)
- [x] Kreiran `Priority.java` enum (LOW, MEDIUM, HIGH)
- [x] Dodano polje `category` v `Todo.java`
- [x] Dodano polje `priority` v `Todo.java`
- [x] Dodano polje `description` v `Todo.java`
- [x] Testirana migracija baze podatkov

**Rezultat:**
- Uspešno dodani enum tipi za kategorizacijo nalog
- Hibernate avtomatsko kreiral nove kolone v tabeli `todos`
- Backend kompiliran brez napak

---

### **TASK-2: Backend - REST API za Statistiku**
- **Story Points:** 8 SP
- **Procenjeno Vreme:** 3 ure
- **Odgovoren:** Petar Kojadinović
- **Status:** ✅ DOKONČANO

**Acceptance Criteria:**
- [x] Kreiran `TodoStatisticsDTO.java`
- [x] Implementirane metode v `TodoRepository` (countByCategory, countByPriority)
- [x] Implementirana logika v `TodoService.getStatistics()`
- [x] Kreiran `GET /api/todos/statistics` endpoint
- [x] Dodana endpointa `/categories` in `/priorities`
- [x] Testiran API v Postman/Browser

**Rezultat:**
- API vrača JSON s celotno statistiko
- Dodana globalna CORS konfiguracija (`WebConfig.java`)
- Vsi endpointi dostopni preko REST API-ja

**Primer API Odgovora:**
```json
{
  "totalTasks": 15,
  "completedTasks": 9,
  "pendingTasks": 6,
  "completionPercentage": 60.0,
  "tasksByCategory": {
    "WORK": 7,
    "PERSONAL": 5,
    "SHOPPING": 3
  },
  "tasksByPriority": {
    "HIGH": 4,
    "MEDIUM": 8,
    "LOW": 3
  },
  "overdueTasks": 2,
  "tasksWithoutDueDate": 5
}
```

---

### **TASK-3: Backend - Unit Testi**
- **Story Points:** 5 SP
- **Procenjeno Vreme:** 2 uri
- **Odgovoren:** Boris Sajlović
- **Status:** ✅ DOKONČANO

**Acceptance Criteria:**
- [x] Test za osnovno statistiko (total, completed, pending)
- [x] Test za statistiko po kategorijah
- [x] Test za statistiko po prioritetah
- [x] Test za prazno bazo podatkov
- [x] Vsi testi uspešno pretečejo (`mvn test`)

**Rezultat:**
- Kreiran `TodoServiceTest.java` z 6 unit testi
- Pokritost kode: ~75% za `TodoService`
- Vsi testi uspešno pretekli (6/6 PASS)
- Odkriti varnostni problemi dokumentirani v testiranju

**Statistika Testiranja:**
```
Tests run: 6, Failures: 0, Errors: 0, Skipped: 0
Time elapsed: 0.834 s
[INFO] BUILD SUCCESS
```

---

### **TASK-4: Frontend - Statistics Dashboard**
- **Story Points:** 8 SP
- **Procenjeno Vreme:** 3 ure
- **Odgovoren:** Filip Sekulović
- **Status:** ✅ DOKONČANO

**Acceptance Criteria:**
- [x] Kreirana `StatCard.jsx` komponenta (reusable kartica)
- [x] Kreirana `StatsDashboard.jsx` komponenta (glavni dashboard)
- [x] Dodani CSS stili (`StatsDashboard.css`)
- [x] Prikazuje Total/Completed/Pending/Completion Rate
- [x] Prikazuje statistiko po kategorijah
- [x] Prikazuje statistiko po prioritetah
- [x] Dodani motivacijski sporočili glede na napredek

**Rezultat:**
- Moderni, responziven dashboard z animacijami
- Vizualno privlačen prikaz statistike
- Loading in error state pravilno obravnavani

---

### **TASK-5: Frontend - API Integracija**
- **Story Points:** 3 SP
- **Procenjeno Vreme:** 1 ura
- **Odgovoren:** Filip Sekulović
- **Status:** ✅ DOKONČANO

**Acceptance Criteria:**
- [x] Dodana `getStatistics()` funkcija v `todoService.js`
- [x] Integrisana `StatsDashboard` komponenta v `App.jsx`
- [x] Dodano dugme za toggle med Tasks/Statistics
- [x] API pozivi delujejo pravilno
- [x] Error handling implementiran

**Rezultat:**
- Frontend uspešno komunicira z backend API-jem
- Toggle med seznamom nalog in statistiko deluje
- Automatski refresh statistike po spremembi nalog

---

### **TASK-6: Dokumentacija in Finalizacija**
- **Story Points:** 5 SP
- **Procenjeno Vreme:** 2 uri
- **Odgovoren:** Vsi člani ekipe
- **Status:** ✅ DOKONČANO

**Acceptance Criteria:**
- [x] Kreiran folder `implementacija/`
- [x] Napisan `porocilo_scrum.md`
- [x] Ažuriran `README.md` z novimi funkcionalnostmi
- [x] Dodani screenshot-i aplikacije
- [x] Vsi GitHub Issues zaprti

---

## 📊 Planning Poker - Ocenjevanje Nalog

### Metoda Ocenjevanja
Uporabljali smo **Fibonacci zaporedje** za Story Points:
- **1 SP** = Zelo enostavno (< 30 min)
- **2 SP** = Enostavno (30 min - 1 h)
- **3 SP** = Srednje (1-2 h)
- **5 SP** = Kompleksno (2-4 h)
- **8 SP** = Zelo kompleksno (4-6 h)
- **13 SP** = Ekstremno kompleksno (cel dan)

### Proces Planning Poker
1. **Product Owner** (asistent) predstavi User Story
2. **Člani ekipe** diskutirajo o kompleksnosti
3. **Sočasno glasovanje** - vsak član izbere število
4. **Diskusija** pri odstopanjih
5. **Konsenzus** - dogovor o končnem številu Story Points

### Rezultati Planning Poker Sesije

| Task | Petar | Boris | Filip | Konsenzus | Dejansko Vreme |
|:-----|:------|:------|:------|:----------|:---------------|
| TASK-1 | 3 SP | 3 SP | 3 SP | **3 SP** | ~1h ✅ |
| TASK-2 | 8 SP | 8 SP | 5 SP | **8 SP** | ~3h ✅ |
| TASK-3 | 5 SP | 5 SP | 5 SP | **5 SP** | ~2h ✅ |
| TASK-4 | 8 SP | 8 SP | 8 SP | **8 SP** | ~3h ✅ |
| TASK-5 | 3 SP | 2 SP | 3 SP | **3 SP** | ~1h ✅ |
| TASK-6 | 5 SP | 5 SP | 5 SP | **5 SP** | ~2h ✅ |
| **SKUPAJ** | | | | **32 SP** | **~12h** |

**Velocity:** 32 Story Points / 5 dni = **6.4 SP na dan**

---

## 🔄 Scrum Dogodki

### Daily Standup (Vsak Dan)
**Format:** 15 minut preko Discord-a

**Vprašanja:**
1. Kaj sem naredil včeraj?
2. Kaj bom naredil danes?
3. Ali imam kakšne ovire?

**Primer Standup-a (18. december):**

**Petar:**
- Včeraj: TASK-1 dokončan (enumi)
- Danes: TASK-2 (REST API)
- Ovire: Nobenih

**Boris:**
- Včeraj: Postavitev testing okolja
- Danes: TASK-3 (unit testi)
- Ovire: Težave z Mockito knjižnico (rešeno)

**Filip:**
- Včeraj: Design mockup za dashboard
- Danes: TASK-4 (frontend komponente)
- Ovire: Nobenih

---

### Sprint Planning (16. december)
- **Trajanje:** 2 uri
- **Rezultat:** Razdelitev nalog, Planning Poker
- **Sprint Goal:** Implementirati analizo produktivnosti z vizualizacijo statistike

---

### Sprint Review (21. december)
- **Trajanje:** 1 ura
- **Udeleženec:** Asistent (Product Owner)
- **Demo:** Prikaz delujoče aplikacije
- **Feedback:** Pozitiven, predlogi za nadaljnje izboljšave

---

### Sprint Retrospective (21. december)
**Što je šlo dobro? ✅**
- Odlična timska komunikacija
- Planning Poker precizne ocene
- Vsi task-i dokončani pravočasno
- Git workflow brez konfliktov

**Što bi lahko bilo boljše? 🔧**
- CORS problemi vzeli več časa kot pričakovano
- Unit testi bi lahko bili bolj obsežni
- Frontend komponente bi lahko bile bolj reusable

**Action Items za naslednji Sprint:**
- Dodati integracijsko testiranje
- Izboljšati error handling
- Implementirati authentikacijo uporabnikov

---

## 📈 GitHub Project Board - Napredek

### Struktura Tablice
```
┌─────────────────────┬─────────────────────┬─────────────────────┐
│      📝 To Do       │   🔨 In Progress    │      ✅ Done        │
├─────────────────────┼─────────────────────┼─────────────────────┤
│                     │                     │ [TASK-1] Enumi      │
│                     │                     │ [TASK-2] REST API   │
│                     │                     │ [TASK-3] Testi      │
│                     │                     │ [TASK-4] Dashboard  │
│                     │                     │ [TASK-5] API Int.   │
│                     │                     │ [TASK-6] Dokument.  │
└─────────────────────┴─────────────────────┴─────────────────────┘
```

### Korištenje GitHub Project Board-a
- **Issues** povezani sa task-ovima
- **Labels** za kategorizaciju (backend, frontend, testing, documentation)
- **Assignees** za odgovornost
- **Comments** za diskusiju i ažuriranja
- **Pull Requests** povezani sa issues (npr. "Closes #1")

---

## 🔀 Git Workflow

### Branch Strategija
```
main (produkcija)
  ├── feature/task-1-enums
  ├── feature/task-2-statistics-api
  ├── feature/task-3-unit-tests
  ├── feature/task-4-dashboard
  ├── feature/task-5-api-integration
  └── feature/task-6-documentation
```

### Commit Konvencija
```
[TASK-X] Kratek opis

✅ Bullet point 1
✅ Bullet point 2

Story Points: X SP | Čas: Xh
Closes #X
```

**Primer:**
```
[TASK-2] Implementiran REST API endpoint za statistiku nalog

✅ Kreiran TodoStatisticsDTO.java
✅ Dodane metode v TodoRepository
✅ Implementirana getStatistics() metoda
✅ Testiran API - deluje

Story Points: 8 SP | Čas: 3h
Closes #2
```

---

## 📊 Metriki Sprinta

### Burn-down Chart (Tekstualna Reprezentacija)
```
Story Points
32 │ ●
30 │   ╲
28 │     ●
26 │       ╲
24 │         ●
22 │           ╲
20 │             ●
18 │               ╲
16 │                 ●
14 │                   ╲
12 │                     ●
10 │                       ╲
 8 │                         ●
 6 │                           ╲
 4 │                             ●
 2 │                               ╲
 0 │                                 ●
   └─────────────────────────────────────
   Day 1  Day 2  Day 3  Day 4  Day 5
```

**Opažanja:**
- Konstantna hitrost dokončevanja nalog
- Nobenih velikih odstopanj od načrta
- Sprint Goal dosežen

---

### Velocity Trend
| Sprint | Story Points | Dokončano | Velocity |
|:-------|:-------------|:----------|:---------|
| **Sprint 1** | 32 SP | 32 SP | **100%** |

---

## 🎯 Doseđeni Cilji

### Funkcionalni Zahtevi
- ✅ **Statistika nalog** - Celotna analiza produktivnosti
- ✅ **Kategorije in prioriteti** - Organizacija nalog
- ✅ **Vizualizacija** - Privlačen dashboard
- ✅ **API endpointi** - REST API za frontend
- ✅ **Unit testi** - Kvalitetno testiranje

### Nefunkcionalni Zahtevi
- ✅ **Responzivnost** - Deluje na vseh napravah
- ✅ **Performanca** - Hitro nalaganje (<1s)
- ✅ **Uporabniška Izkušnja** - Intuitiven vmesnik
- ✅ **Vzdrževalnost** - Čist, dokumentiran kod
- ✅ **Razširljivost** - Enostavno dodajanje novih funkcij

---

## 🐛 Odkrite Težave in Rešitve

### Težava 1: CORS Policy Errors
**Opis:** Frontend ni mogel dostopati do backend API-ja zaradi CORS policy.

**Rešitev:**
- Dodana globalna CORS konfiguracija (`WebConfig.java`)
- Dodana `@CrossOrigin` anotacija na controller
- Dodani allowed origins v `application.properties`

**Čas Rešitve:** 1 ura

---

### Težava 2: Hibernate Enum Mapping
**Opis:** Enum vrednosti se niso pravilno shranjevale v bazo.

**Rešitev:**
- Uporabljeni `@Enumerated(EnumType.STRING)` namesto `EnumType.ORDINAL`
- Dodani default vrednosti (OTHER, MEDIUM)

**Čas Rešitve:** 30 minut

---

### Težava 3: Frontend State Management
**Opis:** Statistika se ni ažurirala po dodajanju/brisanju nalog.

**Rešitev:**
- Dodan `refreshKey` state variable
- Ažuriranje `refreshKey` po vsaki CRUD operaciji
- UseEffect hook posluša spremembe `refreshKey`

**Čas Rešitve:** 45 minut

---

## 📚 Naučene Lekcije

### Tehnične Lekcije
1. **CORS Configuration** - Razumevanje kako Spring Boot obravnava CORS
2. **JPA Enum Mapping** - Pravilna uporaba `@Enumerated` anotacije
3. **React State Management** - Kako upravljati z odvisnimi state-i
4. **Planning Poker** - Kako ocenjevati kompleksnost nalog

### Procesne Lekcije
1. **Daily Standups** - Ohranjanje komunikacije v ekipi
2. **Task Dekompozicija** - Razdelitev velikih nalog na manjše
3. **Git Workflow** - Uporaba branch-ov in pull request-ov
4. **Dokumentacija** - Pomembnost sprotnega dokumentiranja

---

## 🚀 Prihodnje Izboljšave (Backlog)

### Sprint 2 (Naslednji Teden)
- [ ] **Autentikacija uporabnikov** - Login/Register funkcionalnost
- [ ] **Filtriranje po datumu** - Prikaz nalog za določen časovni obdobje
- [ ] **Export v PDF** - Možnost izvoza statistike
- [ ] **Dark Mode** - Tema za nočno delo

### Sprint 3 (Naslednji Mesec)
- [ ] **Notifikacije** - Email opomniki za roke
- [ ] **Deljenje nalog** - Sodelovanje med uporabniki
- [ ] **Mobilna aplikacija** - React Native verzija
- [ ] **AI priporočila** - Predlogi za prioritizacijo

---

## 📸 Screenshot-i Aplikacije

### 1. Glavni Seznam Nalog
![TODO List](../docs/screenshots/todo-list.png)

**Funkcionalnosti:**
- Prikaz vseh nalog z imenom, rokom, kategorijo, prioriteto
- Checkbox za označevanje dokonč anih
- Gumbi za urejanje in brisanje
- Filtriranje po statusu in iskanje

---

### 2. Dodajanje Nove Naloge
![Add TODO Form](../docs/screenshots/add-todo-form.png)

**Funkcionalnosti:**
- Vnosno polje za ime naloge
- Date picker za rok
- Dropdown za kategorijo (WORK, PERSONAL, itd.)
- Dropdown za prioriteto (HIGH, MEDIUM, LOW)
- Gumb za dodajanje

---

### 3. Statistics Dashboard
![Statistics Dashboard](../docs/screenshots/statistics-dashboard.png)

**Funkcionalnosti:**
- 4 statistične kartice (Total, Completed, Pending, Completion %)
- Razdelitev po kategorijah (graf)
- Razdelitev po prioritetah (graf)
- Opozorila za pretečene naloge
- Motivacijska sporočila

---

### 4. API Response (Postman)
![API Statistics](../docs/screenshots/api-statistics.png)

**Endpoint:** `GET /api/todos/statistics`

**Response:**
```json
{
  "totalTasks": 15,
  "completedTasks": 9,
  "pendingTasks": 6,
  "completionPercentage": 60.0,
  "tasksByCategory": {...},
  "tasksByPriority": {...}
}
```

---

## ✅ Zaključek

### Uspešnost Sprinta
Sprint je bil **izjemno uspešen**. Vsi načrtovani task-i (6/6) so bili dokončani v predvidenem času. Ekipa je dobro sodelovala, Planning Poker ocene so bile precizne, in kvaliteta kode je visoka.

### Dosežen Sprint Goal
✅ **"Implementirati analizo produktivnosti z vizualizacijo statistike"** - DOSEŽENO

### Zahvale
Zahvaljujemo se asistentu za podporo in feedback, ter vsem članom ekipe za trd delo in predanost projektu.

---

## 📅 Naslednji Koraki

1. **Zagovor** - Predstavitev projekta na vajah (22. december 2024)
2. **Sprint 2 Planning** - Načrtovanje naslednjih funkcionalnosti
3. **Deployment** - Postavitev aplikacije na Heroku/AWS
4. **User Testing** - Testiranje s pravimi uporabniki

---

**Datum:** 21. december 2024  
**Sprint:** Sprint 1 - Analiza Produktivnosti  
**Status:** ✅ DOKONČANO  
**Pripravil:** RIS_PROJEKT Ekipa (Petar, Boris, Filip)

---

_Konec Poročila_