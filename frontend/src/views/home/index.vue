<template>
  <v-row justify="center">
    <v-col
      cols="12"
      md="8"
    >
      <!-- link1 -->
      <v-hover>
        <template v-slot="{ hover }">
          <v-card
            :elevation="hover ? 24 : 6"
            class="mb-12 mx-auto"
            max-width="400px"
            to="/my"
          >
            <v-img
              class="pa-6"
              style="background-color: #38393e"
              src="@/assets/images/menu-doctor.png"
            />
            <v-card-title>
              psihoterapeuti
            </v-card-title>
            <v-card-text>
              Prijava psihoterapeuta
            </v-card-text>
          </v-card>
        </template>
      </v-hover>
      <!-- link2 -->
      <v-hover>
        <template v-slot="{ hover }">
          <v-card
            :elevation="hover ? 24 : 6"
            class="mb-12 mx-auto"
            max-width="400px"
            to="/inquire"
          >
            <v-img
              class="align-center"
              style="background-color: #38393e"
              src="@/assets/images/menu-user.png"
            />
            <v-card-title>
              korisnici
            </v-card-title>
            <v-card-text>
              Zakazivanje termina
            </v-card-text>
          </v-card>
        </template>
      </v-hover>
      <div>
        <a
          href="#"
          @click="logout"
        >
          Logout
        </a>
      </div>
    </v-col>
  </v-row>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';
import { UserModule } from '@/store/modules/user';

@Component({
  name: 'Home',
})
export default class extends Vue {
  private hover: boolean = false;

  logout() {
    UserModule.LogOut();
  }
}
</script>

<style lang="scss" scoped></style>
