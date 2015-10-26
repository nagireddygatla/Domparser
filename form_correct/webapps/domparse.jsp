<%@ page language="java" contentType="text/html; charset=ISO-8859-1"  pageEncoding="ISO-8859-1" import="java.util.*"%>
<%@ page import="java.sql.*, javax.sql.*, java.io.*, javax.naming.*" %>
<%Connection con = null;try{Class.forName("com.mysql.jdbc.Driver");con= DriverManager.getConnection("jdbc:mysql://localhost:3306/movierec?zeroDateTimeBehavior=convertToNull","root","");}catch(Exception e){e.printStackTrace();}%>

<%try{
String text_0 = (String)request.getParameter("firstname");
String text_1 = (String)request.getParameter("lastname");
String text_2 = (String)request.getParameter("ssn");
String radtmp0 = (String)request.getParameter("gender");
String seltmp0 = (String)request.getParameter("jobtype");

Statement stmt_mn = con.createStatement();
String maininsrt = "insert into JobApplicationPage(firstname,lastname,ssn,gender,jobtype) values(\""+text_0+"\",\""+text_1+"\",\""+text_2+"\",\""+radtmp0+"\",\""+seltmp0+"\");";
stmt_mn.executeUpdate(maininsrt);stmt_mn.close();

if(request.getParameterValues("fields")!=null){String [] mulchk0 = request.getParameterValues("fields");
for(int loopvarchk0=0;loopvarchk0 < mulchk0.length;loopvarchk0++){String insstmtchk0 = "insert into JobApplicationPage_fields_20(ssn,fields) values(\""+text_2+"\",\""+mulchk0[loopvarchk0]+"\")";
Statement stmtschk0=con.createStatement();
stmtschk0.executeUpdate(insstmtchk0);stmtschk0.close();}}


if(request.getParameterValues("city")!=null){String [] mulchk1 = request.getParameterValues("city");
for(int loopvarchk1=0;loopvarchk1 < mulchk1.length;loopvarchk1++){String insstmtchk1 = "insert into JobApplicationPage_city_21(ssn,city) values(\""+text_2+"\",\""+mulchk1[loopvarchk1]+"\")";
Statement stmtschk1=con.createStatement();
stmtschk1.executeUpdate(insstmtchk1);stmtschk1.close();}}
%><h2>Data is inserted Successfully</h2><%}catch (SQLException e)  {  %><h2>Data is not inserted due to issues</h2><% e.printStackTrace();}finally{con.close();}%>