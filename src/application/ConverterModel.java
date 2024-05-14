package application;

import java.io.*;

/**
 * ConverterModel represents the model component of a currency conversion application.
 * It manages currency data, provides methods for currency conversion and manipulation,
 * and interacts with the file system to save and retrieve currency information.
 */
public class ConverterModel implements ConverterModelInterface {

    // Array to store currencies
    private static Currency[] currencies;

    // File path to save currency data
    private final String fileName = "/Users/konrad/studium_code/2.Semester/currency_editor_procject/savingFileCurrency";

    // Flag to indicate whether to overwrite the save file
    private boolean overwriteSaveFile = false;

    /**
     * Constructor for creating a new ConverterModel instance.
     * Initializes the model by populating currencies array from saved file.
     */
    public ConverterModel() {
        createSavedCurrencies();
    }

    /**
     * Populates the currencies array by reading currency data from the saved file.
     */
    private void createSavedCurrencies() {
        String[] rawCurrencies;
        rawCurrencies = readFromFile();
        currencies = new Currency[rawCurrencies.length/3];
        for (int i = 0; i < rawCurrencies.length; i = i+3) {
            Currency currency = new Currency(rawCurrencies[i], rawCurrencies[i+1], Double.parseDouble(rawCurrencies[i+2]));
            if (i == 0) {
                currencies[0] = currency;
            } else {
                currencies[i/3] = currency;
            }
        }
    }

    /**
     * Retrieves names of all currencies as an array of strings.
     *
     * @return An array of strings containing names of all currencies.
     */
    @Override
    public String[] getAllCurrenciesAsString() {
        String[] namesAsString = new String[currencies.length];
        for (int i = 0; i < namesAsString.length; i++) {
            namesAsString[i] = currencies[i].getCurrencyName();
        }
        return namesAsString;
    }

    /**
     * Adds a new currency to the model and saves it to the file.
     *
     * @param name          The name of the new currency.
     * @param label         The label or symbol of the new currency.
     * @param exchangeRate  The exchange rate of the new currency.
     */
    @Override
    public void addNewCurrency(String name, String label, double exchangeRate) {
        Currency[] newCurrencies = new Currency[currencies.length + 1];
        for (int i = 0; i < currencies.length; i++) {
            newCurrencies[i] = currencies[i];
        }
        newCurrencies[currencies.length] = new Currency(name, label, exchangeRate);
        currencies = newCurrencies;

        try {
            writeToFile(name, label, exchangeRate);
        } catch (Exception e) {
            System.out.println("Error while saving currency");
            throw new RuntimeException(e);
        }
    }

    /**
     * Converts an amount from one currency to another.
     *
     * @param amount        this is the amount of the currency the user wants to sell.
     * @param fromCurrency  this is the name of the currency the user wants to sell
     * @param toCurrency    this is the name currency the user wants to buy
     * @return The converted amount.
     */
    @Override
    public double convertCurrencyTo(double amount, String fromCurrency, String toCurrency) {
        double fromCurrencyExchangeRate = 0;
        double toCurrencyExchangeRate = 0;
        boolean fromCurrencyExists = false;
        boolean toCurrencyExists = false;

        for (int i = 0; i < currencies.length; i++) {
            if (currencies[i].getCurrencyName().equals(fromCurrency)) {
                fromCurrencyExchangeRate = currencies[i].getExchangeRate();
                fromCurrencyExists = true;
            }
            if (currencies[i].getCurrencyName().equals(toCurrency)) {
                toCurrencyExchangeRate = currencies[i].getExchangeRate();
                toCurrencyExists = true;
            }
        }
        if (!fromCurrencyExists && !toCurrencyExists) {
            return -1;
        }

        return Math.floor(((amount / fromCurrencyExchangeRate) * toCurrencyExchangeRate) * 100 ) / 100;
    }

    /**
     * Changes the exchange rate of a currency and updates the file.
     *
     * @param name             this is the name of the currency the user wants to change the exchange rate on.
     * @param newExchangeRate  the amount of type double, which is the new exchange rate.
     */
    @Override
    public void changeExchangeRate(String name, double newExchangeRate) {
        for (int i = 0; i < currencies.length; i++) {
            if (currencies[i].getCurrencyName().equals(name)) {
                currencies[i].setExchangeRate(newExchangeRate);
            }
        }
        for (int i = 0; i < currencies.length; i++) {
            try {
                if (i == 0) {
                    overwriteSaveFile = true;
                } else {
                    overwriteSaveFile = false;
                }
                writeToFile(currencies[i].getCurrencyName(), currencies[i].getCurrencyLabel(), currencies[i].getExchangeRate());
            } catch (Exception e) {
                System.out.println("Error while saving currency" + e.getMessage());
            }
        }

    }

    /**
     * Retrieves the name of the first currency in the model.
     *
     * @return The name of the first currency, or an error message if none exists.
     */
    @Override
    public String getFirstCurrencyAsString() {
        if (currencies.length > 0 && currencies[0] != null) {
            return String.valueOf(currencies[0].getCurrencyName());
        } else {
            return "error fetching currency";
        }
    }

    /**
     * Retrieves the exchange rate of a currency from its name.
     *
     * @param currency the name of the currency.
     * @return The exchange rate of the currency, or -1 if not found.
     */
    @Override
    public double getExchangeRateFromString(String currency) {
        for (int i = 0; i < currencies.length; i++) {
            if (currencies[i].getCurrencyName().equals(currency)) {
                return currencies[i].getExchangeRate();
            }
        }
        return -1;
    }

    /**
     * Retrieves the currency label from its exchange rate.
     *
     * @param exchangeRate The exchange rate of the currency.
     * @return The label of the currency, or an error message if not found.
     */
    @Override
    public String getCurrencySignFromExchangeRate(double exchangeRate) {
        for (int i = 0; i < currencies.length; i++) {
            if (currencies[i].getExchangeRate() == exchangeRate) {
                return currencies[i].getCurrencyLabel();
            }
        }
        return "error getting label";
    }

    /**
     * Writes currency data to the file.
     *
     * @param name          The name of the currency.
     * @param label         The label or symbol of the currency.
     * @param exchangeRate  The exchange rate of the currency.
     */
    private void writeToFile(String name, String label, double exchangeRate) {
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(fileName, !overwriteSaveFile);
            fileWriter.write(name + "#" + label + "#" + exchangeRate + "#");
        } catch (IOException e) {
            System.out.println("Error while writing file" + e.getMessage());
        }
        finally {
            if (fileWriter != null) {
                try {
                    fileWriter.close();
                } catch (IOException e) {
                    System.out.println("Error while closing file" + e.getMessage());
                }
            }
        }
    }

    /**
     * Reads currency data from the file.
     *
     * @return An array of strings containing currency data.
     */
    private String[] readFromFile() {
        FileReader fileReader = null;
        char[] buffer = new char[1024];
        try {
            fileReader = new FileReader(fileName);
            int charsRead = fileReader.read(buffer);

            String fileContent = new String(buffer, 0, charsRead);
            return fileContent.split("#");
        } catch (Exception e) {
            System.out.println("Error while reading file. returning default data. " + e.getMessage());
            return createDefaultCurrenciesIfFetchingFailed();
        }
        finally {
            if (fileReader != null) {
                try {
                    fileReader.close();
                } catch (IOException e) {
                    System.out.println("Error while closing file" + e.getMessage());
                }
            }
        }
    }

    /**
     * Creates default currency data if file reading fails.
     *
     * @return An array of strings containing default currency data.
     */
    private String[] createDefaultCurrenciesIfFetchingFailed() {
        return new String[] {"Polish Zloty", "zl", "4.9", "Euro", "€", "1.0", "Yen", "¥", "161.37"};
    }
}