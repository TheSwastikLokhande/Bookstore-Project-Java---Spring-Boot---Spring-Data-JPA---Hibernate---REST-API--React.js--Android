import { useState, useEffect } from 'react'
import reactLogo from './assets/react.svg'
import viteLogo from '/vite.svg'
import './App.css'

function App() {
  const [books, setBooks] = useState([])
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState(null)
  const [form, setForm] = useState({ title: '', author: '', isbn: '', price: '' })
  const [editingId, setEditingId] = useState(null)

  useEffect(() => {
    fetchBooks()
  }, [])

  const fetchBooks = () => {
    setLoading(true)
    fetch('http://localhost:8080/bookstore-api/books')
      .then((response) => {
        if (!response.ok) {
          throw new Error('Network response was not ok')
        }
        return response.json()
      })
      .then((data) => {
        setBooks(data)
        setLoading(false)
      })
      .catch((error) => {
        setError(error.message)
        setLoading(false)
      })
  }

  const handleInputChange = (e) => {
    const { name, value } = e.target
    setForm((prev) => ({ ...prev, [name]: value }))
  }

  const handleSubmit = (e) => {
    e.preventDefault()
    if (editingId === null) {
      // Create new book
      fetch('http://localhost:8080/bookstore-api/books', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ ...form, price: parseFloat(form.price) }),
      })
        .then((response) => {
          if (!response.ok) {
            throw new Error('Failed to create book')
          }
          return response.json()
        })
        .then(() => {
          setForm({ title: '', author: '', isbn: '', price: '' })
          fetchBooks()
        })
        .catch((error) => setError(error.message))
    } else {

      console.log(editingId)
      // Update existing book
      fetch(`http://localhost:8080/bookstore-api/books/${editingId}`, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ ...form, price: parseFloat(form.price) }),
      })
        .then((response) => {
          if (!response.ok) {
            throw new Error('Failed to update book')
          }
          return response.json()
        })
        .then(() => {
          setForm({ title: '', author: '', isbn: '', price: '' })
          setEditingId(null)
          fetchBooks()
        })
        .catch((error) => setError(error.message))
    }
  }

  const handleEdit = (book) => {
    setForm({
      title: book.title,
      author: book.author,
      isbn: book.isbn,
      price: book.price.toString(),
    })
    setEditingId(book.id)
  }

  const handleDelete = (id) => {
    console.log(id)
    fetch(`http://localhost:8080/bookstore-api/books/${id}`, {
      method: 'DELETE',
    })
      .then((response) => {
        if (!response.ok) {
          throw new Error('Failed to delete book')
        }
        fetchBooks()
      })
      .catch((error) => setError(error.message))
  }

  if (loading) {
    return <p>Loading books...</p>
  }

  if (error) {
    return <p>Error loading books: {error}</p>
  }

  return (
    <>
      <h1>Bookstore</h1>

      <form onSubmit={handleSubmit} className="book-form">
        <input
          type="text"
          name="title"
          placeholder="Title"
          value={form.title}
          onChange={handleInputChange}
          required
        />
        <input
          type="text"
          name="author"
          placeholder="Author"
          value={form.author}
          onChange={handleInputChange}
          required
        />
        <input
          type="text"
          name="isbn"
          placeholder="ISBN"
          value={form.isbn}
          onChange={handleInputChange}
          required
        />
        <input
          type="number"
          step="0.01"
          name="price"
          placeholder="Price"
          value={form.price}
          onChange={handleInputChange}
          required
        />
        <button type="submit">{editingId === null ? 'Add Book' : 'Update Book'}</button>
        {editingId !== null && (
          <button
            type="button"
            onClick={() => {
              setForm({ title: '', author: '', isbn: '', price: '' })
              setEditingId(null)
            }}
          >
            Cancel
          </button>
        )}
      </form>

      <table>
        <thead>
          <tr>
            <th>ID</th>
            <th>Title</th>
            <th>Author</th>
            <th>ISBN</th>
            <th>Price</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {books.map(({ id, title, author, isbn, price }) => (
            <tr key={id}>
              <td>{id}</td>
              <td>{title}</td>
              <td>{author}</td>
              <td>{isbn}</td>
              <td>{price.toFixed(2)}</td>
              <td>
                <button onClick={() => handleEdit({ id, title, author, isbn, price })}>
                  Edit
                </button>
                <button onClick={() => handleDelete(id)}>Delete</button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>

      <p className="read-the-docs">
        Click on the Vite and React logos to learn more
      </p>
    </>
  )
}

export default App
