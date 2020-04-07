<template>
  <v-row justify="center">
    <v-col cols="12" md="6">
      <v-snackbar
        v-model="showSnackbar"
        :color="snackbarColor"
        :top="snackbarTop"
      >
        {{snackbarText}}
        <v-btn
          dark
          text
          @click="showSnackbar = false"
        >
          OK
        </v-btn>
      </v-snackbar>
      <v-text-field
        append-icon="lock"
        label="e-mail"
        :value="targetUser.name"
        outlined
        disabled
      ></v-text-field>
      <v-text-field
        :type="show1 ? 'text' : 'password'"
        :append-icon="show1 ? 'mdi-eye' : 'mdi-eye-off'"
        label="Nova šifra"
        ref="passwordRef"
        :rules="rules.passwordRules"
        v-model="newPassword"
        outlined
        counter
        @click:append="show1 = !show1"
      ></v-text-field>
      <v-btn color="primary"
        @click="submitModifications">
        Sačuvaj
      </v-btn>
    </v-col>
  </v-row>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';
// eslint-disable-next-line no-unused-vars
import UserApi from '@/api/UserApi';
// eslint-disable-next-line no-unused-vars
import { User } from '@/model/User';

@Component({
  name: 'UserEditor',
})
export default class extends Vue {
  private targetUser = {
    id: 0,
    name: '',
    role: '',
    token: '',
  }

  private show1 = false;

  private newPassword = '';

  private snackbarColor = 'success'; // or error

  private showSnackbar = false;

  private snackbarText = '';

  private snackbarTop = true;

  private rules = {
    passwordRules: [
      (value: string) => (value || '').length >= 5 || 'Min 5 karaktera',
    ],
  }

  async created() {
    this.fetchData();
  }

  private async fetchData() {
    const { data } = await UserApi.getUser(+this.$route.params.id);
    const d: User = data;
    this.targetUser = d;
  }

  private async submitModifications() {
    const userId: number = +this.$route.params.id;
    const pass: string = this.newPassword;
    const { data } = await UserApi.modifyUserData(userId, pass);
    // TODO have the message work as it should
    this.displaySnackbarInfo(data);
  }

  displaySnackbarInfo(success: boolean) {
    if (success) {
      this.showSnackbar = true;
      this.snackbarText = 'Sačuvano';
      this.snackbarColor = 'success';
    } else {
      this.snackbarText = 'Došlo je do greške';
      this.snackbarColor = 'error';
    }
  }
}

</script>
