<template>
  <v-row justify="center">
    <v-col
      cols="12"
      md="6"
    >
      <v-dialog
        v-model="showDialog"
        width="500"
      >
        <v-card>
          <v-card-title
            class="headline grey lighten-2"
            primary-title
          >
            Promena šifre
          </v-card-title>
          <v-card-text>
            {{this.infoMsg}}
          </v-card-text>
          <v-divider></v-divider>
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn
              color="primary"
              text
              @click="showDialog = false"
            >
              OK
            </v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>


      <v-text-field
        append-icon="lock"
        label="e-mail"
        :value="targetUser.name"
        outlined
        disabled
      />
      <v-text-field
        ref="passwordRef"
        v-model="newPassword"
        :type="show1 ? 'text' : 'password'"
        :append-icon="show1 ? 'mdi-eye' : 'mdi-eye-off'"
        label="Nova šifra"
        :rules="rules.passwordRules"
        outlined
        counter
        @click:append="show1 = !show1"
      />
      <v-btn
        color="primary"
        @click="submitModifications"
        :disabled="newPassword.length < 5"
      >
        Sačuvaj
      </v-btn>
    </v-col>
  </v-row>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';
import UserApi from '@/api/UserApi';

@Component({
  name: 'AdminUser',
})
export default class extends Vue {
  private targetUser = {
    id: 0,
    name: '',
    role: '',
    token: '',
  };

  private show1 = false;

  private showDialog = false;

  private newPassword = '';

  private infoMsg = '';

  private rules = {
    passwordRules: [
      (v: string) => !!v || 'Lozinka je obavezna',
      (v: string) => (v && v.length >= 5) || 'Lozinka mora imati najmanje 5 karaktera',
    ],
  };

  async created() {
    await this.fetchData();
  }

  private async fetchData() {
    const { data } = await UserApi.getUser(+this.$route.params.id);
    this.targetUser = data;
  }

  private async submitModifications() {
    const userId: number = +this.$route.params.id;
    const pass: string = this.newPassword;
    await UserApi.updatePassword(userId, pass);
    this.displaySnackbarInfo(true);
  }

  displaySnackbarInfo(success: boolean) {
    this.showDialog = true;
    this.newPassword = '';
    if (success === true) {
      this.infoMsg = 'Sačuvano';
    } else {
      this.infoMsg = 'Došlo je do greške!';
    }
  }
}
</script>
