<template>
  <v-row justify="center">
    <v-col cols="12" md="8">
      <!-- link1 -->
      <v-hover>
        <template v-slot="{ hover }">
          <v-card
            :elevation="hover ? 24 : 6"
            class="mb-12 mx-auto"
            max-width="400px"
            to="/doctor"
          >
            <v-img
              class="pa-6"
              style="background-color: #38393e"
              src="@/assets/images/menu-doctor.png"
            ></v-img>
            <v-card-title>
              psihoterapeuti
            </v-card-title>
            <v-card-text>
              Ovo je tekst
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
            ></v-img>
            <v-card-title>
              korisnici
            </v-card-title>
            <v-card-text>
              Ovo je tekst
            </v-card-text>
          </v-card>
        </template>
      </v-hover>
      <div>
        <router-link to="/login">
          Login
        </router-link>
      </div>
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
import HelloWorld from '@/components/HelloWorld.vue';
import { UserModule } from '@/store/modules/user';

@Component({
  name: 'Home',
  components: {
    HelloWorld,
  },
})
export default class extends Vue {
  private hover: boolean = false;

  logout() {
    UserModule.LogOut();
  }
}
</script>

<style lang="scss" scoped></style>
