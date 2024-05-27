**Getting Started**

To run the application using Docker Compose with PostgreSQL, follow these steps:

    1. Clone the repository from GitHub
    2. Navigate to the project directory: cd library-management-system
    3. Run Docker Compose to start the containers:
    4. docker compose up -d
    5. This command will start the PostgreSQL container named ps-database, configured with the specified username (hamza) and password (password).
After these steps your container should be up and running.

**Creating the library Database**

After your containers are up and running, you can create a database named library by following these steps:

1. Access the Running Database Container:
Open your terminal.
Execute the following command to access the running PostgreSQL container. This command opens a bash shell inside the container.

        docker exec -it ps-database bash
2. Connect to PostgreSQL:
Once inside the container, connect to the PostgreSQL instance using the psql command:    

        psql -U hamza
3. Create the library Database:
    After connecting to PostgreSQL, create the database by running:
   
        CREATE DATABASE library;

After following these steps you can run the application without facing any problems.

**API Endpoints**
**Book Management**

GET /api/books: Retrieve a list of all books.

GET /api/books/{id}: Retrieve details of a specific book by ID.

POST /api/books: Add a new book to the library.
Request Body:

    {
     "title": "The Great Gatsby",
     "author": "F. Scott Fitzgerald",
     "publicationYear": 1925,
     "ISBN": "9780743273565"
    }

PUT /api/books/{id}: Update an existing book's information.
Request Body:

    {
      "title": "Updated Title",
      "author": "Updated Author",
      "publicationYear": 2022,
      "ISBN": "9780743273565"
    }

or you can update only a specific field by providing a value for it and ignoring the other fields like:
Request Body:

    {
      "title": "Updated Title"
    }

DELETE /api/books/{id}: Remove a book from the library.

**Patron Management**

GET /api/patrons: Retrieve a list of all patrons.

GET /api/patrons/{id}: Retrieve details of a specific patron by ID.

POST /api/patrons: Add a new patron to the system.
Request Body:

    {
        "name": "John Doe",
        "email": "john.doe@example.com",
        "phoneNumber": "(941) 430-6886 x985",
        "address": "Suite 071 968 Maribeth Mission, South Cletustown, WA 60694"
    }

PUT /api/patrons/{id}: Update an existing patron's information.
Request Body:

    {
        "name": "John Doe",
        "email": "john.doe@example.com",
        "phoneNumber": "(941) 430-6886 x985",
        "address": "Suite 071 968 Maribeth Mission, South Cletustown, WA 60694"
    }

or you can update only a specific field by providing a value for it and ignoring the other fields like:
Request Body:

    {
      "name": "Hamza Azeem"
    }

DELETE /api/patrons/{id}: Remove a patron from the system.

**Borrowing**

POST /api/borrow/{bookId}/patron/{patronId}: Allow a patron to borrow a book.

PUT /api/return/{bookId}/patron/{patronId}: Record the return of a borrowed book by a patron.

**Data Storage**
The application uses PostgreSQL as the database to persist book, patron, and borrowing record details. Proper relationships are set up between entities.

**Validation and Error Handling**

The API requests undergo input validation to ensure the correctness of the data. In case of validation failures or exceptions, appropriate HTTP status codes and error messages are returned for graceful error handling












    

    
