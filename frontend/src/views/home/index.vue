<template>
  <v-row
    align="center"
    justify="center"
  >
    <!--
    <div class="col-12 text-center">
      <h3>
        BROJ ZA MEDICINSKE RADNIKE
      </h3>
      <v-btn
        x-large
        color="success"
        dark
        href="tel://+38165555333"
      >
        <v-icon
          class="mr-3"
        >
          mdi-phone
        </v-icon>
        065-555-333
      </v-btn>
    </div>
    -->
    <div class="col-12 text-center">
      <h3>
        Korisni tekstovi
      </h3>
      <v-sheet
        class="text-left mx-auto"
        style="max-width:432px"
      >
        <v-list-item to="/doc/1">
          <v-list-item-avatar>
            <v-img src="@/assets/images/mental-health.jpg" />
          </v-list-item-avatar>
          <v-list-item-content>
            <v-list-item-title color="blue">
              Kako sačuvati mentalno zdravlje?
            </v-list-item-title>
            <v-list-item-subtitle>
              Život preplavljen strahom može da se preživi
            </v-list-item-subtitle>
          </v-list-item-content>
        </v-list-item>
        <v-list-item to="/doc/2">
          <v-list-item-avatar>
            <v-img src="@/assets/images/call-center.jpg" />
          </v-list-item-avatar>
          <v-list-item-content>
            <v-list-item-title color="blue">
              Psihoterapeuti su vam na raspolaganju!
            </v-list-item-title>
            <v-list-item-subtitle>
              Besplatna pomoć za sve građane! Pshihoterapeuti su vam na raspolaganju
            </v-list-item-subtitle>
          </v-list-item-content>
        </v-list-item>
      </v-sheet>
    </div>
    <v-col
      cols="12"
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
            <img
              style="background-color: #38393e; width: 100%"
              class="pa-4"
              src="@/assets/images/menu-user.png"
            >
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
  private hover = false;
}
</script>

<style lang="scss" scoped></style>
