<template>
  <div class="p-4 bg-white shadow-sm rounded vh-100">
    <b-modal ref="bookModal" title="New Book" @ok="handleSubmit" @cancel="handleModalClose">
      <b-form @submit.prevent="handleSubmit">
        <b-form-group label="Title" label-for="title" :invalid="titleInvalid">
          <b-form-input
              v-model="form.title"
              type="text"
              placeholder="Enter title"
              required />
        </b-form-group>

        <b-form-group label="Author" label-for="author" :invalid="authorInvalid">
          <b-form-select
              id="author"
              v-model="form.authorId"
              :options="authors"
              required
              text-field="fullname"
              value-field="id"
              placeholder="Select an author"
          />
        </b-form-group>

        <b-form-group label="Pages" label-for="pages" :invalid="pagesInvalid">
          <b-form-input
              id="pages"
              v-model.number="form.pages"
              type="number"
              min="0"
              required
              placeholder="Enter pages"
          ></b-form-input>
        </b-form-group>
        <b-alert
            v-model=validationMessage.show
            :variant=validationMessage.variant
            class="mt-2"
        >
          {{ validationMessage.message }}
        </b-alert>
      </b-form>
    </b-modal>

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
        :pagination="true"
        :totalRows="totalBooks"
        @page-change="handlePageChange"
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
        v-model=notification.show
        :variant=notification.variant
        class="mb-3 bottom-alert"
    >
      {{ notification.message }}
    </b-alert>
  </div>
</template>

<script>
import Api from '../Api.js';
import {BAlert, BButton, BForm, BFormGroup, BFormInput, BModal} from "bootstrap-vue-next";

export default {
  name: 'BooksTable',
  components: {BModal, BForm, BFormInput, BFormGroup, BAlert, BButton},
  data() {
    return {
      books: [],
      authors: [],
      notification: {
        show: false,
        message: '',
        variant: null, // success | danger | info | warning
      },
      validationMessage: {
        show: false,
        message: '',
        variant: null,
      },
      columns: [
        { label: 'ID', field: 'id', type: 'number', sortable: true },
        { label: 'Title', field: 'title', sortable: true },
        { label: 'Author', field: 'authorFullName', sortable: true },
        { label: 'Pages', field: 'pages', type: 'number', sortable: true },
        { label: 'Actions', field: 'actions' },
      ],
      form: {
        title: '',
        authorId: null,
        pages: '',
      },
      editMode: false,
      totalBooks: 0,
      currentPage: 1,
    };
  },
  computed: {
    titleInvalid() {
      return this.form.title === null || this.form.title.length <= 0;
    },
    authorInvalid() {
      return this.form.authorId === null || this.form.authorId < 0;
    },
    pagesInvalid() {
      return this.form.pages === null || this.form.pages <= 0;
    },
    formInvalid() {
      return this.titleInvalid && this.authorInvalid && this.pagesInvalid;
    },
  },
  mounted() {
    this.fetchBooks();
    this.fetchAuthors();
  },
  methods: {
    // fetchBooks() {
    //   Api.getBooks()
    //       .then((response) => {
    //         this.books = response.data.content.map((book) => ({
    //           ...book,
    //           authorFullName: `${book.author.name} ${book.author.surname}`,
    //         }));
    //       })
    //       .catch((error) => {
    //         console.error('Failed to fetch books:', error);
    //       });
    // },
    async fetchBooks() {
      console.log(this.currentPage);
      try {
        const response = await Api.getBooks(this.currentPage - 1, this.perPage); // backend is 0-indexed
        this.books = response.data.content.map(book => ({
          ...book,
          authorFullName: `${book.author.name} ${book.author.surname}`,
        }));
        this.totalBooks = response.data.totalElements;
      } catch (error) {
        console.error('Error fetching books:', error);
      }
    },
    handlePageChange({ currentPage }) {
      this.currentPage = currentPage;
      this.fetchBooks();
    },

    fetchAuthors() {
      Api.getAuthors()
          .then((response) => {
            this.authors = response.data.data.map(author => ({
              id: author.id,
              fullname: `${author.name} ${author.surname}`
            }));
          })
          .catch((error) => {
            console.error('Failed to fetch books:', error);
          });
    },
    addBook() {
      this.editMode = false;
      this.$refs.bookModal.show();
    },
    editBook(book) {
      this.editMode = true;
      this.form.id = book.id;
      this.form.title = book.title;
      this.form.pages = book.pages;
      this.form.authorId = book.author.id;
      this.$refs.bookModal.show();
    },
    deleteBook(book) {
      if (window.confirm(`Are you sure you want to delete the book: ${book.title}?`)) {
        Api.deleteBook(book.id)
            .then(() => {
              this.fetchBooks();
              this.showNotification('Book added successfully!', "success");
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
      this.notification.show = true;
      setTimeout(() => {
        this.notification.show = false;
      }, 2000);
    },
    handleSubmit(event) {
      if (this.titleInvalid || this.authorInvalid || this.pagesInvalid) {
        let message = "Please enter valid values for: ";
        if (this.titleInvalid) {
          message += "title, ";
        }
        if (this.authorInvalid) {
          message += "author, ";
        }
        if (this.pagesInvalid) {
          message += "pages";
        }
        this.showValidationMessage(message, "danger");
        event.preventDefault();
      } else {
        const newBook = { ...this.form };
        console.log(newBook);
        if (!this.editMode) {
          Api.addBook(newBook)
              .then(() => {
                this.fetchBooks();
                this.showNotification('Book added successfully!', "success");
              })
              .catch((error) => {
                console.error('Error deleting book:', error);
                this.showNotification('Failed to add the book.', "danger");
              });
        } else {
          Api.updateBook(newBook.id, newBook)
              .then(() => {
                this.fetchBooks();
                this.showNotification('Book updated successfully!', "success");
              })
              .catch((error) => {
                console.error('Error deleting book:', error);
                this.showNotification('Failed to update the book.', "danger");
              });
        }

        this.clearForm();
      }
    },
    showValidationMessage(message, variant) {
      this.validationMessage.show = true;
      this.validationMessage.message = message;
      this.validationMessage.variant = variant;
    },
    handleModalClose() {
      if (this.formInvalid) {
        return false;
      } else {
        this.clearForm();
        return true;
      }
    },
    clearForm() {
      this.form = {
        id: '',
        title: '',
        authorId: '',
        pages: '',
      };
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