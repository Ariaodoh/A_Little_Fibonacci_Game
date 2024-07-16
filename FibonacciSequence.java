package com.ljmu.comp7501.eodoh;

import java.util.Arrays;
import java.util.Scanner;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



/**
 * FIBONACCI APP!
 * This can generate and print the first 30 numbers of the fibonacci sequence 
 * This app was created for user interaction from the command line, no GUIs are made for this app
 * This app was created as a part of the requirments for the completion of LJMU-7501-COMP module 
 *
 * @author Odoh Eucharia Ebere
 *
 */
public class FibonacciSequence {
    public static void main( String[] args ){
        final int max = 30;//declares the max number of the fibinnaci sequence to generate. Can increase or decrease number based on requirements
        int[] fibonacciArray = fibonacciArraysGenerator(max);//holds array from generator method

        //Create function to return appropriate suffix
        Function<Integer, String> ordinalIndicator = n -> {
            String str;
            if (n == 1) {
                str = "st";
            } else if (n == 2) {
                str = "nd";
            } else if (n == 3) {
                str = "rd";
            } else {
                str = "th";
            }
            return str;
        };

        Scanner scanner = new Scanner(System.in);

        //prompt user entry
        System.out.println("\n \nWelcome to the Fibonacci Sequence Generator. \nYou can print any of the frist 30 numbers of the sequence by entering the index or range. [e.g 3rd number or 3rd-15th number]");

        while (true) {
            String userInput = scanner.nextLine();

            int[] range = extractInputNumbers(userInput);//return index as array from method

            //checks to ensure range array returend from method is valid
            if (range != null && range.length == 2) {
                //assign array indices to individual integers
                int startIndex = range[0];
                int endIndex = range[1];

                //Assign appropriate ordinal indicator to individual string variables 
                String suffix = ordinalIndicator.apply(startIndex);
                String suffix2 = ordinalIndicator.apply(endIndex);
                
                //get fibonacci numbers from fibonacci array and print results optionally 
                if (startIndex == endIndex) {
                    int fibonacciNumber = fibonacciArray[startIndex];
                    System.out.print(String.format("\n \nThe %d%s Fibonacci number is: %d", startIndex, suffix, fibonacciNumber));

                }else if (startIndex < endIndex && endIndex <= max){
                    int[] rangeArray = Arrays.copyOfRange(fibonacciArray, startIndex, endIndex + 1);
                    System.out.print(String.format("\n \nThe %d%s - %d%s Fibonacci number is: ", startIndex, suffix, endIndex, suffix2));
                    Arrays.stream(rangeArray).forEach(System.out::println);
                    
                } else {
                        System.err.println("\n \nInvalid Index Range");
                }

                
                scanner.close();//close scanner resource 
                break;//breaks while loop for user input

            } else {
                System.out.println("\n \nInvalid input format. Please use the format 'xth number' for a single number or xth-yth number' for a range...");
            }

        }
        
    }


    /**
     * ----------------------[ FIBONACCI SEQUENCE GENERATOR METHOD ]-----------------------
     *  This method calculates the first numbers of the fibonacci sequence
     *  @param index the maximum number of fibonacci numbers to generate
     * @return returns an array of the frist 30 numbers in the fibonacci sequence 
     */
    static int[] fibonacciArraysGenerator(int index){

        int[] fibonacciArray = new int[index];

        if (index >= 1) {
            fibonacciArray[0] = 0;
        }
        if (index >= 2) {
            fibonacciArray[1] = 1;
        }

        for (int i = 2; i < index; i++) {
            fibonacciArray[i] = fibonacciArray[i - 1] + fibonacciArray[i - 2];
        }

        return fibonacciArray;

    }


    /**
     * ------------------------------[EXTRACT INPUT NUMBERS METHOD]------------------------------
     * This method extracts the index numbers required from the user input entered 
     * It uses the regex matcher and pattern classes to parse user input
     * @param input this is input string to be matched
     * @return either an array of two integer or null if match is not found 
     */
    public static int[] extractInputNumbers(String input) {
        
        String pattern = "(\\d+)\\D*-?(\\d*)\\D*number";// Defines a regex pattern to match the format "xth-yth number"
        Pattern regex = Pattern.compile(pattern);
  
        Matcher matcher = regex.matcher(input);// Creates a Matcher object to find the pattern in the input

        // Checks if the pattern is found
        if (matcher.matches()) {
            int start = Integer.parseInt(matcher.group(1));//captures frist digit of the specified regex
            int end = matcher.group(2).isEmpty() ? start : Integer.parseInt(matcher.group(2));// If the second digit is not captured, default it to the same as the first digit
            
            return new int[]{start, end};//returns array of two ints

        } else {
            return null; // when pattern not found in input

        }
    }
    
    
}//----------------------------------[APP END]------------------------------------
