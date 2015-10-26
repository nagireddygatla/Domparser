# Domparser

This project reads XML files which are similar to HTML forms using DOMParser.

Basing on these XML tags, it creates 4 files:

1. One HTML file to take user inputs.
2. One SQL Create Query file corresponding to the input HTML form
3. One JSP file which inserts inputs into database
4. One JSP file to display inserted data from database

The application also has capability to detect bad XML files, as an XML schema is built.
