<%@ page language="java" contentType="text/html; charset=ISO-8859-1"  pageEncoding="ISO-8859-1" import="java.util.*"%>
<%@ page import="java.sql.*, javax.sql.*, java.io.*, javax.naming.*" %>
<%Connection con1 = null;try{Class.forName("com.mysql.jdbc.Driver");con1= DriverManager.getConnection("jdbc:mysql://localhost:3306/companydb?zeroDateTimeBehavior=convertToNull","root","");}catch(Exception e){e.printStackTrace();}%>

<h2>Table Name - JobApplicationPage</h2><table border="1"><tr>

<%Statement stdisp=con1.createStatement();ResultSet aResultset = stdisp.executeQuery("select COLUMN_NAME, DATA_TYPE from INFORMATION_SCHEMA.COLUMNS IC where TABLE_NAME = 'JobApplicationPage' and table_schema= 'companydb'");
int i=0;while (aResultset.next()) {String column_name = aResultset.getString("COLUMN_NAME");i++;%><th><%=column_name%></th><%}%> </tr><%aResultset.close();stdisp.close();%>
<tr><%Statement datdisp=con1.createStatement();
ResultSet datResultset = datdisp.executeQuery("select * from JobApplicationPage");
while (datResultset.next()) {%><tr><%for(int j=1;j<=i;j++){String data_re = datResultset.getString(j);%><td><%=data_re%></td><%}%></tr><%}%></table><%datResultset.close();datdisp.close();%>


<br/><br/><h2>Table Name - JobApplicationPage_fields_20</h2><table border="1"><tr>

<%Statement stdisp_chk0=con1.createStatement();ResultSet aResultset_chk0 = stdisp_chk0.executeQuery("select COLUMN_NAME, DATA_TYPE from INFORMATION_SCHEMA.COLUMNS IC where TABLE_NAME = 'JobApplicationPage_fields_20'  and table_schema= 'companydb'");
int chk0=0;while (aResultset_chk0.next()) {String column_namechk0 = aResultset_chk0.getString("COLUMN_NAME");chk0++;%><th><%=column_namechk0%></th><%}%> </tr><%aResultset_chk0.close();stdisp_chk0.close();%>
<tr><%Statement datdisp_chk0=con1.createStatement();
ResultSet datResultset_chk0 = datdisp_chk0.executeQuery("select * from JobApplicationPage_fields_20");
while (datResultset_chk0.next()) {%><tr><%for(int datchk=1;datchk<=chk0;datchk++){String data_chk0 = datResultset_chk0.getString(datchk);%><td><%=data_chk0%></td><%}%></tr><%}%></table><%datResultset_chk0.close();datdisp_chk0.close();%>


<br/><br/><h2>Table Name - JobApplicationPage_city_21</h2><table border="1"><tr>

<%Statement stdisp_chk1=con1.createStatement();ResultSet aResultset_chk1 = stdisp_chk1.executeQuery("select COLUMN_NAME, DATA_TYPE from INFORMATION_SCHEMA.COLUMNS IC where TABLE_NAME = 'JobApplicationPage_city_21'  and table_schema= 'companydb'");
int chk1=0;while (aResultset_chk1.next()) {String column_namechk1 = aResultset_chk1.getString("COLUMN_NAME");chk1++;%><th><%=column_namechk1%></th><%}%> </tr><%aResultset_chk1.close();stdisp_chk1.close();%>
<tr><%Statement datdisp_chk1=con1.createStatement();
ResultSet datResultset_chk1 = datdisp_chk1.executeQuery("select * from JobApplicationPage_city_21");
while (datResultset_chk1.next()) {%><tr><%for(int datchk=1;datchk<=chk1;datchk++){String data_chk1 = datResultset_chk1.getString(datchk);%><td><%=data_chk1%></td><%}%></tr><%}%></table><%datResultset_chk1.close();datdisp_chk1.close();%>
<%con1.close();%>