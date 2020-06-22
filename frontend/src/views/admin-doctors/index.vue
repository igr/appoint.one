<template>
  <v-row justify="center">
    <v-col
      cols="12"
      md="8"
    >
      <v-card>
        <v-card-title>
          Lista terapeuta
          <v-spacer />
          <v-text-field
            v-model="search"
            append-icon="mdi-magnify"
            label="Pretraga"
            single-line
            hide-details
          />
        </v-card-title>
        <v-data-table
          :headers="headers"
          :items="doc"
          :items-per-page="15"
          :search="search"
          class="elevation-1"
        />
      </v-card>
    </v-col>
    <v-col
      cols="8"
      md="8"
    >
      <v-btn
        dark
        @click="downloadCsv()"
      >
        Preuzmi CSV
      </v-btn>
    </v-col>
  </v-row>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';
import { Doctor } from '@/model/Doctor';
import DoctorApi from '@/api/DoctorApi';
import StatsApi from '@/api/StatsApi';

@Component({
  name: 'AdminDoctors',
})
export default class extends Vue {
  private search = '';

  private headers = [
    {
      text: 'ID',
      align: 'start',
      sortable: true,
      value: 'id',
    },
    { text: 'Ime', value: 'data.name' },
    { text: 'e-pošta', value: 'data.email' },
    { text: 'Telefon', value: 'data.phone' },
  ];

  private doctors: Doctor[] = [];

  get doc(): Doctor[] {
    return this.doctors;
  }

  async created() {
    await this.fetchData();
  }

  private async fetchData() {
    const { data } = await DoctorApi.getAll();
    const d: Doctor[] = data;
    d.forEach((it) => this.doctors.push(it));
  }

  async downloadCsv() {
    const { data } = await StatsApi.getDoctors();

    const fileURL = window.URL.createObjectURL(new Blob([data]));
    const fileLink = document.createElement('a');

    fileLink.href = fileURL;
    fileLink.setAttribute('download', 'doctors.csv');
    document.body.appendChild(fileLink);
    fileLink.click();
  }
}
</script>

<style scoped>
</style>
