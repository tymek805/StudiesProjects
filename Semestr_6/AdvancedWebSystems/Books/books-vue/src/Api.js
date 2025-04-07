import axios from 'axios';

const apiClient = axios.create({
    baseURL: 'http://localhost:8000/api/v1',
    headers: {
        'Content-Type': 'application/json',
    },
});

export default {
    getBooks() {
        return apiClient.get('/books');
    },
    deleteBook(bookId) {
        return apiClient.delete(`/book/${bookId}`);
    }
};
