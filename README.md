# Assignment
Calculate Relative Account Balance for a given account.

 # How to guide
 
   ### Run Tests  
   
   $ mvn test
   
  ### Build jar
   
   $ mvn package
    
   The project loads data from CSV file residing under resources/transactions.csv.
   
   Above command builds a jar file under target/mebank-1.0-SNAPSHOT.jar location.
   
   1. Run the jar file from command line
   
      $ java -jar target/mebank-1.0-SNAPSHOT.jar
   
   2. You'll be prompted with 3 user inputs one after the other as shown below:
   
      Enter accountId: ACC334455
    
      Enter fromDate: 20/10/2018 12:47:55
    
      Enter toDate: 20/10/2018 19:00:00
    
    
    
  Output:
  
   Relative Balance for the period is: -$25.00
  
   Number of transactions included is: 1

    
    
    
    
