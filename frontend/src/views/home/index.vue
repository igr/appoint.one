<template>
  <v-row justify="center">
    <v-col
      cols="12"
      md="4"
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
              Vi ste psihoterapeut?
            </v-card-title>
            <v-card-text>
              Prijavite se
            </v-card-text>
          </v-card>
        </template>
      </v-hover>
    </v-col>
    <v-col
      cols="12"
      md="4"
    >
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
              Tražite razgovor?
            </v-card-title>
            <v-card-text>
              Zakažite termin
            </v-card-text>
          </v-card>
        </template>
      </v-hover>
    </v-col>
  </v-row>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';

@Component({
  name: 'Home',
})
export default class extends Vue {
  private hover: boolean = false;
}
</script>

<style lang="scss" scoped></style>
