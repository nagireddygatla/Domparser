package xmlParsing;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.*;
import java.util.*;

//class started

public class Project3 {

	static String keyname;
	static String keylength;
	static String keytype;

	// global variable

	/**
	 * @param args
	 *            the command line arguments
	 */
	// -----------------------------------------------------------------------------------------------------------------------------
	public static void show(String s) throws IOException { // Function
															// Definition--method
															// show started
		
		try {
			
		
			
			File file;
			file = new File(s);

			// File fXmlFile = new File("/Users/mkyong/staff.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(file);
			

			NodeList nList_chk = doc.getElementsByTagName("textbox");

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


	NodeList chk = doc.getElementsByTagName("checkboxes");
			NodeList sel = doc.getElementsByTagName("multiselect");
			//System.out.println(keypresent);
			if(keypresent == null){
			if(chk.getLength()!=0 || sel.getLength()!=0){
				
				System.out.println("Terminating!!There is no key attribute, but there are checkboxes or multiselect elements");
				System.exit(0);
				
			}
			}
		
			
			Properties properties = new Properties();
			StringBuilder htmlBuilder = new StringBuilder();// string
															// htmlbuilder
			StringBuilder sqlBuilder = new StringBuilder();// string sqlbuilder
			StringBuilder phpBuilder = new StringBuilder();
			StringBuilder phpBuilder1 = new StringBuilder();
			StringBuilder queryBuilder = new StringBuilder();
			StringBuilder valueBuilder = new StringBuilder();
			StringBuilder lastpart = new StringBuilder();
			StringBuilder lastpart1 = new StringBuilder();
			StringBuilder phpnewfile = new StringBuilder();
			StringBuilder validationBuilder = new StringBuilder();// string sqlbuilder
			phpnewfile.append("<?php \n");
			phpnewfile.append("session_start(); \n");
			phpnewfile.append("?> \n");
			phpBuilder.append("<?php \n");
			phpBuilder.append("session_start(); \n");
			phpBuilder.append("?> \n");
			FileInputStream fileInput = new FileInputStream(new File(
					"config.properties"));
			properties.load(fileInput);
			String hostname = properties.getProperty("hostname");
			phpBuilder.append("<?php \n $hostname=\"" + hostname + "\";\n");
			phpnewfile.append("<?php \n $hostname=\"" + hostname + "\";\n");
			String username = properties.getProperty("username");
			phpBuilder.append("$username=\"" + username + "\";\n");
			phpnewfile.append("$username=\"" + username + "\";\n");
			String dbname = properties.getProperty("dbname");
			phpBuilder.append("$dbname=\"" + dbname + "\";\n");
			phpnewfile.append("$dbname=\"" + dbname + "\";\n");
			String password = properties.getProperty("password");
			phpBuilder.append("$password=" + password + ";\n\n");
			phpnewfile.append("$password=" + password + ";\n\n");
			phpnewfile.append("$connection = new mysqli($hostname, $username, $password, $dbname);\n");
						phpnewfile.append("\n if ($connection->connect_error) \n{\n");
			phpnewfile.append("die(" + "\"Connection failed: \""
					+ ". $connection->connect_error);");
			phpnewfile.append("\n }");
			phpnewfile.append("\n$p=\"SELECT * from \"; \n ");
			
			int n=0;
			//phpnewfile.append("$q=array("form",");
			//phpnewfile.append("");	
			fileInput.close();
			String val = null;
		 NodeList select0 = doc.getElementsByTagName("dataInputForm");
			for (int i = 0; i < select0.getLength(); i++) {
				Node dataInputForm_node = select0.item(i);
				if (select0.item(i).getNodeType() == Node.ELEMENT_NODE) {
				Element select_elemen = (Element) dataInputForm_node;
				val =select_elemen.getAttribute("id");
					
					}
				}
			phpnewfile.append("\n $q=array(\"form_"+val+"\",");
			sqlBuilder.append("DROP TABLE IF EXISTS form_"+val+";\n");
			sqlBuilder.append("CREATE TABLE form_"+val);
			
			//***********************************************************************
			
 
			
// selects all
//****************************************************************************************
			
			sqlBuilder.append("(");
			validationBuilder.append("<html>");
			validationBuilder.append("<head></head>");
			validationBuilder.append("<script type=\"text/javascript\">\n");
			validationBuilder.append("function validateForm()\n {");
			
			htmlBuilder.append("<body>");
			
			htmlBuilder.append("<form name=\"myform\" align='center' action='f1.php' method='POST' onsubmit=\"return validateForm()\">");
			
			
			
			
			valueBuilder.append(" values(");
			
			
			lastpart1.append("\n if($conn->query($query) === TRUE){echo ");
			lastpart1.append("'<br>record created for form_"+val+" table<br>';");
			//==================================================================================================
			//code for Id form
			
			//phpBuilder.append();
			
			
			
			
			
			//=================================================================================
			lastpart1.append("\n } \n");
			lastpart1.append("else \n { \n echo");
			lastpart1
					.append("\"error:\" . $query . \"<br><br>\" . $conn->error;\n");
			lastpart1.append("}\n");
			
			lastpart1.append("\n if($conn->query($query1) === TRUE){echo ");
			lastpart1.append("'record created for checkbox_"+val+" table<br>';");
			lastpart1.append("\n } \n");
			lastpart1.append("else \n { \n echo");
			lastpart1
					.append("\"error:\" . $query1 . \"<br><br>\" . $conn->error;\n");
					
			lastpart1.append("}\n ");
			// ================================================================================================================
			// code for xml title
			NodeList select1 = doc.getElementsByTagName("title");// selects all
																	// tagnames

			for (int i = 0; i < select1.getLength(); i++) {
				Node title_node = select1.item(i);
			/* 	System.out.println("Length:" + select1.getLength());
				System.out.println("\n Current Element :"
						+ title_node.getNodeName()); */

				if (title_node.getNodeType() == Node.ELEMENT_NODE) {
					Element title_element = (Element) title_node;
					htmlBuilder.append(title_element
							.getElementsByTagName("caption").item(0)
							.getTextContent());
					htmlBuilder.append("</br></br>");

				}
			}
			// ============================================================================================================

			// code for xml textbox retrieval 
			NodeList select2 = doc.getElementsByTagName("textbox");
			for (int i = 0; i < select2.getLength(); i++) {
				Node textbox_node = select2.item(i);
				StringBuilder keyBuilder = new StringBuilder();
				//System.out.println("\n Current Element :"+ textbox_node.getNodeName());

				if (textbox_node.getNodeType() == Node.ELEMENT_NODE) {
					Element textbox_element = (Element) textbox_node;

					htmlBuilder.append(textbox_element.getElementsByTagName("caption").item(0).getTextContent());
					htmlBuilder.append("<input type='textbox' name='");
					htmlBuilder.append(textbox_element
							.getElementsByTagName("name").item(0)
							.getTextContent());
							
			        validationBuilder.append("var x=document.forms[\"myform\"]");
                     validationBuilder.append("[\"");
					 validationBuilder.append(textbox_element.getElementsByTagName("name").item(0).getTextContent());
					  validationBuilder.append("\"]");
					  validationBuilder.append(".value;\n");
					  
					 validationBuilder.append("if (x == null || x == \"\")");
					 validationBuilder.append("\n");
					  validationBuilder.append("{ alert(\"");
					  validationBuilder.append(textbox_element.getElementsByTagName("caption").item(0).getTextContent());
					  validationBuilder.append(" must be filled\");\n");
				      validationBuilder.append("return false ;\n } \n");
					  
					 // validationBuilder.append()
					StringBuilder textBuilder = new StringBuilder();
					phpBuilder.append("$"
							+ textbox_element.getElementsByTagName("name")
									.item(0).getTextContent() + "=$_POST[\"");
					phpBuilder.append(textbox_element
							.getElementsByTagName("name").item(0)
							.getTextContent()
							+ "\"]; \n");
					queryBuilder.append(textbox_element
							.getElementsByTagName("name").item(0)
							.getTextContent()
							+ ",");
					valueBuilder.append("'$"
							+ textbox_element.getElementsByTagName("name")
									.item(0).getTextContent() + "',");

					// code keyvalue name retrieval
					keyBuilder.append(((Element) textbox_node)
							.getAttributeNode("key"));
					String keyvalue = keyBuilder.toString();
					String var1 = "1more";
					String var = var1.concat(keyvalue);
				//	System.out.println(var);
					if (!var.equals("1morenull")) {
			/* 			System.out.println("name="
								+ (textbox_element.getElementsByTagName("name")
										.item(0).getTextContent())); */
						StringBuilder keyname1 = new StringBuilder();
						keyname1.append((textbox_element.getElementsByTagName(
								"name").item(0).getTextContent()));
						keyname = keyname1.toString();
						StringBuilder keyname2 = new StringBuilder();
						keyname2.append((textbox_element.getElementsByTagName(
								"maxlength").item(0).getTextContent()));
						keylength = keyname2.toString();
						StringBuilder keytype1 = new StringBuilder();
						if ((select2.item(i).getAttributes()
								.getNamedItem("datatype").getNodeValue())
								.equals("integer")) {
							keytype1.append("int");
						} else if ((select2.item(i).getAttributes()
								.getNamedItem("datatype").getNodeValue())
								.equals("string")) {
							keytype1.append("varchar");
						}

						// keytype1.append(select2.item(i).getAttributes().getNamedItem("datatype").getNodeValue());
						keytype = keytype1.toString();

					}
					// code keyvalue name retrieval ended
					htmlBuilder.append("'");
					htmlBuilder.append("maxlength='");
					htmlBuilder.append(textbox_element
							.getElementsByTagName("maxlength").item(0)
							.getTextContent());
					htmlBuilder.append("'");
					htmlBuilder.append("size='");
					htmlBuilder.append(textbox_element
							.getElementsByTagName("size").item(0)
							.getTextContent());
					htmlBuilder.append("'");
					htmlBuilder.append(">");

					htmlBuilder.append("</br></br>");

					sqlBuilder.append(textbox_element
							.getElementsByTagName("name").item(0)
							.getTextContent());
					// System.out.println(textbox_element.getElementsByTagName("name").item(0).getTextContent());
					sqlBuilder.append(" ");
					if ((select2.item(i).getAttributes()
							.getNamedItem("datatype").getNodeValue())
							.equals("integer")) {
						sqlBuilder.append("int");
					} else if ((select2.item(i).getAttributes()
							.getNamedItem("datatype").getNodeValue())
							.equals("string")) {
						sqlBuilder.append("varchar");
					}

					sqlBuilder.append("("
							+ textbox_element.getElementsByTagName("maxlength")
									.item(0).getTextContent() + ")");
					sqlBuilder.append(" NOT NULL");

					sqlBuilder.append(",\n");

				}

			}
			// =============================================================================================================================================
			                //  checkbox query insertion code
			StringBuilder insertcheckBuilder1 = new StringBuilder();
			StringBuilder checkboxbuilder = new StringBuilder();
			StringBuilder insertcheckBuilder2 = new StringBuilder();
						//===========================================================================================
			/* NodeList select99 = doc.getElementsByTagName("checkboxes");
			for (int i = 0; i < select99.getLength(); i++) {
			Node checkboxes_node = select99.item(i);
			
            if (checkboxes_node.getNodeType() == Node.ELEMENT_NODE) {
				
			Element checkboxes_element = (Element) checkboxes_node;	
				
			String checkbox_name=checkboxes_element.getElementsByTagName("name").item(i).getTextContent();
					System.out.println(checkbox_name);	
				
				
			
			} */
			//============================================================================================
			String checkinsert;
			String checkinsert2;
			Element checkboxes_element = null;
			NodeList nListchk = doc.getElementsByTagName("checkboxes");
			
			for (int i = 0; i < nListchk.getLength(); i++) {
				Node nNode_chk = nListchk.item(i);
				System.out.println("iteration"+i);
				if (nNode_chk.getNodeType() == Node.ELEMENT_NODE) {
					 
					checkboxes_element = (Element) nNode_chk;
					System.out.println("hello");
					String checkbox_name=checkboxes_element.getElementsByTagName("name").item(0).getTextContent();
					
							
					System.out.println("This is name"+checkbox_name);
					phpnewfile.append("\"checkbox_"+val+"\"");
								//n=n+1;
					
					htmlBuilder.append(checkboxes_element
							.getElementsByTagName("caption").item(0)
							.getTextContent());
					
					

					phpBuilder.append("$"
							+ checkboxes_element.getElementsByTagName("name")
									.item(0).getTextContent() + "=$_POST[\"");
					phpBuilder.append(checkboxes_element
							.getElementsByTagName("name").item(0)
							.getTextContent()
							+ "\"]; \n");
					phpBuilder1.append(checkboxes_element
							.getElementsByTagName("name").item(0)
							.getTextContent());
					phpBuilder1.append(");");

					htmlBuilder.append("</br>");
					System.out.println("before check group"+checkboxes_element.getElementsByTagName("checkboxgroup"));
					NodeList select31 = checkboxes_element.getElementsByTagName("checkboxgroup");
					for (int i1 = 0; i1 < select31.getLength(); i1++) {
						Node checkboxgroup_node = select31.item(i1);
						
						if (checkboxgroup_node.getNodeType() == Node.ELEMENT_NODE) {
							System.out.println("hello");
							Element checkboxes_element_chk = (Element) checkboxgroup_node;
							NodeList select312 = checkboxes_element_chk
									.getElementsByTagName("checkbox");
							
							/* System.out.println("CheckBox Length"
									+ select312.getLength()); */
							for (int j = 0; j < select312.getLength(); j++) {
								Node checkbox_node = select312.item(j);
/* 
								System.out.println("\n Current Element :"
										+ checkbox_node.getNodeName()); */
								if (checkbox_node.getNodeType() == Node.ELEMENT_NODE) {

									Element checkbox_element = (Element) checkbox_node;
									htmlBuilder.append(checkbox_element
											.getElementsByTagName("caption")
											.item(0).getTextContent());
									htmlBuilder
											.append("<input type='checkbox' name='");
									htmlBuilder.append(checkboxes_element
											.getElementsByTagName("name")
											.item(0).getTextContent());

									htmlBuilder.append("[]' value='"
											+ checkbox_element
													.getElementsByTagName(
															"value").item(0)
													.getTextContent());

									htmlBuilder.append("'");
									
									StringBuilder statusBuilder = new StringBuilder();
									statusBuilder
											.append(((Element) checkbox_node)
													.getAttributeNode("status"));

									String status = statusBuilder.toString();
									String status1 = "1more";
									String status2 = status1.concat(status);
								//	System.out.println(status2);
									if (!status2.equals("1morenull")) {
										htmlBuilder.append(select312.item(j)
												.getAttributes()
												.getNamedItem("status")
												.getNodeValue());

									}
									htmlBuilder.append(">");
									htmlBuilder.append("</br>");

								}
							}

						}
					}
				}
				insertcheckBuilder1.append("\n $query1"+i+"=(\"INSERT INTO checkbox_"+val+""+i+"(");
				insertcheckBuilder1.append(keyname);
				insertcheckBuilder1.append(",");
				insertcheckBuilder1.append("checkboxes_name,");
				insertcheckBuilder1.append("checkboxes_value)");
				insertcheckBuilder1.append(" values('");
				insertcheckBuilder1.append("$" + keyname + "'" + ",");
				insertcheckBuilder1.append(checkboxes_element
						.getElementsByTagName("name").item(0)
						.getTextContent());
				
				
				
				
				phpBuilder1.append("$array = implode(',',$");// courseno;
	//--------------------------------------------------------------------------------------------------------------------------------------------------------

				// -----------------------------------------------------------------------------------------------------------------------------------------------
				// code for checkboxes
				htmlBuilder.append("</br>");
				
				
				checkboxbuilder
						.append(";\n DROP TABLE IF EXISTS checkbox_"+val+""+i+"; \n CREATE TABLE checkbox_"+val+""+i+"( ");
				checkboxbuilder.append(keyname + " " + keytype);

				checkboxbuilder.append("(" + keylength + ") NOT NULL, \n");
				
				
				checkboxbuilder.append("checkboxes_name varchar(200) NOT NULL,\n");
				checkboxbuilder.append("checkbox_value varchar(200) NOT NULL,\n");

				insertcheckBuilder1.append("'$array')\");");

				checkboxbuilder.append("CONSTRAINT FK_checkbox_"+val+""+i+" FOREIGN KEY (");
				checkboxbuilder.append(keyname);
				checkboxbuilder.append(")");
				checkboxbuilder.append("REFERENCES form_"+val+""+i+" (");
				checkboxbuilder.append(keyname);
				checkboxbuilder.append(")");


			}
			
			
			

						// ------------------------------------------------------------------------------------------

			
			checkinsert = insertcheckBuilder1.toString();
			
			checkinsert2 = insertcheckBuilder2.toString();

			// =========================================================================================================================================

			htmlBuilder.append("</br>");
		
			NodeList select4 = doc.getElementsByTagName("select");
			for (int i = 0; i < select4.getLength(); i++) {
				Node select_node = select4.item(i);
				/* System.out.println("\n Current Element :"
						+ select_node.getNodeName()); */
				if (select_node.getNodeType() == Node.ELEMENT_NODE) {
					Element select_element = (Element) select_node;
					htmlBuilder.append(select_element
							.getElementsByTagName("caption").item(0)
							.getTextContent());
					htmlBuilder.append("<select name ='");
					htmlBuilder.append(select_element
							.getElementsByTagName("name").item(0)
							.getTextContent());
					htmlBuilder.append("'");
					htmlBuilder.append(">");
					phpBuilder.append("$"
							+ select_element.getElementsByTagName("name")
									.item(0).getTextContent() + "=$_POST[\"");
					phpBuilder.append(select_element
							.getElementsByTagName("name").item(i)
							.getTextContent()
							+ "\"]; \n");
					queryBuilder.append(select_element
							.getElementsByTagName("name").item(i)
							.getTextContent()
							+ ",");
					valueBuilder.append("'$"
							+ select_element.getElementsByTagName("name")
									.item(i).getTextContent() + "',");
					htmlBuilder.append("</br></br>");
					NodeList select41 = select_element.getElementsByTagName("options");
					for (int i1 = 0; i1 < select41.getLength(); i1++) {
						Node options_node = select41.item(i1);
						if (options_node.getNodeType() == Node.ELEMENT_NODE) {
							Element options_element = (Element) options_node;
							NodeList option412 = options_element
									.getElementsByTagName("option");
							/* System.out.println("option Length"
									+ option412.getLength()); */
							
							for (int j = 0; j < option412.getLength(); j++) {
								Node option_node = option412.item(j);

							/* 	System.out.println("\n Current Element :"
										+ option_node.getNodeName()); */
								if (option_node.getNodeType() == Node.ELEMENT_NODE) {

									Element option_element = (Element) option_node;

									htmlBuilder.append("<option value='");
									htmlBuilder.append(option_element
											.getElementsByTagName("value")
											.item(0).getTextContent());
									htmlBuilder.append("'");
									htmlBuilder.append(">");
									htmlBuilder.append(option_element
											.getElementsByTagName("caption")
											.item(0).getTextContent());
									htmlBuilder.append("</option>");

								}
							}

						}
					}

					htmlBuilder.append("</select> <br></br>");
					sqlBuilder.append(select_element
							.getElementsByTagName("name").item(0)
							.getTextContent());
					sqlBuilder.append(" varchar(200)");
					sqlBuilder.append(" NOT NULL");
					sqlBuilder.append(",\n");
				}
			}
			// =================================================================================================================
			// code for radio buttons
			NodeList select5 = doc.getElementsByTagName("radio");
			for (int i = 0; i < select5.getLength(); i++) {
				Node select5_node = select5.item(i);
				/* System.out.println("\n Current Element :"
						+ select5_node.getNodeName()); */
				if (select5_node.getNodeType() == Node.ELEMENT_NODE) {
					Element select5_element = (Element) select5_node;

					htmlBuilder.append(select5_element
							.getElementsByTagName("caption").item(0)
							.getTextContent());

					NodeList select51 = doc.getElementsByTagName("radiogroup");
					for (int i1 = 0; i1 < select51.getLength(); i1++) {
						Node radiogroup_node = select51.item(i1);
						if (radiogroup_node.getNodeType() == Node.ELEMENT_NODE) {
							NodeList radioelement512 = doc
									.getElementsByTagName("radioelement");
							/* System.out.println("radioelement Length"
									+ radioelement512.getLength()); */

							for (int j = 0; j < radioelement512.getLength(); j++) {
								Node radioelement_node = radioelement512
										.item(j);
/* 
								System.out.println("\n Current Element :"
										+ radioelement_node.getNodeName()); */
								if (radioelement_node.getNodeType() == Node.ELEMENT_NODE) {

									Element radioelement_element = (Element) radioelement_node;
									//System.out.println("hai");
									htmlBuilder.append("</br>");
									htmlBuilder.append("<input type='radio'  ");
									htmlBuilder.append("name='");
									htmlBuilder.append(select5_element
											.getElementsByTagName("name")
											.item(0).getTextContent()
											+ "' ");
									htmlBuilder.append(" value='");
									htmlBuilder.append(radioelement_element
											.getElementsByTagName("value")
											.item(0).getTextContent());
									htmlBuilder.append("' checked");
									htmlBuilder.append(">");
									// htmlBuilder.append("</br>");

									htmlBuilder.append(radioelement_element
											.getElementsByTagName("caption")
											.item(0).getTextContent());

								}

							}

						}
					}
					sqlBuilder.append(select5_element
							.getElementsByTagName("name").item(0)
							.getTextContent());
					phpBuilder.append("$"
							+ select5_element.getElementsByTagName("name")
									.item(0).getTextContent() + "=$_POST[\"");
					phpBuilder.append(select5_element
							.getElementsByTagName("name").item(0)
							.getTextContent()
							+ "\"]; \n");
					queryBuilder.append(select5_element
							.getElementsByTagName("name").item(0)
							.getTextContent()
							+ ",");
					valueBuilder.append("'$"
							+ select5_element.getElementsByTagName("name")
									.item(0).getTextContent() + "',");
					sqlBuilder.append(" varchar(200)");
					sqlBuilder.append(" NOT NULL");

					sqlBuilder.append(",\n");

				}
			}
			// ==================================================================================================================
		
			
			//-------------------------------------------------------------------
                           //multiselect query insertion code
						   

         StringBuilder phpmultiselectBuilder2 = new StringBuilder();
			phpmultiselectBuilder2.append(",");
			
			StringBuilder mutliboxbuilder = new StringBuilder();
			StringBuilder phpmultiselectBuilder1 = new StringBuilder();
			
			
			
			
			
			
			
			
			//------------------------------------------------------------------------------------------------------------------------------------
			// code for multi select
			NodeList select7 = doc.getElementsByTagName("multiselect");
			for (int i = 0; i < select7.getLength(); i++) {
				Node select_node = select7.item(i);
			/* 	System.out.println("\n Current Element :"
						+ select_node.getNodeName()); */
				if (select_node.getNodeType() == Node.ELEMENT_NODE) {
					phpnewfile.append(",\"multibox_"+val+"\"");
					n=n+1;
					
					
					Element select_element = (Element) select_node;
					htmlBuilder.append(select_element
							.getElementsByTagName("caption").item(0)
							.getTextContent());
					htmlBuilder.append("<select name ='");
					htmlBuilder.append(select_element
							.getElementsByTagName("name").item(0)
							.getTextContent()+"[]");
							   
			                   phpmultiselectBuilder1.append("\n $query2=\"INSERT INTO multibox_"+val+"(");
			                   phpmultiselectBuilder1.append(keyname);
			                   phpmultiselectBuilder1.append(",");
			           phpBuilder1.append("$array1 = implode(',',$");
						phpmultiselectBuilder1.append("multibox_name");
							phpBuilder1.append(select_element
							.getElementsByTagName("name").item(0)
							.getTextContent());
							 phpBuilder1.append(");");
							
			//===========================================================================================================================
			                //  phpnewfile.append("\"multibox\""); 
			
			
                             phpmultiselectBuilder1.append(",");
							phpmultiselectBuilder1.append("multi_values");
								phpmultiselectBuilder1.append(")");
								phpmultiselectBuilder1.append(" values('");
								phpmultiselectBuilder1.append("$" + keyname + "'" + ",'");
								phpmultiselectBuilder1.append(select_element.getElementsByTagName("name").item(0).getTextContent());
			                   phpmultiselectBuilder1.append("',");				
			                    phpmultiselectBuilder1.append("'$array1'");// semester;
						
							 						
					htmlBuilder.append("'");
					htmlBuilder.append("multiple=\"multiple\" size='");
					htmlBuilder.append("size");
					htmlBuilder.append("'");
					htmlBuilder.append(">");
					htmlBuilder.append("</br></br>");
					NodeList select71 = select_element.getElementsByTagName("options");
					for (int i1 = 0; i1 < select71.getLength(); i1++) {
						Node options_node = select71.item(i1);
						if (options_node.getNodeType() == Node.ELEMENT_NODE) {
							Element options_element = (Element) options_node;
							NodeList option712 = options_element
									.getElementsByTagName("option");
						/* 	System.out.println("option Length"
									+ option712.getLength()); */
							
							mutliboxbuilder
									.append(";\n \n DROP TABLE IF EXISTS multibox_"+val+"; \n CREATE TABLE multibox_"+val+"( ");

							mutliboxbuilder.append(keyname + " " + keytype);

							mutliboxbuilder.append("(" + keylength + ") NOT NULL, \n");
							mutliboxbuilder
									.append("multibox_name varchar(200) NOT NULL,\n multi_values varchar(200) NOT NULL,\n");
							mutliboxbuilder.append("CONSTRAINT FK_multibox_"+val+" FOREIGN KEY (");
							mutliboxbuilder.append(keyname);
							mutliboxbuilder.append(")");
							mutliboxbuilder.append("REFERENCES form_"+val+" (");
							mutliboxbuilder.append(keyname);
							mutliboxbuilder.append("));");
							

							for (int j = 0; j < option712.getLength(); j++) {
								Node option_node = option712.item(j);

						/* 		System.out.println("\n Current Element :"
										+ option_node.getNodeName()); */
								if (option_node.getNodeType() == Node.ELEMENT_NODE) {

									Element option_element = (Element) option_node;

									htmlBuilder.append("<option value='");
									htmlBuilder.append(option_element
											.getElementsByTagName("value")
											.item(0).getTextContent());
									htmlBuilder.append("'");
									htmlBuilder.append(">");
									htmlBuilder.append(option_element
											.getElementsByTagName("caption")
											.item(0).getTextContent());
									htmlBuilder.append("</option>");

								}
							}

						}
					}
					htmlBuilder.append("</select> <br></br>");
					/* sqlBuilder.append(select_element
							.getElementsByTagName("name").item(0)
							.getTextContent()); */
					phpBuilder.append("$"
							+ select_element.getElementsByTagName("name")
									.item(0).getTextContent() + "=$_POST[\"");
					phpBuilder.append(select_element
							.getElementsByTagName("name").item(0)
							.getTextContent()
							+ "\"]; \n");
			/* 		queryBuilder.append(select_element
							.getElementsByTagName("name").item(0)
							.getTextContent()
							+ ","); */
				/* 	valueBuilder.append("'$"
							+ select_element.getElementsByTagName("name")
									.item(0).getTextContent() + "',"); */

					/* sqlBuilder.append(" varchar(200)");
					sqlBuilder.append(" NOT NULL");

					sqlBuilder.append(",\n"); */
					
					
					lastpart.append(")\";");
					
					lastpart1.append("\n if($conn->query($query2) === TRUE){echo ");
			lastpart1.append("'record created for multibox_"+val+"';");
			lastpart1.append("\n } \n");
			lastpart1.append("else \n { \n echo");
			lastpart1
					.append("\"error:\" . $query2 . \"<br><br>\" . $conn->error;\n");
			lastpart1.append("}\n ");
			lastpart1.append("$conn->close(); \n\n?>");
				}
			}

			// ===================================================================================================================
			
			
			// sql multiselect table
			
             //php multiselect query insertion 
			
			 String multibox; 
							multibox = mutliboxbuilder.toString();
							
							//second php file3w2q1	`
							phpnewfile.append("); \n");
							phpnewfile.append("$n=");
							phpnewfile.append(n);
							phpnewfile.append(";");
							phpnewfile.append("\n");
					phpnewfile.append("\n for($i=0;$i<=$n;$i++)");
					phpnewfile.append("\n { \n ");
				phpnewfile.append("$query=$p.$q[$i]; \n");
				phpnewfile.append("echo $query; \n");
					phpnewfile.append("\n if ($res=mysqli_query($connection,$query)) \n {");
				phpnewfile.append("\n $count=mysqli_num_fields($res);");
				phpnewfile.append("\n printf(\"<table  border=1><tr>\");");
				phpnewfile.append("while ($col=mysqli_fetch_field($res)) \n {");
				phpnewfile.append("echo \"<th>$col->name</th>\"; \n }");
                           phpnewfile.append("\n printf (\"</tr>\");");
						   
                                phpnewfile.append("\n while ($value=mysqli_fetch_row($res))");
								 phpnewfile.append("\n { \n  printf (\"<tr>\");");
								  phpnewfile.append("");
						 phpnewfile.append("\n for($j=0; $j<$count; $j++)");
						  phpnewfile.append("\n { \n printf(\"<td>\"); \n echo \"$value[$j]\"; \n ");
	            phpnewfile.append("\n  printf(\"</td>\"); \n } \n printf(\"</tr>\"); \n } \n printf(\"</table>\"); \n mysqli_free_result($res); \n} \n}");
						 
                phpnewfile.append("mysqli_close($connection)\n");
                phpnewfile.append("?>");
	            String phpnew;
				phpnew=phpnewfile.toString();
				FileWriter fileWrit = new FileWriter("f2.php");
			    BufferedWriter bufferedWrit = new BufferedWriter(fileWrit);
			    bufferedWrit.write(phpnew);
			    bufferedWrit.close();
			
								
							 
							
		     //multi
							
					
			
			//==================================================================================================================
			
			//String checkinsert;
			//checkinsert = phpmultiselectBuilder2.appendtoString();
			String phpmulti1 ;
			String phpmulti2 ;
			phpmulti1=phpmultiselectBuilder1.toString();
			phpmulti2 = phpmultiselectBuilder2.toString();
		//	System.out.println("PHPMULTI"+phpmulti2);
			

			// ======================================================================================================================
			// code for submit button
			NodeList select6 = doc.getElementsByTagName("submit");
			for (int i1 = 0; i1 < select6.getLength(); i1++) {
				Node select6_node = select6.item(i1);
				/* System.out.println("\n Current Element :"
						+ select6_node.getNodeName()); */
				if (select6_node.getNodeType() == Node.ELEMENT_NODE) {
					Element select6_element = (Element) select6_node;
					htmlBuilder.append("<br></br>");
					htmlBuilder.append("<input type='submit' value='");
					htmlBuilder.append(select6_element
							.getElementsByTagName("caption").item(0)
							.getTextContent()
							+ "'>");

					htmlBuilder.append("</br></br></br>");

				}
			}
			

			// ================================================================================================================================
			// code for show tables button
       		//htmlBuilder.append("<button onclick='f12.php'>show tables</button>");
			//htmlBuilder.append("<form align='center' action='f12.php' method='POST'>");
				htmlBuilder.append("</form>");
			htmlBuilder.append("<form action='f2.php' method='POST'>");
			htmlBuilder.append("<input type='submit' value='show table'>");
		
			validationBuilder.append("\n}\n");
			validationBuilder.append("</script>");
			String valid;
		
			valid = validationBuilder.toString();
			
			
			String query;
			String result1;
			query = queryBuilder.toString();
			//System.out.println(query);
			result1 = query.substring(0, query.length() - 1);

			//System.out.println(result1);

			StringBuilder result = new StringBuilder();

			result.append(result1);
			result.append(")");

			
			
			
			// =============================================================================================================================
			// all string builders are converted to string

			phpBuilder
					.append("$conn = new mysqli($hostname, $username, $password, $dbname);\n");
					

			StringBuilder phpBuilder2 = new StringBuilder();

			phpBuilder2.append("\n if ($conn->connect_error) \n{\n");
			phpBuilder2.append("die(" + "\"Connection failed: \""
					+ ". $conn->connect_error);");
			phpBuilder2.append("\n }");
			phpBuilder2.append("\n$query=");
			phpBuilder2.append("\"INSERT INTO form_"+val+"(");
			
			// =======================================================================================================================================

			// ===========================================================================================================================================
			// phpBuilder.append()
			String value = valueBuilder.toString();
			//System.out.println(value);
			// ====================================================
			String value1;
			value1 = value.substring(0, value.length() - 1);
			String value2=value1+")\";";

	

			// =================================================
			htmlBuilder.append("</form>");
			htmlBuilder.append("</h2>");
			htmlBuilder.append("</body>");
			htmlBuilder.append("</html>");
			// -----------------------------------------
			checkboxbuilder.append(")");
			String check = checkboxbuilder.toString();

			//valueBuilder.append(")\";");
			// ------------------------------------------------

			// ==================================================
			String html = htmlBuilder.toString();
			FileWriter fileWriter = new FileWriter("f1.html");
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			bufferedWriter.write(valid);
			bufferedWriter.write(html);
			bufferedWriter.close();
			sqlBuilder.append("PRIMARY KEY " + "(");
			sqlBuilder.append(keyname);
			sqlBuilder.append(")" + "\n" + ")");
			String sql = sqlBuilder.toString();
			BufferedWriter bufferedWrite = new BufferedWriter(new FileWriter(
					"f1.sql"));
			bufferedWrite.write(sql);
			bufferedWrite.write(check);
			bufferedWrite.write(multibox);
			//System.out.println(multibox);

			bufferedWrite.close();

			String phpcode = phpBuilder.toString();
			String phpcode1 = phpBuilder1.toString();
			String phpcode2 = phpBuilder2.toString();
			String res = result.toString();
			String last = lastpart.toString();
			String last1 = lastpart1.toString();
			FileWriter fileWriter1 = new FileWriter("f1.php");
			BufferedWriter bufferedWriter1 = new BufferedWriter(fileWriter1);
			bufferedWriter1.write(phpcode);
			bufferedWriter1.write(phpcode1);
			bufferedWriter1.write(phpcode2);
			bufferedWriter1.write(res);
			bufferedWriter1.write(value2);
			bufferedWriter1.write(checkinsert);
			bufferedWriter1.write(checkinsert2);
			bufferedWriter1.write(phpmulti1);
			//bufferedWriter1.write(phpmulti2);
			bufferedWriter1.write(last);
			bufferedWriter1.write(last1);
		
			//bufferedWriter1.writ e(res);

			bufferedWriter1.close();
			
			//System.out.println(phpmulti);
           // System.out.println("n="+n);


		} catch (Exception e) {
			System.out.println("in catch" + e);
		}
	} // method end

	public static void main(String[] args) throws IOException {
		// TODO code application logic here

		//for (String s : args) {
			//System.out.println("Input Form Name:" + s);
			show("form_correct.xml");
//		}
	}
}
