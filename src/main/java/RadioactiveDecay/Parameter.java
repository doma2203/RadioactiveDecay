package RadioactiveDecay;

import java.util.HashMap;

class Parameter extends HashMap<String, Float>
{
    //sprawdza poprawność nazwy klucza
    private Boolean isCorrectKey(String key, String pattern)
    {
        return (key.compareToIgnoreCase(pattern) == 0);
    }

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
