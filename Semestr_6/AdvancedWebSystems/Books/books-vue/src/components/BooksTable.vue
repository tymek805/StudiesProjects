<template>
  <div class="p-4 bg-white shadow-sm rounded vh-100">
    <div class="d-flex justify-content-between align-items-center mb-3">
      <h2 class="h4">Book List</h2>
      <b-button variant="primary" @click="addBook">Add Book</b-button>
    </div>

    <vue-good-table
        :columns="columns"
        :rows="books"
        :pagination-options="{ enabled: true, perPage: 5 }"
        :search-options="{ enabled: true, placeholder: 'Search books...' }"
        :sort-options="{ enabled: true }"
    >
      <template #table-row="props">
        <template v-if="props.column.field === 'actions'">
          <div class="d-flex gap-2">
            <b-button size="sm" variant="warning" @click="editBook(props.row)">Edit</b-button>
            <b-button size="sm" variant="danger" @click="deleteBook(props.row)">Delete</b-button>
          </div>
        </template>
        <template v-else>
          {{ props.formattedRow[props.column.field] }}
        </template>
      </template>
    </vue-good-table>
    <b-alert
        v-model="show"
        :variant='"success"'
        dismissible
        @dismissed="notification.message = ''"
        class="mb-3 bottom-alert"
    >
      {{ notification.message }}
    </b-alert>
  </div>
</template>

<script>
import Api from '../Api.js';
import {BAlert, BButton} from "bootstrap-vue-next";

export default {
  name: 'BooksTable',
  components: {BAlert, BButton},
  data() {
    return {
      books: [],
      notification: {
        show: false,
        message: '',
        variant: null, // success | danger | info | warning
      },
      columns: [
        { label: 'ID', field: 'id', type: 'number', sortable: true },
        { label: 'Title', field: 'title', sortable: true },
        { label: 'Author', field: 'authorFullName', sortable: true },
        { label: 'Pages', field: 'pages', type: 'number', sortable: true },
        { label: 'Actions', field: 'actions' },
      ],
    };
  },
  mounted() {
    this.fetchBooks();
  },
  methods: {
    fetchBooks() {
      Api.getBooks()
          .then((response) => {
            this.books = response.data.data.map((book) => ({
              ...book,
              authorFullName: `${book.author.name} ${book.author.surname}`,
            }));
          })
          .catch((error) => {
            console.error('Failed to fetch books:', error);
          });
    },
    addBook() {
      console.log('Add Book clicked');
      // Open Bootstrap modal to add
    },
    editBook(book) {
      console.log('Edit Book:', book);
      // Open Bootstrap modal to edit
    },
    deleteBook(book) {
      if (window.confirm(`Are you sure you want to delete the book: ${book.title}?`)) {
        Api.deleteBook(book.id)
            .then(() => {
              this.fetchBooks();
              this.showNotification('Book deleted successfully!', "success");
            })
            .catch((error) => {
              console.error('Error deleting book:', error);
              this.showNotification('Failed to delete the book.', "danger");
            });
      }
    },
    showNotification(message, variant) {
      this.notification.message = message;
      this.notification.variant = variant;
      this.show = true;
      setTimeout(() => {
        this.show = false;
      }, 2000);
    },
  },
};
</script>
<style>
.bottom-alert {
  position: absolute !important;
  bottom: 0;
}
</style>