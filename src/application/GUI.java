package application;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;

/**
 * Represents the graphical user interface for the currency converter application.
 */
public class GUI {
    // Instance variables

    // Converter model interfaces
    private ConverterModelInterface converterModelInterface = new ConverterModel();
    private final ConverterModelInterface converterModelEnum = new ConverterModelEnum();
    private final ConverterModelInterface converterModelClass = new ConverterModel();

    // Boolean flag to track the current converter model
    private boolean isConverterModelEnum = false;

    // Exchange rates for selected currencies
    private double buyFromSelectedCurrencyRate;
    private double buyToSelectedCurrencyRate;

    // Buffer variables to store currency details
    private String nameOfCurrencyBuffer;
    private String signOfCurrencyBuffer;
    private String exchangeRateOfCurrencyBuffer;
    private double changeRateOfCurrencyBuffer;

    // Selected currency to sell
    private String currencyToSellSign;

    // Components
    private JLabel titleCurrencyToBuyLabel;
    private JTextField inputValueTextField;
    private JLabel buyFromSelectedCurrencyRateLabel;
    private JLabel buyToSelectedCurrencyRateLabel;
    private JComboBox<String> buyFromCurrenciesArray;
    private JComboBox<String> buyToCurrenciesArray;
    private JComboBox<String> exchangeRateCurrenciesArray;

    GUI() {
        initializingVariables();
        createWindow();
    }

    private void initializingVariables() {
        String titleCurrencyToBuy = converterModelInterface.getFirstCurrencyAsString();
        try {
            buyFromSelectedCurrencyRate = converterModelInterface.getExchangeRateFromString(titleCurrencyToBuy);
        } catch (UnkownCurrencyException e) {
            System.out.println("Unknown Currency Exception " + e.getMessage());
        }
        try {
            buyToSelectedCurrencyRate = converterModelInterface.getExchangeRateFromString(titleCurrencyToBuy);
        } catch (UnkownCurrencyException e) {
            System.out.println("Unknown Currency Exception " + e.getMessage());
        }
    }

    private void createWindow() {
        JFrame window = new JFrame();
        JPanel mainPanel = new JPanel(new BorderLayout());

        JPanel titlePanel = titlePanel();
        JPanel convertPanel = convertPanel();
        JPanel currencyAdjustmentPanel = currencyAdjustmentPanel();

        mainPanel.add(currencyAdjustmentPanel, BorderLayout.SOUTH);
        mainPanel.add(convertPanel, BorderLayout.CENTER);
        mainPanel.add(titlePanel, BorderLayout.NORTH);

        mainPanel.setBorder(new EmptyBorder(50, 50, 50, 50));
        window.add(mainPanel);
        window.setPreferredSize(new Dimension(850, 550));
        window.pack();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }

    private JPanel titlePanel() {
        JPanel titlePanel = new JPanel(new BorderLayout());
        titleCurrencyToBuyLabel = new JLabel("buy Polish Zloty");
        titleCurrencyToBuyLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleCurrencyToBuyLabel.setFont(new Font(titleCurrencyToBuyLabel.getFont().getName(), Font.BOLD, 20));
        titleCurrencyToBuyLabel.setBorder(new EmptyBorder(0, 0, 20, 0));

        JPanel buyFromToCurrencyInTitlePanel = new JPanel(new BorderLayout());
        buyFromToCurrencyInTitlePanel.setBorder(new EmptyBorder(0, 250, 0, 120));

        buyFromSelectedCurrencyRateLabel = new JLabel(buyFromSelectedCurrencyRate + " " + converterModelInterface.getCurrencySignFromExchangeRate(buyFromSelectedCurrencyRate));
        buyFromSelectedCurrencyRateLabel.setFont(new Font(buyFromSelectedCurrencyRateLabel.getFont().getName(), Font.BOLD, 20));

        JLabel buyFromToSelectedCurrenciesEqualSign = new JLabel("=");
        buyFromToSelectedCurrenciesEqualSign.setHorizontalAlignment(SwingConstants.CENTER);
        buyFromToSelectedCurrenciesEqualSign.setFont(new Font(buyFromToSelectedCurrenciesEqualSign.getFont().getName(), Font.BOLD, 20));

        buyToSelectedCurrencyRateLabel = new JLabel(buyToSelectedCurrencyRate + " " + converterModelInterface.getCurrencySignFromExchangeRate(buyFromSelectedCurrencyRate));
        buyToSelectedCurrencyRateLabel.setFont(new Font(buyToSelectedCurrencyRateLabel.getFont().getName(), Font.BOLD, 20));

        buyFromToCurrencyInTitlePanel.add(buyFromSelectedCurrencyRateLabel, BorderLayout.WEST);
        buyFromToCurrencyInTitlePanel.add(buyFromToSelectedCurrenciesEqualSign, BorderLayout.CENTER);
        buyFromToCurrencyInTitlePanel.add(buyToSelectedCurrencyRateLabel, BorderLayout.EAST);

        JButton button = changeModelButton();

        titlePanel.add(button, BorderLayout.EAST);
        titlePanel.add(titleCurrencyToBuyLabel, BorderLayout.NORTH);
        titlePanel.add(buyFromToCurrencyInTitlePanel, BorderLayout.CENTER);

        return titlePanel;
    }

    private JPanel convertPanel() {
        JPanel convertPanel = new JPanel(new BorderLayout());
        JPanel convertFromCurrency = convertFromCurrency();
        JLabel arrow = new JLabel("➟");
        arrow.setHorizontalAlignment(SwingConstants.CENTER);
        arrow.setFont(new Font(arrow.getFont().getName(), Font.PLAIN, 70));
        arrow.setBorder(new EmptyBorder(20,17,0,0));

        JPanel convertToCurrency = convertToCurrency();

        convertPanel.setBorder(new EmptyBorder(70, 100, 90, 100));

        convertPanel.add(convertFromCurrency, BorderLayout.WEST);convertPanel.add(arrow, BorderLayout.CENTER);
        convertPanel.add(convertToCurrency, BorderLayout.EAST);

        return convertPanel;
    }

    private JPanel convertFromCurrency() {
        JPanel convertFromCurrency = new JPanel(new BorderLayout());
        buyFromCurrenciesArray = new JComboBox<>(converterModelInterface.getAllCurrenciesAsString());
        buyFromCurrenciesArray.addActionListener(e -> {
            try {
                buyFromSelectedCurrencyRateLabel.setText(converterModelInterface.getExchangeRateFromString(String.valueOf(buyFromCurrenciesArray.getSelectedItem())) + " " + converterModelInterface.getCurrencySignFromExchangeRate(converterModelInterface.getExchangeRateFromString(String.valueOf(buyFromCurrenciesArray.getSelectedItem()))));
            } catch (UnkownCurrencyException ex) {
                System.out.println("buyFromSelectedCurrencyRateLabel: " + ex.getMessage());
            }
            currencyToSellSign = converterModelInterface.getCurrencySignFromExchangeRate(buyFromSelectedCurrencyRate);
        });
        JTextField inputFromValueTextField = new JTextField();
        inputFromValueTextField.getDocument().addDocumentListener(createDocumentListener(inputFromValueTextField));
        inputFromValueTextField.setPreferredSize(new Dimension(200, 25));

        convertFromCurrency.add(buyFromCurrenciesArray, BorderLayout.NORTH);
        convertFromCurrency.add(inputFromValueTextField, BorderLayout.CENTER);

        return convertFromCurrency;
    }

    private JPanel convertToCurrency() {
        JPanel convertToCurrency = new JPanel(new BorderLayout());
        buyToCurrenciesArray = new JComboBox<>(converterModelInterface.getAllCurrenciesAsString());
        buyToCurrenciesArray.addActionListener(e -> {
            try {
                buyToSelectedCurrencyRateLabel.setText(converterModelInterface.getExchangeRateFromString(String.valueOf(buyToCurrenciesArray.getSelectedItem())) + " " + converterModelInterface.getCurrencySignFromExchangeRate(converterModelInterface.getExchangeRateFromString(String.valueOf(buyToCurrenciesArray.getSelectedItem()))));
            } catch (UnkownCurrencyException ex) {
                System.out.println("buyToSelectedCurrencyRateLabel: " + ex.getMessage());
            }
            titleCurrencyToBuyLabel.setText("buy " + buyToCurrenciesArray.getSelectedItem());
        });
        inputValueTextField = new JTextField();
        inputValueTextField.setPreferredSize(new Dimension(200, 25));

        convertToCurrency.add(buyToCurrenciesArray, BorderLayout.NORTH);
        convertToCurrency.add(inputValueTextField, BorderLayout.CENTER);

        return convertToCurrency;
    }

    private JPanel currencyAdjustmentPanel() {
        JPanel currencyAdjustmentPanel = new JPanel( new BorderLayout());
        currencyAdjustmentPanel.setBorder(new EmptyBorder(0, 100, 20, 100));

        JPanel addCurrencyPanel = addCurrencyPanel();
        JPanel changeExchangeRate = changeExchangeRate();

        currencyAdjustmentPanel.add(addCurrencyPanel, BorderLayout.WEST);
        currencyAdjustmentPanel.add(changeExchangeRate, BorderLayout.CENTER);

        return currencyAdjustmentPanel;
    }

    private JPanel addCurrencyPanel() {
        JPanel addCurrencyPanel = new JPanel(new BorderLayout());
        addCurrencyPanel.setBorder(new EmptyBorder(0, 0, 0, 70));

        JLabel title = new JLabel("add currency");
        title.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel nameAndSignOfCurrency = new JPanel(new BorderLayout());

        JTextField nameOfCurrencyTextField = new JTextField("euro");
        nameOfCurrencyTextField.setForeground(new Color(0, 0, 0, 128));

        nameOfCurrencyTextField.getDocument().addDocumentListener(createDocumentListener(nameOfCurrencyTextField, "nameOfCurrencyBuffer"));

        JTextField signOfCurrencyTextField = new JTextField("€");
        signOfCurrencyTextField.setForeground(new Color(0, 0, 0, 128));
        signOfCurrencyTextField.setPreferredSize(new Dimension(40, 10));
        signOfCurrencyTextField.getDocument().addDocumentListener(createDocumentListener(signOfCurrencyTextField, "signOfCurrencyBuffer"));

        nameAndSignOfCurrency.add(nameOfCurrencyTextField, BorderLayout.CENTER);
        nameAndSignOfCurrency.add(signOfCurrencyTextField, BorderLayout.EAST);

        JPanel amountAndButtonLabel = new JPanel(new BorderLayout());

        JTextField amountOfCurrency = new JTextField("12.34");
        amountOfCurrency.setForeground(new Color(0, 0, 0, 128));
        amountOfCurrency.setPreferredSize(new Dimension(130, 10));
        amountOfCurrency.getDocument().addDocumentListener(createDocumentListener(amountOfCurrency, "exchangeRateOfCurrencyBuffer"));

        JButton saveButton = new JButton("save");
        saveButton.addActionListener(e -> {
            if (isNewCurrencyEmpty()) {
                openErrorMessageWindow("empty currency", "every field must contain information");
            } else {
                if (isConverterModelEnum) {
                    openErrorMessageWindow("enum", "you cant add new currencies to an enum");
                } else {
                    converterModelInterface.addNewCurrency(nameOfCurrencyBuffer, signOfCurrencyBuffer, Double.parseDouble(exchangeRateOfCurrencyBuffer));
                    clearTextFields(nameOfCurrencyTextField, signOfCurrencyTextField, amountOfCurrency);
                    updateComboBoxes();
                }
            }
        });

        amountAndButtonLabel.add(amountOfCurrency, BorderLayout.WEST);
        amountAndButtonLabel.add(saveButton, BorderLayout.CENTER);

        addCurrencyPanel.add(title, BorderLayout.NORTH);
        addCurrencyPanel.add(nameAndSignOfCurrency, BorderLayout.CENTER);
        addCurrencyPanel.add(amountAndButtonLabel, BorderLayout.SOUTH);

        return addCurrencyPanel;
    }

    private JPanel changeExchangeRate() {
        final String[] nameOfCurrency = new String[1];

        JPanel changeExchangeRateLabel = new JPanel(new BorderLayout());
        changeExchangeRateLabel.setBorder(new EmptyBorder(0, 70, 0, 0));

        JLabel title = new JLabel("change exchange rate");
        title.setHorizontalAlignment(SwingConstants.CENTER);
        exchangeRateCurrenciesArray = new JComboBox<>(converterModelInterface.getAllCurrenciesAsString());
        exchangeRateCurrenciesArray.addActionListener(e -> nameOfCurrency[0] = String.valueOf(exchangeRateCurrenciesArray.getSelectedItem()));

        JPanel exchangeRateAndButton = new JPanel(new BorderLayout());
        JTextField changeExchangeRateTextField = new JTextField("4.32");
        changeExchangeRateTextField.setForeground(new Color(0, 0, 0, 128));
        changeExchangeRateTextField.getDocument().addDocumentListener(createDocumentListener(changeExchangeRateTextField, "changeRateOfCurrencyBuffer"));

        JButton saveButton = new JButton("save");
        saveButton.addActionListener(e -> {
            try {
                converterModelInterface.changeExchangeRate(nameOfCurrency[0], changeRateOfCurrencyBuffer);
            } catch (UnkownCurrencyException ex) {
                System.out.println("Unknown currency: " + ex.getMessage());
            }
        });

        exchangeRateAndButton.add(changeExchangeRateTextField, BorderLayout.WEST);
        exchangeRateAndButton.add(saveButton, BorderLayout.CENTER);

        changeExchangeRateLabel.add(title, BorderLayout.NORTH);
        changeExchangeRateLabel.add(exchangeRateCurrenciesArray, BorderLayout.CENTER);
        changeExchangeRateLabel.add(exchangeRateAndButton, BorderLayout.SOUTH);

        return changeExchangeRateLabel;
    }

    // other methods

    private JButton changeModelButton() {
        JButton button = new JButton("change model");
        button.addActionListener(e -> changeModel());

        return button;
    }

    private void openErrorMessageWindow(String title, String message) {
        JFrame frame = new JFrame(title);
        JPanel panel = new JPanel(new BorderLayout());

        JLabel label = new JLabel(message);
        label.setBorder(new EmptyBorder(50, 5, 5, 5));
        label.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.setBorder(new EmptyBorder(20, 150, 20, 150));
        JButton button = new JButton("OK");
        button.setPreferredSize(new Dimension(60, 40));
        button.addActionListener(e -> frame.dispose());

        buttonPanel.add(button, BorderLayout.CENTER);
        panel.add(label, BorderLayout.NORTH);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        frame.add(panel);
        frame.setPreferredSize(new Dimension(400, 200));
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void updateTwoCurrencyTextField(JTextField textField) {
        if (textField.getText().isEmpty()) {
            inputValueTextField.setText("");
        } else {
            try {
                inputValueTextField.setText(String.valueOf(converterModelInterface.convertCurrencyTo(Double.parseDouble(textField.getText()), String.valueOf(buyFromCurrenciesArray.getSelectedItem()), String.valueOf(buyToCurrenciesArray.getSelectedItem()))));
            } catch (UnkownCurrencyException e) {
                System.out.println("updateTwoCurrencyTextField: application.UnknownCurrencyException " + e.getMessage());
            }
        }
    }

    private void changeModel() {
        if (isConverterModelEnum) {
            converterModelInterface = converterModelClass;
            updateComboBoxes();
            isConverterModelEnum = false;
        } else {
            updateComboBoxesForEnum();
            converterModelInterface = converterModelEnum;
            isConverterModelEnum = true;
        }
    }

    private void updateComboBoxes() {
        String[] arrayFromLogic = converterModelInterface.getAllCurrenciesAsString();

        buyFromCurrenciesArray.removeAllItems();
        buyToCurrenciesArray.removeAllItems();
        exchangeRateCurrenciesArray.removeAllItems();

        for (String s : arrayFromLogic) {
            buyFromCurrenciesArray.addItem(s);
            buyToCurrenciesArray.addItem(s);
            exchangeRateCurrenciesArray.addItem(s);
        }
    }

    private void updateComboBoxesForEnum() {
        String[] arrayFromLogic = converterModelInterface.getAllCurrenciesAsString();

        buyFromCurrenciesArray.removeAllItems();
        buyToCurrenciesArray.removeAllItems();
        exchangeRateCurrenciesArray.removeAllItems();

        for (int i = 0; i < 3; i++) {
            buyFromCurrenciesArray.addItem(arrayFromLogic[i]);
            buyToCurrenciesArray.addItem(arrayFromLogic[i]);
            exchangeRateCurrenciesArray.addItem(arrayFromLogic[i]);
        }
    }

    private DocumentListener createDocumentListener(JTextField textField) {
        return new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
                updateTwoCurrencyTextField(textField);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateTwoCurrencyTextField(textField);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {

            }
        };
    }

    private DocumentListener createDocumentListener(JTextField textField, String variable) {
        return new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
                if (variable.equals("nameOfCurrencyBuffer")) nameOfCurrencyBuffer = textField.getText();
                if (variable.equals("signOfCurrencyBuffer")) signOfCurrencyBuffer = textField.getText();
                if (variable.equals("exchangeRateOfCurrencyBuffer")) exchangeRateOfCurrencyBuffer = textField.getText();
                if (variable.equals("changeRateOfCurrencyBuffer")) changeRateOfCurrencyBuffer = Double.parseDouble(textField.getText());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                if (variable.equals("nameOfCurrencyBuffer")) nameOfCurrencyBuffer = textField.getText();
                if (variable.equals("signOfCurrencyBuffer")) signOfCurrencyBuffer = textField.getText();
                if (variable.equals("exchangeRateOfCurrencyBuffer")) exchangeRateOfCurrencyBuffer = textField.getText();
                if (variable.equals("changeRateOfCurrencyBuffer")) changeRateOfCurrencyBuffer = Double.parseDouble(textField.getText());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {

            }
        };
    }

    private boolean isNewCurrencyEmpty() {
        boolean buffer = false;
        if (nameOfCurrencyBuffer == null || nameOfCurrencyBuffer.isEmpty()) {
            return true;
        } else if (signOfCurrencyBuffer == null || signOfCurrencyBuffer.isEmpty()) {
            return true;
        } else if (exchangeRateOfCurrencyBuffer == null || exchangeRateOfCurrencyBuffer.isEmpty()) {
            return true;
        } else {
            return buffer;
        }
    }

    private void clearTextFields(JTextField ... textFields) {
        for (JTextField textField : textFields) {
            textField.setText("");
        }
    }
}

























