<template>
  <v-alert
    v-model="showMessage"
    :type="info.type"
    :dismissible="true"
    :prominent="true"
  >
    {{ info.message }}
  </v-alert>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';
import { AppModule } from '@/store/modules/app';

@Component
export default class Info extends Vue {
  public close() {
    AppModule.clearInfo();
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
    console.log(AppModule.info);
    return AppModule.info.message;
  }

  get info() {
    return AppModule.info;
  }

  get type() {
    return AppModule.info.type;
  }
}
</script>
