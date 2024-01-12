package com.example.first_second.application_logic;

/**
 * Implementierung der ApplicationLogic
 */
public class ApplicationLogicImpl implements ApplicationLogic{

    public String calculatePortion(String text, double multiplier) throws IllegalArgumentException{
        if (text == null) throw new IllegalArgumentException("Text darf nicht null sein");
        if (multiplier <= 0) return text;

        StringBuilder result = new StringBuilder();
        StringBuilder currentNumber = new StringBuilder();

        for (int i = 0; i < text.length(); i++) {
            char currentChar = text.charAt(i);
            if (Character.isDigit(currentChar)) {
                boolean separatorFound = false;
                while (Character.isDigit(currentChar) || currentChar == '.' || currentChar == ',') {
                    if (Character.isDigit(currentChar)) {
                        currentNumber.append(currentChar);
                    } else {
                        if (separatorFound) break;
                        currentNumber.append(currentChar);
                        separatorFound = true;
                    }
                    i++;
                    if (i < text.length()) {
                        currentChar = text.charAt(i);
                    } else {
                        break;
                    }
                }
                String numberString = currentNumber.toString();
                boolean hadComma = numberString.contains(",");
                double number = Double.parseDouble(numberString.replace(",", "."));
                double product = number * multiplier;
                if(product % 1 == 0){
                    int productWithoutDecimal = (int) product;
                    result.append(productWithoutDecimal);
                } else {
                    if (hadComma){
                        String temp = String.valueOf(product).replace(".", ",");
                        result.append(temp);
                    } else {
                        result.append(product);
                    }
                }
                if (i < text.length()) {
                    result.append(text.charAt(i));
                }
                currentNumber.setLength(0);
            } else {
                result.append(currentChar);
            }
        }
        return result.toString();
    }
}
