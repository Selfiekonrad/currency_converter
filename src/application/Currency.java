package application;

/**
 * Represents a currency with its name, label, and exchange rate.
 */
public class Currency {
    private final String currencyName;
    private final String currencyLabel;
    private double exchangeRate;

    /**
     * Constructor to initialize a currency with its name, label, and exchange rate.
     *
     * @param currencyName The name of the currency.
     * @param currencyLabel The label of the currency (symbol).
     * @param exchangeRate The exchange rate of the currency.
     */
    Currency(String currencyName, String currencyLabel, double exchangeRate) {
        this.currencyName = currencyName;
        this.currencyLabel = currencyLabel;
        this.exchangeRate = exchangeRate;
    }

    String getCurrencyLabel() {
        return this.currencyLabel;
    }

    String getCurrencyName() {
        return this.currencyName;
    }

    double getExchangeRate() {
        return this.exchangeRate;
    }

    void setExchangeRate(double exchangeRate) {
        this.exchangeRate = exchangeRate;
    }
}
