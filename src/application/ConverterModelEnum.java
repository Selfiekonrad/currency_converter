package application;

/**
 * Implementation of the ConverterModelInterface using an enum to represent currencies.
 */
public class ConverterModelEnum implements ConverterModelInterface {

    /**
     * Enum representing different currencies with their names, labels, and exchange rates.
     */
    enum Currencies {
            POLISH_ZLOTY(new Currency("Polish Zloty", "zl", 4.81)),
            EURO(new Currency("Euro","€",1)),
            YEN(new Currency("Yen","¥",161.37));

            private Currency currency;

            Currencies(Currency currency) {
                this.currency = currency;
            }

            public String getName() {
                return currency.getCurrencyName();
            }

            public double getExchangeRate() {
                return currency.getExchangeRate();
            }

            public String getLabel() {
                return currency.getCurrencyLabel();
            }

            public void setExchangeRate(double exchangeRate) {
                this.currency.setExchangeRate(exchangeRate);
            }
        }

    @Override
    public String[] getAllCurrenciesAsString() {
            String[] allCurrencies = new String[Currencies.values().length];

            allCurrencies[0] = Currencies.POLISH_ZLOTY.getName();
            allCurrencies[1] = Currencies.EURO.getName();
            allCurrencies[2] = Currencies.YEN.getName();

            return allCurrencies;
        }

    @Override
    public String getFirstCurrencyAsString() {
            return Currencies.POLISH_ZLOTY.getName();
        }

    @Override
    public void addNewCurrency(String name, String label, double exchangeRate) {

        }

    /**
     * Converts an amount from one currency to another.
     *
     * @param amount        The amount to convert.
     * @param fromCurrency  The currency to convert from.
     * @param toCurrency    The currency to convert to.
     * @return              The converted amount.
     */
    @Override
    public double convertCurrencyTo(double amount, String fromCurrency, String toCurrency) {
            double fromValue;
            double toValue;

            switch (fromCurrency) {
                case "Euro":
                    fromValue = Currencies.EURO.getExchangeRate();
                    break;
                case "Polish Zloty":
                    fromValue = Currencies.POLISH_ZLOTY.getExchangeRate();
                    break;
                case "Yen":
                    fromValue = Currencies.YEN.getExchangeRate();
                    break;
                default:
                    fromValue = -1;
            }

            switch (toCurrency) {
                case "Euro":
                    toValue = Currencies.EURO.getExchangeRate();
                    break;
                case "Polish Zloty":
                    toValue = Currencies.POLISH_ZLOTY.getExchangeRate();
                    break;
                case "Yen":
                    toValue = Currencies.YEN.getExchangeRate();
                    break;
                default:
                    toValue = -1;
            }

            return Math.floor(((amount / fromValue) * toValue) * 100 ) / 100;
        }

    /**
     * Changes the exchange rate of a currency.
     *
     * @param name                     The name of the currency.
     * @param newExchangeRate          The new exchange rate.
     * @throws UnkownCurrencyException If the currency is unknown.
     */
    @Override
    public void changeExchangeRate(String name, double newExchangeRate) throws UnkownCurrencyException {
            switch (name) {
                case "Euro": Currencies.EURO.setExchangeRate(newExchangeRate);
                break;
                case "Polish Zloty": Currencies.POLISH_ZLOTY.setExchangeRate(newExchangeRate);
                break;
                case "Yen": Currencies.YEN.setExchangeRate(newExchangeRate);
                break;
                default: throw new UnkownCurrencyException();
            }
        }

    /**
     * Retrieves the exchange rate of a currency given its name.
     *
     * @param currency                  The name of the currency.
     * @return                          The exchange rate of the currency.
     * @throws UnkownCurrencyException  If the currency is unknown.
     */
    @Override
    public double getExchangeRateFromString(String currency) throws UnkownCurrencyException {
            switch (currency) {
                case "Euro": return Currencies.EURO.getExchangeRate();
                case "Polish Zloty": return Currencies.POLISH_ZLOTY.getExchangeRate();
                case "Yen": return Currencies.YEN.getExchangeRate();
                default: throw new UnkownCurrencyException();
            }
        }

    /**
     * Retrieves the name of the currency associated with a given exchange rate.
     *
     * @param exchangeRate The exchange rate of the currency.
     * @return             The name of the currency.
     */
    @Override
    public String getCurrencySignFromExchangeRate(double exchangeRate) {
            if (exchangeRate == (Currencies.EURO.getExchangeRate())) {
                return Currencies.EURO.getName();
            } else if (exchangeRate == (Currencies.POLISH_ZLOTY.getExchangeRate())) {
                return Currencies.POLISH_ZLOTY.getName();
            } else if (exchangeRate == (Currencies.YEN.getExchangeRate())) {
                return Currencies.YEN.getName();
            }
            return "error fetching name";
        }
}