import { createApp } from 'vue';
import App from './App.vue';

import 'bootstrap/dist/css/bootstrap.css';
import 'bootstrap-vue-next/dist/bootstrap-vue-next.css';

import BootstrapVueNext from 'bootstrap-vue-next';
import VueGoodTablePlugin from 'vue-good-table-next';
import 'vue-good-table-next/dist/vue-good-table-next.css';

const app = createApp(App);
app.use(BootstrapVueNext);
app.use(VueGoodTablePlugin);
app.mount('#app');
