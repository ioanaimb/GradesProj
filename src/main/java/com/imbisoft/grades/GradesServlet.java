package com.imbisoft.grades;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Map;

@WebServlet(name = "GradesServlet", urlPatterns = {"/index.jsp","/home"})
public class GradesServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        handleRequest(request, response);
    }

    public void handleRequest(HttpServletRequest req, HttpServletResponse res) throws IOException {
        PrintWriter out = res.getWriter();
        res.setContentType("text/html");
        String paramName = "choice";
        String paramValue = req.getParameter(paramName);
        //TODO continue to add get choice functionality
        //  if (paramValue==null) {
        //      out.write("Parameter " + paramName + " not found");
        //  } else {
            Map<String, ArrayList<Double>> students;
            Grades grades = new Grades();
            //Adding table head
            out.println("<table><thead><tr><th>Nume</th>");
            for(String test:grades.getAvailTestNames()) {
                out.println("<th>" + test + "</th>");
            }
            out.println("<th>Nota</th></tr></thead><tbody>");
            // Now the paramName is null so it will not sort by name
            // To test sorting by name add paramName = "name";
            if(paramName.equals("name"))
                // Shows test results sorted by student name
                students = grades.getStudentsByName();
            else
                // Shows test results sorted by student grades, highest grade first
                students = grades.getStudentsByGrade();
            // adding all the entries in the table
            for (Map.Entry<String, ArrayList<Double>> entry : students.entrySet()) {
                out.println("<tr><td>" + entry.getKey() + "</td>");
                for(Double finalGrades: entry.getValue()){
                    if (finalGrades == -1)
                        out.println("<td>--</td>");
                    else
                        out.println("<td>" + finalGrades + "</td>");
                }
                out.println("</tr>");
            }
            out.println("</tbody></table>");
            out.flush();

       // }
        out.close();
    }

}
