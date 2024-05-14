package application;

public interface ConverterModelInterface {

    /**
     * @return array of currency's names as Strings
     */
    String[] getAllCurrenciesAsString();

    /**
     * @return the first currency's name
     */
    String getFirstCurrencyAsString();

    /**
     * @param name of the new currency that should be added.
     * @param label the label of the new currency.
     * @param exchangeRate the exchange rate of the new currency.
     */
    void addNewCurrency(String name, String label, double exchangeRate);

    /**
     * @param amount this is the amount of the currency the user wants to sell.
     * @param fromCurrency this is the name of the currency the user wants to sell
     * @param toCurrency this is the name currency the user wants to buy
     * @return the return is of type double in the currency the user bought.
     */
    double convertCurrencyTo(double amount, String fromCurrency, String toCurrency) throws UnkownCurrencyException;

    /**
     * @param name this is the name of the currency the user wants to change the exchange rate on.
     * @param newExchangeRate the amount of type double, which is the new exchange rate.
     */
    void changeExchangeRate(String name, double newExchangeRate) throws UnkownCurrencyException;

    /**
     * @param currency the name of the currency should be delivered, which we want the exchange rate from.
     * @return returns the exchange rate
     */
    double getExchangeRateFromString(String currency) throws UnkownCurrencyException;

    /**
     * familiar to getExchangeRateFromString but this functions returns the sign of the provided currency.
     */
    String getCurrencySignFromExchangeRate(double exchangeRate);
}
