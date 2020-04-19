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
      <v-sheet
        class="text-left mx-auto"
        style="max-width:432px"
      >
        <v-btn
          text
          color="blue"
          to="/doc/1"
        >
          BESPLATNA PSIHOTERAPIJSKA PODRŠKA<br>U KRIZI COVID-19
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
import { AppModule } from '@/store';

@Component({
  name: 'Home',
})
export default class extends Vue {
  private hover = false;

  mounted() {
    window.addEventListener('beforeinstallprompt', this.addToHomescreen);
  }

  beforeDestroy() {
    window.removeEventListener('beforeinstallprompt', this.addToHomescreen);
  }

  addToHomescreen(e: any) {
    // Prevent Chrome 67 and earlier from automatically showing the prompt
    e.preventDefault();
    // Show Add to Home Screen prompt if it is not called already
    if (!AppModule.hasA2HSTriggered) {
      e.userChoice.then((choiceResult: any) => {
        if (choiceResult.outcome === 'accepted') {
          AppModule.setA2HS({ triggered: true, accepted: true });
        } else {
          AppModule.setA2HS({ triggered: true, accepted: false });
        }
      });
      e.prompt();
    }
  }
}
</script>

<style lang="scss" scoped></style>
