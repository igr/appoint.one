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
    <div class="col-12 text-center align-center">
      <v-sheet
        class="col-12 md-6"
      >
        <v-btn
          text
          color="blue"
          to="/doc/1"
        >
          BESPLATNA PSIHOTERAPIJSKA PODRŠKA U KRIZI COVID-19
        </v-btn>
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
