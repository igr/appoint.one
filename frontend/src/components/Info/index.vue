<template>
  <v-alert
    v-model="showMessage"
    :type="type"
    :dismissible="true"
    :prominent="true"
  >
    {{ message }}
  </v-alert>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';
import { AppModule } from '@/store/modules/app';

@Component
export default class Info extends Vue {
  public close() {
    AppModule.clearInfoMessage();
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
    return AppModule.infoMessage;
  }

  get type() {
    return 'warning';
  }
}
</script>
