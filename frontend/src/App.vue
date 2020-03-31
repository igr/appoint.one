<template>
  <v-app>
    <Alert />
    <router-view />
  </v-app>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';
import Alert from '@/components/Alert/index.vue';

@Component({
  components: {
    Alert,
  },
})
export default class App extends Vue {}
</script>
