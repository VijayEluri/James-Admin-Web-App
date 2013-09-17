/home/ericm/jarFiles/apache-tomcat-7.0.22/bin/shutdown.sh    

rm -v /home/ericm/jarFiles/apache-tomcat-7.0.22/logs/*   

rm -rvf /home/ericm/jarFiles/apache-tomcat-7.0.22/webapps/james_admin_struts2*   

mvn clean package    

cp -v /home/ericm/github/James-Admin-Web-App/struts2/target/james_admin_struts2.war  /home/ericm/jarFiles/apache-tomcat-7.0.22/webapps/   

/home/ericm/jarFiles/apache-tomcat-7.0.22/bin/startup.sh    

#- EOF