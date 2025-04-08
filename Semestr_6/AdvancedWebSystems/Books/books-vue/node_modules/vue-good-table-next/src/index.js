import VueGoodTable from './components/Table.vue';

export default {
  install: (app, options) => {
    app.component('VueGoodTable', VueGoodTable);
  }
}

export { VueGoodTable };