package RadioactiveDecay;

import java.util.HashMap;

/**
 * HaszMapa przechowujaca wartosci trzech parametrow:
 * czasu polowicznego rozpadu,
 * sredniego czasu zycia czastki
 * i stalej rozpadu.
 * Do obiektu mozna wlozyc tylko jeden parametr,
 * pozostale obliczane sa na podstawie jego wartosci,
 * poniewaz sa ze soba zwiazane wzorami matematycznymi.
 */
class Parameter extends HashMap<String, Float>
{
    /**
     * Sprawdza poprawność klucza. Klucz musi byc jedna z trzech nazw:
     * halfLife, decayConstant lub meanLifeTime.
     * @param key klucz podany przez uzytkownika
     * @param pattern wzor klucza - jeden z trzech halfLife, decayConstant lub meanLifeTime
     * @return zwraca info o poprawnosci klucza
     */
    private Boolean isCorrectKey(String key, String pattern)
    {
        return (key.compareToIgnoreCase(pattern) == 0);
    }

    /**
     * Do obiektu mozna wlozyc tylko jeden parametr,
     * pozostale obliczane sa na podstawie jego wartosci,
     * poniewaz sa ze soba zwiazane wzorami matematycznymi.
     * Klucz musi być jedna z trzech nazw:
     * halfLife, decayConstant lub meanLifeTime.
     * @param key nazwa parametru - halfLife, decayConstant lub meanLifeTime
     * @param value partosc parametru (halfLife podajemy w sekundach)
     * @return zwraca wartosc klucza
     */
    @Override
    public Float put(String key, Float value)
    {
        //do mapy można dołożyć tylko jeden klucz z wartością, gdyż pozostałe są ze sobą związane i można je wyliczyć z tego jednego
        if (this.size() < 1)
        {
            //sprawdza poprawnosc klucza i wylicza automatycznie pozostale wartosci
            //są tylko trzy poprawne klucze: czas połowicznego rozpadu, stała rozpadu i średni czas życia cząstki
            if (isCorrectKey(key, "halfLife"))
            {
                super.put("decayConstant", (float)Math.log(2)/value );
                super.put("meanLifeTime", value/(float)Math.log(2) );
                return super.put(key, value);

            }
            if (isCorrectKey(key, "meanLifeTime"))
            {
                super.put("decayConstant", 1/value );
                super.put("halfLife", (float)Math.log(2)*value);
                return super.put(key, value);
            }
            if (isCorrectKey(key, "decayConstant"))
            {
                super.put("halfLife", (float)Math.log(2)/value );
                super.put("meanLifeTime", 1/value );
                return super.put(key, value);
            }
            else
                throw new IllegalArgumentException("Niepoprawny argument!");
        }
        else
            throw new IllegalArgumentException("parametry są już ustawione!");
    }
}
