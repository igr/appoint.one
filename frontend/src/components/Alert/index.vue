<template>
  <v-snackbar
    v-model="showMessage"
    top="top"
    :color="color"
    :timeout="5000"
  >
    <span id="alert">{{ alert.message }}</span>
    <v-btn
      icon
      dark
      @click.native="close"
    >
      <v-icon>
        mdi-close
      </v-icon>
    </v-btn>
  </v-snackbar>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';
import { AppModule } from '@/store';

@Component
export default class Alert extends Vue {
  public close() {
    AppModule.clearAlert();
  }

  get showMessage() {
    return this.alert.message !== '';
  }

  set showMessage(val: boolean) {
    if (!val) {
      this.close();
    }
  }

  get alert() {
    return AppModule.alert;
  }

  get color() {
    switch (this.alert.type) {
      case 'info': return 'info';
      case 'success': return 'success';
      default: return 'red';
    }
  }
}
</script>

<style lang="scss">
span#alert {
  font-size: 1.2em;
  font-weight: bold;
}
</style>
