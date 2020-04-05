<template>
  <v-row justify="center">
    <v-col
      cols="12"
      md="8"
    >
      <v-card>
        <v-card-text
          align="center"
        >
          <div>psihoterapeut</div>
          <p class="display-2 text--primary">
            {{ doc.data.name }}
          </p>
          <p>{{ occupationText(doc.data.occupation) }}</p>
          <div class="mb-2" style="font-size: 1.5em;">
            <v-icon class="mr-2">mdi-email</v-icon>{{ doc.data.email }}
          </div>
          <div style="font-size: 1.5em;">
            <v-icon class="mr-2">mdi-phone</v-icon>{{ doc.data.phone }}
          </div>
        </v-card-text>
        <v-card-actions class="justify-center">
          <v-btn
            fab
            dark
            color="red"
            :href="`mailto:${doc.data.email}`"
          >
            <v-icon>mdi-email</v-icon>
          </v-btn>
          <v-btn
            fab
            dark
            color="primary"
            :href="`tel:${doc.data.phone}`"
          >
            <v-icon>mdi-phone</v-icon>
          </v-btn>
          <v-btn
            fab
            dark
            color="green"
            :href="`whatsapp://send?abid=${doc.data.phone}`"
          >
            <v-icon>mdi-whatsapp</v-icon>
          </v-btn>
          <v-btn
            fab
            dark
            color="purple"
            :href="`whatsapp://send?abid=${doc.data.phone}`"
          >
            <v-icon>mdi-cellphone</v-icon>
          </v-btn>
          <v-btn
            fab
            dark
            color="blue"
            :href="`${doc.data.zoom}`"
          >
            <v-icon>mdi-webcam</v-icon>
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-col>
  </v-row>
</template>

<script lang="ts">
import { Component, Prop, Vue } from 'vue-property-decorator';
// eslint-disable-next-line no-unused-vars
import { Doctor } from '@/model/Doctor';
import { occupationOf } from '@/utils/data';

@Component
export default class DoctorProfile extends Vue {
  @Prop() private doc!: Doctor;

  public occupationText(value: Number) {
    return occupationOf(value);
  }
}
</script>

<style>
</style>
