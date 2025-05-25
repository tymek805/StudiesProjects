import axios from 'axios';

const apiClient = axios.create({
    baseURL: 'http://localhost:8000/api/v1',
    headers: {
        'Content-Type': 'application/json',
    },
});

export const getBooks = async (page = 0, size = 5) => {
    try {
        const response = await apiClient.get(`/books?page=${page}&size=${size}`);
        return response.data; // this contains status, message, data
    } catch (error) {
        console.error("Failed to fetch books:", error);
        throw error;
    }
};

export default {
    getBooks,

    deleteBook(bookId) {
        return apiClient.delete(`/book/${bookId}`);
    },
    addBook(book) {
        return apiClient.post(`/book`, book);
    },
    updateBook(id, book) {
        return apiClient.put(`/book/${id}`, book);
    },

    getAuthors() {
        return apiClient.get('/authors');
    },
};
