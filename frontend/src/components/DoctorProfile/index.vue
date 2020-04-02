<template>
  <v-row>
    <v-col cols="8" class="offset-2">
      <v-card>
        <v-card-text align="center">
          <div>psihoterapeut</div>
          <p class="display-1 text--primary">
            {{ doc.data.name }}
          </p>
          <p>{{ doc.data.occupation }}</p>
          <div><v-icon>mdi-email</v-icon>{{ doc.data.email }}</div>
          <div><v-icon>mdi-phone</v-icon>{{ doc.data.phone }}</div>
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

@Component
export default class DoctorProfile extends Vue {
  @Prop() private doc!: Doctor;
}
</script>

<style>
</style>
