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
import { AppModule } from '@/store';

@Component
export default class Info extends Vue {
  public close() {
    AppModule.clearInfo();
  }

  get showMessage() {
    return this.info.message !== '';
  }

  set showMessage(val: boolean) {
    if (!val) {
      this.close();
    }
  }

  get info() {
    return AppModule.info;
  }
}
</script>
