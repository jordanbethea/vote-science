# vote-science
voting app using play framework and slick




Accessing the h2 browser, instructions from stack overflow:
To browse the contents of your database via the H2 Web Console, start both the web console and the Play application via the same Play console:

At first, enter the Play console by running the Typesafe Activator:
~/Projects/play-app $ activator
Start the web console:
[play-app] $ h2-browser
This should open the H2 console interface in your browser.
Run the Play app:
[play-app] $ run
Access the Play app in your browser. This will cause the Play application to connect to the in-memory H2 database and to initialize it with some default data, if any.
Log in to the H2 web console in your browser to inspect the database content. Use the following settings which you can save (for instance, as Play-App In-Memory Database) to easily access them again later:
Driver Class: org.h2.Driver
JDBC URL: jdbc:h2:mem:play
User Name: sa
Password: <blank>