# library-management-system

Pre-requisite : You have Maven and Node.js installed

**Technologies:**
1. Java
2. Spring Boot: 2.4.3
3. Spring Data JPA
4.  JUnit: 5
5. Mockito: 3.6
6. Angular: 11
7. Boostrap: 4.6
8. Database: in-memory H2

**How to run:**
1. Open command prompt
2. Go to project parent folder
3. Run command: mvn spring-boot:run

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