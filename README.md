# Sistem de Gestiune a Stocurilor (Java Desktop App)

Acest proiect este o aplicație desktop dezvoltată în **Java (Swing)** pentru gestionarea inventarului unui depozit. A fost realizat pentru a aplica în mod practic principiile Programării Orientate pe Obiecte (OOP), arhitectura MVC și integrarea cu o bază de date relațională.

## Arhitectură și Concepte OOP
Aplicația respectă principiile de design software curat:
* **Model-View-Controller (MVC):** Separarea clară între interfața grafică (`DepozitView`), logica de business (`DepozitController`) și persistența datelor.
* **Moștenire și Polimorfism:** Produsele sunt modelate printr-o ierarhie de clase (clasa de bază `Produs` extinsă de `Mobila`, `Electronic`, `Consumabil`).
* **Interfețe (Contracts):** Folosirea interfețelor precum `Cautabil` și `Stocabil` pentru decuplarea componentelor.
* **Tratarea Excepțiilor:** Excepții custom (ex: `StocInsuficientException`) pentru a preveni erorile la tranzacțiile de inventar.

## Funcționalități Principale
* **Gestiunea Produselor:** Adăugarea, căutarea și eliminarea produselor din depozit pe diverse categorii.
* **Persistența Datelor:** Conexiune la o bază de date **PostgreSQL** prin JDBC (`DataBaseManager`).
* **Automatizare SQL:** Structura bazei de date folosește **Triggere** și funcții (`actualizeaza_stoc()`) pentru a calcula automat cantitățile (IN/OUT) la fiecare tranzacție, direct la nivel de backend.

## Tehnologii Folosite
* **Limbaj:** Java (Swing GUI)
* **Concepte:** OOP, MVC, Custom Exceptions
* **Bază de date:** PostgreSQL (JDBC, Triggers, Functions)

## Structura Proiectului
* `src/` - Conține codul sursă Java organizat modular.
* `baza_de_date.sql` - Scriptul SQL complet pentru recrearea tabelelor, a relațiilor (Foreign Keys) și a triggerelor automate.
