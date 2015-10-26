CREATE TABLE JobApplicationPage(firstname varchar(20),lastname varchar(20),ssn integer(9),PRIMARY KEY (ssn),gender varchar(20),jobtype varchar(20));

CREATE TABLE JobApplicationPage_fields_20(ssn integer(9),fields varchar(20),PRIMARY KEY (ssn,fields),FOREIGN KEY (ssn) REFERENCES JobApplicationPage (ssn));

CREATE TABLE JobApplicationPage_city_21(ssn integer(9),city varchar(20),PRIMARY KEY (ssn,city),FOREIGN KEY (ssn) REFERENCES JobApplicationPage (ssn));