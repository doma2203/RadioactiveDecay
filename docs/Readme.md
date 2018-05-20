# RadioactiveDecay
Biblioteka `RadioactiveDecay` jest biblioteką Javy, która dostarcza API pozwalające na  przeprowadzenie symulacji rozpadu promieniotwórczego atomów najpopularniejszych izotopów promieniotwórczych.
## Przeprowadzenie symulacji
Proces przeprowadzenia symulacji składa się z trzech etapów:

### Utworzenie eksperymentu
Utworzenie eksperymentu polega na utworzeniu obiektu `Experiment`, który może przyjmować dwa rodzaje parametrów:

a) Próbkę (zbiór atomów)

```java
Experiment ex = new Experiment(100, new Sample(22,atom));
```

W przypadku podania próbki `Sample` jako drugi parametr, pierwszym parametrem jest czas trwania eksperymentu.


b) Pojedynczy atom

```java
       Experiment ex = new Experiment(100,40,
                       new Atom(ChemicalElement.Uran,"halflife",32.0f));
```

Jeśli chcemy stworzyć eksperyment na podstawie pojedynczego atomu, pierwszy parametr pozostaje niezmienny, drugim jest ilość atomów, natomiast trzecim - wzorcowy atom.

### Uruchomienie wątku

Następuje ono przez wykonanie funkcji eksperymentu:
```java
ex.start()
```

### Warunki stopu
 Całość symulacji ograniczana jest pętlą sprawdzającą, czy czas eksperymentu się nie skończył:

```java
while (ex.getTime() < ex.getDuration()) {}
```

### Pobieranie parametrów
Pobieranie parametrów w pętli jest możliwe dzięki funkcjom

```java
ex.getRemainingParticles();
ex.getSurviveProbability();
ex.getRadiologicalActivity()
```

Możliwe jest także sprawdzenie, czy atom o danym indeksie się w danym momencie rozpadł:

```java
ex.isUndergone(index)
```

## Przykład

```java
public class App
{
    public static void main( String[] args )
    {
        Atom atom = new Atom(ChemicalElement.Uran, "halflife", 32.0f);
        Sample sample = new Sample(22, ChemicalElement.Uran, "halfLife", 32.0f);
        Experiment ex = new Experiment(
                4, 22, ChemicalElement.Uran, "halfLife", 32.0f);
        ex.start();

        while (ex.getTime() < ex.getDuration())
        {
            try
            {
                sleep(300L);
            } catch (Exception e)
            {
                System.out.println(e.getMessage());
            }
            System.out.println("time: " + ex.getTime() / 1000);
            System.out.println("remaining particles: " + ex.getRemainingParticles());
            System.out.println("survive probability: " + ex.getSurviveProbability());
            System.out.println("radiological activity: " + ex.getRadiologicalActivity());
            for (int i = 0; i < sample.size(); i++)
            {
                System.out.print(ex.isUndergone(i) + " ");
            }
            System.out.println("\n");
        }
    }
}
```

## Zobacz też

[Dokumentacja dostępnych funkcji](https://doma2203.github.io/RadioactiveDecay/)

---
(C) wrobika & gorzz & doma2203 
