// Class that provides information about all the available tests
package com.imbisoft.grades;

public class AvailableTest {
    protected String name;
    protected double weight;
    private int numTotalQuestions;
    private int numCorrectQuestions;

    public AvailableTest(String test) {
        this.name = findName(test);
        this.weight = findWeight(test);
        this.numTotalQuestions = findNumQuestions(test);
    }

    public int getNumTotalQuestions() {
        return numTotalQuestions;
    }

    public int getNumCorrectQuestions() {
        return numCorrectQuestions;
    }

    public void setNumCorrectQuestions(int numCorrectQuestions) {
        this.numCorrectQuestions = numCorrectQuestions;
    }

    // retrieves the test name from a String
    private String findName(String test){
        if(test.charAt(0) == 'G')
            return test.substring(0,test.lastIndexOf("("));
        else
            return test.substring(0,test.lastIndexOf("["));

    }

    // retrieves the total number of questions for a test
    private int findNumQuestions(String test){
        if(this.name.charAt(0) == 'G') {
            String numQuestions = test.substring(test.lastIndexOf("(") + 1, test.lastIndexOf(")"));
            return Integer.parseInt(numQuestions);
        } else
            return 0;
    }

    public String getName() {
        return name;
    }

    public double getWeight() {
        return weight;
    }

    // retrieves the weight of a test
    private int findWeight(String test){
        String  weight = test.substring(test.lastIndexOf("[")+1,test.lastIndexOf("]"));
        return Integer.parseInt(weight);
    }

}
