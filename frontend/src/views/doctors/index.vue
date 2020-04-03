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
        >
          <template v-slot:item.data.confirmed="{ item }">
            <v-simple-checkbox
              v-model="item.data.confirmed"
              disabled
            />
          </template>
        </v-data-table>
      </v-card>
    </v-col>
  </v-row>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';
// eslint-disable-next-line no-unused-vars
import { Doctor } from '@/model/Doctor';
import DoctorApi from '@/api/DoctorApi';

@Component({
  name: 'ListOfDoctors',
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
    { text: 'Država', value: 'data.country.name' },
    { text: 'e-pošta', value: 'data.email' },
    { text: 'Telefon', value: 'data.phone' },
    { text: 'Zoom', value: 'data.zoom' },
    { text: 'Potvrđen', value: 'data.confirmed' },
  ];

  private doctors: Doctor[] = [];

  get doc(): Doctor[] {
    return this.doctors;
  }

  async created() {
    this.fetchData();
  }

  private async fetchData() {
    const { data } = await DoctorApi.getAll();
    const d: Doctor[] = data;
    d.forEach((it) => this.doctors.push(it));
    console.log(this.doctors);
  }
}
</script>

<style scoped>
</style>
