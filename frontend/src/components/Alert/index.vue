<template>
  <v-snackbar
    v-model="showMessage"
    top="top"
    color="red"
    :timeout="5000"
  >
    {{ message }}
    <v-btn
      text
      dark
      @click.native="close"
    >
      close
    </v-btn>
  </v-snackbar>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';
import { AppModule } from '@/store';

@Component
export default class Alert extends Vue {
  public close() {
    AppModule.clearAlertMessage();
  }

  get showMessage() {
    return this.message !== '';
  }

  set showMessage(val: boolean) {
    if (!val) {
      this.close();
    }
  }

  get message() {
    return AppModule.alertMessage;
  }
}
</script>
