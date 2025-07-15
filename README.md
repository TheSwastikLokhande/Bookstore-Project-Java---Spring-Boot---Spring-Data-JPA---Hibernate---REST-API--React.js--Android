# Bookstore Project

This repository contains three main components of the Bookstore application:

1. **Backend REST API**  
   - Location: `bookstore-api` directory  
   - Technology: Java Spring Boot  
   - Description: Provides RESTful API endpoints for managing books, including CRUD operations.  
   - Key files:  
     - `BookController.java` - REST controller for book endpoints  
     - `BookService.java` - Service layer handling business logic  
     - `BookRepository.java` - JPA repository for database access  
     - `Book.java` - JPA entity representing a book  

2. **Frontend - React Web Application**  
   - Location: `bookstore-react` directory  
   - Technology: React.js  
   - Description: Web-based user interface for interacting with the bookstore API.  
   - Key files:  
     - `src/App.jsx` - Main React component  
     - Other React components and assets in `src/` and `public/` folders  

3. **Frontend - Android Application**  
   - Location: `BookStore_Crud` directory  
   - Technology: Android (Java)  
   - Description: Android mobile app with a RecyclerView displaying books and supporting CRUD operations.  
   - Key files:  
     - `BookAdapter.java` - Adapter for RecyclerView to display book items  
     - Layout files in `app/src/main/res/layout/` such as `book_list_item.xml`  
     - API interface in `api/BookstoreApi.java` for Retrofit calls to backend  

---

### Backend REST API

1. Navigate to the `bookstore-api` directory.  
2. Build and run the Spring Boot application using your preferred IDE or command line:  
   ```bash
   ./gradlew bootRun
   ```  
3. The API will be available at `http://localhost:8080/books`.

### React Frontend

1. Navigate to the `bookstore-react` directory.  
2. Install dependencies:  
   ```bash
   npm install
   ```  
3. Start the development server:  
   ```bash
   npm run dev
   ```  
4. Access the web app at `http://localhost:3000`.

### Android Application

1. Open the `BookStore_Crud` project in Android Studio.  
2. Build and run the app on an emulator or physical device.  
3. The app communicates with the backend API to display and manage books.

---

## Notes

- Ensure the backend API is running before starting the frontend applications.  
- The Android app uses Retrofit for API calls; verify the base URL matches your backend server address.  
- The React app also communicates with the backend API; update the API endpoint if necessary in the React source code.

---

## Repository Structure

```
/
├── bookstore-api/          # Backend REST API (Spring Boot)
├── bookstore-react/        # React frontend web app
└── BookStore_Crud/         # Android frontend app
```

---

## Contact

For any questions or issues, please contact the project maintainer.
