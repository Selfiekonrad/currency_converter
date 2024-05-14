package application_tests;
import application.*;

public class AppTest {
    public static void main(String[] args) {
        runAllTest();
    }

    static void runAllTest() {
        testChangeExchangeRate();
        testAddNewCurrency();
        testConvertCurrencyToClass();
        testConvertCurrencyToEnum();
    }

    static void testConvertCurrencyToClass() {
        application.ConverterModelInterface converterModel = new application.ConverterModel();
        double convertedCurrencyResult = 0;
        try {
            convertedCurrencyResult = converterModel.convertCurrencyTo(20, "Polish Zloty", "Yen");
        } catch (UnkownCurrencyException e) {
            System.out.println("error testing convertCurrencyTo");
        }
        annahmeGleich("testConvertCurrency", 670.97, convertedCurrencyResult);
    }

    static void testConvertCurrencyToEnum() {
        application.ConverterModelInterface converterModel = new application.ConverterModelEnum();
        double convertedCurrencyResult = 0;
        try {
            convertedCurrencyResult = converterModel.convertCurrencyTo(20, "Polish Zloty", "Yen");
        } catch (UnkownCurrencyException e) {
            System.out.println("error testing convertCurrencyTo");
        }
        annahmeGleich("testConvertCurrency", 670.97, convertedCurrencyResult);
    }


    static void testAddNewCurrency() {
        application.ConverterModelInterface converterModel = new application.ConverterModel();
        converterModel.addNewCurrency("testCurrency", "xd", 69.69);

        String nameResult = converterModel.getAllCurrenciesAsString()[converterModel.getAllCurrenciesAsString().length-1];
        double exchangeRateResult = 0;
        try {
            exchangeRateResult = converterModel.getExchangeRateFromString(nameResult);
        } catch (UnkownCurrencyException e) {
            System.out.println("error testing addNewCurrency");
        }

        annahmeGleich("testAddNewCurrencyName", nameResult, "testCurrency");
        annahmeGleich("testAddNewCurrencyExchangeRate", exchangeRateResult, 69.69);
    }

    static void testChangeExchangeRate() {
        application.ConverterModelInterface converterModel = new application.ConverterModel();
        try {
            converterModel.changeExchangeRate("Polish Zloty", 4.6);
        } catch (UnkownCurrencyException e) {
            System.out.println("error testing changeExchangeRate");
        }

        double ist = 0;
        try {
            ist = converterModel.getExchangeRateFromString("Polish Zloty");
        } catch (UnkownCurrencyException e) {
            System.out.println("error testing changeExchangeRate");
        }

        annahmeGleich("testChangeExchangeRate", 4.6 , ist);
    }


    static void annahmeGleich(String testName, double soll, double ist) {
        if (soll == ist) {
            System.out.println(testName + " successfull");
        } else {
            System.out.println(testName + " falsch soll="+soll+" ist="+ist);
        }
    }

    static void annahmeGleich(String testName, String soll, String ist) {
        if (soll.equals(ist)) {
            System.out.println(testName + " successfull");
        } else {
            System.out.println(testName + " false. soll: "+soll+". ist:"+ist);
        }
    }
}
