# library-management-system

**With angular:**
1. Make sure node.js is installed.
2. Check node is installed. Use command 'node -v' . It should printout node version. Mine is v14.16.0
3. Go to directory: 'src\main\resources\frontend\angular-app' and run 'npm install @angular/cli' command to install angular
4. Navigate to parent folder and run command 'mvn spring-boot:run'
5. It will take a while when you execute above command first time.

**Without Angular installation**
1. Code base already have latest compiled angular files, so you can directly run spring boot app.
2. Comment out following plugin from pom.file which executes angular command:
   `<plugin>
       <groupId>org.codehaus.mojo</groupId>
       <artifactId>exec-maven-plugin</artifactId>
       <version>1.6.0</version>
       <executions>
           <execution>
               <phase>validate</phase>
               <goals>
                <goal>exec</goal>
               </goals>
           </execution>
       </executions>
       <configuration>
           <executable>ng</executable>
           <workingDirectory>src\main\resources\frontend\angular-app</workingDirectory>
           <arguments>
            <argument>build</argument>
           </arguments>
       </configuration>
   </plugin>`
3. Run command 'mvn spring-boot:run'


**Technologies:**
1. Java
2. Spring Boot: 2.4.3
3. Spring Data JPA
4. JUnit: 5
5. Mockito: 3.6
6. Angular: 11
7. Boostrap: 4.6
8. Database: in-memory H2

** How to run test cases **
1. Run 'mvn test' command from cmd
2. Test cases will also get executed while creating build

**How to run:**
1. Open command prompt
2. Go to project parent folder
3. Run command: mvn spring-boot:run
4. Hit http://localhost:8080/ in your favourite browser 

**Functionality provided:**
1. 3 users and 3 books already loaded in database on application start-up
2. User(Librarian) can create a register request by selecting user, entry type (BORROW or RETURN) and book.
3. Any user at any time can only borrow 2 books.
4. Any user at any time can not have multiple copies of same book.
5. On RETURN request, librarian will be able to see only borrowed list of books, borrowed by selected user.

**Database Relationship:**
1. RegisterEntry --> parent table used to log specific type of request either BORROW or RETURN by User
2. BookEntry --> Number of books user borrowed/returned as part RegisterEntry. One-to-Many relation between RegisterEntry and BookEntry.
3. Book --> To store book basic info which won't change like book name, author.
4. BookDetails --> Child table of Book to store updatable information like book quantity available.
5. User --> to store user details

**In-memory database console:**
http://localhost:8080/h2-console