package xmlParsing;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.util.Properties;


public class DomObject {
	
	static String titlemain = null;
	static String jspIds = null;
	static String jspVals = null;
	static String dbname = null;
	//static String iferror = null;
	public static void main(String[] args) {
		
		File fullpath1 = new File("form_correct.xml");
		String Filename = fullpath1.getName();
	
	
		String [] split = Filename.split("\\.");
		//System.out.println(split[0]);
		String curdir = System.getProperty("user.dir");
	
		File jarfile = new File(curdir+"/mysql-connector-java-5.1.18-bin.jar");
		
			//File a = new File("C:/Users/Nagi Reddy/workspace/DomParser");
	
	File fromdir = new File(curdir+"/"+split[0]);
	fromdir.mkdir();
	File creatdir = new File(fromdir+"/webapps");
	creatdir.mkdir();
	String webapdir = fromdir+"/webapps";
	File subdir1 = new File(creatdir+"/WEB-INF");
	subdir1.mkdir();
	File subdir2 = new File(subdir1+"/classes");
	subdir2.mkdir();
	File subdir3 = new File(subdir1+"/lib");
	subdir3.mkdir();
	File newjar = new File(subdir3+"/mysql-connector-java-5.1.18-bin.jar");
	try {
		Files.copy(jarfile.toPath(),newjar.toPath());
	} catch (IOException e1) {
		
		System.out.println("File already exists");
	}
	//jarfile.renameTo(newjar);
		try {
			
			Properties prop = new Properties();
			String propFileName = "database.properties";
			InputStream inputStream = DomObject.class.getResourceAsStream("database.properties");
			if (inputStream != null) {
				prop.load(inputStream);
			} else {
				throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
			}
			dbname = prop.getProperty("dbname");
			String username = prop.getProperty("username");
			String password = prop.getProperty("password");
			
			//File fXmlFile = new File("C:/Users/Nagi Reddy/Desktop/Dom_Parser_asgmt/form2.xml");
			File fXmlFile = new File(curdir+"//"+Filename);
			//System.out.println(curdir+"\\" +split[0]);

			//File fXmlFile = new File(args[0]);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc;
			doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();
			//System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
			beforeCheck(doc);
			htmlCode(doc,webapdir);
			mysqlCreate(doc,webapdir);
			jspCode(doc,dbname,username,password,webapdir);
			jspDisp(doc,dbname,username,password,webapdir);
		} 
		catch (SAXException | IOException | ParserConfigurationException e) {
			e.printStackTrace();
		}
		//} 
	}
	
	public static void beforeCheck(Document befchk) {
		
		NodeList nList_chk = befchk.getElementsByTagName("textbox");

		String keypresent = null;
		for (int tempchk = 0; tempchk < nList_chk.getLength(); tempchk++) {
			
			Node nNode_txtchk = nList_chk.item(tempchk);
			
			if (nNode_txtchk.getNodeType() == Node.ELEMENT_NODE) {
				 
				Element eElement_txt = (Element) nNode_txtchk;
				//Boolean b =   eElement_txt.hasAttribute("key");
				//System.out.println(b);
				if(eElement_txt.hasAttribute("key")){
					keypresent = "present";		
				}
				
				
			}
		}
		
		NodeList chk = befchk.getElementsByTagName("checkboxes");
		NodeList sel = befchk.getElementsByTagName("multiselect");
		System.out.println(keypresent);
		if(keypresent == null){
		if(chk.getLength()!=0 || sel.getLength()!=0){
			
			System.out.println("Terminating!!There is no key attribute, but there are checkboxes or multiselect elements");
			System.exit(0);
			
		}}
		
		NodeList nList_nam = befchk.getElementsByTagName("name");
		
		for (int tempnam = 0; tempnam < nList_nam.getLength()-1; tempnam++) {
			
			Node nNode_txtnam = nList_nam.item(tempnam);
			
			if (nNode_txtnam.getNodeType() == Node.ELEMENT_NODE) {
				 
				Element eElement_txt = (Element) nNode_txtnam;
				 String firnam = eElement_txt.getTextContent();
					//System.out.println(firnam);
				for (int tempmin = tempnam+1; tempmin < nList_nam.getLength(); tempmin++) {
					
					Node nNode_txtmin = nList_nam.item(tempmin);
					
					if (nNode_txtmin.getNodeType() == Node.ELEMENT_NODE) {
						 
						Element eElement_txtmin = (Element) nNode_txtmin;
						String secnam = eElement_txtmin.getTextContent();
						//System.out.println(secnam);
						if(firnam.equalsIgnoreCase(secnam)){
						
							
							System.out.println("Terminating!!Names of two elements turned out to be same");
							System.exit(0);
						}	
					}
				}				
			}
		}				
	}

	//Method that generates html code!!!
	public static void htmlCode(Document doc,String htmldir) {
		
		File file = new File(htmldir+"/index.html");
		BufferedWriter bw = null;
		FileOutputStream fos = null;
		
		
		try {
			if (!file.exists()) {
				
				file.createNewFile();
			}
			
			fos = new FileOutputStream(file);
			bw = new BufferedWriter(new OutputStreamWriter(fos));
			//System.out.println("This is the buffered writer"+bw);
			String init = "<"+"html"+">"+"<"+"head"+">" + "<" + "meta charset=ISO-8859-1"+">";
			
			bw.write(init);
			bw.newLine();
			
		
			
		} 
		
		catch (IOException e) {
			
			e.printStackTrace();
		}
	
		
		
	//for(String s:args){
	try {
	 	
		title(doc,bw);
		String jshead = "<"+"script type"+" ="+ "\"text"+"/"+"javascript\""+">";
		bw.write(jshead);
		bw.newLine();
		String fun = "function validation" + "(" + "){";
		bw.write(fun);
		bw.newLine();
		textBox(doc,bw,"jshead","htmlfile");
		String funcl = "}</script></head>";
		bw.write(funcl);
		bw.newLine();
		
		String frmnam = "<body><"+"h1"+">"+titlemain+"<"+"/h1"+">"+
    "<"+"form name=\"domparse\" method=\"post\"  action = \"domparse.jsp\" onsubmit=\"return validation();\""+">"+
    	"<table>";
		bw.write(frmnam);
		bw.newLine();	
		textBox(doc,bw,"htmlbody","htmlfile");
		//bw.close();
		checkBox(doc,bw,"htmlfile",null);
		radioButton(doc,bw,"htmlfile");
		selectMulti(doc, "select",bw,"htmlfile",null);
		if(doc.getElementsByTagName("multiselect") != null){
		selectMulti(doc, "multiselect",bw,"htmlfile",null);
		}
		submit(doc,bw);
		String fin = "</table></form></body></html>";
		bw.write(fin);
		
		bw.close();
		
			}			    
	     catch (Exception e) {
		e.printStackTrace();
	    }
		
	}
	
	
	//Method to create mysql query - CREATE TABLE
	public static void mysqlCreate(Document docsql,String sqldir) {
		
		File file_sql = new File(sqldir+"/domParser.sql");
		BufferedWriter bw_sql = null;
		try {
			if (!file_sql.exists()) {
				
				file_sql.createNewFile();
			}
			
			FileOutputStream fos_sql = new FileOutputStream(file_sql);
			bw_sql = new BufferedWriter(new OutputStreamWriter(fos_sql));
			String tableName = titlemain.replaceAll("\\s","");
			String sqlstrt = "CREATE TABLE "+tableName+"(";
			bw_sql.write(sqlstrt);
			String primkey = textBox(docsql, bw_sql,"nohtml","sqlfile");
			//System.out.println("this is the primary key"+primkey);
			radioButton(docsql,bw_sql,"sqlfile");
			selectMulti(docsql,"select",bw_sql,"sqlfile",primkey);
			bw_sql.write(");");
			
			if(docsql.getElementsByTagName("multiselect") != null){
			selectMulti(docsql,"multiselect",bw_sql,"sqlfile",primkey);
			}
			checkBox(docsql,bw_sql,"sqlfile",primkey);
			
			bw_sql.close();

		}
		
		catch (IOException e) {
			
			e.printStackTrace();
		}
			
	}
	
	
	//method to create jsp insert page
	public static void jspCode(Document docjsp, String db, String user, String pass,String jspdir) {

		File file_jsp = new File(jspdir+"/domparse.jsp");
		BufferedWriter bw_jsp = null;
		try {
			if (!file_jsp.exists()) {
				//System.out.println("hello jsp creation");
				file_jsp.createNewFile();
			}
			
			FileOutputStream fos_jsp = new FileOutputStream(file_jsp);
			bw_jsp = new BufferedWriter(new OutputStreamWriter(fos_jsp));
			//String tblName_jsp = titlemain.replaceAll("\\s","");
			String head1 = "<%@ page language=\"java\" contentType=\"text/html; charset=ISO-8859-1\"  pageEncoding=\"ISO-8859-1\" import=\"java.util.*\"%>";
			bw_jsp.write(head1);
			bw_jsp.newLine();
			String head2 = "<%@ page import=\"java.sql.*, javax.sql.*, java.io.*, javax.naming.*\" %>";
			bw_jsp.write(head2);
			bw_jsp.newLine();
			String dbconn = "<%"
					+ "Connection con = null;"
					+ "try{"
					+ "Class.forName(\"com.mysql.jdbc.Driver\");"
					+ "con= DriverManager.getConnection(\"jdbc:mysql://localhost:3306/"+db+"?zeroDateTimeBehavior=convertToNull\",\""+user+"\",\""+pass+"\");"
					+ "}"
					+ "catch(Exception e){e.printStackTrace();"
					+ "}"
					+ "%>";
			bw_jsp.write(dbconn);
			bw_jsp.newLine();
			bw_jsp.newLine();
			bw_jsp.write("<%try{");
			bw_jsp.newLine();
			String primarkey = textBox(docjsp, bw_jsp, "nohtml", "jspfile");
			radioButton(docjsp,bw_jsp,"jspfile");
			selectMulti(docjsp,"select",bw_jsp,"jspfile",null);
			bw_jsp.newLine();
			String mainstmt = "Statement stmt_mn = con.createStatement();";
			bw_jsp.write(mainstmt);
			String titlemain_jsp = titlemain.replaceAll("\\s","");
			String [] jvalsplt = jspVals.split(",");
			String jspfin = null;
			for(int i = 0; i<jvalsplt.length;i++){
				
				 if(i==0){
					jspfin = "\\\"\"+"+jvalsplt[i]+"+\"\\\"";
					 
				 }
				else{
					 jspfin = jspfin + ",\\\"\"+"+jvalsplt[i]+"+\"\\\"";
				 }
			}
			
			String main_insert = "String maininsrt = \"insert into "+titlemain_jsp+"("+jspIds+") values("+jspfin+");\";";
			
			bw_jsp.newLine();
			bw_jsp.write(main_insert);
			bw_jsp.newLine();
			String finExec = "stmt_mn.executeUpdate(maininsrt);stmt_mn.close();";
			bw_jsp.write(finExec);
			
			
			if(docjsp.getElementsByTagName("multiselect") != null){
				selectMulti(docjsp,"multiselect",bw_jsp,"jspfile",primarkey);
				}
				checkBox(docjsp,bw_jsp,"jspfile",primarkey);
				
			String closin = "%><h2>Data is inserted Successfully</h2><%}catch (SQLException e)  {  %><h2>Data is not inserted due to issues</h2><% e.printStackTrace();}finally{con.close();}%>";
			bw_jsp.write(closin);
			//String print = "System.out.println(text_0+text_1+text_2+radtmp0+seltmp0);%>";
			//bw_jsp.write(print);
			//System.out.println(jspVals);
			bw_jsp.close();
		}
		catch(IOException e){
			e.printStackTrace();
		}	
	}
	
	
	//method to finally display the inserted data
	
	public static void jspDisp(Document docjdisp,String dbnam, String userid, String passw,String jspdispdir) {
		
		File file_disp = new File(jspdispdir+"/domDisplay.jsp");
		BufferedWriter bw_disp = null;
		try {
			if (!file_disp.exists()) {
				//System.out.println("hello jsp creation");
				file_disp.createNewFile();
			}
			FileOutputStream fos_disp = new FileOutputStream(file_disp);
			bw_disp = new BufferedWriter(new OutputStreamWriter(fos_disp));
			String headdisp = "<%@ page language=\"java\" contentType=\"text/html; charset=ISO-8859-1\"  pageEncoding=\"ISO-8859-1\" import=\"java.util.*\"%>";
			bw_disp.write(headdisp);
			bw_disp.newLine();
			String headdisp2 = "<%@ page import=\"java.sql.*, javax.sql.*, java.io.*, javax.naming.*\" %>";
			bw_disp.write(headdisp2);
			bw_disp.newLine();
			String dbconndisp = "<%"
					+ "Connection con1 = null;"
					+ "try{"
					+ "Class.forName(\"com.mysql.jdbc.Driver\");"
					+ "con1= DriverManager.getConnection(\"jdbc:mysql://localhost:3306/"+dbnam+"?zeroDateTimeBehavior=convertToNull\",\""+userid+"\",\""+passw+"\");"
					+ "}"
					+ "catch(Exception e){e.printStackTrace();"
					+ "}"
					+ "%>";
			bw_disp.write(dbconndisp);
			bw_disp.newLine();
			bw_disp.newLine();
			String disptitle = titlemain.replaceAll("\\s","");
			String tblnam = "<h2>Table Name - "+disptitle+"</h2><table border=\"1\"><tr>";
			bw_disp.write(tblnam);
			bw_disp.newLine();
			String startdisp = "<%Statement stdisp=con1.createStatement();";
			bw_disp.newLine();
			bw_disp.write(startdisp);
			String resultset = "ResultSet aResultset = stdisp.executeQuery(\"select COLUMN_NAME, DATA_TYPE from INFORMATION_SCHEMA.COLUMNS IC where TABLE_NAME = '"+disptitle+"' and table_schema= '"+dbnam+"'\");";
			bw_disp.write(resultset);
			bw_disp.newLine();
			String whilLoop = "int i=0;while (aResultset.next()) {"
				  + "String column_name = aResultset.getString(\"COLUMN_NAME\");i++;%>"
				  + "<th><%=column_name%></th>"
			      + "<%}%> </tr><%aResultset.close();stdisp.close();%>";
			bw_disp.write(whilLoop);
			bw_disp.newLine();
			String datdisp = "<tr><%Statement datdisp=con1.createStatement();";
			bw_disp.write(datdisp);
			bw_disp.newLine();
			String datresultset = "ResultSet datResultset = datdisp.executeQuery(\"select * from "+disptitle+"\");";
			bw_disp.write(datresultset);
			bw_disp.newLine();
			String dataLoop = "while (datResultset.next()) {"
					  + "%><tr><%for(int j=1;j<=i;j++){String data_re = datResultset.getString(j);%>"
					  + "<td><%=data_re%></td>"
				      + "<%}%></tr><%}%></table><%datResultset.close();datdisp.close();%>";
			bw_disp.write(dataLoop);
			bw_disp.newLine();
			
			if(docjdisp.getElementsByTagName("multiselect") != null){
				selectMulti(docjdisp,"multiselect",bw_disp,"jspdisp",null);
				}
				checkBox(docjdisp,bw_disp,"jspdisp",null);
			String closing = "<%con1.close();%>";
			bw_disp.write(closing);
			bw_disp.close();
			System.out.println("Great!! Whole of execution is successful!!");
		}
		catch(IOException e){
			
			e.printStackTrace();
		}

		
	}
	
	
	//method to get the title
	public static void title(Document doc,BufferedWriter bw) {
		 
		
		NodeList nList1 = doc.getElementsByTagName("title");
		
		if(nList1.getLength() == 0){
			//iferror = "error";
			System.out.println("Terminating!! There is no title in the form");
			System.exit(0);
		}
		
		for (int temp1 = 0; temp1 < nList1.getLength(); temp1++) {
			Node nNode1 = nList1.item(temp1);
			
			if (nNode1.getNodeType() == Node.ELEMENT_NODE) {
				 
				Element eElement = (Element) nNode1;
			//System.out.println("Title : " + eElement.getElementsByTagName("caption").item(0).getTextContent());
			try {
				//bw.write(eElement.getElementsByTagName("caption").item(0).getTextContent());
				//String s = "hello write something into file";
				titlemain = eElement.getElementsByTagName("caption").item(0).getTextContent();
				String title = "<"+"title"+">"+eElement.getElementsByTagName("caption").item(0).getTextContent()+"<"+"/title"+">";
				bw.write(title);
				bw.newLine();
				//bw.flush();
				//bw.close();
			} catch (DOMException e) {
				
				e.printStackTrace();
			} catch (IOException e) {
				
				e.printStackTrace();
			}
			
		}
		}
	 
	}//method for title of the page code
	
	
	//method for text box code
	
	public static String textBox(Document doc,BufferedWriter bw,String jsht,String filType)
	{
		
		String prim = null;
		try {
		
		NodeList nList_txt = doc.getElementsByTagName("textbox");
		
		for (int temp = 0; temp < nList_txt.getLength(); temp++) {
		
			Node nNode_txt = nList_txt.item(temp);
			
			if (nNode_txt.getNodeType() == Node.ELEMENT_NODE) {
				 
				Element eElement_txt = (Element) nNode_txt;
	 
				//System.out.println("DataType : " + eElement_txt.getAttribute("datatype"));
				
				//System.out.println("Name : " + eElement_txt.getElementsByTagName("name").item(0).getTextContent());
				//System.out.println("Caption : " + eElement_txt.getElementsByTagName("caption").item(0).getTextContent());
				//System.out.println("Size : " + eElement_txt.getElementsByTagName("size").item(0).getTextContent());
				//System.out.println("MaxLength : " + eElement_txt.getElementsByTagName("maxlength").item(0).getTextContent());
				String Name = null;
				String Caption = null;
				
				if(eElement_txt.getElementsByTagName("name").getLength() !=  0){
				Name = eElement_txt.getElementsByTagName("name").item(0).getTextContent();
				}
				if(eElement_txt.getElementsByTagName("caption").getLength() !=  0){
				Caption = eElement_txt.getElementsByTagName("caption").item(0).getTextContent();
				}
				if(Name == null || Caption == null){
					
					//iferror = "error";
					System.out.println("Terminating!! There is no name or caption for textbox in the form");
					System.exit(0);
				}

				String dattype = null;
				if(eElement_txt.hasAttribute("datatype")){
				dattype = eElement_txt.getAttribute("datatype");
				if(dattype.equalsIgnoreCase("integer") || dattype.equalsIgnoreCase("string") || dattype.equalsIgnoreCase("decimal")){
					
				}
				else{
					System.out.println(dattype);
					System.out.println("Data type of the text box is not valid");
					
					//iferror = "error";
					System.exit(0);
				}
				}
				
				
				String maxlen = eElement_txt.getElementsByTagName("maxlength").item(0).getTextContent();
				String txtSize = eElement_txt.getElementsByTagName("size").item(0).getTextContent();
				if(filType.equalsIgnoreCase("jspfile")){
					
					String text_temp = "text_" + temp;
					String tojsp = "String "+text_temp+" = (String)request.getParameter(\""+Name+"\");";
					bw.write(tojsp);
					bw.newLine();
					if(eElement_txt.hasAttribute("key")){
						
						prim = Name+","+text_temp;
					}
					
					if(temp == 0){
						//System.out.println("maintables");
						jspIds = Name;
						jspVals = text_temp;
					}
					else{
						jspIds = jspIds+"," + Name;
						jspVals = jspVals+","+text_temp;
						
					}
					
					//System.out.println(jspIds);
					
					//System.out.println("This is textbox jspvals"+jspVals);
				}
				
				if(filType.equalsIgnoreCase("htmlfile"))
				{
				if(jsht.equalsIgnoreCase("jshead")){
					
				String inputcheckin = null;
				if(dattype.equalsIgnoreCase("integer")){
					
					inputcheckin = "var c = new RegExp(/^[A-Za-z]+$/);";
					
				}
				if(dattype.equalsIgnoreCase("decimal")){
					inputcheckin = "var b = new RegExp(/^\\s*-?[1-9]\\d*(\\.\\d{1,2})?\\s*$/);";
					
				}
				if(dattype.equalsIgnoreCase("string")){
					
					inputcheckin = "var a = new RegExp(/^\\d+$/);";
					
				}
				bw.write(inputcheckin);
				bw.newLine();
				
				String ifcn = "if(document.domparse."+Name+".value"+"=="+"\"\""+")"+"{"; 
				bw.write(ifcn);
				bw.newLine();
				String alrt = "alert(\"Please enter "+Caption+"\");";
				bw.write(alrt);
				bw.newLine();
				String fls = "return false;}";
				bw.write(fls);
				bw.newLine();
				//String datchk = "if(typeof document.domparse."+Name+".value " + "!='"  + dattype+"'){";
				String checkchos = inputcheckin.substring(4,5);
				String datchk = "if("+checkchos+".test(document.domparse."+Name+".value)){";
				bw.write(datchk);
				bw.newLine();
				String alrt2 = "alert(\"Please enter "+dattype+" in "+Caption+"\");";
				bw.write(alrt2);
				bw.newLine();
				bw.write(fls);
				bw.newLine();
				}
				else
				{
					String txtbx = "<tr><td>"+Caption+"</td><td><input type=\"text\" id = \""+Name+"\" size = \""+txtSize+"\" name=\""+Name+"\" maxlength = \""+maxlen+"\"/></td></tr>"; 
					bw.write(txtbx);
					bw.newLine();
					
				}
			}

				if(filType.equalsIgnoreCase("sqlfile")){
					
					if(dattype.equalsIgnoreCase("string") || dattype == null){
						dattype = "varchar";
					}
					String colheads = null;
					String colheads1 = null;
					if(temp != 0){
					colheads = ","+Name+" " + dattype + "("+txtSize+")";
					colheads1 = colheads.substring(1);
					}
					else{
						
						colheads = Name+" " + dattype + "("+txtSize+")";
						colheads1 = colheads;
					}
					bw.write(colheads);
					if(eElement_txt.hasAttribute("key")){
						//ifkey = eElement_txt.getAttribute("key");
						String primkey = ",PRIMARY KEY ("+ Name + ")";
						bw.write(primkey);
						prim = colheads1;
					}
				}
				
			}
			
		}
		
		
		} 
		catch (DOMException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		return prim;
	}//method end of text boxes code
	
	//method start of check box code
	public static void checkBox(Document doc, BufferedWriter bwc, String incomefile,String primakeye){
		
		
		NodeList nListchk = doc.getElementsByTagName("checkboxes");
		try {
		for (int chktemp = 0; chktemp < nListchk.getLength(); chktemp++) {
			Node nNode_chk = nListchk.item(chktemp);
			
			if (nNode_chk.getNodeType() == Node.ELEMENT_NODE) {
				 
				Element eElement_chk = (Element) nNode_chk;
				//System.out.println("Name : " + eElement_chk.getElementsByTagName("name").item(0).getTextContent());
				//System.out.println("Caption : " + eElement_chk.getElementsByTagName("caption").item(0).getTextContent());
				
				
				String chkopname = null;
				String chkmncap = null;
				
				if(eElement_chk.getElementsByTagName("name").getLength() !=  0){
					chkopname = eElement_chk.getElementsByTagName("name").item(0).getTextContent();
				}
				if(eElement_chk.getElementsByTagName("caption").getLength() !=  0){
					chkmncap = eElement_chk.getElementsByTagName("caption").item(0).getTextContent();
				}
				
				int chkboxlen = eElement_chk.getElementsByTagName("caption").getLength();
					//System.out.println(chkboxlen);
				
				
				if(chkopname == null){
					
					//iferror = "error";
					System.out.println("Terminating!! There is no name for checkbox in the form");
					System.exit(0);
					
				}
				
				if(incomefile.equalsIgnoreCase("htmlfile")){
				String chkname = "<tr><td>"+chkmncap+"</td><td>";
				bwc.write(chkname);
				bwc.newLine();
				}
				
				if(incomefile.equalsIgnoreCase("sqlfile")){	
					bwc.newLine();
					bwc.newLine();
					String titlemain1 = titlemain.replaceAll("\\s","");
					System.out.println(primakeye);
					String[] subpart = primakeye.split(" ");
					String chksql = "CREATE TABLE "+titlemain1+"_"+chkopname+"_2"+chktemp+"("+primakeye+","+chkopname+" varchar(20),PRIMARY KEY ("+subpart[0]+","+chkopname+"),FOREIGN KEY ("+subpart[0]+") REFERENCES "+titlemain1+" ("+subpart[0]+"));";
					bwc.write(chksql);
				}
				
				if(incomefile.equalsIgnoreCase("jspfile")){
					bwc.newLine();
					bwc.newLine();
					String mulchk = "mulchk"+chktemp;
					String mulchkjsp = "if(request.getParameterValues(\""+chkopname+"\")!=null){String [] "+mulchk+" = request.getParameterValues(\""+chkopname+"\");";
					bwc.write(mulchkjsp);
					bwc.newLine();
					String loopvarchk = "loopvarchk"+chktemp;
					String [] primarkeval = primakeye.split(",");
					String titlemain4 = titlemain.replaceAll("\\s","");
					String forloopchk = "for(int "+loopvarchk+"=0;"+loopvarchk+" < mulchk"+chktemp+".length;"+loopvarchk+"++){"
							+"String insstmtchk"+chktemp+" = \"insert into "+titlemain4+"_"+chkopname+"_2"+chktemp+"("+primarkeval[0]+","+chkopname+") values(\\\"\"+"+primarkeval[1]+"+\"\\\",\\\"\"+"+mulchk+"["+loopvarchk+"]+\"\\\")\";";
					bwc.write(forloopchk);
					bwc.newLine();
					String insrtchk = "Statement stmtschk"+chktemp+"=con.createStatement();";
					bwc.write(insrtchk);
					bwc.newLine();
					String exins = "stmtschk"+chktemp+".executeUpdate(insstmtchk"+chktemp+");stmtschk"+chktemp+".close();}}";
					bwc.write(exins);
					bwc.newLine();

					
				}
				
				if(incomefile.equalsIgnoreCase("jspdisp")){
					bwc.newLine();
					bwc.newLine();
					String titlemainchk = titlemain.replaceAll("\\s","");
					String tabldisp_chk = titlemainchk+"_"+chkopname+"_2"+chktemp;
					String tblnam_chk = "<br/><br/><h2>Table Name - "+tabldisp_chk+"</h2><table border=\"1\"><tr>";
					bwc.write(tblnam_chk);
					bwc.newLine();
					String startdisp_chk = "<%Statement stdisp_chk"+chktemp+"=con1.createStatement();";
					bwc.newLine();
					bwc.write(startdisp_chk);
					String resultset_chk = "ResultSet aResultset_chk"+chktemp+" = stdisp_chk"+chktemp+".executeQuery(\"select COLUMN_NAME, DATA_TYPE from INFORMATION_SCHEMA.COLUMNS IC where TABLE_NAME = '"+tabldisp_chk+"'  and table_schema= '"+dbname+"'\");";
					bwc.write(resultset_chk);
					bwc.newLine();
					String whilLoop_chk = "int chk"+chktemp+"=0;while (aResultset_chk"+chktemp+".next()) {"
						  + "String column_namechk"+chktemp+" = aResultset_chk"+chktemp+".getString(\"COLUMN_NAME\");chk"+chktemp+"++;%>"
						  + "<th><%=column_namechk"+chktemp+"%></th>"
					      + "<%}%> </tr><%aResultset_chk"+chktemp+".close();stdisp_chk"+chktemp+".close();%>";
					bwc.write(whilLoop_chk);
					bwc.newLine();
					String datdisp_chk = "<tr><%Statement datdisp_chk"+chktemp+"=con1.createStatement();";
					bwc.write(datdisp_chk);
					bwc.newLine();
					String datresultset_chk = "ResultSet datResultset_chk"+chktemp+" = datdisp_chk"+chktemp+".executeQuery(\"select * from "+tabldisp_chk+"\");";
					bwc.write(datresultset_chk);
					bwc.newLine();
					String dataLoop_chk = "while (datResultset_chk"+chktemp+".next()) {"
							  + "%><tr><%for(int datchk=1;datchk<=chk"+chktemp+";datchk++){String data_chk"+chktemp+" = datResultset_chk"+chktemp+".getString(datchk);%>"
							  + "<td><%=data_chk"+chktemp+"%></td>"
						      + "<%}%></tr><%}%></table><%datResultset_chk"+chktemp+".close();datdisp_chk"+chktemp+".close();%>";
					bwc.write(dataLoop_chk);
					bwc.newLine();
					
				}
				
					
				NodeList nList_chkgrp = eElement_chk.getElementsByTagName("checkboxgroup");
				
				
				for (int chktemp_grp = 0; chktemp_grp < nList_chkgrp.getLength(); chktemp_grp++) {
					Node nNode_chkgrp = nList_chkgrp.item(chktemp_grp);
					
					if (nNode_chkgrp.getNodeType() == Node.ELEMENT_NODE) {
						Element eElement_chkgrp = (Element) nNode_chkgrp;
						
						 int chkbxgrplen = eElement_chkgrp.getElementsByTagName("caption").getLength();
						 //System.out.println(chkbxgrplen);
						 if(chkboxlen == chkbxgrplen){
							 
							 System.out.println("Terminating!! There is no caption for checkbox");
							 System.exit(0);
						 }
					//System.out.println("Name of the node"+nNode_chkgrp.getNodeName());
					if(nNode_chkgrp.getNodeName() == "checkboxgroup")
					{
						
						
						if(nNode_chkgrp.hasChildNodes()){
					NodeList nList_chkbox = nNode_chkgrp.getChildNodes();
					for (int chktemp_chkbx = 0; chktemp_chkbx < nList_chkbox.getLength(); chktemp_chkbx++) {
						Node nNode_chkbx = nList_chkbox.item(chktemp_chkbx);
						if(nNode_chkbx.getNodeName() == "checkbox"){
							
							if(nNode_chkbx.getNodeType() == Node.ELEMENT_NODE){
								if(nNode_chkbx.getNodeName() != "break"){
								Element eElement_chkbx = (Element)nNode_chkbx;
								
								//System.out.println("CheckBoxValue : " + eElement_chkbx.getElementsByTagName("value").item(0).getTextContent());
								//System.out.println("CheckBox_caption : " + eElement_chkbx.getElementsByTagName("caption").item(0).getTextContent());
								String chkopval = eElement_chkbx.getElementsByTagName("value").item(0).getTextContent();
								String chkcap = eElement_chkbx.getElementsByTagName("caption").item(0).getTextContent();
								
								
								String ifchk = null;
								String chkbxs = null;
								if(eElement_chkbx.hasAttribute("status")){
								ifchk = eElement_chkbx.getAttribute("status");
								}
								if(incomefile.equalsIgnoreCase("htmlfile")){
								if(ifchk != null){
								  chkbxs = "<input type=\"checkbox\" name=\""+chkopname+"\" value=\""+chkopval+"\" checked>"+ chkcap+"<br>";
								}
								else
								{
									chkbxs = "<input type=\"checkbox\" name=\""+chkopname+"\" value=\""+chkopval+"\">"+ chkcap+"<br>";
								}
									bwc.write(chkbxs);
									bwc.newLine();
								
								}
								
								}
							}
							
						}
						}
					if(incomefile.equalsIgnoreCase("htmlfile")){
					String chkcls = "</td></tr>";
					bwc.write(chkcls);
					bwc.newLine();
					}
					
					}
						
						
					}
					
					}
					
				}
			}
					
			}
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
	}//method end of check box code
	
	
	public static void radioButton(Document doc, BufferedWriter bwr,String typofile){
		
		//start of radio button
		try {
		NodeList nList_rad = doc.getElementsByTagName("radio");
		//System.out.println(nList_rad.getLength());
		int selrad = 0;
		for (selrad = 0; selrad < nList_rad.getLength(); selrad++) {
					
			Node node_rad = nList_rad.item(selrad);
			if(node_rad.getNodeType() == Node.ELEMENT_NODE){
				Element elem_rad = (Element)node_rad;
				//System.out.println("Name of the select:"+elem_rad.getElementsByTagName("name").item(0).getTextContent());
				//System.out.println("Caption of the select:"+elem_rad.getElementsByTagName("caption").item(0).getTextContent());
				String radmnam = null;
				String radmcap = null;
				if(elem_rad.getElementsByTagName("name").getLength() != 0){
				radmnam = elem_rad.getElementsByTagName("name").item(0).getTextContent();
				}
				
				if(elem_rad.getElementsByTagName("caption").getLength() != 0){
				radmcap = elem_rad.getElementsByTagName("caption").item(0).getTextContent();
				}
				int radmlen = elem_rad.getElementsByTagName("caption").getLength();
				//System.out.println(radmlen);
				if(radmnam == null){
					
					//iferror = "error";
					System.out.println("Terminating!! There is no name for radio button in the form");
					System.exit(0);
				}
				
				
				if(typofile.equalsIgnoreCase("jspfile")){
					
					String radstr = "radtmp"+selrad;
					String radjsp = "String "+radstr+" = (String)request.getParameter(\""+radmnam+"\");";
					bwr.write(radjsp);
					bwr.newLine();
					if(doc.getElementsByTagName("textbox") != null){
					
					jspIds = jspIds+"," + radmnam;
					jspVals = jspVals+"," + radstr;
					}
					else
					{
						if(selrad == 0){	
							jspIds = radmnam;
							jspVals = radstr;
							}
							else{
								
								jspIds = jspIds+"," + radmnam;
								jspVals = jspIds+"," + radstr;
							}
						
					}
					
				}
				
				
				if(typofile.equalsIgnoreCase("htmlfile")){
					String chkname = "<tr><td>"+radmcap+"</td><td>";
					bwr.write(chkname);
				bwr.newLine();
			}
				
				if(typofile.equalsIgnoreCase("sqlfile"))
				{
				
					String sqlrad = ","+radmnam + " varchar(20)";
					bwr.write(sqlrad);
				
				}
				
				NodeList nList_radgrp = elem_rad.getElementsByTagName("radiogroup");
				for(int radtemp = 0; radtemp<nList_radgrp.getLength();radtemp++){
					
					Node node_radgrp = nList_radgrp.item(radtemp);
					if(node_radgrp.getNodeType() == Node.ELEMENT_NODE){
						
						Element radgrp_elem = (Element)node_radgrp;
						int radgrpnum = radgrp_elem.getElementsByTagName("caption").getLength();
						//System.out.println(radgrpnum);
						if(radmlen == radgrpnum){
							
							System.out.println("Terminating!!There is no caption for the radio button in the form");
							System.exit(0);
						}
						
						
						if(node_radgrp.getNodeName() == "radiogroup"){
							if(node_radgrp.hasChildNodes()){
							NodeList nList_radel = node_radgrp.getChildNodes();
							for(int rads_temp = 0; rads_temp<nList_radel.getLength();rads_temp++){
								
								Node radel_node = nList_radel.item(rads_temp);
								if(radel_node.getNodeName() == "radioelement")
								{
								if(radel_node.getNodeType() == Node.ELEMENT_NODE){
									if(radel_node.getNodeName() != "break"){
									Element rads_elem = (Element)radel_node;
									//System.out.println("Value of the option"+rads_elem.getElementsByTagName("value").item(0).getTextContent());
									//System.out.println("Caption of the option"+rads_elem.getElementsByTagName("caption").item(0).getTextContent());
									String radopval = rads_elem.getElementsByTagName("value").item(0).getTextContent();
									String radopcap = rads_elem.getElementsByTagName("caption").item(0).getTextContent();
									
									if(typofile.equalsIgnoreCase("htmlfile"))
									{
									String radbtn = "<input type=\"radio\" name=\""+radmnam+"\" value=\""+radopval+"\" checked>"+ radopcap+"<br>";
									bwr.write(radbtn);
									bwr.newLine();
									}
														
									}
								}
								}
								
							}
							if(typofile.equalsIgnoreCase("htmlfile")){
							String finrdc = "</td></tr>";
							bwr.write(finrdc);
							bwr.newLine();
							}
							}
						}
					}
						
				}
				
			}
			
		}
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
	}//end of radio elements code
	
	//method start of select code
	public static void selectMulti(Document doc, String oper,BufferedWriter bws,String cominFile,String primkey){
		
		try {
		NodeList nList_sel = doc.getElementsByTagName(oper);
		for (int seltemp = 0; seltemp < nList_sel.getLength(); seltemp++) {
					
			Node node_sel = nList_sel.item(seltemp);
			if(node_sel.getNodeType() == Node.ELEMENT_NODE){
				Element elem_sel = (Element)node_sel;
				//System.out.println("Name of the select:"+elem_sel.getElementsByTagName("name").item(0).getTextContent());
				//System.out.println("Caption of the select:"+elem_sel.getElementsByTagName("caption").item(0).getTextContent());
				String selsize = null;
				if(oper == "multiselect"){
					//System.out.println("Size of the multi-select:"+elem_sel.getElementsByTagName("size").item(0).getTextContent());
					selsize = elem_sel.getElementsByTagName("size").item(0).getTextContent();
				}
				String selnam = null;
				String selcap = null;
				if(elem_sel.getElementsByTagName("name").getLength()!=0){
				selnam = elem_sel.getElementsByTagName("name").item(0).getTextContent();
				}
				if(elem_sel.getElementsByTagName("caption").getLength()!=0){
				selcap = elem_sel.getElementsByTagName("caption").item(0).getTextContent();
				}
				int sellen = elem_sel.getElementsByTagName("caption").getLength();
				if(selnam == null){
					
					//iferror = "error";
					System.out.println("Terminating!! There is no name for select or multiselect in the form");
					System.exit(0);
				}
				
				//System.out.println(cominFile);
				if(cominFile.equalsIgnoreCase("jspfile")){
					if(oper.equalsIgnoreCase("select")){
					String selstr = "seltmp"+seltemp;
					String seljsp = "String "+selstr+" = (String)request.getParameter(\""+selnam+"\");";
					bws.write(seljsp);
					bws.newLine();
					if(doc.getElementsByTagName("textbox") != null && doc.getElementsByTagName("radioelement")!=null){
						
							
							jspIds = jspIds+"," + selnam;
							jspVals = jspVals+"," + selstr;
						
						
						}
						else
						{
							if(seltemp == 0){	
								jspIds = selnam;
								jspVals = selstr;
								}
								else{
									
									jspIds = jspIds+"," + selnam;
									jspVals = jspVals+"," + selstr;
								}
							
						}
					
					
					
					
					}
					//System.out.println("this is select"+jspVals);
					if(oper.equalsIgnoreCase("multiselect")){
						bws.newLine();
						bws.newLine();
						//System.out.println("hello into jsp multiselect");
						String mulsel = "mulsel"+seltemp;
						String mulseljsp = "if(request.getParameterValues(\""+selnam+"\")!=null){String [] "+mulsel+" = request.getParameterValues(\""+selnam+"\");";
						bws.write(mulseljsp);
						bws.newLine();
						String loopvar = "loopvar"+seltemp;
						String [] primkeval = primkey.split(",");
						String titlemain3 = titlemain.replaceAll("\\s","");
						String forloop = "for(int "+loopvar+"=0;"+loopvar+" < mulsel"+seltemp+".length;"+loopvar+"++){"
								+"String insstmt"+seltemp+" = \"insert into "+titlemain3+"_"+selnam+"_1"+seltemp+"("+primkeval[0]+","+selnam+") values(\\\"\"+"+primkeval[1]+"+\"\\\",\\\"\"+"+mulsel+"["+loopvar+"]+\"\\\")\";";
						bws.write(forloop);
						bws.newLine();
						String insrt = "Statement stmts"+seltemp+"=con.createStatement();";
						bws.write(insrt);
						bws.newLine();
						String exins = "stmts"+seltemp+".executeUpdate(insstmt"+seltemp+");stmts"+seltemp+".close();}}";
						bws.write(exins);
						bws.newLine();
					}
					
					
				}
				
				
				if(cominFile.equalsIgnoreCase("jspdisp")){
					bws.newLine();
					bws.newLine();
					String titlemaindisp = titlemain.replaceAll("\\s","");
					String tabldisp_sel = titlemaindisp+"_"+selnam+"_1"+seltemp;
					String tblnam_sel = "<br/><br/><h2>Table Name - "+tabldisp_sel+"</h2><table border=\"1\"><tr>";
					bws.write(tblnam_sel);
					bws.newLine();
					String startdisp_sel = "<%Statement stdisp_sel=con1.createStatement();";
					bws.newLine();
					bws.write(startdisp_sel);
					String resultset_sel = "ResultSet aResultset_sel = stdisp_sel.executeQuery(\"select COLUMN_NAME, DATA_TYPE from INFORMATION_SCHEMA.COLUMNS IC where TABLE_NAME = '"+tabldisp_sel+"' and table_schema= '"+dbname+"'\");";
					bws.write(resultset_sel);
					bws.newLine();
					String whilLoop_sel = "int sel=0;while (aResultset_sel.next()) {"
						  + "String column_namesel = aResultset_sel.getString(\"COLUMN_NAME\");sel++;%>"
						  + "<th><%=column_namesel%></th>"
					      + "<%}%> </tr><%aResultset_sel.close();stdisp_sel.close();%>";
					bws.write(whilLoop_sel);
					bws.newLine();
					String datdisp_sel = "<tr><%Statement datdisp_sel=con1.createStatement();";
					bws.write(datdisp_sel);
					bws.newLine();
					String datresultset_sel = "ResultSet datResultset_sel = datdisp_sel.executeQuery(\"select * from "+tabldisp_sel+"\");";
					bws.write(datresultset_sel);
					bws.newLine();
					String dataLoop_sel = "while (datResultset_sel.next()) {"
							  + "%><tr><%for(int datsel=1;datsel<=sel;datsel++){String data_sel = datResultset_sel.getString(datsel);%>"
							  + "<td><%=data_sel%></td>"
						      + "<%}%></tr><%}%></table><%datResultset_sel.close();datdisp_sel.close();%>";
					bws.write(dataLoop_sel);
					bws.newLine();
					
				}
				
				
				String selmname = null;
				if(cominFile.equalsIgnoreCase("htmlfile"))
				{
				if(oper.equalsIgnoreCase("select")){
				selmname = "<tr><td>"+selcap+"</td><td><select name="+selnam+">";
				}
				else
				{
					selmname = "<tr><td>"+selcap+"</td><td><select name="+selnam+" multiple size="+selsize+">";
					
				}
				bws.write(selmname);
				
				bws.newLine();
				}
				
				if(cominFile.equalsIgnoreCase("sqlfile")){
					
					if(oper.equalsIgnoreCase("select")){
					String sel_sql = ","+selnam + " varchar(20)";
					bws.write(sel_sql);
					}
					if(oper.equalsIgnoreCase("multiselect")){
						bws.newLine();
						bws.newLine();
						String titlemain2 = titlemain.replaceAll("\\s","");
						String [] subparts =  primkey.split(" ");
					String suptable = "CREATE TABLE "+titlemain2+"_"+selnam+"_1"+seltemp+"("+primkey+","+selnam+" varchar(20), PRIMARY KEY ("+subparts[0]+","+selnam+"),FOREIGN KEY ("+subparts[0]+") REFERENCES "+titlemain2+" ("+subparts[0]+"));";
					bws.write(suptable);
						
					}
				}
				
				NodeList nList_opts = elem_sel.getElementsByTagName("options");
				for(int opttemp = 0; opttemp<nList_opts.getLength();opttemp++){
					
					Node node_opt = nList_opts.item(opttemp);
					if(node_opt.getNodeType() == Node.ELEMENT_NODE){
						Element optma_elem = (Element)node_opt;
						int optm_len = optma_elem.getElementsByTagName("value").getLength();
						//System.out.println(sellen);
						//System.out.println(optm_len);
						if(optm_len == sellen){
							
							System.out.println("Terminating!!There is no caption for select or multiselect.");
							System.exit(0);
						}
						
						if(node_opt.getNodeName() == "options"){
							if(node_opt.hasChildNodes()){
							NodeList nList_optn = node_opt.getChildNodes();
							for(int opts_temp = 0; opts_temp<nList_optn.getLength();opts_temp++){
								
								Node opts_node = nList_optn.item(opts_temp);
								if(opts_node.getNodeName() == "option")
								{
								if(opts_node.getNodeType() == Node.ELEMENT_NODE){
									if(opts_node.getNodeName() != "break"){
									Element opts_elem = (Element)opts_node;
									//System.out.println("Value of the option"+opts_elem.getElementsByTagName("value").item(0).getTextContent());
									//System.out.println("Caption of the option"+opts_elem.getElementsByTagName("caption").item(0).getTextContent());
									String subselcap = opts_elem.getElementsByTagName("caption").item(0).getTextContent();
									String subselval = opts_elem.getElementsByTagName("value").item(0).getTextContent();
									if(cominFile.equalsIgnoreCase("htmlfile")){
									String radbtn = "<option value=\""+subselval+"\">"+ subselcap+"</option>";
									bws.write(radbtn);
									bws.newLine();
									}
									}
								}
								
								}
							}
							if(cominFile.equalsIgnoreCase("htmlfile")){
							String finsel = "</select></td></tr>";
							bws.write(finsel);
							bws.newLine();
							}
							}
						}
					}	
				}
				
			}
			
		}
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}//method end of select code
	
	//start of submit code
	public static void submit(Document doc,BufferedWriter bwsub){
		NodeList nList_sub = doc.getElementsByTagName("submit");
		
		if(nList_sub.getLength() == 0){
			//iferror = "error";
			System.out.println("Terminating!! There is no submit button in the form");
			System.exit(0);
		}
		
		for (int tempsub = 0; tempsub < nList_sub.getLength(); tempsub++) {
			Node nNode_sub = nList_sub.item(tempsub);
			if (nNode_sub.getNodeType() == Node.ELEMENT_NODE) {
				 
			Element eElement_sub = (Element) nNode_sub;
			//System.out.println("Submit : " + eElement_sub.getElementsByTagName("caption").item(0).getTextContent());
			String sub = eElement_sub.getElementsByTagName("caption").item(0).getTextContent();
			String submt = "<tr><td></td><td><input type=\"submit\" value=\""+sub+"\"></td>";
			try {
				bwsub.write(submt);
				
			} catch (IOException e) {
				
				e.printStackTrace();
			}			
		}
		}
		
	}//end of submit code

}//end of class