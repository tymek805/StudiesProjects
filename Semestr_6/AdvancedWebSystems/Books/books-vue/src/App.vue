<template>
  <div class="p-8 vh-100 vw-100 flex">
    <BooksTable />
  </div>
</template>

<script>
import BooksTable from './components/BooksTable.vue';

export default {
  name: 'App',
  components: {
    BooksTable,
  },
};
</script>

<style>
body {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  margin: 0;
  background-color: #f9fafb;
}
</style>
