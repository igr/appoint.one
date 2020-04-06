<template>
  <v-row justify="center">
    <v-col cols="12" md="6">
      <v-text-field
        append-icon="lock"
        label="e-mail"
        value="PLACEHOLDER@gmail.com"
        outlined
        disabled
      ></v-text-field>
      <v-text-field
        type="password"
        label="New password"
        outlined
      ></v-text-field>
      <v-btn color="primary" text @click="submit">Save</v-btn>
    </v-col>
  </v-row>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';
import UserApi from '@/api/UserApi';
// eslint-disable-next-line no-unused-vars
import { User } from '@/model/User';

@Component({
  name: 'UserEditor',
})
export default class extends Vue {
  private targetUser: User[] = [];

  // post to 'admin/modifyUserData'

  async created() {
    this.fetchData();
  }

  // eslint-disable-next-line no-empty-function
  private async fetchData() {
    // `user/${userId}`
    const { data } = await UserApi.getUser(+this.$route.params.id);
    const d: User[] = data;
    d.forEach((it) => this.targetUser.push(it));
    console.log(data);
  }
}
</script>
