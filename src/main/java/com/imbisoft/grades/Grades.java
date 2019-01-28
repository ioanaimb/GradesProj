// Class containing most of the business logic
// Parses a file, processes the information from it
// and provides output data to be presented
package com.imbisoft.grades;

import java.io.*;
import java.util.*;

public class Grades {
    // all available test names
    private List<String> availTestNames;
    // all the students and their results
    private Map<String, Map<String,Double>> students;
    // all available tests
    Map <String, AvailableTest>availableTests = new HashMap();

    public List<String> getAvailTestNames() {
        return availTestNames;
    }

    // returns the students and thier results ordered by name
    public Map<String, ArrayList<Double>> getStudentsByName() {
        Map<String, ArrayList<Double>> sortedStudents = getStudentsAndGrades("name");
        return sortedStudents;
    }

    // returns the students and thier results ordered by grade
    public Map<String, ArrayList<Double>> getStudentsByGrade() {
        Map<String, ArrayList<Double>> sortedStudents = getStudentsAndGrades("grade");
        return sortedStudents;
    }

    public Grades() {
        // The name of the file to open.
        String fileName = new File("C:\\student-grades-tables\\src\\main\\resources\\grades.txt").getAbsolutePath();
        String line = null;
        String delimiter = ",";
        String[] names = null;
        String[] tests = null;
        try {
            // FileReader reads text files in the default encoding.
            FileReader fileReader =
                    new FileReader(fileName);

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader =
                    new BufferedReader(fileReader);
            // read the first line with names
            if((line = bufferedReader.readLine()) != null){
                names = line.split(delimiter);
                initializeStudentData(names);
            }
            // red the second line, with available tests
            if((line = bufferedReader.readLine()) != null){
                tests = line.split(delimiter);
                saveAvailableTests(tests);
            }

            // read the rest of the file with test results
            while((line = bufferedReader.readLine()) != null) {
                tests = line.split(delimiter);
                addPerformedTests(tests);
            }

            // Always close files.
            bufferedReader.close();
        }
        catch(FileNotFoundException ex) {
            System.out.println("Unable to open file '" + fileName + "'");
        }
        catch(IOException ex) {
            System.out.println("Error reading file '" + fileName + "'");
        }
    }

    // stores the student names and prepares to store test results
    private void initializeStudentData(String[] names){
        String studentName;
        HashMap<String,Double> studentGrades;
        students = new HashMap<String, Map<String, Double>>();
        for(int i = 0; i<names.length; i++) {
            studentName = names[i].trim();
            studentGrades = new HashMap<String,Double>();
            students.put(studentName, studentGrades);
        }
    }
    // saves all the available tests and their weights
    private void saveAvailableTests(String[] tests){
        AvailableTest aTest;
        String testLine;
        for(String p:tests) {
            testLine = p.trim();
            aTest = new AvailableTest(testLine);
            availableTests.put(aTest.getName(), aTest);
        }
        this.availTestNames = new ArrayList<String>(availableTests.keySet());
    }
    // Calculates the grades for a test
    private void addPerformedTests(String[] tests){
        String studentName;
        String testName;
        AvailableTest aTest;
        Map<String,Double> performedTests;
        double answeredQ, totalQ;
        double funGrade, impGrade;
        double testGrade;
        studentName = tests[0].trim();
        testName = tests[1].trim();
        performedTests = students.get(studentName);
        if(testName.charAt(0) == 'G'){
            answeredQ = Integer.parseInt(tests[2].trim());
            aTest = availableTests.get(testName);
            totalQ = aTest.getNumTotalQuestions();
            testGrade = answeredQ*10/totalQ;
        } else {
            funGrade = Integer.parseInt(tests[2].trim());
            impGrade = Integer.parseInt(tests[3].trim());
            testGrade = (funGrade + impGrade) / 2;
        }
        testGrade = Math.round(testGrade*100);
        testGrade = testGrade/100;
        performedTests.put(testName,testGrade);
        students.put(studentName, performedTests);
    }
    // Helper method that prepares the output, sorted my name or grade
    private Map<String, ArrayList<Double>> getStudentsAndGrades(String choice) {
        String studentName;
        Double grade, weight, totalGrade;
        Double finalGrade = 0.0;
        Double totalWeight = 0.0;
        TreeMap<String, ArrayList<Double>> sortedStudents = new TreeMap();
        HashMap<String, Double> studentsFinalGrades = new HashMap();
        Map<String, Double> studentGrades;
        ArrayList<Double> matchedGrades;
        for (Map.Entry<String, Map<String, Double>> entry : students.entrySet()) {
            studentGrades = entry.getValue();
            finalGrade = 0.0;
            totalWeight = 0.0;
            studentName = entry.getKey();
            matchedGrades = new ArrayList<>();
            for (String availTest : availTestNames) {
                if (studentGrades.containsKey(availTest)) {
                    grade = studentGrades.get(availTest);
                    weight = availableTests.get(availTest).getWeight();
                    finalGrade += grade * weight;
                    totalWeight += weight;
                } else
                    grade = -1.0;
                matchedGrades.add(grade);
            }
            totalGrade = (double) Math.round(finalGrade * 100 / totalWeight);
            totalGrade /= 100;
            matchedGrades.add(totalGrade);
            sortedStudents.put(studentName, matchedGrades);
            studentsFinalGrades.put(studentName, totalGrade);
        }
        if (choice.equals("name"))
            return sortedStudents;
        else {
            return sortByValue(studentsFinalGrades, sortedStudents);
        }
    }

    // Helper method to sort by grades
    private  Map<String, ArrayList<Double>>
    sortByValue(Map<String, Double> map, Map<String, ArrayList<Double>> sortedStudents ) {
        List<Map.Entry<String, Double>> list = new ArrayList<>(map.entrySet());
        list.sort(Map.Entry.comparingByValue());
        Collections.reverse(list);
        Map<String, ArrayList<Double>> result = new LinkedHashMap<>();
        for (Map.Entry<String, Double> entry : list) {
            sortedStudents.get(entry.getKey());
            result.put(entry.getKey(), sortedStudents.get(entry.getKey()));
        }
        return result;
    }
}
