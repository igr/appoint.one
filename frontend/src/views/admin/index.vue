<template>
  <v-row
    justify="center"
  >
    <v-col
      cols="12"
      md="8"
    >
      <h1>Administracija</h1>
      <v-btn
        x-large
        color="red"
        style="color: white;"
        class="ma-2"
        to="/admin/doctors"
      >
        Doktori
      </v-btn>
      <v-btn
        x-large
        color="red"
        style="color: white;"
        class="ma-2"
        to="/admin/evals"
      >
        Evaluacije
      </v-btn>
      <v-btn
        x-large
        color="red"
        style="color: white;"
        class="ma-2"
        @click.stop="sendTestMail()"
      >
        Test email
      </v-btn>
    </v-col>
  </v-row>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';
import EmailApi from '@/api/EmailApi';

@Component({
  name: 'Admin',
})
export default class extends Vue {
  async sendTestMail() {
    await EmailApi.test();
    alert('OK');
  }
}
</script>

<style lang="scss">
</style>
