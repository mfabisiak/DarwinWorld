# Darwin World

## Wariant: Szybkie zwierzaki

Autorzy: Mateusz Fabisiak, Maciej Bednarczyk


## 🌍 Platformy i Uruchamianie:

1. JVM: ✅ Pełna funkcjonalność.
2. WebAssembly: ✅ Pełna funkcjonalność. 
3. Android: ⚠️ Aplikacja działa, z wyjątkiem wczytywania konfiguracji z pliku .

## 🚀 Zrealizowane Rozszerzenia:

W projekcie zaimplementowano szereg dodatkowych funkcjonalności wykraczających poza podstawową specyfikację:

1.  **Zapis i odczyt konfiguracji:** Możliwość definiowania parametrów symulacji, zapisywania ich do pliku (JSON) i ponownego wczytywania.
2.  **Śledzenie wybranego zwierzaka:** Interaktywność mapy – po kliknięciu w zwierzaka, w panelu bocznym wyświetlane są jego szczegółowe dane (genom, poziom energii, wiek, liczba dzieci, liczba potomków, liczba zjedzonych roślin, status życia/śmierci).
3.  **Wyróżnianie dominującego genotypu:** Zwierzaki posiadające najpopularniejszy genotyp są wyróżnione na mapie na żółto.
4.  **Zapis statystyk do pliku (CSV):** Aplikacja umożliwia eksportowanie statystyk z każdego dnia symulacji do pliku `.csv`.
5.  **Podróż w czasie:** Zaimplementowano bufor historii, który pozwala nie tylko zatrzymać symulację, ale również cofać się do poprzednich dni (`Poprzedni`) oraz przewijać symulację krok po kroku (`Następny`).
6.  **Skalowanie widoku:** Siatka mapy oraz wielkość obiektów dostosowują się dynamicznie do rozmiaru okna aplikacji i zdefiniowanych wymiarów świata.
7.  **Wizualizowanie energii** Na mapie w trakcie trwania symulacji, nad każdym zwierzakiem znajduje się pasek energii w jednym z 5 kolorów, który oznacza ilość pozostałej energii.
8.  **Uruchamianie wielu symulacji** Aplikacja umożliwia uruchamianie symulacji w wielu okienkach.
9.  **Własne rozszerzenie**: Wieloplatformowość (JVM, WebAssembly, Android).
   
## 🐆 Wariant Specjalny: Szybkie Zwierzaki:

### Zasada działania
W konfiguracji można włączyć tryb, w którym zwierzaki posiadające odpowiedni zapas energii poruszają się o więcej niż jedno pole na turę.
* Zasięg ruchu zależy od nadmiaru energii (powyżej kosztu podstawowego) oraz parametru `maxRange`.
* Koszt energetyczny rośnie wraz z przebytym dystansem.

### Mechanika Kolizji
Podczas szybkiego ruchu może dojść do kolizji z innym zwierzakiem. Jeśli na drodze rozpędzonego osobnika stoi inny zwierzak:
1.  Agresor zatrzymuje się na polu na którym nastąpiła kolizja.
2.  Agresor traci podwójnie energię.
3.  W naszej symulacji przyjęliśmy model ruchu w którym wszystkie zwierzaki zaczynają ruch jednocześnie ( brak aktualizacji occupiedPositions po ruchu każdego zwierzaka ). Do kolizji brana jest pod uwagę ich pozycja przed ruchem. Eliminuje to problem ustalenia kolejności, w której miałyby się poruszać zwierzaki, gdyby było to sekwencyjnie.

* **Dostępna online:** [Uruchom symulację w przeglądarce](https://mfabisiak.github.io/PO_2025_PT1315_Fabisiak_Bednarczyk/wasmJs/productionExecutable/)

