# Movies
Golden Raspberry Awards

java version "19.0.1" 2022-10-18

How to run:

Select src/main/java, right click
Run as: Java Application

![image](https://user-images.githubusercontent.com/24591804/212768974-debc577a-5feb-4f23-a420-283792f123fe.png)


How to test:

Select src/test/java, right click
Run as: JUnit Test


![image](https://user-images.githubusercontent.com/24591804/212769213-3442485c-7a8c-4392-b432-a415c9ac8415.png)


Data must be located at /texo/src/main/resources and filename must be movielist.csv


![image](https://user-images.githubusercontent.com/24591804/212769564-6803a911-80eb-47c9-9a07-f5d8b19379d2.png)


Since h2 database has some limitations and for test purposes the csv file pattern must be:

id, releaseYear, title, studios, producers, winner
3,1980,"The Formula","MGM/United Artists","Steve Shagan",

The winner values must be "yes", "no", and it can be empty, in the example above, it is empty.
Notice the field "studios" after MGM has a "/" this is because the h2 database considers 3 characters followed by a white space as a new field
