
Instructions to run :
After unzipping the file, please run playground.bat

Notes:
1)Playground configuration details are available in application.properties file.By default 4 types of playsite is used, to remove any site please use installation
	number as zero.
	
2)When the application is running , following rest endpoints can be used to see the reports.
  i)history on what play sites kid played, and how long - http://localhost:8080/playground/history 
  ii)total visitor count during a day on all registered play sites - http://localhost:8080/playground/visitorCount
  iii)play site utilization snapshots taken during the working hours - http://localhost:8080/playground/utilization
  iv)To add a kid to playsite - http://localhost:8080/playground/addKid/{playsite} 
		Kid Object :
		
		{
			"name":"Test",
			"age":12,
			"ticketNumber":425473747,
			"isVip":true
		}
3)Vip feature is implemented. For running locally , by default few kids are added to playsite.
4)project can be imported in any IDE. 

Please let me know if you face any problem accessing the location.


