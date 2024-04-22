import './assets/main.css'

import { createApp } from 'vue'
import { createPinia } from 'pinia'

import App from './App.vue'
import router from './router'

import PrimeVue from 'primevue/config';
import "primeflex/primeflex.css";
import 'primeicons/primeicons.css'
import 'primevue/resources/themes/aura-dark-green/theme.css'
import ToastService from 'primevue/toastservice';

import 'boxicons/css/boxicons.css';

const app = createApp(App)

app.use(createPinia())
app.use(router)
app.use(PrimeVue)
app.use(ToastService)

app.mount('#app')
