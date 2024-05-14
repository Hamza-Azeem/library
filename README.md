**Getting Started**

To run the application using Docker Compose with PostgreSQL, follow these steps:

    1. Clone the repository from GitHub
    2. Navigate to the project directory: cd library-management-system
    3. Run Docker Compose to start the containers:
    4. docker-compose up -d
    5. This command will start the PostgreSQL container named ps-database, configured with the specified username (hamza) and password (password).
    6. Once the containers are up and running, you can interact with the API endpoints.

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
     "isbn": "9780743273565"
    }

PUT /api/books/{id}: Update an existing book's information.
Request Body:

    {
      "title": "Updated Title",
      "author": "Updated Author",
      "publicationYear": 2022,
      "isbn": "9780743273565"
    }

or you can update only a specific field by providing a value for it and ignoring the other fields like:
Request Body:

    {
      "title": "Updated Title"
    }

DELETE /api/books/{id}: Remove a book from the library.

**Patron Management**

























    

    
