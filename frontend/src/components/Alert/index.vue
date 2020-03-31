<template>
  <v-snackbar v-model="message" top="top" color="red" :timeout="5000">
    {{ message }}
    <v-btn text dark @click.native="close">
      close
    </v-btn>
  </v-snackbar>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';
import { AppModule } from '@/store/modules/app';

@Component
export default class Alert extends Vue {
  set message(value) {
    AppModule.setError(value);
  }

  public close() {
    AppModule.setError('');
  }

  get message() {
    return AppModule.error;
  }
}
</script>
